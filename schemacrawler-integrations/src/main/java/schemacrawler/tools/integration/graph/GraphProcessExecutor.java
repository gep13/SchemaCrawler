/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2018, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.tools.integration.graph;


import static java.nio.file.Files.move;
import static java.util.Objects.requireNonNull;
import static sf.util.IOUtility.readResourceFully;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;

import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.utility.ProcessExecutor;
import sf.util.FileContents;
import sf.util.SchemaCrawlerLogger;
import sf.util.StringFormat;

final class GraphProcessExecutor
  extends AbstractGraphProcessExecutor
{

  private static final SchemaCrawlerLogger LOGGER = SchemaCrawlerLogger
    .getLogger(GraphProcessExecutor.class.getName());

  private final List<String> graphvizOpts;

  GraphProcessExecutor(final Path dotFile,
                       final Path outputFile,
                       final GraphOutputFormat graphOutputFormat,
                       final List<String> graphvizOpts)
    throws SchemaCrawlerException
  {
    super(dotFile, outputFile, graphOutputFormat);

    this.graphvizOpts = requireNonNull(graphvizOpts,
                                       "No Graphviz options provided");
  }

  @Override
  public Boolean call()
  {

    final List<String> command = createDiagramCommand();
    LOGGER.log(Level.INFO,
               new StringFormat("Generating diagram using Graphviz:\n%s",
                                command.toString()));

    final ProcessExecutor processExecutor = new ProcessExecutor();
    processExecutor.setCommandLine(command);

    Integer exitCode;
    try
    {
      exitCode = processExecutor.call();
    }
    catch (final Exception e)
    {
      LOGGER.log(Level.INFO,
                 String.format("Could not generate diagram using Graphviz:\n%s",
                               command.toString()),
                 e);
      exitCode = -1;
    }
    final boolean isProcessInError = exitCode == null || exitCode != 0;

    LOGGER.log(Level.INFO,
               new FileContents(processExecutor.getProcessOutput()));
    final Supplier<String> processError = new FileContents(processExecutor
      .getProcessOutput());
    if (isProcessInError)
    {
      LOGGER.log(Level.SEVERE,
                 new StringFormat("Process returned exit code %d%n%s",
                                  exitCode,
                                  processError));
      captureRecovery(dotFile, outputFile, processExecutor.getCommand());
    }
    else
    {
      LOGGER.log(Level.WARNING, processError);
      LOGGER.log(Level.INFO,
                 new StringFormat("Generated diagram <%s>", outputFile));
    }

    return isProcessInError;
  }

  @Override
  public boolean canGenerate()
  {

    final List<String> command = new ArrayList<>();
    command.add("dot");
    command.add("-V");

    LOGGER.log(Level.INFO,
               new StringFormat("Checking if Graphviz is available:\n%s",
                                command.toString()));

    final ProcessExecutor processExecutor = new ProcessExecutor();
    processExecutor.setCommandLine(command);

    Integer exitCode;
    try
    {
      exitCode = processExecutor.call();
      LOGGER.log(Level.INFO,
                 new FileContents(processExecutor.getProcessOutput()));
    }
    catch (final Exception e)
    {
      exitCode = -1;
    }
    final boolean isProcessInError = exitCode == null || exitCode != 0;

    return isProcessInError;
  }

  private void captureRecovery(final Path dotFile,
                               final Path outputFile,
                               final List<String> command)
  {
    // Move DOT file to current directory
    final Path movedDotFile = outputFile.normalize().getParent()
      .resolve(dotFile.getFileName());

    // Print command to run
    command.remove(command.size() - 1);
    command.remove(command.size() - 1);
    command.add(outputFile.toString());
    command.add(movedDotFile.toString());

    final String message = String
      .format("%s%nGenerate your diagram manually, using:%n%s",
              readResourceFully("/dot.error.txt"),
              String.join(" ", command));

    try
    {
      move(dotFile, movedDotFile);
    }
    catch (final IOException e)
    {
      LOGGER.log(Level.INFO,
                 String
                   .format("Could not move %s to %s", dotFile, movedDotFile),
                 e);
    }

    LOGGER.log(Level.SEVERE, message);
  }

  private List<String> createDiagramCommand()
  {
    final List<String> command = new ArrayList<>();
    command.add("dot");

    command.addAll(graphvizOpts);

    command.add("-T");
    command.add(graphOutputFormat.getFormat());
    command.add("-o");
    command.add(outputFile.toString());
    command.add(dotFile.toString());

    return command;
  }

}
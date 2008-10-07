/* 
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2008, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */

package schemacrawler.tools.util;


import java.util.ArrayList;
import java.util.List;

import schemacrawler.tools.OutputFormat;
import schemacrawler.tools.util.TableCell.Align;

/**
 * Represents an HTML table row.
 * 
 * @author Sualeh Fatehi
 */
final class TableRow
{

  /**
   * System specific line separator character.
   */
  private static final String NEWLINE = System.getProperty("line.separator");

  private final OutputFormat outputFormat;
  private final List<TableCell> cells;

  TableRow(final OutputFormat outputFormat)
  {
    this.outputFormat = outputFormat;
    cells = new ArrayList<TableCell>();
  }

  TableRow(final OutputFormat outputFormat, final int colSpan)
  {
    this(outputFormat);
    cells.add(new TableCell("", 0, Align.left, colSpan, "", outputFormat));
  }

  public void add(final TableCell cell)
  {
    cells.add(cell);
  }

  /**
   * Converts the table row to HTML.
   * 
   * @return HTML
   */
  @Override
  public String toString()
  {
    if (outputFormat == OutputFormat.html)
    {
      return toHtmlString();
    }
    else
    {
      return toPlainTextString();
    }
  }

  String getFieldSeparator()
  {
    if (outputFormat == OutputFormat.csv)
    {
      return ",";
    }
    else
    {
      return "  ";
    }
  }

  /**
   * Converts the table row to HTML.
   * 
   * @return HTML
   */
  private String toHtmlString()
  {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("\t<tr>" + NEWLINE);
    for (final TableCell cell: cells)
    {
      buffer.append("\t\t").append(cell).append(NEWLINE);
    }
    buffer.append("\t</tr>");

    return buffer.toString();
  }

  /**
   * Converts the table row to CSV.
   * 
   * @return CSV
   */
  private String toPlainTextString()
  {
    final StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < cells.size(); i++)
    {
      final TableCell cell = cells.get(i);
      if (i > 0)
      {
        buffer.append(getFieldSeparator());
      }
      buffer.append(cell.toString());
    }

    return buffer.toString();
  }

}

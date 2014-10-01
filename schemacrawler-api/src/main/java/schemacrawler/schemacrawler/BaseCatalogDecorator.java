/*
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
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
package schemacrawler.schemacrawler;


import java.util.Collection;
import java.util.Map;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.ColumnDataType;
import schemacrawler.schema.DatabaseInfo;
import schemacrawler.schema.JdbcDriverInfo;
import schemacrawler.schema.NamedObject;
import schemacrawler.schema.Routine;
import schemacrawler.schema.Schema;
import schemacrawler.schema.SchemaCrawlerInfo;
import schemacrawler.schema.Sequence;
import schemacrawler.schema.Synonym;
import schemacrawler.schema.Table;

public abstract class BaseCatalogDecorator
  implements Catalog
{

  private static final long serialVersionUID = -3953296149824921463L;

  protected final Catalog catalog;

  public BaseCatalogDecorator(final Catalog catalog)
  {
    if (catalog == null)
    {
      throw new IllegalArgumentException("No catalog provided");
    }
    this.catalog = catalog;
  }

  @Override
  public int compareTo(final NamedObject o)
  {
    return catalog.compareTo(o);
  }

  @Override
  public Object getAttribute(final String name)
  {
    return catalog.getAttribute(name);
  }

  @Override
  public <T> T getAttribute(final String name, final T defaultValue)
  {
    return catalog.getAttribute(name, defaultValue);
  }

  @Override
  public Map<String, Object> getAttributes()
  {
    return catalog.getAttributes();
  }

  @Override
  public ColumnDataType getColumnDataType(final Schema schema, final String name)
  {
    return catalog.getColumnDataType(schema, name);
  }

  @Override
  public Collection<ColumnDataType> getColumnDataTypes()
  {
    return catalog.getColumnDataTypes();
  }

  @Override
  public Collection<ColumnDataType> getColumnDataTypes(final Schema schema)
  {
    return catalog.getColumnDataTypes(schema);
  }

  @Override
  public DatabaseInfo getDatabaseInfo()
  {
    return catalog.getDatabaseInfo();
  }

  @Override
  public String getFullName()
  {
    return catalog.getFullName();
  }

  @Override
  public JdbcDriverInfo getJdbcDriverInfo()
  {
    return catalog.getJdbcDriverInfo();
  }

  @Override
  public String getLookupKey()
  {
    return getFullName();
  }

  @Override
  public String getName()
  {
    return catalog.getName();
  }

  @Override
  public String getRemarks()
  {
    return catalog.getRemarks();
  }

  @Override
  public Routine getRoutine(final Schema schema, final String name)
  {
    return catalog.getRoutine(schema, name);
  }

  @Override
  public Collection<Routine> getRoutines()
  {
    return catalog.getRoutines();
  }

  @Override
  public Collection<Routine> getRoutines(final Schema schema)
  {
    return catalog.getRoutines(schema);
  }

  @Override
  public Schema getSchema(final String name)
  {
    return catalog.getSchema(name);
  }

  @Override
  public SchemaCrawlerInfo getSchemaCrawlerInfo()
  {
    return catalog.getSchemaCrawlerInfo();
  }

  @Override
  public Collection<Schema> getSchemas()
  {
    return catalog.getSchemas();
  }

  /**
   * @param schema
   * @param name
   * @return
   * @see schemacrawler.schema.Catalog#getSequence(schemacrawler.schema.Schema,
   *      java.lang.String)
   */
  @Override
  public Sequence getSequence(final Schema schema, final String name)
  {
    return catalog.getSequence(schema, name);
  }

  /**
   * @return
   * @see schemacrawler.schema.Catalog#getSequences()
   */
  @Override
  public Collection<Sequence> getSequences()
  {
    return catalog.getSequences();
  }

  /**
   * @param schema
   * @return
   * @see schemacrawler.schema.Catalog#getSequences(schemacrawler.schema.Schema)
   */
  @Override
  public Collection<Sequence> getSequences(final Schema schema)
  {
    return catalog.getSequences(schema);
  }

  @Override
  public Synonym getSynonym(final Schema schema, final String name)
  {
    return catalog.getSynonym(schema, name);
  }

  @Override
  public Collection<Synonym> getSynonyms()
  {
    return catalog.getSynonyms();
  }

  @Override
  public Collection<Synonym> getSynonyms(final Schema schema)
  {
    return catalog.getSynonyms(schema);
  }

  @Override
  public ColumnDataType getSystemColumnDataType(final String name)
  {
    return catalog.getSystemColumnDataType(name);
  }

  @Override
  public Collection<ColumnDataType> getSystemColumnDataTypes()
  {
    return catalog.getSystemColumnDataTypes();
  }

  @Override
  public Table getTable(final Schema schema, final String name)
  {
    return catalog.getTable(schema, name);
  }

  @Override
  public Collection<Table> getTables()
  {
    return catalog.getTables();
  }

  @Override
  public Collection<Table> getTables(final Schema schema)
  {
    return catalog.getTables(schema);
  }

  @Override
  public boolean hasRemarks()
  {
    return catalog.hasRemarks();
  }

  @Override
  public void setAttribute(final String name, final Object value)
  {
    catalog.setAttribute(name, value);
  }

}
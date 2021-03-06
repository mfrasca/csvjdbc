/**
 *  CsvJdbc - a JDBC driver for CSV files
 *  Copyright (C) 2008  Mario Frasca
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.relique.jdbc.csv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParsedExpression extends LogicalExpression
{
	public Expression content;
	private Map<String, Object> placeholders;
	public ParsedExpression(Expression left)
	{
		content = left;
		placeholders = new HashMap<String, Object>();
	}
	public boolean isTrue(Map<String, Object> env)
	{
		if(placeholders != null)
		{
			Map<String, Object> useThisEnv = new HashMap<String, Object>();
			useThisEnv.putAll(env);
			useThisEnv.putAll(placeholders);
			env = useThisEnv;
		}
		return ((LogicalExpression)content).isTrue(env);
	}
	public Object eval(Map<String, Object> env)
	{
		if(placeholders != null)
		{
			Map<String, Object> useThisEnv = new HashMap<String, Object>();
			useThisEnv.putAll(env);
			useThisEnv.putAll(placeholders);
			env = useThisEnv;
		}
		return content.eval(env);
	}
	public String toString()
	{
		return content.toString();
	}
	public List<String> usedColumns()
	{
		return content.usedColumns();
	}
	public List<AggregateFunction> aggregateFunctions()
	{
		return content.aggregateFunctions();
	}
	public int getPlaceholdersCount()
	{
		return Placeholder.nextIndex - 1;
	}
	public void setPlaceholdersValues(Object[] values)
	{
		for(int i=1; i<values.length; i++)
		{
			placeholders.put("?" + i, values[i]);
		}
	}
}

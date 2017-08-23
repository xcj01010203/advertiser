package com.xiaotu.common.db.impl;

import com.xiaotu.common.db.IDialect;

/**
 * @类名 MySQLDialect
 * @日期 2015年6月25日
 * @作者 高海军
 * @功能 PostgreSQL数据库方言
 */
public class PostgreSQLDialect implements IDialect
{
	
	@Override
	public String getLimitSQL(String sql, int offset, int limit)
	{
		
		sql = sql.trim();
		StringBuffer buf = new StringBuffer(sql.length() + 20).append(sql);
		
		if (offset > 0)
		{
			buf.append(" LIMIT ").append(limit).append(" OFFSET ")
					.append(offset);
		}
		else
		{
			buf.append(" LIMIT ").append(limit);
		}
		
		return buf.toString();
	}
	
}
/**
 * 
 */
package com.ece651.toolsUnits;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: RowMapper.java
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月9日 下午8:52:39
 */
public interface RowMapper {
	
	public Object mapRow(ResultSet rs, int index) throws SQLException;
	
}

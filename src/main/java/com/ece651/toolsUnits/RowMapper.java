/**
 * 
 */
package com.ece651.toolsUnits;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: RowMapper.java
 * @Description: 配合Dao底层的批量JDBC操作接口
 * 
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.9  8:52:39 PM
 */
public interface RowMapper {
	
	public Object mapRow(ResultSet rs, int index) throws SQLException;
	
}

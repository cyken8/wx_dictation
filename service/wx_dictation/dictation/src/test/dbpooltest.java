package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import indi.cyken.utils.JDBCUtils;


public class dbpooltest {
	public static void main(String[] args) {
		select();
	
	}
	
	//定义2个方法,实现数据表的添加,数据表查询
	//QueryRunner类对象,写在类成员位置
	private static QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource()); 
	
	//数据表查询
	public static void select(){
		String sql = "SELECT * FROM T_BOOK";
		try{
		List<Object[]> list = qr.query(sql, new ArrayListHandler());
		for(Object[] objs : list){
			for(Object obj : objs){
				System.out.print(obj+"\t");
			}
			System.out.println();
		}
		}catch(SQLException ex){
			throw new RuntimeException("数据查询失败");
		}
	}
}

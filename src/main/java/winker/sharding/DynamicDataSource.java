package winker.sharding;

import java.sql.Connection;
import java.sql.SQLException;

import javax.activation.DataSource;

import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.WinkerDataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;

import winker.sharding.Routing.ShardingHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

	// 数据库名称 前缀 系统会根据路由规则生产后缀
	private String dbName;

    int a =0;
	// 表的总数量
	public static Integer tableAmount = 2;
	// 数据库数量
	public static Integer dbAmount = 2;

	@Override
	protected Object determineCurrentLookupKey() {
//		 DataSourceUtils.doGetConnection(dataSource)
//		dbNumber.set(1);
//		return dbName + dbNumber.get();
		a++;
		System.out.println(this.getDbName()+0);
//		System.out.println("winkerDataSource"+a);
		if(ShardingHolder.dbNumber.get() == null){
			return "";
		}else{
			return this.getDbName()+ShardingHolder.dbNumber.get();
		}
		
		
		

	}

	@Override
	public Connection getConnection() throws SQLException {
	   javax.sql.DataSource targetDataSource = determineTargetDataSource();
 	   Connection conn = targetDataSource.getConnection();
 	   WinkerDataSourceTransactionManager.setConnectionHolder(targetDataSource, conn);
		return conn;
	}
	

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public void afterPropertiesSet() {
		 if(dbName==null){
			 throw new RuntimeException("dbName must not empty!");
		 }
		super.afterPropertiesSet();
	}
}
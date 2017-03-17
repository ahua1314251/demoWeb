package winker.sharding;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	// 数据库名称 前缀 系统会根据路由规则生产后缀
	private String dbName;


	// 表的总数量
	public static Integer tableAmount = 2;
	// 数据库数量
	public static Integer dbAmount = 2;

	@Override
	protected Object determineCurrentLookupKey() {
//		 DataSourceUtils.doGetConnection(dataSource)
//		dbNumber.set(1);
//		return dbName + dbNumber.get();
		System.out.println(this.getDbName()+);
		return "winkerDataSource2";
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
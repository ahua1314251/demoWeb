package winker.sharding;

/**
 * 
 * @author tom.liu
 *
 */
public class RouterBean {

  private  String dataSourceName; //暂时不用 保留字段
  
  private  String tableName;  //保留字段 暂时不用
  
  private  Integer DbNumber; 
  
  private  Integer tableNumber; 

  private  Object Param; 
  
public String getDataSourceName() {
	return dataSourceName;
}

public void setDataSourceName(String dataSourceName) {
	this.dataSourceName = dataSourceName;
}

public String getTableName() {
	return tableName;
}

public void setTableName(String tableName) {
	this.tableName = tableName;
}

public Integer getDbNumber() {
	return DbNumber;
}

public void setDbNumber(Integer dbNumber) {
	DbNumber = dbNumber;
}

public Integer getTableNumber() {
	return tableNumber;
}

public void setTableNumber(Integer tableNumber) {
	this.tableNumber = tableNumber;
}
  
  
  
}

package winker.sharding;

public class BaseShardingParam {
	private  Integer DbNumber; 
	  
	private  Integer tableNumber;

	private Long id;
	
	private Long xid;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getXid() {
		return xid;
	}

	public void setXid(Long xid) {
		this.xid = xid;
	} 
	  
	  
}

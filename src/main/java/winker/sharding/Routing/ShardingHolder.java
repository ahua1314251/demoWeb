package winker.sharding.Routing;

public class ShardingHolder {

	
	// 线程本地环境 传送变量
	private static final ThreadLocal<Object> paramObj = new ThreadLocal<Object>();

	// 线程本地环境 传送变量
	public static final ThreadLocal<Integer> dbNumber = new ThreadLocal<Integer>();
	
}

package wingsoft.tool.db;

import java.util.HashMap;

public class ConnectionPoolManager {
	protected static HashMap<String,ConnectionPool> pools =new HashMap<String, ConnectionPool>();
	public static void addPool(String poolName,ConnectionPool pool){
		pools.put(poolName, pool);
	}
	public static ConnectionPool getPool(String poolName){
		ConnectionPool pool = new ConnectionPool(poolName);
		return pool;
	}
}

package org.springframework.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.util.Assert;

public class WinkerConnectionHolder extends  ResourceHolderSupport{

	private TransactionDefinition definition;
	
	private WinkerDataSourceTransactionManager.DataSourceTransactionObject txObject;
	
	private  boolean isBegin = false;
	
	public static final String SAVEPOINT_NAME_PREFIX = "SAVEPOINT_";

	private Connection currentConnection;
	
	private Map<DataSource,Connection> connectionMap =  new HashMap<DataSource, Connection>();
	
	private boolean transactionActive = false;

	private Boolean savepointsSupported;

	private int savepointCounter = 0;

	
	public WinkerConnectionHolder() {
	}
	
	public WinkerConnectionHolder(WinkerDataSourceTransactionManager.DataSourceTransactionObject txObject ,TransactionDefinition definition) {
           this.txObject = txObject;
           this.definition = definition;
	}

	public WinkerConnectionHolder(Connection con,DataSource targetDataSource) {
           this.setConnection(con,targetDataSource);
	}

	/**
	 * Return whether this holder currently has a Connection.
	 */
	protected boolean hasConnection() {
		return (this.connectionMap.get(WinkerDataSourceUtils.getCurrentDataSource().get()) != null);
	}
	
//	protected boolean hasConnection(DataSource targetDataSource) {
//		return (this.currentConnection != null);
//	}

	/**
	 * Set whether this holder represents an active, JDBC-managed transaction.
	 * @see DataSourceTransactionManager
	 */
	protected void setTransactionActive(boolean transactionActive) {
		this.transactionActive = transactionActive;
	}

	/**
	 * Return whether this holder represents an active, JDBC-managed transaction.
	 */
	protected boolean isTransactionActive() {
		return this.transactionActive;
	}


	/**
	 * Override the existing Connection handle with the given Connection.
	 * Reset the handle if given {@code null}.
	 * <p>Used for releasing the Connection on suspend (with a {@code null}
	 * argument) and setting a fresh Connection on resume.
	 */
	protected void setConnection(Connection connection,DataSource targetDataSource) {
		Assert.notNull(connection, "Connection must not be null");
		Assert.notNull(targetDataSource, "Connection must not be null");
		if(isSynchronizedWithTransaction()&&isTransactionActive()){
			try {
				doBegin(connection) ;
			} catch (SQLException e) {
			  throw new RuntimeException("========as==asd=asd=");
			}
		}
		if (this.currentConnection != null) {
			this.currentConnection = null;
		}
		this.currentConnection  = connection;
		this.connectionMap.put(targetDataSource, connection);
	}

	/**
	 * Return the current Connection held by this ConnectionHolder.
	 * <p>This will be the same Connection until {@code released}
	 * gets called on the ConnectionHolder, which will reset the
	 * held Connection, fetching a new Connection on demand.
	 * @see ConnectionHandle#getConnection()
	 * @see #released()
	 */
//	public Connection getConnection() {
//		return this.connectionMap.get(WinkerDataSourceUtils.getCurrentDataSource().get());
//	}

	/**
	 * Return whether JDBC 3.0 Savepoints are supported.
	 * Caches the flag for the lifetime of this ConnectionHolder.
	 * @throws SQLException if thrown by the JDBC driver
	 */
	public boolean supportsSavepoints() throws SQLException {
		if (this.savepointsSupported == null) {
			this.savepointsSupported = new Boolean(getConnection().getMetaData().supportsSavepoints());
		}
		return this.savepointsSupported.booleanValue();
	}

	/**
	 * Create a new JDBC 3.0 Savepoint for the current Connection,
	 * using generated savepoint names that are unique for the Connection.
	 * @return the new Savepoint
	 * @throws SQLException if thrown by the JDBC driver
	 */
	public Savepoint createSavepoint() throws SQLException {
		this.savepointCounter++;
		return getConnection().setSavepoint(SAVEPOINT_NAME_PREFIX + this.savepointCounter);
	}

	/**
	 * Releases the current Connection held by this ConnectionHolder.
	 * <p>This is necessary for ConnectionHandles that expect "Connection borrowing",
	 * where each returned Connection is only temporarily leased and needs to be
	 * returned once the data operation is done, to make the Connection available
	 * for other operations within the same transaction. This is the case with
	 * JDO 2.0 DataStoreConnections, for example.
	 * @see org.springframework.orm.jdo.DefaultJdoDialect#getJdbcConnection
	 */
	@Override
	public void released() {
		super.released();

	}
	
	

	public void close() {
		super.released();
		if (!isOpen() && this.currentConnection != null) {
			this.currentConnection = null;
		}
		txObject =null;
		definition = null;
	}


	public void doBegin(Connection conn) throws SQLException {

		Integer previousIsolationLevel = WinkerDataSourceUtils.prepareConnectionForTransaction(conn, definition);
		txObject.setPreviousIsolationLevel(previousIsolationLevel);

		// Switch to manual commit if necessary. This is very expensive in some JDBC drivers,
		// so we don't want to do it unnecessarily (for example if we've explicitly
		// configured the connection pool to set it already).
		if (conn.getAutoCommit()) {
			txObject.setMustRestoreAutoCommit(true);
//			if (logger.isDebugEnabled()) {
//				logger.debug("Switching JDBC Connection [" + currentConnection + "] to manual commit");
//			}
			conn.setAutoCommit(false);
		}

        isBegin = false;

	}

	
	
	
	
	@Override
	public void clear() {
		super.clear();
		this.transactionActive = false;
		this.savepointsSupported = null;
		this.savepointCounter = 0;
	}

	public  boolean isBegin() {
		return isBegin;
	}

	public  void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}

	public Map<DataSource, Connection> getConnectionMap() {
		return connectionMap;
	}

	
	

}

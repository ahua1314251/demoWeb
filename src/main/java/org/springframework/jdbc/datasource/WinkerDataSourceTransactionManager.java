/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package  org.springframework.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.NestedTransactionNotSupportedException;
import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * {@link org.springframework.transaction.PlatformTransactionManager}
 * implementation for a single JDBC {@link javax.sql.DataSource}. This class is
 * capable of working in any environment with any JDBC driver, as long as the setup
 * uses a {@code javax.sql.DataSource} as its {@code Connection} factory mechanism.
 * Binds a JDBC Connection from the specified DataSource to the current thread,
 * potentially allowing for one thread-bound Connection per DataSource.
 *
 * <p><b>Note: The DataSource that this transaction manager operates on needs
 * to return independent Connections.</b> The Connections may come from a pool
 * (the typical case), but the DataSource must not return thread-scoped /
 * request-scoped Connections or the like. This transaction manager will
 * associate Connections with thread-bound transactions itself, according
 * to the specified propagation behavior. It assumes that a separate,
 * independent Connection can be obtained even during an ongoing transaction.
 *
 * <p>Application code is required to retrieve the JDBC Connection via
 * {@link DataSourceUtils#getConnection(DataSource)} instead of a standard
 * J2EE-style {@link DataSource#getConnection()} call. Spring classes such as
 * {@link org.springframework.jdbc.core.JdbcTemplate} use this strategy implicitly.
 * If not used in combination with this transaction manager, the
 * {@link DataSourceUtils} lookup strategy behaves exactly like the native
 * DataSource lookup; it can thus be used in a portable fashion.
 *
 * <p>Alternatively, you can allow application code to work with the standard
 * J2EE-style lookup pattern {@link DataSource#getConnection()}, for example for
 * legacy code that is not aware of Spring at all. In that case, define a
 * {@link TransactionAwareDataSourceProxy} for your target DataSource, and pass
 * that proxy DataSource to your DAOs, which will automatically participate in
 * Spring-managed transactions when accessing it.
 *
 * <p>Supports custom isolation levels, and timeouts which get applied as
 * appropriate JDBC statement timeouts. To support the latter, application code
 * must either use {@link org.springframework.jdbc.core.JdbcTemplate}, call
 * {@link DataSourceUtils#applyTransactionTimeout} for each created JDBC Statement,
 * or go through a {@link TransactionAwareDataSourceProxy} which will create
 * timeout-aware JDBC Connections and Statements automatically.
 *
 * <p>Consider defining a {@link LazyConnectionDataSourceProxy} for your target
 * DataSource, pointing both this transaction manager and your DAOs to it.
 * This will lead to optimized handling of "empty" transactions, i.e. of transactions
 * without any JDBC statements executed. A LazyConnectionDataSourceProxy will not fetch
 * an actual JDBC Connection from the target DataSource until a Statement gets executed,
 * lazily applying the specified transaction settings to the target Connection.
 *
 * <p>This transaction manager supports nested transactions via the JDBC 3.0
 * {@link java.sql.Savepoint} mechanism. The
 * {@link #setNestedTransactionAllowed "nestedTransactionAllowed"} flag defaults
 * to "true", since nested transactions will work without restrictions on JDBC
 * drivers that support savepoints (such as the Oracle JDBC driver).
 *
 * <p>This transaction manager can be used as a replacement for the
 * {@link org.springframework.transaction.jta.JtaTransactionManager} in the single
 * resource case, as it does not require a container that supports JTA, typically
 * in combination with a locally defined JDBC DataSource (e.g. an Apache Commons
 * DBCP connection pool). Switching between this local strategy and a JTA
 * environment is just a matter of configuration!
 *
 * @author Juergen Hoeller
 * @since 02.05.2003
 * @see #setNestedTransactionAllowed
 * @see java.sql.Savepoint
 * @see DataSourceUtils#getConnection(javax.sql.DataSource)
 * @see DataSourceUtils#applyTransactionTimeout
 * @see DataSourceUtils#releaseConnection
 * @see TransactionAwareDataSourceProxy
 * @see LazyConnectionDataSourceProxy
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
@SuppressWarnings("serial")
public class WinkerDataSourceTransactionManager  extends AbstractPlatformTransactionManager
implements ResourceTransactionManager, InitializingBean {

	private DataSource dataSource;


	/**
	 * Create a new DataSourceTransactionManager instance.
	 * A DataSource has to be set to be able to use it.
	 * @see #setDataSource
	 */
	public WinkerDataSourceTransactionManager() {
		setNestedTransactionAllowed(true);
	}

	/**
	 * Create a new DataSourceTransactionManager instance.
	 * @param dataSource JDBC DataSource to manage transactions for
	 */
	public WinkerDataSourceTransactionManager(DataSource dataSource) {
		this();
		setDataSource(dataSource);
		afterPropertiesSet();
	}

	/**
	 * Set the JDBC DataSource that this instance should manage transactions for.
	 * <p>This will typically be a locally defined DataSource, for example an
	 * Apache Commons DBCP connection pool. Alternatively, you can also drive
	 * transactions for a non-XA J2EE DataSource fetched from JNDI. For an XA
	 * DataSource, use JtaTransactionManager.
	 * <p>The DataSource specified here should be the target DataSource to manage
	 * transactions for, not a TransactionAwareDataSourceProxy. Only data access
	 * code may work with TransactionAwareDataSourceProxy, while the transaction
	 * manager needs to work on the underlying target DataSource. If there's
	 * nevertheless a TransactionAwareDataSourceProxy passed in, it will be
	 * unwrapped to extract its target DataSource.
	 * <p><b>The DataSource passed in here needs to return independent Connections.</b>
	 * The Connections may come from a pool (the typical case), but the DataSource
	 * must not return thread-scoped / request-scoped Connections or the like.
	 * @see TransactionAwareDataSourceProxy
	 * @see org.springframework.transaction.jta.JtaTransactionManager
	 */
	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			// If we got a TransactionAwareDataSourceProxy, we need to perform transactions
			// for its underlying target DataSource, else data access code won't see
			// properly exposed transactions (i.e. transactions for the target DataSource).
			this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
		}
		else {
			this.dataSource = dataSource;
		}
	}

	/**
	 * Return the JDBC DataSource that this instance manages transactions for.
	 */
	public DataSource getDataSource() {
		return this.dataSource;
	}

	@Override
	public void afterPropertiesSet() {
		if (getDataSource() == null) {
			throw new IllegalArgumentException("Property 'dataSource' is required");
		}
	}


	@Override
	public Object getResourceFactory() {
		return getDataSource();
	}

	@Override
	protected Object doGetTransaction() {
		DataSourceTransactionObject txObject = new DataSourceTransactionObject();
		txObject.setSavepointAllowed(isNestedTransactionAllowed());
		WinkerConnectionHolder conHolder =
				(WinkerConnectionHolder) TransactionSynchronizationManager.getResource(this.dataSource);
		txObject.setWinkerConnectionHolder(conHolder, false);
		return txObject;
	}

	@Override
	protected boolean isExistingTransaction(Object transaction) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
		return (txObject.getWinkerConnectionHolder() != null && txObject.getWinkerConnectionHolder().isTransactionActive());
	}

	/**
	 * This implementation sets the isolation level but ignores the timeout.
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
		Connection con = null;

		try {
			if (txObject.getWinkerConnectionHolder() == null ||
					txObject.getWinkerConnectionHolder().isSynchronizedWithTransaction()) {
				txObject.setWinkerConnectionHolder(new WinkerConnectionHolder(txObject,definition), true);
			}

			txObject.getWinkerConnectionHolder().setSynchronizedWithTransaction(true);
			txObject.getWinkerConnectionHolder().setBegin(true);
			
			// Bind the session holder to the thread.
			if (txObject.isNewConnectionHolder()) {
				TransactionSynchronizationManager.bindResource(getDataSource(), txObject.getWinkerConnectionHolder());
			}
			
			txObject.getWinkerConnectionHolder().setTransactionActive(true);

			int timeout = determineTimeout(definition);
			if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
				txObject.getWinkerConnectionHolder().setTimeoutInSeconds(timeout);
			}
			
		}catch (Throwable ex) {
			if (txObject.isNewConnectionHolder()) {
				DataSourceUtils.releaseConnection(con, this.dataSource);
				txObject.setWinkerConnectionHolder(null, false);
			}
			throw new CannotCreateTransactionException("Could not open JDBC Connection for transaction", ex);
		}
	}

	@Override
	protected Object doSuspend(Object transaction) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
		txObject.setConnectionHolder(null);
		WinkerConnectionHolder conHolder = (WinkerConnectionHolder)
				TransactionSynchronizationManager.unbindResource(this.dataSource);
		return conHolder;
	}

	@Override
	protected void doResume(Object transaction, Object suspendedResources) {
		WinkerConnectionHolder conHolder = (WinkerConnectionHolder) suspendedResources;
		TransactionSynchronizationManager.bindResource(this.dataSource, conHolder);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
		Map<DataSource, Connection>  conMap = txObject.getWinkerConnectionHolder().getConnectionMap();
		if (status.isDebug()) {
			logger.debug("Committing JDBC transaction on Connection [" + conMap + "]");
		}
		try {
			for(Connection con : conMap.values()){
				con.commit();
			}
		}
		catch (SQLException ex) {
			throw new TransactionSystemException("Could not commit JDBC transaction", ex);
		}
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
		Map<DataSource,Connection> conMap = txObject.getWinkerConnectionHolder().getConnectionMap();
		if (status.isDebug()) {
			logger.debug("Rolling back JDBC transaction on Connection [" + conMap + "]");
		}
		try {
			for(Connection con : conMap.values()){
			con.rollback();
			}
		}
		catch (SQLException ex) {
			throw new TransactionSystemException("Could not roll back JDBC transaction", ex);
		}
	}

	@Override
	protected void doSetRollbackOnly(DefaultTransactionStatus status) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
		if (status.isDebug()) {
			logger.debug("Setting JDBC transaction [" + txObject.getWinkerConnectionHolder().getConnectionMap() +
					"] rollback-only");
		}
		txObject.setRollbackOnly();
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;

		// Remove the connection holder from the thread, if exposed.
		if (txObject.isNewConnectionHolder()) {
			TransactionSynchronizationManager.unbindResource(this.dataSource);
		}

		// Reset connection.
		Map<DataSource,Connection> conMap = txObject.getWinkerConnectionHolder().getConnectionMap();
		try {
			for(Connection con : conMap.values()){
			if (txObject.isMustRestoreAutoCommit()) {
				con.setAutoCommit(true);
			}
			DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
			}
		}
		catch (Throwable ex) {
			logger.debug("Could not reset JDBC Connection after transaction", ex);
		}

		if (txObject.isNewConnectionHolder()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Releasing JDBC Connection [" + conMap + "] after transaction");
			}
			for(Connection con : conMap.values()){
			DataSourceUtils.releaseConnection(con, this.dataSource);
			}
		}

		txObject.getWinkerConnectionHolder().clear();
	}


	/**
	 * DataSource transaction object, representing a WinkerConnectionHolder.
	 * Used as transaction object by DataSourceTransactionManager.
	 */
	static class DataSourceTransactionObject implements SavepointManager, SmartTransactionObject {

		
		private boolean newConnectionHolder;

		private boolean mustRestoreAutoCommit;

		private static final Log logger = LogFactory.getLog(JdbcTransactionObjectSupport.class);




		private WinkerConnectionHolder connectionHolder;

		private Integer previousIsolationLevel;

		private boolean savepointAllowed = false;


		public void setConnectionHolder(WinkerConnectionHolder connectionHolder) {
			this.connectionHolder = connectionHolder;
		}

		public WinkerConnectionHolder getConnectionHolder() {
			return this.connectionHolder;
		}

		public WinkerConnectionHolder getWinkerConnectionHolder() {
			return this.connectionHolder;
		}
		
		
		public boolean hasConnectionHolder() {
			return (this.connectionHolder != null);
		}

		public void setPreviousIsolationLevel(Integer previousIsolationLevel) {
			this.previousIsolationLevel = previousIsolationLevel;
		}

		public Integer getPreviousIsolationLevel() {
			return this.previousIsolationLevel;
		}

		public void setSavepointAllowed(boolean savepointAllowed) {
			this.savepointAllowed = savepointAllowed;
		}

		public boolean isSavepointAllowed() {
			return this.savepointAllowed;
		}

		@Override
		public void flush() {
			// no-op
		}


		//---------------------------------------------------------------------
		// Implementation of SavepointManager
		//---------------------------------------------------------------------

		/**
		 * This implementation creates a JDBC 3.0 Savepoint and returns it.
		 * @see java.sql.Connection#setSavepoint
		 */
		@Override
		public Object createSavepoint() throws TransactionException {
			WinkerConnectionHolder conHolder = getConnectionHolderForSavepoint();
			try {
				if (!conHolder.supportsSavepoints()) {
					throw new NestedTransactionNotSupportedException(
							"Cannot create a nested transaction because savepoints are not supported by your JDBC driver");
				}
				return conHolder.createSavepoint();
			}
			catch (SQLException ex) {
				throw new CannotCreateTransactionException("Could not create JDBC savepoint", ex);
			}
		}

		/**
		 * This implementation rolls back to the given JDBC 3.0 Savepoint.
		 * @see java.sql.Connection#rollback(java.sql.Savepoint)
		 */
		@Override
		public void rollbackToSavepoint(Object savepoint) throws TransactionException {
			WinkerConnectionHolder conHolder = getConnectionHolderForSavepoint();
			try {
				Map<DataSource,Connection> conMap = conHolder.getConnectionMap();
				for(Connection con : conMap.values()){
					con.rollback((Savepoint) savepoint);
				}
			}
			catch (Throwable ex) {
				throw new TransactionSystemException("Could not roll back to JDBC savepoint", ex);
			}
		}

		/**
		 * This implementation releases the given JDBC 3.0 Savepoint.
		 * @see java.sql.Connection#releaseSavepoint
		 */
		@Override
		public void releaseSavepoint(Object savepoint) throws TransactionException {
			WinkerConnectionHolder conHolder = getConnectionHolderForSavepoint();
			try {
				Map<DataSource,Connection> conMap = conHolder.getConnectionMap();
				for(Connection con : conMap.values()){
					con.releaseSavepoint((Savepoint) savepoint);
				}
				
			}
			catch (Throwable ex) {
				logger.debug("Could not explicitly release JDBC savepoint", ex);
			}
		}

		protected WinkerConnectionHolder getConnectionHolderForSavepoint() throws TransactionException {
			if (!isSavepointAllowed()) {
				throw new NestedTransactionNotSupportedException(
						"Transaction manager does not allow nested transactions");
			}
			if (!hasConnectionHolder()) {
				throw new TransactionUsageException(
						"Cannot create nested transaction if not exposing a JDBC transaction");
			}
			return getConnectionHolder();
		}

		public void setConnectionHolder(WinkerConnectionHolder connectionHolder, boolean newConnectionHolder) {
			this.setConnectionHolder(connectionHolder);
			this.newConnectionHolder = newConnectionHolder;
		}

		public void setWinkerConnectionHolder(WinkerConnectionHolder connectionHolder, boolean newConnectionHolder) {
			this.setConnectionHolder(connectionHolder);
			this.newConnectionHolder = newConnectionHolder;
		}
		
		public boolean isNewConnectionHolder() {
			return this.newConnectionHolder;
		}

		public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
			this.mustRestoreAutoCommit = mustRestoreAutoCommit;
		}

		public boolean isMustRestoreAutoCommit() {
			return this.mustRestoreAutoCommit;
		}

		public void setRollbackOnly() {
			getConnectionHolder().setRollbackOnly();
		}

		@Override
		public boolean isRollbackOnly() {
			return getConnectionHolder().isRollbackOnly();
		}
	}


}

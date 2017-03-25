package org.springframework.jdbc.datasource;

import static org.springframework.util.Assert.notNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransaction;

public class WinkerSpringManagedTransaction implements Transaction {
	 private static final Log LOGGER = LogFactory.getLog(SpringManagedTransaction.class);

	  private DataSource dataSource;

	  private Connection connection;

	  private boolean isConnectionTransactional;

	  private boolean autoCommit;

	  public WinkerSpringManagedTransaction(DataSource dataSource) {
	    notNull(dataSource, "No DataSource specified");
	    this.dataSource = dataSource;
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public Connection getConnection() throws SQLException {
	      openConnection();
	      

	      
	    return this.connection;
	  }

	  /**
	   * Gets a connection from Spring transaction manager and discovers if this
	   * {@code Transaction} should manage connection or let it to Spring.
	   * <p>
	   * It also reads autocommit setting because when using Spring Transaction MyBatis
	   * thinks that autocommit is always false and will always call commit/rollback
	   * so we need to no-op that calls.
	   */
	  private void openConnection() throws SQLException {
//		  DynamicDataSource DdataSource = null;
//		if(this.dataSource instanceof DynamicDataSource){
//			  DdataSource = (DynamicDataSource) this.dataSource;
//			  this.connection = WinkerDataSourceUtils.getConnection(this.dataSource);
////		      WinkerDataSourceTransactionManager.setConnectionHolder(dataSource, connection);
////		      WinkerDataSourceTransactionManager.setConnectionHolder(DdataSource.getTargetDataSource(), connection);
//		}else{
//
//	    this.connection = WinkerDataSourceUtils.getConnection(DdataSource.getTargetDataSource());
//		}
	    this.connection = WinkerDataSourceUtils.getConnection(this.dataSource);
	    this.autoCommit = this.connection.getAutoCommit();
	    this.isConnectionTransactional = WinkerDataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);

	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug(
	          "JDBC Connection ["
	              + this.connection
	              + "] will"
	              + (this.isConnectionTransactional ? " " : " not ")
	              + "be managed by Spring");
	    }
	    
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public void commit() throws SQLException {
	    if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
	      if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("Committing JDBC Connection [" + this.connection + "]");
	      }
	      this.connection.commit();
	    }
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public void rollback() throws SQLException {
	    if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
	      if (LOGGER.isDebugEnabled()) {
	        LOGGER.debug("Rolling back JDBC Connection [" + this.connection + "]");
	      }
	      this.connection.rollback();
	    }
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public void close() throws SQLException {
	    WinkerDataSourceUtils.releaseConnection(this.connection, this.dataSource);
	  }


}

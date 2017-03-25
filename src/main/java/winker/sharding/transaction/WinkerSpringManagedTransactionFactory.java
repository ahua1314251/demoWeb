package winker.sharding.transaction;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.jdbc.datasource.WinkerSpringManagedTransaction;

public class WinkerSpringManagedTransactionFactory extends SpringManagedTransactionFactory {

	  @Override
	  public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
	    return new WinkerSpringManagedTransaction(dataSource);
	  }
}


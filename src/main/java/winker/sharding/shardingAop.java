package winker.sharding;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import winker.sharding.Routing.ShardingHolder;
@Order(1)
@Aspect
@Component
public class shardingAop {
	 @Order(1)
	 @Pointcut("execution(* winker.dal.mapper.*.*(..))")  
	    private void anyMethod(){}//定义一个切入点
	 
	 @Order(1)
	 @Before("anyMethod()&& args(..)")  
	    public void doAccessCheck(JoinPoint obj){  
            
		 for(Object param : obj.getArgs()){
			 if(param!=null && (param instanceof BaseShardingParam)){
				 
				 ShardingHolder.dbNumber.set((int) (((BaseShardingParam)param).getId()%2));
				 return ;
			 }
		 }

	     System.out.println("前置通知");  
	    }  
}

package winker.sharding;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import winker.dal.model.WinkerUser;
@Aspect
@Component
public class shardingAop {
	 @Pointcut("execution(* winker.dal.mapper.*.*(..))")  
	    private void anyMethod(){}//定义一个切入点
	 
	 
	 @Before("anyMethod()&& args(obj)")  
	    public void doAccessCheck(WinkerUser obj){  
//		 DynamicDataSource.
		 
		 
		 System.out.println(obj.getAddress());
	        System.out.println("前置通知");  
	    }  
}

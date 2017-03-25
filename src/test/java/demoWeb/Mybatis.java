package demoWeb;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import winker.dal.mapper.WinkerUserMapper;
import winker.dal.model.WinkerUser;
import winker.service.WinkerUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class Mybatis {

	@Autowired
	WinkerUserMapper winkerUserMapper ;
	@Autowired
	WinkerUserService winkerUserService;
	

	@Test
	public  void test() throws Exception{
		WinkerUser record = new WinkerUser();
		record.setId(11L);
		record.setLoginId("12312");
		
		record.setEmail("23123123");
		record.setPassword("123123");
		record.setUserName("shanfu.liu18");
		record.setAddress("qweqw");
		record.setUpdateTime(new Date());
		winkerUserService.updateUser(record,1234);
//		record.setId(12L);
//		winkerUserService.updateUser(record,321);
//		winkerUserService.updateUser(record,1234);

//		record.setId(12L);
//		record.setUpdateTime(new Date());
//		winkerUserService.updateUser(record,5678);
//		throw new Exception("exception");
//		
		
	}
	
	@Test
	public  void asd(){
		Long a =12341231L;
		System.out.println(a.hashCode());
		
		String b="123456789";
		System.out.println(b.hashCode());
	}
	
	
}

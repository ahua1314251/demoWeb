package demoWeb;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	
//	@Transactional(value="winkerTransactionManager")
	@Test
	public  void test(){
		WinkerUser record = new WinkerUser();
		record.setId(13);
		record.setLoginId("12312");
		record.setEmail("23123123");
		record.setPassword("123123");
		record.setUserName("shanfu.liu18");
		record.setAddress("qweqw");
		record.setUpdateTime(new Date());
		winkerUserService.updateUser(record);

	
		
	}
	
	@Test
	public  void asd(){
		Long a =12341231L;
		System.out.println(a.hashCode());
		
		String b="123456789";
		System.out.println(b.hashCode());
	}
	
	
}

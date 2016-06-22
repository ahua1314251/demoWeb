package winker.service;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import winker.dal.criteria.UserCriteria;
import winker.dal.mapper.WinkerUserMapper;
import winker.dal.model.WinkerUser;

@Service
public class UserWinkerService {
	 
	private static final Logger LOGGER  = LogManager.getLogger(UserWinkerService.class);

	@Autowired
	WinkerUserMapper winkerUserMapper;
	public boolean userValidate(WinkerUser user){
		Integer result = winkerUserMapper.validateUserByLoginIdAndPwd(user);
		if(result!=null){
			return true;
		}
		return false;
	}
	
	
	public List<WinkerUser> getUserListByPage(UserCriteria user){
		List<WinkerUser> result = winkerUserMapper.getUserListByPage(user);
		return result;
	}
	
	
	
}

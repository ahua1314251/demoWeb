package winker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import winker.dal.mapper.WinkerUserMapper;
import winker.dal.model.WinkerUser;

@Service
public class WinkerUserService {

	@Autowired
	WinkerUserMapper userMapper;

	public boolean userValidate(WinkerUser user) {
		Integer result = userMapper.validateUserByLoginIdAndPwd(user);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	@Transactional(value="winkerTransactionManager")
	public void updateUser(WinkerUser user,int abcde ) {
		Integer result = userMapper.updateByPrimaryKeySelective(user);
		user.setId(12L);
		 userMapper.updateByPrimaryKeySelective(user);
//		throw new RuntimeException("adas");
	}

}

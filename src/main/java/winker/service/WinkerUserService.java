package winker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

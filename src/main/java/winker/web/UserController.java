package winker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import winker.service.UserWinkerService;

@Component
@RequestMapping("/sysUser")
public class UserController {

	@Autowired
	UserWinkerService userWinkerService;
	@RequestMapping("sysUserList.xhtml")	
	public String userListPage() {
		return "sysUser/sysUserList";
	}
	
	
}

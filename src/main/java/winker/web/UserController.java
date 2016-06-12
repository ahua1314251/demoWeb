package winker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import winker.service.UserWinkerService;

@Component
@RequestMapping("/sysUser")
public class UserController {

	@Autowired
	UserWinkerService userWinkerService;
	
	/**
	* @Athor: tom
	* @Date: 2016年4月4日
	* @Title: userListPage  
	* @Description: TODO
	* @param  
	* @return String    
	* @throws
	 */
	@RequestMapping("sysUserList.xhtml")	
	public String userListPage() {
		return "sysUser/sysUserList";

	}
	
	
	@RequestMapping("search.ajax")	
	@ResponseBody
	public String userSearch() {
		return "sysUser/sysUserList";
	}
	
	
}

package winker.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import winker.core.bean.Result;
import winker.dal.criteria.UserCriteria;
import winker.dal.model.WinkerUser;
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
	public Result<List<WinkerUser>> userSearch(UserCriteria criteria) {
		Result<List<WinkerUser>> result = new Result<List<WinkerUser>>();		
		List<WinkerUser> users = userWinkerService.getUserListByPage(criteria);
		result.setTotal(criteria.getTotalElements());
		result.setRows(users);
		return result;
	}
	        
	@RequestMapping("sysUserAdd.xhtml")	
	public String sysUserAddPage() {
		return "sysUser/sysUserAdd";

	}
}

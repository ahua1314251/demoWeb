package winker.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import winker.dal.model.WinkerUser;
import winker.service.UserWinkerService;

@Controller
@RequestMapping("/")
public class LoginController {
	private static final Logger LOGGER  = LogManager.getLogger(LoginController.class);
	@Autowired
	UserWinkerService userWinkerService;
	@RequestMapping("/")	
	public String index() {
		return "login";
	}
	
	@RequestMapping(value = "/login")
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping(value={"/loginValidate.do"},method = RequestMethod.POST )	
	public String loginValidate(WinkerUser user,RedirectAttributesModelMap mod) {
	//	Constants.DEFAULT_CHARACTER_ENCODING="utf-8";
		LOGGER.info("user----:{}--{}",user.getLoginId(),user.getPassword());
		if(userWinkerService.userValidate(user))
		{
		return "redirect:/sysUser/sysUserList.xhtml";
		}
		mod.addFlashAttribute("message", "用户名密码错误");
		return "redirect:login.xhtml";
	}
}

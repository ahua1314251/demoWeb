package winker.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import resource.org.bean.user.Sys_user;

@Component
@RequestMapping("/")
public class LoginController {

	@RequestMapping("/")	
	public String index() {
		return "redirect:/login.xhtml ";
	}
	
	@RequestMapping(value = "/login")
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping({"/loginValidate.do"})	
	public String loginValidate(Sys_user user,RedirectAttributesModelMap mod,HttpServletRequest xx) {
	//	Constants.DEFAULT_CHARACTER_ENCODING="utf-8";
		if(userService.userValidate(user))
		{
		return "redirect:/material/materialList.xhtml";
		}
		mod.addFlashAttribute("message", "用户名密码错误");
		return "redirect:login.xhtml";
	}
}

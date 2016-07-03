package shopping.web;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class shoppingController {

	
	@RequestMapping("shopping.xhtml")	
	public String userListPage() {
		return "shopping/index";
	}
	
	
	@RequestMapping("contact.xhtml")	
	public String contact() {
		return "shopping/contact";
	}
	
}

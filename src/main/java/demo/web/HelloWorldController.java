package demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/helloWorld")
    @ResponseBody
    public String helloWorld() {
      
        return "helloWorld";
    }
    
    public String helloWorld2(Model model) {
        model.addAttribute("message", "Hello World!");
        return "helloWorld";
    }
}

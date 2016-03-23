package winker.core.interceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import winker.core.interceptor.bean.MenuBean;
import winker.core.interceptor.bean.MenuBeans;


public class SysytemInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
/*    	System.out.println(handler.getClass());
    	System.out.println(request.getRequestURL());
    	System.out.println(request.getRequestURI());
    	System.out.println("---------------------------");*/
        return true;
    }
    
    
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
    		ModelAndView modelAndView) throws Exception {
    	   if(request.getRequestURI().contains("xhtml")||request.getRequestURI().contains("vm"))
    		   
           initMenu(request, response, handler, modelAndView);
    	    	
    	super.postHandle(request, response, handler, modelAndView);
    }
    
    
    private void initMenu(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
    	@SuppressWarnings("unchecked")
		List<MenuBean> menuBeanList = (List<MenuBean>)request.getSession().getAttribute("menuBeanList");
    	if(menuBeanList==null)
    	{
    		menuBeanList = loadMenu();
    		request.getSession().setAttribute("menuBeanList", menuBeanList);
    	}

    	modelAndView.addObject("menuBeanList", menuBeanList);
    	modelAndView.addObject("currentUrl", request.getRequestURI());
    	modelAndView.addObject("domainPath", "/demoWeb");
    }
    private List<MenuBean> loadMenu1(){
    	
		MenuBean menuBean=new MenuBean();
		menuBean.setContent("物料管理");
		menuBean.setUrl("http://www.baidu.com");
		menuBean.setResourceType("type1");
		
		MenuBean menuBean1=new MenuBean();
		menuBean1.setContent("物料列表");
		menuBean1.setUrl("/e_resource/material/materialList.xhtml");
		
		MenuBean menuBean2=new MenuBean();
		menuBean2.setContent("添加物料");
		menuBean2.setUrl("/e_resource/material/materialAdd.xhtml");
		
        List<MenuBean> menuBeanList = new ArrayList<MenuBean>();
		menuBean.getChildrens().add(menuBean1);
		menuBean.getChildrens().add(menuBean2);
		menuBeanList.add(menuBean);
		return menuBeanList;
    	
    }
private List<MenuBean> loadMenu(){
    	
	MenuBeans beans =null;
	try {
		JAXBContext jaxbContext = JAXBContext.newInstance(MenuBeans.class);
		Unmarshaller unmarshaller;
		unmarshaller = jaxbContext.createUnmarshaller();
		System.out.println(this.getClass().getResource("/menu-config.xml").getPath());
	    beans =(MenuBeans) unmarshaller.unmarshal(new File(this.getClass().getResource("/menu-config.xml").getPath()));
	    System.out.println(beans.getMenubeans().get(0).getId());
	} catch (JAXBException e) {
		e.printStackTrace();
	}

		return beans.getMenubeans();
    	
    }
    
}
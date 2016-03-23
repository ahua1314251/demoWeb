package winker.core.interceptor.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "menus")
@XmlAccessorType (XmlAccessType.FIELD)
public class MenuBeans implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "menu")
	private List<MenuBean> menubeans = null;
	MenuBeans(){
		menubeans =new ArrayList<MenuBean>();
	}
	public List<MenuBean> getMenubeans() {
		return menubeans;
	}
	public void setMenubeans(List<MenuBean> menubeans) {
		this.menubeans = menubeans;
	}
}
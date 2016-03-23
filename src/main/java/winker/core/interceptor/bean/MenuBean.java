package winker.core.interceptor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="menu")
@XmlAccessorType(XmlAccessType.NONE)

public class MenuBean implements Serializable{
	@XmlAttribute
	private String id;
	@XmlAttribute
    private String url;
	@XmlAttribute
    private String content;
	@XmlAttribute
    private String parent;
	@XmlAttribute
    private String icon;
	@XmlAttribute
	private boolean isActive;
	@XmlAttribute
	private boolean isDisplay;
	@XmlElement(name = "menu")
	private List<MenuBean> childrens;
	@XmlAttribute
	private String resourceType;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDisplay() {
		return isDisplay;
	}
	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	public List<MenuBean> getChildrens() {
		if(childrens==null)
		{
			childrens= new ArrayList<MenuBean>();
		}
		return childrens;
	}
	public void setChildrens(List<MenuBean> childrens) {
		this.childrens = childrens;
	}
	
	public boolean hasChildren()
	{
		if(childrens==null||childrens.size()<1)
		{
			return false;
		}
		return true;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}

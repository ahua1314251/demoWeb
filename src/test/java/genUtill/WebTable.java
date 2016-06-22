package genUtill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import winker.dal.model.Material;
import winker.dal.model.WinkerUser;

public class WebTable {

	
	public static void main(String[] args){
		generateDbTableByClazz(WinkerUser.class);
	}
	
	
	public static void generateDbTableByClazz(Class clazz){
		List<String> list =  new ArrayList<String>();
		list.add("create table "+clazz.getSimpleName()+" (");
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field tmpField: fields){
//			System.out.println(tmpField.getType().getName());
//			System.out.println(tmpField.getGenericType().getTypeName());
			if(tmpField.getGenericType().getTypeName().equalsIgnoreCase(String.class.getName())){
				list.add("`"+tmpField.getName()+"` varchar(50) DEFAULT NULL,");
			}
			if(tmpField.getGenericType().getTypeName().equalsIgnoreCase(int.class.getName())){
				list.add("`"+tmpField.getName()+"` int(11) DEFAULT NULL,");
			}
			if(tmpField.getGenericType().getTypeName().equalsIgnoreCase(Date.class.getName())){
				list.add("`"+tmpField.getName()+"` datetime DEFAULT NULL,");
			}
		}
		
		list.add(" )");
		for(String tmp:list){
			System.out.println(tmp);
		}
	}
	
}

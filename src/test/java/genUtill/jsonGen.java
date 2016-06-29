package genUtill;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class jsonGen {

	public static void main(String args[]) throws JsonProcessingException{
		
		List<DemoObj> list = new ArrayList<DemoObj>();
		DemoObj oo = new DemoObj();
		DemoObj oo2 = new DemoObj();
		list.add(oo); 
		list.add(oo2); 
		
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(mapper.writeValueAsString(list));
		
		
	}
	
	static class DemoObj {
		private String areaName="123";
		private String query="1232";
		public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}
		
		
	}
	
}

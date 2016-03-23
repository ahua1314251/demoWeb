package winker.core.interceptor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.context.support.WebApplicationObjectSupport;
public class SystemStaticParameter extends WebApplicationObjectSupport {
    public static Map <String,Object> parameter;
       
	public static Map<String, Object> getParameter() {
		if(parameter==null)
		{
			parameter = new HashMap<String, Object>();
		}
		return parameter;
	}

	public static void setParameter(Map<String, Object> parameter) {
		SystemStaticParameter.parameter = parameter;
	}
       
       
}

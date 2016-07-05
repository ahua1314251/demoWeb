package genUtill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjecUtil {
	 /**
	   * 序列化对象
	   * @param obj
	   * @param filePath
	   * @throws FileNotFoundException
	   * @throws IOException
	   */
	  public static void SerializeObject(Object obj,String filePath) throws 
	  FileNotFoundException,IOException {
	  // ObjectOutputStream 对象输出流，将对象存储到文件中，完成对对象的序列化操作
	  ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
	  os.writeObject(obj);
	  os.close();
	                    }
	      
	  /**
	   * 反序列化对象
	   * @param filePath
	   * @return
	   * @throws Exception
	   * @throws IOException
	   */
	  public static Object DeserializeObject(String filePath)  {
		  File file = new File(filePath);
	 //	  System.out.println("**********"+file.getAbsoluteFile());
		  Object obj = null;
		  if(!file.exists())
		  {
			  return null;
		  }
	      ObjectInputStream ois =null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			obj = (Object) ois.readObject();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(ois!=null)
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	      return obj;
	  }
}

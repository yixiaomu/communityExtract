package cn.passionshark.project.community.core.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;


public class ResourceUtils {

	  private ResourceBundle resourceBundle;

	  private ResourceUtils(String resource)
	  {
	    this.resourceBundle = ResourceBundle.getBundle(resource);
	  }

	  public static ResourceUtils getResource(String resource)
	  {
	    return new ResourceUtils(resource);
	  }

	  public String getValue(String key, Object[] args)
	  {
	    String temp = this.resourceBundle.getString(key);
	    return MessageFormat.format(temp, args);
	  }

	  public Map<String, String> getMap()
	  {
	    Map map = new HashMap();
	    Iterator i$ = this.resourceBundle.keySet().iterator();
	    while (true)
	    {
	      if (i$.hasNext())
	        break;
	      String key = (String)i$.next();
	      map.put(key, this.resourceBundle.getString(key));
	    }
	    return map;
	  }
}

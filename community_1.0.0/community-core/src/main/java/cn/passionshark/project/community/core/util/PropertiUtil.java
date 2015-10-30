package cn.passionshark.project.community.core.util;

import java.util.HashMap;
import java.util.Map;


public class PropertiUtil {
	private static Map<String,String> serverUrlMap = null;


	static {
		serverUrlMap = ResourceUtils.getResource("serverUrl")
				.getMap();
	}
	
	public Map<String,String> getServerUrlMap()
	{
		return serverUrlMap;
	}
}

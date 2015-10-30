package cn.passionshark.project.community.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 */
public class JSONUtil {
	
	
	/**
	 * 方法说明 : 转化 Map为JSON对象
	 *         支持 value为 String 或 String[] 
	 * 
	 * @param Map<String, Object>
	 * @return net.sf.json.JSONObject
	 */
	public static JSONObject convertMap2JsonObject(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		
		JSONObject jsonObject = new JSONObject();
		String [] tmp = null;
		StringBuffer sb = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey() == null || "".equals(entry.getKey().trim())) {
				continue;
			}
			
			if (entry.getValue() instanceof String) {
				jsonObject.put(entry.getKey(), entry.getValue().toString());
			} else if (entry.getValue() instanceof String[]) {
				tmp = (String [])entry.getValue();
				sb = new StringBuffer();
				if (tmp != null && tmp.length > 0) {
					for (int j = 0; j < tmp.length; j++) {
						sb.append(tmp[j]).append(",");
					}
					
					jsonObject.put(entry.getKey(), sb.toString().subSequence(0, sb.toString().length() - 1));
				}
			}
		}
		
		return jsonObject;
	}
	
	
	/**
	 * 方法说明 : 转化 Map为json字符串
	 *         支持 value为 String 或 String[]
	 * 
	 * @param Map<String, Object>
	 * @return String
	 */
	public static String convertMap2JsonStr(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		
		JSONObject jsonObject = convertMap2JsonObject(map);
		
		return jsonObject.toString();
	}
	
	
	/**
	 * 
	 * 方法说明 : 转化接口返回数据为 json 格式数据
	 *        map 中必须包含 业务处理状态信息 status
	 * 
	 * @param map
	 * @return
	 */
	public static String convertServerData2JsonStr(Map<String, String> map, String status) {
		String retMessage = "";
		if (map == null || map.isEmpty() ) {
			return retMessage;
		}
		
		if (map.get(status) == null || "".equals(map.get(status).trim())) {
			return retMessage;
		}
		
		JSONObject jsonObject = new JSONObject();		
		JSONArray dataArray = new JSONArray();
		JSONObject dataObject = new JSONObject();
		for (Entry<String, String> entry : map.entrySet()) {
			
			dataObject.put(entry.getKey(), entry.getValue());
			
		}
		dataArray.add(dataObject);
		if(dataArray == null || dataArray.isEmpty())
		{
			jsonObject.put("ret", false);
		}
		else
		{
			jsonObject.put("ret", true);
		}
		jsonObject.put("data", dataArray);
		
		return jsonObject.toString();
	}
	
	/**
	 * 
	 * 方法说明 : 转化接口返回数据为 json 格式数据
	 *        map 中必须包含 业务处理状态信息 status
	 * 
	 * @param map
	 * @return
	 */
	public static String convertServerData2JsonStr(Map<String, String> map ) {
		String retMessage = "";
		if (map == null || map.isEmpty()) {
			return retMessage;
		}
		
		JSONObject jsonObject = new JSONObject();		
		JSONArray dataArray = new JSONArray();
		JSONObject dataObject = new JSONObject();
		for (Entry<String, String> entry : map.entrySet()) {
			
			dataObject.put(entry.getKey(), entry.getValue());
			
		}
		dataArray.add(dataObject);
		if(dataArray == null || dataArray.isEmpty())
		{
			jsonObject.put("ret", false);
		}
		else
		{
			jsonObject.put("ret", true);
		}
		jsonObject.put("data", dataArray);
		
		return jsonObject.toString();
	}
	
	public static String convert2Json(Map<String, Object> map) {
		String retMessage = "";
		if (map == null || map.isEmpty()) {
			return retMessage;
		}
		
		JSONObject jsonObject = new JSONObject();		
		JSONArray dataArray = new JSONArray();
		JSONObject dataObject = new JSONObject();
		for (Entry<String, Object> entry : map.entrySet()) {
			
			dataObject.put(entry.getKey(), entry.getValue());
			
		}
		dataArray.add(dataObject);
		if(dataArray == null || dataArray.isEmpty())
		{
			jsonObject.put("ret", false);
		}
		else
		{
			jsonObject.put("ret", true);
		}
		jsonObject.put("data", dataArray);
		
		return jsonObject.toString();
	}
	
	/**
	 * 默认成功返回 JSON转换
	 * @param map
	 * @return
	 */
	public static String convertMapToJson(Map<String, String> map) {
		return convertMapToJson(map,true);
	}
	
	/**
	 * 
	 * 方法说明 : 转化接口返回数据为 json 格式数据
	 * status 决定返回json ret的状态
	 * @param map
	 * @return
	 */
	public static String convertMapToJson(Map<String, String> map,boolean status) {

		JSONObject jsonObject = new JSONObject();		

		if(map == null || map.isEmpty()){
			jsonObject.put("ret", false);
			return jsonObject.toString();
		}
		JSONObject dataObject = new JSONObject();
		for (Entry<String, String> entry : map.entrySet()) {
			dataObject.put(entry.getKey(), entry.getValue());
			
		}
		JSONArray dataArray = new JSONArray();
		dataArray.add(dataObject);
		
		jsonObject.put("ret", status);
		jsonObject.put("data", dataArray);
		return jsonObject.toString();
	}
	
	/**
	 * 
	 * 方法说明 : 转化接口返回数据为 json 格式数据
	 *         map 转化数据
	 *         status 状态字段在map中的key值
	 *         statusSignFlag 状态字段是否在data 中存在标识  true ： 存在  false ： 不存在
	 * 
	 * @param map
	 * @return
	 */
	public static String convertServerData2JsonStr(Map<String, String> map, String status, boolean statusSignFlag) {
		String retMessage = "";
		if (map == null || map.isEmpty()) {
			return retMessage;
		}
		
		if (map.get(status) == null || "".equals(map.get(status).trim())) {
			return retMessage;
		}
		
		JSONObject jsonObject = new JSONObject();		
		JSONArray dataArray = new JSONArray();
		JSONObject dataObject = new JSONObject();
		for (Entry<String, String> entry : map.entrySet()) {
			try {
				if (entry.getKey().equals(status)) {
					if (statusSignFlag) {
						dataObject.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
					}
					
				} else {
					dataObject.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
			}	
		}
		dataArray.add(dataObject);
		if(dataArray==null ||dataArray.isEmpty())
		{
			jsonObject.put("ret", false);
		}
		else
		{
			jsonObject.put("ret", true);
		}
		jsonObject.put("data", dataArray);
		
		return jsonObject.toString();
	}
	
	
	/**
	 * 
	 * 方法说明 : 转化接口返回数据为 json 格式数据
	 *         status 状态字段在map中的key值
	 *         statusSignFlag 状态字段是否在data 中存在标识  true ： 存在  false ： 不存在
	 * 
	 * @param errcode 错误码
	 * @param errmsg  错误信息
	 * @return
	 */
	public static String convertErreoJsonStr(boolean status,Long errcode,String errmsg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", status);
		jsonObject.put("errcode", errcode);
		jsonObject.put("errmsg", errmsg);
		return jsonObject.toString();
	}


	
	/**
     * 
     * 方法说明 : 转化接口返回数据为 json 格式数据
     *         status 状态字段在map中的key值
     *         statusSignFlag 状态字段是否在data 中存在标识  true ： 存在  false ： 不存在
     * 
     * @param errcode 错误码
     * @param errmsg  错误信息
     * @return
     */
    public static String convertErreoJsonStr(boolean status,String errcode,String errmsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret", status);
        jsonObject.put("errcode", errcode);
        jsonObject.put("errmsg", errmsg);
        return jsonObject.toString();
    }
	
	
	
	public static void main(String [] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("name", "jiangbin");
//		map.put("sex", "male");
//		map.put("address", new String[]{"china", "beijing"});
//		map.put("food", new String[]{"", ""});
//		
//		System.out.println(convertMap2JsonStr(map));
		
		
		Map<String, String> serverMap = new HashMap<String, String>();
		serverMap.put("status", "fa");
		serverMap.put("name", "jiangbin");
		serverMap.put("sex", "male");
		
		System.out.println(convertMapToJson(serverMap,true));
		
		//System.out.println(convertServerData2JsonStr(serverMap,"status"));
	}
	
}	

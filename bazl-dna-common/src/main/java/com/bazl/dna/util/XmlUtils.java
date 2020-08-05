package com.bazl.dna.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

/**
 * xml 工具类
 */
public class XmlUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

	/**
	 * Generate constructor
	 */
	private XmlUtils() {
	}

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param xml
	 *            xml格式的字符串
	 * @return 成功返回json 格式的字符串;失败反回null
	 */
	public static String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
			SAXReader sb = new SAXReader();
			Document doc = sb.read(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			is.close();
			return obj.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param file
	 *            java.io.File实例是一个有效的xml文件
	 * @return 成功反回json 格式的字符串;失败反回null
	 */
	public static String xml2JSON(File file) {
		JSONObject obj = new JSONObject();
		try {
			SAXReader sb = new SAXReader();
			Document doc = sb.read(file);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private static JSONObject iterateElement(Element element) {
		List<Element> nodeList = element.elements();
		JSONObject obj = new JSONObject();
		JSONObject attributes = new JSONObject();
		List<Attribute> attributesList = element.attributes();
		if (!attributesList.isEmpty()) {
			for (Attribute defaultAttribute : attributesList) {
				attributes.put(defaultAttribute.getName(), defaultAttribute.getText());
			}
			obj.put("attributes", attributes);
		}
		JSONArray jsonArray = null;
		Element et = null;
		for (int i = 0; i < nodeList.size(); i++) {
			jsonArray = new JSONArray();
			et = nodeList.get(i);
			if (Strings.isNullOrEmpty(et.getTextTrim())) {
				if (obj.containsKey(et.getName())) {
					jsonArray = obj.getJSONArray(et.getName());
				}
				jsonArray.add(iterateElement(et));
				obj.put(et.getName(), jsonArray);
			} else {
				if (obj.containsKey(et.getName())) {
					jsonArray = obj.getJSONArray(et.getName());
				}
				jsonArray.add(et.getTextTrim());
				obj.put(et.getName(), jsonArray);
			}
		}
		return obj;
	}

	/**
	 * json to xml
	 * 
	 * @param json
	 * @return
	 */
	public static String json2XML(String json) {
		org.json.JSONObject jsonObj = new org.json.JSONObject(json);
		return "<xml>" + XML.toString(jsonObj) + "</xml>";
	}

	/**
	 * xml to json
	 * 
	 * @param xml
	 * @return
	 */
	public static String xml2JSONObject(String xml) {
		org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml.replace("<xml>", "").replace("</xml>", ""));
		return xmlJSONObj.toString();
	}

}

package com.zhxs.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	public static String toJSON(Object obj) {
		String json;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	public static <T> T toObject(String json, Class<T> target){
		T readValue = null;
		try {
			readValue = mapper.readValue(json, target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return readValue;
		
	}
}

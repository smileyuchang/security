package io.renren.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 ******************************************************
 * Copyright HSLD Corporation. All Rights Reserved
 * Description:反射工具类
 * CreateTime:2017年1月5日 下午1:40:06
 * CreateUser: 阿宾
 *******************************************************
 */
public class ReflectUtil {
	/*
	 *************************************** 
	 * Description:设置对象属性值：obj 实体对象,fieldName 属性名,value 属性值 
	 * CreateTime :2017年1月5日 下午1:40:39
	 * CreateUser :阿宾 
	 *****************************************
	*/
	public static void setProperty(Object obj, String fieldName, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			if (field != null) {
				Class<?> fieldType = field.getType();
				field.setAccessible(true);
				// 根据字段类型给字段赋值
				if (String.class == fieldType) {
					
					String s1 = value.toString();
					String s2 = String.valueOf(value);
//					 String s= new String(String.valueOf(value).getBytes("ISO8859_1"), "utf-8");
					field.set(obj, s1);
				} else if ((Integer.TYPE == fieldType)
						|| (Integer.class == fieldType)) {
					field.set(obj, Integer.parseInt(value.toString()));
				} else if ((Long.TYPE == fieldType)
						|| (Long.class == fieldType)) {
					field.set(obj, Long.valueOf(value.toString()));
				} else if ((Float.TYPE == fieldType)
						|| (Float.class == fieldType)) {
					field.set(obj, Float.valueOf(value.toString()));
				} else if ((BigDecimal.class == fieldType)) {
					field.set(obj, new BigDecimal(value.toString()));
				} else if ((Short.TYPE == fieldType)
						|| (Short.class == fieldType)) {
					field.set(obj, Short.valueOf(value.toString()));
				} else if ((Double.TYPE == fieldType)
						|| (Double.class == fieldType)) {
					field.set(obj, Double.valueOf(value.toString()));
				} else if (Character.TYPE == fieldType) {
					if ((value != null) && (value.toString().length() > 0)) {
						field.set(obj,
								Character.valueOf(value.toString().charAt(0)));
					}
				} else if (Date.class == fieldType) {
					if (value instanceof Date) {
						field.set(obj, value);
					} else if (value instanceof String) {
						Date date;
						if(((String) value).length()>10){
							date =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
						}else{
							
							date =new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
						}
						field.set(obj, date);
					}
				} else {
					field.set(obj, value);
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/*
	 *************************************** 
	 * Description:获取对象属性值：obj 实体对象，fieldName 属性名
	 * CreateTime :2017年1月5日 下午1:42:00
	 * CreateUser :阿宾 
	 *****************************************
	*/
	public static Object getProperty(Object obj, String fieldName) {
		Field field = getFieldName(obj, fieldName);
		Object value = null;
		try {
			if (field != null) {
				Class<?> fieldType = field.getType();
				field.setAccessible(true);
				// 根据字段类型给字段赋值
				if (Date.class == fieldType) {
					Object o = field.get(obj);
					if (o != null) {
						value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(o);
					}
				} else {
					value = field.get(obj);
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// 获取department.name 属性值
	public static Object getNestedProperty(Object obj, String fieldName) {
		Object value = null;
		String[] attributes = fieldName.split("\\.");
		try {
			value = getProperty(obj, attributes[0]);
			for (int i = 1; i < attributes.length; i++) {
				value = getProperty(value, attributes[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/*
	 *************************************** 
	 * Description: 获取属性
	 * CreateTime :2017年1月5日 下午1:45:38
	 * CreateUser :阿宾 
	 *****************************************
	*/
	public static Field getFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/*
	 *************************************** 
	 * Description:获取对象所有字段的名字：obj 目标对象
	 * CreateTime :2017年1月5日 下午1:44:29
	 * CreateUser :阿宾 
	 *****************************************
	*/
	public static String[] getFieldNames(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			if ((fields[i].getModifiers() & Modifier.STATIC) == 0) {
				fieldNames.add(fields[i].getName());
			}
		}
		return fieldNames.toArray(new String[fieldNames.size()]);
	}
}

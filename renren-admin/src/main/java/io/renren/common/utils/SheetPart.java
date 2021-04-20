package io.renren.common.utils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 模板组成部分
 */
public class SheetPart {

	private Class entityClass;

	private String name;

	private LinkedHashMap<String, String> fieldMap;

	private LinkedHashMap<String, String> protectMap;

	private LinkedHashMap<String, String> formulaMap;

	private Integer titleRowNumber;

	private Integer startRowNumber;

	private Integer endRowNumber;

	private String headline;

	private List data;

	private int count;

	public SheetPart(String headline, LinkedHashMap<String, String> fieldMap, LinkedHashMap<String, String> protectMap,
                     List data, int count) {
		this.headline = headline;
		this.fieldMap = fieldMap;
		this.protectMap = protectMap;
		this.data = data;
		this.count = count;
	}

	public SheetPart(String headline, LinkedHashMap<String, String> fieldMap, LinkedHashMap<String, String> protectMap,
                     LinkedHashMap<String, String> formulaMap, List data, int count) {
		this.fieldMap = fieldMap;
		this.protectMap = protectMap;
		this.formulaMap = formulaMap;
		this.headline = headline;
		this.data = data;
		this.count = count;
	}

	public SheetPart(Class entityClass, String name, LinkedHashMap<String, String> fieldMap, Integer titleRowNumber,
                     Integer startRowNumber, Integer endRowNumber) {
		this.entityClass = entityClass;
		this.name = name;
		this.fieldMap = fieldMap;
		this.titleRowNumber = titleRowNumber;
		this.startRowNumber = startRowNumber;
		this.endRowNumber = endRowNumber;
	}

	public LinkedHashMap<String, String> getFormulaMap() {
		return formulaMap;
	}

	public void setFormulaMap(LinkedHashMap<String, String> formulaMap) {
		this.formulaMap = formulaMap;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedHashMap<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(LinkedHashMap<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public Integer getTitleRowNumber() {
		return titleRowNumber;
	}

	public void setTitleRowNumber(Integer titleRowNumber) {
		this.titleRowNumber = titleRowNumber;
	}

	public Integer getStartRowNumber() {
		return startRowNumber;
	}

	public void setStartRowNumber(Integer startRowNumber) {
		this.startRowNumber = startRowNumber;
	}

	public Integer getEndRowNumber() {
		return endRowNumber;
	}

	public void setEndRowNumber(Integer endRowNumber) {
		this.endRowNumber = endRowNumber;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public LinkedHashMap<String, String> getProtectMap() {
		return protectMap;
	}

	public void setProtectMap(LinkedHashMap<String, String> protectMap) {
		this.protectMap = protectMap;
	}

}

package com.toolkit2.client;
/*
 * 		操作API
 * */
public class OperateCode {

	String code;
	String type;
	String className;
	String description;
	String methodName;
	
	boolean visible = false;

	public OperateCode() {
		code = "";
		type = "";
		className = "";
		description = "";
		methodName = "";
		visible = true;
	}

	public OperateCode(String code, String methodName, String description) {
		this.code = "";
		type = "";
		className = "";
		this.description = "";
		this.methodName = "";
		visible = true;
		this.methodName = methodName;
		this.code = code;
		this.description = description;
		type = "Method";
	}

	public OperateCode(String code, String type, String className,
			String description) {
		this.code = "";
		this.type = "";
		this.className = "";
		this.description = "";
		methodName = "";
		visible = true;
		this.code = code;
		this.type = type;
		this.className = className;
		this.description = description;
	}

	public OperateCode(String code, String type, String className,
			String methodName, String description) {
		this.code = "";
		this.type = "";
		this.className = "";
		this.description = "";
		this.methodName = "";
		visible = true;
		this.code = code;
		this.type = type;
		this.className = className;
		this.methodName = methodName;
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
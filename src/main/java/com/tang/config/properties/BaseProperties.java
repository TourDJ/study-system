package com.tang.config.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="tang")
public class BaseProperties {

	private String simpleProp;
	private String[] arrayProps;
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
	
	public String getSimpleProp() {
		return simpleProp;
	}
	
	//String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要
	public void setSimpleProp(String simpleProp) {
		this.simpleProp = simpleProp;
	}
	
	public List<Map<String, String>> getListProp1() {
		return listProp1;
	}

	public String[] getArrayProps() {
		return arrayProps;
	}
 
	public void setArrayProps(String[] arrayProps) {
		this.arrayProps = arrayProps;
	}

}

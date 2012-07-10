package com.boriskuzmic.raphael.gwt.charts;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class TranslateAttributes {

	public float translateX;
	public float translateY;
	
	public TranslateAttributes(float translateX, float translateY) {
		this.translateX = translateX;
		this.translateY = translateY;
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("translation", new JSONString("" + translateX + "," + translateY));	
		return json;
	}
	
}

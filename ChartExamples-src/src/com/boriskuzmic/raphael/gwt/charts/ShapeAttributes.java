package com.boriskuzmic.raphael.gwt.charts;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class ShapeAttributes {

	private String stroke = "none";
	private String strokeWidth = "0";
	private String fill = "";
	private String gradient = "";
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("stroke", new JSONString(stroke));
		json.put("stroke-width", new JSONString(strokeWidth));
		json.put("fill", new JSONString(fill));
		json.put("gradient", new JSONString(gradient));
		return json;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}
	
	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public void setGradient(String gradient) {
		this.gradient = gradient;
	}
	
	
	
}

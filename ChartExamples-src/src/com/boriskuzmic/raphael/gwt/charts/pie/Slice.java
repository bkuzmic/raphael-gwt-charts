package com.boriskuzmic.raphael.gwt.charts.pie;

import com.boriskuzmic.raphael.gwt.charts.pie.parts.AbstractSlicePart;
import com.google.gwt.json.client.JSONObject;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;

public class Slice {
	
	public float translateX;
	public float translateY;
	public float middleX;
	public float middleY;
	
	private Raphael.Path borderPath;
	private Raphael.Path side1Path;
	private Raphael.Path side2Path;
	private PieChart.TopPath topPath;
	
	public Slice() {	
	}
	
	public PathBuilder getPathBuilder(AbstractSlicePart part, float startX, float startY, float endX, float endY, int largeAngleFlag) {
		return part.createPath(startX, startY, endX, endY, largeAngleFlag);
	}
	
	public JSONObject getAttributes(AbstractSlicePart part, int sliceIndex) {
		return part.createAttributes(sliceIndex).getJSON();
	}

	public Raphael.Path getBorderPath() {
		return borderPath;
	}

	public void setBorderPath(Raphael.Path borderPath) {
		this.borderPath = borderPath;
	}

	public Raphael.Path getSide1Path() {
		return side1Path;
	}

	public void setSide1Path(Raphael.Path side1Path) {
		this.side1Path = side1Path;
	}

	public Raphael.Path getSide2Path() {
		return side2Path;
	}

	public void setSide2Path(Raphael.Path side2Path) {
		this.side2Path = side2Path;
	}

	public PieChart.TopPath getTopPath() {
		return topPath;
	}

	public void setTopPath(PieChart.TopPath topPath) {
		this.topPath = topPath;
	}
	
	
	
}

package com.boriskuzmic.raphael.gwt.charts;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChartOptions {
	public int canvasWidth;
	public int canvasHeight;
	
	public List<Float> values = new ArrayList<Float>();
	public List<String> labels = new ArrayList<String>();
	
	public List<String> colors = new ArrayList<String>();
	public List<String> darkColors = new ArrayList<String>();
	public List<String> lightColors = new ArrayList<String>();
	
	public boolean drawAs3D = true;
	
	public boolean tooltipUseRoundedValues = false;	
	public boolean tooltipShowValueInPercentage = false;
	public boolean tooltipChangeableBorderColor = false;
	
	public boolean showLegend = false;
	public enum LegendPosition {
		LEFT, RIGHT, TOP, BOTTOM
	}
	public LegendPosition chartLegendPosition = LegendPosition.LEFT;
	public boolean highlightLegendOnChartHover = true;
	
	public String[] backgroundFill = new String[] {"#fff", "#fff"};
	
}

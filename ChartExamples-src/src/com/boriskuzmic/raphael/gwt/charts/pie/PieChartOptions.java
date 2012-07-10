package com.boriskuzmic.raphael.gwt.charts.pie;

import com.boriskuzmic.raphael.gwt.charts.AbstractChartOptions;

public class PieChartOptions extends AbstractChartOptions {
		
	public float centerX;
	public float centerY;
	public float radius1;
	public float radius2;
	
	public int size3d = 15; // thickness of 3D pie
	public int explodeFactor = 6; // six time smaller then size of radius
	
	public boolean useAnimation = true;
	public int animationOverSpeed = 1000; // in miliseconds
	public int animationOutSpeed = 500; // in miliseconds
	
	public boolean showExplodedSlices = false;
	public boolean explodeOnlyLargestSlice = false;
	
	public PieChartOptions(int canvasWidth, int canvasHeight) {		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.centerX = (float) canvasWidth / 2;
		this.centerY = (float) canvasHeight / 2;
		this.radius1 = (float) canvasWidth / 2;
		this.radius2 = (float) canvasHeight / 2;		
	}
	
	public PieChartOptions(int canvasWidth, int canvasHeight, float radius) {		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.centerX = (float) canvasWidth / 2;
		this.centerY = (float) canvasHeight / 2;
		this.radius1 = radius;
		this.radius2 = radius;		
	}
	
	public PieChartOptions(int canvasWidth, int canvasHeight, float radius1, float radius2) {		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.centerX = (float) canvasWidth / 2;
		this.centerY = (float) canvasHeight / 2;
		this.radius1 = radius1;
		this.radius2 = radius2;		
	}
}

package com.boriskuzmic.raphael.gwt.charts.pie.parts;

import com.boriskuzmic.raphael.gwt.charts.ShapeAttributes;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChartOptions;
import com.hydro4ge.raphaelgwt.client.PathBuilder;

public abstract class AbstractSlicePart {
	
	protected PieChartOptions options;			
	
	public AbstractSlicePart(PieChartOptions pieChartOptions) {
		this.options = pieChartOptions;
	}
	
	public abstract PathBuilder createPath(float startX, float startY, float endX, float endY, int largeAngleFlag);	
	
	public abstract ShapeAttributes createAttributes(int sliceIndex);
	
}

package com.boriskuzmic.raphael.gwt.charts.pie.parts;

import com.boriskuzmic.raphael.gwt.charts.ShapeAttributes;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChartOptions;
import com.hydro4ge.raphaelgwt.client.PathBuilder;

public class SidePart extends AbstractSlicePart {

	public SidePart(PieChartOptions pieChartOptions) {
		super(pieChartOptions);		
	}

	@Override
	public PathBuilder createPath(float startX, float startY, float endX, float endY, int largeAngleFlag) {		
		PathBuilder pathBuilder = new PathBuilder();
		pathBuilder.M(startX, startY)
			.L(options.centerX, options.centerY)
			.L(options.centerX, options.centerY + options.size3d)
			.L(startX, startY + options.size3d)
			.z();	
		return pathBuilder;
	}

	@Override
	public ShapeAttributes createAttributes(int sliceIndex) {
		ShapeAttributes shapeAttributes = new ShapeAttributes();		
		shapeAttributes.setFill(options.darkColors.get(sliceIndex)); 				
		return shapeAttributes;
	}

}

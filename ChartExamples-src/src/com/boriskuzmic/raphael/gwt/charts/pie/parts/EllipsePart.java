package com.boriskuzmic.raphael.gwt.charts.pie.parts;

import com.boriskuzmic.raphael.gwt.charts.ShapeAttributes;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChartOptions;
import com.hydro4ge.raphaelgwt.client.PathBuilder;

public class EllipsePart extends AbstractSlicePart {

	public EllipsePart(PieChartOptions pieChartOptions) {
		super(pieChartOptions);		
	}

	@Override
	public PathBuilder createPath(float startX, float startY, float endX, float endY, int largeAngleFlag) {
		PathBuilder pathBuilder = new PathBuilder();
		pathBuilder.M(options.centerX, options.centerY)
			.L(startX, startY)
			.A(options.radius1, options.radius2, 0, 0, 0, endX, endY)
			.A(options.radius1, options.radius2, 0, 0, 0, startX, startY)			
			.z();	
		return pathBuilder;
	}

	@Override
	public ShapeAttributes createAttributes(int sliceIndex) {
		ShapeAttributes shapeAttributes = new ShapeAttributes();				
		shapeAttributes.setGradient("180-" + options.darkColors.get(sliceIndex) 
									+ "-" + options.lightColors.get(sliceIndex));
		return shapeAttributes;
	}

}

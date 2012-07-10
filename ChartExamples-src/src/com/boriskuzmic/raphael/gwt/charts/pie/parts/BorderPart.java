package com.boriskuzmic.raphael.gwt.charts.pie.parts;

import com.boriskuzmic.raphael.gwt.charts.ShapeAttributes;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChartOptions;
import com.hydro4ge.raphaelgwt.client.PathBuilder;

public class BorderPart extends AbstractSlicePart {

	public BorderPart(PieChartOptions pieChartOptions) {
		super(pieChartOptions);		
	}

	@Override
	public PathBuilder createPath(float startX, float startY, float endX, float endY, int largeAngleFlag) {				
		PathBuilder pathBuilder = new PathBuilder();
		pathBuilder.M(startX, startY)
			.A(options.radius1, options.radius2, 0, largeAngleFlag, 0, endX, endY)
			.L(endX, endY + options.size3d)
			.A(options.radius1, options.radius2, 0, largeAngleFlag, 1, startX, startY + options.size3d)
			.L(startX, startY)
			.z();
		return pathBuilder;
	}

	@Override
	public ShapeAttributes createAttributes(int sliceIndex) {
		ShapeAttributes shapeAttributes = new ShapeAttributes();		
		shapeAttributes.setGradient("90-" + options.darkColors.get(sliceIndex) + "-" + options.lightColors.get(sliceIndex)); 				
		return shapeAttributes;
	}

}

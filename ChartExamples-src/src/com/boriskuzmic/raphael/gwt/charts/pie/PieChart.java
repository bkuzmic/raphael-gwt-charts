package com.boriskuzmic.raphael.gwt.charts.pie;

import java.util.*;
import java.util.Map.*;

import com.hydro4ge.raphaelgwt.client.*;
import com.boriskuzmic.raphael.gwt.charts.*;
import com.boriskuzmic.raphael.gwt.charts.pie.parts.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;

public class PieChart extends Raphael {
	
	private static int RED_FACTOR = 36;
	private static int GREEN_FACTOR = 30;
	private static int BLUE_FACTOR = 24;	
	
	private PieChartOptions options;
	
	private Slice currentSlice;
	private List<Slice> slices = new ArrayList<Slice>();
	
	private float totalSumOfValues;
	private float deegreesTotal;
	private int largeAngleFlag;
	
	private int curIndex;
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	private float curAlpha;
	private float sumOfValues;
	private float rad = (float) Math.PI / 180;
	
	private boolean draw3dBorder = true;
	
	private HashMap<Integer, PathBuilder> sidePartsForLaterDrawing = new HashMap<Integer, PathBuilder>();
	private HashMap<Integer, PathBuilder> borderPartsForLaterDrawing = new HashMap<Integer, PathBuilder>();
	private HashMap<Integer, PathBuilder> topPartsForLaterDrawing = new HashMap<Integer, PathBuilder>();
	
	private Element chartElement;
	private Tooltip tooltip;
	private Legend legend;
	
	public class TopPath extends Raphael.Path implements HasMouseOverHandlers, HasMouseOutHandlers{

		public int topSliceIndex = 0;
		
		public TopPath(PathBuilder pathBuilder) {
			super(pathBuilder);
		}
		
		@Override
		public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
			return this.addDomHandler(handler, MouseOutEvent.getType());
		}

		@Override
		public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
			return this.addDomHandler(handler, MouseOverEvent.getType());
		}
		
	}

	public PieChart(int width, int height) {
		super(width, height);		
		setup(width, height, null);
	}
	
	public PieChart(int width, int height, PieChartOptions chartOptions) {
		super(width, height);		
		setup(width, height, chartOptions);
	}
	
	private void setup(int canvasWidth, int canvasHeight, PieChartOptions chartOptions) {		
		if (chartOptions == null) {
			options = new PieChartOptions(canvasWidth, canvasHeight);
		} else {
			options = chartOptions;
		}		
	}

	@Override
	public void onLoad() {
	    super.onLoad();	   
	    drawPieChart(); 
	    chartElement = this.getElement().getFirstChildElement();	    
	    addLegendSupport();
	}
	
	private void drawPieChart() {		
		deegreesTotal = calculateTotalSumOfValuesInDeegrees();
		
		calculateUsedColors();

		startX = options.centerX + options.radius1;
		startY = options.centerY;

		sumOfValues = 0;
		curAlpha = 0;	
		
		if (options.values.size() == 1) {
			options.useAnimation = false; // animation not needed			
			currentSlice = new Slice();						
			createEllipse();		
			if (options.drawAs3D) {					
				createBorderParts();
			}			
			slices.add(currentSlice);
		} else {
			for (int i = 0; i < options.values.size(); i++) {
				currentSlice = new Slice();
				
				curIndex = i;
				float curValue = options.values.get(i).floatValue();
	
				endX = startX;
				endY = startY;
	
				sumOfValues += curValue;
	
				curAlpha = deegreesTotal * sumOfValues;
	
				largeAngleFlag = calculateLargeArcFlag(curValue);
	
				startX = options.centerX + options.radius1 * (float) Math.cos(curAlpha * rad);
				startY = options.centerY + options.radius2 * (float) Math.sin(curAlpha * rad);
				
				calculateTranslationAndMiddleCoordinates(curValue);								
				
				if (options.drawAs3D) {
					createSideParts();					
					createBorderParts();	
				}
				createTopParts();	
				
				slices.add(currentSlice);					
			}
			
			drawPartsFlagedForLaterDrawing();
		}
									
		drawBackground();
		
		explodeSlicesIfEnabled();
		
		addAnimationAndTooltipSupport();			
	}	

	private float calculateTotalSumOfValuesInDeegrees() {
		totalSumOfValues = 0;
		for (Float value : options.values) {
			totalSumOfValues += value;
		}
		return 360 / totalSumOfValues;
	}
	
	private void calculateUsedColors() {
		this.resetColor();
		if (areColorsDefined()) {
			setupColors();
		} else {
			setupDefaultColors();
		}							
	}

	private boolean areColorsDefined() {		
		return options.colors.size() == options.values.size();
	}
	
	private void setupColors() {
		// reset dark and light colors
		options.darkColors.clear();
		options.lightColors.clear();
		for (String color : options.colors) {
			setupDarkAndLightColors(color);
		}
	}	

	private void setupDefaultColors() {
		for (int i = 0; i < options.values.size(); i++) {
			String color = this.getColor(); 
			options.colors.add(color);
			setupDarkAndLightColors(color);
		}
	}
	
	private void setupDarkAndLightColors(String color) {
		String darkColor = calculateDarkColor(color);
		options.darkColors.add(darkColor);
		String lightColor = calculateLightColor(color);
		options.lightColors.add(lightColor);		
	}
	

	private String calculateDarkColor(String color) {		
		RGBColor rgbColor = new RGBColor(color);		
		int r = rgbColor.r - RED_FACTOR;
		int g = rgbColor.g - GREEN_FACTOR;
		int b = rgbColor.b - BLUE_FACTOR;
		return "#" + toHex(r) + toHex(g) + toHex(b);	
	}
	
	private String calculateLightColor(String color) {
		RGBColor rgbColor = new RGBColor(color);		
		int r = rgbColor.r + RED_FACTOR;
		int g = rgbColor.g + GREEN_FACTOR;
		int b = rgbColor.b + BLUE_FACTOR;
		return "#" + toHex(r) + toHex(g) + toHex(b);
	}
	
	private String toHex(int number) {
		number = Math.max(0, number);
		number = Math.min(number, 255);
		return Integer.toHexString( 0x100 | number).substring(1);
 	}

	private int calculateLargeArcFlag(float value) {
		return (deegreesTotal * value) < 180 ? 0 : 1;
	}  

	private void calculateTranslationAndMiddleCoordinates(float curValue) {		
		float alphaM = deegreesTotal * (sumOfValues - (curValue / 2));	
		float explodeRadius1 = options.radius1 / options.explodeFactor;
		float explodeRadius2 = options.radius2 / options.explodeFactor;
		
		// calculate translation coordinates for explode effect
		currentSlice.translateX = options.centerX + explodeRadius1 * (float) Math.cos(alphaM * rad);
		currentSlice.translateY = options.centerY + explodeRadius2 * (float) Math.sin(alphaM * rad);	
				
		float tooltipRadius1 = options.radius1 + explodeRadius1;
		float tooltipRadius2 = options.radius2 + explodeRadius2;			
		
		// calculate middle coordinates of top part of slice, for tooltip support
		currentSlice.middleX  = options.centerX + tooltipRadius1 * (float) Math.cos(alphaM * rad);
		currentSlice.middleY = options.centerY + tooltipRadius2 * (float) Math.sin(alphaM * rad);		
	}
	
	private void createEllipse() {
		endX = startX;
		endY = startY;
		startX = options.centerX - options.radius1;
		largeAngleFlag = 0;
		EllipsePart ellipsePart = new EllipsePart(options);
		PathBuilder pbEllipse = currentSlice.getPathBuilder(ellipsePart, startX, startY, endX, endY, largeAngleFlag);
		TopPath ellipse = createTopPath(ellipsePart, pbEllipse);
		currentSlice.middleX = options.centerX + options.radius1;
		currentSlice.middleY = options.centerY;
		currentSlice.setTopPath(ellipse);		
	}	

	private void createSideParts() {
		SidePart sidePart = new SidePart(options);						
		
		PathBuilder pbSideStart = currentSlice.getPathBuilder(sidePart, startX, startY, 0, 0, 0);
		PathBuilder pbSideEnd = currentSlice.getPathBuilder(sidePart, endX, endY, 0, 0, 0);	
		
		if (curAlpha >= 90 && curAlpha <= 270) {	
			Path sidePath = createPathAndSendToBack(sidePart, pbSideEnd);
			currentSlice.setSide1Path(sidePath);
		} else {							
			if (curAlpha > 180 && curAlpha <= 360) {
				if (deegreesTotal * (sumOfValues - options.values.get(curIndex).floatValue()) < 270) {								
					Path sidePath = createPathAndSendToBack(sidePart, pbSideEnd);					
					currentSlice.setSide2Path(sidePath);						
				}
			}
			if (curAlpha < 90) {	
				Path sidePath = createPathAndSendToBack(sidePart, pbSideStart);								
				currentSlice.setSide1Path(sidePath);					
			} else {
				storeSidePartForLaterDrawing(pbSideStart);								
			}
		}		
	}
	
	private void storeSidePartForLaterDrawing(PathBuilder pathBuilder) {
		sidePartsForLaterDrawing.put(new Integer(curIndex), pathBuilder);	
	}

	private void createBorderParts() {
		if (draw3dBorder) {
			Path borderPath = null;
			BorderPart borderPart = new BorderPart(options);
			PathBuilder pbBorder = currentSlice.getPathBuilder(borderPart, startX, startY, endX, endY, largeAngleFlag);							
			
			if (curAlpha <= 90) {
				borderPath = createPath(borderPart, pbBorder);
				currentSlice.setBorderPath(borderPath);
			} else {
				if (curAlpha >= 180) {																
					pbBorder = currentSlice.getPathBuilder(borderPart, options.centerX - options.radius1, options.centerY, endX, endY, 0);	
					borderPath = createPath(borderPart, pbBorder);				
					currentSlice.setBorderPath(borderPath);
					draw3dBorder = false;
				} else {
					storeBorderPartForLaterDrawing(pbBorder);
				}
			}
		} 		
	}
	
	private void storeBorderPartForLaterDrawing(PathBuilder pathBuilder) {
		borderPartsForLaterDrawing.put(new Integer(curIndex), pathBuilder);	
	}
	
	private void createTopParts() {
		TopPart topPart = new TopPart(options);								
		PathBuilder pbTop = currentSlice.getPathBuilder(topPart, startX, startY, endX, endY, largeAngleFlag);			
		storeTopPartForLaterDrawing(pbTop);						
	}
	
	private void storeTopPartForLaterDrawing(PathBuilder pathBuilder) {
		topPartsForLaterDrawing.put(new Integer(curIndex), pathBuilder);	
	}
	
	private void addAnimationAndTooltipSupport() {
		for (Slice slice : slices) {
			slice.getTopPath().addMouseOverHandler(new MouseOverHandler() {			
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if (options.useAnimation) {
						animateSliceOver(event);	
					}
					if (tooltipEnabled()) {
						showTooltip(event);
					}
					highlightLegendOn(event);
				}
			});
			
			slice.getTopPath().addMouseOutHandler(new MouseOutHandler() {			
				@Override
				public void onMouseOut(MouseOutEvent event) {
					if (options.useAnimation) {
						animateSliceOut(event);					
					}
					if (tooltipEnabled()) {
						hideTooltip(event);
					}
					highlightLegendOff(event);
				}		
			});		
		}
	}
	
	private void animateSliceOver(MouseOverEvent event) {	
		TopPath topPath = (TopPath) event.getSource();
		Slice slice = slices.get(topPath.topSliceIndex);		
		float tx = slice.translateX - options.centerX;
		float ty = slice.translateY - options.centerY;
		TranslateAttributes translateAttributes = new TranslateAttributes(tx, ty);				
		
		animateSliceParts(slice, translateAttributes, options.animationOverSpeed);		
	}
	
	private void animateSliceOut(MouseOutEvent event) {
		TopPath topPath = (TopPath) event.getSource();		
		topPath.stopAnimation();
		Slice slice = slices.get(topPath.topSliceIndex);	
		TranslationCoordinates translationCoordinates = topPath.getTranslationCoordinates();
		TranslateAttributes translateAttributes = new TranslateAttributes(-translationCoordinates.x(), -translationCoordinates.y());			
		
		animateSliceParts(slice, translateAttributes,  options.animationOutSpeed);		
	}
	
	private void animateSliceParts(Slice slice, TranslateAttributes translateAttributes, int speed) {
		slice.getTopPath().animate(translateAttributes.getJSON(), speed);
		if (slice.getSide1Path() != null) slice.getSide1Path().animateWith(slice.getTopPath(), translateAttributes.getJSON(), speed);
		if (slice.getSide2Path() != null) slice.getSide2Path().animateWith(slice.getTopPath(), translateAttributes.getJSON(), speed);
		if (slice.getBorderPath() != null) slice.getBorderPath().animateWith(slice.getTopPath(), translateAttributes.getJSON(), speed);
	}
	
	private boolean tooltipEnabled() {
		return tooltip != null;
	}
	
	private void showTooltip(MouseOverEvent event) {	
		TopPath topPath = (TopPath) event.getSource();		
		Slice slice = slices.get(topPath.topSliceIndex);										
		
		float tooltipX = calculateTooltipX(slice);		
		float tooltipY = calculateTooltipY(slice);
		
		tooltip.setPosition(tooltipX, tooltipY);
				
		String tooltipText = generateTooltipText(topPath.topSliceIndex);				
		tooltip.setText(tooltipText);
		
		if (options.tooltipChangeableBorderColor) {
			tooltip.setBorderColor(options.colors.get(topPath.topSliceIndex));		
		}
		tooltip.show();
	}	

	private void hideTooltip(MouseOutEvent event) {
		tooltip.hide();
	}
	
	private float calculateTooltipX(Slice slice) {
		float chartCenterX = chartElement.getAbsoluteLeft() + options.centerX;				
		int adjustX = 0;
		// if slice is on left side from chart center X, adjust tooltip X coordinate		
		if (options.centerX - slice.middleX > 0) {		
			adjustX = -tooltip.getWidth();
		}		
		return chartCenterX - options.centerX + slice.middleX + adjustX;
	}
	
	private float calculateTooltipY(Slice slice) {
		float chartCenterY = chartElement.getAbsoluteTop() + options.centerY;	
		int adjustY = 0;
		// if slice is on top side from chart center Y, adjust tooltip Y coordinate
		if (options.centerY - slice.middleY > 0) {
			adjustY = -tooltip.getHeight();
		}	
		return chartCenterY - options.centerY + slice.middleY + adjustY;
	}
	
	private String generateTooltipText(int index) {
		String value = "";
		if (options.tooltipShowValueInPercentage) {
			value = getPercentageValue(index);
		} else {
			value = getDefaultValue(index);			
		}
		
		String label = getLabelValue(index);		
		String tooltipText = createTooltipText(label, value);
		return tooltipText;
	}
	
	private String getPercentageValue(int index) {
		return String.valueOf(Math.round((options.values.get(index ) / totalSumOfValues ) * 100)) + "%";
	}
	
	private String getDefaultValue(int index) {
		String value = "";
		float val = options.values.get(index);
		if (options.tooltipUseRoundedValues) {
			value = String.valueOf(Math.round(val));
		} else {
			value = String.valueOf(val);
		}		
		return value;
	}
	
	private String getLabelValue(int index) {
		String label;
		try {
			label = options.labels.get(index);
		} catch (IndexOutOfBoundsException e) {
			label = "";
		}
		return label;
	}
	
	private String createTooltipText(String label, String value) {
		return label == "" ? value : label + " - " + value;
	}
	
	private void addLegendSupport() {
		if (options.showLegend && legend != null) {
			for (int i=0; i < options.values.size(); i++) {
				String value = getDefaultValue(i);
				String label = getLabelValue(i);
				legend.addItem(createLegendItemText(label, value), options.colors.get(i));			
			}							
			legend.setSizeAndPosition(chartElement.getParentElement(), 
									options.canvasWidth, options.canvasHeight,
									options.chartLegendPosition);			
		}
	}
	
	private String createLegendItemText(String label, String value) {
		return label == "" ? value: label + " (" + value + ")";
	}
	
	private void highlightLegendOn(MouseOverEvent event) {
		if (isLegendEnabled()) {
			TopPath topPath = (TopPath) event.getSource();	
			legend.highlightItemOn(topPath.topSliceIndex);
		}
	}
	
	private void highlightLegendOff(MouseOutEvent event) {
		if (isLegendEnabled()) {
			TopPath topPath = (TopPath) event.getSource();	
			legend.highlightItemOff(topPath.topSliceIndex);
		}
	}
	
	private boolean isLegendEnabled() {
		return options.highlightLegendOnChartHover && legend != null;
	}
	
	private void drawBackground() {
		Rect backgroundRect = new Rect(0, 0, options.canvasWidth, options.canvasHeight);
		ShapeAttributes shapeAttributes = new ShapeAttributes();	
		shapeAttributes.setGradient("90-" + options.backgroundFill[0] + "-" + options.backgroundFill[1]);		
		backgroundRect.attr(shapeAttributes.getJSON());
		backgroundRect.toBack();
	}
	
	private Path createPathAndSendToBack(AbstractSlicePart part, PathBuilder pathBuilder) {
		Path path = createPath(part, pathBuilder);
		path.toBack();
		return path;
	}
	
	private Path createPath(AbstractSlicePart part, PathBuilder pathBuilder) {
		Path path = new Path(pathBuilder);
		path.attr(currentSlice.getAttributes(part, curIndex));
		return path;
	}
	
	private void drawPartsFlagedForLaterDrawing() {
		drawRestOfSideParts();
		drawRestOfBorderParts();
		drawRestOfTopParts();
	}
	
	private void drawRestOfSideParts() {
		for (Entry<Integer, PathBuilder> entry : sidePartsForLaterDrawing.entrySet()) {
			curIndex = entry.getKey().intValue();
			Path side1Path = createPathAndSendToBack(new SidePart(options), entry.getValue());
			slices.get(curIndex).setSide1Path(side1Path);
		}
	}
	
	private void drawRestOfBorderParts() {
		for (Entry<Integer, PathBuilder> entry : borderPartsForLaterDrawing.entrySet()) {
			curIndex = entry.getKey().intValue();
			Path borderPath = createPath(new BorderPart(options), entry.getValue());
			slices.get(curIndex).setBorderPath(borderPath);
		}
	}
	
	private void drawRestOfTopParts() {
		for (Entry<Integer, PathBuilder> entry : topPartsForLaterDrawing.entrySet()) {
			curIndex = entry.getKey().intValue();
			TopPath topPath = createTopPath(new TopPart(options), entry.getValue());			
			topPath.topSliceIndex = curIndex; // used later in animation to get slice index
			slices.get(curIndex).setTopPath(topPath);
		}
	}
	
	private TopPath createTopPath(AbstractSlicePart part, PathBuilder pathBuilder) {
		TopPath path = new TopPath(pathBuilder);
		path.attr(currentSlice.getAttributes(part, curIndex));
		return path;
	}	
	
	private void explodeSlicesIfEnabled() {
		if (options.showExplodedSlices) {
			options.useAnimation = false;
			List<Slice> explodedSlices = new ArrayList<Slice>();
			if (options.explodeOnlyLargestSlice) {
				int maxIndex = findMaxValueIndex();
				explodedSlices.add(slices.get(maxIndex));
			} else {
				explodedSlices = slices;
			}
			showExplodeSlices(explodedSlices);			
		}
	}
	
	private int findMaxValueIndex() {
		int maxIndex = 0;
		float maxValue = 0;
		for (int i=0; i < options.values.size(); i++) {
			if (options.values.get(i).floatValue() > maxValue) {
				maxValue = options.values.get(i).floatValue();
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private void showExplodeSlices(List<Slice> explodedSlices) {
		for (Slice slice : explodedSlices) {
			float tx = slice.translateX - options.centerX;
			float ty = slice.translateY - options.centerY;
			TranslateAttributes translateAttributes = new TranslateAttributes(tx, ty);								
			animateSliceParts(slice, translateAttributes, options.animationOverSpeed);
		}
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	
	public void setLegend(Legend legend) {
		this.legend = legend;
	}

}

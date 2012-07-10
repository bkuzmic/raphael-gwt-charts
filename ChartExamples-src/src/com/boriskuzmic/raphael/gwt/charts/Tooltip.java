package com.boriskuzmic.raphael.gwt.charts;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class Tooltip extends Widget {

	private Element chartTooltipDiv;
	private Element tooltipTextSpan;
	
	public Tooltip() {
		super();
		chartTooltipDiv = DOM.createDiv();
		chartTooltipDiv.addClassName("gwt-raphaelcharts-tooltip");
		tooltipTextSpan = DOM.createSpan();
		chartTooltipDiv.appendChild(tooltipTextSpan);
		setElement(chartTooltipDiv);				
	}
	
	public int getWidth() {
		resetCss();		
		int width = chartTooltipDiv.getOffsetWidth();
		restoreCss();
		return width;
	}
	
	public int getHeight() {
		resetCss();
		int height = chartTooltipDiv.getOffsetHeight();
		restoreCss();
		return height;
	}
	
	private void resetCss() {
		DOM.setStyleAttribute(chartTooltipDiv, "display",  "block");
		DOM.setStyleAttribute(chartTooltipDiv, "visibility",  "hidden");		
	}
	
	private void restoreCss() {
		DOM.setStyleAttribute(chartTooltipDiv, "display",  "none");
		DOM.setStyleAttribute(chartTooltipDiv, "visibility",  "");
	}
	
	public void setText(String text) {
		tooltipTextSpan.setInnerHTML(text);
	}
	
	public void setPosition(float left, float top) {
		DOM.setStyleAttribute(chartTooltipDiv, "left",  String.valueOf(left) + "px");
		DOM.setStyleAttribute(chartTooltipDiv, "top",  String.valueOf(top) + "px");		
	}
	
	public void show() {
		DOM.setStyleAttribute(chartTooltipDiv,"display","block");
	}
	
	public void hide() {
		DOM.setStyleAttribute(chartTooltipDiv,"display","none");
	}

	public void setBorderColor(String color) {
		DOM.setStyleAttribute(tooltipTextSpan, "borderColor", color);		
	}
	
}

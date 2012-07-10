package com.boriskuzmic.raphael.gwt.charts;

import java.util.ArrayList;
import java.util.List;

import com.boriskuzmic.raphael.gwt.charts.AbstractChartOptions.LegendPosition;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.Widget;


public class Legend extends Widget {
	
	private Element parent;
	private int parentWidth;
	private int parentHeight;
	private DivElement legendDiv;
	private DivElement wrapperOuterDiv;
	private DivElement wrapperInnerDiv;
	private UListElement containerUL;
	private List<LIElement> itemsLI;
	
	private LegendPosition position;
	private String legendCSS = "gwt-raphaelcharts-legend";
	
	public Legend() {
		super();
		legendDiv = Document.get().createDivElement();						
		legendDiv.addClassName(legendCSS);
		
		wrapperOuterDiv = Document.get().createDivElement();
		wrapperOuterDiv.addClassName("outerContainer");
		wrapperInnerDiv = Document.get().createDivElement();
		wrapperInnerDiv.addClassName("innerContainer");
		wrapperOuterDiv.appendChild(wrapperInnerDiv);
		legendDiv.appendChild(wrapperOuterDiv);
		
		containerUL = Document.get().createULElement();
		wrapperInnerDiv.appendChild(containerUL);		
		itemsLI = new ArrayList<LIElement>();		
		setElement(legendDiv);
	}
	
	public void addItem(String itemText, String color) {
		LIElement item = Document.get().createLIElement();
		SpanElement colorHolder = Document.get().createSpanElement();	
		colorHolder.addClassName("color");
		colorHolder.getStyle().setBackgroundColor(color);
		colorHolder.setInnerHTML("&nbsp;");
		SpanElement textHolder = Document.get().createSpanElement();
		textHolder.setInnerText(itemText);				
		item.appendChild(colorHolder);
		item.appendChild(textHolder);
		containerUL.appendChild(item);
		itemsLI.add(item);
	}
	
	public void show() {
		legendDiv.getStyle().setDisplay(Display.BLOCK);		
	}
	
	public void hide() {
		legendDiv.getStyle().setDisplay(Display.NONE);		
	}
	
	public void setSizeAndPosition(Element parent, int parentWidth, int parentHeight, LegendPosition legendPosition) {
		this.parent = parent;
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;	
		this.position = legendPosition;					
		
		setPosition();				
	}		

	private int getContainerWidth() {
		int width = 0;
		width = legendDiv.getOffsetWidth();
		width += 20; // add extra space to avoid floating overlaps
		return width;
	}
	
	public void highlightItemOn(int itemIndex) {
		itemsLI.get(itemIndex).addClassName("highlight");
	}
	
	public void highlightItemOff(int itemIndex) {
		itemsLI.get(itemIndex).removeClassName("highlight");
	}	
	
	private void setPosition() {
		if (position == LegendPosition.LEFT) {
			setStyles(Float.LEFT, Float.RIGHT);			
			parent.appendChild(legendDiv);			
			recalculateParentDivSize();	
		} else if (position == LegendPosition.RIGHT) {
			setStyles(Float.RIGHT, Float.LEFT);	
			parent.appendChild(legendDiv);			
			recalculateParentDivSize();	
		} else if (position == LegendPosition.TOP) {
			parent.insertBefore(legendDiv, parent.getFirstChildElement());
		} else if (position == LegendPosition.BOTTOM ) {
			parent.appendChild(legendDiv);
		}
	}
	
	private void setStyles(Float legendStyle, Float parentStyle) {
		legendDiv.getStyle().setFloat(legendStyle);				
		parent.getFirstChildElement().getStyle().setFloat(parentStyle);
		legendDiv.getStyle().setHeight(parentHeight, Unit.PX);	
	}
	
	private void recalculateParentDivSize() {
		// resize parent so legend would be closer to chart
		parent.getStyle().setWidth(parentWidth + getContainerWidth(), Unit.PX);	
	}
	
	public void refresh() {
		setSizeAndPosition(this.parent, this.parentWidth, this.parentHeight, this.position);
	}
	
	public void setCustomCSSClass(String cssClassName) {
		legendDiv.removeClassName(legendCSS);
		legendDiv.addClassName(cssClassName);
		legendCSS = cssClassName;
	}
	
}

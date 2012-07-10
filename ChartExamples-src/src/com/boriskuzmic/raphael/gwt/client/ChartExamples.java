package com.boriskuzmic.raphael.gwt.client;

import java.util.*;

import com.boriskuzmic.raphael.gwt.charts.AbstractChartOptions.LegendPosition;
import com.boriskuzmic.raphael.gwt.charts.Legend;
import com.boriskuzmic.raphael.gwt.charts.Tooltip;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChart;
import com.boriskuzmic.raphael.gwt.charts.pie.PieChartOptions;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChartExamples implements EntryPoint {

	int width = 300;
	int height = 190;
	
	private Legend example3D_8_Legend;

	public void onModuleLoad() {
		
		final TabPanel tp2D = new TabPanel();				

		tp2D.add(example2D_1(), "Example 1");
		tp2D.add(example2D_2(), "Example 2");
		tp2D.add(example2D_3(), "Example 3");
		tp2D.add(example2D_4(), "Example 4");
		
		tp2D.selectTab(0);
		
		final TabPanel tp3D = new TabPanel();				
		
		tp3D.add(example3D_1(), "Example 1");
		tp3D.add(example3D_2(), "Example 2");
		tp3D.add(example3D_3(), "Example 3");
		tp3D.add(example3D_4(), "Example 4");
		tp3D.add(example3D_5(), "Example 5");
		tp3D.add(example3D_6(), "Example 6");
		tp3D.add(example3D_7(), "Example 7");		
		tp3D.add(example3D_8(), "Example 8");
		tp3D.add(example3D_9(), "Example 9");
		
		
		tp3D.selectTab(0);
		
		tp3D.addSelectionHandler(new SelectionHandler<Integer>(){
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 7) {
					// do legend refresh, just for this example with TabPanel
					// calculation of new width for legend is needed
					example3D_8_Legend.refresh();
				}
			}
		}); 
	

		RootPanel.get("examples2d").add(tp2D);
		RootPanel.get("examples3d").add(tp3D);	
	}
	
	private VerticalPanel example2D_1() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 1: 2D pie</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(30));
		vals.add(new Float(10));
		vals.add(new Float(15));	
		
		int width = 300;
		int height = 300;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120);
		pieChartOptions.values = vals;
		pieChartOptions.drawAs3D = false;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	private VerticalPanel example2D_2() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 2: 2D pie, one value only</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));		

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 70);
		pieChartOptions.values = vals;	
		pieChartOptions.drawAs3D = false;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);	
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	private VerticalPanel example2D_3() {		
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 3: 2D pie, one (largest) exploded slice and custom colors and background</h1>"));
		
		List<String> customColors = new ArrayList<String>();
	    customColors.add("#a00");
	    customColors.add("#0a0");
	    customColors.add("#00a");
	    
	    List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(20));
		vals.add(new Float(40));
		
		int height = 300;
		
		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120);
		pieChartOptions.values = vals;	
		pieChartOptions.colors = customColors;
		pieChartOptions.drawAs3D = false;
		pieChartOptions.showExplodedSlices = true;
		pieChartOptions.explodeOnlyLargestSlice = true;
		pieChartOptions.backgroundFill[0] = "#fff";
		pieChartOptions.backgroundFill[1] = "#999";
		
		PieChart pieChart = new PieChart(width, height, pieChartOptions);
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		return example;
	}
	
	private VerticalPanel example2D_4() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 4: 2D Pie, Custom Legend example</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));		
		
		List<String> labels = new ArrayList<String>();
		labels.add("Test 1");
		labels.add("Test 2");
		labels.add("Test 3");	
		
		List<String> customColors = new ArrayList<String>();
	    customColors.add("#369");
	    customColors.add("#639");
	    customColors.add("#963");
		
		int height = 240;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 100);
		pieChartOptions.values = vals;
		pieChartOptions.labels = labels;
		pieChartOptions.colors = customColors;
		pieChartOptions.drawAs3D = false;
		pieChartOptions.showLegend = true;	
		pieChartOptions.chartLegendPosition = LegendPosition.BOTTOM;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);				
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Legend legend = new Legend();
		legend.setCustomCSSClass("custom-legend");		
		pieChart.setLegend(legend);
		example.add(legend);
		
		return example;
	}
	
	private VerticalPanel example3D_1() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 1: default pie chart options</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(30));
		
		List<String> labels = new ArrayList<String>();
		labels.add("Slice 1");
		labels.add("Slice 2");
		labels.add("Slice 3");

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.labels = labels;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);				
		
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	private VerticalPanel example3D_2() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 2: with custom colors and different 3d size</h1>"));
		
		List<String> customColors = new ArrayList<String>();
	    customColors.add("#a00");
	    customColors.add("#0a0");
	    customColors.add("#00a");
	    
	    List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(20));
		vals.add(new Float(40));
		
		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;	
		pieChartOptions.colors = customColors;
		pieChartOptions.size3d = 10;
		
		PieChart pieChart = new PieChart(width, height, pieChartOptions);
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		return example;
	}
	
	private VerticalPanel example3D_3() {
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 3: tooltip changeable border color, value in percents</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(20));
		
		List<String> labels = new ArrayList<String>();
		labels.add("Test 1");
		labels.add("Test 2");
		labels.add("Test 3");
		labels.add("Test 4");
		
		int height = 220;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.labels = labels;
		pieChartOptions.tooltipChangeableBorderColor = true;
		pieChartOptions.tooltipShowValueInPercentage = true;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	private VerticalPanel example3D_4() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 4: larger number of values</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(30));
		vals.add(new Float(10));
		vals.add(new Float(15));
		
		int height = 220;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;		

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);		
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		return example;
	}
	
	private VerticalPanel example3D_5() {		
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 5: one value only</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));		

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;		

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);	
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	
	
	private VerticalPanel example3D_6() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 6: 3D pie, all exploded slices with rounded values option</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(30));
		vals.add(new Float(10));
		vals.add(new Float(15));	
		
		int height = 220;
				
		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.showExplodedSlices = true;
		pieChartOptions.tooltipUseRoundedValues = true;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	private VerticalPanel example3D_7() {		
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 7: 3D pie, one (largest) exploded slice</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(25));
		vals.add(new Float(10));
		vals.add(new Float(15));	
		
		int height = 220;
				
		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.showExplodedSlices = true;
		pieChartOptions.explodeOnlyLargestSlice = true;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Tooltip pieChartTooltip = new Tooltip();
		pieChart.setTooltip(pieChartTooltip);
		
		example.add(pieChartTooltip);
		return example;
	}
	
	
	
	private VerticalPanel example3D_8() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 8: Legend example</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));
		vals.add(new Float(20));
		vals.add(new Float(30));
		vals.add(new Float(10));
		vals.add(new Float(15));
		
		List<String> labels = new ArrayList<String>();
		labels.add("Slice 1");
		labels.add("Slice 2");
		labels.add("Slice 3");
		labels.add("Slice 4");
		labels.add("Slice 5");			
		
		int height = 220;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.labels = labels;		
		pieChartOptions.showLegend = true;	
		pieChartOptions.chartLegendPosition = LegendPosition.RIGHT;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);				
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Legend legend = new Legend();
		example3D_8_Legend = legend;
		pieChart.setLegend(legend);
		example.add(legend);
		
		return example;
	}
	
	private VerticalPanel example3D_9() {	
		VerticalPanel example = new VerticalPanel();
		example.add(new HTML("<h1>Example 9: Custom Legend example</h1>"));
		
		List<Float> vals = new ArrayList<Float>();
		vals.add(new Float(10));
		vals.add(new Float(30));
		vals.add(new Float(20));		
		
		List<String> labels = new ArrayList<String>();
		labels.add("Test 1");
		labels.add("Test 2");
		labels.add("Test 3");	
		
		List<String> customColors = new ArrayList<String>();
	    customColors.add("#369");
	    customColors.add("#639");
	    customColors.add("#963");
		
		int height = 220;

		PieChartOptions pieChartOptions = new PieChartOptions(width, height, 120, 70);
		pieChartOptions.values = vals;
		pieChartOptions.labels = labels;
		pieChartOptions.colors = customColors;
		pieChartOptions.showLegend = true;	
		pieChartOptions.chartLegendPosition = LegendPosition.TOP;

		PieChart pieChart = new PieChart(width, height, pieChartOptions);		
		example.add(pieChart);				
		example.setCellHorizontalAlignment(pieChart, HasHorizontalAlignment.ALIGN_CENTER);
		
		Legend legend = new Legend();
		legend.setCustomCSSClass("custom-legend");		
		pieChart.setLegend(legend);
		example.add(legend);
		
		return example;
	}
		
}

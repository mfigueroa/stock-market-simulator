package csc330sms;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import csc330sms.exchange.MarkitAPI;

/**
 * Creates a candlestick chart by leveraging JFreeChart and Markit API.
 * @author m
 *
 */
public class StockChart extends JFrame {

    public StockChart(String title, String symbol, int days) {
        super(title);
        
        final DefaultHighLowDataset dataset = (DefaultHighLowDataset)createDataset(symbol, days);
        final JFreeChart chart = createChart(dataset, title);
        chart.getXYPlot().setOrientation(PlotOrientation.VERTICAL);
        final ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new java.awt.Dimension(1000, 400));
        setContentPane(chartPanel);
    }	

    public static OHLCDataset createDataset(String symbol, int days) {
    	
    	MarkitAPI api = new MarkitAPI();
    	JSONObject json = (JSONObject)api.getHistoricalData(symbol, days);

    	JSONArray positions = (JSONArray)json.get("Positions");
    	int count = positions.size();
    	
        Date[] date = new Date[count];
        double[] high = new double[count];
        double[] low = new double[count];
        double[] open = new double[count];
        double[] close = new double[count];
        double[] volume = new double[count];
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        JSONArray dates = (JSONArray)json.get("Dates");
        JSONObject elements = (JSONObject)((JSONArray)json.get("Elements")).get(0);
        JSONObject elements2 = (JSONObject)((JSONArray)json.get("Elements")).get(1);
        
        JSONObject dataSeries = (JSONObject)elements.get("DataSeries");
        JSONObject dataSeries2 = (JSONObject)elements2.get("DataSeries");
        
        for (int i = 0; i < positions.size(); i++) {
        	// Parse date
        	String _date = dates.get(i).toString();
        	_date = _date.split("T")[0];
        	try {
				date[i] = df.parse(_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	high[i] = Double.parseDouble(((JSONArray)((JSONObject)dataSeries.get("high")).get("values")).get(i).toString());
        	low[i] = Double.parseDouble(((JSONArray)((JSONObject)dataSeries.get("low")).get("values")).get(i).toString());
        	open[i] = Double.parseDouble(((JSONArray)((JSONObject)dataSeries.get("open")).get("values")).get(i).toString());
        	close[i] = Double.parseDouble(((JSONArray)((JSONObject)dataSeries.get("close")).get("values")).get(i).toString());
        	volume[i] = Double.parseDouble(((JSONArray)((JSONObject)dataSeries2.get("volume")).get("values")).get(i).toString());
        }

        return new DefaultHighLowDataset(symbol, date, high, low, open, close, volume);

    }

    private static JFreeChart createChart(OHLCDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createCandlestickChart(
            title,
            "Date",
            "Price",
            dataset,
            true
        );
        
        XYPlot plot = (XYPlot)chart.getPlot();
        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("$0.00"));
        yAxis.setRange(dataset.getLowValue(0, 0), dataset.getHighValue(0, 0));
        
        // SMA
        XYDataset dataset2 = MovingAverage.createMovingAverage(dataset, "-MAVG", 3 * 24 * 60 * 60 * 1000L, 0L);
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, new StandardXYItemRenderer());

        return chart;
    }
}


package csu.mathapp;

import com.sun.deploy.panel.ExceptionListDialog;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GraphCommand extends Command
{
    private final String DEFAULT_DOMAIN = "[-10,10]";

    public GraphCommand()
    {
        super.setName("show graph for");
        super.setDescription("Performs the graphing action, currently supported functions: linear, quadratic. Usage: show graph for [function], [ domain: [start, end] ]. e.g. show graph for y = x, [-10,10]");
        super.updateProperty = new PropertyChangeSupport(this);
    }

    private boolean isDigit(String inQuestion)
    {
        if (inQuestion == null)
        {
            return false;
        }
        try
        {
            Integer.parseInt(inQuestion);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private String getNumericPart(String input, int start)
    {
        boolean validChar = true;
        int i = start;
        String str = "";
        while (validChar && i < input.length())
        {
            if (isDigit("" + input.charAt(i)))
            {
                str += input.charAt(i);
            }
            else
            {
                validChar = false;
            }
            i++;
        }
        return str;
    }

    private String getNumericPart(String input, int start, int end)
    {
        String integerStr = "";
        char curChar;

        for (int i = start; i < end; i++)
        {
            curChar = input.charAt(i);
            if (curChar == '-')
            {
                integerStr += curChar;
            }
            else if (isDigit("" + curChar))
            {
                integerStr += curChar;
            }
        }
        return integerStr;
    }

    private String parseFunction(String inputStr)
    {
        if(inputStr.equals("")){
            return "";
        }
        int indexFunctionStart = inputStr.indexOf('y');

        int indexFunctionEnd = inputStr.indexOf(',');
        if(indexFunctionEnd == -1)
        {
            indexFunctionEnd = inputStr.length();
        }

        String funcStr = "";

        for (int i = indexFunctionStart; i < indexFunctionEnd; i++)
        {
            funcStr += inputStr.charAt(i);
        }
        return funcStr;
    }

    private boolean isFuction(String s) {
        return Pattern.matches("show graph for y\\s?=\\s?(\\d*(\\.\\d+)?x?(\\^\\d*(\\.\\d+)?)?(/\\d?(\\.\\d+)?)?\\s?(\\+|-)?\\s?)",s);
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        //TODO implement action
        //spawnPlotter(param);
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"alert alert-warning\">Warning: The graph functionality is in development for web!</div>");
        if(param.contains("simple constant function")){
            File f = new File("./simple-constant-function.png");
            if(!f.exists()){
                double[] xData = new double[21];
                for(int i = 0; i < xData.length; i++){
                    xData[i] = i-10;
                }
                double[] yData = new double[21];
                Arrays.fill(yData, 1);
                XYChart chart = new XYChart(500, 400);
                chart.setTitle("simple constant function");
                chart.setXAxisTitle("X");
                chart.setYAxisTitle("Y");
                XYSeries series = chart.addSeries("f(x)", xData,yData);
                series.setMarker(SeriesMarkers.CIRCLE);
                try{
                    BitmapEncoder.saveBitmap(chart, f.getName(), BitmapEncoder.BitmapFormat.PNG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"text-center\"><img src=\"./"+f.getName()+"\"></div>");
        }
        else if(param.contains("simple linear function")){
            File f = new File("./simple-linear-function.png");
            if(!f.exists()){
                double[] xData = new double[21];
                for(int i = 0; i < xData.length; i++){
                    xData[i] = i-10;
                }
                double[] yData = new double[21];
                for(int i = 0; i <xData.length; i++){
                    yData[i] = xData[i];
                }
                XYChart chart = new XYChart(500, 400);
                chart.setTitle("simple linear function");
                chart.setXAxisTitle("X");
                chart.setYAxisTitle("Y");
                XYSeries series = chart.addSeries("f(x)", xData,yData);
                series.setMarker(SeriesMarkers.CIRCLE);
                try{
                    BitmapEncoder.saveBitmap(chart, f.getName(), BitmapEncoder.BitmapFormat.PNG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"text-center\"><img src=\"./"+f.getName()+"\"></div>");
        }
        else if(param.contains("simple quadratic function")){

        }
        else if(isFuction(param)){
            //System.out.println("is a function");
            this.updateProperty.firePropertyChange("numGraphs", null, 1);
        }
        else {
            //System.out.println("is not a function");
            CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"alert alert-danger\">Error: Did not recognise function, please try again.</div>");
        }

    }
}

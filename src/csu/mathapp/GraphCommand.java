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
        super.setDescription("Performs the graphing action, currently supported functions: constant, linear, quadratic. Usage: show graph for [function], [ domain: [start, end] ]. e.g. show graph for y = x, [-10,10]. Try using 'show graph for simple (constant|linear|quadratic) function'");

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

    private void createSimpleGraph(double[] xData, double[] yData, File f, String title){
        XYChart chart = new XYChart(500, 400);
        chart.setTitle(title);
        chart.setXAxisTitle("X");
        chart.setYAxisTitle("Y");
        XYSeries series = chart.addSeries("f(x)", xData,yData);
        series.setMarker(SeriesMarkers.CIRCLE);
        try{
            BitmapEncoder.saveBitmap(chart, f.getPath(), BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        //TODO implement action
        //spawnPlotter(param);
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"alert alert-warning\">Warning: The graph functionality is in development for web!</div>");
        String whatToAdd = "";
        if(param.contains("simple constant function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-constant-function.png");
            if(!f.exists()){
                double[] xData = new double[21];
                for(int i = 0; i < xData.length; i++){
                    xData[i] = i-10;
                }
                double[] yData = new double[21];
                Arrays.fill(yData, 1);
                createSimpleGraph(xData, yData, f, "simple constant function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(param.contains("simple linear function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-linear-function.png");

            if(!f.exists()){
                double[] xData = new double[21];
                for(int i = 0; i < xData.length; i++){
                    xData[i] = i-10;
                }
                double[] yData = new double[21];
                for(int i = 0; i <xData.length; i++){
                    yData[i] = xData[i];
                }
                createSimpleGraph(xData,yData,f,"simple linear function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(param.contains("simple quadratic function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-quadratic-function.png");
            if(!f.exists()){
                double[] xData = new double[21];
                for(int i = 0; i < xData.length; i++){
                    xData[i] = i-10;
                }
                double[] yData = new double[21];
                for(int i = 0; i <xData.length; i++){
                    yData[i] = xData[i]*xData[i];
                }
                createSimpleGraph(xData,yData,f,"simple quadratic function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(isFuction(param)){
            //System.out.println("is a function");
            this.updateProperty.firePropertyChange("numGraphs", null, 1);
        }
        else {
            //System.out.println("is not a function");
            whatToAdd = "<div class=\"alert alert-danger\">Error: Did not recognise function, please try again.</div>";
        }
        CoreManager.getCoreManagerInstance(sessionId).appendToBody(whatToAdd);
    }
}

package csu.mathapp;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.Scanner;

public class GraphCommand extends Command
{
    private final String DEFAULT_DOMAIN = "[-10,10]";

    public GraphCommand()
    {
        super.setName("show graph for");
        super.setDescription("Performs the graphing action and outputs and image of the graph. This command currently only support one x variable. Usage: show graph for [function], [ domain: [start, end] ]. e.g. show graph for y = x, [-10,10]. Try using 'show graph for simple (constant|linear|quadratic) function'. We are currently working on improving this command, for now please enter all terms in descending order with constants after the x variable");

        super.updateProperty = new PropertyChangeSupport(this);
    }

    private boolean isFuction(String s) {
        return Pattern.matches("show graph for y\\s?=\\s?\\d*(\\.\\d+)?x?(\\^\\d*(\\.\\d+)?)?(/\\d?(\\.\\d+)?)?\\s?((\\+|-)\\s?\\d+)?\\s?,\\s?\\[-?\\d*,\\s?-?\\d*]",s);
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

    private double[] createXData(String inputStr) {
        int xLowLimit;
        int xHighLimit;
        String domainStr;
        if(Pattern.matches("\\[-?\\d*,\\s?-?\\d*]", inputStr)){
            domainStr = inputStr.replace("[", "").replace(",", " ").replace("]", "");
        } else {
            String[] splitParam = inputStr.split(",", 2);
            domainStr = splitParam[1].replace("[", "").replace(",", "").replace("]", "");
        }
        Scanner domainScanner = new Scanner(domainStr);
        xLowLimit = domainScanner.nextInt();
        xHighLimit = domainScanner.nextInt();

        int xRange = (xHighLimit - xLowLimit) + 1;
        double[] xData = new double[xRange];
        for(int i = 0; i < xRange; i++) {
            xData[i] = xLowLimit + i;
        }
        return xData;
    }

    private double[] createYData(double[] xData, String functionPart) {
        boolean hasX = functionPart.contains("x");
        boolean hasExp = functionPart.contains("^");
        boolean hasRatio = functionPart.contains("/");
        boolean hasCoefficient = hasX && Pattern.matches("y\\s?=\\s?\\d+x.*", functionPart);
        boolean hasConstant = Pattern.matches("y\\s?=\\s?\\d*x(\\^\\d+)?(/\\d+)?\\s?(\\+|-)\\s?\\d+", functionPart);

        double coefficient = 1;
        if(hasCoefficient){
            coefficient = Double.parseDouble(functionPart.substring(functionPart.indexOf("="), functionPart.indexOf("x")).replaceAll("[^0-9]", ""));
        }
        double constant = 0;
        if(hasConstant) {
            int start = functionPart.indexOf("+");
            if(start == -1) {
                start = functionPart.indexOf("-");
            }
            constant = Double.parseDouble((functionPart.substring(start).replaceAll("[^0-9]", "")));
            if(functionPart.contains("-")) {
                constant *= -1;
            }
        }
        double exp = 1;
        if(hasExp) {
            int start = functionPart.indexOf("^");
            int stop = functionPart.length();
            if(hasRatio){
                stop = functionPart.indexOf("/");
            }
            else if(hasConstant){
                stop = functionPart.indexOf("+");
                if(stop == -1) {
                    stop = functionPart.indexOf("-");
                }
            }
            String strExp = functionPart.substring(start, stop).replaceAll("[^0-9]", "");
            exp = Double.parseDouble(functionPart.substring(start, stop).replaceAll("[^0-9]", ""));
        }

        double divisor = 1;
        if(hasRatio) {
            int start = functionPart.indexOf("/");
            int stop = functionPart.length();
            if(hasConstant) {
                stop = functionPart.indexOf("+");
                if(stop == -1) {
                    stop = functionPart.indexOf("-");
                }
            }
            divisor = Double.parseDouble(functionPart.substring(start,stop).replaceAll("[^0-9]", ""));
        }

        double[] yData = new double[xData.length];
        for(int i = 0; i < yData.length; i++) {
            yData[i] = coefficient * Math.pow(xData[i], exp) / divisor + constant;
        }

        return yData;
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        CoreManager.getCoreManagerInstance(sessionId).appendToBody(ALERT_TYPE.WARNING,"The graph functionality is in development for web!");
        String whatToAdd = "";
        if(param.contains("simple constant function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-constant-function.png");
            if(!f.exists()){
                double[] xData = createXData(DEFAULT_DOMAIN);
                double[] yData = new double[xData.length];
                Arrays.fill(yData, 1);
                createSimpleGraph(xData, yData, f, "simple constant function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(param.contains("simple linear function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-linear-function.png");

            if(!f.exists()){
                double[] xData = createXData(DEFAULT_DOMAIN);
                double[] yData = new double[xData.length];
                System.arraycopy(xData, 0, yData, 0, xData.length);
                createSimpleGraph(xData,yData,f,"simple linear function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(param.contains("simple quadratic function")){
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot() + "/simple-quadratic-function.png");
            if(!f.exists()){
                double[] xData = createXData(DEFAULT_DOMAIN);
                double[] yData = new double[xData.length];
                for(int i = 0; i <xData.length; i++){
                    yData[i] = xData[i]*xData[i];
                }
                createSimpleGraph(xData,yData,f,"simple quadratic function");
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else if(isFuction(param)){
            String functionPart = param.substring(param.indexOf("y"), param.indexOf(","));
            File f = new File(CoreManager.getCoreManagerInstance(sessionId).getRoot()+ "/" + functionPart.replace(" ", "_") + ".png");
            if(!f.exists()){
                double[] xData = createXData(param);
                double[] yData = createYData(xData, functionPart);
                createSimpleGraph(xData, yData,f ,functionPart);
            }
            whatToAdd = "<div class=\"text-center\"><img src=\""+f.getName()+"\"></div>";
        }
        else {
            whatToAdd = "Function Parse Error";
        }
        //this.updateProperty.firePropertyChange("numGraphs", null, 1);
        if(whatToAdd.equals("Function Parse Error")) {
            CoreManager.getCoreManagerInstance(sessionId).appendToBody(ALERT_TYPE.ERROR,"Did not recognise function, please try again.");
        } else {
            CoreManager.getCoreManagerInstance(sessionId).appendToBody(whatToAdd);
        }


    }
}

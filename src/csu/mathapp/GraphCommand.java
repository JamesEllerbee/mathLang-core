package csu.mathapp;

public class GraphCommand extends Command
{
    private final String DEFAULT_DOMAIN = "[-10,10]";

    public GraphCommand()
    {
        super.setName("show graph for");
        super.setDescription("Performs the graphing action, currently supported functions: linear, quadratic. Usage: show graph for [function], [ domain: [start, end] ]. e.g. show graph for y = x, [-10,10]");
        //super.updateProperty = new PropertyChangeSupport(this);
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

    @Override
    public void performAction(String param, String sessionId)
    {
        //TODO implement action
        //MainFormManager mfm = MainFormManager.getMainFormManagerInstance();
        //spawnPlotter(param);
        //this.updateProperty.firePropertyChange("numGraphs", null, 1);
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("<em>This is not currently implemented for web!</em>");
    }
}

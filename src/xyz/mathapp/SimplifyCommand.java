package xyz.mathapp;

import java.util.regex.Pattern;

public class SimplifyCommand extends Command
{

    public SimplifyCommand()
    {
        setName("simplify");
        setDescription("Simplifies the given expression. Usage: simplify [expression].");
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

    private String getCoefficient(String term)
    {
        String coefficient = "";
        boolean containsCarrot = term.contains("^");
        int stopIndex;

        if (containsCarrot)
        {
            stopIndex = term.indexOf("^");
        }
        else
        {
            stopIndex = term.length();
        }

        for (int j = 0; j < stopIndex; j++)
        {
            if (term.charAt(j) == '-')
            {
                coefficient += "-";
            }
            if (isDigit("" + term.charAt(j)))
            {
                coefficient += term.charAt(j);
            }
        }

        if (coefficient.equals("-"))
        {
            coefficient += "1";
        }
        else if (coefficient.equals(""))
        {
            coefficient += "1";
        }
        return coefficient;
    }

    private boolean isBinomialMultiplication(String str)
    {
        return Pattern.matches("(\\s*\\(\\s*[0-9]*[a-z]\\s*[+-]\\s*[0-9]+\\s*\\)){2}", str);
    }

    private String[] parseTerms(String expr)
    {
        final int NUM_TERMS = 5;
        String[] terms = new String[NUM_TERMS];
        String term = "";
        int curIndex = 0;
        for(int i = 0; i < expr.length(); i++)
        {
            char curChar = expr.charAt(i);
            if(isDigit("" + curChar))
            {
                term += "" + curChar;
            }
            else if(("" + curChar).matches("[a-z]"))
            {
                term += "" + curChar;
            }
            else if(curChar=='+'||curChar==')'||curChar=='-')
            {
                terms[curIndex] = term;
                term = "";
                if(curChar=='-')
                {
                    term = '-'+term;
                }
                curIndex++;
            }
        }

        return terms;

    }

    private char getSymbol(String expr)
    {
        char symbol = ' ';
        char curChar;
        for(int i = 0; i < expr.length(); i++)
        {
            curChar = expr.charAt(i);
            if((""+curChar).matches("[a-z]"))
            {
                symbol = curChar;
            }
        }
        return symbol;
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody(ALERT_TYPE.WARNING,"This command is not yet fully implemented!");
        String[] tokens = param.split(" ", 2);
        if (isBinomialMultiplication(tokens[1]))
        {
            cm.appendToBody("The expression you have entered is binomial multiplication");
            cm.appendToBody("Use the <b>F.O.I.L</b> method, multiply the first, outer, inner, and last.");
            cm.appendToBody("<img src=\"https://calcworkshop.com/wp-content/uploads/foil-method-formula.png\" height=\"400\" width=\"600\">");
            cm.appendToBody("Therefore (ax + b)(cx + d) expanded = acx<sup>2</sup> + adx + bcx + bd");
            String[] expr = parseTerms(tokens[1]);
            char symbol = getSymbol(tokens[1]);
            int a, b, c, d;
            a = Integer.parseInt(getCoefficient(expr[0]));
            b = Integer.parseInt(getCoefficient(expr[1]));
            c = Integer.parseInt(getCoefficient(expr[2]));
            d = Integer.parseInt(getCoefficient(expr[3]));
            cm.appendToBody(String.format("Where a = %d, b = %d, c = %d, d = %d", a,b,c,d));
            cm.appendToBody(String.format("(%d)(%d)%c<sup>2</sup> + (%d)(%d)%c + (%d)(%d)%c + (%d)(%d)",a,c,symbol,a,d,symbol,b,c,symbol,b,d));
            cm.appendToBody(String.format("%d%c<sup>2</sup> + %d%c + %d",a*c,symbol,a*d+b*c,symbol,b*d));
        }
        else
        {
            cm.appendToBody(ALERT_TYPE.ERROR,"Expression not recognized or not currently supported");
        }
    }
}

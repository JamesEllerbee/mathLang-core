package csu.mathapp;

import java.beans.PropertyChangeSupport;
import java.util.regex.Pattern;

public class SolveCommand extends Command
{

    public SolveCommand()
    {
        super.setName("solve for");
        super.setDescription("Performs the equation solver action. Usage: solve for [symbol], [equation].");
        super.updateProperty = new PropertyChangeSupport(this);
    }

    private String getExp(String term)
    {
        if (!term.contains("^"))
        {
            return "1";
        }
        StringBuilder expStr = new StringBuilder();
        for (int i = term.indexOf("^"); i < term.length(); i++)
        {
            if (isDigit("" + term.charAt(i)))
            {
                expStr.append(term.charAt(i));
            }
        }
        return expStr.toString();
    }

    /**
     * Returns true or false based on if the algorithm scans a carrot and a numeric character
     *
     * @param term the String containing the term
     * @return true or false
     */
    private boolean hasExp(String term)
    {
        return term.contains("^");
    }

    /**
     * This method returns true of a term numeric value
     *
     * @param term the String containing the value of the term
     * @return true or false
     */
    private boolean hasCoefficient(String term)
    {
        for (int i = 0; i < term.length(); i++)
        {
            if (isDigit("" + term.charAt(i)))
            {
                return true;
            }
            else if (term.charAt(i) == '^')
            {
                return false;
            }

        }
        return false;
    }

    /**
     * This method parses the tokens array to string
     *
     * @param tokens the String array containing the terms and operators
     * @return the step
     */
    private String createStepString(String[] tokens)
    {
        StringBuilder step = new StringBuilder();

        for (String ele : tokens)
        {
            if (ele != null && !ele.equals("0"))
            {
                step.append(ele);
            }
        }

        return step.toString();
    }

    private boolean hasDifferentSymbols(String[] tokens, char symbol)
    {
        //loop determines whether or not there is more than one symbol
        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i] != null)
            {
                for (int j = 0; j < tokens[i].length(); j++)
                {
                    if (!(tokens[i].charAt(j) == symbol))
                    {
                        if (!isDigit("" + tokens[i].charAt(j)) && tokens[i].charAt(j) != '=' && tokens[i].charAt(j) != '+' && tokens[i].charAt(j) != '-' && tokens[i].charAt(j) != '/' && tokens[i].charAt(j) != '^')
                        {
                            if (tokens[i].charAt(j) != ' ')
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean sameSymbolOnBothSides(String[] equation, int indexOfESign, char symbol)
    {
        for (int i = indexOfESign; i < equation.length; i++)
        {
            if (equation[i] != null)
            {
                if (equation[i].contains("" + symbol))
                {
                    return true;
                }
            }
        }
        return false;
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

    private void solveEquation(String equation, String[] tokens, char symbol, CoreManager cm)
    {
        boolean isStandardQuadraticForm = Pattern.matches("\\s*-?\\s*[0-9]*[a-z]\\^2\\s*[+-]\\s*-?\\s*[0-9]*[a-z]\\s*[+-]\\s*-?\\s*[0-9]+\\s*=\\s*0\\s*", equation) || Pattern.matches("\\s*-?[0-9]*[a-z]\\^2\\s*[+-]\\s*[0-9]*[a-z]\\s*=\\s*-?\\s*[0-9]\\s*", equation); //TODO check for equation of form ax^2 + bx + c = 0 or ax^2 + bx = c
        if (!isStandardQuadraticForm)
        {
            solveLinearEquation(tokens, symbol, cm);
        }
        else if (isStandardQuadraticForm)
        {
            solveQuadraticEquation(tokens, symbol, "standard", cm);
        }
        else
        {
            cm.appendToBody(ALERT_TYPE.ERROR, "Unable to solve in current arrangement!");
            return;
        }
        if(updateProperty!=null)
        {
            updateProperty.firePropertyChange("numEquations", null, 1);
        }
    }

    private String userDialogPrompt()
    {
        String promptStr = "What will be the result of the step?";
        String ans = "";
        while (ans.equals(""))
        {
            ans = null;
            if (ans == null)
            {
                return null;
            }
            if (!promptStr.equals("No input dectected!\n" + promptStr))
            {
                promptStr = "No input dectected!\n" + promptStr;
            }
        }
        return ans;
    }

    private boolean isCorrect(String ans, String step)
    {
        return ans.replace(" ", "").equals(step);
    }

    private String interactiveOutputString(String ans, String step, boolean hint)
    {
        String whatToAdd = "> ";
        if (ans.replace(" ", "").equals(step))
        {
            whatToAdd += "<div class=\"alert alert-success\">" + ans + ", Correct.</div>";
        }
        else
        {
            whatToAdd += "<div class=\"alert alert-danger\">" + ans + ", Not quite right." + (hint? "Hint: be sure to apply the inverse operation to both side." : "") + "Try again.</div>" ;
        }
        return whatToAdd;
    }

    private String output(String step, String decimalAns, CoreManager core)
    {
        if (!step.equals("") && core.getCurrentMode() != MODE.OUTPUT)
        {
            if (core.getCurrentMode() == MODE.INTERACTIVE)
            {
                core.getExpectedInputs().add(step);
            }
            else
            {
                core.appendToBody("Result of step: " + step + " " + decimalAns);
            }
        }
        return "";
    }

    /**
     * This method takes in the array of terms and operators in the parsed equation.
     *
     * @param tokens the array of tokens
     * @param symbol to solve the equation in terms of this symbol
     */
    private void solveLinearEquation(String[] tokens, char symbol, CoreManager cm)
    {
        //todo simplify the equation fist. order terms in descending degree, on both sides of the equal sign, respect order of operations
        int indexOfESign = -1;
        int indexOfSymbol = 0; //assuming at least one of the symbols are in the 0th index
        //boolean moreThanOneSymbol = false;
        boolean moreThanOneSymbol = hasDifferentSymbols(tokens, symbol); //set true if found a non-numeric character that isn't the main symbol, =, +, -, /, etc

        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i] != null)
            {
                if (tokens[i].equals("="))
                {
                    indexOfESign = i;
                }
            }
        }

        if (tokens[0] != null && !tokens[0].contains("" + symbol))
        {
            cm.appendToBody("There was an error processing the equation, issue: bad ordering.<br>This tool is still in early development. This is an error on the tool's end and you did nothing wrong.");
            return;
        }
        else if (moreThanOneSymbol)
        {
            cm.appendToBody("Dealing with more than one symbol not implemented yet.");
            return;
        }

        if (sameSymbolOnBothSides(tokens, indexOfESign, symbol))
        {
            cm.appendToBody("Group " + symbol + " on same side of the equation.");
            return;
        }

        int termIndex = -1;
        int i = 0;
        while (i < tokens.length && !tokens[i].equals("="))
        {
            i++;
            termIndex++;
        }

        String step = "";

        if (termIndex > 0)
        {
            int term = Integer.parseInt(tokens[termIndex]);
            tokens[termIndex] = "0";
            tokens[indexOfESign + 1] = "" + (Integer.parseInt(tokens[indexOfESign + 1]) + (term * -1));


            step = createStepString(tokens);

            if (cm.getCurrentMode() != MODE.OUTPUT)
            {
                cm.appendToBody("\tAdd " + (term * -1) + " to both sides");
            }
        }

        if (cm.getCurrentMode() != MODE.OUTPUT)
        {
            step = output(step, "", cm);
            if (step.equals("return"))
            {
                return;
            }
        }
        String decimalResult = "";
        if (tokens[0] != null && hasCoefficient(tokens[0]))
        {
            String coefficient = getCoefficient(tokens[indexOfSymbol]);

            tokens[indexOfSymbol] = "" + symbol + (hasExp(tokens[indexOfSymbol]) ? ("^" + getExp(tokens[indexOfSymbol])) : "");
            String previousTerm = "";
            if (Integer.parseInt(tokens[indexOfESign + 1]) % Integer.parseInt(coefficient) == 0)
            {
                tokens[indexOfESign + 1] = "" + (Integer.parseInt(tokens[indexOfESign + 1]) / Integer.parseInt(coefficient));
            }
            else
            {
                previousTerm = tokens[indexOfESign + 1];
                tokens[indexOfESign + 1] = "" + tokens[indexOfESign + 1] + "/" + coefficient;
            }

            step = createStepString(tokens);
            if (cm.getCurrentMode() != MODE.OUTPUT)
            {
                cm.appendToBody("\tDivide by " + coefficient + " on both sides");
            }
            decimalResult = step.contains("/") ? (" Decimal answer: " + (Integer.parseInt(previousTerm) / Double.parseDouble(coefficient))) : "";
        }

        if (cm.getCurrentMode() != MODE.OUTPUT)
        {
            step = output(step, decimalResult, cm);
            if (step.equals("return"))
            {
                return;
            }
        }

        if (tokens[0] != null && tokens[0].contains("/"))
        {
            //todo expand for division e.g. x/2
            cm.appendToBody("\tDivide both sides by coeff.");
            return;
        }

        if (cm.getCurrentMode() != MODE.OUTPUT)
        {
            step = output(step, decimalResult, cm);
            if (step.equals("return"))
            {
                return;
            }
        }

        if (tokens[0] != null && tokens[0].contains("^") && i == 2)
        {
            String expStr = getExp(tokens[indexOfSymbol]);
            int exp = Integer.parseInt(expStr);
            if (exp == 2 && cm.getCurrentMode() != MODE.OUTPUT)
            {
                cm.appendToBody("\tTake the square root of both sides of the equation");
            }
            tokens[0] = "x";
            String result = "";
            if (exp == 2)
            {
                result = "+ or - " + (Math.sqrt(Integer.parseInt(tokens[indexOfESign + 1])));
            }
            tokens[indexOfESign + 1] = result;
            step = createStepString(tokens);
        }
        output(step, "", cm);
        if (cm.getCurrentMode() == MODE.OUTPUT)
        {
            cm.appendToBody("Answer: " + step + " " + decimalResult);
        }
    }

    private String applyQuadraticFormula(int a, int b, int c)
    {
        int d = b * b - 4 * a * c;

        double x1 = (-1 * b + Math.sqrt(d)) / 2 * a;
        double x2 = (-1 * b - Math.sqrt(d)) / 2 * a;

        if (Double.isNaN(x1) && Double.isNaN(x2))
        {
            return "no real roots";
        }
        if (x1 == x2)
        {
            return "x = " + x1;
        }

        return "x = " + x1 + ", x = " + x2;
    }

    private boolean isPerfectSquare(int radicand)
    {
    /*
    Take the square root of the number.
    Take floor/ceil/round of the square root which we got in step 1.
    Subtract value we got in step 2 from the square root.
    If the output of step 3 is 0 then the number is perfect square else not.
    */
        double root = Math.sqrt(radicand);
        return (root - Math.floor(root)) == 0;
    }

    private void solveQuadraticEquation(String[] tokens, char symbol, String type, CoreManager cm)
    {
        //System.out.println("In solve quad equ method");
        if (type.equals("standard"))
        {
            int a;
            int b;
            int c;
            try
            {
                a = Integer.parseInt(getCoefficient(tokens[0]));
                b = Integer.parseInt(getCoefficient(tokens[1]));
                c = 0;
                if (!tokens[2].equals("="))
                {
                    c = Integer.parseInt(getCoefficient(tokens[2]));
                }
                else
                {
                    c = (-1 * Integer.parseInt(getCoefficient(tokens[3])));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                cm.appendToBody("An unexpected error has occurred");
                return;
            }
            cm.appendToBody("Using the quadratic formula:<br>x = (-b ± √[b<sup>2</sup> - 4ac</span>])/2a");

            if (cm.getCurrentMode() != MODE.OUTPUT)
            {
                cm.appendToBody("Substitute... a = " + a + ", b = " + b + ", c = " + c);
                cm.appendToBody("x = ( -(" + b + ") " + "± " + "√[(" + b + ")<sup>2</sup> - 4(" + a + ")(" + c + ")] ) / 2(" + a + ") )");
                int b1 = b * -1;
                int b2 = b * b;
                int a1 = 2 * a;
                int ac = 4 * a * c;
                cm.appendToBody(String.format("Simplify...<br>x = ( %d ± √[%d - %d] ) / %d", b1, b2,ac, a1));
                //System.out.println(String.format("b2 = %d, ac = %d, b2 - ac = %d", b2, ac, b2-ac));
                int radicand = b2 - ac;
                cm.appendToBody(String.format("x = ( %d ± √[%d] ) / %d", b1, radicand, a1));
                if(radicand == 0)
                {
                    cm.appendToBody(String.format("Since √[0] = 0, therefore only one real solution...<br>x = %d / %d", b1, a1));
                    cm.appendToBody("Thus x = " + (b1 / a1));
                }
                else if(isPerfectSquare(radicand))
                {
                    int root = (int)(Math.sqrt(radicand));
                    cm.appendToBody(String.format("Since √[%d] = %d and -%d, there must be two real solutions...", radicand, root, root));
                    cm.appendToBody(String.format("x = (%d + %d)/%d, (%d - %d)/%d",b1,root,a1,b1,root,a1));
                    int x1 = b1 + root;
                    int x2 = b1 - root;
                    cm.appendToBody(String.format("x = %d/%d, %d/%d", x1,a1,x2,a1));
                    x1 = x1 / a1;
                    x2 = x2 / a1;
                    cm.appendToBody(String.format("x = %d, %d", x1, x2));
                }
                else //not a perfect root
                {
                    //do sqrt stuff
                    cm.appendToBody("Non perfect square result. approximating result...");
                    cm.appendToBody("Final Result: " + applyQuadraticFormula(a, b, c));
                }
            }
            else
            {
                cm.appendToBody("Final Result: " + applyQuadraticFormula(a, b, c));
            }
        }
    }

    private String[] parseEquation(String equation, char symbol)
    {
        final int NUM_TOKENS = 10;
        String[] tokens = new String[NUM_TOKENS];

        int tokensIndex = 0;

        StringBuilder memory = new StringBuilder();
        char curChar;
        for (int i = 0; i < equation.length(); i++)
        {
            curChar = equation.charAt(i);
            if (curChar == '+' || curChar == '=')
            {
                tokens[tokensIndex] = memory.toString();
                tokensIndex++;

                memory = new StringBuilder();
                if (curChar == '=')
                {
                    tokens[tokensIndex] = "=";
                    tokensIndex++;
                }
            }
            else if (curChar == '-')
            {
                if (memory.toString().equals(""))
                {
                    memory.append(curChar);
                }
                else
                {
                    tokens[tokensIndex] = memory.toString();
                    tokensIndex++;
                    memory = new StringBuilder("-");
                }
            }
            else if (isDigit("" + curChar))
            {
                memory.append(equation.charAt(i));
            }
            else if (curChar != ' ')
            {
                //should be a symbol
                memory.append(curChar);
            }

        }
        if (!memory.toString().equals(""))
        {
            tokens[tokensIndex] = memory.toString();
        }

        return tokens;
    }

    private String findEquation(String inputString)
    {
        //first find the index of ','
        int startingIndex = 0;
        StringBuilder equationStr = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++)
        {
            if (inputString.charAt(i) == ',')
            {
                startingIndex = i;
            }
        }
        //second copy all of the contents following
        for (int j = startingIndex + 1; j < inputString.length(); j++)
        {
            char c = inputString.charAt(j);
            if (c != ' ')
            {
                equationStr.append(c);
            }
        }
        return equationStr.toString();
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        if (param.contains("="))
        {
            //this works for now, but in the future parse for a symbol until a ',' is found, probably strip whitespace
            char mainSymbol = param.charAt(this.getName().length() + 1);
            String theEquation = findEquation(param);
            cm.appendToBody("Solving for " + mainSymbol + ", given equation '" + theEquation + "'...");
            solveEquation(theEquation, parseEquation(theEquation, mainSymbol), mainSymbol, cm);
        }
        else
        {
            cm.appendToBody(ALERT_TYPE.ERROR,"Format error.");
        }
    }
}
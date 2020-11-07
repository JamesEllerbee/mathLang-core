package xyz.mathapp;

public class Step
{
    private String output;
    private String expectedInput;
    private String feedBack;

    public Step(String output, String expectedInput, String feedBack)
    {
        this.output = output;
        this.expectedInput = expectedInput;
        this.feedBack = feedBack;
    }

    public String getExpectedInput()
    {
        return expectedInput;
    }

    public String getFeedBack()
    {
        return feedBack;
    }

    public String getOutput()
    {
        return output;
    }
}

package xyz.mathapp;

public class Step
{
    private String expectedInput;
    private String feedBack;

    public Step(String expectedInput, String feedBack)
    {
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
}

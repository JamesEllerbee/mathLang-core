package csu.mathapp;

public class EverythingCommand extends Command
{
    @Override
    public void performAction(String param, String sessionId)
    {
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("42");
    }
}

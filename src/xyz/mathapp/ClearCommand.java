package xyz.mathapp;

public class ClearCommand extends Command {

    public ClearCommand()
    {
        setName("clear");
        setDescription("Use this command to clear the output text area.");
    }

    @Override
    public void performAction(String param, String sessionId) {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody("");
    }
}

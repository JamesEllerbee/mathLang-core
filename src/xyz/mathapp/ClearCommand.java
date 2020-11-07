package xyz.mathapp;

public class ClearCommand extends Command {

    public ClearCommand()
    {
        setName("clear");
        setDescription("Use this command to clear the output text area. Use while in interactive mode to break out of the command.");
    }

    @Override
    public void performAction(String param, String sessionId) {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody("");
        if(cm.getCurrentMode() == MODE.INTERACTIVE) {
            cm.getSteps().clear();
        }
    }
}

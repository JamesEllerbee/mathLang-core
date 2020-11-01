package csu.mathapp;

public class CurrentModeCommand extends Command
{

    public CurrentModeCommand()
    {
        setName("current mode");
        setDescription("Use this to display the current output mode. Usage: current mode.");
    }

    @Override
    public void performAction(String param, String sessionId) {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody(ALERT_TYPE.INFORMATION, "Current mode is <span class=\"text-monospace\">" + cm.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</span>");
    }
}

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
        CoreManager core = CoreManager.getCoreManagerInstance(sessionId);
        core.appendToBody(ALERT_TYPE.INFORMATION, "Current mode is <span class=\"text-monospace\">" + core.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</span>");
    }
}

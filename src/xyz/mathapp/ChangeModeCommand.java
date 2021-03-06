package xyz.mathapp;

public class ChangeModeCommand extends Command {

    public ChangeModeCommand()
    {
        setName("change mode");
        setDescription("Changes the output mode to the specified mode. Usage: change mode [new mode].");
    }

    @Override
    public void performAction(String param, String sessionId) {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        String[] tokens = param.split(" ", 3);
        cm.setCurrentMode(MODE.valueOf(tokens[2].toUpperCase().replace(' ', '_')));
        //core.appendToBody("<div class=\"alert alert-info\">Output mode changed to " + core.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</div>");
        cm.appendToBody(ALERT_TYPE.INFORMATION, "Output mode changed to " + cm.getCurrentMode().name().toLowerCase().replace('_', ' '));
    }
}

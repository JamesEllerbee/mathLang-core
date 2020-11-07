package xyz.mathapp;

public class  ChangeModeCommand extends Command {

    public ChangeModeCommand()
    {
        setName("change mode");
        setDescription("Changes the output mode to the specified mode. Usage: change mode [new mode].");
    }

    @Override
    public void performAction(String param, String sessionId) {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    private boolean isValidMode(String newMode) {
        return newMode.equals(MODE.INTERACTIVE.name()) || newMode.equals(MODE.OUTPUT.name()) || newMode.equals(MODE.STEP_BY_STEP.name());
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        String[] tokens = param.split(" ", 3);
        if(tokens.length == 2) {
            cm.appendToBody(ALERT_TYPE.ERROR, "No mode specified");
        } else if(!isValidMode(tokens[2].toUpperCase().replace(' ', '_'))) {
            cm.appendToBody(ALERT_TYPE.ERROR, "Not a valid mode");
        }
        else {
            cm.setCurrentMode(MODE.valueOf(tokens[2].toUpperCase().replace(' ', '_')));
            cm.appendToBody(ALERT_TYPE.INFORMATION, "Output mode changed to " + cm.getCurrentMode().name().toLowerCase().replace('_', ' '));
        }

    }
}

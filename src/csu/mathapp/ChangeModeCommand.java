package csu.mathapp;

public class ChangeModeCommand extends Command {

    public ChangeModeCommand()
    {
        setName("change mode");
        setDescription("Changes the output mode to the specified mode. Usage: change mode [new mode].");
    }

    @Override
    public void performAction(String param, String sessionId) {
        CoreManager core = CoreManager.getCoreManagerInstance(sessionId);
        String[] tokens = param.split(" ", 3);
        core.setCurrentMode(MODE.valueOf(tokens[2].toUpperCase().replace(' ', '_')));
        core.appendToBody("<strong>Output mode changed to</strong> <i>" + core.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</i>");
    }
}

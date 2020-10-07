public class ChangeModeCommand extends Command {

    public ChangeModeCommand()
    {
        setName("change mode");
        setDescription("Changes the output mode to the specified mode. Usage: change mode [new mode].");
    }

    @Override
    public void performAction(String param) {
        CoreManager core = CoreManager.getCoreManagerInstance();
        String[] tokens = param.split(" ", 3);
        core.setCurrentMode(MODE.valueOf(tokens[2].toUpperCase().replace(' ', '_')));
        core.appendToBody("<strong>Output mode changed to</strong> <i>" + core.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</i>");
    }
}

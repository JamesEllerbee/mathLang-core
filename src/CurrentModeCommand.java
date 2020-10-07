public class CurrentModeCommand extends Command
{

    public CurrentModeCommand()
    {
        setName("current mode");
        setDescription("Use this to display the current output mode. Usage: current mode.");
    }

    @Override
    public void performAction(String param) {
        CoreManager core = CoreManager.getCoreManagerInstance();
        core.appendToBody("Current mode is <i>" + core.getCurrentMode().name().toLowerCase().replace('_', ' ') + "</i>");
    }
}

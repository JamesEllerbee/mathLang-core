package csu.mathapp;

public class HelpCommand extends Command
{

    public HelpCommand()
    {
        setName("help");
        setDescription("Displays help information for all commands or a specific command. Usage: help or help [command].");
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        String[] tokens = param.split(" ", 2);
        if (tokens.length == 1)
        {
            cm.appendToBody("here are the available commands...<br>" + CommandDirectory.getCommandDescriptionsHTML());
        }
        else
        {
            cm.appendToBody(CommandDirectory.getCommandDescription(tokens[1]));
        }
    }
}

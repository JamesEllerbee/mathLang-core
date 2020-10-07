public class CommandDirectory
{
    private static Command[] commands = {
            new HelpCommand(),
            new CurrentModeCommand(),
            new ChangeModeCommand(),
            new ShowProfileCommand(),
            new SimplifyCommand(),
            new SolveCommand(), new GraphCommand(),
            new ClearCommand(),
            new QuitCommand(),
    };

    private static Command findCommand(String target)
    {
        for (Command cmd : commands)
        {
            if (target.equals(cmd.getName()))
            {
                return cmd;
            }
        }
        return null;
    }

    public static Command getCommand(String command)
    {
        String[] tokens = command.split(" ", 4);
        Command cmd = findCommand(tokens[0]);
        if (cmd == null && tokens.length >= 2)
        {
            cmd = findCommand(tokens[0] + " " + tokens[1]);
        }

        if (cmd == null && tokens.length >= 3)
        {
            cmd = findCommand(tokens[0] + " " + tokens[1] + " " + tokens[2]);
        }
        else
        {
            return cmd;
        }
        if (cmd != null)
        {
            return cmd;
        }
        System.err.println("No command for input " + command);
        return null;
    }

    public static String getCommandDescriptions()
    {
        String a = "";
        for (Command cmd : commands)
        {
            a += cmd.getName() + "\t: " + cmd.getDescription() + "\n";
        }
        return a;
    }

    public static String getCommandDescriptions(String lineTerminator)
    {
        String a = "";
        for (Command cmd : commands)
        {
            a += cmd.getName() + "\t: " + cmd.getDescription() + lineTerminator;
        }
        return a;
    }

    public static String getCommandDescriptionsHTML()
    {
        String a = "";
        for (Command cmd : commands)
        {
            a += "<b>" + cmd.getName() + "</b>" + "\t: " + cmd.getDescription() + "<br>";
        }
        return a;
    }

    public static String getCommandDescription(String target)
    {
        for (Command cmd : commands)
        {
            if (cmd.getName().equals(target))
            {
                return cmd.getName() + "\t: " + cmd.getDescription();
            }
        }
        return "No such command";
    }
}

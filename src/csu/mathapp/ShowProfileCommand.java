package csu.mathapp;

public class ShowProfileCommand extends Command
{

    public ShowProfileCommand()
    {
        setName("show profile");
        setDescription("displays profile information");
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        //TODO implement action
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("<div class=\"alert alert-warning\">Warning: The profile functionality has not been added to the web app!</div>");
    }
}

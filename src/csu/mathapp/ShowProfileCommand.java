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
        CoreManager.getCoreManagerInstance(sessionId).appendToBody(ALERT_TYPE.INFORMATION,"The profile functionality is in development for the web app!");
    }
}

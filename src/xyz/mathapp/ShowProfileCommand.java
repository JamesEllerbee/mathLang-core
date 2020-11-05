package xyz.mathapp;

public class ShowProfileCommand extends Command
{

    public ShowProfileCommand()
    {
        setName("show profile");
        setDescription("displays profile information");
        setHide(true);
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        //TODO implement action
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));

    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody(ALERT_TYPE.INFORMATION,"The profile functionality is in development for the web app!");
    }
}

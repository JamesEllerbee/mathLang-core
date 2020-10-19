package csu.mathapp;

public class ShowProfileCommand extends Command
{

    public ShowProfileCommand()
    {
        setName("show profile");
        setDescription("opens the profile window");
    }

    @Override
    public void performAction(String param, String sessionId)
    {
        //MainFormManager.getMainFormManagerInstance().getProfileForm().showProfile();
        //TODO implement action
    }
}

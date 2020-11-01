package csu.mathapp;

public class EverythingCommand extends Command
{
    public EverythingCommand() {
        super.setName("what is the answer to life, the universe, and everything?");
        super.setDescription("The ultimate question");
    }
    @Override
    public void performAction(String param, String sessionId)
    {
        performAction(param, CoreManager.getCoreManagerInstance(sessionId));
    }

    @Override
    public void performAction(String param, CoreManager cm)
    {
        cm.appendToBody("42");
    }
}

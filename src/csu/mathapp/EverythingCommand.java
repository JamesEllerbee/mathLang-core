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
        CoreManager.getCoreManagerInstance(sessionId).appendToBody("42");
    }
}

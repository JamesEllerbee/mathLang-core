public class ClearCommand extends Command {

    public ClearCommand()
    {
        setName("clear");
        setDescription("Use this command to clear the output text area.");
    }

    @Override
    public void performAction(String param) {
        //TODO refactor to CoreManager!
        //MainFormManager.getMainFormManagerInstance().appendToBody("");
    }
}

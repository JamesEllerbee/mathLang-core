/**
State manager for the math lang core project
 author: James Ellerbee
 v0.01 (2020/10/7)
 */

public class CoreManager
{
    private static CoreManager coreManagerInstance;

    public static CoreManager getCoreManagerInstance() {
        if (coreManagerInstance == null) {
            coreManagerInstance = new CoreManager();
        }
        return coreManagerInstance;
    }

    public static String[] parseInput(String input){
        //todo fix
        String[] tokens = input.split(" ", 2);
        if(tokens.length == 2) {
            return tokens;
        } else {
            return new String[]{input, ""};
        }
    }

    private MODE currentMode;

    public void setCurrentMode(MODE newMode) {
        this.currentMode = newMode;
    }

    public MODE getCurrentMode() {
        return currentMode;
    }

    private String body;

    public CoreManager() {
        currentMode = MODE.STEP_BY_STEP;
        body = "";
        appendToBody("<strong>Initializing...</strong><br><strong>Current output mode</strong>: "
                + "<i>" + currentMode.name().toLowerCase().replace('_', ' ') + "</i>.");

    }

    public void appendToBody(String whatToAdd) {
        if (whatToAdd != null)
        {
            if (whatToAdd.equals(""))
            {
                body = "Done.<br>";
            }
            else
            {
                body += whatToAdd + "<br>";
            }
        }
    }

    public String render() {
        return body;
    }
}

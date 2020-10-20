package csu.mathapp;

import java.util.HashMap;

/**
 * State manager for the math lang core project
 * author: James Ellerbee
 * v1.0 (2020/10/19)
 */

public class CoreManager
{
    final int MAX_NUM_LINE = 20;

    private static HashMap<String, CoreManager> coreManagerInstances;

    /**
     * Returns the CoreManager that coresponds to the sessionId
     * @param sessionId
     * @return the current or new CoreManager
     */
    public static CoreManager getCoreManagerInstance(String sessionId) {
        if(coreManagerInstances == null) {
            coreManagerInstances = new HashMap<String, CoreManager>();
        }

        if(sessionId != null) {
           CoreManager cm = coreManagerInstances.get(sessionId);
           if(cm == null) {
               cm = new CoreManager();
               coreManagerInstances.put(sessionId, cm);
           }
           return cm;
        }
        return null;
    }

    private MODE currentMode;

    public void setCurrentMode(MODE newMode) {
        this.currentMode = newMode;
    }

    public MODE getCurrentMode() {
        return currentMode;
    }

    private String body;

    private String root;

    private CoreManager() {
        currentMode = MODE.STEP_BY_STEP;
        body = "";

        root = "./";
        appendToBody("<strong>Initializing...</strong><br><strong>Current output mode</strong>: "
                + "<span class=\"text-monospace\">" + currentMode.name().toLowerCase().replace('_', ' ') + "</span>.<br>"
                + "<div class=\"alert alert-info\">To get start, use command 'help' to see available commands and their descriptions</div>");
    }

    public void setRoot(String root){
        this.root = root;
    }

    public String getRoot() {
        return this.root;
    }

    /**
     * Appends content to the body
     * @param whatToAdd, the string containing new content
     */
    public void appendToBody(String whatToAdd) {
        //TODO implement content limiter, split body regex=<br> if length > MAX_NUM_LINES, ignore the first n lines of the body
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

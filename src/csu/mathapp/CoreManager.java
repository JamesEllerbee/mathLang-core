package csu.mathapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * State manager for the math lang core project
 * author: James Ellerbee
 * v1.0 (2020/10/19)
 */

public class CoreManager
{
    int MAX_NUM_LINES = 16;

    private static HashMap<String, CoreManager> coreManagerInstances;

    /**
     * Returns the CoreManager that is maped with the sessionId
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

    //deprecated
    private String body;

    private List<String> lines;

    private List<String> expectedInputs;

    public List<String> getExpectedInputs() {
        return expectedInputs;
    }

    private String root;

    public String getRoot() {
        return root;
    }

    /**
     * Constructor for class
     */
    private CoreManager() {
        currentMode = MODE.STEP_BY_STEP;
        body = "";
        lines = new CircularStringList(MAX_NUM_LINES);
        expectedInputs = new ArrayList<>();
        File f = new File("./.mathappconf");
        if(f.exists()) {
            try {
                BufferedReader fw = new BufferedReader(new FileReader(f));
                root = fw.readLine().replace("\n","");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else
        {
            root = "./";
        }
        appendToBody("<strong>Initializing...</strong><br><strong>Current output mode</strong>: "
                + "<span class=\"text-monospace\">" + currentMode.name().toLowerCase().replace('_', ' ') + "</span>.<br>"
                + "<div class=\"alert alert-info\">To get start, use command 'help' to see available commands and their descriptions</div>");
    }

    /**
     * Appends content to the body
     * @param whatToAdd, the string containing new content
     */
    public void appendToBody(String whatToAdd) {
        if (whatToAdd != null)
        {
            if (whatToAdd.equals(""))
            {
                lines.removeAll(null);
                lines.add("Done.<br>");
            }
            else
            {
                lines.add(whatToAdd + "<br>");
            }
        }
    }

    public String render() {
        String[] linesArr = (String[])lines.toArray();
        StringBuilder sb = new StringBuilder();
        for(Iterator<String> i = lines.iterator(); i.hasNext();) {
                sb.append(i.next());
        }
        return sb.toString();
    }
}

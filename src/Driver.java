import csu.mathapp.Command;
import csu.mathapp.CommandDirectory;
import csu.mathapp.CoreManager;
import csu.mathapp.MODE;

import java.io.*;

public class Driver
{
    static String sessionId = "development";

    final static String head = "<head>"
            + "<title>Math Tutoring System</title>"
            + "<link rel=\"stylesheet\" href=\"./assets/style.css\">"
            + "<link rel=\"stylesheet\" href=\"./assets/bootstrap.css\">"
            + "</head>";

    final static String body = "<body class=\"theme\">"
            + "<div class=\"jumbotron\">"
            + "<h1 class=\"display-5 text-center\">MathApp.xyz!</h1>"
            + "</div>"
            + "<div class=\"main\">"
            + "%s"
            + "<br><hr>"
            + "<div class=\"d-flex justify-content-center\">"
            + "<form class=\"form-inline\" action=\"application\" method=\"post\">"
            + "<div class=\"form-group mb-2\">"
            + "<input class=\"form-control\" type=\"text\" id=\"command\" name=\"command\" </input>"
            + "</div>"
            +    "<button class=\"btn btn-primary mb-2\" type=\"submit\">Submit</button>"
            + "</form>"
            + "</div>"
            + "</body>";



    public static void main(String[] args)
    {
        //dev code
        CoreManager cm = CoreManager.getCoreManagerInstance(sessionId);
        Command cmd;

        String[] commandsToRun = null;

        File f = new File("./commandsToRun.txt");
        if(f.exists()) {
            try{
                BufferedReader bf = new BufferedReader(new FileReader(f));
                int numLines = 0;
                String line = bf.readLine();
                while(line != null) {
                    numLines++;
                    line = bf.readLine();
                }
                bf.close();
                bf = new BufferedReader(new FileReader(f));
                commandsToRun = new String[numLines];
                for(int i = 0; i < commandsToRun.length; i++) {
                    commandsToRun[i] = bf.readLine();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
             commandsToRun = new String[]{
                "help",
                "current mode",
                "show graph for ",
                "show graph for simple constant function",
                "show graph for simple linear function",
                "show graph for simple quadratic function",
                "show graph for y = 2x, [-10, 10]",

            };
        }

        for (String commandStr : commandsToRun)
        {
            if(cm.getCurrentMode() == MODE.INTERACTIVE && cm.getExpectedInputs().size() > 0)
            {
                cm.appendToBody("<img src=\"./assets/img/pencil-square.svg\" alt=\"\" width=\"32\" height=\"32\" title=\"Your Input\">> " + commandStr);
                cm.checkStep(commandStr);
            } else {
                cm.appendToBody("> " + commandStr);
                cmd = CommandDirectory.getCommand(commandStr, sessionId);
                if(cmd!=null){
                    cmd.performAction(commandStr, sessionId);
                }
            }
        }

        String toRender = head + String.format(body, cm.render());
        try {
            f = new File("./sample.html");
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            bf.write(toRender);
            bf.close();
            System.out.println("done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

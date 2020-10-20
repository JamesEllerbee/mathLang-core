import csu.mathapp.Command;
import csu.mathapp.CommandDirectory;
import csu.mathapp.CoreManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Driver
{
    static String sessionId = "development";

    final static String head = "<head>"
            + "<title>Math Tutoring System</title>"
            + "<link rel=\"stylesheet\" href=\"http://math-app.xyz/assets/style.css\">"
            + "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
            + "</head>";

    final static String body = "<body class=\"theme\">"
            + "<div class=\"jumbotron\">"
            + "<h1 class=\"display-4 text-center\">MathApp.xyz!</h1>"
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

    final static String[] commandsToRun = {
            "clear",
            "help",
            "current mode",
            "show graph for ",
            "show profile",
            "simplify garbagetext",
            "simplify x+y+z",
            "simplify (x+1)(x-1)",
            "solve for garbagetext",
            "solve for x, x-1=2",
            "invalid command input",
    };

    public static void main(String[] args)
    {
        //dev code

        CoreManager cm = CoreManager.getCoreManagerInstance(sessionId);
        Command cmd;
        for (String commandStr : commandsToRun)
        {
            cm.appendToBody("> " + commandStr);
            cmd = CommandDirectory.getCommand(commandStr, sessionId);
            if(cmd!=null){
                cmd.performAction(commandStr, sessionId);
            }
        }
        String toRender = head + String.format(body, cm.render());
        try {
            File f = new File("./sample.html");
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

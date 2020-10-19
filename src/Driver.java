import csu.mathapp.Command;
import csu.mathapp.CommandDirectory;
import csu.mathapp.CoreManager;

import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        //dev code
        CoreManager cm = CoreManager.getCoreManagerInstance();
        Scanner sc = new Scanner(System.in);
        String commandString = "";
        Command cmd;
        System.out.println("Testing...");
        while(!commandString.equals("quit")){
            System.out.printf("> ");
            commandString = sc.nextLine();
            cmd = CommandDirectory.getCommand(commandString);
            if(cmd != null){
                cmd.performAction(commandString);
            } else {
                System.out.println("command not found.");
            }
            System.out.println(cm.render());
        }
    }
}

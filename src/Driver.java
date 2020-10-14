import java.util.Scanner;

/**
This driver class is not to be included in the API, just for debugging purposes
 */
public class Driver
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String usrin = "";
        String[] tokens;
        Command cmd;
        System.out.println("Development for math tutoring system");
        CoreManager core = CoreManager.getCoreManagerInstance();
        while(!usrin.equals("quit")){
            System.out.println(core.render());
            System.out.printf("> ");
            usrin = sc.nextLine();
            cmd = CommandDirectory.getCommand(usrin);
            if(cmd != null){
                cmd.performAction(usrin);
            }
        }
    }
}

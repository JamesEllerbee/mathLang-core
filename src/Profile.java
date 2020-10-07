import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Profile implements PropertyChangeListener
{
    private String profileName;
    public String getProfileName(){return profileName;}
    private String dateProfileCreated;
    private int numEquations;
    private int numGraphs;
    private String lastGraphPlotted;
    private String mostCommonlyMissedOperation; //calculated internally
    private int numAdd;
    private int numAddMissed;
    private int numMult;
    private int numMultMissed;
    private int numRoot;
    private int numRootMissed;

    public Profile()
    {
        profileName = "n/a";
        dateProfileCreated = "n/a";
        mostCommonlyMissedOperation = "n/a";
        lastGraphPlotted = "n/a";
    }

    public Profile(String profileName)
    {
        this.profileName = profileName;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        dateProfileCreated = dtf.format(now);
        mostCommonlyMissedOperation = "none yet";
        lastGraphPlotted = "none yet";
    }

    public Profile(String profileName, String dateProfileCreated, int numEquations, int numGraphs, String mostCommonlyMissedOperation, int numAdd, int numMult, int numRoot)
    {
        this.profileName = profileName;
        this.dateProfileCreated = dateProfileCreated;
        this.numEquations = numEquations;
        this.numGraphs = numGraphs;
        this.mostCommonlyMissedOperation = mostCommonlyMissedOperation;
        this.numAdd = numAdd;
        this.numMult = numMult;
        this.numRoot = numRoot;
    }

    public void propertyChange(PropertyChangeEvent evt)
    {
        switch(evt.getPropertyName())
        {
            case "profileName":
                this.profileName = (String)(evt.getNewValue());
            break;

            case "dateProfileCreated":
                this.dateProfileCreated = (String)(evt.getNewValue());
            break;

            case "numEquations":
                this.numEquations += (int)(evt.getNewValue());
            break;

            case "numGraphs":
                this.numGraphs += (int)(evt.getNewValue());
            break;

            case "lastGraphPlotted":
                this.lastGraphPlotted = (String)(evt.getNewValue());
            break;

            case "numAdd":
                this.numAdd += (int)(evt.getNewValue());
            break;

            case "numMult":
                this.numMult += (int)(evt.getNewValue());
            break;

            case "numRoot":
                this.numRoot += (int)(evt.getNewValue());
            break;

            default:
                System.err.println("No such property " + evt.getPropertyName());
            break;
        }
    }

    public String toString()
    {
        return "Date of creation: " + dateProfileCreated + "\n" +
                "Number of equations solved: " + numEquations + "\n" +
                "Number of graphs plotted: " + numGraphs + "\n" +
                "Most commonly missed operation: " + mostCommonlyMissedOperation + "\n" +
                "Last function plotted: \'" + lastGraphPlotted + "\'";
    }
}

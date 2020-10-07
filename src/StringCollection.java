import java.util.HashMap;

public class StringCollection
{

    private HashMap<String, String> dictonary;

    public StringCollection()
    {
        dictonary = new HashMap<>();
    }

    public StringCollection(String[] keys, String[] values)
    {
        dictonary = new HashMap<>();
        for (int i = 0; i < keys.length; i++)
        {
            dictonary.put(keys[i], values[i]);
        }
    }

    public String getString(String key)
    {
        return dictonary.get(key);
    }

}

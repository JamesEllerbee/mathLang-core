package xyz.mathapp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Command implements CommandInterface
{
    private String name;
    private String description;
    boolean hide;

    protected PropertyChangeSupport updateProperty; //todo fire property changes

    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        updateProperty.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        updateProperty.removePropertyChangeListener(pcl);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isHide()
    {
        return hide;
    }

    public void setHide(boolean hide)
    {
        this.hide = hide;
    }
}

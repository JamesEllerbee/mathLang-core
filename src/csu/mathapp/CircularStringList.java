package csu.mathapp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CircularStringList implements List<String>
{
    private String[] list;
    private int currentPosition;

    public CircularStringList(int size){
        list = new String[size];
        currentPosition = 0;
    }

    @Override
    public int size()
    {
        return list.length;
    }

    @Override
    public boolean isEmpty()
    {
        return list.length==0;
    }

    @Override
    public boolean contains(Object o)
    {
        return false;
    }

    @Override
    public Iterator<String> iterator()
    {
        return null;
    }

    @Override
    public Object[] toArray()
    {
        return list;
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return null;
    }

    @Override
    public boolean add(String s)
    {
        if(currentPosition==list.length){
            currentPosition=0;
        }
        list[currentPosition++] = s;
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if(currentPosition==0) {
            currentPosition = list.length-1;
        }
        else {
            currentPosition--;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c)
    {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c)
    {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public String get(int index)
    {
        return null;
    }

    @Override
    public String set(int index, String element)
    {
        return null;
    }

    @Override
    public void add(int index, String element)
    {

    }

    @Override
    public String remove(int index)
    {
        return null;
    }

    @Override
    public int indexOf(Object o)
    {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return 0;
    }

    @Override
    public ListIterator<String> listIterator()
    {
        return null;
    }

    @Override
    public ListIterator<String> listIterator(int index)
    {
        return null;
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex)
    {
        return null;
    }
}

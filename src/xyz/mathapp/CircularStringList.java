package xyz.mathapp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CircularStringList implements List<String>
{
    private String[] list;
    private int head;

    public CircularStringList(int size){
        list = new String[size];
        head = 0;
    }

    @Override
    public int size()
    {
        return list.length;
    }

    @Override
    public boolean isEmpty()
    {
        int count = 0;
        for(String s : list){
            if(s==null){
                count++;
            }
        }
        return count==list.length;
    }

    @Override
    public boolean contains(Object o)
    {
        return false;
    }

    @Override
    public Iterator<String> iterator()
    {
        if(isEmpty()){
            return null;
        }
        return new CircularListIterator(list, head);
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

        list[head++] = s;
        if(head ==list.length){
            head =0;
        }
        list[head] = null;
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if(head ==0) {
            head = list.length-1;
        }
        else {
            head--;
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

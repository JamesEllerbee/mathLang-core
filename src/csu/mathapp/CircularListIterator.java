package csu.mathapp;

import java.util.Iterator;

public class CircularListIterator implements Iterator<String>
{
    int head;
    int curPos;
    String[] list;

    public CircularListIterator(String[] list, int head) {
        this.list = list;
        this.head = head;
        if(head==0) {
            curPos = list.length-1;
        } else {
            curPos = head - 1;
        }
    }

    @Override
    public boolean hasNext()
    {
        if(curPos<0){
            curPos = list.length - 1;
        }
        return curPos!=head&&list[curPos]!=null;
    }

    @Override
    public String next()
    {
        return list[curPos--];
    }
}

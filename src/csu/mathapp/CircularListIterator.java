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
        curPos=findStartingIndex(head);
    }

    private int findStartingIndex(int a) {
        a++;
        while(list[a]==null){
            a++;
            if(a==list.length){
                a=0;
            }
        }
        return a;
    }

    @Override
    public boolean hasNext()
    {
        if(curPos==list.length){
            curPos=0;
        }
        return list[curPos]!=null;
    }

    @Override
    public String next()
    {

        return list[curPos++];
    }
}

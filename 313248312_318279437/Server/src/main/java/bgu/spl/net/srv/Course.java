package bgu.spl.net.srv;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Course
{
    private String name;
    private int index;
    private int number;
    private int capacity;
    private int enrolled;
    private LinkedList<Integer> kdam;
    private LinkedList<String> users;

    public Course(String name, int ind, int num, int cap, LinkedList<Integer> kdam)
    {
        this.name = name;
        index = ind;
        number = num;
        capacity = cap;
        enrolled = 0;
        this.kdam = new LinkedList<>(kdam);
        users = new LinkedList<>();
    }

    public synchronized boolean isFull() {
        return (capacity - enrolled == 0);
    }        //maybe synchronized

    public synchronized void register(String name)
    {
        users.add(name);
        enrolled++;
    }

    public synchronized void unregister(String name)
    {
        users.remove(name);
        enrolled--;
    }

    public int getIndex() {
        return index;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Integer> getKdamCourse() { return kdam; }

    public synchronized int getEnrolled() {
        return enrolled;
    }


    public synchronized LinkedList<String> getUsers()            //returns sorted by name
    {
        Collections.sort(users);
        return users;
    }

}

class SortByIndex implements Comparator<bgu.spl.net.srv.Course>
{
    public int compare(bgu.spl.net.srv.Course a, bgu.spl.net.srv.Course b) { return a.getIndex() - b.getIndex(); }
}

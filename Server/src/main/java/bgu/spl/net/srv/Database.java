package bgu.spl.net.srv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database
{
    private HashMap<Integer, Course> courses;
    private HashMap<String, User> users;

    //to prevent user from creating new Database
    private Database()
    {
        courses = new HashMap<>();
        users = new HashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance()
    {
        if (DatabaseHolder.instance == null)
            DatabaseHolder.instance.initialize("./Courses.txt");
        return DatabaseHolder.instance;
    }

    private static class DatabaseHolder { private static Database instance = new Database();}

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath)
    {
        if (!courses.isEmpty())
            return false;
        List<String> lines = new LinkedList<>();
        try { lines = Files.readAllLines(Paths.get(coursesFilePath), StandardCharsets.UTF_8); }
        catch (IOException e) { return false; }
        int ind = 1;                //keeps the index in the original file
        for (String line : lines)
        {
            String[] info = line.split("\\|");
            String name = info[1];
            int num = Integer.valueOf(info[0]);
            int cap = Integer.valueOf(info[3]);
            LinkedList<Integer> kdams = toCourses(info[2]);
            courses.put(num, new Course(name,ind,num,cap,kdams));
            ind++;
        }

        return true;
    }

    private LinkedList<Integer> toCourses(String list)
    {
        LinkedList<Integer> kdams = new LinkedList<>();
        list = list.substring(1,list.length()-1);
        if(!list.isEmpty())
        {
            String[] arr = list.split(",");
            for (String s : arr)
                kdams.addLast(Integer.valueOf(s));
        }
        return kdams;
    }

    public synchronized boolean register(String name, String pass, boolean isAdmin) {
        if (users.containsKey(name))
            return false;
        User user = new User(name, pass, isAdmin);
        users.put(name, user);
        return true;
    }

    public boolean logIn(String user, String pass)
    {
        if (!users.containsKey(user))
            return false;
        return users.get(user).logIn(pass);
    }

    public boolean logOut(String user) { return users.get(user).logOut(); }

    public synchronized boolean signCourse(int num, String username)
    {
        User user = users.get(username);
        Course course = courses.get(num);
        if (course == null || (course.isFull() | isSignedCourse(username,num) != 0))
            return false;
        LinkedList<Integer> kdam = course.getKdamCourse();
        for (Integer curr : kdam)                              //checks if the user is registered to all kdams
        {
            Course c = getCourse(curr);
            if (user.isSigned(c) == 0)
                return false;
        }
        user.signCourse(course);
        return true;
    }

    public boolean unSignCourse(String name, int num)
    {
        User user = users.get(name);
        Course course = courses.get(num);
        if (course == null || user.isAdmin() || user.isSigned(course)==0)
            return false;
        user.unSignCourse(course);
        return true;
    }

    public LinkedList<Integer> kdamCheck(int num) { return getCourse(num).getKdamCourse(); }

    //for course stats
    public Course getCourse(int num) { return courses.get(num); }

    //for user stats
    public User getUser(String name) { return users.get(name); }


    //0 = not signed, 1 = signed, 2 = admin (not allowed)
    public int isSignedCourse(String name, int num)
    {
        User user = users.get(name);
        if(user.isAdmin())
            return 2;
        return user.isSigned(courses.get(num));
    }

    public String CourseStat(String user_name, int courseNum)
    {
        User user = getUser(user_name);
        Course course = getCourse(courseNum);
        if ((user == null | course == null) || !user.isAdmin())
            return null;
        String output = "Course: (" + courseNum + ") " + course.getName() + "\n";
        output += "Seats Available: " + (course.getCapacity()-course.getEnrolled()) + "/" + course.getCapacity() + "\n";
        output += "Students Registered: " + course.getUsers().toString().replaceAll(" ", "");
        return output;
    }

    public String getCourses(String user)
    {
        LinkedList<Course> list = getUser(user).getCourses();
        String output = "[";
        for (Course c : list)
            output += c.getNumber() + ",";
        if (!list.isEmpty())
            output = output.substring(0,output.length()-1);
        return output+"]";
    }

    public String studentStat(String user_name, String student_name)
    {
        User admin = getUser(user_name);
        User student = getUser(student_name);
        if (!admin.isAdmin() || student.isAdmin())
            return null;
        String output = "Student: " + student_name + "\n";
        output += "Courses: " + getCourses(student_name);
        return output;
    }


}

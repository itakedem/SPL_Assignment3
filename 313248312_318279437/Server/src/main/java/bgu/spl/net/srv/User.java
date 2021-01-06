package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    private String userName;
    private String password;
    private boolean isAdmin;
    private AtomicBoolean isLogged;
    private LinkedList<Course> courses;

    public User(String name, String pass, boolean isAdmin) {
        userName = name;
        password = pass;
        this.isAdmin = isAdmin;
        isLogged = new AtomicBoolean(false);
        this.courses = new LinkedList<>();
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isLogged() {
        return isLogged.get();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LinkedList<Course> getCourses()      //return sorted by course file indexes
    {
        courses.sort(new SortByIndex());
        return courses;
    }

    public int isSigned(Course course) {
        if(courses.contains(course))
            return 1;
        return 0;
    }

    public boolean logIn(String password) {
        if (!this.password.equals(password) | isLogged.get())
            return false;
        isLogged.set(true);
        return true;
    }

    public boolean logOut() {
        if (!isLogged.get())
            return false;
        isLogged.set(false);
        return true;
    }

    public void signCourse(Course course) {
        course.register(userName);
        courses.add(course);
    }

    public void unSignCourse(Course course) {
        course.unregister(userName);
        courses.remove(course);
    }

    public void unRegister() {
        for (Course course : courses)
            unSignCourse(course);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", isLogged=" + isLogged +
                ", courses=" + courses +
                '}';
    }
}

package bgu.spl.net.impl.communication;

import java.io.Serializable;

public class Message implements Serializable {
    private int op_code;
    private int message_op_code;
    private String user_name = null;
    private String password;
    private int course_num;
    private String student_user_name;
    private String output = "";

    //   Admin/user register & login
    public Message(int code, String user, String pass) {
        op_code = code;
        user_name = user;
        password = pass;
    }

    //logout & myCourses
    public Message(int code) {
        op_code = code;
    }

    //courseReg & kdamCheck & courseStat & isRegisteredCourse & unRegisterCourse
    public Message(int code, int course) {
        op_code = code;
        course_num = course;
    }

    //studentStat
    public Message(int code, String student) {
        op_code = code;
        student_user_name = student;
    }

    //ack & error
    public Message(int code, int message_code, String output) {
        op_code = code;
        message_op_code = message_code;
        this.output = output;
    }


    public int getOp_code() {
        return op_code;
    }

    public int getMessage_op_code() {
        return message_op_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public int getCourse_num() {
        return course_num;
    }

    public String getStudent_user_name() { return student_user_name;}

    public void setUser_name(String user_name) {this.user_name = user_name;}

    public void setPassword(String pass) {this.password = pass;}
    public String getOutput() {return output;}

    @Override
    public String toString() {
        return "Message{" +
                "op_code=" + op_code +
                ", message_op_code=" + message_op_code +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", course_num=" + course_num +
                ", student_user_name='" + student_user_name + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}

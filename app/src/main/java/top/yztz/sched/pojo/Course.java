package top.yztz.sched.pojo;

import android.database.Cursor;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import top.yztz.sched.persistence.FieldName;
import top.yztz.sched.persistence.NestField;
import top.yztz.sched.utils.ReflectUtils;
import top.yztz.sched.utils.StringUtils;


public class Course {
    public static final int NEW_COURSE = -1;

    @FieldName("cid")
    private int id;
    @FieldName("cname")
    private String name;
    @FieldName("cplace")
    private String place;
    @FieldName("cteacher")
    private String teacher;

    @NestField
    private Date date;

    public Course() {
    }

    public Course(String name, String place, Date date, String teacher) {
        this.id = NEW_COURSE;
        this.name = name;
        this.place = place;
        this.date = date;
        this.teacher = teacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void copyFrom(Course from) {
        this.name = from.name;
        this.teacher = from.teacher;
        if (null == this.date)  this.date = new Date();
        this.date.copyFrom(from.getDate());
        this.id = from.id;
        this.place = from.place;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", date=" + date +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}

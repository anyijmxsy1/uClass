package com.samon.administrator.uclass.db;

import org.litepal.crud.DataSupport;

public class Course extends DataSupport {
    private int courseId;
    private String courseName;
    private long courseSize;
    private String courseofsubjectName;

    public String getCourseofsubjectName() {
        return courseofsubjectName;
    }

    public void setCourseofsubjectName(String courseofsubjectName) {
        this.courseofsubjectName = courseofsubjectName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(long courseSize) {
        this.courseSize = courseSize;
    }
}

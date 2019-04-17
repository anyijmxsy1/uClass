package com.samon.administrator.uclass.db;

import org.litepal.crud.DataSupport;


public class Chapter extends DataSupport {
    private int ChapterId;
    private String ChapterName;
    private int CourseId;
    private long ChapterSize;
    private String ChapterOfCourseName;

    public int getChapterId() {
        return ChapterId;
    }

    public void setChapterId(int chapterId) {
        ChapterId = chapterId;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public long getChapterSize() {
        return ChapterSize;
    }

    public void setChapterSize(long chapterSize) {
        ChapterSize = chapterSize;
    }

    public String getChapterOfCourseName() {
        return ChapterOfCourseName;
    }

    public void setChapterOfCourseName(String chapterOfCourseName) {
        ChapterOfCourseName = chapterOfCourseName;
    }
}


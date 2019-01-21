package com.samon.administrator.uclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samon.administrator.uclass.util.CourseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FirstAreaFragment extends Fragment {

    /*
首页显示的课程名和图片资源
 */
    private Course[] courses = {
            new Course("math",R.drawable.math),
            new Course("physical",R.drawable.physical),
            new Course("math",R.drawable.math),
            new Course("physical",R.drawable.physical),
            new Course("math",R.drawable.math),
            new Course("physical",R.drawable.physical),
            new Course("math",R.drawable.math),
            new Course("physical",R.drawable.physical)};
    private List<Course> courseList = new ArrayList<>();
    private CourseAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_area,container,false);
        initCourse();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.firstarea_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);
        return view;
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initCourse();
//
//    }

    private void initCourse() {
        courseList.clear();
        for (int i = 0; i < courses.length; i++) {
//            Random random = new Random();
//            int index = random.nextInt(courses.length);
            courseList.add(courses[i]);

        }
    }
}

package com.samon.administrator.uclass.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.samon.administrator.uclass.Course;
import com.samon.administrator.uclass.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context mContext;
    private List<Course> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView courseImage;
        TextView courseName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            courseImage = (ImageView) view.findViewById(R.id.firstarea_course_image);
            courseName = (TextView) view.findViewById(R.id.firstarea_course_name);
        }
    }

    public CourseAdapter(List<Course> courseList){
        mCourseList = courseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.firstarea_course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = mCourseList.get(position);
        holder.courseName.setText(course.getName());
        Glide.with(mContext).load(course.getImageId()).into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}

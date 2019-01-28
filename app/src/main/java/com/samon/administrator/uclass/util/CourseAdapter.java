package com.samon.administrator.uclass.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.samon.administrator.uclass.Course;
import com.samon.administrator.uclass.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context mContext;
    private List<Course> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View courseView;
        CardView cardView;
        ImageView courseImage;
        TextView courseName;

        public ViewHolder(View view){
            super(view);
            courseView = view;
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
        final ViewHolder holder = new ViewHolder(view);
        holder.courseView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                //Toast.makeText(v.getContext(), "you clicke view "+course.getName()+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.geiliweike.com/content/%E7%89%A9%E7%90%86%E9%A2%98%E5%BA%93%EF%BC%88%E6%8F%90%E9%AB%98%E5%9E%8B%EF%BC%89"));
                        mContext.startActivity(intent);
            }
        });
        holder.courseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                //Toast.makeText(v.getContext(), "you clicke view "+course.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.geiliweike.com/content/%E9%A2%98%E5%BA%93#overlay-context=content/%25E9%25A2%2598%25E5%25BA%2593"));
                mContext.startActivity(intent);
            }
        });
        //return new ViewHolder(view);
        return holder;
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

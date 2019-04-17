package com.samon.administrator.uclass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.samon.administrator.uclass.db.Chapter;
import com.samon.administrator.uclass.db.Course;
import com.samon.administrator.uclass.db.Subject;
import com.samon.administrator.uclass.util.BosUtil;
import com.samon.administrator.uclass.util.HttpUtil;
import com.samon.administrator.uclass.util.JsonUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_SUBJECT = 0;
    public static final int LEVEL_COURSE= 1;
    public static final int LEVEL_CHAPTER= 2;
    private int currentLevel;


    private TextView titleText;
    private Button backButton;
    //NestedListView为自定义的继承ListView的类，是为了避免listView只显示的问题
    private NestedListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    private List<Subject> subjectList;
    private List<Course> courseList;
    private List<Chapter> chapterList;
    private Subject selectedSubject;
    private Course selectedCourse;
    private Chapter selectedChapter;

    private String endpoint = "http://bj.bcebos.com";
    private String ak = "EEdfea963ed6544446235cc168976715";
    private String sk = "5d1246f907a0a81cba98c06d72d9ac78";
    private String bucketName = "geiliweike";

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),R.layout.array_adapter,dataList);
        listView.setAdapter(adapter);
        return view;

    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        列表控件的子项被点击 的监听函数
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_SUBJECT){
                    selectedSubject = subjectList.get(position);
                    queryCourses();
                }else  if (currentLevel == LEVEL_COURSE){
                    selectedCourse = courseList.get(position);
                    queryChapter();
                }else if (currentLevel == LEVEL_CHAPTER){
                    selectedChapter = chapterList.get(position);
                    String selectedChapterName = "https://"+bucketName+".bj.bcebos.com/"+selectedSubject.getSubjectName()+"/"+selectedCourse.getCourseName()+"/"+selectedChapter.getChapterName();
                        Intent intent = new Intent(getActivity(),ChapterActivity.class);
                        intent.putExtra("selectedChapterName",selectedChapterName);
                        startActivity(intent);
                        getActivity().finish();
                }
            }
        });

        /*
        返回按钮被点击时监听函数
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_SUBJECT){
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else  if (currentLevel == LEVEL_COURSE){
                    querySubjects();

                }else if (currentLevel == LEVEL_CHAPTER){
                    queryCourses();
                }
            }
        });

        /*
        第一次运行碎片时要运行的方法
         */
        querySubjects();

    }

    /*
优先从数据库查询数据并显示，如果没有，从服务上查询并存入Subject数据库
 */
    private void querySubjects() {
        titleText.setText("科目");
        backButton.setVisibility(View.VISIBLE);
        //DataSupport.deleteAll(Subject.class);
        subjectList = DataSupport.findAll(Subject.class);//从数据库读取数据
        //Log.d("xsy1", "querySubjects: "+subjectList.size());
        if (subjectList.size()>0){
            dataList.clear();
            for (Subject subject:subjectList){
                dataList.add(subject.getSubjectName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_SUBJECT;
        }else {
            BosUtil.sendSubjectRequestToBos(ak,sk,endpoint,bucketName);

        }
    }

    /*
    优先从数据库查询数据并显示，如果没有，从服务上查询并存入Course数据库
     */
    private void queryCourses() {
        titleText.setText(selectedSubject.getSubjectName());
        backButton.setVisibility(View.VISIBLE);
        courseList= DataSupport.where("CourseofsubjectName = ?", String.valueOf(selectedSubject.getSubjectName())).find(Course.class);//从数据库读取数据
        if (courseList.size()>0){
            dataList.clear();
            for (Course course:courseList){
                    dataList.add(course.getCourseName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COURSE;
        }else {
            BosUtil.sendCourseRequestToBos(ak,sk,endpoint,bucketName,selectedSubject);
        }
    }

    /*
优先从数据库查询数据并显示，如果没有，从服务上查询并存入Course数据库
 */
    private void queryChapter() {
        titleText.setText(selectedCourse.getCourseName());
        backButton.setVisibility(View.VISIBLE);
        chapterList= DataSupport.where("ChapterOfCourseName = ?", String.valueOf(selectedCourse.getCourseName())).find(Chapter.class);//从数据库读取数据
        if (chapterList.size()>0){
            dataList.clear();
            for (Chapter chapter:chapterList){
                dataList.add(chapter.getChapterName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CHAPTER;
        }else {
            BosUtil.sendChapterRequestToBos(ak,sk,endpoint,bucketName,selectedCourse);
        }
    }


}

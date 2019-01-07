package com.samon.administrator.uclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
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
    private int currentLevel;


    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    private List<Subject> subjectList;
    private Subject selectedSubject;

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
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
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


            }
        });

        /*
        返回按钮被点击时监听函数
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querySubjects();
            }
        });

        /*
        第一次运行碎片时要运行的方法
         */
        querySubjects();

    }

    private void queryCourses() {
        titleText.setText("课程列表");
        backButton.setVisibility(View.VISIBLE);


    }

    /*
    优先从数据库查询数据，如果没有，从服务上查询并存入数据库
     */
    private void querySubjects() {
        titleText.setText("科目");
        backButton.setVisibility(View.GONE);
        //DataSupport.deleteAll(Subject.class);
        subjectList = DataSupport.findAll(Subject.class);//从数据库读取数据
        Log.d("xsy1", "querySubjects: "+subjectList.size());
        if (subjectList.size()>0){
            dataList.clear();
            for (Subject subject:subjectList){
                if (subject.getSize()==0){
                    dataList.add(subject.getSubjectName());
                }
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_SUBJECT;
        }else {
//            String address = "http://guolin.tech/api/china";
//            queryFromServer(address);//从服务器上读取数据并存入数据库中
//            queryFromBosServer();
            BosUtil.sendBosRequest(ak,sk,endpoint,bucketName);

        }

    }


//
//    private void queryFromBosServer() {
//
//        BosClientConfiguration config = new BosClientConfiguration();
//        config.setCredentials(new DefaultBceCredentials(ak, sk));   //您的AK/SK
//        config.setEndpoint(endpoint);    //传入Bucket所在区域域名
//        final BosClient client = new BosClient(config);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ListObjectsResponse listing = client.listObjects(bucketName);
//                for (BosObjectSummary objectSummary : listing.getContents()) {
//                    Log.d("xsy", "sendBosRequest: "+objectSummary.getKey());
//                }
//            }
//        }).start();
//    }
//
//    private void queryFromServer(String address) {
//        HttpUtil.sendOkHttpRequest(address, new Callback() {
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                boolean result = false;
//                result = JsonUtil.handleSubjectResponse(responseText);
//                if (result){
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            querySubjects();
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
}

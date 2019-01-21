package com.samon.administrator.uclass.util;

import android.text.TextUtils;
import android.util.Log;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.samon.administrator.uclass.db.Course;
import com.samon.administrator.uclass.db.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BosUtil {

    /*
    从服务器中读取部分数据，筛选条件是以size的大小为0的目录，存入本地数据库
     */
    public static void sendSubjectRequestToBos(String AccessKeyID,String SecretAccessKey,String EndPoint,final String bucketName){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessKeyID, SecretAccessKey));   //您的AK/SK
        config.setEndpoint(EndPoint);    //传入Bucket所在区域域名
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ListObjectsResponse listing = client.listObjects(bucketName);
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    if (objectSummary.getSize()==0){
                        int i = 0;
                        Subject subject = new Subject();
                        subject.setSubjectName(objectSummary.getKey());
                        subject.setSize(objectSummary.getSize());
                        subject.setId(i);
                        subject.save();//存入数据库
                        i++;
                        Log.d("xsy4", "sendBosRequest: "+subject.getSubjectName()+"id"+subject.getId());
                    }
                }
            }
        }).start();
    }

    /*
    从服务器上读取数据，筛选条件为被选中的目录字段和id号，存入本地数据库
     */
    public static void sendCourseRequestToBos(String AccessKeyID, String SecretAccessKey, String EndPoint, final String bucketName, final Subject selectedSubject) {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessKeyID, SecretAccessKey));   //您的AK/SK
        config.setEndpoint(EndPoint);    //传入Bucket所在区域域名
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ListObjectsResponse listing = client.listObjects(bucketName);
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    if (objectSummary.getKey().contains(selectedSubject.getSubjectName())){
                        Course course = new Course();
                        course.setCourseName(objectSummary.getKey());
                        course.setCourseSize(objectSummary.getSize());
                        course.setCourseId(selectedSubject.getId());
                        course.save();
                        Log.d("xsy5", "run: "+course.getCourseName()+"id"+course.getCourseId());
                    }
                }
            }
        }).start();
    }
    /*
    从服务器中读取部分数据，筛选条件是以size的大小为0的目录，存入本地数据库
     */
    public static void pushUsernameToBos(String AccessKeyID,String SecretAccessKey,String EndPoint,final String bucketName){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessKeyID, SecretAccessKey));   //您的AK/SK
        config.setEndpoint(EndPoint);    //传入Bucket所在区域域名
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ListObjectsResponse listing = client.listObjects(bucketName);
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    if (objectSummary.getSize()==0){
                        int i = 0;
                        Subject subject = new Subject();
                        subject.setSubjectName(objectSummary.getKey());
                        subject.setSize(objectSummary.getSize());
                        subject.setId(i);
                        subject.save();//存入数据库
                        i++;
                        Log.d("xsy4", "sendBosRequest: "+subject.getSubjectName()+"id"+subject.getId());
                    }
                }
            }
        }).start();
    }


}

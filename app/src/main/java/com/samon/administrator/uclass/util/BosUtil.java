package com.samon.administrator.uclass.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidubce.BceClientException;
import com.baidubce.BceServiceException;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.CreateBucketResponse;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.samon.administrator.uclass.db.Chapter;
import com.samon.administrator.uclass.db.Course;
import com.samon.administrator.uclass.db.Subject;
import com.samon.administrator.uclass.db.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


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
                String[] key;
                int i=0;
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    Subject subject = new Subject();
                    key = objectSummary.getKey().split("\\/");//正刚表达式，以“/"符号将字符串划分为几段字符串，存放在字符串数组key中。
                    if (key.length==1){
                    //if (objectSummary.getSize()==0){
                        subject.setSubjectName(key[0]);
                        //subject.setSubjectName(objectSummary.getKey());
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
                String[] key;
                int i=0;
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    key = objectSummary.getKey().split("\\/");
                    if ((key.length==2)&&(objectSummary.getKey().contains(selectedSubject.getSubjectName()))){

                        Course course = new Course();
                        course.setCourseName(key[1]);
                        //course.setCourseName(objectSummary.getKey());
                        course.setCourseSize(objectSummary.getSize());
                        course.setSubjectId(selectedSubject.getId());
                        course.setCourseofsubjectName(selectedSubject.getSubjectName());
                        course.setCourseId(i);
                        course.save();
                        i++;
                        Log.d("xsy5", "sendBosRequest: "+course.getCourseName()+"courseid"+course.getCourseId()+";subjectid"+course.getSubjectId());
                    }
                }
            }
        }).start();
    }

    /*
从服务器上读取数据，筛选条件为被选中的目录字段和id号，存入本地数据库
 */
    public static void sendChapterRequestToBos(String AccessKeyID, String SecretAccessKey, String EndPoint, final String bucketName, final Course selectedCourse) {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessKeyID, SecretAccessKey));   //您的AK/SK
        config.setEndpoint(EndPoint);    //传入Bucket所在区域域名
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ListObjectsResponse listing = client.listObjects(bucketName);
                String[] key;
                int i=0;
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    key = objectSummary.getKey().split("\\/");
                    if ((key.length==3)&&(objectSummary.getKey().contains(selectedCourse.getCourseName()))){

                        Chapter chapter = new Chapter();
                        chapter.setChapterName(key[2]);
                        chapter.setChapterSize(objectSummary.getSize());
                        chapter.setChapterId(i);
                        chapter.setCourseId(selectedCourse.getCourseId());
                        chapter.setChapterOfCourseName(selectedCourse.getCourseName());
                        chapter.save();
                        i++;
                        Log.d("xsy6", "run: "+chapter.getChapterName()+";courseid"+chapter.getCourseId()+";chapterid"+chapter.getChapterId());
                    }
                }
            }
        }).start();
    }

    /*
    将注册的用户名和密码上传到服务器
     */
    public static void BosClientPushUser(String ak, String sk, String endPoint, final String bucketName,final String path,final String fileName){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ak, sk));
        config.setEndpoint(endPoint); //Bucket所在区域
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
//                    //创建Bucket
//                    String newbucketName="uclassuser";
//                    CreateBucketResponse response = client.createBucket(newbucketName); //新建一个Bucket并指定Bucket名称
//                    Log.d("xsy4",response.getLocation());
//                    Log.d("xsy4",response.getName());
                    //上传Object
                    File file = new File(path);//上传文件的目录
                    PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, fileName, file);

                }catch (BceServiceException e) {
                    System.out.println("Error ErrorCode: " + e.getErrorCode());
                    System.out.println("Error RequestId: " + e.getRequestId());
                    System.out.println("Error StatusCode: " + e.getStatusCode());
                    System.out.println("Error Message: " + e.getMessage());
                    System.out.println("Error ErrorType: " + e.getErrorType());
                } catch (BceClientException e) {
                    System.out.println("Error Message: " + e.getMessage());
                }
            }
        }).start();

    }

    /*
    将服务器上的用户名和密码下载到本地
     */
    public static void BosClientLoadUser(String ak, String sk, String endPoint, final String bucketName,final String fileName,final String path){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ak, sk));
        config.setEndpoint(endPoint); //Bucket所在区域
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // 获取Object
                    BosObject object = client.getObject(bucketName, fileName);
                    // 获取ObjectMeta
                    ObjectMetadata meta = object.getObjectMetadata();
                    // 获取Object的输入流
                    InputStream objectContent = object.getObjectContent();
                    // 处理Object
                    FileOutputStream fos=new FileOutputStream(path);//下载文件的目录/文件名,FileOutputStrea把数据写入本地文件
                    byte[] buffer=new byte[2048];
                    int count=0;
                    while ((count=objectContent.read(buffer))>=0) {
                        fos.write(buffer,0,count);
                    }
                    // 关闭流
                    objectContent.close();
                    fos.close();
                    Log.d("xsyBosClientLoadUser",meta.getETag());
                }catch (BceServiceException e) {
                    System.out.println("Error ErrorCode: " + e.getErrorCode());
                    System.out.println("Error RequestId: " + e.getRequestId());
                    System.out.println("Error StatusCode: " + e.getStatusCode());
                    System.out.println("Error Message: " + e.getMessage());
                    System.out.println("Error ErrorType: " + e.getErrorType());
                } catch (BceClientException e) {
                    System.out.println("Error Message: " + e.getMessage());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }


}

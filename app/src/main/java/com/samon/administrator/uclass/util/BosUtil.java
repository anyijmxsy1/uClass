package com.samon.administrator.uclass.util;

import android.text.TextUtils;
import android.util.Log;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.samon.administrator.uclass.db.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BosUtil {

    public static void sendBosRequest(String AccessKeyID,String SecretAccessKey,String EndPoint,final String bucketName){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessKeyID, SecretAccessKey));   //您的AK/SK
        config.setEndpoint(EndPoint);    //传入Bucket所在区域域名
        final BosClient client = new BosClient(config);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ListObjectsResponse listing = client.listObjects(bucketName);
                String responseText = listing.getContents().toString();
//                handleBosResponse(responseText);
//                result = JsonUtil.handleSubjectResponse(responseText);
//                Log.d("xsy1", "run: "+listing.getMaxKeys());
                for (BosObjectSummary objectSummary : listing.getContents()) {
                    Subject subject = new Subject();
                    subject.setSubjectName(objectSummary.getKey());
                    subject.setSize(objectSummary.getSize());
                    subject.save();//存入数据库
                    subject.updateAll();
                    Log.d("xsy", "sendBosRequest: "+objectSummary.getKey());
                }
            }
        }).start();



    }

//    private static void handleBosResponse(String responseText) {
//        if (!TextUtils.isEmpty(responseText)){
//            try {
//                JSONArray jsonArray = new JSONArray(responseText);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Subject subject = new Subject();
//                    subject.setSubjectName(jsonObject.getString("key"));
//                    subject.save();
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}

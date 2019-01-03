package com.samon.administrator.uclass.util;

import android.text.TextUtils;

import com.samon.administrator.uclass.db.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    /*
    处理服务器返回的数据
     */
    public static boolean handleSubjectResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Subject subject = new Subject();
                    subject.setSubjectName(jsonObject.getString("name"));
                    subject.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

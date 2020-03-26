package com.midooabdaim.bonussystem.data.local.shared;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPrefrance {
    public static SharedPreferences sh;
    public static String TOTAL_BONUS = "TOTAL_BONUS";



    public static void setSharedPreferences(Activity activity) {
        if (sh == null) {
            sh = activity.getSharedPreferences(
                    "userData", activity.MODE_PRIVATE);
        }
    }

    public static void savaData(Activity activity, String data_Key, boolean data_Value) {
        if (sh != null) {
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        }
    }

    public static void savaData(Activity activity, String data_Key, String data_Value) {
        if (sh != null) {
            SharedPreferences.Editor editor = sh.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
            SharedPreferences.Editor editor = sh.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        }
    }

    public static void savaData(Activity activity, String data_Key, Float data_Value) {
        if (sh != null) {
            SharedPreferences.Editor editor = sh.edit();
            editor.putFloat(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
            SharedPreferences.Editor editor = sh.edit();
            editor.putFloat(data_Key, data_Value);
            editor.commit();
        }
    }

    public static Float loadDataFloat(Activity activity, String data_Key) {
        if (sh != null) {

        } else {
            setSharedPreferences(activity);
        }
        return (Float) sh.getFloat(data_Key, 0);
    }

    public static boolean loadDataBoolean(Activity activity, String data_Key) {
        if (sh != null) {

        } else {
            setSharedPreferences(activity);
        }
        return sh.getBoolean(data_Key, false);
    }

    public static String loadDataString(Activity activity, String data_Key) {
        if (sh != null) {

        } else {
            setSharedPreferences(activity);
        }
        return sh.getString(data_Key, "");
    }


    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sh != null) {
            SharedPreferences.Editor editor = sh.edit();
            editor.clear();
            editor.commit();
        }
    }
}
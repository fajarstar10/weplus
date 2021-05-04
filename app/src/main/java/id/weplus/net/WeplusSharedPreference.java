package id.weplus.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import id.weplus.ResponseBeranda;
import id.weplus.pemegangpolis.FormPemegangPolisModelData;

public class WeplusSharedPreference {
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static String preference_name = "weplus";
    private static Gson gson=new Gson();

    public static void saveUser(Context c, String response_login) {
        Log.d("user","save user "+response_login);
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("user", response_login);
        editor.apply();
    }

    public static String getUser(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("user", "");
        if (data != null && data.equals("")) {
            return data;
        }

        return data;
    }

    public static void saveRegister (Context c, String response_register) {
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("register", response_register);
        editor.apply();
    }

    public static String getRegister(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("register", "");
        if (data != null && data.equals("")) {
            return data;
        }
        return data;
    }

    public static void setLoggedInUSer (Context c, String response_login){
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("name", response_login);
        editor.commit();
    }

    public static String getLoggedInUser(Context c){

        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("name", "");
        if (data.equals("")){
            return data;
        }

        return data;
    }

    public static void setToken(Context c, String key) {
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("token_key", key);
        editor.apply();
    }

    public static String getToken(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("token_key", "");
        if (data.equals("")) {
            return data;
        }
        return data;
    }

    public static void setHomeBanner(Context c, String banner) {
        Gson gson = new Gson();
        String stringHomeBannerData = gson.toJson(banner);
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("home_banner", stringHomeBannerData);
        editor.commit();
    }

    public static ResponseBeranda getHomeBanner (Context c) {
        ResponseBeranda responBeranda = null;
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("home_banner", "");
        try {
            Gson gson = new Gson();
            responBeranda = gson.fromJson(data, ResponseBeranda.class);
        } catch (Exception e) {

        }

        return responBeranda ;
    }

    public static void setHomeProdukFavorite (Context c, String banner) {
        Gson gson = new Gson();
        String stringHomeBannerData = gson.toJson(banner);
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("produk_favorite", stringHomeBannerData);
        editor.commit();
    }

    public static ResponseBeranda getHomeProdukFavorite (Context c) {
        ResponseBeranda responBeranda = null;
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("home_banner", "");
        try {
            Gson gson = new Gson();
            responBeranda = gson.fromJson(data, ResponseBeranda.class);
        } catch (Exception e) {

        }

        return responBeranda ;
    }

    public static void setBuyPolis(Context c, String key) {
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("buy_polis", key);
        editor.commit();
    }

    public static String getBuyPolis(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("buy_polis", "");
        if (data.equals("")) {
            return data;
        }
        return data;
    }

    // value yang dimasukin harus string, kalo dikasih type lain, ya jelas error
    // tolong perhatikan kodingannya
    public static void setMitra(Context c, String Partnerlist) {
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("partner", Partnerlist);
        editor.commit();
    }

    public static String getMitra(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("partner", "");
        if (data.equals("")) {
            return data;
        }
        return data;
    }

    public static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean bLoggedIn){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, bLoggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context){
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static void setFormPemegangPolis(Context c, FormPemegangPolisModelData data) {
        Gson gson = new Gson();
        String stringData = gson.toJson(data);
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("form_data_pemegang_polis", stringData);
        editor.commit();
    }

    public static FormPemegangPolisModelData getDataFormPemegangPolis(Context c) {
        FormPemegangPolisModelData formPemegangPolisModelData = null;
        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("form_data_pemegang_polis", "");
        try {
            Gson gson = new Gson();
            formPemegangPolisModelData = gson.fromJson(data, FormPemegangPolisModelData.class);
        } catch (Exception e) {

        }
        return formPemegangPolisModelData;
    }

    public static void setListForm(Context c, List<FormPemegangPolisModelData> list) {
        Gson gson = new Gson();
        String stringData = gson.toJson(list);
        SharedPreferences.Editor editor = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE).edit();
        editor.putString("form_data_pemegang_polis_list", stringData);
        editor.commit();
    }

    public static List<FormPemegangPolisModelData> getListFormPemegangPolis(Context c) {
        List<FormPemegangPolisModelData> list = null;

        SharedPreferences sharedPreferences = c.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("form_data_pemegang_polis_list", "");
        try {
            Gson gson = new Gson();
            list = gson.fromJson(data, new TypeToken<List<FormPemegangPolisModelData>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

package id.weplus.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SavePreference {

    public static void setFisrtInstall(Context c, boolean value){
        SharedPreferences.Editor editor = c.getSharedPreferences("weplus", MODE_PRIVATE).edit();
            editor.putBoolean("chace-first-installation", value);
            editor.commit();
    }

    public static boolean getFirstInstall(Context c){
        SharedPreferences preferences = c.getSharedPreferences("weplus", MODE_PRIVATE);
        boolean data = preferences.getBoolean("chace-first-installation", false);
        return data;
    }

//    public static void setToken(Context c, String token_value){
//        try {
//            String cryptoHelper = CryptoHelper.encrypt(token_value);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString("chace-token", cryptoHelper);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static String getToken(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString("chace-token", "");
//        try {
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveNomorRekening(Context c, String norek){
//        try {
//            String encryp = CryptoHelper.encrypt(norek);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString("chace-nomorrekening", encryp);
//            editor.commit();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void saveUserModel(Context c, String user_model){
//        try {
//            String encryp = CryptoHelper.encrypt(user_model);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString(Extras.user_model, encryp);
//            editor.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getUserModel(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.user_model, "");
//        try{
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveUserID(Context c, String user_id){
//        try {
//            String encrypt = CryptoHelper.encrypt(user_id);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString(Extras.user_id, encrypt);
//            editor.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getUserID(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.user_id, "");
//        try {
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static String getPassword(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.password, "");
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveStatusUser(Context c, String token_model) {
//        try {
//            String encrypt = CryptoHelper.encrypt(token_model);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString(Extras.status, encrypt);
//            editor.commit();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getStatusUser(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.status, "");
//        try {
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveServerID(Context c, String serverID) {
//        try {
//            String encrypt = CryptoHelper.encrypt(serverID);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString("serverID", encrypt);
//            editor.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getServerID(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString("serverID", "");
//        try{
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveDeviceInfo(Context c, String token_model) {
//        try {
//            String encrypt = CryptoHelper.encrypt(token_model);
//        SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//        editor.putString(Extras.device_info, encrypt);
//        editor.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getDeviceInfo(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.device_info, "");
//        try{
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveEmail(Context c, String token_model) {
//        try {
//            String encrypt = CryptoHelper.encrypt(token_model);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString(Extras.email, encrypt);
//            editor.commit();
//        }catch (Exception e){
//
//        }
//    }
//
//    public static String getEmail(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.email, "");
//        try {
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
//
//    public static void saveNoCIFF(Context c, String nociff) {
//        try {
//            String encrypt = CryptoHelper.encrypt(nociff);
//            SharedPreferences.Editor editor = c.getSharedPreferences("briagro", MODE_PRIVATE).edit();
//            editor.putString(Extras.nociff, encrypt);
//            editor.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String getNoCIFF(Context c){
//        SharedPreferences preferences = c.getSharedPreferences("briagro", MODE_PRIVATE);
//        String data = preferences.getString(Extras.nociff, "");
//        try {
//            data = CryptoHelper.decrypt(data);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        if (data.equals("")) {
//            return null;
//        }
//        return data;
//    }
}

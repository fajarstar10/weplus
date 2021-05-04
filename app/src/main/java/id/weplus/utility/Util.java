package id.weplus.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Util {
    public static final String TIME_PATTERN              = "yyyy-MM-dd'T'HH:mm:ssZ";


    static public String currencyFormat(double value) {
        return Util.currencyFormat(value, "#,###");
    }

    static public String currencyFormat(double value, String pattern) {
        NumberFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }

    static public int px2dp(Context context, int value) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    }


    static public String formatDate(String dateStr){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        return outputFormat.format(date);
    }

    public static String arrayListToString(ArrayList<String> strings){
        String result = "";
        for(int i=0 ;i<strings.size();i++){
            result+=strings.get(i)+"<br>";
        }
        return result;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    public static String getCurrentDateFormatStrip(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    public static String upperCaseLetter(String text) {
        String c = (text != null)? text.trim() : "";
        String[] words = c.split(" ");
        String result = "";
        for(String w : words){
            result += (w.length() > 1? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w) + " ";
        }
        return result.trim();
    }

    public static String getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    public static String formatPhoneNumber(String phone_number){
        String result = "";
        if (phone_number.startsWith("0")){
            result = "62" + phone_number.substring(1);
        } else{
            result = phone_number;
        }

        return result;
    }

    public static String trimDoubleQuotes(String text) {
        int textLength = text.length();

        if (textLength >= 2 && text.charAt(0) == '"' && text.charAt(textLength - 1) == '"') {
            return text.substring(1, textLength - 1);
        }
        System.out.print("return trim:" + text);
        return text;
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(int dp, Activity activity) {
        Resources r = activity.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static String getRupiahFormat(String number) {
        String displayedString = "";
        if (number == null || number.length() == 0 || number.equals("null")) {
            displayedString = "Rp 0";
        } else {
            if (number.length() > 3) {
                int length = number.length();

                for (int i = length; i > 0; i -= 3) {
                    if (i > 3) {
                        String myStringPart1 = number.substring(0, i - 3);
                        String myStringPart2 = number.substring(i - 3);

                        String combinedString;

                        combinedString = myStringPart1 + ".";

                        combinedString += myStringPart2;
                        number = combinedString;

                        displayedString = "Rp " + combinedString;
                    }
                }
            } else {
                displayedString = "Rp " + number;
            }
        }
        return displayedString;
    }

    public static String getRupiahFormatIDR(String number) {
        String displayedString = "";
        if (number == null || number.length() == 0 || number.equals("null")) {
            displayedString = "IDR 0";
        } else {
            if (number.length() > 3) {
                int length = number.length();

                for (int i = length; i > 0; i -= 3) {
                    if (i > 3) {
                        String myStringPart1 = number.substring(0, i - 3);
                        String myStringPart2 = number.substring(i - 3);

                        String combinedString;

                        combinedString = myStringPart1 + ".";

                        combinedString += myStringPart2;
                        number = combinedString;

                        displayedString = "IDR " + combinedString;
                    }
                }
            } else {
                displayedString = "IDR " + number;
            }
        }
        return displayedString;
    }



    public static Date formatDateFromAPI(String time) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(time);
        stringBuilder.deleteCharAt((time.length() - 3));
        time = stringBuilder.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_PATTERN);

        Date date = simpleDateFormat.parse(time);
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String plainText) {
        String key = "D1g1t4lbr14gr0OK";
        byte[] keyByte = key.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            String encryptedString = new String(Base64.getEncoder().encode(cipherText),"UTF-8");
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt128(byte[] data) throws UnsupportedEncodingException {
        String kunci = "D1g1t4lbr14gr0OK";

        byte[] key = kunci.getBytes(StandardCharsets.UTF_8);


        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            byte[] finalIvs = new byte[16];
            int len = key.length > 16 ? 16 : key.length;
            System.arraycopy(key, 0, finalIvs, 0, len);
            IvParameterSpec ivps = new IvParameterSpec(finalIvs);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivps);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] decrypt128(byte[] data, byte[] key, byte[] ivs) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            byte[] finalIvs = new byte[16];
            int len = ivs.length > 16 ? 16 : ivs.length;
            System.arraycopy(ivs, 0, finalIvs, 0, len);
            IvParameterSpec ivps = new IvParameterSpec(finalIvs);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivps);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encryptVersi1(String message) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException {
        String kunci = "D1g1t4lbr14gr0OK"; //    4p1br146r0

        byte[] key = kunci.getBytes(StandardCharsets.UTF_8);
        byte[] srcBuff = message.getBytes("UTF8");

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(key);
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

        byte[] dstBuff = ecipher.doFinal(srcBuff);

        String base64 = android.util.Base64.encodeToString(dstBuff, android.util.Base64.DEFAULT);

        return base64;

    }

    public static int getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        String strDate = sdf.format(c.getTime());
        int dateInt = Integer.parseInt(strDate);
        return dateInt;
    }

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }



    /* Checks if external storage is available for read and write
    * (lulus tes)
    *
    * */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read
     *
      * (lulus tes)
      *
      * */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }



//    public static String getUniqueIMEIId(Context context) {
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return "";
//            }
//            String imei = telephonyManager.getDeviceId();
//            Log.e("imei", "=" + imei);
//            if (imei != null && !imei.isEmpty()) {
//                return imei;
//            } else {
//                return android.os.Build.SERIAL;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "not_found";
//    }

    /**
     * Formatting a credit card number: #### #### #### #######
     */
    public static class CreditCardNumberFormattingTextWatcher implements TextWatcher {

        private boolean lock;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (lock || s.length() == 15) {
                return;
            }
            lock = true;
            for (int i = 4; i < s.length(); i += 5) {
                if (s.toString().charAt(i) != ' ') {
                    s.insert(i, " ");
                }
            }
            lock = false;
        }
    }

    // Custom method to take screenshot
    public static Bitmap TakeScreenShot(View rootView)
    {

        // Screenshot taken for the specified root view and its child elements.
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        rootView.draw(canvas);
        return bitmap;
    }

    public static void createFolder(String fname){
        String myfolder= Environment.getExternalStorageDirectory()+"/"+fname;
        File f=new File(myfolder);
        if(!f.exists())
            if(!f.mkdir()){
            }

    }

    public  boolean isReadStoragePermissionGranted(Activity c) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (c.checkSelfPermission(READ_EXTERNAL_STORAGE)
                    == PERMISSION_GRANTED) {
                Log.v("","Permission is granted1");
                return true;
            } else {
                Log.v("","Permission is revoked1");
                ActivityCompat.requestPermissions(c, new String[]{READ_EXTERNAL_STORAGE}, 100);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted(Activity c) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (c.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
                Log.v("","Permission is granted2");
                return true;
            } else {
                Log.v("","Permission is revoked2");
                ActivityCompat.requestPermissions(c, new String[]{WRITE_EXTERNAL_STORAGE}, 200);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted2");
            return true;
        }
    }

    /**
     * create folder (tested)
     * @return
     */
    public static String createFolderDirektori(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BRIAGRO";

        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();
        return path;
    }

    /**
     *
     * @param activity
     * @param title
     *
     * take screen shot and save in folder BRIAGRO
     */
    public static void takeScreenshot(Activity activity, String title) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BRIAGRO";

        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd-hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = path + "/" + title + now + ".jpeg";

            // create bitmap screen capture
            View v1 = activity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePath = imageFile.getPath();

            Toast.makeText(activity,"Bukti tersimpan di folder BRIAGRO" , Toast.LENGTH_LONG).show();

//            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }

//        return  filePath;
    }

    private void share(Activity ac, String sharePath){


//        Log.d("ffff",sharePath);
        File file = new File(sharePath);
        Uri uri = Uri.fromFile(file);
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent .setType("image/*");
//        intent .putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(intent );


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
        shareIntent.setDataAndType(uri, ac.getContentResolver().getType(uri));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        ac.startActivity(Intent.createChooser(shareIntent, "Choose an app"));

    }


    /**
     *
     * @param c
     * @return cek whether network is available or not
     *
     */
    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

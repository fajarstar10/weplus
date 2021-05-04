package id.weplus.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkManager {

    static OkHttpClient okHttpClient;
    public static NetworkManager instance;

    public static NetworkManager getInstance(Class<NetworkService> networkServiceClass)
    {
        if(instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }
    public static NetworkService getNetworkServiceWithVersion(Context context){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("version", "2.0.0")
                                .addHeader("source", "android")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeplusConfig.DOMAIN_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }

    public static NetworkService getNetworkService(Context context){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .addInterceptor(new ChuckInterceptor(context))
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("version", "2.0.0")
                            .addHeader("source", "android")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeplusConfig.DOMAIN_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }

    public static NetworkService networkServiceCustom(Context context, final String token, final String user_id, final String pass_baru, final String deviceinfo, final String serverid, final String otp_value_token){
//        okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(60000, TimeUnit.MILLISECONDS)
//                .connectTimeout(60000, TimeUnit.MILLISECONDS)
//                .build();
//user_id, pass_baru, "mb", serverid, otp_value_token
        /**
         * ("UserId") String userId,
         *                                     @Field("NewPassword") String newPass,
         *                                     @Field("DeviceInfo") String deviceInfo,
         *                                     @Field("ServerID") String serverId,
         *                                     @Field("Token") String token);
         */
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        RequestBody formBody = new FormBody.Builder()
                                .add("UserId", user_id)
                                .add("NewPassword", pass_baru)
                                .add("DeviceInfo", deviceinfo)
                                .add("ServerID", serverid)
                                .add("Token", otp_value_token)
                                .build();
                        String postBodyString = bodyToString(request.body());
                        postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
                        request = requestBuilder
                                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8;Authorization:Bearer" + token), postBodyString))
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeplusConfig.DOMAIN_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }

    public static NetworkService getNetworkServiceWithHeader(Context context, final String token){
        OkHttpClient  client = null;
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };

//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


         client = new OkHttpClient.Builder()
                 .addInterceptor(new ChuckInterceptor(context))
                 .addInterceptor(chain -> {
             Request newRequest  = chain.request().newBuilder()
                     .addHeader("Authorization", "Bearer " + token)
                     .addHeader("Accept", "application/json")
                     .addHeader("version", "2.0.0")
                     .addHeader("source", "android")
 //                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                     .build();
             //Log.i("request",newRequest.body().toString());
             return chain.proceed(newRequest);
         }) .readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();

//        } catch (Exception e){
//
//        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeplusConfig.DOMAIN_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);

        return  networkService;
    }

    public static NetworkService getNetworkServicejustToken(Context context, final String token){

        OkHttpClient  client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {

                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                return chain.proceed(newRequest);
            }
        }).readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeplusConfig.DOMAIN_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }


    public static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }



    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {

        }
    }

}


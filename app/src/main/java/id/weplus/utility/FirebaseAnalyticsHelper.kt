package id.weplus.utility

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import id.weplus.App


class FirebaseAnalyticsHelper{
    companion object Factory{
        @JvmStatic
        fun logEvent(context: Context, event: String){
            val bundle = Bundle()
            bundle.putString("content_id", event);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, event);
            App.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }
}
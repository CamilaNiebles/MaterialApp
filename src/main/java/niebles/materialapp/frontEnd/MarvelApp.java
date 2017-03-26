package niebles.materialapp.frontEnd;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by user on 17/03/2017.
 */
public class MarvelApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}

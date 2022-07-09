package edu.neu.madcourse.firebasedemo;



import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.BuildConfig;

import edu.neu.madcourse.firebasedemo.fcm.FCMActivity;
import edu.neu.madcourse.firebasedemo.realtimedatabase.RealtimeDatabaseActivity;
import edu.neu.madcourse.firebasedemo.ShowPermActivity;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Turn on strict mode only if we're debugging
        if (BuildConfig.DEBUG) {
            // The code below is from Android docs on StrictMode.
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    //  .penaltyDeath()  uncomment this to crash if policy is violated instead of just logging
                    .build());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            extractDataFromNotification(extras);
        }

    }

    public void openFCMActivity(View view) {
        startActivity(new Intent(MainActivity.this, FCMActivity.class));
    }

    public void openDBActivity(View view) {
        startActivity(new Intent(MainActivity.this, RealtimeDatabaseActivity.class));
    }

    public void openSendNotificationActivity(View view){
        startActivity(new Intent(MainActivity.this, SendNotificationActivity.class));
    }

    public void openPermissionsActivity(View view){
        startActivity(new Intent(MainActivity.this, ShowPermActivity.class));
    }


    private void postToastMessage(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * This is the method called to extract the payload sent to the device from the server.
     * Notice that the data is present in the extras. Note that this will be executedOnly
     * when the message is received when app is in background
     * OnMessageReceived the funtion in the MEssagingservice class is the function called when
     * the message is received and the app is in foreground
     * @param extras : The payload is part of bundle
     */
    private void extractDataFromNotification(Bundle extras){

        String dataTitle = extras.getString("title","Nothing");
        String dataContent = extras.getString("content","Nothing");
        postToastMessage("Received : "+ dataTitle + " "+ dataContent);
    }

}

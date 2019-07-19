package com.nstudio.temigreetings.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nstudio.temigreetings.adapter.MenuAdapter;
import com.nstudio.temigreetings.R;
import com.nstudio.temigreetings.windows.ListLocationsWindow;
import com.nstudio.temigreetings.windows.SaveLocationWindow;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener {

    private TextView tvGreetings,tvMessage;
    private Context context;
    private Robot robot;
    private ListLocationsWindow listLocationsWindow;
    private SaveLocationWindow saveLocationWindow;


    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = HomeActivity.this;

        tvGreetings = findViewById(R.id.tvGreetings);
        tvMessage = findViewById(R.id.tvMessage);

        RecyclerView rvOptions = findViewById(R.id.rvOptions);


        String[] titles = {"Navigate To","Save This Location","Follow Me"};
        int[] icons = {R.drawable.img_nav,R.drawable.img_location_add,R.drawable.img_eye};

        MenuAdapter.OnMenuOptionClickListener clickListener = new MenuAdapter.OnMenuOptionClickListener() {
            @Override
            public void onClick(String action, MenuAdapter.MyViewHolder holder) {
                switch (action){
                    case "Navigate To":

                        showLocationListWindow();

                        break;
                    case "Save This Location":

                        showLocationSaveWindow();

                        break;

                    case "Follow Me":

                        robot.beWithMe();
                        Toast.makeText(context,"Following...",Toast.LENGTH_SHORT).show();

                        break;

                }
            }
        };


        rvOptions.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));

        MenuAdapter adapter = new MenuAdapter(icons, titles, clickListener);

        rvOptions.setAdapter(adapter);

        robot = Robot.getInstance();

    }

    private void showLocationListWindow() {
        List<String> locations = robot.getLocations();
        if (locations!=null && locations.size()==0){
            speak("You did not saved any location, Please navigate me to some places and save locations");
            return;
        }
        if (listLocationsWindow==null){


            ListLocationsWindow.OnLocationSelectListener selectListener = new ListLocationsWindow.OnLocationSelectListener() {
                @Override
                public void onLocationSelect(String name) {
                    robot.goTo(name);
                    listLocationsWindow.dismiss();
                }
            };

            listLocationsWindow = new ListLocationsWindow(context,locations,selectListener);
            listLocationsWindow.show();

            return;
        }

        listLocationsWindow.show();

    }

    private void showLocationSaveWindow() {


        if (saveLocationWindow == null){
            SaveLocationWindow.OnLocationSaveListener saveListener = new SaveLocationWindow.OnLocationSaveListener() {
                @Override
                public void onSave(String name) {
                    robot.saveLocation(name);

                    saveLocationWindow.dismiss();
                }
            };

            saveLocationWindow = new SaveLocationWindow(context,saveListener);
            saveLocationWindow.show();

            return;
        }

        saveLocationWindow.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speak("Hi, I'm Temi! May I help you");
            }
        },1000);
    }

    public void speak(String text) {
        TtsRequest ttsRequest = TtsRequest.create(text,true);
        robot.speak(ttsRequest);
    }


     /*
        Setting up all the event listeners
     */

    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addOnBeWithMeStatusChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addConversationViewAttachesListenerListener(this);
        robot.addWakeupWordListener(this);
        robot.addTtsListener(this);
        robot.addOnLocationsUpdatedListener(this);
    }

    /*
        Removing the event listeners upon leaving the app.
     */

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnRobotReadyListener(this);
        robot.removeNlpListener(this);
        robot.removeOnBeWithMeStatusChangedListener(this);
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeConversationViewAttachesListenerListener(this);
        robot.removeWakeupWordListener(this);
        robot.removeTtsListener(this);
        robot.removeOnLocationsUpdateListener(this);
    }


    @Override
    public void onPublish(ActivityStreamPublishMessage activityStreamPublishMessage) {

    }

    @Override
    public void onConversationAttaches(boolean b) {

    }

    @Override
    public void onNlpCompleted(NlpResult nlpResult) {

    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {

    }

    @Override
    public void onWakeupWord(String s) {

    }

    @Override
    public void onBeWithMeStatusChanged(String s) {

    }

    @Override
    public void onGoToLocationStatusChanged(String location, String status) {
        switch (status) {
            case "start":
                robot.speak(TtsRequest.create("Starting", false));
                break;

            case "calculating":
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case "going":
                speak("Navigating to "+location+". Follow me!");

                break;

            case "complete":
                speak("We have Reached on "+location);
                break;

            case "abort":
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }

    @Override
    public void onLocationsUpdated(List<String> locations) {

        if (locations!=null && locations.size()>0){
            Log.e("Locations","Locations updated :\n" + locations);
            speak("I've successfully saved the " + locations.get(locations.size()-1) + " location.");
        }
    }

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

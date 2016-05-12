package com.jaymalabs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;

/**
 * Created by E134292 on 5/9/2016.
 */
public class ExerciseActivity extends AppCompatActivity{

    private TextView mIdText;
    private TextView mNameText;
    private TextView mTimeText;
    private TextView mRepsText;
    private TextView mNextExercise;
    private ProgressBar mWorkoutProgress;
    private int mExerciseIndex = 0;
    private int mExerciseTime;
    private long mStartTime = 0;
    private ExerciseLab exerciseLab;
    List<Exercise> exerciseList;
    private boolean mBreakNext;
    private boolean mActivityRestore;
    private Button pause;
    private int mSeconds;

    private SharedPreferences mPrefs;

    static final int SAVED_SECONDS = 0;

    private final String LOG_TAG = ExerciseActivity.class.getSimpleName();

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            long millis = System.currentTimeMillis() - mStartTime;
            mSeconds = (int) (millis / 1000);
//            int minutes = seconds / 60;
            mSeconds = mExerciseTime - mSeconds;

            mTimeText.setText(String.format("%2d", mSeconds));
            timerHandler.postDelayed(this, 50);

            Log.v(LOG_TAG, String.valueOf(mSeconds));

            if(mSeconds <= 0) {
                if (mExerciseIndex == exerciseList.size()-1) {
                    Log.v(LOG_TAG, "CLOSE THE ACTIVITY");
                    closeActivity();
                }
                else {
                    if(mBreakNext) {
                        mWorkoutProgress.setProgress(mWorkoutProgress.getProgress() + 20);
                        updateUI(0);
                        mStartTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);
                        mBreakNext = false;
                        Log.v(LOG_TAG, "---------------Break Next--------------------");
                    }
                    else {
                        mWorkoutProgress.setProgress(mWorkoutProgress.getProgress() + 5);
                        mExerciseIndex++;
                        mBreakNext = true;
                        updateUI(mExerciseIndex);
                        Log.v(LOG_TAG, "---------------Else--------------------");
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "ON CREATE");

        setContentView(R.layout.activity_exercise);

        if( savedInstanceState != null ) {
            mExerciseTime = savedInstanceState.getInt("seconds");
            mBreakNext = savedInstanceState.getBoolean("break_next");
            mActivityRestore = true;
        }

        //get current screen orientation
        //create lock orientation button and set orientation accordingly
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        exerciseLab = ExerciseLab.get(this);
        exerciseList = exerciseLab.getExercises();

        mIdText = (TextView) findViewById(R.id.exercise_id);
        mNameText = (TextView) findViewById(R.id.exercise_name);
        mTimeText = (TextView) findViewById(R.id.exercise_time);
        mRepsText = (TextView) findViewById(R.id.exercise_reps);
        mNextExercise = (TextView) findViewById(R.id.next_exercise);

        pause = (Button) findViewById(R.id.button4);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerHandler.removeCallbacks(timerRunnable);
            }
        });
        mWorkoutProgress = (ProgressBar) findViewById(R.id.progressBar);
        updateUI(mExerciseIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "ON RESUME");
        timerHandler.postDelayed(timerRunnable, 50);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "ON PAUSE");
        timerHandler.removeCallbacks(timerRunnable);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.v(LOG_TAG, "ON STOP");
//        timerHandler.removeCallbacks(timerRunnable);
//    }

    private void updateUI(int exerciseNum) {
        Log.v(LOG_TAG, "UPDATE UI");
        Exercise exercise = exerciseLab.getExercise(exerciseNum);

        mIdText.setText(String.valueOf(exercise.getId()));
        mNameText.setText(exercise.getName());
        if (mActivityRestore) {
            mActivityRestore = false;
        }
        else{
            mExerciseTime = (exercise.getTime());
        }
        mRepsText.setText(String.valueOf(exercise.getReps()));
        if (exerciseNum == 0) {
            Exercise nextExercise = exerciseLab.getExercise(mExerciseIndex + 1);
            mNextExercise.setText("Next Exercise: " + nextExercise.getName());
        }
        else {
            mNextExercise.setText("");
        }
        mStartTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 50);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", mSeconds);
        savedInstanceState.putBoolean("break_next", mBreakNext);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void closeActivity() {
        this.finish();
    }
}

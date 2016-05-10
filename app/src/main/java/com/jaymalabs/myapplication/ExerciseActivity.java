package com.jaymalabs.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
    private Button mNextButton;
    private ProgressBar mWorkoutProgress;
    private int mExerciseIndex = 0;
    private int mExerciseTime;
    private long mStartTime = 0;
    private ExerciseLab exerciseLab;
    List<Exercise> exerciseList;
    Exercise exercise;
    private boolean mBreakNext;
//    private Timer timer;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - mStartTime;
            int seconds = (int) (millis / 1000);
//            int minutes = seconds / 60;
            seconds = mExerciseTime - seconds;

            mTimeText.setText(String.format("%2d", seconds));
            timerHandler.postDelayed(this, 50);

            if(seconds <= 0) {

                if(mBreakNext) {
                    mIdText.setText("-1");
                    mNameText.setText("Break");
                    mExerciseTime = 10;
                    mRepsText.setText("1");
                    mWorkoutProgress.setProgress((mExerciseIndex + 1) *20);
                    mStartTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    mBreakNext = false;
                }
                else {
                    mExerciseIndex++;

                    if (mExerciseIndex > exerciseList.size()-1) {
                        mExerciseIndex = 0;
                    }
                    mBreakNext = true;
                    updateUI();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        mBreakNext = true;

        exerciseLab = ExerciseLab.get(this);
        exerciseList = exerciseLab.getExercises();

        mIdText = (TextView) findViewById(R.id.exercise_id);
        mNameText = (TextView) findViewById(R.id.exercise_name);
        mTimeText = (TextView) findViewById(R.id.exercise_time);
        mRepsText = (TextView) findViewById(R.id.exercise_reps);
        mWorkoutProgress = (ProgressBar) findViewById(R.id.progressBar);
        updateUI();
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something
                mExerciseIndex++;

                if (mExerciseIndex > exerciseList.size()-1) {
                    mExerciseIndex = 0;
                }
                updateUI();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void updateUI() {
        exercise = exerciseLab.getExercise(mExerciseIndex);

        mIdText.setText(String.valueOf(exercise.getId()));
        mNameText.setText(exercise.getName());
        mExerciseTime = (exercise.getTime());
        mRepsText.setText(String.valueOf(exercise.getReps()));
        mStartTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }
}

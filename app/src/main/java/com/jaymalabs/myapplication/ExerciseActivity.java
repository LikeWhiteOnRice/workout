package com.jaymalabs.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

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
    private ExerciseLab exerciseLab;
    List<Exercise> exerciseList;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

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
    }

    private void updateUI() {
        exercise = exerciseLab.getExercise(mExerciseIndex);

        mIdText.setText(String.valueOf(exercise.getId()));
        mNameText.setText(exercise.getName());
        mTimeText.setText(String.valueOf(exercise.getTime()));
        mRepsText.setText(String.valueOf(exercise.getReps()));
        mWorkoutProgress.setProgress((mExerciseIndex + 1) *20);
    }
}

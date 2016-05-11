package com.jaymalabs.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E134292 on 5/6/2016.
 */
public class ExerciseLab {

    private static ExerciseLab sExerciseLab;
    private List<Exercise> mExercises;

    public static ExerciseLab get(Context context) {
        if(sExerciseLab == null) {
            sExerciseLab = new ExerciseLab(context);
        }
        return sExerciseLab;
    }

    private ExerciseLab (Context context) {
        mExercises = new ArrayList<>();

        Exercise exercise0 = new Exercise();
        exercise0.setId(0);
        exercise0.setName("Get Ready");
        exercise0.setTime(5);
        exercise0.setReps(1);
        //exercise0.setStartImage();
        //exercise0.setEndImage();
        mExercises.add(exercise0);

        Exercise exercise1 = new Exercise();
        exercise1.setId(1);
        exercise1.setName("Crunches");
        exercise1.setTime(20);
        exercise1.setReps(1);
        //exercise0.setStartImage();
        //exercise0.setEndImage();
        mExercises.add(exercise1);

        Exercise exercise2 = new Exercise();
        exercise2.setId(2);
        exercise2.setName("Planks");
        exercise2.setTime(10);
        exercise2.setReps(1);
        //exercise1.setStartImage();
        //exercise1.setEndImage();
        mExercises.add(exercise2);

        Exercise exercise3 = new Exercise();
        exercise3.setId(3);
        exercise3.setName("Bicycles");
        exercise3.setTime(10);
        exercise3.setReps(1);
        //exercise2.setStartImage();
        //exercise2.setEndImage();
        mExercises.add(exercise3);

        Exercise exercise4 = new Exercise();
        exercise4.setId(4);
        exercise4.setName("Leg Ups");
        exercise4.setTime(10);
        exercise4.setReps(1);
        //exercise3.setStartImage();
        //exercise3.setEndImage();
        mExercises.add(exercise4);
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public Exercise getExercise(int id) {
        for (Exercise exercise: mExercises) {
            if(exercise.getId() == id) {
                return exercise;
            }
        }
        return null;
    }
}

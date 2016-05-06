package com.jaymalabs.myapplication;

import android.graphics.Bitmap;

/**
 * Created by E134292 on 5/6/2016.
 */
public class Exercise {

    private int mId;
    private String mName;
    private int mTime;
    private int mReps;
    private Bitmap mStartImage;
    private Bitmap mEndImage;

    public Exercise() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int reps) {
        mReps = reps;
    }

    public Bitmap getStartImage() {
        return mStartImage;
    }

    public void setStartImage(Bitmap image) {
        mStartImage = image;
    }

    public Bitmap getEndImage() {
        return mEndImage;
    }

    public void setEndImage(Bitmap image) {
        mEndImage = image;
    }
}

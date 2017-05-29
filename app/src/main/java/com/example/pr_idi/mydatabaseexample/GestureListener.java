package com.example.pr_idi.mydatabaseexample;

/**
 * Created by Carlo on 16/05/2017.
 */
import android.annotation.TargetApi;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;



public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    static String currentGestureDetected;

    public GestureListener(FilmAdapter filmAdapter, GestureListener gestureListener) {
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        // User tapped the screen twice.
        currentGestureDetected=e.toString();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Since double-tap is actually several events which are considered one aggregate
        // gesture, there's a separate callback for an individual event within the doubletap
        // occurring.  This occurs for down, up, and move.
        currentGestureDetected=e.toString();
        return true;
    }
}

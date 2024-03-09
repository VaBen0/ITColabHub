package ru.dvteam.itcollabhub;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class SyncScrollOnTouchListener implements View.OnTouchListener {

    private final View syncedView;

    public SyncScrollOnTouchListener(@NonNull View syncedView) {
        this.syncedView = syncedView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        MotionEvent syncEvent = MotionEvent.obtain(motionEvent);
        float width1 = view.getWidth();
        float width2 = syncedView.getWidth();

        syncEvent.setLocation(syncedView.getX() + motionEvent.getX() * width2 / width1,
                motionEvent.getY());
        syncedView.onTouchEvent(syncEvent);
        return false;
    }
}

package fr.umontpellier.tp3_android_persistence.utils;

import android.view.MotionEvent;
import android.view.GestureDetector;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeControlTouchListener implements RecyclerView.OnItemTouchListener {

    private final GestureDetector gestureDetector;

    public SwipeControlTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return distanceX > 0;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}

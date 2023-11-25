package com.example.quanlykhohang.Interface;

import android.view.MotionEvent;

public interface FragmentInteractionListener {
    void onFragmentBackPressed();
    boolean onFragmentDispatchTouchEvent(MotionEvent ev);
}

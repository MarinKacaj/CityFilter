package com.marin.cityfilter;

import android.view.View;

/**
 * @author Marin Kacaj
 */
class ViewUtil {

    static void visible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    static void gone(View view) {
        view.setVisibility(View.GONE);
    }
}

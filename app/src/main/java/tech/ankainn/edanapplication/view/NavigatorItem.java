package tech.ankainn.edanapplication.view;

import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.google.android.material.textview.MaterialTextView;

public class NavigatorItem {

    MaterialTextView mView;
    View.OnClickListener listener;
    int drawableRes;
    int strRes;

    public NavigatorItem(@DrawableRes int drawableRes, @StringRes int strRes, OnItemClickListener listener) {
        this.listener = v -> listener.onClick(this);
        this.drawableRes = drawableRes;
        this.strRes = strRes;
    }

    public void setLabel(@StringRes int strRes) {
        this.strRes = strRes;
        if (mView != null)
            mView.setText(strRes);
    }

    void setView(MaterialTextView view) {
        mView = view;
    }

    public interface OnItemClickListener {
        void onClick(NavigatorItem item);
    }
}

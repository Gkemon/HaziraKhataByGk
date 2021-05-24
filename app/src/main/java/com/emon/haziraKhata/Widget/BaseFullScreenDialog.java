package com.emon.haziraKhata.Widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.emon.haziraKhata.R;


public class BaseFullScreenDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null)
                dialog.getWindow().setLayout(width, height);
        }
    }


}

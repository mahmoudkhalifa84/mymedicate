package com.medicate_int.mymedicate.module;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.loading_layout);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public LoadingDialog(Context context) {
        super(context, R.style.PauseDialog);
        setContentView(R.layout.loading_layout);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}

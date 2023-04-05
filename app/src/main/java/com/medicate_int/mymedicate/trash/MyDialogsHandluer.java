package com.medicate_int.mymedicate.trash;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

public class MyDialogsHandluer {
    public MyDialogsHandluer() {
    }
    public static void YouAreNotInFamiily(Context context) {
        Dialog dialog = new Dialog(context, R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        dialog.setContentView(R.layout.you_are_not_in_family);
        dialog.findViewById(R.id.textView98).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CacheHelper(context).setMY_PLACE("مقدمة طلب بطاقة");
                context.startActivity(new Intent(context, HomeActivity.class));
            }
        });
        dialog.findViewById(R.id.textView100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

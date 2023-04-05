package com.medicate_int.mymedicate.ui.main_screen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.ui.MapsActivity;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.ui.Login;


public class About extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.imageView42).setOnClickListener(this);
        view.findViewById(R.id.about_ly_email).setOnClickListener(this);
        view.findViewById(R.id.about_ly_facebook).setOnClickListener(this);
        view.findViewById(R.id.about_ly_linkedin).setOnClickListener(this);
        view.findViewById(R.id.about_ly_loca).setOnClickListener(this);
        view.findViewById(R.id.about_ly_website).setOnClickListener(this);
        view.findViewById(R.id.about_update).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.curint_version)).setText(CacheHelper.getVersionName(getActivity()));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView42:
                getActivity().onBackPressed();
                break;
            case R.id.about_ly_email:
                SendEmail();
                break;
            case R.id.about_ly_facebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MedicateTPA")));
                break;
            case R.id.about_ly_linkedin:
                startActivity(Linkedin());
                break;
            case R.id.about_ly_loca:
                Intent mapIntent = new Intent(getActivity(), MapsActivity.class);
                mapIntent.putExtra("title", getString(R.string.about_comp_loca));
                mapIntent.putExtra("lat", "32.853370");
                mapIntent.putExtra("log", "13.084861");
                startActivity(mapIntent);
                break;
            case R.id.about_ly_website:
                WebSite();
                break;
            case R.id.about_update:
                Login.Message(getString(R.string.about_update), getActivity());
                break;

        }
    }

    private static void Copy(String text, Context context) {
        try {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(context.getString(R.string.copy_email_secc), text);
            clipboard.setPrimaryClip(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendEmail() {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "info@medicate.ly", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, getString(R.string.email_choser)));
        } catch (Exception e) {
            Copy("info@medicate.ly", getActivity());
        }
    }

    public Intent Linkedin() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/medicate-int-tpa"));
        return intent;
    }

    public void WebSite() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.medicate.ly"));
            startActivity(Intent.createChooser(myIntent, getString(R.string.medicate)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
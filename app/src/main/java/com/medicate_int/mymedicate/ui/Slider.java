package com.medicate_int.mymedicate.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.adapter.SliderAdapter;
import com.medicate_int.mymedicate.models.SliderModel;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.services.FirebaseMessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Slider extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    SliderAdapter sliderAdapter;
    List<SliderModel> itemsList;
    ViewPager viewPager;
    TabLayout tab;
    int position = 0;
    Animation btnAnim, btnAnim2;
    TextView slider_next, btnGetStarted;
    CacheHelper statics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_class);
        viewPager = findViewById(R.id.viewPager);
        statics = new CacheHelper(this);
        // Obtain the FirebaseAnalytics instance.
        if (!(HomeActivity.isMyServiceRunning(FirebaseMessageHandler.class, this))) {
            startService(new Intent(this, FirebaseMessageHandler.class));
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        itemsList = new ArrayList<>();
     /*  itemsList.add(new SliderItems("الدكتور أمير غيث","", R.drawable.amer));
       itemsList.add(new SliderItems("الدكتور أصيل غيث","" , R.drawable.asel));*/
        itemsList.add(new SliderModel(getResources().getString(R.string.s_1_title), getResources().getString(R.string.s_1_con), R.drawable.slider_bilding));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_2_title), getResources().getString(R.string.s_2_con), R.drawable.slider_helth_care));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_3_title), getResources().getString(R.string.s_3_con), R.drawable.slider_loca));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_4_title), getResources().getString(R.string.s_4_con), R.drawable.slider_qr));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_5_title), getResources().getString(R.string.s_5_con), R.drawable.news));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_6_title), getResources().getString(R.string.s_6_con), R.drawable.ic_slider_offers));
        itemsList.add(new SliderModel(getResources().getString(R.string.s_7_title), getResources().getString(R.string.s_7_con), R.drawable.slider_click));
        itemsList.add(new SliderModel(" ميديكيت", getResources().getString(R.string.s_final_con), R.drawable.new_logo_slider));
        sliderAdapter = new SliderAdapter(itemsList, this);
        btnAnim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.btn_an);
        btnAnim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.btn_an2);
        viewPager.setAdapter(sliderAdapter);
        tab = findViewById(R.id.tabLayout);
        tab.setupWithViewPager(viewPager);
        slider_next = findViewById(R.id.slider_next);
        btnGetStarted = findViewById(R.id.slider_start);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoaseCantry();
            }
        });
        slider_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = viewPager.getCurrentItem();
                if (position < itemsList.size()) {
                    position++;
                    viewPager.setCurrentItem(position);


                }

                if (position == itemsList.size() - 1) { // when we rech to the last screen
                    loaddLastScreen();
                }
            }
        });

        // tablayout add change listener


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == itemsList.size() - 1) {
                    loaddLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Get Started button click listener
        statics.setMY_CONTRY("بدون بلد");
        viewPager.setAnimation(btnAnim2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setLocale(SetLocal.getLong(getApplicationContext()), getApplicationContext());
    }

    public void setLocale(String lang, Context context) {
        Locale loc = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = loc;
        config.setLayoutDirection(loc);
        res.updateConfiguration(config, displayMetrics);
    }

    private void ChoaseCantry() {

        View pop_background = findViewById(R.id.pop_back);
        LinearLayout constraintLayout = findViewById(R.id.slider_inclod);
        pop_background.setVisibility(View.VISIBLE);
        btnGetStarted.setEnabled(false);
        constraintLayout.setVisibility(View.VISIBLE);


        findViewById(R.id.id_egy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("4");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_libya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("1");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_tinis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("2");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_aljaza).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("3");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_ispanya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("7");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_garmny).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("6");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                btnGetStarted.setText(getText(R.string.start));
                btnGetStarted.setEnabled(true);
                btnGetStarted.startAnimation(btnAnim);
            }
        });
        findViewById(R.id.id_italy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("17"); // TODO:No Number
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_ukranya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("9");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();

            }
        });
        findViewById(R.id.id_turky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("5");
                pop_background.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                go();
            }
        });

    }

    public void go() {
        if (statics.getID().equals("null"))
            statics.setMY_PLACE("تسجيل الدخول");
        else statics.setMY_PLACE("المنزل");
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        Slider.this.finish();
    }


    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {
        viewPager.setClickable(false);
        slider_next.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //tvSkip.setVisibility(View.INVISIBLE);
        tab.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        viewPager.setFocusable(false);
        btnGetStarted.setAnimation(btnAnim);
        statics.savePrefsData("login_qr", "");
        statics.setIntro("true");
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

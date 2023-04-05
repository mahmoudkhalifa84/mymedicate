package com.medicate_int.mymedicate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicate_int.mymedicate.askdoctor.NormalAskDoc;
import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.services.NotificationsService;
import com.medicate_int.mymedicate.trash.AdvSecurity;
import com.medicate_int.mymedicate.trash.AmradMozmna;
import com.medicate_int.mymedicate.trash.UserSettings;
 import com.medicate_int.mymedicate.ui.QrScan;
import com.medicate_int.mymedicate.ui.SplashScreen;
import com.medicate_int.mymedicate.ui.main_screen.About;
import com.medicate_int.mymedicate.ui.main_screen.Notifications;
import com.medicate_int.mymedicate.ui.main_screen.ask_doctor.ChatWithMedicateDoctor;
import com.medicate_int.mymedicate.ui.main_screen.booking.BookAppointmentServiceOrOperation;
import com.medicate_int.mymedicate.ui.main_screen.booking.BookDoctorHomeVisit;
import com.medicate_int.mymedicate.ui.main_screen.booking.booking_doctor.SearchByName;
import com.medicate_int.mymedicate.ui.main_screen.booking.booking_doctor.SearchBySpecializationAndArya;
import com.medicate_int.mymedicate.ui.main_screen.card_request.CardRequestIntro;
import com.medicate_int.mymedicate.ui.main_screen.Climes;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.ui.main_screen.MainMenu;
import com.medicate_int.mymedicate.ui.main_screen.medical_network.MedicalNetwork;
import com.medicate_int.mymedicate.ui.main_screen.medical_network.ViewMedicalNetwork;
import com.medicate_int.mymedicate.ui.main_screen.Offers;
import com.medicate_int.mymedicate.ui.main_screen.card_request.CardRequest;
import com.medicate_int.mymedicate.ui.main_screen.my_account.CheckBalance;
import com.medicate_int.mymedicate.ui.main_screen.my_account.MyAccount;
import com.medicate_int.mymedicate.ui.main_screen.settings.ChangeCountry;
import com.medicate_int.mymedicate.ui.main_screen.settings.ChangeLanguage;
import com.medicate_int.mymedicate.ui.main_screen.settings.Settings;
import com.medicate_int.mymedicate.ui.main_screen.ask_doctor.AskDoctor;
import com.medicate_int.mymedicate.ui.main_screen.booking.Booking;
import com.medicate_int.mymedicate.ui.main_screen.booking.OrderDrugs;
import com.medicate_int.mymedicate.ui.main_screen.support_chat.ChatInitialization;
import com.medicate_int.mymedicate.ui.main_screen.support_chat.SupportChat;
import com.medicate_int.mymedicate.ui.main_screen.support_services.Hotels;
import com.medicate_int.mymedicate.ui.main_screen.support_services.SupportServices;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.MyMedicalAppointments;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.UserPersonalData;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.UserProfile;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.Allergies;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.Drugs;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.HealthRecords;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.MedicalChecks;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.MedicalServices;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.Surgeries;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record.UserMedicalInfo;
import com.medicate_int.mymedicate.ui.main_screen.user_profile.medical_files.MyMedicalFiles;

import static com.medicate_int.mymedicate.ui.SplashScreen.NOTIFICATION_CHANNEL_ID;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Moudle.MoudleInterface {
    private DrawerLayout drawer;
    NavigationView navigationView;
    Fragment fragment;
    Toolbar toolbar;
    NotificationsDatabase notificationsDatabase;
    CacheHelper statics;
    boolean remane = false;
    String recevid_qr = "";
    private String TAG = "HOMECLASS";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recevid_qr = "";
        context = this;
        this.
        setContentView(R.layout.activity_home_fragment_activity);
        // new NotificationsDatabase(this).delOldData();

        notificationsDatabase = new NotificationsDatabase(this.getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer_ind);
        setSupportActionBar(toolbar);
        getSupportFragmentManager();
        drawer = findViewById(R.id.drawer_activity);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        statics = new CacheHelper(this);

        if (!(isMyServiceRunning(NotificationsService.class, this)))
            startService(new Intent(this, NotificationsService.class));
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.coustom_actionbar_title, null);
        ((TextView) v.findViewById(R.id.textView_coustom)).setText(this.getTitle());
        this.getSupportActionBar().setCustomView(v);
        if (statics.getUpdateCheck().equals("true") || statics.getUpdateCheck().equals("null")) {
            UpdateCheck();
            UpdatePrices();
            UpdatePhoneNumber();
            statics.setUpdateCheck("false");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void UpdateCheck() {
        Log.d(TAG, "UpdateCheck: ");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Versions").child("MyMedicate");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue());
                if (snapshot.exists()) {
                    String test = snapshot.getValue(String.class).toString().trim().replace(".", "");
                    if (test.length() == 2) test = test + "0";

                    Log.d(TAG, "UPDATE > : NEW " + test + " OLD  > " + CacheHelper.getVersionName(context).replace(".", ""));

                    if (Integer.parseInt(test) > Integer.parseInt(CacheHelper.getVersionName(context).replace(".", ""))) {
                        showUpDateDig();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: UPDATE ERROR");
            }
        });
    }

    public void UpdatePrices() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Prices").child("MyMedicate").child("for_ins");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue());
                try {
                    if (snapshot.exists()) {
                        statics.setPricesIns(snapshot.getValue(Boolean.class).toString().trim());
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "CASE 23 |NULL");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void UpdatePhoneNumber() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Numbers").child("call_center");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   Log.d(TAG, "onDataChange: " + snapshot.getValue());
                try {
                    if (snapshot.exists()) {

                        statics.setPnoneNummber(snapshot.getValue(String.class).toString().trim());
                        Log.d(TAG, "New Number: " + snapshot.getValue());
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "CASE 23 |NULL");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpDateDig() {
        Dialog update = new Dialog(HomeActivity.this, R.style.PauseDialog);
        update.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        update.setCancelable(true);
        update.setCanceledOnTouchOutside(false);
        update.setContentView(R.layout.update_show);
        ((TextView) update.findViewById(R.id.textView103)).setText(getString(R.string.cur_version).concat(" : " + CacheHelper.getVersionName(context)));
        update.findViewById(R.id.textView102).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.dismiss();
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        update.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_myaccount:
                if (!statics.getID().equals("null")) {
                    statics.setMY_PLACE("حسابي");
                    showHome(new MyAccount());
                } else {
                    Dialog dialog = new Dialog(this, R.style.PauseDialog);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.fragment_dailog);
                    TextView textView = dialog.findViewById(R.id.text_dig);
                    textView.setText(R.string.login_to_contune);
                    dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            statics.setMY_PLACE("تسجيل الدخول");
                            showHome(new Login());
                        }
                    });
                    dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
                break;
            case R.id.nav_login:
                statics.savePrefsData("login_qr", "");
                statics.setMY_PLACE("تسجيل الدخول");
                showHome(new Login());
                break;
            case R.id.nav_logout:
                statics.Logout();
                //   FragmentLogin.Message(getString(R.string.logout_sec), this);

                statics.setMY_PLACE("المنزل");
                startActivity(new Intent(this, SplashScreen.class));
                break;
            case R.id.nav_main_menu:
                statics.setMY_PLACE("المنزل");
                showHome(new MainMenu());

                break;
            case R.id.nav_user_info:
                //  chak();
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    showDi();
                } else {
                    statics.setMY_PLACE("المنزل");
                    startActivityForResult(new Intent(this, QrScan.class), 5);
                }
                break;

            case R.id.nav_settings:
                statics.setMY_PLACE("الاعدادات");
                fragment = new Settings();
                showHome(fragment);
                break;
            case R.id.nav_offers:
                statics.setMY_PLACE("العروض");
                fragment = new Offers();
                showHome(fragment);
                break;
            case R.id.nav_request_card:
                if (statics.getID().equals("null")) {
                    statics.setMY_PLACE("طلب بطاقة");
                    showHome(new CardRequest());
                } else {
                    statics.setMY_PLACE("حسابي");
                    showHome(new MyAccount());
                }
                break;
            case R.id.nav_about:
                statics.setMY_PLACE("حول");
                showHome(new About());
                break;
            case R.id.nav_share:
                Moudle.ShereApp(HomeActivity.this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderDrugs.CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "onActivityResult: تم التقاط الصورة");
                //    Snackbar.make(view, "تم التقاط الصورة", Snackbar.LENGTH_SHORT).show();
                //   statics.setMY_PLACE("المحادثة الفورية");
                //    startActivity(new Intent(context, MedicateDocChat.class).putExtra("img", uri.toString()));
            } else Log.d(TAG, "onActivityResult: لم يتم التقاط الصورة");
        } else if (requestCode == OrderDrugs.CODE_GALARY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    //    statics.setMY_PLACE("المحادثة الفورية");
                    //     startActivity(new Intent(context, MedicateDocChat.class).putExtra("img", uri.toString()));
                    // Snackbar.make(view, getActivity().getString(R.string.app_photo_done), Snackbar.LENGTH_SHORT).show();
                } else
                    Log.d(TAG, "onActivityResult: تم أختيار الصورة");
            } else
                Log.d(TAG, "onActivityResult: لم يتم أختيار الصورة");
        } else if (requestCode == 3) {
            if (resultCode == PackageManager.PERMISSION_GRANTED) {
                statics.setMY_PLACE("المنزل");
                startActivity(new Intent(getApplicationContext(), QrScan.class));
            } else {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    showDi();
                }
            }
        } else if (requestCode == 5) {
            if (data != null)
                recevid_qr = data.getStringExtra("card_num");
            if (recevid_qr.isEmpty())
                Toast.makeText(this, R.string.error_qr, Toast.LENGTH_SHORT).show();
            else
                MainMenu.QR_code(recevid_qr, this);
        } else if (requestCode == 2) {
            if (data != null) {
                if (data.getStringExtra("card_num_login").toString().isEmpty()) {
                    Login.Message(getString(R.string.error_qr), this);
                } else {
                    statics.savePrefsData("login_qr", data.getStringExtra("card_num_login"));
                }
            }
        }
    }

    private void showDi() {
        Dialog dialog2 = new Dialog(HomeActivity.this, R.style.PauseDialog);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.per_requst);
        dialog2.findViewById(R.id.textView42).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
            }
        });
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            switch (statics.getMY_PLACE()) {
                case "داخل الشبكات الطبية":
                    statics.setMY_PLACE("الشبكات الطبية");
                    showHome(new ViewMedicalNetwork());
                    break;
                case "البحث حسب الاسم":
                case "طلب ادوية":
                case "حجز زيارة منزلية":
                case "طلب خدمة او عملية":
                case "البحث حسب التخصص":
                    showHome(new Booking());
                    statics.setMY_PLACE("حجز مواعيد");
                    break;
                case "الاستعلام عن الرصيد":
                    showHome(new MyAccount());
                    statics.setMY_PLACE("حسابي");
                    break;
                case "السجل الطبي/بيانات الشخصية":
                case "السجل الطبي/الادوية":
                case "السجل الطبي/الحساسيات":
                case "السجل الطبي/الامراض المزمنة":
                case "السجل الطبي/الخدمات الطبية":
                case "السجل الطبي/الفحوصات الطبية":
                case "السجل الطبي/العمليات الجراحية":
                    showHome(new HealthRecords());
                    statics.setMY_PLACE("السجل الطبي");
                    break;
                case "اسال طبيب2":
                case "طبيب ميديكيت":
                    showHome(new AskDoctor());
                    statics.setMY_PLACE("اسال طبيب");
                    break;
                case "داخل المطالبات":
                    // todo --- keep null
                    break;
                case "تغيير اللغة":
                case "تغيير البلد":
                    statics.setMY_PLACE("الاعدادات");
                    showHome(new Settings());
                    break;
                case "تغيير اللغة2":
                case "الامان":
                case "تعديل البيانات الشخصية":
                case "تغيير البلد2":
                    statics.setMY_PLACE("الاعدادات المتقدمة");
                    showHome(new UserSettings());
                    break;
                case "داخل التخصص":
                    showHome(new SearchBySpecializationAndArya());
                    statics.setMY_PLACE("البحث حسب التخصص");
                    break;
                case "المعلومات الشخصية":
                case "مواعيدي الطبية":
                case "الاعدادات المتقدمة":
                case "السجل الطبي":
                case "ملفاتي الطبية":
                    showHome(new UserProfile());
                    statics.setMY_PLACE("الملف الشخصي");
                    break;
                case "مواعيدي الطبية2":
                    showHome(new MyMedicalAppointments());
                    statics.setMY_PLACE("السجل الطبي");
                    break;
                case "الفنادق":
                    showHome(new SupportServices());
                    statics.setMY_PLACE("خدماتنا");
                    break;
                default: {
                    if (statics.getMY_PLACE().equals("المنزل")) {
                        Dialog dialog = new Dialog(this, R.style.PauseDialog);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.fragment_dailog);
                        TextView textView = dialog.findViewById(R.id.text_dig);
                        textView.setText(R.string.are_you_suer);
                        dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.you_hope_the_bast_fo_you), Toast.LENGTH_SHORT).show();
                                finishAffinity();
                            }
                        });
                        dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    } else {
                        statics.setMY_PLACE("المنزل");
                        showHome(new MainMenu());
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        statics = new CacheHelper(getApplicationContext());
        if (getIntent().hasExtra("dir")) { // open fragment
            Log.d(TAG, "onStart: go to Notifications");
            statics.setMY_PLACE(getIntent().getStringExtra("dir").toString().trim());
        }
        if (statics.getID().equals("null")) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        }
        if (!remane) {
            fragment = new MainMenu();
            toolbar.setVisibility(View.VISIBLE);
            switch (statics.getMY_PLACE()) {
                case "المنزل":
                    showHome(new MainMenu());
                    break;
                case "تسجيل الدخول":
                    showHome(new Login());
                    toolbar.setVisibility(View.GONE);
                    break;
                case "الشبكات الطبية":
                    showHome(new ViewMedicalNetwork());
                    break;
                case "حول":
                    showHome(new About());

                    break;
                case "مقدمة طلب بطاقة":
                    showHome(new CardRequestIntro());

                    break;
                case "الاستعلام عن الرصيد":
                    showHome(new CheckBalance());

                    break;
                case "داخل الدردشة":
                    showHome(new SupportChat());

                    break;
                case "خدماتنا":
                    showHome(new SupportServices());

                    break;
                case "طبيب ميديكيت":
                    showHome(new NormalAskDoc());

                    break;
            /*    case "تعديل البيانات الشخصية":
                    showHome(new AdvEditUserInfo());

                    break;*/
                case "السجل الطبي/بيانات الشخصية":
                    showHome(new UserMedicalInfo());

                    break;
                case "السجل الطبي/العمليات الجراحية":
                    showHome(new Surgeries());

                    break;
                case "السجل الطبي/الامراض المزمنة":
                    showHome(new AmradMozmna());

                    break;
                case "السجل الطبي/الخدمات الطبية":
                    showHome(new MedicalServices());
                    break;
                case "السجل الطبي/الادوية":
                    showHome(new Drugs());

                    break;
                case "السجل الطبي/الحساسيات":
                    showHome(new Allergies());

                    break;
                case "مواعيدي الطبية":
                    showHome(new MyMedicalAppointments());

                    break;
                case "السجل الطبي/الفحوصات الطبية":
                    showHome(new MedicalChecks());

                    break;
                case "المطالبات":
                    showHome(new Climes());

                    break;
                case "السجل الطبي":
                    showHome(new HealthRecords());

                    break;
                case "الاعدادات المتقدمة":
                    showHome(new UserSettings());

                    break;
                case "حجز زيارة منزلية":
                    showHome(new BookDoctorHomeVisit());

                    break;
                case "اسال طبيب":
                    showHome(new AskDoctor());

                    break;
              /*  case "اسال طبيب2":
                    showHome(new FragmentInsideAskDoctor());

                    break;*/
                case "الاعدادات":
                    showHome(new Settings());

                    break;
                case "الفنادق":
                    showHome(new Hotels());

                    break;
                case "تغيير اللغة":
                case "تغيير اللغة2":
                    showHome(new ChangeLanguage());

                    break;
                case "تغيير البلد":
                case "تغيير البلد2":
                    showHome(new ChangeCountry());
                    break;

                case "المعلومات الشخصية":
                    showHome(new UserPersonalData());

                    break;
                case "داخل الشبكات الطبية":
                    showHome(new MedicalNetwork());

                    break;
                case "محادثة فورية مع ميديكيت":
                    if (statics.getChatState().equals("ok") || !statics.getID().equals("null")) {
                        showHome(new SupportChat());
                    } else
                        showHome(new ChatInitialization());
                    break;
                case "محادثة فورية مع جهة العمل":
                    if (statics.getChatState().equals("ok") || !statics.getID().equals("null")) {
                        showHome(new SupportChat());
                    } else
                        showHome(new ChatInitialization());
                    break;
                case "المحادثة الفورية2":
                    showHome(new ChatInitialization());

                    break;
                case "طلب بطاقة":
                    showHome(new CardRequest());

                    break;
                case "الملف الشخصي":
                    showHome(new UserProfile());

                    break;
                case "الاشعارات":
                case "الاشعارات2":
                    showHome(new Notifications());
                    if (isMyServiceRunning(NotificationsService.class, getApplicationContext()))
                        stopService(new Intent(getApplicationContext(), NotificationsService.class));
                    break;
                case "العروض":
                    showHome(new Offers());

                    break;
                case "حجز مواعيد":
                    showHome(new Booking());

                    break;
                case "الامان":
                    showHome(new AdvSecurity());

                    break;
                case "البحث حسب التخصص":
                    showHome(new SearchBySpecializationAndArya());

                    break;
                case "البحث حسب الاسم":
                    showHome(new SearchByName());

                    break;
                case "طلب ادوية":
                    showHome(new OrderDrugs());

                    break;
                case "حسابي":
                    showHome(new MyAccount());

                    break;
                case "ملفاتي الطبية":
                    showHome(new MyMedicalFiles());

                    break;
                case "طلب خدمة او عملية":
                    showHome(new BookAppointmentServiceOrOperation());

                    break;
                default: {
                    statics.setMY_PLACE("المنزل");
                }
            }
        }
        remane = false;

    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void push(Context context, String t, String c, int i) {
        NotificationsDatabase database = new NotificationsDatabase(context.getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(t);
        builder.setContentText(c);
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(c));
        // if (database.getNewCount()>0)
        builder.setNumber(database.getNewCount());
        builder.setSmallIcon(R.drawable.logo_m_small);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i, builder.build());

    }

    public static void pushChatAskDoctor(Context context, String body) {
        Intent notifyIntent = new Intent(context, ChatWithMedicateDoctor.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationsDatabase database = new NotificationsDatabase(context.getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(notifyPendingIntent);
        builder.setContentTitle(context.getString(R.string.medicate_doc) + " - " + context.getString(R.string.ask_doc));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentText(body);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(body));
        // if (database.getNewCount()>0)
        builder.setNumber(database.getNewCount());
        builder.setSmallIcon(R.drawable.logo_m_small);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }

    public static void pushFirebaseNotifications(Context context, String t, String c) {
        Intent notifyIntent = new Intent(context, HomeActivity.class);
        notifyIntent.putExtra("dir", "الاشعارات2");
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationsDatabase database = new NotificationsDatabase(context.getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(notifyPendingIntent);
        builder.setContentTitle(t);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentText(c);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(c));
        // if (database.getNewCount()>0)
        builder.setNumber(database.getNewCount());
        builder.setSmallIcon(R.drawable.logo_m_small);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());

    }

    public static void pushChat(Context context, String t, String c) {
        Intent notifyIntent = new Intent(context, HomeActivity.class);
        notifyIntent.putExtra("dir", "المحادثة الفورية");
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(notifyPendingIntent);
        builder.setContentTitle(t);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentText(c);
        // if (database.getNewCount()>0)
        builder.setSmallIcon(R.drawable.logo_m_small);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        remane = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showHome(Fragment r) {
        fragment = r;
        FragmentManager fmgr = getSupportFragmentManager();
        fmgr.beginTransaction()

                .setCustomAnimations(R.anim.network_bob, R.anim.popout)
                .replace(R.id.fragment_container, fragment, fragment.getTag()).commit();

    }

    @Override
    public void onBack(boolean bool) {
        if (bool) {
            onBackPressed();
        }
    }

    @Override
    public void okClicked(boolean bool) {

    }
}


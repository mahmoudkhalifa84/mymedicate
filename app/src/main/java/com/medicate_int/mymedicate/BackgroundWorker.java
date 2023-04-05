package com.medicate_int.mymedicate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.services.NotificationsService;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    View v;
    String card_number,type;
    Dialog dialog;
    String TAG = "FragmentLoginTAG";
    private String pass;
    CacheHelper statics;

    public BackgroundWorker(Context context, View v, Dialog dialog) {
        this.context = context;
        this.v = v;

        this.dialog = dialog;
    }


    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://www.medicateint.com/data2/checkLogin";
            try {
                card_number = params[0];
                pass = params[1];
                type = params[2];
                Log.d(TAG, "doInBackground: " + card_number + " > " + pass + " > "+type);
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                String post_data =
                        URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(card_number, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8") + "&"
                                + URLEncoder.encode("userType", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d(TAG, "doInBackground:RES " + result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d(TAG, "ERROR 1" + e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "ERROR 2" + e.getMessage());

            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        statics = new CacheHelper(context);

        dialog.show();

    }

    @Override
    protected void onPostExecute(String result) {
        try {
            //alertDialog.dismiss();
            if (null != context) {
                Log.d("IPADREES" , "RESUALT > " + result );
                dialog.dismiss();
                convertLoginMsg(result);

            }
        } catch (JSONException e) {
            dialog.dismiss();
            Login.Message(context.getResources().getString(R.string.erroe_in_serv),context);
            Log.d(TAG, "onPostExecute: CATCH" + e.getMessage());
            e.printStackTrace();
        }
    }
    public void convertLoginMsg(String json) throws JSONException {
        String name;
      //  Log.d("IPADREES" , "data > " + getIpAddress() );
        if (!(json == null)) {
            JSONObject obj = new JSONObject(json);
            name = obj.getString("msg");
            if (name.equals("0"))
                Login.Message(context.getResources().getString(R.string.pass_or_name_error),context);
            else if (name.equals("1")) {
                name = obj.getString("BeneficiaryID");
                statics.setID(name);
                context.stopService(new Intent(context, NotificationsService.class));
                statics.setCardNumber(card_number);
                statics.setUserName(obj.getString("Name").toString().trim());
                statics.setUserType(type);
                statics.setUserGender(obj.getString("sex").toString().trim());
                statics.setCoName(obj.getString("CoName").toString().trim());
                statics.setComID(obj.getString("ComID").toString().trim());

                statics.setMY_PLACE("المنزل");
                statics.setOldUserID(name);
                statics.setOldUserCardNumber(card_number);
                statics.setOldUserName(obj.getString("Name").toString().trim());
                statics.setOldUserType(type);
                statics.setOldUserPass(pass);
                statics.setUserGender(obj.getString("CoName").toString().trim());

                new NotificationsDatabase(context).delOldData();
                Log.d(TAG, "ID Number : " + name);
                if(HomeActivity.isMyServiceRunning(NotificationsService.class,context))
                context.stopService(new Intent(context,NotificationsService.class));
                context.startActivity(new Intent(context, HomeActivity.class));

            }
        }
        else {
            dialog.dismiss();
           Login.Message(context.getResources().getString(R.string.erroe_in_serv),context);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        dialog.dismiss();
    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}

package com.medicate_int.mymedicate.module;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SendCardRequest extends AsyncTask<String,Void,String> {
    private Context context;
    Dialog dialog;
    String TAG = "CARDREQUST";
    CacheHelper statics;
    int up = 0 ;

    public SendCardRequest(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://www.medicateint.com/data2/InsertDataIntoContacts";
            try {
                for (String i : params) {
                    Log.d(TAG, "doInBackground: " + up + " > " + i);
                    up++;
                }

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
                        URLEncoder.encode("FirstName", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&"
                                + URLEncoder.encode("FatherName", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                                + URLEncoder.encode("GrandFatherName", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                                + URLEncoder.encode("FamilyName", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                                + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                                + URLEncoder.encode("DateOfBirth", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                                + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                                + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8") + "&"
                                + URLEncoder.encode("CityID", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8") + "&"
                                + URLEncoder.encode("AddressLine1", "UTF-8") + "=" + URLEncoder.encode(params[9], "UTF-8") + "&"
                                + URLEncoder.encode("SocialNo", "UTF-8") + "=" + URLEncoder.encode(params[10], "UTF-8") + "&"
                                + URLEncoder.encode("app", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
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
                Log.d(TAG, "doInBackground:RES  sent" + result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d(TAG, "ERROR 1" + e.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "ERROR 2" + e.getMessage() + " | \n" + e.getCause() + " | \n" + e.getLocalizedMessage());
            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        statics = new CacheHelper(context);
     //   dialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if (null != context) {
                Log.d("IPADREES" , "RESUALT > " + result );
             //   dialog.dismiss();
             //   convertLoginMsg(result);
            }
        } catch (Exception e) {
          //  dialog.dismiss();
         //   FragmentLogin.Message(context.getString(R.string.cheack_int_con),context);
            Log.d(TAG, "onPostExecute: CATCH" + e.getMessage());
            e.printStackTrace();
        }
    }
    public void convertLoginMsg(String json) throws JSONException {
        String name;
        if (!(json == null)) {
            JSONObject obj = new JSONObject(json);
            name = obj.getString("msg");
            if (name.equals("0")) {
         /*       successful.Done(0);
            else if (name.equals("2"))
                successful.Done(2);
            else if (name.equals("3"))
                successful.Done(3);
            else if (name.equals("1")) {
                successful.Done(1);
            }*/
            } else {
                Login.Message(context.getResources().getString(R.string.no_internet_con), context);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
      //  dialog.dismiss();
    }
    public interface Successful{
        void Done(int res);
    }

}

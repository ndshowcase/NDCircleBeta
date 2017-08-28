package kr.ms.neungdong.showcase.neungdongcircleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by user on 2015-04-11.
 */
public class Sendserver extends Activity {
    String URL_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		URL_home = QuickstartPreferences.URL_home;

        Intent intent=getIntent();
        String registrationId = intent.getStringExtra("registrationId");
		String act = intent.getStringExtra("act");
		String setting_push = intent.getStringExtra("setting_push");
		String setting_board = intent.getStringExtra("setting_board");

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		 HttpPostData(act,registrationId,setting_push,setting_board);
        finish();
    }

    public void HttpPostData(String act,String reg_id,String setting_push,String setting_board) {
        URL obj;
        String urlParameters  = "act="+act+"&reg_id="+reg_id+"&setting="+setting_push+"&setting_board="+setting_board;

        try {

            obj = new URL(URL_home);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
			con.setRequestProperty("Referer", URL_home);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(urlParameters.getBytes("UTF-8"));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + URL_home);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

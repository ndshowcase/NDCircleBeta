/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.ms.neungdong.showcase.neungdongcircleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    String URL_home = QuickstartPreferences.URL_home;

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG,"refresh token");
        String reg_id_g="none";
        String is_upgrade="false";

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.contains("fcm")) {
            if (prefs.contains("soundonoff")) {
                reg_id_g = prefs.getString("regid", "");
                prefs.edit().putBoolean("fcm", true).apply();
                prefs.edit().putString("regid", refreshedToken).apply();
                is_upgrade = "true";
            }else {
                initial_set();
                prefs.edit().putString("regid", refreshedToken).apply();
            }
        }else {
            prefs.edit().putString("regid", refreshedToken).apply();
        }

        sendRegistrationToServer(refreshedToken,reg_id_g,is_upgrade);

        prefs.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();

        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
    // [END refresh_token]

    private void initial_set() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("soundonoff","true").apply();
        prefs.edit().putString("vibonoff", "true").apply();
        prefs.edit().putString("setting", "true-true-false-false-false-true-false").apply();
        prefs.edit().putString("setting_board", "none").apply();
        prefs.edit().putString("install", "false").apply();
        prefs.edit().putBoolean("fcm", true).apply();
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token, String token_old, String is_up) {
        // Add custom implementation, as needed.

        URL obj;
        String urlParameters="";

        urlParameters  = "act=procAndroidpushappRegIn&reg_id="+token+"&sort=W&is_upgrade="+is_up+"&token_old="+token_old;

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

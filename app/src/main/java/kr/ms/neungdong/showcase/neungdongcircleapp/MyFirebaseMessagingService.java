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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String URL_home = QuickstartPreferences.URL_home;
    private static final String TAG = "MyGcmListenerService";
    String member_srl;
    String nick_name;
    String sort_link;
    boolean statuss;
    boolean statusv;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        String message = remoteMessage.getData().get("message");
        String board = remoteMessage.getData().get("board");
        String dmember = remoteMessage.getData().get("dmember");
        String cmember = remoteMessage.getData().get("cmember");
        String ccmember = remoteMessage.getData().get("ccmember");
        String sort = remoteMessage.getData().get("sort");
        String address = remoteMessage.getData().get("address");

        Log.d(TAG, "Message: " + message +" board : " + board + " dmember : " + dmember + " cmember : " + cmember + " sort : " + sort + " address : " + address);


        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message,board,dmember,cmember,ccmember,sort,address);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     *  GCM message received.
     */
    private void sendNotification(String msg,String board,String dmember,String cmember,String ccmember,String sort,String address) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean status1=false;
        boolean status2=false;
        boolean status3=false;
        boolean status4=false;
        boolean status5=false;
        boolean status6=false;
        boolean status7=false;
        statuss=false;
        statusv=false;
        String title;

        if (prefs.contains("soundonoff")) {
            statuss=Boolean.parseBoolean(prefs.getString("soundonoff", ""));
        }
        if (prefs.contains("vibonoff")) {
            statusv=Boolean.parseBoolean(prefs.getString("vibonoff", ""));
        }
        if(prefs.contains("setting")) {
            String setting = prefs.getString("setting", "");
            String[] array_setting = setting.split("-");
            status1 = Boolean.parseBoolean(array_setting[0]);
            status2 = Boolean.parseBoolean(array_setting[1]);
            status3 = Boolean.parseBoolean(array_setting[2]);
            status4 = Boolean.parseBoolean(array_setting[3]);
            status5 = Boolean.parseBoolean(array_setting[4]);
            status6 = Boolean.parseBoolean(array_setting[5]);
            status7 = Boolean.parseBoolean(array_setting[6]);
        }
        if (prefs.contains("member_srl_origin")) {
            member_srl = prefs.getString("member_srl_origin", "");
        }
        if (prefs.contains("nick_name_origin")) {
            nick_name = prefs.getString("nick_name_origin", "");
        }

        switch(sort){
            case "1" : title= board + getString(R.string.gcm_newdoc);
                Noti(title,msg,address,sort);
                break;

            case "2" :
                String[] array = cmember.split("-");
                boolean comment=false;
                for(int i=0; i< array.length; i++){
                    if(array[i].equals(member_srl)) comment=true;
                }

                if (dmember.equals(member_srl)) {
                    title = nick_name + getString(R.string.gcm_newcomdoc);
                    Noti(title, msg, address, sort);
                    break;
                }else if (ccmember.equals(member_srl)) {
                    title = nick_name + getString(R.string.gcm_newcomcom);
                    Noti(title, msg, address, sort);
                    break;
                }else if(comment) {
                    title = nick_name + getString(R.string.gcm_newcomdoccom);
                    Noti(title,msg,address,sort);
                    break;
                }else{
                    title= board + getString(R.string.gcm_newcom);
                    Noti(title,msg,address,sort);
                    break;
                }

            case "3" : title= getString(R.string.gcm_newmsg);
                Noti(title,msg,address,sort);
                break;

            case "4" : title= getString(R.string.gcm_test);
                Noti(title,msg,address,sort);
                break;

			case "11" :
                if(status6){
                    title = board;
                    Noti(title,msg,address,sort);
                }
                break;
        }
    }

    private void Noti(String title,String msg,String address,String sort){

        Intent intent = new Intent(this, MainActivity.class);
        if (sort.equals("3")){
            sort_link="msg";
        }else if(sort.equals("11")) {
            sort_link = "notice";
        }else{
            sort_link="document";
		}

        intent.putExtra("sort", sort_link);
        intent.putExtra("address", address);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int dummyuniqueInt = new Random().nextInt(543254);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, dummyuniqueInt, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_gcm)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        if(statuss){
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
            ring.play();
        }

        if(statusv){
            ((Vibrator) getApplicationContext().getSystemService(
                    Context.VIBRATOR_SERVICE)).vibrate(800);
        }
    }
}

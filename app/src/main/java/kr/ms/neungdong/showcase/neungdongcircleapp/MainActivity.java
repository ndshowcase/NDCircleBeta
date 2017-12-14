package kr.ms.neungdong.showcase.neungdongcircleapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    String URL_home;
    private static final String TAG = "xepushapp";
    boolean socialxe2015;
    boolean socialxe;
    boolean my_domain_is_socialserver;
    String socialserver;
    private static final int MY_PERMISSION_REQUEST = 85600;
    private final Handler handler = new Handler();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private DownloadManager downloadManager;
    private DownloadManager.Request request;
    private SwipeRefreshLayout mSwipeRefresh;



    Handler handlerr = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case 22:
                    mWebView.loadUrl(finall_url);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    private BroadcastReceiver completeReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, R.string.download_com,Toast.LENGTH_SHORT).show();
        }

    };
    protected WebView mWebView;
    protected WebView childView;
    boolean popup=false;
    boolean install;
    String URL_Check;
    String URL_Check_xe;
    String url_final;
    String urlget;
    String urldown;
    String url_member;
    String finall_url;
    Intent intent_s;
    Intent intent33;
    String member_srl;
    String nick_name;
    String userAgent;
    String sort;
    String address;
    String dailycheck;
    boolean socialnow=false;
    boolean socialnow2=false;
    boolean sync_proc=false;

    ImageButton btnBack;
    ImageButton btnForward;
    ImageButton Gohome;
    ImageButton btnCheck;
    ImageButton Login;
    ImageButton Setting;
    ImageButton Finish;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;
    private ProgressBar mLoadingProgressBar = null;
    Context context;
    private FrameLayout customViewContainer;
    private RelativeLayout bottomlayout;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected int mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if(requestCode == 333) {
            if(resultCode==RESULT_OK){
                if(intent.getStringExtra("login").equals("Y")){
                    mWebView.loadUrl(URL_home+"applogin");
                }else if(intent.getStringExtra("login").equals("New")){
                    mWebView.loadUrl(URL_home+"?mid=applogin&act=dispMemberLogout");
                }else if(intent.getStringExtra("sync").equals("Y")){
                    String url_fi = intent.getStringExtra("url");
                    sync_proc =true;
                    mWebView.loadUrl(url_fi);

                }
            }
        }

        if (requestCode == mRequestCodeFilePicker) {
            if (resultCode == RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {

                        if (Build.VERSION.SDK_INT <19){
                            mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                            mFileUploadCallbackFirst = null;
                        }else{
                            final Uri uri = intent.getData();
                            // Get the File path from the Uri
                            String path = getPath(this, uri);
                            Uri urii = Uri.fromFile(new File(path));
                            mFileUploadCallbackFirst.onReceiveValue(urii);
                            mFileUploadCallbackFirst = null;
                        }
                    }else if (mFileUploadCallbackSecond != null) {

                        Uri[] dataUris;
                        try {
                            dataUris = new Uri[] { Uri.parse(intent.getDataString()) };
                        }
                        catch (Exception e) {
                            dataUris = null;
                        }

                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            }
            else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                }
                else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        intent_s = new Intent(this, Setting.class);
        intent33 = new Intent(this, Sendserver.class);

        URL_home = QuickstartPreferences.URL_home;
        dailycheck = QuickstartPreferences.dailycheck;
        socialxe2015 = QuickstartPreferences.socialxe2015;
        socialxe = QuickstartPreferences.socialxe;
        socialserver = QuickstartPreferences.socialserver;
        my_domain_is_socialserver = QuickstartPreferences.my_domain_is_socialserver;

        android.webkit.CookieSyncManager.createInstance(this);
        android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeExpiredCookie();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("soundonoff")) {
        }else{
            initial_set();
        }

        registBroadcastReceiver();

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String urlget = mWebView.getUrl();
                if(urlget.matches(".*procFileDownload.*") || urlget.matches(".*procAndroidpushappFiledown.*")){
                    mWebView.loadUrl(urldown);
                }else{
                    mWebView.loadUrl(urlget);
                }
                mSwipeRefresh.setRefreshing(false);
            }
        });

        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

        if(URL_home.startsWith("http://")){
            URL_Check_xe = URL_home.replaceAll("http://","");
        }else if(URL_home.startsWith("https://")){
            URL_Check_xe = URL_home.replaceAll("https://","");
        }

        if(URL_Check_xe.startsWith("www.")){
            URL_Check_xe = URL_Check_xe.replaceAll("www.","");
        }
        String[] array_aaa = URL_Check_xe.split("/");
        URL_Check = array_aaa[0];

        btnBack = (ImageButton) findViewById(R.id.btnback);
        btnBack.setOnTouchListener(btnTouchListener);
        btnForward = (ImageButton) findViewById(R.id.btnforward);
        btnForward.setOnTouchListener(btnTouchListener);
        Gohome = (ImageButton) findViewById(R.id.gohome);
        Gohome.setOnTouchListener(btnTouchListener);
        Login = (ImageButton) findViewById(R.id.btnlogin);
        Login.setOnTouchListener(btnTouchListener);
        Setting = (ImageButton) findViewById(R.id.setting);
        Setting.setOnTouchListener(btnTouchListener);
        Finish = (ImageButton) findViewById(R.id.finish);
        Finish.setOnTouchListener(btnTouchListener);
        btnCheck = (ImageButton) findViewById(R.id.btncheck);
        btnCheck.setOnTouchListener(btnTouchListener);
        mWebView = (WebView) findViewById(R.id.webview);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.progressbar_Horizontal);
        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        bottomlayout = (RelativeLayout) findViewById(R.id.bottomlayout);

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("EUC-KR");
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(getFilesDir() + "/database/");

        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webSettings.setAppCachePath(getFilesDir() + "/cache/");

        mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "myJs");
        mWebView.addJavascriptInterface(new MyJavaScriptInterface_reg(), "myJs_reg");
        mWebView.addJavascriptInterface(new MyJavaScriptInterface_ins(), "myJs_ins");

        mWebView.setNetworkAvailable(true);
        mWebView.setVerticalScrollbarOverlay(true);
        mWebView.setHorizontalScrollbarOverlay(true);

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                Toast.makeText(MainActivity.this, R.string.download_start, Toast.LENGTH_LONG).show();

                String[] fileName_a = contentDisposition.split("filename=");
                String fileName = fileName_a[1].replaceAll("\"", "");

                MimeTypeMap mime = MimeTypeMap.getSingleton();
                int index = fileName.lastIndexOf('.')+1;
                String ext = fileName.substring(index).toLowerCase();
                String type = mime.getMimeTypeFromExtension(ext);
                CookieManager cookieManager = CookieManager.getInstance();
                String cookie="";
                try {
                    cookie = cookieManager.getCookie(new URL(URL_home).getHost());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                request = new DownloadManager.Request(Uri.parse(url));

                if (Build.VERSION.SDK_INT >= 11) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                Log.i(TAG,"mimetype : "+type);
                Log.i(TAG,"urldown : "+urldown);

                request.setMimeType(type);

                request.addRequestHeader("Cookie", cookie);
                request.addRequestHeader("User-Agent", userAgent);
                request.addRequestHeader("Referer", urldown);

                request.setTitle(fileName);
                downloadManager.enqueue(request);

            }
        });

        mWebViewClient = new myWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);

        mWebChromeClient = new myWebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);

        String userAgent = webSettings.getUserAgentString();
        webSettings.setUserAgentString(userAgent + " APP_XEPUSH_Android");

        Gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(URL_home);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(dailycheck);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:callAndroid_login()");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoForward()){
                    mWebView.goForward();
                }
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWebView.loadUrl("javascript:callAndroid()");

            }
        });

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkPermission();


    }




    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        Log.i(TAG, "onNewIntent");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        IntentFilter completeFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(completeReceiver, completeFilter);

        if(mWebView !=null){
            mWebView.onResume();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        unregisterReceiver(completeReceiver);
        super.onPause();
        if(mWebView!=null){
            mWebView.onPause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        if(mWebView != null){
            mWebView.destroy();
            mWebView = null;
        }
    }


    public void registBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context contextd, Intent intent) {
                String action = intent.getAction();

                if (action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)) {

                }
            }
        };
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void checkPermission() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel(getString(R.string.checkpermission),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                                MY_PERMISSION_REQUEST);
                                    }
                                }
                            });

                }

                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);

            }else{
                startWebView();
            }

        }else{
            startWebView();
        }
    }

    private void startWebView(){

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        install = Boolean.parseBoolean(prefs.getString("install", ""));

        if (install) {
            sort = getIntent().getStringExtra("sort");
            if (TextUtils.isEmpty(sort)) {
                url_final = URL_home;
            } else {
                address = getIntent().getStringExtra("address");
                if (sort.equals("msg")) {
                    url_final = URL_home + "index.php?act=dispCommunicationMessages&message_srl=" + address;
                }
                if (sort.equals("notice")) {
                    url_final = address;
                }
                if (sort.equals("document")) {
                    url_final = URL_home + address;
                }
                Log.i(TAG, sort + "---" + address);
            }
        } else {
            url_final = URL_home + "applogin";
        }
        Log.i(TAG, "url:" + url_final);

        if (!url_final.matches(".*" + URL_Check + ".*")){
            mWebView.loadUrl(URL_home);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url_final));
            startActivity(i);
        }else {
            mWebView.loadUrl(url_final);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    startWebView();
                } else {
                    startWebView();
                }
                break;

        }
    }

    private class myWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            AlertDialog alertDialog = builder.create();
            String message = "Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = getString(R.string.ssle1);
                    break;

                case SslError.SSL_EXPIRED:
                    message = getString(R.string.ssle2);
                    break;

                case SslError.SSL_IDMISMATCH:
                    message = getString(R.string.ssle3);
                    break;

                case SslError.SSL_NOTYETVALID:
                    message = getString(R.string.ssle4);
                    break;

            }

            alertDialog.setTitle(getString(R.string.notice));
            alertDialog.setMessage(getString(R.string.ssl)+" "+ message +" "+getString(R.string.ssl2));
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.go), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Ignore SSL certificate errors
                    handler.proceed();
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });

            alertDialog.show();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "urlp : " + url);
            if (!url.matches(".*" + URL_Check + ".*")){
                if(socialxe2015){
                    if (!url.matches(".*" + socialserver + ".*") && !url.matches(".*accounts.google.com.*") && !url.matches(".*m.facebook.com.*") && !url.matches(".*me2day.*") && !url.matches(".*api.twitter.com.*") && !url.matches(".*daum.*") && !url.matches(".*nid.naver.*")){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    }else {
                        urldown = url;
                        //view.loadUrl(url);
                        return false;
                    }
                }
                if(socialxe){
                    if(!my_domain_is_socialserver){
                        if (!url.matches(".*" + socialserver + ".*") && !url.matches(".*accounts.google.com.*") && !url.matches(".*m.facebook.com.*") && !url.matches(".*me2day.*") && !url.matches(".*api.twitter.com.*") && !url.matches(".*daum.*") && !url.matches(".*naver.*")){
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(i);
                            return true;
                        }else if(url.matches(".*" + socialserver + ".*")){
                            userAgent = mWebView.getSettings().getUserAgentString();
                            String[] array_w = userAgent.split(";");
                            String version = array_w[1].replaceAll(" ", "");
                            boolean bugs=false;

                            if(version.equals("Android4.4.4")) bugs=true;

                            if(bugs) {
                                if(urldown.startsWith(URL_home)){
                                    socialnow = true;
                                    Log.i(TAG, "getin social : " + url);
                                    String HTML = "<script>" +
                                            "function changeURL( url ) {"+
                                            "document.location = url;"+
                                            "}</script>" +
                                            "<iframe id=\"iframe\" src=\""+url+"\" width=1 height=1 border=0></iframe>";
                                    view.loadDataWithBaseURL(urldown, HTML, "text/html", "UTF-8", null);
                                }else{
                                    view.loadUrl(url);
                                }
                                return true;
                            }else{
                                urldown = url;
                                //view.loadUrl(url);
                                return false;
                            }

                        }else{
                            urldown = url;
                            //view.loadUrl(url);
                            return false;
                        }
                    }else{
                        if (!url.matches(".*accounts.google.com.*") && !url.matches(".*m.facebook.com.*") && !url.matches(".*me2day.*") && !url.matches(".*api.twitter.com.*") && !url.matches(".*daum.*") && !url.matches(".*naver.*")){
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(i);
                            return true;
                        }else {
                            urldown = url;
                            //view.loadUrl(url);
                            return false;
                        }
                    }
                }

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
                return true;

            }else if(url.matches(".*procSocialxeserverAPI.*") && my_domain_is_socialserver) {

                userAgent = mWebView.getSettings().getUserAgentString();
                String[] array_w = userAgent.split(";");
                String version = array_w[1].replaceAll(" ", "");
                boolean bugs=false;

                if(version.equals("Android4.4.4")) bugs=true;

                if(bugs) {
                    if(!socialnow2){
                        socialnow = true;
                        Log.i(TAG, "getin social : " + url);
                        String HTML = "<script>" +
                                "function changeURL( url ) {"+
                                "document.location = url;"+
                                "}</script>" +
                                "<iframe id=\"iframe\" src=\""+url+"\" width=1 height=1 border=0></iframe>";
                        view.loadDataWithBaseURL(URL_home, HTML, "text/html", "UTF-8", null);
                    }else{
                        view.loadUrl(url);
                    }
                    return true;
                }else{
                    urldown = url;
                    //view.loadUrl(url);
                    return false;
                }

            }else if(url.matches(".*procFileDownload.*")) {
                finall_url = getDownUrl(url);
                String checknetwork = checknet();
                if(checknetwork.equals("WIFI")){
                    return false;
                }else{

                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.notice)
                            .setMessage(R.string.nowifi)
                            .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    handlerr.sendEmptyMessageDelayed(22, 20);
                                }

                            })
                            .setNegativeButton(R.string.no, null)
                            .show();


                    return true;
                }
            }else if(url.matches(".*dispBoardWrite.*")){
                userAgent = mWebView.getSettings().getUserAgentString();
                String[] array_w = userAgent.split(";");
                String version = array_w[1].replaceAll(" ", "");
                boolean bugs=false;

                if(version.equals("Android4.4.2")) bugs=true;
                if(version.equals("Android4.4.1")) bugs=true;
                if(version.equals("Android4.4")) bugs=true;

                if(bugs){
                    finall_url = url;
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.writebugs)
                            .setMessage(R.string.writebugsc)
                            .setPositiveButton(R.string.yes3, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mWebView.loadUrl(finall_url);
                                }

                            })
                            .setNegativeButton(R.string.no3, null)
                            .show();

                }else{
                    view.loadUrl(url);
                    urldown = url;
                }
                return true;
            } else {
                if(url.startsWith("http")){
                    urldown = url;
                    //view.loadUrl(url);
                    return false;
                }else if(url.startsWith("line:")) {
                    boolean Line = isPackageInstalled(context, "jp.naver.line.android");
                    if(Line){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    }else{
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=jp.naver.line.android"));
                        startActivity(i);
                        return true;
                    }

                }else if(url.startsWith("kakaolink:")) {
                    boolean Kakao = isPackageInstalled(context, "com.kakao.talk");
                    if (Kakao) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    } else {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kakao.talk"));
                        startActivity(i);
                        return true;
                    }
                }else{
                    if(url.startsWith("intent:")){
                        url = url.replaceAll("intent:","");

                    }

                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
            }
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        public void onPageFinished(WebView view, String url){
            super.onPageFinished(view,url);
            if (sync_proc) {
                mWebView.loadUrl("javascript:callAndroid()");
            }
        }

    }


    public class myWebChromeClient extends WebChromeClient {

        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d("MyApplication", cm.message() + " -- From line "
                    + cm.lineNumber() + " of "
                    + cm.sourceId() );
            if(socialnow && cm.message().startsWith("Refused to display")){
                String[] Em = cm.message().split("'");
                if(Em[1].startsWith("http")){
                    socialnow = false;
                    socialnow2 = true;
                    urldown = Em[1];
                    mWebView.loadUrl(Em[1]);
                }
            }
            return true;
        }


        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
            mLoadingProgressBar.setProgress(progress);

            if (progress == 100) {
                mLoadingProgressBar.setVisibility(View.GONE);
            } else {
                mLoadingProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg)
        {
            popup=true;
            childView = new WebView(MainActivity.this);
            childView.getSettings().setJavaScriptEnabled(true);
            childView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            childView.setWebChromeClient(this);
            childView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.i(TAG, "urlc : " + url);

                    if (!url.matches(".*" + URL_Check + ".*")) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                        return true;
                    }else if(url.matches(".*dispMemberSignUpForm.*")) {
                        view.loadUrl(url);
                        return true;
                    }else{
                        mWebView.loadUrl(url);
                        onCloseWindow(view);
                        return true;
                    }
                }
            });
            childView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mWebView.setVisibility(View.GONE);
            bottomlayout.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(childView);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(childView);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            customViewContainer.setVisibility(View.GONE);
            window.destroy();
            window = null;
            mWebView.setVisibility(View.VISIBLE);
            bottomlayout.setVisibility(View.VISIBLE);
            customViewContainer.removeView(window);
            popup=false;
        }

        // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null);
        }

        // file upload callback (Android 5.0 (API level 21) -- current) (public method)
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            openFileInput(null, filePathCallback);
            return true;
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mWebView.setVisibility(View.GONE);
            bottomlayout.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            mWebView.setVisibility(View.VISIBLE);
            bottomlayout.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    private View.OnTouchListener btnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView)v;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                view.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                view.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
            }
            return false;
        }
    };


    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");

        startActivityForResult(Intent.createChooser(i, getString(R.string.chooser_title)), mRequestCodeFilePicker);

    }


    private void initial_set() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("soundonoff","true").apply();
        prefs.edit().putString("vibonoff", "true").apply();
        prefs.edit().putString("setting", "true-true-false-false-false-true-false").apply();
        prefs.edit().putString("setting_board", "none").apply();
        prefs.edit().putString("install", "false").apply();
        prefs.edit().putBoolean("fcm", true).apply();
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }


    final class MyJavaScriptInterface {

        @JavascriptInterface
        public void callAndroid(final String member,final String nick,final String setting) {

            handler.post(new Runnable() {
                public void run() {

                    String urlget_d = mWebView.getUrl();
                    member_srl = member;
                    nick_name = nick;
                    Log.i(TAG,"setting"+ setting);

                    intent_s.putExtra("member_srl", member_srl);
                    intent_s.putExtra("nick_name", nick_name);
                    intent_s.putExtra("boardset", setting);
                    intent_s.putExtra("redirect", urlget_d);
                    if (sync_proc) {
                        sync_proc = false;
                        intent_s.putExtra("sync_ok", "true");
                        if (member_srl != null) {
                            startActivityForResult(intent_s, 333);
                        }
                    } else {
                        intent_s.putExtra("sync_ok", "false");
                        startActivityForResult(intent_s, 333);
                    }

                }
            });
        }
    }

    public static Map<String, String> getQueryParser(String query) {
        Map<String, String> returnData = new HashMap<String, String>();
        StringTokenizer st = new StringTokenizer(query, "&", false);
        while (st.hasMoreElements()) {
            String paramValueToken = st.nextElement().toString();
            String[] strParamVal = paramValueToken.split("=", 2);
            String paramName = strParamVal[0];
            String paramValue = strParamVal[1];
            returnData.put(paramName, paramValue);
        }
        return returnData;
    }

    protected String getDownUrl(String link) {

        Uri url2 = Uri.parse(link);
        String queryString = url2.getQuery();
        Map<String, String> aaa = getQueryParser(queryString);
        String return_url = URL_home+"index.php?act=procAndroidpushappFiledown&file_srl="+aaa.get("file_srl")+"&sid="+aaa.get("sid");
        return return_url;
    }

    protected String checknet(){

        String return_check="mobile";

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return_check = "WIFI";
            // Do whatever
        }

        return return_check;
    }

    final class MyJavaScriptInterface_login {

        @JavascriptInterface
        public void callAndroid_login(final String str_login) {

            handler.post(new Runnable() {
                public void run() {

                    if(str_login.equals("true")){
                        url_member = "act=dispMemberInfo";
                        mWebView.loadUrl(URL_home+"index.php?"+url_member);
                    }else {
                        url_member = "act=dispMemberLoginForm";
                        urlget = mWebView.getOriginalUrl();
                        String URL_Check2="";
                        if(urlget.startsWith("http://")){
                            URL_Check2 = urlget.replaceAll("http://","");
                        }else if(urlget.startsWith("https://")){
                            URL_Check2 = urlget.replaceAll("https://","");
                        }

                        if(URL_Check2.startsWith("www.")){
                            URL_Check2 = URL_Check2.replaceAll("www.","");
                        }
                        if (URL_Check2.equals(URL_Check_xe)) {
                            mWebView.loadUrl(URL_home + "index.php?" + url_member);
                        } else {
                            if (urlget.matches(".*[?].*")) {
                                Uri url2 = Uri.parse(urlget);
                                String queryString = url2.getQuery();
                                Map<String, String> bbb = getQueryParser(queryString);
                                if (TextUtils.isEmpty(bbb.get("act"))) {
                                    mWebView.loadUrl(urlget + "&" + url_member);
                                } else {
                                    mWebView.loadUrl(urlget.replaceAll(bbb.get("act"), "dispMemberLoginForm"));
                                }
                            } else {
                                String gg = URL_Check2.replaceAll(URL_Check_xe, "");
                                String[] array_s = gg.split("/");
                                if (array_s.length > 1) {
                                    Log.i(TAG, "final url : " + URL_home + "index.php?mid=" + array_s[0] + "&document_srl=" + array_s[1] + "&" + url_member);
                                    mWebView.loadUrl(URL_home + "index.php?mid=" + array_s[0] + "&document_srl=" + array_s[1] + "&" + url_member);
                                } else {
                                    mWebView.loadUrl(URL_home + "index.php?mid=" + gg + "&" + url_member);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    final class MyJavaScriptInterface_reg {

        @JavascriptInterface
        public void callAndroid_reg(final String memb, final String nick_n, final String change_a) {

            handler.post(new Runnable() {
                public void run() {
                    Log.i(TAG, "change_a-" + change_a);
                    String setting_1="";
                    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    String registrationId = prefs.getString("regid", "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("member_srl_origin", memb + "");
                    editor.putString("nick_name_origin", nick_n + "");
                    if(change_a.equals("Y")){
                        setting_1 = "true-true-true-true-true-true-true";
                        editor.putString("setting", setting_1);
                    }else{
                        setting_1 = "false-false-true-true-true-true-true";
                        editor.putString("setting", setting_1);
                    }
                    editor.putString("install", "true");
                    editor.commit();

                    Toast.makeText(getApplicationContext(), R.string.syn,
                            Toast.LENGTH_LONG).show();
                    String final_urlss = URL_home + "index.php?act=procAndroidpushappSync&member_srl="+memb +"&reg_id="+registrationId+"&setting="+setting_1+"&redirect_url="+URL_home;
                    Log.i(TAG, "final url : " + final_urlss);
                    mWebView.loadUrl(final_urlss);
                }
            });
        }
    }


    final class MyJavaScriptInterface_ins {

        @JavascriptInterface
        public void callAndroid_ins() {

            handler.post(new Runnable() {
                public void run() {
                    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("install", "true");
                    editor.commit();
                    mWebView.loadUrl(URL_home);
                }
            });
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

            if(popup){

                customViewContainer.setVisibility(View.GONE);
                childView.destroy();
                childView = null;
                mWebView.setVisibility(View.VISIBLE);
                bottomlayout.setVisibility(View.VISIBLE);
                customViewContainer.removeView(childView);
                popup=false;
                return true;

            }

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }else{
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.quit)
                        .setMessage(R.string.really_quit)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Stop the activity
                                MainActivity.this.finish();
                            }

                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    public static boolean isPackageInstalled(Context ctx, String pkgName) {
        try {
            ctx.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        Log.i(" File -",
                "Authority: " + uri.getAuthority() +
                        ", Fragment: " + uri.getFragment() +
                        ", Port: " + uri.getPort() +
                        ", Query: " + uri.getQuery() +
                        ", Scheme: " + uri.getScheme() +
                        ", Host: " + uri.getHost() +
                        ", Segments: " + uri.getPathSegments().toString()
        );

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    Log.i(TAG, "filepath : " + Environment.getExternalStorageDirectory() + "/" + split[1]);
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                Log.i(TAG, "filepath : " + getDataColumn(context, contentUri, null, null));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };
                Log.i(TAG, "filepath : " + getDataColumn(context, contentUri, selection, selectionArgs));
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.i(TAG, "filepath : " + getDataColumn(context, uri, null, null));
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.i(TAG, "filepath : " + uri.getPath());
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}


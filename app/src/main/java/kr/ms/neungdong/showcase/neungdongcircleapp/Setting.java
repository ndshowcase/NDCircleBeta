package kr.ms.neungdong.showcase.neungdongcircleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by user on 2015-04-11.
 */
public class Setting extends Activity {

    String URL_home;
	
    String layout_sort;
    private ListView m_ListView;
    private CustomAdapter m_Adapter;

    String[] array_board;
    String[] board_name;
    String[] board_srl;
    boolean[] board_boolean;

    Context context;
	Button btn1;
    Button btn2;
	Button btn3;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    CheckBox checkBoxall;
    RelativeLayout layout;
    TextView textView;

    boolean toast_i = true;
	boolean status1 = false;
    boolean status2 = false;
    boolean status3 = false;	
    boolean status4 = false;
    boolean status5 = false;
    boolean status6 = false;
    boolean status7 = false;
    boolean status8 = false;
	boolean status9 = false;

    String member_srl;
    String nick_name;
    String redirect;
    String Url_final;
    String sync_ok=null;

    String setting;
    String boardset;
    String nick_name_origin;
    String member_srl_origin;
    String registrationId;
    Intent intent_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		layout_sort = QuickstartPreferences.layout_sort;
        URL_home = QuickstartPreferences.URL_home;
        intent_back = getIntent();

        switch (layout_sort){
            case "1" : setContentView(R.layout.setting_main); break;
			case "2" : setContentView(R.layout.setting_main2); break;
			case "3" : setContentView(R.layout.setting_main3); break;
        }
        
        context = getApplicationContext();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent2 = getIntent();
        member_srl = intent2.getStringExtra("member_srl");
        nick_name = intent2.getStringExtra("nick_name");
        boardset = intent2.getStringExtra("boardset");
        redirect = intent2.getStringExtra("redirect");
        sync_ok = intent2.getStringExtra("sync_ok");


        Log.i("Setting", "boardset:"+boardset );

		array_board = boardset.split("/-");
		board_srl = new String[array_board.length];
		board_name = new String[array_board.length];
		board_boolean = new boolean[array_board.length];

		for(int i=0;i<array_board.length;i++){
			Log.i("Setting", "array_board:"+array_board[i] );
            array_board[i] = array_board[i].replaceAll("aa123and123aa","&");
            array_board[i] = array_board[i].replaceAll("aa123sharp123aa","#");
            array_board[i] = array_board[i].replaceAll("aa123que123aa","?");
            array_board[i] = array_board[i].replaceAll("aa123end123aa",";");
			String[] array_board_a = array_board[i].split("%#");
			board_name[i] = array_board_a[0];
			board_srl[i] = array_board_a[1];
			board_boolean[i] = true;
		}		

        textView =  (TextView) findViewById(R.id.textView66);

        checkBox1=(CheckBox) findViewById(R.id.checkBox1);
        checkBox2=(CheckBox) findViewById(R.id.checkBox2);
        checkBox3=(CheckBox) findViewById(R.id.checkBox3);
        checkBox4=(CheckBox) findViewById(R.id.checkBox4);
        checkBox5=(CheckBox) findViewById(R.id.checkBox5);
        checkBox6=(CheckBox) findViewById(R.id.checkBox6);
        checkBox7=(CheckBox) findViewById(R.id.checkBox7);
        checkBox8=(CheckBox) findViewById(R.id.checkBox8);
        checkBox9=(CheckBox) findViewById(R.id.checkBox9);

        checkBoxall=(CheckBox) findViewById(R.id.checkBoxall);
        layout=(RelativeLayout) findViewById(R.id.layout);
        if(layout_sort.equals("3")) layout.setVisibility(View.GONE);

		btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        registrationId = prefs.getString("regid", "");
        Log.i("Setting", "registrationId:"+registrationId );

        if(prefs.contains("soundonoff"))
        {
            status8 = Boolean.parseBoolean(prefs.getString("soundonoff", ""));
            if (status8) {
                checkBox8.setChecked(true);
            } else {
                checkBox8.setChecked(false);
            }
        }

        if(prefs.contains("vibonoff"))
        {
            status9 = Boolean.parseBoolean(prefs.getString("vibonoff", ""));
            if (status9) {
                checkBox9.setChecked(true);
            } else {
                checkBox9.setChecked(false);
            }
        }

        if(!layout_sort.equals("3")) {
            if (prefs.contains("setting_board")) {
                String setting_board_in = prefs.getString("setting_board", "");
                Log.i("Setting", "setting_board:" + setting_board_in);
                if (!setting_board_in.equals("none")) {
                    String[] array_board_in = setting_board_in.split("/-");
                    for (int i = 0; i < array_board_in.length; i++) {
                        int find = -1;
                        String[] array_board_b = array_board_in[i].split("%#");
                        for (int j = 0; j < board_srl.length; j++) {
                            if (board_srl[j].equals(array_board_b[0])) find = j;
                        }
                        if (find > 0) {
                            board_boolean[find] = Boolean.parseBoolean(array_board_b[1]);
                        }
                    }
                }
            }
        }

        if(prefs.contains("setting"))
        {
            setting = prefs.getString("setting", "");
			String[] array_setting = setting.split("-");
			status1 = Boolean.parseBoolean(array_setting[0]);
			status2 = Boolean.parseBoolean(array_setting[1]);
			status3 = Boolean.parseBoolean(array_setting[2]);
			status4 = Boolean.parseBoolean(array_setting[3]);
			status5 = Boolean.parseBoolean(array_setting[4]);
			status6 = Boolean.parseBoolean(array_setting[5]);
			status7 = Boolean.parseBoolean(array_setting[6]);

			if (status1) {
                checkBox1.setChecked(true);
            } else {
                checkBox1.setChecked(false);
            }

			if (status2) {
                checkBox2.setChecked(true);
            } else {
                checkBox2.setChecked(false);
            }

			if (status6) {
                checkBox6.setChecked(true);
            } else {
                checkBox6.setChecked(false);
            }

			if(prefs.contains("member_srl_origin")&&prefs.contains("nick_name_origin"))
			{
				member_srl_origin = prefs.getString("member_srl_origin", "");
				nick_name_origin = prefs.getString("nick_name_origin", "");

				Log.i("Setting",member_srl_origin+nick_name_origin+member_srl+nick_name );

				checkBox3.setClickable(true);
				checkBox4.setClickable(true);
				checkBox5.setClickable(true);
				checkBox7.setClickable(true);
				textView.setText(nick_name_origin + getString(R.string.syn_com));

				if(member_srl != null){
					if(member_srl_origin.equals(member_srl)){
						btn2.setText(getString(R.string.syn_ano));
					}else{
						btn2.setText(nick_name + getString(R.string.syn_re));
					}
				}else{
					btn2.setText(getString(R.string.syn_ano));
				}


				if (status3) {
					checkBox3.setChecked(true);
				} else {
					checkBox3.setChecked(false);
				}

				if (status4) {
					checkBox4.setChecked(true);
				} else {
					checkBox4.setChecked(false);
				}

				if (status5) {
					checkBox5.setChecked(true);
				} else {
					checkBox5.setChecked(false);
				}
				

				if (status7) {
					checkBox7.setChecked(true);
				} else {
					checkBox7.setChecked(false);
				}

			}else{
				textView.setText(getString(R.string.syn_not));
				if(member_srl != null){
					btn2.setText(nick_name + getString(R.string.syn_now));
				}else{
					btn2.setText(getString(R.string.syn_first));
				}

			}
		}

        m_ListView = (ListView) findViewById(R.id.listView);

        if(layout_sort.equals("3")) m_ListView.setVisibility(View.GONE);

        if(!layout_sort.equals("3")){

            m_Adapter = new CustomAdapter();
            m_ListView.setAdapter(m_Adapter);

            for(int i=0;i<array_board.length;i++){
                m_Adapter.add(board_name[i]);
            }

            m_ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            m_ListView.setDivider(new ColorDrawable(Color.BLACK));
            m_ListView.setDividerHeight(2);
            setListViewHeightBasedOnChildren(m_ListView);
        }

        Url_final = URL_home + "index.php?act=procAndroidpushappSync&reg_id="+registrationId+"&member_srl="+member_srl+"&redirect_url="+redirect;


        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         Toast.makeText(getApplicationContext(), R.string.sound_on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status8 = true;
                                                         SharedPreferences.Editor editor = prefs.edit();
                                                         editor.putString("soundonoff", status8 + "");
                                                         editor.commit();
                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.sound_off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status8 = false;
                                                         SharedPreferences.Editor editor = prefs.edit();
                                                         editor.putString("soundonoff", status8 + "");
                                                         editor.commit();
                                                     }
                                                 }
                                             }
        );

        checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                              boolean isChecked) {
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         Toast.makeText(getApplicationContext(), R.string.vib_on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status9 = true;
                                                         SharedPreferences.Editor editor = prefs.edit();
                                                         editor.putString("vibonoff", status9 + "");
                                                         editor.commit();
                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.vib_off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status9 = false;
                                                         SharedPreferences.Editor editor = prefs.edit();
                                                         editor.putString("vibonoff", status9 + "");
                                                         editor.commit();
                                                     }
                                                 }
                                             }
        );

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         Toast.makeText(getApplicationContext(), R.string.doc1on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status1 = true;
                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.doc1off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status1 = false;

                                                     }
                                                 }
                                             }
        );



        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com1on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status2 = true;
                                                         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                                         member_srl_origin = prefs.getString("member_srl_origin", "");
                                                         if(member_srl_origin != null){
                                                             toast_i=false;
                                                             checkBox3.setChecked(true);
                                                             checkBox4.setChecked(true);
                                                             checkBox5.setChecked(true);
                                                             toast_i=true;
                                                         }

                                                     } else {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com1off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status2 = false;

                                                     }
                                                 }
                                             }
        );

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com2on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status3 = true;

                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.com2off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status3 = false;
                                                         toast_i=false;
                                                         checkBox2.setChecked(false);
                                                         toast_i=true;

                                                     }
                                                 }
                                             }
        );
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com3on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status4 = true;

                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.com3off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status4 = false;
                                                         toast_i=false;
                                                         checkBox5.setChecked(false);
                                                         checkBox2.setChecked(false);
                                                         toast_i=true;

                                                     }
                                                 }
                                             }
        );

        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.msg1on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status7 = true;

                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.msg1off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status7 = false;

                                                     }
                                                 }
                                             }
        );

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com4on,
                                                                 Toast.LENGTH_LONG).show();
                                                         status5 = true;
                                                         checkBox4.setChecked(true);

                                                     } else {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.com4off,
                                                                 Toast.LENGTH_LONG).show();
                                                         status5 = false;
                                                         checkBox2.setChecked(false);
                                                     }
                                                 }
                                             }
        );

        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {
                                                 @Override
                                                 public void onCheckedChanged (CompoundButton buttonView,
                                                                               boolean isChecked){
                                                     // TODO Auto-generated method stub
                                                     if (isChecked) {
                                                         if(toast_i) Toast.makeText(getApplicationContext(), R.string.pushon,
                                                                 Toast.LENGTH_LONG).show();
                                                         status6 = true;

                                                     } else {
                                                         Toast.makeText(getApplicationContext(), R.string.pushoff,
                                                                 Toast.LENGTH_LONG).show();
                                                         status6 = false;
                                                     }
                                                 }
                                             }
        );



        checkBoxall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                               {
                                                   @Override
                                                   public void onCheckedChanged (CompoundButton buttonView,
                                                                                 boolean isChecked){
                                                       // TODO Auto-generated method stub
                                                       if (isChecked) {
                                                           for(int i=0;i<array_board.length;i++){
                                                               m_ListView.setItemChecked(i,true);
                                                               board_boolean[i]=true;
                                                           }
                                                           m_Adapter.notifyDataSetChanged();
                                                       } else {
                                                           for(int i=0;i<array_board.length;i++){
                                                               m_ListView.setItemChecked(i,false);
                                                               board_boolean[i]=false;
                                                           }
                                                           m_Adapter.notifyDataSetChanged();
                                                       }
                                                   }
                                               }
        );



        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendSettingToServer();
                                        intent_back.putExtra("login", "N");
                                        intent_back.putExtra("sync", "N");
                                        setResult(RESULT_OK,intent_back);
                                        finish();
                                    }
                                }
        );

        btn3.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick (View v){

                                        sendSettingToServer();
                                        intent_back.putExtra("login", "N");
                                        intent_back.putExtra("sync", "N");
                                        setResult(RESULT_OK,intent_back);
                                        finish();
                                    }
                                }
        );
        btn2.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick (View v){

                                        if(sync_ok.equals("true")){
                                            intent_back.putExtra("login", "New");
                                            intent_back.putExtra("sync", "N");
                                            setResult(RESULT_OK,intent_back);
                                            finish();
                                        }else if(member_srl != null){
                                            if(member_srl_origin != null){
                                                if(member_srl_origin.equals(member_srl)){
                                                    intent_back.putExtra("login", "New");
                                                    intent_back.putExtra("sync", "N");
                                                    setResult(RESULT_OK,intent_back);
                                                    finish();
                                                }else{

                                                    intent_back.putExtra("login", "N");
                                                    intent_back.putExtra("sync", "Y");
                                                    intent_back.putExtra("url", Url_final);
                                                    setResult(RESULT_OK, intent_back);
                                                    finish();
                                                }
                                            }else{
                                                intent_back.putExtra("login", "N");
                                                intent_back.putExtra("sync", "Y");
                                                intent_back.putExtra("url", Url_final);
                                                setResult(RESULT_OK,intent_back);
                                                finish();
                                            }

                                        }else{
                                            if(member_srl_origin != null){
                                                intent_back.putExtra("login", "New");
                                                intent_back.putExtra("sync", "N");
                                                setResult(RESULT_OK,intent_back);
                                                finish();
                                            }else{
                                                intent_back.putExtra("login", "Y");
                                                intent_back.putExtra("sync", "N");
                                                setResult(RESULT_OK,intent_back);
                                                finish();
                                            }
                                        }
                                    }
                                }
        );

        if(sync_ok.equals("true")){

            storemember_srl(context, member_srl, nick_name);
            textView.setText(nick_name + getString(R.string.syn_com));
            btn2.setText(getString(R.string.syn_ano));
            toast_i = false;
            checkBox4.setClickable(true);
            checkBox4.setChecked(true);
            checkBox5.setClickable(true);
            checkBox5.setChecked(true);
            checkBox3.setClickable(true);
            checkBox3.setChecked(true);
            checkBox7.setClickable(true);
            checkBox7.setChecked(true);
            toast_i = true;

            Toast.makeText(getApplicationContext(), R.string.syn,
                    Toast.LENGTH_LONG).show();

        }

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            totalHeight += 34;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }


    private void storemember_srl(Context context, String member_srl, String nick_name) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("member_srl_origin", member_srl + "");
        editor.putString("nick_name_origin", nick_name + "");
        editor.commit();
    }

	private void sendSettingToServer() {

		String sort_1 = String.valueOf(status1);
		String sort_2 = String.valueOf(status2);
		String sort_3 = String.valueOf(status3);
		String sort_4 = String.valueOf(status4);
		String sort_5 = String.valueOf(status5);
		String sort_6 = String.valueOf(status6);
		String sort_7 = String.valueOf(status7);
		String setting_send = sort_1 + "-" + sort_2 + "-" + sort_3 + "-" + sort_4 + "-" + sort_5 + "-" + sort_6 + "-" + sort_7;

		String[] board = new String[array_board.length];
		String setting_send2 = "";
		for(int i=0;i<array_board.length;i++){
			board[i]=String.valueOf(board_boolean[i]);
			if(i==0){
				setting_send2 = board_srl[i]+"%#"+board[i];
			}else{
				setting_send2 = setting_send2 +"/-"+ board_srl[i]+"%#"+board[i];
			}
		}

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("setting", setting_send + "");
        editor.putString("setting_board", setting_send2 + "");
        editor.commit();

		Log.i("Setting", "setting:" + setting_send);
		Log.i("Setting", "setting2:" + setting_send2);

		Intent intent_s = new Intent(this, Sendserver.class);
		intent_s.putExtra("act", "procAndroidpushappSyncSetting");
        intent_s.putExtra("registrationId", registrationId);
		intent_s.putExtra("setting_push", setting_send);
		intent_s.putExtra("setting_board", setting_send2);		
        startActivity(intent_s);
    }
    private void login() {
        intent_back.putExtra("login", "Y");
        setResult(RESULT_OK,intent_back);
    }

    public class CustomAdapter extends BaseAdapter {

        private ArrayList<String> m_List;

        public CustomAdapter() {
            m_List = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return m_List.size();
        }

        @Override
        public Object getItem(int position) {
            return m_List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            CheckedTextView ctv=null;

            if ( convertView == null ) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            }
            ctv = (CheckedTextView) convertView.findViewById(android.R.id.text1);
            ctv.setText("   " + m_List.get(position));
            ctv.setClickable(true);
            ((ListView)parent).setItemChecked(position, board_boolean[position]);
            ctv.setChecked(((ListView)parent).isItemChecked(position));

            ctv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CheckedTextView view = (CheckedTextView) v;
                    view.toggle();
                    Log.i("Setting", "isChecked?  " + view.isChecked() + "  position: " + pos);
                    if(view.isChecked()){
                        board_boolean[pos]=true;
                    }else{
                        board_boolean[pos]=false;
                    }
                }
            });

            return convertView;
        }


        public void add(String _msg) {
            m_List.add(_msg);
        }

        public void remove(int _position) {
            m_List.remove(_position);
        }

    }

}





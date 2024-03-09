package com.xero.ui;

import android.content.Context;
import android.content.DialogInterface;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.cardview.widget.CardView;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;

public class LoginActivity extends Activity {

    static {
        System.loadLibrary("native");
    }

    private native String getkey();
    private native String titleLie();
    private final String USER = "USER";
	
 //   private final String PASS = "PASS";
    private Prefs prefs;

    private void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setStatusBarColor(Color.parseColor("#FF121212"));
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FF121212"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLightStatusBar(this);
        setContentView(R.layout.activity_login);
        if (SplashActivity.jams !=20) {
            finish();
        }
        prefs = Prefs.with(this);
        TextView textUsername = findViewById(R.id.key);
		//    TextView textPassword = findViewById(R.id.textPassword);
        textUsername.setText(prefs.read(USER, ""));
		//      textPassword.setText(prefs.read(PASS, ""));

        // Your existing code...
        
        final TextView text1 = findViewById(R.id.home);
        text1.setText(titleLie());
        text1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
		
	/*	final ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		ImageView btnPaste = findViewById(R.id.pastee);
		btnPaste.setOnClickListener(new View.OnClickListener() {
				@Override              
				public void onClick(View v) {
					ClipData abc = myClipboard.getPrimaryClip();
					ClipData.Item item = abc.getItemAt(0);
					String text = item.getText().toString();
					TextView textUsername = findViewById(R.id.textUsername);
					textUsername.setText(text);
				}
			});*/

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView textUsername = findViewById(R.id.key);
					String userKey = textUsername.getText().toString();
					if (userKey.trim().equals("")) {
						Toast.makeText(LoginActivity.this, "请输入卡密 ", Toast.LENGTH_SHORT).show();
						return;
					} else {
					Login(LoginActivity.this, userKey);
					}
				}
			});
        Button getKey = findViewById(R.id.getKey);
        getKey.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(getkey()));
					startActivity(intent);
				}
			});

    }
	// Other code...

    private static void Login(final Context m_Context, final String userKey) {
        final ProgressDialog progressDialog = new ProgressDialog(m_Context, 5);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("验证中...");
		
        progressDialog.setCancelable(false);
        progressDialog.show();
	
        final Handler loginHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    // Successful login, proceed to download and move assets
					
                    Intent i = new Intent(m_Context.getApplicationContext(), MainActivity.class);
                    m_Context.startActivity(i);
                } else if (msg.what == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(m_Context, 5);
                    builder.setTitle("卡密错误");
                    builder.setMessage(msg.obj.toString());
                    builder.setCancelable(false);
                    builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								System.exit(0);
							}
						});
                    builder.show();
                }
                progressDialog.dismiss();
            }
        };

        new Thread(new Runnable() {
				@Override
				public void run() {
					String result = Check(m_Context, userKey);
					if (result.equals("OK")) {
						loginHandler.sendEmptyMessage(0);
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = result;
						loginHandler.sendMessage(msg);
					}
				}
			}).start();
    }

    
	

private static native String Check(Context mContext, String userKey);

    }

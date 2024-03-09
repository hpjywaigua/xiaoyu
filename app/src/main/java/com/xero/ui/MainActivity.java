package com.xero.ui;

import com.xero.ui.R;
import com.xero.ui.FloatingActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.os.*;
import android.os.Build;
import android.util.*;
import android.util.Log;
import android.os.HandlerThread;
import android.content.Intent;
import android.view.*;
import android.view.View.*;
import android.view.View;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Button;
import android.widget.ImageView;
import java.util.*;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ProgressBar;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Process;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.graphics.Color;
import java.text.SimpleDateFormat;
import android.graphics.Typeface;
import android.widget.CompoundButton;
import androidx.cardview.widget.CardView;

public class MainActivity extends Activity {
    
	String gameName = "com.pubg.imobile";
	static int gameType = 5;
	
    static {
        System.loadLibrary("native");
    }
	
	private void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setStatusBarColor(Color.parseColor("#FF121212"));
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FF121212"));
        }
    }
	
	public static int REQUEST_OVERLAY_PERMISSION = 5469;
    static boolean vercheck;
    private static native String Tele();
	
    public static int hiderec_sw=0;
	private TextView modeexc;
	private native String titleLie();
	private Timer waktu = new Timer();
    private TimerTask Waktu;
    public static native String EXP();
	private native String Dev();
    private native String titleShooter();
	
    public static String TimeExpired;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setLightStatusBar(this);
        setContentView(R.layout.activity_main);
        TimeExpired = EXP();
        loadMain();
		modeexc = findViewById(R.id.modeexc);
		CountTimerAccout();
		permissionWindows();
		
		final TextView text1 = findViewById(R.id.text1);
        text1.setText(titleLie());
        text1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));

        final TextView text2 = findViewById(R.id.text2);
        text2.setText(titleShooter());
        text2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
		
		final TextView text3 = findViewById(R.id.text3);
        text3.setText(Dev());
        text3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
		
		final ImageView icrec=findViewById(R.id.icrec);
        icrec.setBackgroundResource(R.drawable.ic_rec);
		
		if (isRootGiven()) {
            modeexc.setText("Root Mode");
			modeexc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
		} else {
            modeexc.setText("Container Mode");
			modeexc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
		}
		
		final Switch hiderec = findViewById(R.id.hiderec);
        hiderec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        hiderec_sw=1;
                        icrec.setBackgroundResource(R.drawable.ic_rec_on);
                    } else {
                        hiderec_sw=0;
                        icrec.setBackgroundResource(R.drawable.ic_rec);
                    }
                }
            });
			
		CardView logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
					startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
              }
			  });
			  
        CardView telegram = findViewById(R.id.telegram);

        telegram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoToTelegram();
                }
            });

		CardView startButton = findViewById(R.id.starthack);
		startButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
                    if (vercheck) {
                        if (isRootGiven()) {
    //                        if (gameType == 5) {
                                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this); 
                                final Handler handle = new Handler() { 
                                    @Override 
                                    public void handleMessage(Message msg) {
                                        super.handleMessage(msg); 
                                        progressDialog.incrementProgressBy(1); 
                                    } 
                                };                 
                                progressDialog.setMessage("Please Wait..."); 
                                progressDialog.show(); 
                                new Thread(new Runnable() { 
                                        @Override public void run() { 
                                            try { 
                                                while (progressDialog.getProgress() <= progressDialog.getMax()) { 
                                                    Thread.sleep(200); 

                                                    progressDialog.dismiss(); 

                                                } 
                                            } catch (Exception e) {
                                                e.printStackTrace(); 
                                            } 
                                        } 
                                    }).start();   
                                Execute("");
                            }
                        }
                        startFloating();     

                    } 
                
			});



		CardView stopButton = findViewById(R.id.stophack);
		stopButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {         
					stopFloating();
				}
			});

    }

    private void GoToTelegram() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Tele()));
        startActivity(browserIntent);
    }

	private void startFloating() {
		startService(new Intent(this, FloatingActivity.class));
	}

	private void stopFloating() {
        stopService(new Intent(this, Overlay.class));
		stopService(new Intent(this, FloatAimbot.class));
		stopService(new Intent(this, FloatingActivity.class));
	}

	private void loadMain() {
            MoveAssets(getFilesDir() + "/", "Logo");
            //Move Your CPP From Assets To Injector Dir

	}

	private void permissionWindows() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("This application requires window overlays access permission, please allow first.");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface p1, int p2) {
							Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
													   Uri.parse("package:" + getPackageName()));
                            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
						}
                    });
				builder.setCancelable(false);
				builder.show();
            }
        }
	}

    public static boolean isRootAvailable() {
        for (String pathDir : System.getenv("PATH").split(":")) {
            if (new File(pathDir, "su").exists()) {
                return true;
            }
        }
        return false;
    }






    public static boolean isRootGiven() {
        if (isRootAvailable()) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{"su", "-c", "id"});
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String output = in.readLine();
                if (output != null && output.toLowerCase().contains("uid=0"))
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process != null)
                    process.destroy();
            }
        }

        return false;
    }

    public void Execute(String path) {
        try {
            ExecuteElf("chmod 777 " + getFilesDir() + path);//VIRTUAL
            ExecuteElf(getFilesDir() + path);
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);//ROOT
            ExecuteElf("su -c " + getFilesDir() + path);
        } catch (Exception e) {
        }
    }

    private void ExecuteElf(String shell) {
        try {
            Runtime.getRuntime().exec(shell, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    private boolean MoveAssets(String outPath, String fileName) {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("--Method--", "copyAssetsSingleFile: cannot create directory.");
                return false;
            }
        }
        try {
            InputStream inputStream = getAssets().open(fileName);
            File outFile = new File(file, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	Handler handler = new Handler();
	private void CountTimerAccout() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date expiryDate = dateFormat.parse(TimeExpired);
                    long now = System.currentTimeMillis();
                    long distance = expiryDate.getTime() - now;
                    long days = distance / (24 * 60 * 60 * 1000);
                    long hours = distance / (60 * 60 * 1000) % 24;
                    long minutes = distance / (60 * 1000) % 60;
                    long seconds = distance / 1000 % 60;
                    if (distance < 0) {
                    } else {
                        TextView Hari = findViewById(R.id.tv_d);
                        TextView Jam = findViewById(R.id.tv_h);
                        TextView Menit = findViewById(R.id.tv_m);
                        TextView Detik = findViewById(R.id.tv_s);
                        if (days > 0) {
                            Hari.setText(" " + String.format("%02d", days));
                        }
                        if (hours > 0) {
                            Jam.setText(" " + String.format("%02d", hours));
                        }
                        if (minutes > 0) {
                            Menit.setText(" " + String.format("%02d", minutes));
                        }
                        if (seconds > 0) {
                            Detik.setText(" " + String.format("%02d", seconds));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    
		@Override
	public void onDestroy() {
		super.onDestroy();
		finishAffinity();
    }
} 

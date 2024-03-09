package com.xero.ui;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.provider.*;
import android.net.*;
import android.graphics.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity
{
	static {
        System.loadLibrary("native");
    }
	
    private ProgressBar progressBar;
    private Timer timer;
    private int i=0;
    static int jams =0;
	private static native String Dev();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		TextView dev = findViewById(R.id.dev);
		dev.setText(Dev());
		dev.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font.ttf"));
        
        progressBar = findViewById(R.id.progressBar);

        timer=new Timer();
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //this repeats every 100 ms
                    if (i<300){
                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        progressBar.setProgress(i);
                        i++;
                    }else{
                        //closing the timer
                        timer.cancel();
                        jams =20;
                        Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                        // close this activity
                        finish();
                    }
                }
            }, 0,5);
			
		Animation in = AnimationUtils.loadAnimation(this, R.anim.translate_scale_rotate);
        overridePendingTransition(0, 0);
        ImageView logo = findViewById(R.id.logo);     
        logo.startAnimation(in);
    }

}





package com.xero.ui;

import com.xero.ui.R;
import com.xero.ui.MainActivity;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.os.PowerManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.os.Build;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.app.AlertDialog;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.content.SharedPreferences;
import android.widget.RadioButton;
import java.io.File;
import android.os.*;
import android.graphics.Color;
import android.content.pm.PackageManager;
import android.app.ActivityManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.CompoundButton;
import java.util.logging.Handler;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.content.Intent;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.IOException;
import android.graphics.Typeface;

import static com.xero.ui.Overlay.getConfig;
import android.widget.ProgressBar;

public class FloatingActivity extends Service {

    static {
        System.loadLibrary("native");
    }

	private View mainView;
    public static String socket;
	public String daemonPath;
	private WindowManager windowManager;
	private WindowManager.LayoutParams paramsView;
	private LinearLayout mainFloatView,miniFloatView,memoryBtn,espBtn,itemBtn,aimBtn,setBtn,menu1,menu5,menu2,menu3,menu4,memenu,carBtn;
    private ImageView closeButton;
    private static native String txtESP();
    private static native String txtINJ();
    private int    mFPS = 0;
    private int    mFPSCounter = 0;
    private long   mFPSTime = 0;
    private TextView textfps;
    private static boolean floatAimbot=false;
    
    public native void recoil(int recoil);
    public native void distances(int distances);
    public native void Bulletspeed(int bulletspeed);
    public native void SettingMemory(int setting_code, boolean value);
    public native void Range(int range);
    public native void Target(int target);
    public native void AimBy(int aimby);
    public native void AimWhen(int aimwhen);
    
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public void onCreate() {
        super.onCreate();
        loadAssets(); 
        ShowMainView();
        loadFunc();
        
        final TextView dev = mainView.findViewById(R.id.dev);
        dev.setText(txtESP());
        dev.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font1.ttf"));
        
        memoryBtn = mainView.findViewById(R.id.memory);
        espBtn = mainView.findViewById(R.id.espBtn);
        itemBtn = mainView.findViewById(R.id.itemBtn);
        carBtn = mainView.findViewById(R.id.carBtn);
        aimBtn = mainView.findViewById(R.id.aimBtn);
        setBtn = mainView.findViewById(R.id.setBtn);
     
        menu1 = mainView.findViewById(R.id.menu1);
        menu2 = mainView.findViewById(R.id.menu2);
        menu3 = mainView.findViewById(R.id.menu3);
        menu4 = mainView.findViewById(R.id.menu4);
        menu5 = mainView.findViewById(R.id.menu5);
        memenu = mainView.findViewById(R.id.memenu);
  
        memoryBtn.setBackgroundResource(R.drawable.menu_round);
        
        final LinearLayout memory1 = mainView.findViewById(R.id.memory1);
        final LinearLayout memory2 = mainView.findViewById(R.id.memory2);
        final LinearLayout memory3 = mainView.findViewById(R.id.memory3);
        final LinearLayout bypassmenu = mainView.findViewById(R.id.bypassmenu);
        final LinearLayout memorymenu = mainView.findViewById(R.id.memorymenu);
        final LinearLayout whmenu = mainView.findViewById(R.id.whmenu);
        
        
        memory1.setBackgroundResource(R.drawable.menu_round);
        
        memory1.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    memory1.setBackgroundResource(R.drawable.menu_round);
                    memory2.setBackgroundResource(R.drawable.round);
                    memory3.setBackgroundResource(R.drawable.round);
                    bypassmenu.setVisibility(View.VISIBLE);
                    memorymenu.setVisibility(View.GONE);
                    whmenu.setVisibility(View.GONE);
                }
            });
            
        memory2.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    memory1.setBackgroundResource(R.drawable.round);
                    memory2.setBackgroundResource(R.drawable.menu_round);
                    memory3.setBackgroundResource(R.drawable.round);
                    bypassmenu.setVisibility(View.GONE);
                    memorymenu.setVisibility(View.VISIBLE);
                    whmenu.setVisibility(View.GONE);
                }
            });
        memory3.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    memory1.setBackgroundResource(R.drawable.round);
                    memory2.setBackgroundResource(R.drawable.round);
                    memory3.setBackgroundResource(R.drawable.menu_round);
                    bypassmenu.setVisibility(View.GONE);
                    memorymenu.setVisibility(View.GONE);
                    whmenu.setVisibility(View.VISIBLE);
                }
            });

   
        

        memoryBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    memoryBtn.setBackgroundResource(R.drawable.menu_round);
                    espBtn.setBackgroundColor(Color.TRANSPARENT);
                    itemBtn.setBackgroundColor(Color.TRANSPARENT);   
                    setBtn.setBackgroundColor(Color.TRANSPARENT);
                    carBtn.setBackgroundColor(Color.TRANSPARENT);
                    aimBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.VISIBLE);
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    menu5.setVisibility(View.GONE);
                    
                    dev.setText(txtINJ());
                }
            });

        itemBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    itemBtn.setBackgroundResource(R.drawable.menu_round);
                    espBtn.setBackgroundColor(Color.TRANSPARENT);
                    memoryBtn.setBackgroundColor(Color.TRANSPARENT);  
                    carBtn.setBackgroundColor(Color.TRANSPARENT);
                    aimBtn.setBackgroundColor(Color.TRANSPARENT);
                    setBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.GONE);
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.VISIBLE);  
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    menu5.setVisibility(View.GONE);
                    
                    dev.setText(txtESP());
                }
            });
        espBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    espBtn.setBackgroundResource(R.drawable.menu_round);
                    itemBtn.setBackgroundColor(Color.TRANSPARENT);
                    memoryBtn.setBackgroundColor(Color.TRANSPARENT);
                    carBtn.setBackgroundColor(Color.TRANSPARENT);
                    aimBtn.setBackgroundColor(Color.TRANSPARENT);
                    setBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.GONE);
                    menu1.setVisibility(View.VISIBLE);
                    menu2.setVisibility(View.GONE);     
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    menu5.setVisibility(View.GONE);
                    
                    dev.setText(txtESP());
                }
            });
            
        carBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    carBtn.setBackgroundResource(R.drawable.menu_round);
                    espBtn.setBackgroundColor(Color.TRANSPARENT);
                    itemBtn.setBackgroundColor(Color.TRANSPARENT);
                    memoryBtn.setBackgroundColor(Color.TRANSPARENT);
                    aimBtn.setBackgroundColor(Color.TRANSPARENT);
                    setBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.GONE);
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.VISIBLE);    
                    menu4.setVisibility(View.GONE);
                    menu5.setVisibility(View.GONE);
                    
                    dev.setText(txtESP());
                }
            });
            
        aimBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    aimBtn.setBackgroundResource(R.drawable.menu_round);
                    espBtn.setBackgroundColor(Color.TRANSPARENT);
                    itemBtn.setBackgroundColor(Color.TRANSPARENT);
                    memoryBtn.setBackgroundColor(Color.TRANSPARENT);
                    carBtn.setBackgroundColor(Color.TRANSPARENT);
                    setBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.GONE);
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.GONE);
                    menu5.setVisibility(View.VISIBLE);
                    
                    dev.setText(txtESP());
                }
            });


        setBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {         
                    setBtn.setBackgroundResource(R.drawable.menu_round);
                    espBtn.setBackgroundColor(Color.TRANSPARENT);
                    itemBtn.setBackgroundColor(Color.TRANSPARENT);
                    memoryBtn.setBackgroundColor(Color.TRANSPARENT);
                    carBtn.setBackgroundColor(Color.TRANSPARENT);
                    aimBtn.setBackgroundColor(Color.TRANSPARENT);
                    memenu.setVisibility(View.GONE);
                    menu1.setVisibility(View.GONE);
                    menu2.setVisibility(View.GONE);
                    menu3.setVisibility(View.GONE);
                    menu4.setVisibility(View.VISIBLE);
                    menu5.setVisibility(View.GONE);
                    
                    dev.setText(txtESP());
                }
            });

        



	}

	private void ShowMainView() {
        mainView = LayoutInflater.from(this).inflate(R.layout.layout_floating, null);
        paramsView = getParams();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(mainView, paramsView);
        InitShowMainView();


    }

	private void InitShowMainView() {
		miniFloatView = mainView.findViewById(R.id.miniFloatMenu); //FLOATING LOGO
		mainFloatView = mainView.findViewById(R.id.mainFloatMenu); //FLOATING MENU

		closeButton = mainView.findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					mainFloatView.setVisibility(View.GONE);
					miniFloatView.setVisibility(View.VISIBLE);                             

				}
			});

		//WHEN FLOAT LOGO TOUCHED
		LinearLayout layoutView = mainView.findViewById(R.id.layoutControlView);
		layoutView.setOnTouchListener(onTouchListener());
	}

	private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = miniFloatView;
            final View expandedView = mainFloatView;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = paramsView.x;
                        initialY = paramsView.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
								collapsedView.setVisibility(View.GONE);
								expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        paramsView.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsView.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(mainView, paramsView);
                        return true;
                }
                return false;
            }
        };
    }

	private boolean isViewCollapsed() {
        return mainFloatView == null || miniFloatView.getVisibility() == View.VISIBLE;
    }

	private WindowManager.LayoutParams getParams() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			getLayoutType(),
			getFlagsType(),
			PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        return params;
    }

	private static int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }

	private int getFlagsType() {
        int LAYOUT_FLAG = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        return LAYOUT_FLAG;
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
    private void DrawESP() {
        if (MainActivity.isRootGiven()) {
            socket = "su -c " + daemonPath;                               
        } else {
            socket = daemonPath;  
        }
        startService(new Intent(this, Overlay.class));
    }

    private void StopESP() {
        stopService(new Intent(this, Overlay.class));
    }
    
    

    public void loadAssets() {
        String filepath =Environment.getExternalStorageDirectory() + "/Android/data/.tyb";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            byte[] buffer = "DO NOT DELETE".getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String pathf = getFilesDir().toString() + "/sock64";
        try {   
            OutputStream myOutput = new FileOutputStream(pathf);
            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = getAssets().open("sock64");
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();
        } catch (IOException e) { 
        }
        daemonPath = getFilesDir().toString() + "/sock64";
        try {
            Runtime.getRuntime().exec("chmod 777 " + daemonPath);
        } catch (IOException e) { 
        }
    }   


    private String getType() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getString("type", "1");
    }
    private void  setValue(String key, boolean b) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putBoolean(key, b);
        ed.apply();

    }

    boolean getConfig(String key) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return  sp.getBoolean(key, false);
        // return !key.equals("");
    }

    void setFps(int fps) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("fps", fps);
        ed.apply();
    }

    int getFps() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("fps", 100);
    }
    void setCount(int count) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("count", count);
        ed.apply();
    }

    int getCount() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("count", 10);
    }
    
    int getEspValue(String a, int b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt(a, b);
    }
    void setEspValue(String a, int b) {
        SharedPreferences sp = this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(a, b);
        ed.apply();
    }
    
    void setLine(int line) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("line", line);
        ed.apply();
    }

    int getLine() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("line", 10);
    }
    
	void setBox(int box) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("box", box);
        ed.apply();
    }

    int getBox() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("box", 10);
    }
	void setSkelton(int skelton) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("skelton", skelton);
        ed.apply();
    }

    int getSkelton() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("skelton", 10);
    }
	
    void setHealth(int health) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("health", health);
        ed.apply();
    }

    int getHealth() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("health", 10);
    }
    
    void setLineColor(int linecolor) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("linecolor", linecolor);
        ed.apply();
    }

    int getLineColor() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("linecolor", 10);
    }
    
    void setBoxColor(int Boxcolor) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("Boxcolor", Boxcolor);
        ed.apply();
    }

    int getBoxColor() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("Boxcolor", 10);
    }
    
    void setSkeletonColor(int Skeletoncolor) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("Skeletoncolor", Skeletoncolor);
        ed.apply();
    }

    int getSkeletonColor() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("Skeletoncolor", 10);
    }
    
    void setBot(int Bot) {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("Bot", Bot);
        ed.apply();
    }

    int getBot() {
        SharedPreferences sp=this.getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.getInt("Bot", 10);
    }
    
    void setStrokeLine(int StrokeLine){
        SharedPreferences sp=this.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("StrokeLine",StrokeLine);
        ed.apply();
    }
    int getStrokeLine(){
        SharedPreferences sp=this.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        return sp.getInt("StrokeLine",0);
    }
	void setStrokeSkelton(int StrokeSkelton){
        SharedPreferences sp=this.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putInt("StrokeSkelton",StrokeSkelton);
        ed.apply();
    }
    int getStrokeSkelton(){
        SharedPreferences sp=this.getSharedPreferences("espValue",Context.MODE_PRIVATE);
        return sp.getInt("StrokeSkelton",0);
    }
    ///


    public native void SettingValue(int setting_code, boolean value);


    private void loadFunc() {
        Switch drawesp = mainView.findViewById(R.id.drawesp);
        drawesp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        DrawESP();
                    } else {
                        StopESP();
                    }
                }
            });
        
        final SeekBar strokeline = mainView.findViewById(R.id.strokeline);
        final TextView stoketxt = mainView.findViewById(R.id.stroketext);
        strokeline.setProgress(getStrokeLine());
        String b = String.valueOf(getStrokeLine());
        stoketxt.setText(b);
        ESPView.ChangeStrokeLine(getStrokeLine());
        strokeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int pos=strokeline.getProgress();
                    setStrokeLine(pos);
                    ESPView.ChangeStrokeLine(pos);
                    String a = String.valueOf(pos);
                    stoketxt.setText(a);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
			
		final SeekBar strokeskelton = mainView.findViewById(R.id.strokeskelton);
        final TextView stokeskeltext = mainView.findViewById(R.id.strokeskeltext);
        strokeskelton.setProgress(getStrokeSkelton());
        String t = String.valueOf(getStrokeSkelton());
        stokeskeltext.setText(t);
        ESPView.ChangeStrokeSkelton(getStrokeSkelton());
        strokeskelton.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int pos=strokeskelton.getProgress();
                    setStrokeSkelton(pos);
                    ESPView.ChangeStrokeSkelton(pos);
                    String a = String.valueOf(pos);
                    stokeskeltext.setText(a);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
 
			
        final RadioButton isBotWhite = mainView.findViewById(R.id.isBotWhite);
        final RadioButton isBotLBlue = mainView.findViewById(R.id.isBotLBlue);

        int checkBot = getBot();
        if (checkBot == 1) {
            isBotWhite.setChecked(true);
            SettingValue(33, true);
            SettingValue(34, false);
        } else if (checkBot == 2) {
            isBotLBlue.setChecked(true);
            SettingValue(33, false);
            SettingValue(34, true);
        }else{
            isBotWhite.setChecked(true);
            SettingValue(33, true);
            SettingValue(34, false);
        }
        isBotWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBot(1);
                        SettingValue(33, true);
                        SettingValue(34, false);
                    }
                }
            });
        isBotLBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBot(2);
                        SettingValue(33, false);
                        SettingValue(34, true);
                    }
                }
            });
            
            
        final RadioButton isHealth1 = mainView.findViewById(R.id.isHealth1);
        final RadioButton isHealth2 = mainView.findViewById(R.id.isHealth2);

        int checkHealth = getHealth();
        if (checkHealth == 1) {
            isHealth1.setChecked(true);
            SettingValue(19, true);
            SettingValue(20, false);
        } else if (checkHealth == 2) {
            isHealth2.setChecked(true);
            SettingValue(19, false);
            SettingValue(20, true);
        }else{
            isHealth1.setChecked(true);
            SettingValue(19, true);
            SettingValue(20, false);
        }
        isHealth1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setHealth(1);
                        SettingValue(19, true);
                        SettingValue(20, false);
                    }
                }
            });
        isHealth2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setHealth(2);
                        SettingValue(19, false);
                        SettingValue(20, true);
                    }
                }
            });
            
            
        final RadioButton isSkeletonRed = mainView.findViewById(R.id.isSkeletonRed);
        final RadioButton isSkeletonYellow = mainView.findViewById(R.id.isSkeletonYellow);
        final RadioButton isSkeletonGreen = mainView.findViewById(R.id.isSkeletonGreen);
        final RadioButton isSkeletonWhite = mainView.findViewById(R.id.isSkeletonWhite);

        int checkSkeletonColor = getSkeletonColor();
        if (checkSkeletonColor == 2) {
            isSkeletonRed.setChecked(true);
            SettingValue(29, false);
            SettingValue(30, true);
            SettingValue(31, false);
            SettingValue(32, false);
        } else if (checkSkeletonColor == 3) {
            isSkeletonYellow.setChecked(true);
            SettingValue(29, false);
            SettingValue(30, false);
            SettingValue(31, true);
            SettingValue(32, false);
        } else if (checkSkeletonColor == 4) {
            isSkeletonGreen.setChecked(true);
            SettingValue(29, false);
            SettingValue(30, false);
            SettingValue(31, false);
            SettingValue(32, true);
        } else if (checkSkeletonColor == 1) {
            isSkeletonWhite.setChecked(true);
            SettingValue(29, true);
            SettingValue(30, false);
            SettingValue(31, false);
            SettingValue(32, false);
        }

        else{
            isSkeletonWhite.setChecked(true);
            SettingValue(29, true);
            SettingValue(30, false);
            SettingValue(31, false);
            SettingValue(32, false);
        }

        isSkeletonRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setSkeletonColor(2);
                        SettingValue(29, false);
                        SettingValue(30, true);
                        SettingValue(31, false);
                        SettingValue(32, false);
                    }
                }
            });
        isSkeletonYellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setSkeletonColor(3);
                        SettingValue(29, false);
                        SettingValue(30, false);
                        SettingValue(31, true);
                        SettingValue(32, false);
                    }
                }
            });
        isSkeletonGreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setSkeletonColor(4);
                        SettingValue(29, false);
                        SettingValue(30, false);
                        SettingValue(31, false);
                        SettingValue(32, true);
                    }
                }
            });
        isSkeletonWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setSkeletonColor(1);
                        SettingValue(29, true);
                        SettingValue(30, false);
                        SettingValue(31, false);
                        SettingValue(32, false);
                    }
                }
            });
            
        final RadioButton isBoxRed = mainView.findViewById(R.id.isBoxRed);
        final RadioButton isBoxYellow = mainView.findViewById(R.id.isBoxYellow);
        final RadioButton isBoxGreen = mainView.findViewById(R.id.isBoxGreen);
        final RadioButton isBoxBlue = mainView.findViewById(R.id.isBoxBlue);

        int checkBoxColor = getBoxColor();
        if (checkBoxColor == 1) {
            isBoxRed.setChecked(true);
            SettingValue(25, true);
            SettingValue(26, false);
            SettingValue(27, false);
            SettingValue(28, false);
        } else if (checkBoxColor == 2) {
            isBoxYellow.setChecked(true);
            SettingValue(25, false);
            SettingValue(26, true);
            SettingValue(27, false);
            SettingValue(28, false);
        } else if (checkBoxColor == 3) {
            isBoxGreen.setChecked(true);
            SettingValue(25, false);
            SettingValue(26, false);
            SettingValue(27, true);
            SettingValue(28, false);
        } else if (checkBoxColor == 4) {
            isBoxBlue.setChecked(true);
            SettingValue(25, false);
            SettingValue(26, false);
            SettingValue(27, false);
            SettingValue(28, true);
        }

        else{
            isBoxRed.setChecked(true);
            SettingValue(25, true);
            SettingValue(26, false);
            SettingValue(27, false);
            SettingValue(28, false);
        }

        isBoxRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBoxColor(1);
                        SettingValue(25, true);
                        SettingValue(26, false);
                        SettingValue(27, false);
                        SettingValue(28, false);
                    }
                }
            });
        isBoxYellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBoxColor(2);
                        SettingValue(25, false);
                        SettingValue(26, true);
                        SettingValue(27, false);
                        SettingValue(28, false);
                    }
                }
            });
        isBoxGreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBoxColor(3);
                        SettingValue(25, false);
                        SettingValue(26, false);
                        SettingValue(27, true);
                        SettingValue(28, false);
                    }
                }
            });
        isBoxBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setBoxColor(4);
                        SettingValue(25, false);
                        SettingValue(26, false);
                        SettingValue(27, false);
                        SettingValue(28, true);
                    }
                }
            });
            
        
        final RadioButton isLineRed = mainView.findViewById(R.id.isLineRed);
        final RadioButton isLineYellow = mainView.findViewById(R.id.isLineYellow);
        final RadioButton isLineGreen = mainView.findViewById(R.id.isLineGreen);
        final RadioButton isLineBlue = mainView.findViewById(R.id.isLineBlue);
        
        int checkLineColor = getLineColor();
        if (checkLineColor == 1) {
            isLineRed.setChecked(true);
            SettingValue(21, true);
            SettingValue(22, false);
            SettingValue(23, false);
            SettingValue(24, false);
        } else if (checkLineColor == 2) {
            isLineYellow.setChecked(true);
            SettingValue(21,false);
            SettingValue(22, true);
            SettingValue(23, false);
            SettingValue(24, false);
        } else if (checkLineColor == 3) {
            isLineGreen.setChecked(true);
            SettingValue(21,false);
            SettingValue(22, false);
            SettingValue(23, true);
            SettingValue(24, false);
        } else if (checkLineColor == 4) {
            isLineBlue.setChecked(true);
            SettingValue(21,false);
            SettingValue(22, false);
            SettingValue(23, false);
            SettingValue(24, true);
        }
        
        else{
            isLineRed.setChecked(true);
            SettingValue(21, true);
            SettingValue(22, false);
            SettingValue(23, false);
            SettingValue(24, false);
        }
        
        isLineRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setLineColor(1);
                        SettingValue(21, true);
                        SettingValue(22, false);
                        SettingValue(23, false);
                        SettingValue(24, false);
                    }
                }
            });
        isLineYellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setLineColor(2);
                        SettingValue(21,false);
                        SettingValue(22, true);
                        SettingValue(23, false);
                        SettingValue(24, false);
                    }
                }
            });
        isLineGreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setLineColor(3);
                        SettingValue(21,false);
                        SettingValue(22, false);
                        SettingValue(23, true);
                        SettingValue(24, false);
                    }
                }
            });
        isLineBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setLineColor(4);
                        SettingValue(21,false);
                        SettingValue(22, false);
                        SettingValue(23, false);
                        SettingValue(24, true);
                    }
                }
            });
			
		final RadioButton isDisableSkelton = mainView.findViewById(R.id.isDisableSkelton);
		final RadioButton isSkeltonPlayer = mainView.findViewById(R.id.isSkeltonPlayer);
        final RadioButton isSkeltonPlayerBot = mainView.findViewById(R.id.isSkeltonPlayerBot);

        int checkSkelton = getSkelton();
        if (checkSkelton == 1) {
            isSkeltonPlayer.setChecked(true);
            SettingValue(39, true);
            SettingValue(40, false);
        } else if (checkSkelton == 2) {
            isSkeltonPlayerBot.setChecked(true);
            SettingValue(39, false);
            SettingValue(40, true);
        }else{
            isDisableSkelton.setChecked(true);
            SettingValue(39, false);
            SettingValue(40, false);
        }


		isDisableSkelton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setSkelton(3);
                        SettingValue(39, false);
						SettingValue(40, false);
                    }
                }
            });
		isSkeltonPlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setSkelton(1);
                        SettingValue(39, true);
						SettingValue(40, false);
                    }
                }
            });
		isSkeltonPlayerBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setSkelton(2);
                        SettingValue(39, false);
						SettingValue(40, true);
                    }
                }
            });
			
		final RadioButton isDisableBox = mainView.findViewById(R.id.isDisableBox);
		final RadioButton isPlayerBoxStroke = mainView.findViewById(R.id.isPlayerBoxStroke);
        final RadioButton isPlayerBoxFilled = mainView.findViewById(R.id.isPlayerBoxFilled);
       
        int checkBox = getBox();
        if (checkBox == 1) {
            isPlayerBoxStroke.setChecked(true);
            SettingValue(37, true);
            SettingValue(38, false);
        } else if (checkBox == 2) {
            isPlayerBoxFilled.setChecked(true);
            SettingValue(37, false);
            SettingValue(38, true);
        }else{
            isDisableBox.setChecked(true);
            SettingValue(37, false);
            SettingValue(38, false);
        }
			
			
		isDisableBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setBox(3);
                        SettingValue(37, false);
						SettingValue(38, false);
                    }
                }
            });
		isPlayerBoxStroke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setBox(1);
                        SettingValue(37, true);
						SettingValue(38, false);
                    }
                }
            });
		isPlayerBoxFilled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setBox(2);
                        SettingValue(37, false);
						SettingValue(38, true);
                    }
                }
            });
			
        final RadioButton isDisableLine = mainView.findViewById(R.id.isDisableLine);
        final RadioButton isTopLine = mainView.findViewById(R.id.isTopLine);
        final RadioButton isMiddleLine = mainView.findViewById(R.id.isMiddleLine);
        final RadioButton isBottomLine = mainView.findViewById(R.id.isBottomLine);
        final RadioButton isTopBottomLine = mainView.findViewById(R.id.isTopBottomLine);
        
        int checkLine = getLine();
        if (checkLine == 1) {
            isTopLine.setChecked(true);
            SettingValue(2, true);
            SettingValue(15, false);
            SettingValue(16, false);
            SettingValue(17, false);
        } else if (checkLine == 2) {
            isMiddleLine.setChecked(true);
            SettingValue(2, false);
            SettingValue(15, true);
            SettingValue(16, false);
            SettingValue(17, false);
        }else if (checkLine == 3) {
            isBottomLine.setChecked(true);
            SettingValue(2, false);
            SettingValue(15, false);
            SettingValue(16, true);
            SettingValue(17, false);
        }else if (checkLine == 4) {
            isTopBottomLine.setChecked(true);
            SettingValue(2, false);
            SettingValue(15, false);
            SettingValue(16, false);
            SettingValue(17, true);
        }else{
            isDisableLine.setChecked(true);
            SettingValue(2, false);
            SettingValue(15, false);
            SettingValue(16, false);
            SettingValue(17, false);
        }
        
        isDisableLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setLine(5);
                        SettingValue(2, false);
                        SettingValue(15, false);
                        SettingValue(16, false);
                        SettingValue(17, false);
                    }
                }
            });
        isTopLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setLine(1);
                        SettingValue(2, true);
                        SettingValue(15, false);
                        SettingValue(16, false);
                        SettingValue(17, false);
                    }
                }
            });
        isMiddleLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setLine(2);
                        SettingValue(2, false);
                        SettingValue(15, true);
                        SettingValue(16, false);
                        SettingValue(17, false);
                    }
                }
            });
        isBottomLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setLine(3);
                        SettingValue(2, false);
                        SettingValue(15, false);
                        SettingValue(16, true);
                        SettingValue(17, false);
                    }
                }
            });   
        isTopBottomLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setLine(4);
                        SettingValue(2, false);
                        SettingValue(15, false);
                        SettingValue(16, false);
                        SettingValue(17, true);
                    }
                }
            });   
            
            
            
            
        final RadioButton isCount1 = mainView.findViewById(R.id.isCount1);
        final RadioButton isCount2 = mainView.findViewById(R.id.isCount2);
        final RadioButton isCount3 = mainView.findViewById(R.id.isCount3);
        final RadioButton isCountNone = mainView.findViewById(R.id.isCountNone);


        int checkCount = getCount();
        if (checkCount == 1) {
            SettingValue(12, false);
            SettingValue(13, true);
            SettingValue(14, false);
            isCount1.setChecked(true);
        } else if (checkCount == 2) {
            SettingValue(12, true);
            SettingValue(13, false);
            SettingValue(14, false);
            isCount2.setChecked(true);
        } else if (checkCount == 3) {
            SettingValue(12, false);
            SettingValue(13, false);
            SettingValue(14, true);
            isCount3.setChecked(true);
        } else if (checkCount == 4) {
            SettingValue(12, false);
            SettingValue(13, false);
            SettingValue(14, false);
            isCountNone.setChecked(true);
        } else {
            SettingValue(12, false);
            SettingValue(13, true);
            SettingValue(14, false);
            isCount1.setChecked(true);
        }


        isCount1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setCount(1);
                        SettingValue(12, false);
                        SettingValue(13, true);
                        SettingValue(14, false);
                    }
                }
            });

        isCount2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setCount(2);
                        SettingValue(12, true);
                        SettingValue(13, false);
                        SettingValue(14, false);
                    }
                }
            });

        isCount3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setCount(3);
                        SettingValue(12, false);
                        SettingValue(13, false);
                        SettingValue(14, true);
                    }
                }
            });


        isCountNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {    
                        setCount(4);
                        SettingValue(12, false);
                        SettingValue(13, false);
                        SettingValue(14, false);
                    }
                }
            });


        final RadioButton IconDistanceName = mainView.findViewById(R.id.dual);
        final RadioButton Icon = mainView.findViewById(R.id.material);
        final RadioButton IconDistance = mainView.findViewById(R.id.meterialdistance);
        final RadioButton Txt = mainView.findViewById(R.id.textonly);
        if (getConfig((String) Icon.getText())) {
            Icon.setChecked(true);
            Txt.setChecked(false);
            IconDistance.setChecked(false); 
            IconDistanceName.setChecked(false);
        } else if (getConfig((String) Txt.getText())) {
            Icon.setChecked(false);
            Txt.setChecked(true);
            IconDistance.setChecked(false); 
            IconDistanceName.setChecked(false);
        } else if (getConfig((String) IconDistance.getText())) {
            Icon.setChecked(false);
            Txt.setChecked(false);
            IconDistance.setChecked(true); 
            IconDistanceName.setChecked(false);
        } else if (getConfig((String) IconDistanceName.getText())) {
            Icon.setChecked(false);
            Txt.setChecked(false);
            IconDistance.setChecked(false); 
            IconDistanceName.setChecked(true);
        } else {
            Icon.setChecked(false);
            Txt.setChecked(true);
            IconDistance.setChecked(false); 
            IconDistanceName.setChecked(false);
        }


        Txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Txt.getText()), Txt.isChecked());

                }
            });

        Icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Icon.getText()), Icon.isChecked());

                }
            });

        IconDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(IconDistance.getText()), IconDistance.isChecked());

                }
            });

        IconDistanceName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(IconDistanceName.getText()), IconDistanceName.isChecked());

                }
            });


        final Switch isVisibility = mainView.findViewById(R.id.isVisibility);
        isVisibility.setChecked(getConfig((String) isVisibility.getText()));
        SettingValue(18, getConfig((String) isVisibility.getText()));
        isVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isVisibility.getText()), isVisibility.isChecked());
                    SettingValue(18, isVisibility.isChecked());
                }
            });


        final RadioButton fps1=mainView.findViewById(R.id.fpsdefault);
        final RadioButton fps2=mainView.findViewById(R.id.fps60);
        final RadioButton fps3=mainView.findViewById(R.id.fps90);
        final RadioButton fps4=mainView.findViewById(R.id.fps120);

        int CheckFps = getFps();
        if (CheckFps == 30) {
            fps1.setChecked(true);
			ESPView.sleepTime = 1000 / 30;
        } else if (CheckFps == 45) {
            fps2.setChecked(true);
			ESPView.sleepTime = 1000 / 45;
        } else if (CheckFps == 50) {
            fps3.setChecked(true);
			ESPView.sleepTime = 1000 / 50;
        } else if (CheckFps == 60) {
            fps4.setChecked(true);
			ESPView.sleepTime = 1000 / 60;
        } else {
            fps1.setChecked(true);
			ESPView.sleepTime = 1000 / 30;
        }

        fps1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fps2.setChecked(false);
                        fps3.setChecked(false);
                        fps4.setChecked(false);
                        setFps(30);
                        ESPView.ChangeFps(30);
                    }
                }
            });

        fps2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fps1.setChecked(false);
                        fps3.setChecked(false);
                        fps4.setChecked(false);
                        setFps(45);
                        ESPView.ChangeFps(45);
                    }
                }
            });

        fps3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fps2.setChecked(false);
                        fps1.setChecked(false);
                        fps4.setChecked(false);
                        setFps(50);
                        ESPView.ChangeFps(50);
                    }
                }
            });

        fps4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fps2.setChecked(false);
                        fps3.setChecked(false);
                        fps1.setChecked(false);
                        setFps(60);
                        ESPView.ChangeFps(60);
                    }
                }
            });

        final Switch isFPS = mainView.findViewById(R.id.isFPS);
        isFPS.setChecked(getConfig((String) isFPS.getText()));
        SettingValue(11, getConfig((String) isFPS.getText()));
        isFPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isFPS.getText()), isFPS.isChecked());
                    SettingValue(11, isFPS.isChecked());
                }
            });

        final CheckBox isEnemyWeapon = mainView.findViewById(R.id.isEnemyWeapon);
        isEnemyWeapon.setChecked(getConfig((String) isEnemyWeapon.getText()));
        SettingValue(9, getConfig((String) isEnemyWeapon.getText()));
        isEnemyWeapon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isEnemyWeapon.getText()), isEnemyWeapon.isChecked());
                    SettingValue(9, isEnemyWeapon.isChecked());
                }
            });

        final CheckBox isGrenadeWarning = mainView.findViewById(R.id.isGrenadeWarning);
        isGrenadeWarning.setChecked(getConfig((String) isGrenadeWarning.getText()));
        SettingValue(10, getConfig((String) isGrenadeWarning.getText()));
        isGrenadeWarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isGrenadeWarning.getText()), isGrenadeWarning.isChecked());
                    SettingValue(10, isGrenadeWarning.isChecked());
                }
            });
            
       
        final CheckBox isHead = mainView.findViewById(R.id.isHead);
        isHead.setChecked(getConfig((String) isHead.getText()));
        SettingValue(6, getConfig((String) isHead.getText()));
        isHead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isHead.getText()), isHead.isChecked());
                    SettingValue(6, isHead.isChecked());
                }
            });
        
        
        final CheckBox isBack = mainView.findViewById(R.id.isBack);
        isBack.setChecked(getConfig((String) isBack.getText()));
        SettingValue(7, getConfig((String) isBack.getText()));
        isBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isBack.getText()), isBack.isChecked());
                    SettingValue(7, isBack.isChecked());
                }
            });

        final CheckBox isHealth = mainView.findViewById(R.id.isHealth);
        isHealth.setChecked(getConfig((String) isHealth.getText()));
        SettingValue(4, getConfig((String) isHealth.getText()));
        isHealth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isHealth.getText()), isHealth.isChecked());
                    SettingValue(4, isHealth.isChecked());
                }
            });
			
		final CheckBox isTimID = mainView.findViewById(R.id.isTimID);
        isTimID.setChecked(getConfig((String) isTimID.getText()));
        SettingValue(36, getConfig((String) isTimID.getText()));
        isTimID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isTimID.getText()), isTimID.isChecked());
                    SettingValue(36, isTimID.isChecked());
                }
            });
		
        final CheckBox isName = mainView.findViewById(R.id.is名字);
        isName.setChecked(getConfig((String) isName.getText()));
        SettingValue(5, getConfig((String) isName.getText()));
        isName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isName.getText()), isName.isChecked());
                    SettingValue(5, isName.isChecked());
                }
            });
        final CheckBox isDist = mainView.findViewById(R.id.isDist);
        isDist.setChecked(getConfig((String) isDist.getText()));
        SettingValue(3, getConfig((String) isDist.getText()));
        isDist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    setValue(String.valueOf(isDist.getText()), isDist.isChecked());
                    SettingValue(3, isDist.isChecked());
                }
            });

        ////ITEM
        final CheckBox Desert = mainView.findViewById(R.id.Desert);
        Desert.setChecked(getConfig((String) Desert.getText()));
        Desert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Desert.getText()), Desert.isChecked());
                }
            });
        final CheckBox M416 = mainView.findViewById(R.id.m416);
        M416.setChecked(getConfig((String) M416.getText()));
        M416.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M416.getText()), M416.isChecked());
                }
            });

        final CheckBox QBZ = mainView.findViewById(R.id.QBZ);
        QBZ.setChecked(getConfig((String) QBZ.getText()));
        QBZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QBZ.getText()), QBZ.isChecked());
                }
            });

        final CheckBox SCARL = mainView.findViewById(R.id.SCARL);
        SCARL.setChecked(getConfig((String) SCARL.getText()));
        SCARL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SCARL.getText()), SCARL.isChecked());
                }
            });

        final CheckBox AKM = mainView.findViewById(R.id.AKM);
        AKM.setChecked(getConfig((String) AKM.getText()));
        AKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(AKM.getText()), AKM.isChecked());
                }
            });

        final CheckBox M16A4 = mainView.findViewById(R.id.M16A4);
        M16A4.setChecked(getConfig((String) M16A4.getText()));
        M16A4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M16A4.getText()), M16A4.isChecked());
                }
            });

        final CheckBox AUG = mainView.findViewById(R.id.AUG);
        AUG.setChecked(getConfig((String) AUG.getText()));
        AUG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(AUG.getText()), AUG.isChecked());
                }
            });

        final CheckBox M249 = mainView.findViewById(R.id.M249);
        M249.setChecked(getConfig((String) M249.getText()));
        M249.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M249.getText()), M249.isChecked());
                }
            });

        final CheckBox Groza = mainView.findViewById(R.id.Groza);
        Groza.setChecked(getConfig((String) Groza.getText()));
        Groza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Groza.getText()), Groza.isChecked());
                }
            });

        final CheckBox MK47 = mainView.findViewById(R.id.MK47);
        MK47.setChecked(getConfig((String) MK47.getText()));
        MK47.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MK47.getText()), MK47.isChecked());
                }
            });

        final CheckBox M762 = mainView.findViewById(R.id.M762);
        M762.setChecked(getConfig((String) M762.getText()));
        M762.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M762.getText()), M762.isChecked());
                }
            });
        final CheckBox G36C = mainView.findViewById(R.id.G36C);
        G36C.setChecked(getConfig((String) G36C.getText()));
        G36C.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(G36C.getText()), G36C.isChecked());
                }
            });
        final CheckBox DP28 = mainView.findViewById(R.id.DP28);
        DP28.setChecked(getConfig((String) DP28.getText()));
        DP28.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(DP28.getText()), DP28.isChecked());
                }
            });
        final CheckBox MG3 = mainView.findViewById(R.id.MG3);
        MG3.setChecked(getConfig((String) MG3.getText()));
        MG3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MG3.getText()), MG3.isChecked());
                }
            });
        final CheckBox FAMAS = mainView.findViewById(R.id.FAMAS);
        FAMAS.setChecked(getConfig((String) FAMAS.getText()));
        FAMAS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(FAMAS.getText()), FAMAS.isChecked());
                }
            });
        final CheckBox ASM = mainView.findViewById(R.id.ASM);
        ASM.setChecked(getConfig((String) ASM.getText()));
        ASM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ASM.getText()), ASM.isChecked());
                }
            });


        //SMG

        final CheckBox UMP = mainView.findViewById(R.id.UMP);
        UMP.setChecked(getConfig((String) UMP.getText()));
        UMP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(UMP.getText()), UMP.isChecked());
                }
            });
        final CheckBox bizon = mainView.findViewById(R.id.bizon);
        bizon.setChecked(getConfig((String) bizon.getText()));
        bizon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(bizon.getText()), bizon.isChecked());
                }
            });
        final CheckBox MP5K = mainView.findViewById(R.id.MP5K);
        MP5K.setChecked(getConfig((String) MP5K.getText()));
        MP5K.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MP5K.getText()), MP5K.isChecked());
                }
            });
        final CheckBox TommyGun = mainView.findViewById(R.id.TommyGun);
        TommyGun.setChecked(getConfig((String) TommyGun.getText()));
        TommyGun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(TommyGun.getText()), TommyGun.isChecked());
                }
            });
        final CheckBox vector = mainView.findViewById(R.id.vector);
        vector.setChecked(getConfig((String) vector.getText()));
        vector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(vector.getText()), vector.isChecked());
                }
            });
        final CheckBox P90 = mainView.findViewById(R.id.P90);
        P90.setChecked(getConfig((String) P90.getText()));
        P90.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(P90.getText()), P90.isChecked());
                }
            });
        final CheckBox UZI = mainView.findViewById(R.id.UZI);
        UZI.setChecked(getConfig((String) UZI.getText()));
        UZI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(UZI.getText()), UZI.isChecked());
                }
            });

        //Snipers

        final CheckBox AWM = mainView.findViewById(R.id.AWM);
        AWM.setChecked(getConfig((String) AWM.getText()));
        AWM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(AWM.getText()), AWM.isChecked());
                }
            });
        final CheckBox QBU = mainView.findViewById(R.id.QBU);
        QBU.setChecked(getConfig((String) QBU.getText()));
        QBU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QBU.getText()), QBU.isChecked());
                }
            });
        final CheckBox Kar98k = mainView.findViewById(R.id.Kar98k);
        Kar98k.setChecked(getConfig((String) Kar98k.getText()));
        Kar98k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Kar98k.getText()), Kar98k.isChecked());
                }
            });
        final CheckBox M24 = mainView.findViewById(R.id.M24);
        M24.setChecked(getConfig((String) M24.getText()));
        M24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M24.getText()), M24.isChecked());
                }
            });
        final CheckBox SLR = mainView.findViewById(R.id.SLR);
        SLR.setChecked(getConfig((String) SLR.getText()));
        SLR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SLR.getText()), SLR.isChecked());
                }
            });
        final CheckBox SKS = mainView.findViewById(R.id.SKS);
        SKS.setChecked(getConfig((String) SKS.getText()));
        SKS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SKS.getText()), SKS.isChecked());
                }
            });
        final CheckBox MK14 = mainView.findViewById(R.id.MK14);
        MK14.setChecked(getConfig((String) MK14.getText()));
        MK14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MK14.getText()), MK14.isChecked());
                }
            });
        final CheckBox Mini14 = mainView.findViewById(R.id.Mini14);
        Mini14.setChecked(getConfig((String) Mini14.getText()));
        Mini14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Mini14.getText()), Mini14.isChecked());
                }
            });
        final CheckBox Mosin = mainView.findViewById(R.id.Mosin);
        Mosin.setChecked(getConfig((String) Mosin.getText()));
        Mosin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Mosin.getText()), Mosin.isChecked());
                }
            });
        final CheckBox VSS = mainView.findViewById(R.id.VSS);
        VSS.setChecked(getConfig((String) VSS.getText()));
        VSS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(VSS.getText()), VSS.isChecked());
                }
            });
        final CheckBox Win94 = mainView.findViewById(R.id.Win94);
        Win94.setChecked(getConfig((String) Win94.getText()));
        Win94.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Win94.getText()), Win94.isChecked());
                }
            });
        final CheckBox MK12 = mainView.findViewById(R.id.MK12);
        MK12.setChecked(getConfig((String) MK12.getText()));
        MK12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MK12.getText()), MK12.isChecked());
                }
            });

        //Scopes

        final CheckBox x2 = mainView.findViewById(R.id.x2);
        x2.setChecked(getConfig((String) x2.getText()));
        x2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(x2.getText()), x2.isChecked());
                }
            });
        final CheckBox x3 = mainView.findViewById(R.id.x3);
        x3.setChecked(getConfig((String) x3.getText()));
        x3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(x3.getText()), x3.isChecked());
                }
            });
        final CheckBox x4 = mainView.findViewById(R.id.x4);
        x4.setChecked(getConfig((String) x4.getText()));
        x4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(x4.getText()), x4.isChecked());
                }
            });
        final CheckBox x6 = mainView.findViewById(R.id.x6);
        x6.setChecked(getConfig((String) x6.getText()));
        x6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(x6.getText()), x6.isChecked());
                }
            });
        final CheckBox x8 = mainView.findViewById(R.id.x8);
        x8.setChecked(getConfig((String) x8.getText()));
        x8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(x8.getText()), x8.isChecked());
                }
            });
        final CheckBox canted = mainView.findViewById(R.id.canted);
        canted.setChecked(getConfig((String) canted.getText()));
        canted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(canted.getText()), canted.isChecked());
                }
            });
        final CheckBox hollow = mainView.findViewById(R.id.hollow);
        hollow.setChecked(getConfig((String) hollow.getText()));
        hollow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(hollow.getText()), hollow.isChecked());
                }
            });
        final CheckBox reddot = mainView.findViewById(R.id.reddot);
        reddot.setChecked(getConfig((String) reddot.getText()));
        reddot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(reddot.getText()), reddot.isChecked());
                }
            });

        //Armor

        final CheckBox bag1 = mainView.findViewById(R.id.bag1);
        bag1.setChecked(getConfig((String) bag1.getText()));
        bag1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(bag1.getText()), bag1.isChecked());
                }
            });
        final CheckBox bag2 = mainView.findViewById(R.id.bag2);
        bag2.setChecked(getConfig((String) bag2.getText()));
        bag2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(bag2.getText()), bag2.isChecked());
                }
            });
        final CheckBox bag3 = mainView.findViewById(R.id.bag3);
        bag3.setChecked(getConfig((String) bag3.getText()));
        bag3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(bag3.getText()), bag3.isChecked());
                }
            });
        final CheckBox helmet1 = mainView.findViewById(R.id.helmet1);
        helmet1.setChecked(getConfig((String) helmet1.getText()));
        helmet1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(helmet1.getText()), helmet1.isChecked());
                }
            });
        final CheckBox helmet2 = mainView.findViewById(R.id.helmet2);
        helmet2.setChecked(getConfig((String) helmet2.getText()));
        helmet2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(helmet2.getText()), helmet2.isChecked());
                }
            });
        final CheckBox helmet3 = mainView.findViewById(R.id.helmet3);
        helmet3.setChecked(getConfig((String) helmet3.getText()));
        helmet3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(helmet3.getText()), helmet3.isChecked());
                }
            });
        final CheckBox vest1 = mainView.findViewById(R.id.vest1);
        vest1.setChecked(getConfig((String) vest1.getText()));
        vest1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(vest1.getText()), vest1.isChecked());
                }
            });
        final CheckBox vest2 = mainView.findViewById(R.id.vest2);
        vest2.setChecked(getConfig((String) vest2.getText()));
        vest2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(vest2.getText()), vest2.isChecked());
                }
            });
        final CheckBox vest3 = mainView.findViewById(R.id.vest3);
        vest3.setChecked(getConfig((String) vest3.getText()));
        vest3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(vest3.getText()), vest3.isChecked());
                }
            });
        //Ammo
        final CheckBox a9 = mainView.findViewById(R.id.a9);
        a9.setChecked(getConfig((String) a9.getText()));
        a9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a9.getText()), a9.isChecked());
                }
            });
        final CheckBox a7 = mainView.findViewById(R.id.a7);
        a7.setChecked(getConfig((String) a7.getText()));
        a7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a7.getText()), a7.isChecked());
                }
            });
        final CheckBox a5 = mainView.findViewById(R.id.a5);
        a5.setChecked(getConfig((String) a5.getText()));
        a5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a5.getText()), a5.isChecked());
                }
            });
        final CheckBox a300 = mainView.findViewById(R.id.a300);
        a300.setChecked(getConfig((String) a300.getText()));
        a300.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a300.getText()), a300.isChecked());
                }
            });
        final CheckBox a45 = mainView.findViewById(R.id.a45);
        a45.setChecked(getConfig((String) a45.getText()));
        a45.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a45.getText()), a45.isChecked());
                }
            });
        final CheckBox Arrow = mainView.findViewById(R.id.arrow);
        Arrow.setChecked(getConfig((String) Arrow.getText()));
        Arrow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Arrow.getText()), Arrow.isChecked());
                }
            });
        final CheckBox a12 = mainView.findViewById(R.id.a12);
        a12.setChecked(getConfig((String) a12.getText()));
        a12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(a12.getText()), a12.isChecked());
                }
            });

        //Shotgun
        final CheckBox DBS = mainView.findViewById(R.id.DBS);
        DBS.setChecked(getConfig((String) DBS.getText()));
        DBS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(DBS.getText()), DBS.isChecked());
                }
            });
        final CheckBox S686 = mainView.findViewById(R.id.S686);
        S686.setChecked(getConfig((String) S686.getText()));
        S686.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(S686.getText()), S686.isChecked());
                }
            });
        final CheckBox sawed = mainView.findViewById(R.id.sawed);
        sawed.setChecked(getConfig((String) sawed.getText()));
        sawed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(sawed.getText()), sawed.isChecked());
                }
            });
        final CheckBox M1014 = mainView.findViewById(R.id.M1014);
        M1014.setChecked(getConfig((String) M1014.getText()));
        M1014.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(M1014.getText()), M1014.isChecked());
                }
            });
        final CheckBox S1897 = mainView.findViewById(R.id.S1897);
        S1897.setChecked(getConfig((String) S1897.getText()));
        S1897.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(S1897.getText()), S1897.isChecked());
                }
            });
        final CheckBox S12K = mainView.findViewById(R.id.S12K);
        S12K.setChecked(getConfig((String) S12K.getText()));
        S12K.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(S12K.getText()), S12K.isChecked());
                }
            });

        //Throwables
        final CheckBox grenade = mainView.findViewById(R.id.grenade);
        grenade.setChecked(getConfig((String) grenade.getText()));
        grenade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(grenade.getText()), grenade.isChecked());
                }
            });
        final CheckBox molotov = mainView.findViewById(R.id.molotov);
        molotov.setChecked(getConfig((String) molotov.getText()));
        molotov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(molotov.getText()), molotov.isChecked());
                }
            });
        final CheckBox stun = mainView.findViewById(R.id.stun);
        stun.setChecked(getConfig((String) stun.getText()));
        stun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(stun.getText()), stun.isChecked());
                }
            });
        final CheckBox smoke = mainView.findViewById(R.id.smoke);
        smoke.setChecked(getConfig((String) smoke.getText()));
        smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(smoke.getText()), smoke.isChecked());
                }
            });

        //Medics

        final CheckBox painkiller = mainView.findViewById(R.id.painkiller);
        painkiller.setChecked(getConfig((String) painkiller.getText()));
        painkiller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(painkiller.getText()), painkiller.isChecked());
                }
            });
        final CheckBox medkit = mainView.findViewById(R.id.medkit);
        medkit.setChecked(getConfig((String) medkit.getText()));
        medkit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(medkit.getText()), medkit.isChecked());
                }
            });
        final CheckBox firstaid = mainView.findViewById(R.id.firstaid);
        firstaid.setChecked(getConfig((String) firstaid.getText()));
        firstaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(firstaid.getText()), firstaid.isChecked());
                }
            });
        final CheckBox bandage = mainView.findViewById(R.id.bandage);
        bandage.setChecked(getConfig((String) bandage.getText()));
        bandage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(bandage.getText()), bandage.isChecked());
                }
            });
        final CheckBox injection = mainView.findViewById(R.id.injection);
        injection.setChecked(getConfig((String) injection.getText()));
        injection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(injection.getText()), injection.isChecked());
                }
            });
        final CheckBox energydrink = mainView.findViewById(R.id.energydrink);
        energydrink.setChecked(getConfig((String) energydrink.getText()));
        energydrink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(energydrink.getText()), energydrink.isChecked());
                }
            });
        //Handy
        final CheckBox Pan = mainView.findViewById(R.id.Pan);
        Pan.setChecked(getConfig((String) Pan.getText()));
        Pan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Pan.getText()), Pan.isChecked());
                }
            });
        final CheckBox Crowbar = mainView.findViewById(R.id.Crowbar);
        Crowbar.setChecked(getConfig((String) Crowbar.getText()));
        Crowbar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Crowbar.getText()), Crowbar.isChecked());
                }
            });
        final CheckBox Sickle = mainView.findViewById(R.id.Sickle);
        Sickle.setChecked(getConfig((String) Sickle.getText()));
        Sickle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Sickle.getText()), Sickle.isChecked());
                }
            });
        final CheckBox Machete = mainView.findViewById(R.id.Machete);
        Machete.setChecked(getConfig((String) Machete.getText()));
        Machete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Machete.getText()), Machete.isChecked());
                }
            });
        final CheckBox Crossbow = mainView.findViewById(R.id.Crossbow);
        Crossbow.setChecked(getConfig((String) Crossbow.getText()));
        Crossbow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Crossbow.getText()), Crossbow.isChecked());
                }
            });

        //Pistols
        final CheckBox P92 = mainView.findViewById(R.id.P92);
        P92.setChecked(getConfig((String) P92.getText()));
        P92.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(P92.getText()), P92.isChecked());
                }
            });
        final CheckBox R45 = mainView.findViewById(R.id.R45);
        R45.setChecked(getConfig((String) R45.getText()));
        R45.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(R45.getText()), R45.isChecked());
                }
            });
        final CheckBox P18C = mainView.findViewById(R.id.P18C);
        P18C.setChecked(getConfig((String) P18C.getText()));
        P18C.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(P18C.getText()), P18C.isChecked());
                }
            });
        final CheckBox P1911 = mainView.findViewById(R.id.P1911);
        P1911.setChecked(getConfig((String) P1911.getText()));
        P1911.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(P1911.getText()), P1911.isChecked());
                }
            });
        final CheckBox R1895 = mainView.findViewById(R.id.R1895);
        R1895.setChecked(getConfig((String) R1895.getText()));
        R1895.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(R1895.getText()), R1895.isChecked());
                }
            });
        final CheckBox Scorpion = mainView.findViewById(R.id.Scorpion);
        Scorpion.setChecked(getConfig((String) Scorpion.getText()));
        Scorpion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Scorpion.getText()), Scorpion.isChecked());
                }
            });
            
            //Other
        final CheckBox CheekPad = mainView.findViewById(R.id.CheekPad);
        CheekPad.setChecked(getConfig((String) CheekPad.getText()));
        CheekPad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(CheekPad.getText()), CheekPad.isChecked());
                }
            });
        final CheckBox CompensatorSMG = mainView.findViewById(R.id.CompensatorSMG);
        CompensatorSMG.setChecked(getConfig((String) CompensatorSMG.getText()));
        CompensatorSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(CompensatorSMG.getText()),CompensatorSMG.isChecked());
                }
            });

        final CheckBox FlashHiderSMG = mainView.findViewById(R.id.FlashHiderSMG);
        FlashHiderSMG.setChecked(getConfig((String) FlashHiderSMG.getText()));
        FlashHiderSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(FlashHiderSMG.getText()),FlashHiderSMG.isChecked());
                }
            });

        final CheckBox FlashHiderAr = mainView.findViewById(R.id.FlashHiderAr);
        FlashHiderAr.setChecked(getConfig((String) FlashHiderAr.getText()));
        FlashHiderAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(FlashHiderAr.getText()),FlashHiderAr.isChecked());
                }
            });

        final CheckBox ArCompensator = mainView.findViewById(R.id.ArCompensator);
        ArCompensator.setChecked(getConfig((String) ArCompensator.getText()));
        ArCompensator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ArCompensator.getText()),ArCompensator.isChecked());
                }
            });

        final CheckBox TacticalStock = mainView.findViewById(R.id.TacticalStock);
        TacticalStock.setChecked(getConfig((String) TacticalStock.getText()));
        TacticalStock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(TacticalStock.getText()),TacticalStock.isChecked());
                }
            });

        final CheckBox Duckbill = mainView.findViewById(R.id.Duckbill);
        Duckbill.setChecked(getConfig((String) Duckbill.getText()));
        Duckbill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Duckbill.getText()),Duckbill.isChecked());
                }
            });

        final CheckBox FlashHiderSniper = mainView.findViewById(R.id.FlashHiderSniper);
        FlashHiderSniper.setChecked(getConfig((String) FlashHiderSniper.getText()));
        FlashHiderSniper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(FlashHiderSniper.getText()),FlashHiderSniper.isChecked());
                }
            });

        final CheckBox SuppressorSMG = mainView.findViewById(R.id.SuppressorSMG);
        SuppressorSMG.setChecked(getConfig((String) SuppressorSMG.getText()));
        SuppressorSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SuppressorSMG.getText()),SuppressorSMG.isChecked());
                }
            });

        final CheckBox HalfGrip = mainView.findViewById(R.id.HalfGrip);
        HalfGrip.setChecked(getConfig((String) HalfGrip.getText()));
        HalfGrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(HalfGrip.getText()),HalfGrip.isChecked());
                }
            });

        final CheckBox StockMicroUZI = mainView.findViewById(R.id.StockMicroUZI);
        StockMicroUZI.setChecked(getConfig((String) StockMicroUZI.getText()));
        StockMicroUZI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(StockMicroUZI.getText()),StockMicroUZI.isChecked());
                }
            });

        final CheckBox SuppressorSniper = mainView.findViewById(R.id.SuppressorSniper);
        SuppressorSniper.setChecked(getConfig((String) SuppressorSniper.getText()));
        SuppressorSniper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SuppressorSniper.getText()),SuppressorSniper.isChecked());
                }
            });

        final CheckBox SuppressorAr = mainView.findViewById(R.id.SuppressorAr);
        SuppressorAr.setChecked(getConfig((String) SuppressorAr.getText()));
        SuppressorAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(SuppressorAr.getText()),SuppressorAr.isChecked());
                }
            });

        final CheckBox ExQdSniper = mainView.findViewById(R.id.ExQdSniper);
        ExQdSniper.setChecked(getConfig((String) ExQdSniper.getText()));
        ExQdSniper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExQdSniper.getText()),ExQdSniper.isChecked());
                }
            });

        final CheckBox QdSMG = mainView.findViewById(R.id.QdSMG);
        QdSMG.setChecked(getConfig((String) QdSMG.getText()));
        QdSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QdSMG.getText()),QdSMG.isChecked());
                }
            });

        final CheckBox ExSMG = mainView.findViewById(R.id.ExSMG);
        ExSMG.setChecked(getConfig((String) ExSMG.getText()));
        ExSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExSMG.getText()),ExSMG.isChecked());
                }
            });

        final CheckBox QdSniper = mainView.findViewById(R.id.QdSniper);
        QdSniper.setChecked(getConfig((String) QdSniper.getText()));
        QdSniper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QdSniper.getText()),QdSniper.isChecked());
                }
            });

        final CheckBox ExSniper = mainView.findViewById(R.id.ExSniper);
        ExSniper.setChecked(getConfig((String) ExSniper.getText()));
        ExSniper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExSniper.getText()),ExSniper.isChecked());
                }
            });

        final CheckBox ExAr = mainView.findViewById(R.id.ExAr);
        ExAr.setChecked(getConfig((String) ExAr.getText()));
        ExAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExAr.getText()),ExAr.isChecked());
                }
            });

        final CheckBox ExQdAr = mainView.findViewById(R.id.ExQdAr);
        ExQdAr.setChecked(getConfig((String) ExQdAr.getText()));
        ExQdAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExQdAr.getText()),ExQdAr.isChecked());
                }
            });

        final CheckBox QdAr = mainView.findViewById(R.id.QdAr);
        QdAr.setChecked(getConfig((String) QdAr.getText()));
        QdAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QdAr.getText()),QdAr.isChecked());
                }
            });

        final CheckBox ExQdSMG = mainView.findViewById(R.id.ExQdSMG);
        ExQdSMG.setChecked(getConfig((String) ExQdSMG.getText()));
        ExQdSMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ExQdSMG.getText()),ExQdSMG.isChecked());
                }
            });

        final CheckBox QuiverCrossBow = mainView.findViewById(R.id.QuiverCrossBow);
        QuiverCrossBow.setChecked(getConfig((String) QuiverCrossBow.getText()));
        QuiverCrossBow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(QuiverCrossBow.getText()),QuiverCrossBow.isChecked());
                }
            });

        final CheckBox BulletLoop = mainView.findViewById(R.id.BulletLoop);
        BulletLoop.setChecked(getConfig((String) BulletLoop.getText()));
        BulletLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(BulletLoop.getText()),BulletLoop.isChecked());
                }
            });

        final CheckBox ThumbGrip = mainView.findViewById(R.id.ThumbGrip);
        ThumbGrip.setChecked(getConfig((String) ThumbGrip.getText()));
        ThumbGrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(ThumbGrip.getText()),ThumbGrip.isChecked());
                }
            });

        final CheckBox LaserSight = mainView.findViewById(R.id.LaserSight);
        LaserSight.setChecked(getConfig((String) LaserSight.getText()));
        LaserSight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(LaserSight.getText()),LaserSight.isChecked());
                }
            });

        final CheckBox AngledGrip = mainView.findViewById(R.id.AngledGrip);
        AngledGrip.setChecked(getConfig((String) AngledGrip.getText()));
        AngledGrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(AngledGrip.getText()),AngledGrip.isChecked());
                }
            });

        final CheckBox LightGrip = mainView.findViewById(R.id.LightGrip);
        LightGrip.setChecked(getConfig((String) LightGrip.getText()));
        LightGrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(LightGrip.getText()),LightGrip.isChecked());
                }
            });

        final CheckBox VerticalGrip = mainView.findViewById(R.id.VerticalGrip);
        VerticalGrip.setChecked(getConfig((String) VerticalGrip.getText()));
        VerticalGrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(VerticalGrip.getText()),VerticalGrip.isChecked());
                }
            });

        final CheckBox GasCan = mainView.findViewById(R.id.GasCan);
        GasCan.setChecked(getConfig((String) GasCan.getText()));
        GasCan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(GasCan.getText()),GasCan.isChecked());
                }
            });
            
            
            //Vehicle
        final CheckBox UTV = mainView.findViewById(R.id.UTV);
        UTV.setChecked(getConfig((String) UTV.getText()));
        UTV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(UTV.getText()), UTV.isChecked());
                }
            });
            
        final CheckBox Buggy = mainView.findViewById(R.id.Buggy);
        Buggy.setChecked(getConfig((String) Buggy.getText()));
        Buggy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Buggy.getText()), Buggy.isChecked());
                }
            });
        final CheckBox UAZ = mainView.findViewById(R.id.UAZ);
        UAZ.setChecked(getConfig((String) UAZ.getText()));
        UAZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(UAZ.getText()), UAZ.isChecked());
                }
            });
        final CheckBox Trike = mainView.findViewById(R.id.Trike);
        Trike.setChecked(getConfig((String) Trike.getText()));
        Trike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Trike.getText()), Trike.isChecked());
                }
            });
        final CheckBox Bike = mainView.findViewById(R.id.Bike);
        Bike.setChecked(getConfig((String) Bike.getText()));
        Bike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Bike.getText()), Bike.isChecked());
                }
            });
        final CheckBox Dacia = mainView.findViewById(R.id.Dacia);
        Dacia.setChecked(getConfig((String) Dacia.getText()));
        Dacia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Dacia.getText()), Dacia.isChecked());
                }
            });
        final CheckBox Jet = mainView.findViewById(R.id.Jet);
        Jet.setChecked(getConfig((String) Jet.getText()));
        Jet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Jet.getText()), Jet.isChecked());
                }
            });
        final CheckBox Boat = mainView.findViewById(R.id.Boat);
        Boat.setChecked(getConfig((String) Boat.getText()));
        Boat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Boat.getText()), Boat.isChecked());
                }
            });
        final CheckBox Scooter = mainView.findViewById(R.id.Scooter);
        Scooter.setChecked(getConfig((String) Scooter.getText()));
        Scooter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Scooter.getText()), Scooter.isChecked());
                }
            });
        final CheckBox Bus = mainView.findViewById(R.id.Bus);
        Bus.setChecked(getConfig((String) Bus.getText()));
        Bus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Bus.getText()), Bus.isChecked());
                }
            });
        final CheckBox Mirado = mainView.findViewById(R.id.Mirado);
        Mirado.setChecked(getConfig((String) Mirado.getText()));
        Mirado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Mirado.getText()), Mirado.isChecked());
                }
            });
        final CheckBox Rony = mainView.findViewById(R.id.Rony);
        Rony.setChecked(getConfig((String) Rony.getText()));
        Rony.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Rony.getText()), Rony.isChecked());
                }
            });
        final CheckBox Snowbike = mainView.findViewById(R.id.Snowbike);
        Snowbike.setChecked(getConfig((String) Snowbike.getText()));
        Snowbike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Snowbike.getText()), Snowbike.isChecked());
                }
            });
        final CheckBox Snowmobile = mainView.findViewById(R.id.Snowmobile);
        Snowmobile.setChecked(getConfig((String) Snowmobile.getText()));
        Snowmobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Snowmobile.getText()), Snowmobile.isChecked());
                }
            });
        final CheckBox Tempo = mainView.findViewById(R.id.Tempo);
        Tempo.setChecked(getConfig((String) Tempo.getText()));
        Tempo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Tempo.getText()), Tempo.isChecked());
                }
            });
        final CheckBox Truck = mainView.findViewById(R.id.Truck);
        Truck.setChecked(getConfig((String) Truck.getText()));
        Truck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Truck.getText()), Truck.isChecked());
                }
            });
        final CheckBox MonsterTruck = mainView.findViewById(R.id.MonsterTruck);
        MonsterTruck.setChecked(getConfig((String) MonsterTruck.getText()));
        MonsterTruck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(MonsterTruck.getText()), MonsterTruck.isChecked());
                }
            });
        final CheckBox BRDM = mainView.findViewById(R.id.BRDM);
        BRDM.setChecked(getConfig((String) BRDM.getText()));
        BRDM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(BRDM.getText()), BRDM.isChecked());
                }
            });
        final CheckBox LadaNiva = mainView.findViewById(R.id.LadaNiva);
        LadaNiva.setChecked(getConfig((String) LadaNiva.getText()));
        LadaNiva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(LadaNiva.getText()), LadaNiva.isChecked());
                }
            });
            
        final CheckBox Motorglider = mainView.findViewById(R.id.Motorglider);
        Motorglider.setChecked(getConfig((String) Motorglider.getText()));
        Motorglider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Motorglider.getText()), Motorglider.isChecked());
                }
            });
            
        final CheckBox CoupeRB = mainView.findViewById(R.id.CoupeRB);
        CoupeRB.setChecked(getConfig((String) CoupeRB.getText()));
        CoupeRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(CoupeRB.getText()), CoupeRB.isChecked());
                }
            });
            
            //Special
        final CheckBox Crate = mainView.findViewById(R.id.Crate);
        Crate.setChecked(getConfig((String) Crate.getText()));
        Crate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Crate.getText()), Crate.isChecked());
                }
            });
            
        final CheckBox Airdrop = mainView.findViewById(R.id.Airdrop);
        Airdrop.setChecked(getConfig((String) Airdrop.getText()));
        Airdrop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(Airdrop.getText()), Airdrop.isChecked());
                }
            });
        final CheckBox DropPlane = mainView.findViewById(R.id.DropPlane);
        DropPlane.setChecked(getConfig((String) DropPlane.getText()));
        DropPlane.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(DropPlane.getText()), DropPlane.isChecked());
                }
            });
        final CheckBox FlareGun = mainView.findViewById(R.id.FlareGun);
        FlareGun.setChecked(getConfig((String) FlareGun.getText()));
        FlareGun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setValue(String.valueOf(FlareGun.getText()), FlareGun.isChecked());
                }
            });
            
           //Injector
           
        
            
            
        Switch reducerec = mainView.findViewById(R.id.reducerec);
        reducerec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        SettingValue(35, true); 
                    } else {
                        SettingValue(35, false);
                    }
                }

            });
            
        final Switch aimbot = mainView.findViewById(R.id.isAimbot);
        aimbot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					setValue(String.valueOf(aimbot.getText()), aimbot.isChecked());
					floatAimbot=true;
					startFloatAimbot();
				} else {
					setValue(String.valueOf(aimbot.getText()), aimbot.isChecked());
					SettingValue(43,false);
					floatAimbot=false;
					stopFloatAimbot();
				} 
            }
        });

        
        Switch isIgroneKock = mainView.findViewById(R.id.isIgroneBot);
        isIgroneKock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        SettingValue(46, true); 
                        
                    } else {
                        SettingValue(46, false);
                        
                    }
                }

            });

        final SeekBar range = mainView.findViewById(R.id.radiusaim);
        range.setProgress(getEspValue("RangeAim",0));
        final TextView rangetext = mainView.findViewById(R.id.intradius);
        rangetext.setText(String.valueOf(getEspValue("RangeAim",0)));
        Range(range.getProgress());
        range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    Range(range.getProgress());
                    String a = String.valueOf(range.getProgress());
                    rangetext.setText(a);
                    setEspValue("RangeAim",i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            
        
            
            
            
        final RadioGroup aimby=mainView.findViewById(R.id.aimby);
        aimby.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int chkdId=aimby.getCheckedRadioButtonId();
                    RadioButton btn=mainView.findViewById(chkdId);
                    AimBy(Integer.parseInt(btn.getTag().toString()));
                }
            });

        final RadioGroup aimwhen=mainView.findViewById(R.id.aimwhen);
        aimwhen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int chkdId=aimwhen.getCheckedRadioButtonId();
                    RadioButton btn=mainView.findViewById(chkdId);
                    AimWhen(Integer.parseInt(btn.getTag().toString()));
                }
            });
        final RadioGroup aimbotmode=mainView.findViewById(R.id.aimbotmode);
        aimbotmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int chkdId=aimbotmode.getCheckedRadioButtonId();
                    RadioButton btn=mainView.findViewById(chkdId);
                    Target(Integer.parseInt(btn.getTag().toString()));
                }
            });
        
        
            
            
        final SeekBar distances = mainView.findViewById(R.id.distanceaim);
        distances.setProgress(getEspValue("DistAim",0));
        final TextView distancetext = mainView.findViewById(R.id.intdistance);
        distancetext.setText(String.valueOf(getEspValue("DistAim",0)));
        distances(distances.getProgress());
        distances.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    distances(distances.getProgress());
                    String a = String.valueOf(distances.getProgress());
                    distancetext.setText(a);
                    setEspValue("DistAim",i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


        final SeekBar Recoil = mainView.findViewById(R.id.recoil);
        Recoil.setProgress(getEspValue("RecoilAim",0));
        final TextView setrecoil = mainView.findViewById(R.id.intrecoil);
        setrecoil.setText(String.valueOf(getEspValue("RecoilAim",0)));
        recoil(Recoil.getProgress());
        Recoil.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    recoil(Recoil.getProgress());
                    String a = String.valueOf(Recoil.getProgress());
                    setrecoil.setText(a);
                    setEspValue("RecoilAim",i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        final SeekBar bulletspeed = mainView.findViewById(R.id.speedbullet);
        bulletspeed.setProgress(getEspValue("BulletAim",0));
        final TextView setbulletspeed = mainView.findViewById(R.id.intbullet);
        setbulletspeed.setText(String.valueOf(getEspValue("BulletAim",0)));
        Bulletspeed(bulletspeed.getProgress());
        bulletspeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    Bulletspeed(bulletspeed.getProgress());
                    String a = String.valueOf(bulletspeed.getProgress());
                    setbulletspeed.setText(a);
                    setEspValue("BulletAim",i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            
        final Switch isLogoBypass = mainView.findViewById(R.id.is腾讯防封);
        isLogoBypass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        RunCPP("/lobby");
                          Toast.makeText(FloatingActivity.this, "腾讯防封开启成功", Toast.LENGTH_SHORT).show();
                        isLogoBypass.setTextColor(Color.parseColor("#000000"));
                    } else {
                    
                        Toast.makeText(FloatingActivity.this, "腾讯防封开启成功", Toast.LENGTH_SHORT).show();
                        isLogoBypass.setTextColor(Color.parseColor("#000000"));
                    }
                }

            });
     
    }
	
		public void RunCPP(String path)
    {
        try
        {
            ExecuteElf("chmod 777 " + getFilesDir() + path);
            ExecuteElf(getFilesDir() + path);
            ExecuteElf("su -c chmod 777 " + getFilesDir() + path);
            ExecuteElf("su -c " + getFilesDir() + path);
        }
        catch (Exception e)
        {
        }
    }

    
    private void startFloatAimbot(){
		SettingValue(43, false);
		startService(new Intent(this, FloatAimbot.class));
	}

	private void stopFloatAimbot(){
		stopService(new Intent(this, FloatAimbot.class));
	}
    
    

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mainView != null) {
			windowManager.removeView(mainView);
		}
	}
}

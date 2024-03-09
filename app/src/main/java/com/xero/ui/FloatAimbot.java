package com.xero.ui;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.os.Build;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class FloatAimbot extends Service {

	static {
        System.loadLibrary("native");
    }

	private View mainView;
	private WindowManager windowManager;
	private WindowManager.LayoutParams paramsView;

	private RelativeLayout miniFloatView;

	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ShowMainView();
	}

	private void ShowMainView() {
        mainView = LayoutInflater.from(this).inflate(R.layout.float_aimbot, null);
        paramsView = getParams();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (MainActivity.hiderec_sw ==1) {
		RecorderFakeUtils.setFakeRecorderWindowLayoutParams(paramsView);
        windowManager.addView(mainView, paramsView);
		}else{
		windowManager.addView(mainView, paramsView);
		}
        InitShowMainView();
    }

	private void InitShowMainView() {
		miniFloatView = mainView.findViewById(R.id.miniFloatMenu); //FLOATING LOGO

		//WHEN FLOAT LOGO TOUCHED
		RelativeLayout layoutView = mainView.findViewById(R.id.layoutControlView);
		layoutView.setOnTouchListener(onTouchListener());
		LoadView();//LOAD FUNCTION
	}

	private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = miniFloatView;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

			public boolean flashnyala=false;

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
								if (!flashnyala) { //Jump Login Protection
									ImageView myImageView = mainView.findViewById(R.id.imageview_control);
									myImageView.setImageResource(R.drawable.aimon);
									AimbotPOV(43,true);
									flashnyala=true;
								} else {
									ImageView myImageView = mainView.findViewById(R.id.imageview_control);
									myImageView.setImageResource(R.drawable.aimoff);
									AimbotPOV(43,false);
									flashnyala=false;
								}
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
        return miniFloatView.getVisibility() == View.VISIBLE;
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

	public native void AimbotPOV(int setting_code,boolean value);

	private void LoadView() {


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mainView != null) {
			windowManager.removeView(mainView);
		}
	}
}



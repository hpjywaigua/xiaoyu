package com.xero.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.graphics.Matrix;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.SystemClock;
import com.xero.ui.FloatingActivity;
import android.graphics.Typeface;
import static com.xero.ui.Overlay.getConfig;

public class ESPView extends View implements Runnable {
    Paint mStrokePaint;
    Paint mFilledPaint,mFilledPaint2;
    Paint mTextPaint;
	Paint mFPSText;
    Thread mThread;
    Paint p;
    Bitmap bitmap,out;
    static long sleepTime;
    private  static int setStroke,setStrokeSkel;
    private  static int itemSize,itemPosition;
    private int    mFPS = 0;
    private int    mFPSCounter = 0;
    private long   mFPSTime = 0;
	private  static int Pos;
    private  static int Size;

    public static void ChangeStrokeLine(int strokeline){
        setStroke = strokeline;
    }
    
    public static void ChangeStrokeSkelton(int strokeskelton){
        setStrokeSkel = strokeskelton;
    }
	
    public static void ChangeFps(int fps) {
        sleepTime = 1000 / fps;
    }

    public ESPView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
        mThread = new Thread(this);
        mThread.start();
		AssetManager manager = context.getAssets();
		mTextPaint.setTypeface(Typeface.createFromAsset(manager, "fonts/font.ttf"));
		mFPSText.setTypeface(Typeface.createFromAsset(manager, "fonts/digital.ttf"));
		
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == VISIBLE) {
            ClearCanvas(canvas);
            Overlay.DrawOn(this, canvas);
        }
    }
	
    @Override
    public void run() {
        
        while (mThread.isAlive() && !mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                long td = System.currentTimeMillis() - t1;

                Thread.sleep(Math.max(Math.min(0, sleepTime - td), sleepTime));
            } catch (Exception e) {
                Thread.currentThread().interrupt();//preserve the message
                return;
                //System.out.println("----------------\n"+e.toString()+"\n----------------------");
            }
        }
        
    }
	
    public void InitializePaints() {
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(Color.rgb(0, 0, 0));
		mStrokePaint.setTextAlign(Paint.Align.CENTER);

        mFilledPaint = new Paint();
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setAntiAlias(true);       
        mFilledPaint.setColor(Color.rgb(0, 0, 0));
        
        mFilledPaint2 = new Paint();
        mFilledPaint2.setStyle(Paint.Style.FILL);
        mFilledPaint2.setAntiAlias(true);       
        mFilledPaint2.setColor(Color.rgb(0, 0, 0));
        
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	    mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.rgb(0, 0, 0));
		mStrokePaint.setStrokeWidth(0.5f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
		
		mFPSText = new Paint();
		mFPSText.setStyle(Paint.Style.FILL_AND_STROKE);
	    mFPSText.setAntiAlias(true);
        mFPSText.setColor(Color.rgb(0, 0, 0));
        mFPSText.setTextAlign(Paint.Align.CENTER);
        mFPSText.setStrokeWidth(0.1f);
		mFPSText.setShadowLayer(10, 1, 1, Color.BLACK);
		
        p = new Paint();
        final int bitmap_count_oth = OTHER.length;
        for (int i = 0 ; i < bitmap_count_oth ; i++) {
            OTHER[i] = BitmapFactory.decodeResource(getResources(), OTH_NAME[i]);
            if (i == 4) {
                OTHER[i] = scale(OTHER[i], 400, 35);
            } else if (i == 5) {
                    OTHER[i] = scale(OTHER[i], 22, 22);
            } else {
                OTHER[i] = scale(OTHER[i], 80, 80);
            }
        }
    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void DrawLine(Canvas cvs, int a, int r, int g,int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }
    public void DrawLinePlayer(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth+setStroke/3);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }
    
	public void DrawSkelton(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth + setStrokeSkel/3);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }
	
    public void DrawRect(Canvas cvs, int a, int r, int g, int b, float stroke, float x, float y, float width, float height) {
        mStrokePaint.setStrokeWidth(stroke);
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        cvs.drawRoundRect(new RectF(x, y, width, height), 5, 5, mStrokePaint);
        
    }
    
    public void DrawFilledRect(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        mFilledPaint2.setColor(Color.rgb(r, g, b));
        mFilledPaint2.setAlpha(a);
        cvs.drawRoundRect(new RectF(x, y, width, height), 5, 5, mFilledPaint2);
        
    }
    
    public void DebugText(String s) {
        System.out.println(s);
    }
    
    public void DrawTextName(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mFPSText.setARGB(a,r, g, b);
        mFPSText.setTextSize(size);
        if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }
        String s = "" + mFPS;
        cvs.drawText(""+s, posX, posY, mFPSText);
        
    }

    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }

    public void DrawWeapon(Canvas cvs, int a, int r, int g, int b, int id, int ammo, float posX, float posY, float size) {
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        String wname=getWeapon(id);
        if (wname != null)
            cvs.drawText(wname + ": " + ammo, posX, posY, mTextPaint);
    }

    public void DrawEnemyCount(Canvas cvs, int a, int r, int g, int b, int x, int y, int width, int height) {
        int colors[] = {Color.TRANSPARENT, Color.rgb(r, g, b), Color.TRANSPARENT};
        GradientDrawable mDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors);
        mDrawable.setShape(GradientDrawable.RECTANGLE);
        mDrawable.setGradientRadius(2.0f * 60);
        Rect mRect = new Rect(x, y, width, height);
        mDrawable.setBounds(mRect);
        cvs.save();
        mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mDrawable.draw(cvs);
        cvs.restore();
    }

    public void DrawName(Canvas cvs, int a, int r, int g, int b, String nametxt, int teamid, float posX, float posY, float size) {
        String[] namesp = nametxt.split(":");
        char[] nameint = new char[namesp.length];
        for (int i = 0; i < namesp.length; i++)
            nameint[i] = (char) Integer.parseInt(namesp[i]);
        String realname = new String(nameint);
        mTextPaint.setARGB(a, r, g, b);
        mTextPaint.setTextSize(size);
        cvs.drawText(realname, posX, posY, mTextPaint);
    }
	
	public void DrawTimID(Canvas cvs, int a, int r, int g, int b, String nametxt, int teamid, float posX, float posY, float size) {
        String[] namesp = nametxt.split(":");
        char[] nameint = new char[namesp.length];
        for (int i = 0; i < namesp.length; i++)
            nameint[i] = (char) Integer.parseInt(namesp[i]);
        String realname = new String(nameint);
		mTextPaint.setARGB(a, r, g, b);
        cvs.drawText("" + teamid, posX , posY - Pos, mTextPaint);
    }
	
	
    public void DrawItems(Canvas cvs,  String itemName, float distance, float posX, float posY, float size) {
        String realItemName = getItemName(itemName);
        mTextPaint.setTextSize(size);
        if (realItemName != null && !realItemName.equals(""))
            if (true && getConfig("Icon") || getConfig("Icon Distance") || getConfig("Icon Distance Name")) {
                if (realItemName == "M416") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m416);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "SCAR-L") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scar_l);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "AKM") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.akm);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "QBZ") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qbz);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "M16A-4") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m164a);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "M249") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m249);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Groza") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.groza);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Mk47 Mutant") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mk47);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "AUG") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aug);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "M762") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m762);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "G36C") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.g36c);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "DP28") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dp);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "ASM AR") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ams);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "MG3 Machine Gun") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mg3);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "FAMAS") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.famas);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "UMP") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ump);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "MP5K") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mp5k);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "UZI") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.uzi);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Vector") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vector);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "TommyGun") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tommy);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "P90") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p90);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Bizon") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bizon);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "AWM") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.awm);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "QBU") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qbu);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "M24") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m24);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Kar98k") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kar98k);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Mini14") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mini14);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "SKS") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sks);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "SLR") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slr);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "MK14") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mk14);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "MK12") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mk12);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "VSS") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vss);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "CrossBow") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crossbow);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Mosin") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mosin);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Win94") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.win94);
                    out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 50, // Left
                        posY - 70 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "8x") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s8x);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "2x") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s2x);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Red Dot") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.reddot);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "3X") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s3x);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Hollow Sight") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hollow);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "6x") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s6x);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "4x") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s4x);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Canted Sight") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.canted);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Bag lvl 3") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bag3);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Bag lvl 2") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bag2);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Bag lvl 1") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bag1);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Vest lvl 3") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vest3);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Vest lvl 2") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vest2);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Vest lvl 1") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vest1);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Helmet lvl 3") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.helmet3);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Helmet lvl 2") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.helmet2);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Helmet lvl 1") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.helmet1);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 67, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 40, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "45ACP") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammocp);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "9mm") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammo9mm);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "12 Gauge") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammo12uage);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "7.62mm") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammo762);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "5.56mm") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammo556);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "Arrow") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aarrow);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                } else if (realItemName == "300Magnum") {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ammo300magnum);
                    out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 30, // Left
                        posY - 85 -  itemPosition, // Top
                        null // Paint
                    );
                    
                    //Shotgun
                    
                   } else if (realItemName == "DBS") {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dbs);
                        out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                        cvs.drawBitmap(
                            out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                        );
                   } else if (realItemName == "S686") {
                       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s686);
                       out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                       cvs.drawBitmap(
                           out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                       );
                   } else if (realItemName == "S12K") {
                       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s12k);
                       out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                       cvs.drawBitmap(
                           out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                       );
                   } else if (realItemName == "M1014") {
                       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m1014);
                       out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                       cvs.drawBitmap(
                           out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                       );
                   } else if (realItemName == "S1897") {
                       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s1897);
                       out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                       cvs.drawBitmap(
                           out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                       );
                   } else if (realItemName == "SawedOff") {
                       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sawed);
                       out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                       cvs.drawBitmap(
                           out, // Bitmap
                           posX - 50, // Left
                           posY - 70 -  itemPosition, // Top
                           null // Paint
                       );
                       
                       //Throwables
                       } else if (realItemName == "Grenade") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frag_nad);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Smoke") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smoke);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Stun") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stun);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Molotov") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.molotov);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                           
                           //Medics
                       } else if (realItemName == "FirstAid") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first_aid);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "PainKiller") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pain_killer);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Bandage") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bandage);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Injection") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.injection);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Energy Drink") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.energy_drink);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Medkit") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.medkit);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                           
                           //Handy
                           
                       } else if (realItemName == "Pan") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pan);
                           out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 50, // Left
                               posY - 70 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Machete") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.machete);
                           out = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 24, // Left
                               posY - 70 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Crowbar") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crow);
                           out = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 25, // Left
                               posY - 70 -  itemPosition, // Top
                               null // Paint
                           );
                       } else if (realItemName == "Sickle") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sickle);
                           out = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                           
                           //Pistol
                       } else if (realItemName == "R1895" || realItemName == "P92" || realItemName == "Scorpion" || realItemName == "P18C" || realItemName == "R45" || realItemName == "P1911" || realItemName == "DesertEagle") {
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pistol);
                           out = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 25, // Left
                               posY - 70 -  itemPosition, // Top
                               null // Paint
                           );
                       }else if (realItemName == "Crate"){
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crate);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 69, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );

                       }else if (realItemName == "AirDrop"){
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.airdrop);
                           out = Bitmap.createScaledBitmap(bitmap, 60, 71, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       }else if (realItemName == "DropPlane"){
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
                           out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 85 -  itemPosition, // Top
                               null // Paint
                           );
                       }else if (realItemName == "Flare Gun"){
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flare_gun);
                           out = Bitmap.createScaledBitmap(bitmap, 90, 48, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 50, // Left
                               posY - 70 -  itemPosition, // Top
                               null // Paint
                           );
                      
                       }else if (realItemName == "Gas Can"){
                           bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gas_can);
                           out = Bitmap.createScaledBitmap(bitmap, 52, 72, false);
                           cvs.drawBitmap(
                               out, // Bitmap
                               posX - 30, // Left
                               posY - 90 -  itemPosition, // Top
                               null // Paint
                           );

                       }
                
                if (true && getConfig("Icon Distance")) {
                    cvs.drawText("" + Math.round(distance) + "m", posX, posY - itemPosition, mTextPaint);
                }
                if (true && getConfig("Icon Distance Name")) {
                    cvs.drawText(realItemName + " (" + Math.round(distance) + "m)", posX, posY - itemPosition, mTextPaint);
                }
            } else {
                cvs.drawText(realItemName + " (" + Math.round(distance) + "m)", posX, posY - itemPosition, mTextPaint);
            }
              
    }
            
    public void DrawVehicles(Canvas cvs, String itemName,float distance, float posX, float posY, float size) {
        String realVehicleName = getVehicleName(itemName);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(size);
		mStrokePaint.setTextSize(size);
        if(realVehicleName!=null && !realVehicleName.equals(""))
            if (true && getConfig("Icon") || getConfig("Icon Distance") || getConfig("Icon Distance Name"))  {
                if (realVehicleName == "Buggy"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.buggy);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "UAZ"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.uaz);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Trike"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.motocycle_cart);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }
                
                else if (realVehicleName == "Bike"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.motocycle);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Dacia"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dacia);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "UTV"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.utv);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Jet"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aquarail);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Boat"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_boat);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "MiniBus"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mini_bus);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "CoupeRB"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mirado_open);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }
                else if (realVehicleName == "Motorglider"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.motorglider);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }
                else if (realVehicleName == "Mirado"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mirado);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Snowbike"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowbike);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Snowmobile"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snow_mobile);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Tempo"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tuktuk);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                    } else if (realVehicleName == "Rony"){
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rony);
                        out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                        cvs.drawBitmap(
                            out, // Bitmap
                            posX - 20, // Left
                            posY - 80 -  itemPosition, // Top
                            null // Paint
                        );
                    
                }else if (realVehicleName == "Truck"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pickup);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "BRDM"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brdm);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "LadaNiva"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lada_niva);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Bus"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mini_bus);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Scooter"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scooter);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }else if (realVehicleName == "Monster Truck"){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pickup);
                    out = Bitmap.createScaledBitmap(bitmap, 50, 58, false);
                    cvs.drawBitmap(
                        out, // Bitmap
                        posX - 20, // Left
                        posY - 80 -  itemPosition, // Top
                        null // Paint
                    );
                }
                
                if (true && getConfig("Icon Distance")) {
                    cvs.drawText("" + Math.round(distance) + "m", posX, posY - itemPosition, mTextPaint);
                }
                if (true && getConfig("Icon Distance Name")) {
                    cvs.drawText(realVehicleName + " (" + Math.round(distance) + "m)", posX, posY - itemPosition, mTextPaint);
                }
            } else {
                cvs.drawText(realVehicleName + " (" + Math.round(distance) + "m)", posX, posY - itemPosition, mTextPaint);
								
            }
       
        
            }
                
                
                
             
    
    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius, float stroke) {
        mStrokePaint.setARGB(a, r, g, b);
        mStrokePaint.setStrokeWidth(stroke);
        cvs.drawCircle(posX, posY, radius, mStrokePaint);
    }

    public void DrawFilledCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius) {
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawCircle(posX, posY, radius, mFilledPaint);
    }

    public void DrawOTH(Canvas cvs, int image_number, float X, float Y) {

        cvs.drawBitmap(OTHER[image_number], X, Y, p);

    }

    Bitmap[] OTHER = new Bitmap[6];

    private static final int[] OTH_NAME = {
        R.drawable.ic_clear_pro,
        R.drawable.ic_clear_noob,
        R.drawable.ic_warn_pro,
        R.drawable.ic_warn_noob,
        R.drawable.ic_warning,
        R.drawable.ic_boot
    };


    private String getWeapon(int id) {
        if (id == 101006)
            return "AUG";

        if (id == 101008)
            return "M762" ;

        if (id == 101003)
            return "SCAR-L";

        if (id == 101004)
            return "M416";

        if (id == 101002)
            return "M16A4";

        if (id == 101009)
            return "MK47";

        if (id == 101010)
            return "G36C";

        if (id == 101007)
            return "QBZ";

        if (id == 101001)
            return "AKM";

        if (id == 101005)
            return "Groza";

        if (id == 102005)
            return "Bizon";

        if (id == 102004)
            return "TommyGun";

        if (id == 102007)
            return "MP5K";

        if (id == 102002)
            return "UMP";

        if (id == 102003)
            return "Vector";

        if (id == 102001)
            return "Uzi";

        if (id == 105002)
            return "DP28";

        if (id == 105001)
            return "M249";

        //snipers

        if (id == 103003)
            return "AWM";

        if (id == 103010)
            return "QBU";

        if (id == 103009)
            return "SLR";

        if (id == 103004)
            return "SKS";

        if (id == 103006)
            return "Mini14";

        if (id == 103002)
            return "M24";

        if (id == 103001)
            return "Kar98k";

        if (id == 103005)
            return "VSS";

        if (id == 103008)
            return "Win94";

        if (id == 103007)
            return "Mk14";

//shotguns and hand weapons
        if (id == 104003)
            return "S12K";

        if (id == 104004)
            return "DBS";

        if (id == 104001)
            return "S686";

        if (id == 104002)
            return "S1897";

        if (id == 108003)
            return "Sickle";

        if (id == 108001)
            return "Machete";

        if (id == 108002)
            return "Crowbar";

        if (id == 107001)
            return "CrossBow";

        if (id == 108004)
            return "Pan";

        //pistols

        if (id == 106006)
            return "SawedOff";

        if (id == 106003)
            return "R1895";

        if (id == 106008)
            return "Vz61";

        if (id == 106001)
            return "P92";

        if (id == 106004)
            return "P18C";

        if (id == 106005)
            return "R45";

        if (id == 106002)
            return "P1911";

        if (id == 106010)
            return "DesertEagle";
        if (id == 103011)
            return "Mosin";
        if (id == 107005)
            return "PanzerFaust";
        return "";

    }

    private String getItemName(String s) {
        //Scopes
        if (s.contains("MZJ_8X")&& getConfig("8x")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "8x";
        }

        if (s.contains("MZJ_2X") && getConfig("2x")) { mTextPaint.setARGB(255, 230, 172, 226);
            return "2x";
        }

        if (s.contains("MZJ_HD") && getConfig("Red Dot")) { mTextPaint.setARGB(255, 230, 172, 226);
            return "Red Dot";
        }

        if (s.contains("MZJ_3X") && getConfig("3x")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "3X";
        }

        if (s.contains("MZJ_QX") && getConfig("Hollow")) { mTextPaint.setARGB(255, 153, 75, 152);
            return "Hollow Sight";
        }

        if (s.contains("MZJ_6X") && getConfig("6x")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "6x";
        }

        if (s.contains("MZJ_4X") && getConfig("4x")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "4x";
        }

        if (s.contains("MZJ_SideRMR") && getConfig("Canted")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "Canted Sight";
        }


        //AR and smg
        if (s.contains("AUG") && getConfig("AUG")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "AUG";
        }

        if (s.contains("M762") && getConfig("M762")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "M762";
        }

        if (s.contains("SCAR") && getConfig("SCAR-L")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "SCAR-L";
        }
        if (s.contains("FAMAS") && getConfig("FAMAS")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "FAMAS";
        }
        if (s.contains("MG3") && getConfig("MG3 Machine Gun")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "MG3 Machine Gun";
        }
        if (s.contains("AN94") && getConfig("ASM AR")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "ASM AR";
        }
        if (s.contains("M416") && getConfig("M416")) { mTextPaint.setARGB(255, 0, 250, 250);
            return "M416";
        }

        if (s.contains("M16A4") && getConfig("M16A4")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "M16A-4";
        }

        if (s.contains("Mk47") && getConfig("MK47")) { mTextPaint.setARGB(255, 247, 99, 245);
            return "Mk47 Mutant";
        }

        if (s.contains("G36") && getConfig("G36C")) { mTextPaint.setARGB(255, 116, 227, 123);
            return "G36C";
        }

        if (s.contains("QBZ") && getConfig("QBZ")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "QBZ";
        }

        if (s.contains("AKM") && getConfig("AKM")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "AKM";
        }

        if (s.contains("Groza") && getConfig("Groza")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "Groza";
        }

        if (s.contains("PP19") && getConfig("Bizon")) { mTextPaint.setARGB(255, 255, 246, 0);
            return "Bizon";
        }

        if (s.contains("TommyGun") && getConfig("TommyGun")) { mTextPaint.setARGB(255, 0, 0, 207);
            return "TommyGun";
        }

        if (s.contains("MP5K") && getConfig("MP5K")) { mTextPaint.setARGB(255, 0, 0, 207);
            return "MP5K";
        }

        if (s.contains("UMP9") && getConfig("UMP")) { mTextPaint.setARGB(255, 0, 0, 207);
            return "UMP";
        }

        if (s.contains("Vector") && getConfig("Vector")) { mTextPaint.setARGB(255, 233, 0, 207);
            return "Vector";
        }

        if (s.contains("MachineGun_Uzi") && getConfig("UZI")) { mTextPaint.setARGB(255, 233, 0, 207);
            return "UZI";
        }
        

        if (s.contains("MachineGun_P90") && getConfig("P90")) { mTextPaint.setARGB(255, 233, 0, 207);
            return "P90";
        }

        if (s.contains("DP28") && getConfig("DP28")) { mTextPaint.setARGB(255, 255, 165, 0);
            return "DP28";
        }

        if (s.contains("M249") && getConfig("M249")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "M249";
        }

        //snipers

        if (s.contains("AWM") && getConfig("AWM")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "AWM";
        }

        if (s.contains("QBU") && getConfig("QBU")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "QBU";
        }

        if (s.contains("SLR") && getConfig("SLR")) { mTextPaint.setARGB(255, 43, 26, 28);
            return "SLR";
        }

        if (s.contains("SKS") && getConfig("SKS")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "SKS";
        }

        if (s.contains("Mini14") && getConfig("Mini14")) { mTextPaint.setARGB(255, 0, 255, 0);
            return "Mini14";
        }

        if (s.contains("Sniper_M24") && getConfig("M24")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "M24";
        }

        if (s.contains("Kar98k") && getConfig("Kar98k")) { mTextPaint.setARGB(255, 153, 165, 0);
            return "Kar98k";
        }

        if (s.contains("VSS") && getConfig("VSS")) { mTextPaint.setARGB(255, 255, 43, 0);
            return "VSS";
        }

        if (s.contains("Win94") && getConfig("Win94")) { mTextPaint.setARGB(255, 207, 207, 207);
            return "Win94";
        }

        if (s.contains("Mk14") && getConfig("MK14")) { mTextPaint.setARGB(255, 153, 0, 0);
            return "MK14";
        }
        if (s.contains("Sniper_Mosin") && getConfig("Mosin")) { mTextPaint.setARGB(255, 153, 0, 0);
            return "Mosin";
        }
        if (s.contains("Sniper_MK12") && getConfig("MK12")) { mTextPaint.setARGB(255, 153, 0, 0);
            return "MK12";
        }
        
//shotguns and hand weapons
        if (s.contains("S12K") && getConfig("S12K")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S12K";
        }

        if (s.contains("DBS") && getConfig("DBS")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "DBS";
        }
        if (s.contains("SawedOff") && getConfig("Sawed-Off")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "SawedOff";
        }
        
        if (s.contains("M1014") && getConfig("M1014")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "M1014";
        }
        if (s.contains("DP12") && getConfig("DBS")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "DBS";
        }
        if (s.contains("S686") && getConfig("S686")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S686";
        }

        if (s.contains("S1897") && getConfig("S1897")) { mTextPaint.setARGB(255, 153, 109, 109);
            return "S1897";
        }

        if (s.contains("Sickle") && getConfig("Sickle")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Sickle";
        }

        if (s.contains("Machete") && getConfig("Machete")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Machete";
        }

        if (s.contains("Cowbar") && getConfig("Crowbar")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Crowbar";
        }

        if (s.contains("CrossBow") && getConfig("Crossbow")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "CrossBow";
        }

        if (s.contains("Pan") && getConfig("Pan")) { mTextPaint.setARGB(255, 102, 74, 74);
            return "Pan";
        }

        //pistols

        

        if (s.contains("R1895") && getConfig("R1895")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "R1895";
        }

        if (s.contains("Vz61") && getConfig("Scorpion")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "Scorpion";
        }

        if (s.contains("P92") && getConfig("P92")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P92";
        }

        if (s.contains("P18C") && getConfig("P18C")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P18C";
        }

        if (s.contains("R45") && getConfig("R45")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "R45";
        }

        if (s.contains("P1911") && getConfig("P1911")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "P1911";
        }

        if (s.contains("DesertEagle") && getConfig("Dessert Eagle")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "DesertEagle";
        }


        //Ammo
        if (s.contains("Ammo_762mm") && getConfig("7.62mm")) { mTextPaint.setARGB(255, 92, 36, 28);
            return "7.62mm";
        }

        if (s.contains("Ammo_45AC") && getConfig("45ACP")) { mTextPaint.setColor(Color.LTGRAY);
            return "45ACP";
        }

        if (s.contains("Ammo_556mm") && getConfig("5.56mm")) { mTextPaint.setColor(Color.GREEN);
            return "5.56mm";
        }

        if (s.contains("Ammo_9mm") && getConfig("9mm")) { mTextPaint.setColor(Color.YELLOW);
            return "9mm";
        }

        if (s.contains("Ammo_300Magnum") && getConfig("300Magnum")) { mTextPaint.setColor(Color.BLACK);
            return "300Magnum";
        }

        if (s.contains("Ammo_12Guage") && getConfig("12Gauge")) { mTextPaint.setARGB(255, 156, 91, 81);
            return "12 Gauge";
        }

        if (s.contains("Ammo_Bolt") && getConfig("Arrow")) { mTextPaint.setARGB(255, 156, 113, 81);
            return "Arrow";
        }

        //bag helmet vest
        if (s.contains("Bag_Lv3") && getConfig("Bag L3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Bag lvl 3";
        }

        if (s.contains("Bag_Lv1")  && getConfig("Bag L1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Bag lvl 1";
        }

        if (s.contains("Bag_Lv2") && getConfig("Bag L2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Bag lvl 2";
        }

        if (s.contains("Armor_Lv2") && getConfig("Vest L2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Vest lvl 2";
        }


        if (s.contains("Armor_Lv1") && getConfig("Vest L1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Vest lvl 1";
        }


        if (s.contains("Armor_Lv3") && getConfig("Vest L3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Vest lvl 3";
        }


        if (s.contains("Helmet_Lv2") && getConfig("Helmet L2")) { mTextPaint.setARGB(255, 77, 115, 255);
            return "Helmet lvl 2";
        }

        if (s.contains("Helmet_Lv1") && getConfig("Helmet L1")) { mTextPaint.setARGB(255, 127, 154, 250);
            return "Helmet lvl 1";
        }

        if (s.contains("Helmet_Lv3") && getConfig("Helmet L3")) { mTextPaint.setARGB(255, 36, 83, 255);
            return "Helmet lvl 3";
        }

        //Healthkits
        if (s.contains("Pills") && getConfig("PainKiller")) { mTextPaint.setARGB(255, 227, 91, 54);
            return "PainKiller";
        }

        if (s.contains("Injection") && getConfig("Injection")) { mTextPaint.setARGB(255,204, 193, 190);
            return "Injection";
        }

        if (s.contains("Drink") && getConfig("EnergyDrink")) { mTextPaint.setARGB(255, 54, 175, 227);
            return "Energy Drink";
        }

        if (s.contains("Firstaid") && getConfig("FirstAid")) { mTextPaint.setARGB(255, 194, 188, 109);
            return "FirstAid";
        }

        if (s.contains("Bandage") && getConfig("Bandage")) { mTextPaint.setARGB(255, 43, 189, 48);
            return "Bandage";
        }

        if (s.contains("FirstAidbox") && getConfig("MedKit")) { mTextPaint.setARGB(255, 0, 171, 6);
            return "Medkit";
        }

        //Throwables
        if (s.contains("Grenade_Stun") && getConfig("Stun")) { mTextPaint.setARGB(255,204, 193, 190);
            return "Stun";
        }

        if (s.contains("Grenade_Shoulei") && getConfig("Grenade")) { mTextPaint.setARGB(255, 2, 77, 4);
            return "Grenade";
        }

        if (s.contains("Grenade_Smoke") && getConfig("Smoke")) { mTextPaint.setColor(Color.WHITE);
            return "Smoke";
        }

        if (s.contains("Grenade_Burn") && getConfig("Molotov")) { mTextPaint.setARGB(255, 230, 175, 64);
            return "Molotov";
        }


        //others
        if (s.contains("Large_FlashHider") && getConfig("Flash Hider Ar")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Flash Hider Ar";
        }

        if (s.contains("QK_Large_C") && getConfig("Ar Compensator")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Ar Compensator";
        }

        if (s.contains("Mid_FlashHider") && getConfig("Flash Hider SMG")) { mTextPaint.setARGB(255, 255, 213, 130);
            return "Flash Hider SMG";
        }

        if (s.contains("QT_A_") && getConfig("Tactical Stock")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Tactical Stock";
        }

        if (s.contains("DuckBill") && getConfig("Duckbill")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "DuckBill";
        }

        if (s.contains("Sniper_FlashHider") && getConfig("Flash Hider Snp")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Flash Hider Sniper";
        }

        if (s.contains("Mid_Suppressor") && getConfig("Suppressor SMG")) { mTextPaint.setARGB(255, 158, 222, 195);
            return "Suppressor SMG";
        }

        if (s.contains("HalfGrip") && getConfig("Half Grip")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Half Grip";
        }


        if (s.contains("Choke") && getConfig("Choke")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Choke";
        }

        if (s.contains("QT_UZI") && getConfig("Stock Micro UZI")) { mTextPaint.setARGB(255, 155, 189, 222);
            return "Stock Micro UZI";
        }

        if (s.contains("QK_Sniper") && getConfig("SniperCompensator")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Sniper Compensator";
        }

        if (s.contains("Sniper_Suppressor") && getConfig("Sup Sniper")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Suppressor Sniper";
        }

        if (s.contains("Large_Suppressor") && getConfig("Suppressor Ar")) { mTextPaint.setARGB(255, 60, 127, 194);
            return "Suppressor Ar";
        }


        if (s.contains("Sniper_EQ_") && getConfig("Ex.Qd.Sniper")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.Sniper";
        }

        if (s.contains("Mid_Q_") && getConfig("Qd.SMG")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.SMG";
        }

        if (s.contains("Mid_E_") && getConfig("Ex.SMG")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.SMG";
        }

        if (s.contains("Sniper_Q_") && getConfig("Qd.Sniper")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.Sniper";
        }

        if (s.contains("Sniper_E_") && getConfig("Ex.Sniper")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.Sniper";
        }

        if (s.contains("Large_E_") && getConfig("Ex.Ar")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Ex.Ar";
        }

        if (s.contains("Large_EQ_") && getConfig("Ex.Qd.Ar")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.Ar";
        }

        if (s.contains("Large_Q_") && getConfig("Qd.Ar")) { mTextPaint.setARGB(255, 193, 163, 209);
            return "Qd.Ar";
        }

        if (s.contains("Mid_EQ_") && getConfig("Ex.Qd.SMG")) { mTextPaint.setARGB(255, 193, 140, 222);
            return "Ex.Qd.SMG";
        }

        if (s.contains("Crossbow_Q") && getConfig("Quiver CrossBow")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Quiver CrossBow";
        }

        if (s.contains("ZDD_Sniper") && getConfig("Bullet Loop")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Bullet Loop";
        }


        if (s.contains("ThumbGrip") && getConfig("Thumb Grip")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Thumb Grip";
        }

        if (s.contains("Lasersight") && getConfig("Laser Sight")) { mTextPaint.setARGB(255, 148, 121, 163);
            return "Laser Sight";
        }

        if (s.contains("Angled") && getConfig("Angled Grip")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Angled Grip";
        }

        if (s.contains("LightGrip") && getConfig("Light Grip")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Light Grip";
        }

        if (s.contains("Vertical") && getConfig("Vertical Grip")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Vertical Grip";
        }

        if (s.contains("GasCan") && getConfig("Gas Can")) { mTextPaint.setARGB(255, 255, 143, 203);
            return "Gas Can";
        }

        if (s.contains("Mid_Compensator") && getConfig("Compensator SMG")) { mTextPaint.setARGB(255, 219, 219, 219);
            return "Compensator SMG";
        }


        //special
        if (s.contains("Flare") && getConfig("FlareGun")) { mTextPaint.setARGB(255, 242, 63, 159);
            return "Flare Gun";
        }

        if (s.contains("Ghillie") && getConfig("Ghillie Suit")) { mTextPaint.setARGB(255, 139, 247, 67);
            return "Ghillie Suit";
        }
        if (s.contains("CheekPad") && getConfig("CheekPad")) { mTextPaint.setARGB(255, 112, 55, 55);
            return "CheekPad";
        }
        if ( s.contains("PickUpListWrapperActor") && getConfig("Crate")) { mTextPaint.setARGB(200, 255, 255, 255);
            return "Crate";
        }
        if ((s.contains("AirDropPlane") )&& getConfig("DropPlane")) { mTextPaint.setARGB(255, 224, 177, 224);
            return "DropPlane";
        }
        if ((s.contains("AirDrop")  )&& getConfig("AirDrop")) { mTextPaint.setARGB(255, 255, 10, 255);
            return "AirDrop";
        }
        //return s;
        return null;

    }

   private String getVehicleName(String s){
        if(s.contains("Buggy") && getConfig("Buggy"))
            return "Buggy";

        if(s.contains("UAZ") && getConfig("UAZ"))
            return "UAZ";

        if(s.contains("MotorcycleC") && getConfig("Trike") )
            return "Trike";

        if(s.contains("Motorcycle") && getConfig("Bike"))
            return "Bike";

        if(s.contains("Dacia") && getConfig("Dacia"))
            return "Dacia";

        if(s.contains("AquaRail") && getConfig("Jet"))
            return "Jet";

        if(s.contains("PG117") && getConfig("Boat"))
            return "Boat";

        if(s.contains("MiniBus") && getConfig("Bus"))
            return "Bus";

        if(s.contains("Mirado") && getConfig("Mirado"))
            return "Mirado";

        if(s.contains("Scooter") && getConfig("Scooter"))
            return "Scooter";

        if(s.contains("Rony") && getConfig("Rony"))
            return "Rony";

        if(s.contains("Snowbike") && getConfig("Snowbike"))
            return "Snowbike";

        if(s.contains("Snowmobile") && getConfig("Snowmobile"))
            return "Snowmobile";

        if(s.contains("Tuk") && getConfig("Tempo"))
            return "Tempo";
            
       if(s.contains("UTV") && getConfig("UTV"))
           return "UTV";

        if(s.contains("PickUp") && getConfig("Truck"))
            return "Truck";

        if(s.contains("BRDM") && getConfig("BRDM"))
            return "BRDM";

        if(s.contains("LadaNiva") && getConfig("LadaNiva"))
            return "LadaNiva";

        if(s.contains("Bigfoot") && getConfig("MonsterTruck"))
            return "Monster Truck";

        if(s.contains("CoupeRB") && getConfig("CoupeRB"))
           return "CoupeRB";
           
       if(s.contains("Motorglider") && getConfig("Motorglider"))
           return "Motorglider";
        return "";
    }

    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float)bitmap.getWidth() / maxWidth;
        float heightRatio = (float)bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int)(((float)width / bitmap.getWidth()) * bitmap.getHeight());
        } else {
            height = maxHeight;
            width = (int)(((float)height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float ratioX = (float)width / bitmap.getWidth();
        float ratioY = (float)height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}

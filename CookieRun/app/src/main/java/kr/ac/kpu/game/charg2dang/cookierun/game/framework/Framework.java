package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.charg2dang.cookierun.ui.activity.HighscoreActivity;

public class Framework
{
	private static HighscoreActivity hightscoreActiviey;
	private final String TAG = Framework.class.getSimpleName();
	private  boolean isDebuMode = true;
	private static Framework  inst;
	private Paint collisionBoxpaint = new Paint();
	private View view;
	private static float commonVelocity = -800;
	public Resources getResources()
	{
		return this.view.getResources();
	}
	public static Activity	main;

	private Paint cookieFontPaint;
	private Typeface typeface;


	public static Framework getInstance()
	{
		if(inst == null)
		{
			inst = new Framework();
		}
		return inst;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	public Framework()
	{
		collisionBoxpaint.setColor(Color.RED);
		collisionBoxpaint.setAlpha(70);
	}

	public void setFont(Paint fontPaint)
	{
		cookieFontPaint = fontPaint;
	}

	public static float getCommonVelocity()
	{
		return commonVelocity;
	}

	public static void setMainActiviey(Activity main){ Framework.main = main; }
	public static Activity getMainActiviey(){ return Framework.main;}

	public boolean isDebugMode()
	{
		return this.isDebuMode;
	}

	public Paint getDebugPaint()
	{
		return collisionBoxpaint;
	}

	public Paint getFontPaint()
	{
		return cookieFontPaint;
	}

	public static void setHightscoreActiviey(HighscoreActivity highscoreActivity)
	{
		Framework.hightscoreActiviey = highscoreActivity;
	}

	public static Activity getHightscoreActiviey(){ return Framework.hightscoreActiviey;}
}

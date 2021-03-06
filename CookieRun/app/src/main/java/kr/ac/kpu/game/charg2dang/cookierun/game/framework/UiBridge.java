package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class UiBridge
{
	private static final String TAG = UiBridge.class.getSimpleName();
	private static View view;
	private static Resources resources;
	public static Metrics metrics = new Metrics();
	private static Activity activity;

	public static class Metrics
	{
		public int oneInch;
		public Point size;
		public Point fullSize;
		public Point center;
	}

	public static int x(int size)
	{
		int mdpi = size * metrics.oneInch / 160;
		if (mdpi < 0)
		{
			mdpi += metrics.size.x;
		}

		return mdpi;
	}


	public static float x(float size)
	{
		float mdpi = size * metrics.oneInch / 160;
		if (mdpi < 0)
		{
			mdpi += metrics.size.x;
		}

		return mdpi;
	}

	public static float y(float size)
	{
		float mdpi = size * metrics.oneInch / 160;
		if (mdpi < 0)
		{
			mdpi += metrics.size.y;
		}

		return mdpi;
	}

	public static int y(int size)
	{
		int mdpi = size * metrics.oneInch / 160;
		if (mdpi < 0)
		{
			mdpi += metrics.size.y;
		}

		return mdpi;
	}

	public static void setActivity(Activity activity)
	{
		UiBridge.activity = activity;
		WindowManager wm = activity.getWindowManager();
		Point size = new Point();
		Point fullSize = new Point();
		Display display = wm.getDefaultDisplay();
		display.getSize(size);
		display.getSize(fullSize);
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		size.y -= getStatusBarHeight();
		fullSize.x += getNavigationBarHeight();
		metrics.size = size;
		metrics.fullSize = fullSize;

		Log.d(TAG, " fullsize : " + fullSize);
		metrics.center = new Point(size.x / 2, size.y / 2);
		metrics.oneInch = dm.densityDpi; //400dpi, 120 400 * 120 / 160
	}

	public static void exit() {
		activity.finish();
	}
	public static void post(Runnable runnable) {
		view.post(runnable);
	}



	private static int getStatusBarHeight()
	{
		int height = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0)
		{
			height = activity.getResources().getDimensionPixelSize(resourceId);
		}

		return height;
	}

	public static int getNavigationBarHeight()
	{
		int height = 0;
		int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0)
		{
			height = activity.getResources().getDimensionPixelSize(resourceId);
		}

		return height;
	}

	public static void setView(View view)
	{
		UiBridge.view = view;
		UiBridge.resources = view.getResources();
	}
	public static Resources getResources() {
		return resources;
	}
	public static View getView() {
		return view;
	}
	public static Activity getActivity()  {  return activity;  }


}
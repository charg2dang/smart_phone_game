package kr.ac.kpu.game.charg2dang.cookierun.game.scenes;

import android.content.Intent;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.obj.BitmapObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Scene;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.SceneManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.bg.StaticBackground;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.ScoreObject;
import kr.ac.kpu.game.charg2dang.cookierun.ui.activity.HighscoreActivity;
import kr.ac.kpu.game.charg2dang.cookierun.ui.activity.MainActivity;

public class ResultScene extends Scene
{
	private static final String TAG = ResultScene.class.getSimpleName();
	private static StaticBackground 	bg;
	private static BitmapObject 		text;
	private ScoreObject					scoreObject;
	private boolean trigger = false;
	private  float	timer = 1.0f;
	private float time;

	public void initObjects()
	{
		bg = new StaticBackground(R.mipmap.ui_result_fg1);
		add(LayerType.bg, bg);

		text = new BitmapObject(UiBridge.metrics.center.x, 200.f, 500, 200, R.mipmap.ui_result_text);
		add(LayerType.ui, text);

		scoreObject = ScoreManager.getInstance().getResultScore();

		add(LayerType.ui, scoreObject);

		scoreObject.move(0, 800);
	}


	@Override
	public void update(long frameTimeNanos)
	{
		super.update(frameTimeNanos);

		if(false) // 조건 맞으면
			SceneManager.getInstance().changeScene(SceneType.game, true);


		time += GameTimer.getInstance().getCurrentDeltaSecondsSngle();
		if(time >= timer)
		{
			if (trigger == false)
			{
				trigger =true;

				Intent intent = new Intent(Framework.getMainActiviey(), HighscoreActivity.class);
				intent.putExtra( "score", scoreObject.getScore());
				Framework.getMainActiviey().startActivity(intent);
			}
		}
	}


	@Override
	protected int getLayerCount()
	{
		return LayerType.MAX.ordinal();
	}


	public void add(final LayerType layerType, final GameObject obj)
	{
		super.add(layerType.ordinal(), obj);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}


	@Override
	protected void onPause(PauseReason reason) { }
	@Override
	protected void onResume() { }
}

package kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.kpu.game.charg2dang.cookierun.R;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.res.bitmap.SharedBitmap;

public class JumpButton extends GameObject
{
	private static final String TAG = JumpButton.class.getSimpleName();
	private final SharedBitmap jump;
	private final SharedBitmap jumpPressed;
	private RectF box;
	private float x, y;
	private boolean down;
	private float scale;
	private Cookie cookie;

	private static JumpButton instance;
	private boolean state = true;

	public static JumpButton getInstance()
	{
		if(instance == null)
		{
			instance = new JumpButton(0, 0);
		}

		return instance;
	}

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
		updateForColliderBox();
	}
	private JumpButton(float x, float y)
	{
		this.jump = SharedBitmap.load(R.mipmap.jump);
		this.jumpPressed = SharedBitmap.load(R.mipmap.jump_pressed);

		this.x = x;// - ( jump.getWidth() / 2 );
		this.y = y;// - ( jump.getHeight() / 2 );

		down = false;

		box = new RectF();

		cookie = Cookie.getInstance();

		updateForColliderBox();
	}

	public void setScale(float scale)
	{
		this.scale = scale;

		updateForColliderBox();
	}

	public void updateForColliderBox()
	{
		box.left = x;
		box.right = x + (scale * jump.getWidth());
		box.top = y + (scale * jump.getHeight());
		box.bottom = y;
	}


	@Override
	public void update(long timeDiffNanos)
	{ }

	@Override
	public void draw(Canvas canvas)
	{
		canvas.save();
		canvas.scale(scale, scale , x, y);

		if(down == false)  jump.draw(canvas, x, y);
		else jumpPressed.draw(canvas, x, y);

		canvas.restore();
	}

	@Override
	public boolean getState()
	{
		return state;
	}

	public boolean isPressed()
	{
		return down;
	}


	public void onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				float dx = event.getX();
				float dy = event.getY();

				if(box.left <= dx && dx <= box.right)
				{
					if (box.bottom <= dy && dy <= box.top)
					{
						down = true;
					}
				}

				break;

			case MotionEvent.ACTION_UP:

				if(down != true) return;

				down = false;

				break;

			default:

				break;
		}
	}
}

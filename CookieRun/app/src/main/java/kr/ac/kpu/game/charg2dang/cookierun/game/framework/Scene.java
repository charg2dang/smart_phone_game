package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.Recyclable;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.world.MainWorld;
import kr.ac.kpu.game.charg2dang.cookierun.util.CollisionHelper;

public abstract class GameWorld
{
    private static final String TAG = GameWorld.class.getSimpleName();
    protected static GameWorld singleton;
    private RecyclePool recyclePool = new RecyclePool();
    protected View view;
    private long frameTimeNanos;
    private long timeDiffNanos;
    private ArrayList<GameObject> trash = new ArrayList<>();
    protected Rect rect;
    protected ArrayList<ArrayList<GameObject>> layers;
    private CollisionHelper collisionHelper = new CollisionHelper();

    public static GameWorld get()
    {
        if(GameWorld.singleton == null)
        {
            Log.e(TAG, "Gameworld subclass not created!!!");
        }

        return GameWorld.singleton;
    }


    private void initLayers()
    {
        layers = new ArrayList<>();
        int layerCount = getLayerCount();

        for(int i = 0; i < layerCount; i++)
        {
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

    public void initResources(View view)
    {
        this.view = view;
        initLayers();
        initObjects();
    }

    public void draw(Canvas canvas)
    {
        for(ArrayList<GameObject> objects : layers)
        {
            for (GameObject o : objects)
            {
                o.draw(canvas);
            }
        }
    }

    public void update(long frameTimeNanos)
    {
        this.timeDiffNanos = frameTimeNanos - this.frameTimeNanos;
        this.frameTimeNanos = frameTimeNanos;
        if (rect == null)
        {
            return;
        }

        for(ArrayList<GameObject> objects : layers)
        {
            for (GameObject o : objects)
            {
                o.update(timeDiffNanos);
            }
        }

//        if(trash.size() > 0)
//        {
        removeTrashObjects();
//        }

        trash.clear();
    }

    public void collide(long frameTimeNanos)
    {
        if (layers.size() == 0)
        {
            return;
        }

//        Cookie cookie = Cookie.getInstance();
        ArrayList<GameObject> items     = layers.get(LayerType.item.ordinal());
        ArrayList<GameObject> players   = layers.get(LayerType.player.ordinal());
        ArrayList<GameObject> obstacles = layers.get(LayerType.obstacle.ordinal());


        for( GameObject player : players )
        {
            RectF box = ((Cookie)player).getBox();
            if(box == null)
            {
                return;
            }

            for (GameObject item : items)
            {
                collisionHelper.collides((BoxCollidable) player, (BoxCollidable) item);
            }

            for (GameObject obstacle : obstacles)
            {
                collisionHelper.collides((BoxCollidable) player, (BoxCollidable) obstacle);
            }

            return;
        }
    }


    private void removeTrashObjects()
    {
        for( ArrayList<GameObject> layer : layers)
        {
            for (Iterator<GameObject> iterator = layer.iterator(); iterator.hasNext(); )
            {
                GameObject go = iterator.next();
                if (go.getState() == false)
                {
                    iterator.remove();
                }

                if (go instanceof Recyclable)
                {
                    ((Recyclable) go).recycle();
                    getRecyclePool().add(go);
                }
            }
        }


//        for ( int tIndex = trash.size() - 1; tIndex >= 0; tIndex-- )
//        {
//            GameObject tObj = trash.get(tIndex);
//
//            for(ArrayList<GameObject> objects : layers)
//            {
//                int index = objects.indexOf(tObj);
//                if( index >= 0)
//                {
//                    objects.remove(index);
//                    break;
//                }
//            }
//
//            trash.remove(tIndex);
//            if(tObj instanceof Recyclable)
//            {
//                ((Recyclable) tObj).recycle();
//                getRecyclePool().add(tObj);
//            }
//        }
    }

    public void add(final int index, final GameObject obj)
    {
        view.post(new Runnable()
        {
            @Override
            public void run()
            {
                ArrayList<GameObject> objects = layers.get(index);
                objects.add(obj);
            }
        });
    }
    protected GameWorld()  { }
    public void remove(GameObject obj)
    {
        trash.add(obj);
    }

    public boolean onTouchEvent(MotionEvent event) {  return false;  }
    protected void initObjects(){};

    public ArrayList<GameObject> objectsAt(int enemy)
    {
        return this.layers.get(enemy);
    }

    public void setRect(Rect rect)
    {
        this.rect = rect;
    }

    public RecyclePool getRecyclePool()
    {
        return this.recyclePool;
    }
    public long getCurrentFrameTimeNanos() { return frameTimeNanos; }
    abstract protected int getLayerCount();
    public int getLeft()
    {
        return this.rect.left;
    }
    public int getRight()
    {
        return this.rect.right;
    }
    public int getTop()
    {
        return this.rect.top;
    }
    public int getBottom()
    {
        return this.rect.bottom;
    }
    public Resources getResources()
    {
        return this.view.getResources();
    }
    public Context getContext()
    {
        return view.getContext();
    }
    public long getTimeDiffNanos()
    {
        return timeDiffNanos;
    }
    public float getTimeDiffInSecond()
    {
        return (float)(timeDiffNanos / 1_000_000_000.0);
    }

    public void pause()
    {
    }

    public void resume()
    {

    }

}
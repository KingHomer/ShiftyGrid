package com.boone.framework.livewallpaper.wallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.world.World;

//Extend this class to build a quick live wallpaper impl
public abstract class BaseWallpaperService extends WallpaperService {

	protected abstract AbstractWallpaperEngine getEngine();

	@Override public Engine onCreateEngine() {
		return getEngine();
	}

	//Base class for the wallpaper engine.
	public abstract class AbstractWallpaperEngine extends Engine implements WallpaperEngine {

		private Handler mHandler = new Handler();

		// World time elapsed ..Move to world.
		private long mStartTime;
		private long mElapsedTime;
		private long mLastDrawTime;

		private World mWorld;

		private Renderer mRenderer;

		private float mScreenHeight;
		private float mScreenWidth;

		private float mOffsetX;

		private boolean mVisible;

		public AbstractWallpaperEngine() {
			mWorld = getWorld();
			mRenderer = getRenderer();
		}

		public abstract World createWorld();
		
		public abstract Renderer createRenderer();

		private Runnable engineRunner = new Runnable() {
			@Override public void run() {
				queueStep();
				drawFrame();
			}
		};

		private void queueStep() {
			mElapsedTime = SystemClock.elapsedRealtime() - mStartTime;

			mWorld.step();
			mHandler.removeCallbacks(engineRunner);
			if (mVisible) {
				mHandler.postDelayed(engineRunner, 40); // 1000 / 25
			}
		}

		@Override public void onCreate(SurfaceHolder holder) {
			super.onCreate(holder);

			mScreenHeight = getDesiredMinimumHeight();
			mScreenWidth = getDesiredMinimumWidth();

			setTouchEventsEnabled(true); // user set property.

			mStartTime = SystemClock.elapsedRealtime();
			mWorld.onCreate(this);
			mRenderer.onCreate(this);
		}

		@Override public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(engineRunner);
			mWorld.onDestroy();
		}

		@Override public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(engineRunner);
		}

		@Override public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			// should world get this ?
		}

		@Override public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			// maybe run a step
			if (mVisible) {
				drawFrame();
			}
		}

		@Override public void onOffsetsChanged(float xOffset, float yOffsett, float xStep, float yStep, int xPixels, int yPixles) {
			// does world need to see the change ?
			mOffsetX = xPixels;

			mWorld.step();
			drawFrame();
		}

		public void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					mRenderer.render(c);
				}
			} catch (Exception e) {
				mHandler.removeCallbacks(engineRunner);
				mWorld.onDestroy();
			} finally {
				if (c != null) {
					holder.unlockCanvasAndPost(c);
				}
			}
		}
		
		@Override public World getWorld() {
			if (mWorld == null) {
				mWorld = createWorld();
			}
			return mWorld;
		}
		
		@Override public Renderer getRenderer() {
			if(mRenderer == null) {
				mRenderer = createRenderer();
			}
			return mRenderer;
		}

		@Override public long getStartTime() {
			return mStartTime;
		}

		@Override public long getElapsedTime() {
			return mElapsedTime;
		}

		@Override public long getLastDrawTime() {
			return mLastDrawTime;
		}

		@Override public float getScreenHeight() {
			return mScreenHeight;
		}

		@Override public float getScreenOffset() {
			return mOffsetX;
		}

		@Override public float getScreenWidth() {
			return mScreenWidth;
		}
	}
}

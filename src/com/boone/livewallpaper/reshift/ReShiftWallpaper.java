package com.boone.livewallpaper.reshift;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;

@Deprecated public class ReShiftWallpaper extends WallpaperService {

	@Override public Engine onCreateEngine() {
		return new ReShiftEngine();
	}

	private class ReShiftEngine extends Engine implements WallpaperEngine {
		// Thread handlers
		private Handler mHandler = new Handler();

		// World time elapsed ..Move to world.
		private long mStartTime;
		private long mElapsedTime;
		private long mLastDrawTime;

		// World object
		private World mWorld;

		// Renderer
		private Renderer mRenderer;

		// Grid object ... member of world ?

		private float mScreenHeight;
		private float mScreenWidth;

		private float mOffsetX;

		private boolean mVisible;

		private Runnable engineRunner = new Runnable() {
			@Override public void run() {
				mWorld.step();
				drawFrame();
			}
		};

		public ReShiftEngine() {
			// build world .. use factory or singleton instance.

		}

		@Override public void onCreate(SurfaceHolder holder) {
			super.onCreate(holder);

			mScreenHeight = getDesiredMinimumHeight();
			mScreenWidth = getDesiredMinimumWidth();

			setTouchEventsEnabled(true); // user set property.

			mWorld.onCreate(this);
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
				mLastDrawTime = System.currentTimeMillis();
			}
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

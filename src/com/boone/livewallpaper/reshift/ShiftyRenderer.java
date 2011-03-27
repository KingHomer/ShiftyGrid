package com.boone.livewallpaper.reshift;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.reshift.grid.ShiftyBox;

public class ShiftyRenderer implements Renderer {

	Paint mPaint;

	//TODO clean this up
	ShiftyWorld world;
	ReShiftEngine engine;
	
	public ShiftyRenderer() {
		mPaint = new Paint();
		mPaint.setColor(0xffffffff);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	}
	
	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = (ReShiftEngine) engine;
		world = (ShiftyWorld) engine.getWorld();
	}

	@Override public void render(Canvas c) {
		c.save();
		c.drawColor(0xff000000);

		//TODO redo .. probably wont work
		Collection<ShiftyBox> boxes = world.getBoxes();
		for (Iterator<ShiftyBox> i = boxes.iterator(); i.hasNext();) {
			ShiftyBox box = i.next();

			if (box.getLeft() > (engine.getScreenOffset() + engine.getScreenWidth())) {
				continue;
			}

			if (box.getRight() < -engine.getScreenOffset()) {
				continue;
			}

			mPaint.setColor(box.getARGB());
			//rect = new RectF(box.left, box.top, box.right, box.bottom);
			//Moving the canvas might be cheaper.
			RectF rect = new RectF(box.getBox());

			rect.offsetTo(box.getLeft() + engine.getScreenOffset(), box.getTop());
			c.drawRect(rect, mPaint);
		}

		c.restore();
	}

}

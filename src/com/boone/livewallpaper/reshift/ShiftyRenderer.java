package com.boone.livewallpaper.reshift;

import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.shiftygrid.ShiftyBox;

public class ShiftyRenderer implements Renderer {

	Paint mPaint;

	//TODO clean this up
	ShiftyWorld world;
	ReShiftEngine engine;
	
	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = (ReShiftEngine) engine;
		world = (ShiftyWorld) engine.getWorld();
	}

	@Override public void render(Canvas c) {
		c.save();
		c.drawColor(0xff000000);

		//TODO redo .. probably wont work
		List<ShiftyBox> boxes = world.getBoxes();

		RectF rect = null;
		for (Iterator<ShiftyBox> i = boxes.iterator(); i.hasNext();) {
			ShiftyBox box = i.next();

			if (!box.isActive) {
				continue;
			}

			if (box.left > (engine.getScreenOffset() + engine.getScreenWidth())) {
				continue;
			}

			if (box.right < -engine.getScreenOffset()) {
				continue;
			}

			mPaint.setColor(box.getARGB());
			rect = new RectF(box.left, box.top, box.right, box.bottom);

			rect.offsetTo(box.left + engine.getScreenOffset(), box.top);
			c.drawRoundRect(rect, 0.0f, 0.0f, mPaint);
		}

		c.restore();
	}

}

package com.boone.shiftygrid;

import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ShiftyRenderer implements Renderer {

	Paint mPaint;
	
	ShiftyWorld world;
	WallpaperEngine engine;

	public ShiftyRenderer() {
		world = (ShiftyWorld) getWorld();
		engine = world.getEngine();
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Canvas c) {
		c.save();
		c.drawColor(0xff000000);

		ShiftyWorld world = (ShiftyWorld) getWorld();
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

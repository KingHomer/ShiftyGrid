package com.boone.shiftygrid;

import android.graphics.Canvas;

public interface Renderer {

	public void render(Canvas c);
	
	public World getWorld();
	
}

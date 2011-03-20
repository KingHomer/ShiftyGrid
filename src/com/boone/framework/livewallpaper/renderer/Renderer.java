package com.boone.framework.livewallpaper.renderer;

import com.boone.framework.livewallpaper.world.World;

import android.graphics.Canvas;

public interface Renderer {

	public void render(Canvas c);
	
	public World getWorld();
	
}

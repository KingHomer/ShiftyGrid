package com.boone.framework.livewallpaper.wallpaper;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.world.World;

//Access to timers, screen dimensions, world and renderer.
public interface WallpaperEngine {
		
	public long getStartTime();
	
	public long getElapsedTime();
	
	public long getLastDrawTime();
	
	public float getScreenHeight();
	
	public float getScreenWidth();
	
	public float getScreenOffset();
	
	public World getWorld();
	
	public Renderer getRenderer();
}

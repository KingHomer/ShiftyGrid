package com.boone.framework.livewallpaper.wallpaper;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.world.World;

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

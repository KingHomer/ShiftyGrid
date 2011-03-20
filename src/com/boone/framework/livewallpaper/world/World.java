package com.boone.framework.livewallpaper.world;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;


public interface World {
	
	public World instance();
	
	public void onCreate(WallpaperEngine engine);
	
	public void onDestroy();
	
	public void step();
	
	public Renderer render();
	
	public WallpaperEngine getEngine();
	
}

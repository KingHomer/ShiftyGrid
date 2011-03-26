package com.boone.framework.livewallpaper.world;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;

/*
 * Does stuff over time in steps
 */
public interface World {
	
	public void onCreate(WallpaperEngine engine);
	
	public void onDestroy();
	
	public void step();
	
}

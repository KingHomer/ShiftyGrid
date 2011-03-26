package com.boone.framework.livewallpaper.world;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;

public interface World {
	
	public void onCreate(WallpaperEngine engine);
	
	public void onDestroy();
	
	public void step();
	
}

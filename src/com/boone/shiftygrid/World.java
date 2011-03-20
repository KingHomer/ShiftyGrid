package com.boone.shiftygrid;


public interface World {
	
	public World instance();
	
	public void onCreate(WallpaperEngine engine);
	
	public void onDestroy();
	
	public void step();
	
	public Renderer render();
	
	public WallpaperEngine getEngine();
	
}

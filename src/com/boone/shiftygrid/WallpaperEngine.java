package com.boone.shiftygrid;

public interface WallpaperEngine {
	
	public long getStartTime();
	
	public long getElapsedTime();
	
	public long getLastDrawTime();
	
	public float getScreenHeight();
	
	public float getScreenWidth();
	
	public float getScreenOffset();
}

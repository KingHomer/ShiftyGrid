package com.boone.framework.livewallpaper.renderer;

import android.graphics.Canvas;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;

/*
 * Draws world objects onto a canvas
 */
public interface Renderer {

	public void render(Canvas c);

	public void onCreate(WallpaperEngine engine);
}

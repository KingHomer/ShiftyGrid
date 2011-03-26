package com.boone.framework.livewallpaper.renderer;

import android.graphics.Canvas;
import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;

public interface Renderer {

	public void render(Canvas c);

	public void onCreate(WallpaperEngine engine);
}

package com.boone.livewallpaper.reshift;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.BaseWallpaperService;
import com.boone.framework.livewallpaper.world.World;

public class ReShiftWallpaperService extends BaseWallpaperService{

	@Override protected AbstractWallpaperEngine getEngine() {
		return new ReShiftEngine();
	}
	
	private class ReShiftEngine extends AbstractWallpaperEngine {

		@Override public World getWorld() {
			return new ShiftyWorld();
		}
		
		@Override public Renderer getRenderer() {
			return new ShiftyRenderer();
		}
		
	}

}

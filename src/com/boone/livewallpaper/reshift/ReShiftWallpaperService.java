package com.boone.livewallpaper.reshift;

import com.boone.framework.livewallpaper.renderer.Renderer;
import com.boone.framework.livewallpaper.wallpaper.BaseWallpaperService;
import com.boone.framework.livewallpaper.world.World;

public class ReShiftWallpaperService extends BaseWallpaperService{

	@Override protected AbstractWallpaperEngine getEngine() {
		return new ReShiftEngine();
	}
	
	//ShiftyGrid live wallpaper.
	public class ReShiftEngine extends AbstractWallpaperEngine {

		@Override public World createWorld() {
			return new ShiftyWorld();
		}
		
		@Override public Renderer createRenderer() {
			return new ShiftyRenderer();
		}
		
	}

}

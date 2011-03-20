package com.boone.livewallpaper.reshift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;
import com.boone.livewallpaper.shiftygrid.ShiftyBox;

public class ShiftyWorld implements World {

	private ShiftyWorld instance;

	// This needs to be set correctly
	private WallpaperEngine engine;

	// grid instance
	private Map<String, ShiftyBox> boxes;
	private int activeBoxCount;

	// dimensions
	private float mGridHeight;
	private float mGridWidth;

	public World instance() {
		if (instance == null) {
			instance = new ShiftyWorld();
		}
		return instance;
	}

	public ShiftyWorld() {
		// init
		boxes = new HashMap<String, ShiftyBox>();
	}

	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = engine;

		mGridHeight = engine.getScreenHeight();
		mGridWidth = 2 * engine.getScreenWidth();
	}

	@Override public void onDestroy() {
		boxes.clear();
	}

	@Override public void step() {
		// TODO Auto-generated method stub

	}

	public List<ShiftyBox> getBoxes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public WallpaperEngine getEngine() {
		// TODO Auto-generated method stub
		return null;
	}
}

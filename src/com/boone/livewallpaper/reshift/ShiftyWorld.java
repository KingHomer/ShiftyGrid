package com.boone.livewallpaper.reshift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.shiftygrid.ShiftyBox;

public class ShiftyWorld implements World {

	private ReShiftEngine engine;

	// grid instance
	private Map<String, ShiftyBox> boxes;
	private int activeBoxCount;

	// dimensions
	private float mGridHeight;
	private float mGridWidth;

	public ShiftyWorld() {
		// init
		boxes = new HashMap<String, ShiftyBox>();
	}

	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = (ReShiftEngine) engine;

		//this needs to be elsewhere
		mGridHeight = this.engine.getScreenHeight();
		mGridWidth = 2 * this.engine.getScreenWidth();
	}

	@Override public void onDestroy() {
		//need to clean house more.
		boxes.clear();
	}

	@Override public void step() {
		// TODO Auto-generated method stub

	}

	public List<ShiftyBox> getBoxes() {
		// TODO Auto-generated method stub
		return null;
	}
}

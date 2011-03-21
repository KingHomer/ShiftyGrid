package com.boone.livewallpaper.reshift;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.shiftygrid.ShiftyBox;

public class ShiftyWorld implements World {

	private ReShiftEngine engine;

	private Map<String, ShiftyBox> boxes;
	
	private int activeBoxCount;
	private int maxNumBoxes;

	// dimensions
	private float mGridHeight;
	private float mGridWidth;
	private float boxSideLength;
	
	private long lastBoxBuildTime;
	private long lastBoxShiftTime;

	public ShiftyWorld() {
		// init
	}

	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = (ReShiftEngine) engine;
		initGrid();		
	}

	@Override public void onDestroy() {
		//need to clean house more.
		boxes.clear();
	}

	@Override public void step() {
		/*
		 * check time
		 * if build time
		 * 		build box
		 * if shift time
		 * 		shift box
		 * 
		 */
	}
	
	private void initGrid() {
		boxes = new HashMap<String, ShiftyBox>();
		mGridHeight = this.engine.getScreenHeight();
		mGridWidth = 2 * this.engine.getScreenWidth();
		boxSideLength = gcd(mGridWidth, mGridHeight) / 2;
		activeBoxCount = 0;
		maxNumBoxes = findMaxBoxes();
	}

	public Collection<ShiftyBox> getBoxes() {
		return boxes.values();
	}
	
	//Redo this method.
	private int findMaxBoxes() {
		int boxCount = 0;
		
		float bottom = boxSideLength;
		float right;
		while(bottom <= mGridHeight) {
			right = boxSideLength;
			while(right <= mGridWidth) {
				right += boxSideLength;
				
				boxCount++;
			}
			bottom += boxSideLength;
		}
		
		return boxCount;
	}
	
	private float gcd(float a, float b) {
		if (b == 0) {
			return a; 
		} else {
			return gcd(b, a % b);
		}
	}
}

package com.boone.livewallpaper.reshift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.reshift.grid.ShiftyBox;

public class ShiftyWorld implements World {

	private ReShiftEngine engine;

	//Changing this around
	private Collection<ShiftyBox> activeBoxes;
	private Stack<ShiftyBox> availableBoxes;
	
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
		availableBoxes.removeAllElements();
		activeBoxes.clear();
	}

	@Override public void step() {
		/*
		 * check time
		 * if build time
		 * 		if room available
		 * 			build box
		 * if shift time
		 * 		if shiftable box available
		 * 			shift box
		 * 
		 */
	}
	
	private void initGrid() {
		mGridHeight = this.engine.getScreenHeight();
		mGridWidth = 2 * this.engine.getScreenWidth();
		
		activeBoxCount = 0;
		boxSideLength = gcd(mGridWidth, mGridHeight) / 2;  //Change this to make the boxes much smaller
		availableBoxes = new Stack<ShiftyBox>();
		activeBoxes = new ArrayList<ShiftyBox>();
		maxNumBoxes = findMaxBoxes();
		
		//redo
		for(int x = 0; x < maxNumBoxes - 25; x++) {
			availableBoxes.add(new ShiftyBox(boxSideLength));
		}
	}

	public Collection<ShiftyBox> getBoxes() {
		return activeBoxes;
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
	
	//Write a World impl when it becomes more solid
	public float getWorldHeight() {
		return mGridHeight;
	}
	
	public float getWorldWidth() {
		return mGridWidth;
	}
}

package com.boone.livewallpaper.reshift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import android.graphics.RectF;

import com.boone.framework.livewallpaper.wallpaper.WallpaperEngine;
import com.boone.framework.livewallpaper.world.World;
import com.boone.livewallpaper.reshift.ReShiftWallpaperService.ReShiftEngine;
import com.boone.livewallpaper.reshift.grid.ShiftyBox;

public class ShiftyWorld implements World {

	private static long BOX_BUILD_TIMER = 500;

	private ReShiftEngine engine;

	// Changing this around
	private Collection<ShiftyBox> activeBoxes;
	private Stack<ShiftyBox> availableBoxes;
	private ShiftyBox shiftingBox;

	private long lastBoxBuildTime;
	private long lastBoxShiftTime;

	private int activeBoxCount;
	private int maxNumBoxes;

	private float mGridHeight;
	private float mGridWidth;
	private RectF mGrid;
	private float boxSideLength;

	public ShiftyWorld() {
		// init
	}

	@Override public void onCreate(WallpaperEngine engine) {
		this.engine = (ReShiftEngine) engine;
		initGrid();
	}

	@Override public void onDestroy() {
		// need to clean house more.
		availableBoxes.removeAllElements();
		activeBoxes.clear();
	}

	@Override public void step() {
		if (engine.getElapsedTime() - lastBoxBuildTime > BOX_BUILD_TIMER) {
			activateBox();
			lastBoxBuildTime = engine.getElapsedTime();
		}

		// if(shiftingBox.getMoveState() > 0) {

		// }
	}

	private void initGrid() {
		mGridHeight = this.engine.getScreenHeight();
		mGridWidth = this.engine.getScreenWidth();

		mGrid = new RectF(0, 0, mGridWidth, mGridHeight);

		activeBoxCount = 0;
		boxSideLength = gcd(mGridWidth, mGridHeight) / 8; // Change this to make
															// the boxes much
															// smaller
		availableBoxes = new Stack<ShiftyBox>();
		activeBoxes = new ArrayList<ShiftyBox>();
		maxNumBoxes = findMaxBoxes();

		// redo
		for (int x = 0; x < maxNumBoxes - 25; x++) {
			availableBoxes.add(new ShiftyBox(boxSideLength));
		}

		lastBoxBuildTime = 0;
		lastBoxShiftTime = 0;
	}

	public void activateBox() {
		ShiftyBox nextBox = availableBoxes.pop();
		nextBox.active(true);
		placeBox(nextBox);
		activeBoxes.add(nextBox);
	}

	public void killBox(ShiftyBox deadBox) {
		deadBox.active(false);
		activeBoxes.remove(deadBox);
		availableBoxes.add(deadBox);
	}

	private void placeBox(ShiftyBox box) {
		int randX = (int) (Math.random() * mGridWidth);
		int randY = (int) (Math.random() * mGridHeight);

		box.setCenter(randX, randY);
		boolean checkCol = checkCollision(box);
		if (checkCol == false) {
			placeBox(box);
		}
	}

	private boolean checkCollision(ShiftyBox box) {
		for (Iterator<ShiftyBox> boxes = activeBoxes.iterator(); boxes.hasNext();) {
			ShiftyBox activeBox = boxes.next();
			if (RectF.intersects(box.getBoundBox(), activeBox.getBoundBox()) || activeBox.getBoundBox().contains(box.getBoundBox())) {
				return true;
			}
		}

		//return RectF.intersects(mGrid, box.getBoundBox()) || !mGrid.contains(box.getBox()) ? true : false;
		if(RectF.intersects(mGrid, box.getBoundBox()) || !mGrid.contains(box.getBoundBox())) {
			return true;
		}
		return false;
	}

	public Collection<ShiftyBox> getBoxes() {
		return activeBoxes;
	}

	// Redo this method.
	private int findMaxBoxes() {
		int boxCount = 0;

		float bottom = boxSideLength;
		float right;
		while (bottom <= mGridHeight) {
			right = boxSideLength;
			while (right <= mGridWidth) {
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

	// Delete
	public float getWorldHeight() {
		return mGridHeight;
	}

	public float getWorldWidth() {
		return mGridWidth;
	}
}

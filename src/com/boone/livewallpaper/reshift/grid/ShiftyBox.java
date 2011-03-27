package com.boone.livewallpaper.reshift.grid;

import com.boone.framework.livewallpaper.world.shapes.Box;

public class ShiftyBox extends Box {

	//Refactor grid sizing to be static.
	private static float BOXSIZE = 20;
	
	public ShiftyBox(float size) {
		super(0, 0, size, size);
		moveState = NOTMOVING;
		isActive = false;
	}
	
	public ShiftyBox(float size, int color, int alpha) {
		super(size, size, size, size, color, alpha);
		moveState = NOTMOVING;
		isActive = false;
	}

	//redo this whole movement thing
	public static final int NOTMOVING = 0;
	public static final int X = 1;
	public static final int Y = 2;
	private int moveState;
	
	private boolean isActive;
	
	public int getMoveState() {
		return moveState;
	}
	
	public void setMoveState(int moveState) {
		this.moveState = moveState;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void active(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override public void setCenter(float x, float y) {
		getBox().bottom = y + BOXSIZE;
		getBox().top = y - BOXSIZE;
		getBox().right = x + BOXSIZE;
		getBox().left = x - BOXSIZE;
	}
}

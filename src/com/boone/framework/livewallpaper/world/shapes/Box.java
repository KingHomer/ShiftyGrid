package com.boone.framework.livewallpaper.world.shapes;

import android.graphics.Color;
import android.graphics.RectF;

//TODO Work on this
public class Box {

	private RectF box;
	private int color;
	private int alpha;
	
	//TODO animations
	private boolean isAnimating;
	
	public Box(float left, float right, float top, float bottom, int color, int alpha) {
		box = new RectF(left, top, right, bottom);
		this.color = color;
		this.alpha = alpha;
		isAnimating = false;
	}
	
	public Box(float left, float right, float top, float bottom, int color) {
		box = new RectF(left, top, right, bottom);
		this.color = color;
		alpha = 255;
	}
	
	public Box(float left, float right, float top, float bottom) {
		box = new RectF(left, top, right, bottom);
		color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		alpha = 255;
	}
	
	public float getTop() {
		return box.top;
	}
	
	public float getBottom() {
		return box.bottom;
	}
	
	public float getLeft() {
		return box.left;
	}
	
	public float getRight() {
		return box.right;
	}
	
	public float getCenterX() {
		return box.centerX();
	}
	
	public float getCenterY() {
		return box.centerY();
	}
	
	public RectF getBox() {
		return box;
	}
}

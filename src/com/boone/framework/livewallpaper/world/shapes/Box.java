package com.boone.framework.livewallpaper.world.shapes;

import android.graphics.Color;
import android.graphics.RectF;

//TODO Work on this
public class Box {
	
	private RectF box;
	private static final float BOUNDSIZE = 5;
	
	private int color;
	private int alpha;
	
	public Box(float left, float right, float top, float bottom, int color, int alpha) {
		box = new RectF(left, top, right, bottom);
		this.color = color;
		this.alpha = alpha;
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
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getARGB() {
		return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public void setAlpha(int alpha) {
		this.alpha = alpha;
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
	
	//Consider doing the bounding differently
	public float getBoundTop() {
		return box.top + BOUNDSIZE;
	}
	
	public float getBoundBottom() {
		return box.bottom + BOUNDSIZE;
	}
	
	public float getBoundLeft() {
		return box.left + BOUNDSIZE;
	}
	
	public float getBoundRight() {
		return box.right + BOUNDSIZE;
	}
	
	public float getCenterX() {
		return box.centerX();
	}
	
	public float getCenterY() {
		return box.centerY();
	}
	
	public void setCenter(float x, float y) {
		float size = ((box.right - box.left) / 2);
		box.set(x - size, y + size, x + size, y - size);
	}
	
	public RectF getBox() {
		return box;
	}
	
	//Do this differently
	public RectF getBoundBox() {
		//TODO cost waayy to much.  Too expensive to move two boxes when animating.
		RectF boundBox = new RectF(box);
		boundBox.inset(BOUNDSIZE, BOUNDSIZE);
		return boundBox;
	}
}

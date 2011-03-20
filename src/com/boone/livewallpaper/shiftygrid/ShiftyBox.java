package com.boone.livewallpaper.shiftygrid;

import android.graphics.Color;

public class ShiftyBox {
	//Map key attributes
	public int xCount;
	public int yCount;
	
	//coordinate attributes
	public float centerX;
	public float centerY;
	public float left;
	public float right;
	public float top;
	public float bottom;
	private float origLeft;
	private float origRight;
	private float origTop;
	private float origBottom;
	
	//coordinate animation attributes
	private float leftDelta;
	private float rightDelta;
	private float topDelta;
	private float bottomDelta;
	
	//Color dderrp
	public int color;
	private int alpha;
	
	//Display and animation flags, values
	public boolean isActive;
	public boolean isAnimating;
	public boolean isPopping;
	public boolean isShifting;
	public boolean isAlphaPhase;
	private int step;
	private int maxSteps;
	
	//Growth attributes
	public boolean grow;
	public int growCount;
	public long growTime;
	
	//Box to shift to
	public ShiftyBox neighborBox;
	
	public ShiftyBox(float left, float right, float top, float bottom, int color, int xCount, int yCount){
		this.xCount = xCount;
		this.yCount = yCount;
		
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		
		this.color = color;
		this.alpha = 255;
		
		this.centerX = left + right / 2;
		this.centerY = top + bottom / 2;
		
		this.isActive = false;
		this.isAnimating = false;
		this.isShifting = false;
		this.isAlphaPhase = false;
		
		
		this.grow = false;
		
		this.growCount = 0;
		
		step = 0;
		maxSteps = 20;
	}
	
	public ShiftyBox(){
		isActive = false;
	}
	
	public void step(){
		if(neighborBox != null && neighborBox.isShifting){
//			origLeft = left;
//			origRight = right;
//			origTop = top;
//			origBottom = bottom;
//			
//			left = neighborBox.left;
//			right = neighborBox.right;
//			bottom = neighborBox.bottom;
//			top = neighborBox.top;
//			
//			neighborBox.left = origLeft;
//			neighborBox.right = origRight;
//			neighborBox.top = origTop;
//			neighborBox.bottom = origBottom;
			//TODO Here is where the shift animation needs to go. derrrrr
//			if(neighborBox == null){
//				step = maxSteps;
//				this.isAnimating = false;
//			}
//			if(step < maxSteps){
//				if(step == 0){
//					origLeft = left;
//					origRight = right;
//					origTop = top;
//					origBottom = bottom;
//				
//					leftDelta = Math.abs(left - neighborBox.left);
//					rightDelta = Math.abs(right - neighborBox.right);
//					topDelta = Math.abs(top - neighborBox.top);
//					bottomDelta = Math.abs(bottom - neighborBox.bottom);
//				}
//				step++;
//			
//				float fraction = (float) step / (float) maxSteps;
//			
//				if(left < neighborBox.left){
//					left = left + (fraction * leftDelta);
//				}else{
//					left = left - (fraction * leftDelta);
//				}
//				
//				if(right < neighborBox.right){
//					right = right + (fraction * rightDelta);
//				}else{
//					right = right - (fraction * rightDelta);
//				}
//				
//				if(top < neighborBox.top){
//					top = top + (fraction * topDelta);
//				}else{
//					top = top - (fraction * topDelta);
//				}
//
//				if(bottom < neighborBox.bottom){
//					bottom = bottom + (fraction * bottomDelta);
//				}else{
//					bottom = bottom + (fraction * bottomDelta);
//				}
//			}else{
//				//TODO finish this
//				step = 0;
//				isAnimating = false;
//			}
		}else if(isAlphaPhase){
			if(step <= maxSteps){
				if(step == 0){
					alpha = 0;
				}
				
				step++;
				
				float fraction = (float) step / (float)maxSteps;
				
				alpha = (int)(fraction * 255);
			}else{
				isAlphaPhase = false;
				grow = true; 
				alpha = 255;
				step = 0;
			}
		}else if(isPopping){
			if(step <= maxSteps){
				if(step == 0){
					origLeft = left;
					origRight = right;
					origTop = top;
					origBottom = bottom;
								
					leftDelta = left - centerX;
					rightDelta = right - centerX;
					topDelta = top - centerY;
					bottomDelta = bottom - centerY;
					
					left = centerX;
					right = centerX;
					top = centerY;
					bottom = centerY;
				}
				step++;
			
				float fraction = (float) step / (float)maxSteps;
			
				left = centerX + (fraction * leftDelta);
				right = centerX + (fraction * rightDelta);
				top = centerY + (fraction * topDelta);
				bottom = centerY + (fraction * bottomDelta);
			}else{
				isAnimating = false;
				left = origLeft;
				right = origRight;
				top = origTop;
				bottom = origBottom;
				
				step = 0;
			}
		}
	}

	public String getCenterKey(){
		//return centerX + ", " + centerY;
		String key = xCount + "', " + yCount;
		return key;
		//return xCount + ", " + yCount;
	}
	
	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getARGB(){
		return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
	}

	public String getLeftNeighbor(float boxSideLength) {
		return (xCount -1) + "', " + yCount;
	}

	public String getTopNeighbor(float boxSideLength) {
		return xCount + "', " + (yCount + -1);
	}

	public String getRightNeighbor(float boxSideLength) {
		return (xCount + 1)  + "', " + yCount;
	}

	public String getBottomNeighbor(float boxSideLength) {
		return xCount + "', " + (yCount + 1);
	}
	
	public void resetCenter(){
		this.centerX = this.left + this.right / 2;
		this.centerY = this.top + this.bottom / 2;
	}
}

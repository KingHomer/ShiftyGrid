package com.boone.livewallpaper.shiftygrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class ShiftyGridWallpaper extends WallpaperService {
	
	public void onCreate(){
		super.onCreate();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public Engine onCreateEngine() {
		return new ShiftyGridEngine();
	}

	//First write of wallpaper.
	class ShiftyGridEngine extends Engine{
		private Handler mHandler = new Handler();
		
		private boolean mVisible;
		
		//Timers
		private long mStartTime;
		private long mElapsedTime;
		private long mLastDrawTime;
		
		private final Paint mPaint = new Paint();
		
		//Dimensions
		private float mScreenHeight;
		private float mScreenWidth;
		private float mGridHeight;
		private float mGridWidth;
		
		//Grid constraints
		private int mNumActiveBoxes;
		private int mMaxRandomBoxes;
		private boolean mGrowFlag;
		private int mMinActiveBoxes;
		
		//Grid constants
		private float boxSideLength;
		
		private float mOffsetX;
		
		//Map of grid boxes; List of blank boxes
		private Map<String, ShiftyBox> boxes;
		private ArrayList<String> shiftList;
		
		private Runnable mDrawShiftyGrid = new Runnable(){
			@Override
			public void run() {
				queueNext();
				drawFrame();
			}
			
		};
		
		public ShiftyGridEngine(){
			final Paint paint = mPaint;
            paint.setColor(0xffffffff);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(2);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            
            mStartTime = SystemClock.elapsedRealtime();
      
            mLastDrawTime = 0;
		}
		
		public void onCreate(SurfaceHolder holder){
			super.onCreate(holder);
			
			mScreenHeight = getDesiredMinimumHeight();
			mScreenWidth = getDesiredMinimumWidth();
			
			mGridHeight = mScreenHeight;
			mGridWidth = 2 * mScreenWidth;
			
			boxSideLength = gcd(mGridWidth, mGridHeight) / 2;
			mNumActiveBoxes = 0;
			
			mMaxRandomBoxes = 5;
			mGrowFlag = true;
			
			buildGrid();
			
			setTouchEventsEnabled(true);
		}
		
		public void onDestroy(){
			super.onDestroy();
			mHandler.removeCallbacks(mDrawShiftyGrid);
		}
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder){
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDrawShiftyGrid);
		}
		
		public void onVisibilityChanged(boolean visible){
			mVisible = visible;
			queueNext();
			if(visible){
				drawFrame();
			}
		}
		
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        	super.onSurfaceChanged(holder, format, width, height);
        	
        	mScreenHeight = height;
        	mScreenWidth = width;
        	
        	mGridWidth = 2 * width;
        	
        	queueNext();
        	drawFrame();
        }
        
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                float xStep, float yStep, int xPixels, int yPixels) {
            mOffsetX = xPixels;
            
            queueNext();
            drawFrame();
        }
		
		private void queueNext(){
			mElapsedTime = SystemClock.elapsedRealtime() - mStartTime;

//			if(!mGrowFlag && mNumActiveBoxes < mMinActiveBoxes - 8){
//				activateBox();
//				mGrowFlag = true;
//			}
			
			if(mElapsedTime - mLastDrawTime > 1000){
				//TODO this should be controlled with a timer
				if(mNumActiveBoxes >= boxes.size() || mNumActiveBoxes >= mMinActiveBoxes){
					buildGrid();
				}
				mLastDrawTime = mElapsedTime;
				
				/**
				 * TODO
				 * This should only activate a box if it is below the max number of random boxes
				 * or if the number of active boxes is below the minimum count and the grid is not
				 * growing.  Animation is cool here just no growing.
				 */
				if(mNumActiveBoxes < mMaxRandomBoxes){
					activateBox();
				}
				
				if(!mGrowFlag){
					if(mNumActiveBoxes < mMinActiveBoxes){
						activateBox();
					}
					//shiftBox();
				}
			}
		
			mHandler.removeCallbacks(mDrawShiftyGrid);
			if(mVisible){
				mHandler.postDelayed(mDrawShiftyGrid, 1000 / 25);
			}
		}
		
		private ShiftyBox getRandomSide(ShiftyBox box, boolean activeFlag) {
			Random random = new Random();
			int side = random.nextInt(4);
			String key = "";
			int county = 1;
						
			while(true){
				switch(side){
					case(0):
						side++;
					case(1):
						key =  box.getLeftNeighbor(boxSideLength);
						break;
					case(2):
						key =  box.getTopNeighbor(boxSideLength);
						break;
					case(3):
						key =  box.getRightNeighbor(boxSideLength);
						break;
					case(4):
						key = box.getBottomNeighbor(boxSideLength);
						break;
				
				}
				
				synchronized(this.boxes){
					if(boxes.get(key) != null){
						if(boxes.get(key).isActive == activeFlag){
							return boxes.get(key);
						}else{
							if(county == 4){
								return null;
							}
							if(side == 4){
								side = 0;
							}
							side++;
							county++;
						}
					}else{
						if(side == 4){
							return null;
						}
						side++;
					}
				}
			}				
		}
		
		//TODO This is totally busted.  Boxes stay flagged for growin
		private void growBox(ShiftyBox box) {
			if(mNumActiveBoxes < mMinActiveBoxes){
				synchronized(this.boxes){
					ShiftyBox growBox = null;
					growBox = getRandomSide(box, false);
					if(growBox == null){
						box.grow = false;
					}else{
						//TODO should use activateBox method
						activateBox(growBox, false);
						box.growCount++;
						growBox.isAlphaPhase = true;
						growBox.growCount = box.growCount;
						growBox.growTime = mElapsedTime;
						if(growBox.growCount >= 8){
							growBox.grow = false;
						}
						
						//boxes.put(growBox.getCenterKey(), growBox);
						box.grow = false;
						
						//boxes.put(box.getCenterKey(), box);
					
						//mNumActiveBoxes++;
					}
				}
			}else{
				box.grow = false;
			}
		}

		private void shiftBox() {
			synchronized(this.boxes){
				//TODO Shift dem boxes
				if(shiftList == null || shiftList.size() == 0){
					shiftList = new ArrayList<String>();
					Iterator<ShiftyBox> it = boxes.values().iterator();
					
					ShiftyBox emptyBox = null;
					while(it.hasNext()){
						emptyBox = it.next();
						if(!emptyBox.isActive){
							//TODO set all attributes here
							emptyBox.neighborBox = getRandomSide(emptyBox, true);
							if(emptyBox.neighborBox != null){
								emptyBox.neighborBox.isAnimating = true;
								emptyBox.neighborBox.isShifting = true;
								emptyBox.neighborBox.growTime = mElapsedTime;
							}
							
							shiftList.add(emptyBox.getCenterKey());
						}
					}
				}
				
				for(String key : shiftList){
					ShiftyBox emptyBox = boxes.get(key);
					if(emptyBox.neighborBox == null || emptyBox.isActive){
						continue;
					}
					if(emptyBox.neighborBox.isAnimating){
						emptyBox.neighborBox.step();
					}else{
						//TODO maybe change this time constant ... test
						if(mElapsedTime - emptyBox.growTime > 5000){
							emptyBox.neighborBox = getRandomSide(emptyBox, true);
							if(emptyBox.neighborBox != null){
								emptyBox.neighborBox.isAnimating = true;
								emptyBox.neighborBox.isShifting = true;
								emptyBox.growTime = mElapsedTime;
							}
						}
					}
				}
			}
		}

		private void activateBox(){
			mGrowFlag = true;
			synchronized(this.boxes){
				if(boxes.size() != 0 && mNumActiveBoxes < mMinActiveBoxes){
					Random generator = new Random();
					Object[] values = boxes.values().toArray();
					while(true){
						ShiftyBox boxxy = (ShiftyBox) values[generator.nextInt(values.length)];
						if(boxxy.isActive){
							continue;
						}else{
							activateBox(boxxy, true);
							break;
						}
					}
				}
			}
		}
		
		private void activateBox(ShiftyBox inactiveBox, boolean animationFlag){
			if(mNumActiveBoxes < mMinActiveBoxes){
				synchronized(this.boxes){
					inactiveBox.isActive = true;
					inactiveBox.isAnimating = true;
					inactiveBox.grow = true;
					inactiveBox.isPopping = animationFlag;
					
					//boxes.put(inactiveBox.getCenterKey(), inactiveBox);
					
					mNumActiveBoxes++;
				}
			}
		}

		public void buildGrid(){
			mNumActiveBoxes = 0;
			
			int xCount = 1;
			int yCount = 1;
			
			float left = 0;
			float top = 0;
			float right = boxSideLength;
			float bottom = boxSideLength;
			
			int red, green, blue;
			
			boxes = new HashMap<String, ShiftyBox>();
			synchronized(this.boxes){
				ShiftyBox box = null;
				while(bottom <= mGridHeight){
					right = boxSideLength;
					left = 0;
				
					while(right <= mGridWidth){
						red = (int)(Math.random() * 255);
						green = (int)(Math.random() * 255);
						blue = (int)(Math.random() * 255);
						box = new ShiftyBox(left + 5, right - 5, top + 5, bottom - 5, Color.rgb(red, green, blue), xCount, yCount);
					
						left += boxSideLength;
						right += boxSideLength;
						
						xCount++;
					
						boxes.put(box.getCenterKey(), box);
					}
					top += boxSideLength;
					bottom += boxSideLength;
					
					xCount = 1;
					yCount++;
				}
				
				mMinActiveBoxes = boxes.size() - 5;
			}
		}
		
		public void drawFrame(){
			final SurfaceHolder holder = getSurfaceHolder();
			
			Canvas c = null;
			try{
				c = holder.lockCanvas();
				if(c != null){
					drawGrid(c);
				}
			}finally{
				if(c != null){
					holder.unlockCanvasAndPost(c);
				}
			}
		}
		
		public void drawGrid(Canvas c){
			mGrowFlag = false;
			
			c.save();
			c.drawColor(0xff000000);
			
			synchronized(this.boxes){
				Iterator<ShiftyBox> i = boxes.values().iterator();
				RectF rect = null;
				while(i.hasNext()){
					ShiftyBox box = i.next();
					//mGrowFlag = false;
					
					if(box.isAnimating && !box.grow){
						mGrowFlag = true;
						box.step();	
					}else if(box.grow){
						mGrowFlag = true;
						if(mElapsedTime - box.growTime > 3000){
							box.growTime = mElapsedTime;
							growBox(box);
						}
					}
					
					if(!box.isActive){
						continue;
					}
				
					if(box.left > (-this.mOffsetX + mScreenWidth)){
						continue;
					}
	
					if(box.right < -this.mOffsetX){
						continue;
					}
					
					//mPaint.setColor(box.getColor());
					mPaint.setColor(box.getARGB());
					rect = new RectF(box.left, box.top, box.right, box.bottom);
					rect.offsetTo(box.left + mOffsetX, box.top);
					c.drawRoundRect(rect, 0.0f, 0.0f, mPaint);
			
				}
			}
			
			c.restore();
		}
		
		private float gcd(float a, float b) {
			   if (b==0) 
			     return a;
			   else
			     return gcd(b, a % b);
		 } 	
	}
}

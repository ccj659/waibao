package com.example.jinglinzichan.view;

import com.example.jinglinzichan.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 水波浪球形进度View
 */
public class SinkView_xiangqing extends FrameLayout {
	private Bitmap mBitmap, mScaleBitmap;
	public Paint mPaint = new Paint();

	private int mRepeatCount;
	private int mSpeed = 15;
	private float mLeft, mPercent;
	private int mPercents;

	private static final int mTextColor = 0xFFFFFFFF;
	private static final int mTextSize = 70;

	public SinkView_xiangqing(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setPercent(int percent) {
		mPercents = percent;
		mPercent = (float) mPercents / 100;
		postInvalidate();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		// 裁剪成圆区域
		Path path = new Path();
		canvas.save();
		path.reset();
		canvas.clipPath(path);
		path.addCircle(width / 2, height / 2, width / 2, Path.Direction.CCW);
		canvas.clipPath(path, Region.Op.REPLACE);

		if (mScaleBitmap == null) {
			mBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.baise);
			mScaleBitmap = Bitmap.createScaledBitmap(mBitmap,
					mBitmap.getWidth(), getHeight(), false);
			mBitmap.recycle();
			mBitmap = null;
			mRepeatCount = (int) Math.ceil(getWidth() / mScaleBitmap.getWidth()
					+ 0.5) + 1;
		}

		for (int i = 0; i < mRepeatCount; i++) {
			canvas.drawBitmap(mScaleBitmap,
					mLeft + (i - 1) * mScaleBitmap.getWidth(),
					(float) ((0.99 - mPercent) * getHeight()), null);
		}
		String text = (int) (mPercent * 100) + "%";
		mPaint.setColor(mTextColor);
		mPaint.setTextSize(mTextSize);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawText(text, (getWidth() - mPaint.measureText(text)) / 2,
				getHeight() / 2 + mTextSize / 2, mPaint);

		mLeft += mSpeed;
		if (mLeft >= mScaleBitmap.getWidth()) {
			mLeft = 0;
		}

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(4);
		mPaint.setAntiAlias(true);
		mPaint.setColor(mTextColor);
		canvas.drawCircle(width / 2, height / 2, width / 2 - 2, mPaint);
		postInvalidateDelayed(40);
		canvas.restore();
	}
}
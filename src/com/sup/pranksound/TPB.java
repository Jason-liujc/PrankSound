package com.sup.pranksound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;


public class TPB extends ProgressBar{
	public String text="";
	private int textColor = Color.WHITE;
	private float textSize = 30;
	Paint textPaint = new Paint();
	Rect bounds = new Rect();

	public TPB(Context context) {
		super(context);
	}
	
	public TPB(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	public TPB(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	}
	
	public synchronized void setText(String text) {
	    if (text != null) {
	        this.text = text;
	    } else {
	        this.text = "";
	    }
	    postInvalidate();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    //create an instance of class Paint, set color and font size
	    textPaint.setAntiAlias(true);
	    textPaint.setColor(textColor);
	    textPaint.setTextSize(textSize);
	    //In order to show text in a middle, we need to know its size
	    textPaint.getTextBounds(text, 0, text.length(), bounds);
	    //Now we store font size in bounds variable and can calculate it's position
	    int x = getWidth() / 2 - bounds.centerX();
	    int y = getHeight() / 2 - bounds.centerY();
	    //drawing text with appropriate color and size in the center
	    canvas.drawText(text, x, y, textPaint);
	}
	
}

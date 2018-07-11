package com.quseit.widget;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class CustomEditText extends EditText
{
  private Drawable dRight;
  private Rect rBounds;

  public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }
  public CustomEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public CustomEditText(Context context) {
    super(context);
  }

  @Override
  public void setCompoundDrawables(Drawable left, Drawable top,
      Drawable right, Drawable bottom)
  {
    if(right !=null)
    {
      dRight = right;
    }
    super.setCompoundDrawables(left, top, right, bottom);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {

    if(event.getAction() == MotionEvent.ACTION_UP && dRight!=null)
    {
      rBounds = dRight.getBounds();
      final int x = (int)event.getX();
      final int y = (int)event.getY();
      if(x>=(this.getRight()-rBounds.width()) && x<=(this.getRight()-this.getPaddingRight())
          && y>=this.getPaddingTop() && y<=(this.getHeight()-this.getPaddingBottom()))
      {
        this.setText("");
        event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
      }
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void finalize() throws Throwable
  {
    dRight = null;
    rBounds = null;
    super.finalize();
  }
}

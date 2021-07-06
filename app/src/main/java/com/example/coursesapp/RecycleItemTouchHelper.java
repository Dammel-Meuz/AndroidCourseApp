package com.example.coursesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.security.PrivateKey;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class RecycleItemTouchHelper extends ItemTouchHelper.SimpleCallback {


    int buttonWidth;
    private RecyclerView recyclerView;
    private List<MyButton> buttonList;
    private GestureDetector gestureDetector;
    private int swipePosition = -1;
    private float swipethreshold = 0.5f;
    private Map<Integer, List<MyButton>>  buttonBuffer;
    private Queue<Integer> removerQueue;

    private  GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (MyButton button:buttonList){
                if (button.onClick(e.getX(),e.getY()))
                    break;
            }
            return true;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (swipePosition<0)return false;
            Point point = new Point((int) event.getRawX(),(int)event.getRawY());
            RecyclerView.ViewHolder swipViewHolder =recyclerView.findViewHolderForAdapterPosition(swipePosition);
            View swipedItem = swipViewHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            if (event.getAction() == event.ACTION_DOWN || event.getAction() == event.ACTION_UP ||
                    event.getAction() == event.ACTION_MOVE){
                if (rect.top < point.y && rect.bottom>point.y)
                    gestureDetector.onTouchEvent(e);
                else {
                    removerQueue.add(swipePosition);
                    swipePosition = -1;
                }
            }
        }
    }


    public RecycleItemTouchHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }


    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    private class MyButton {
        private String text;
        private int imageResId,textSize,color,pos;
        private RectF clickRegion;
        private MyButtonClickListener listener;
        private Context context;
        private Resources resources;

        public MyButton(String text, int imageResId, int textSize, int color, int pos, RectF clickRegion, MyButtonClickListener listener, Context context ) {
            this.text = text;
            this.imageResId = imageResId;
            this.textSize = textSize;
            this.color = color;
            this.pos = pos;
            this.clickRegion = clickRegion;
            this.listener = listener;
            this.context = context;
            resources = context.getResources();
        }
        public Boolean onClick(float x, float y){
            if (clickRegion != null && clickRegion.contains(x,y)){
                listener.onClick(pos);
                return false;
            }
            return false;
        }
        public void onDraw(Canvas c, RectF rectF, int pos){
            Paint p =new Paint();
            p.setColor(color);
            c.drawRect(rectF,p);
            //text
            p.setColor(Color.WHITE);
            p.setTextSize(textSize);

            Rect rect = new Rect();
            float cHeight = rectF.height();
            float cWidth = rectF.width();
            p.setTextAlign(Paint.Align.LEFT);
            p.getTextBounds(text,0,text.length(),rect);
            float x = 0,y = 0;
            if (imageResId == 0){   //if just show text
                x=cWidth/2f - rect.width()/2f -rect.left;
                y=cHeight/2f+rect.height()/2f-rect.bottom;
                c.drawText(text,rectF.left+x,rectF.top+y,p);
            }else {
                Drawable d = ContextCompat.getDrawable(context,imageResId);
                Bitmap bitmap = drawableToBitmap(d);
                        c.drawBitmap(bitmap,(rectF.left+rectF.right)/2,(rectF.top+rectF.bottom)/2,p);
            }
            clickRegion = rectF;
            this.pos = pos;
        }
    }

    private Bitmap drawableToBitmap(Drawable d) {
        if (d instanceof BitmapDrawable)
            return ((BitmapDrawable)d).getBitmap();
        Bitmap bitmap= Bitmap.createBitmap(d.getIntrinsicWidth(),d.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas= new Canvas(bitmap);
        d.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        d.draw(canvas);
        return bitmap;
    }

}

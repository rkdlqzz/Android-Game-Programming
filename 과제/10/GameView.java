package com.example.morecontrols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Paint paint = new Paint();
    private Bitmap soccerBitmap;
    private Rect soccerBallSrcRect = new Rect();
    private Rect soccerBallDstRect = new Rect();
    private Paint circle1Paint = new Paint();
    private Paint circle2Paint = new Paint();
    private Paint textPaint = new Paint();
    private Rect textBoundRect = new Rect();

    public GameView(Context context) {
        super(context);
        Log.d(TAG, "GameView cons");
        initView();
    }

    public GameView(Context context, AttributeSet as) {
        super(context, as);
        Log.d(TAG, "GameView cons with as");
        initView();
    }

    private void initView() {
        paint.setColor(Color.MAGENTA);
        circle1Paint.setColor(Color.RED);
        circle2Paint.setColor(Color.GREEN);
        textPaint.setColor(Color.BLUE);

        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        soccerBallSrcRect.set(0, 0, soccerBitmap.getWidth(), soccerBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas); // canvas : 그림을 그릴 대상
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        int contentWidth = width - left - right;
        int contentHeight = height - top - bottom;

        int cx = left + contentWidth / 2;
        int cy = top + contentHeight / 2;
        int size = (int) (contentWidth + contentHeight) / 2;

        // 배경
        canvas.drawRoundRect(left, top, left + contentWidth, top + contentHeight, 20, 20, paint);

        // 축구공
        int soccerBallRadius = size / 6;
        soccerBallDstRect.set(cx - soccerBallRadius, cy - soccerBallRadius, cx + soccerBallRadius, cy + soccerBallRadius);
        canvas.drawBitmap(soccerBitmap, soccerBallSrcRect, soccerBallDstRect, null);

        // 좌하단 원
        int c1cx = left + contentWidth / 4;
        int c1cy = cy + contentHeight / 4;
        int circle1Radius = size / 10;
        canvas.drawCircle(c1cx, c1cy, circle1Radius, circle1Paint);

        // 우상단 원
        int c2cx = cx + contentWidth / 4;
        int c2cy = top + contentHeight / 4;
        int circle2Radius = size / 10;
        canvas.drawCircle(c2cx, c2cy, circle2Radius, circle2Paint);

        // 우하단 텍스트
        String text = "Soccer";
        textPaint.setTextSize(size / 10);
        textPaint.getTextBounds(text, 0, text.length(), textBoundRect);
        int tx = cx + contentWidth / 4 - textBoundRect.width() / 2;
        int ty = cy + contentHeight / 4 + textBoundRect.height() / 2;
        canvas.drawText(text, tx, ty, textPaint);
    }
}

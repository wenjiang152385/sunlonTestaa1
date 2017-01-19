package com.oraro.sunlon.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.oraro.sunlon.Interface.MyCircleCallback;
import com.oraro.sunlon.constant.XYCoordinateSet;
import com.oraro.sunlon.sunlontesta.R;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class OverCircleView extends View {

    /**
     * 画笔
     */
    private Paint mPaint;


    private Bitmap bitmap;

    private int mCircleX = 400;

    private int mCircleY = 400;

    private int mRadius = 400;


    /**
     * 初始偏移量
     */
    private double mOffsetAngle = -45;


    /**
     * 控件个数
     */
    private int mItemCount = 4;

    /**
     * 控件半径
     */
    private int mItemRaduis = 60;


    /**
     * 每个控件的夹角
     */
    float itemAngle = 360 / mItemCount;


    /**
     * 保存上一次滑动的坐标点
     */
    private float mFloatX;
    private float mFloatY;

    /**
     * 保存上次选中的圆形控件
     */
    private int mCirleItemIndex = -1;

    private int mCurrentItemIndex = -1;


    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;

    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;


    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;


    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;


    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;


    /**
     * 圆形控件索引切换回调
     */
    private MyCircleCallback callback;

    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    /**
     * 自动修正的任务
     */
    private AutoReviseRunnable mReviseRunnable;


    public OverCircleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mCircleX = 0;
        mCircleY = getDisplayMetrics().heightPixels / 2;
        mRadius = getDisplayMetrics().heightPixels * 7 / 18;
//        post(mFlingRunnable = new AutoFlingRunnable((float)mOffsetAngle));
    }

    public void autoRun() {
        post(mFlingRunnable = new AutoFlingRunnable(450));
    }

    public void setOnCircleItemChangeListener(MyCircleCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        drawVirtualCircleWithRectangularCoordinate(canvas, mCircleX, mCircleY, mRadius);

//        drawVirtualCircleWithRectangularCoordinate(canvas, mCircleX, mCircleY, mRadius * 3 / 5);

        getStandardYCoordinateSet();

        drawCircleItemsWithRectangularCoordinate(canvas, mItemRaduis, mItemCount, mOffsetAngle);


        //canvas.drawLine(0, 400, 800, 400, mPaint);


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        float tpX = event.getX();
        float tpY = event.getY();

//        int distanceFromCirclePoints = (int) distanceOfTwoPoints(tpX, tpY, mCircleX, mCircleY);
        //排除正好点击在圆上的情况，当点击在相邻扇形交界点会造成无法判定那个区域的问题所以直接屏蔽掉
//        if (distanceFromCirclePoints >= mRadius) {
//            //Log.e("wjq","超出范围不去监听touch事件 = " + distanceFromCirclePoints);
//            reviseTrail();
//            return true;
//        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mFloatX = tpX;

                mFloatY = tpY;

                mDownTime = System.currentTimeMillis();

                mTmpAngle = 0;
                removeCallbacks(mReviseRunnable);

                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }


                break;

            case MotionEvent.ACTION_MOVE:
                float start = getAngle(mFloatX, mFloatY);
                float end = getAngle(tpX, tpY);

                if (getQuadrant(tpX, tpY) == 1 || getQuadrant(tpX, tpY) == 4) {
                    mOffsetAngle += end - start;
                    mTmpAngle += end - start;
                } else {
                    mOffsetAngle += start - end;
                    mTmpAngle += start - end;
                }
                invalidate();

                mFloatX = tpX;

                mFloatY = tpY;

                break;


            case MotionEvent.ACTION_UP:


                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);


//                Log.e("wjq","anglePerSecond = " + anglePerSecond);
                // Log.e("wjq", anglePrMillionSecond + " , mTmpAngel = " +
                // mTmpAngle);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                } else {
                    reviseTrail();
                }


                break;

            default:

                break;
        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        int[] imgDrawable = {R.mipmap.app_icon, R.mipmap.pic_com, R.mipmap.pic_mon, R.mipmap.pic_rice};
//        String[] textTitle = {"公司简介", "视频简介", "视频监控", "产品介绍"};
//        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//        for (int i = 0; i < mItemCount; i++) {
//            View view = layoutInflater.inflate(R.layout.circle_item, null);
//            TextView textView = (TextView) view.findViewById(R.id.circle_items);
//            Drawable tabDrawable = getResources().getDrawable(imgDrawable[i]);
//            tabDrawable.setBounds(0, 0, 210, 210);
//            textView.setCompoundDrawables(null, tabDrawable, null, null);
//            textView.setText(textTitle[i]);
//        }

    }


    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private DisplayMetrics getDisplayMetrics() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredSize(widthMeasureSpec, true);
        int height = getMeasuredSize(heightMeasureSpec, false);
        setMeasuredDimension(width, height);
    }


    /**
     * 计算控件的实际大小
     *
     * @param length  onMeasure方法的参数，widthMeasureSpec或者heightMeasureSpec
     * @param isWidth 是宽度还是高度
     * @return int 计算后的实际大小
     */
    private int getMeasuredSize(int length, boolean isWidth) {
        // 模式
        int specMode = MeasureSpec.getMode(length);
        // 尺寸
        int specSize = MeasureSpec.getSize(length);
        // 计算所得的实际尺寸，要被返回
        int retSize = 0;

        // 对不同的指定模式进行判断
        if (specMode == MeasureSpec.EXACTLY) {  // 显式指定大小，如40dp或fill_parent
            retSize = specSize;
        } else {                              // 如使用wrap_content
            retSize = (isWidth ? mCircleX + mRadius : mCircleY + mRadius);
            if (specMode == MeasureSpec.UNSPECIFIED) {
                retSize = Math.min(retSize, specSize);
            }
        }

        return retSize;
    }


    /**
     * 通过直角坐标画圆
     *
     * @param canvas  画布
     * @param circleX 圆心横坐标
     * @param circleY 圆心纵坐标
     * @param radius  半径
     */
    private void drawVirtualCircleWithRectangularCoordinate(Canvas canvas, int circleX, int circleY, int radius) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(circleX, circleY, radius, mPaint);
    }

    /**
     * 画圆形控件
     *
     * @param canvas 画布
     * @param radius 圆形控件半径
     * @param count  控件个数
     */
    private void drawCircleItemsWithRectangularCoordinate(Canvas canvas, int radius, int count, double offsetAngle) {
        int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.GRAY, Color.RED, Color.YELLOW, Color.GREEN};
        int[] imgDrawable = {R.drawable.app_icon, R.drawable.pic_com, R.drawable.pic_mon, R.drawable.pic_rice};
        int[] imgNormal = {R.drawable.app_icon_normal,R.drawable.pic_com_normal,R.drawable.pic_mon_normal,R.drawable.pic_rice_normal};
        String[] textTitle = {"农场介绍", "视频简介", "视频监控", "产品介绍"};
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
//        LayoutInflater mInflater = LayoutInflater.from(getContext());
        float itemCircleAngle = itemAngle / 2;
        for (int i = 0; i < count; i++) {
            mPaint.setColor(colors[i]);
            double angle = itemCircleAngle + itemAngle * i + offsetAngle;
            XYCoordinateSet xyCoordinateSet = getCircleItemXYCoordinateSet(angle);
//            Log.e("wjq","i = " + i + "angle = " + angle);


            canvas.drawCircle(xyCoordinateSet.getXCoordinateSet(), xyCoordinateSet.getYCoordinateSet(), radius, mPaint);

            //img
            Paint bitMapPaint = new Paint();
            bitMapPaint.setAntiAlias(true);
            bitmap = BitmapFactory.decodeResource(getResources(), imgNormal[i]);
            Bitmap myBitmap = bitmap.createScaledBitmap(bitmap, mRadius * 1 / 2, mRadius * 1 / 2, true);
            canvas.drawBitmap(myBitmap,
                    xyCoordinateSet.getXCoordinateSet() - mRadius * 1 / 4,
                    xyCoordinateSet.getYCoordinateSet() - mRadius * 1 / 4, bitMapPaint);
            bitmap.recycle();

            if (isInStandardYCoordinateSet(xyCoordinateSet) == true) {
                mCurrentItemIndex = i;
                bitmap = BitmapFactory.decodeResource(getResources(), imgDrawable[i]);
                Bitmap myBitmap1 = bitmap.createScaledBitmap(bitmap, mRadius * 3 / 5, mRadius * 3 / 5, true);
                canvas.drawBitmap(myBitmap1,
                        xyCoordinateSet.getXCoordinateSet() - mRadius * 3 / 10,
                        xyCoordinateSet.getYCoordinateSet() - mRadius * 3 / 10, bitMapPaint);
                bitmap.recycle();
            }
            //text
            Paint textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(mRadius / 10);
            canvas.drawText(textTitle[i],
                    xyCoordinateSet.getXCoordinateSet() - mRadius * 1 / 5,
                    xyCoordinateSet.getYCoordinateSet() + mRadius * 118 / 300, textPaint);

//            canvas.drawLine(xyCoordinateSet.getXCoordinateSet(), xyCoordinateSet.getYCoordinateSet(), 0, 0, mPaint);
//            canvas.drawLine(xyCoordinateSet.getXCoordinateSet(),xyCoordinateSet.getYCoordinateSet(), xyCoordinateSet.getXCoordinateSet(), 0,mPaint);
        }

    }

    /**
     * 通过传入的偏移角度，圆形控件的坐标对象
     *
     * @param angle 角度
     * @return
     */
    private XYCoordinateSet getCircleItemXYCoordinateSet(double angle) {
        XYCoordinateSet xyCoordinateSet = new XYCoordinateSet();

        /**
         * 规定控件的运动轨迹是以大圆圆心为圆心，大圆3/5半径为半径的圆上运动
         */
        double itemMovementTracksRaduis = mRadius * 3 / 5;

        double anglePI = angle * Math.PI / 180;
        float itemCircleX = (float) (mCircleX + itemMovementTracksRaduis * Math.cos(anglePI));

        float itemCircleY = (float) (mCircleY + itemMovementTracksRaduis * Math.sin(anglePI));

        xyCoordinateSet.setXCoordinateSet(itemCircleX);
        xyCoordinateSet.setYCoordinateSet(itemCircleY);

        return xyCoordinateSet;
    }


    private double angleInStandard;

    /**
     * 修正轨迹
     */
    private void reviseTrail() {

        /**
         * 判断圆形控件索引是否发送改变
         */
        if (!isFling) {
            if (mCurrentItemIndex != mCirleItemIndex) {
                callback.getIndex(mCurrentItemIndex);
            }
            mCirleItemIndex = mCurrentItemIndex;
        }

        removeCallbacks(mFlingRunnable);
        // Log.e("wjq","mCurrentItemIndex = " + mCurrentItemIndex);
        double angle = itemAngle / 2 + itemAngle * mCurrentItemIndex + mOffsetAngle;
        XYCoordinateSet xyCoordinateSet = getCircleItemXYCoordinateSet(angle);
        // Log.e("wjq","mCurrentItemIndex = " + mCurrentItemIndex +" angle = " + angle);
        angleInStandard = -getAngle(xyCoordinateSet.getXCoordinateSet(), xyCoordinateSet.getYCoordinateSet());
        // Log.e("wjq","得到在－PI ／ 2 ～ PI ／ 2 的夹角 ＝ " + getAngle(xyCoordinateSet.getXCoordinateSet(),xyCoordinateSet.getYCoordinateSet()));
        post(mReviseRunnable = new AutoReviseRunnable(angleInStandard));

    }


    /**
     * 计算两点之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private double distanceOfTwoPoints(float x1, float y1, float x2, float y2) {
        float distanceX = Math.abs(x1 - x2);
        float distanceY = Math.abs(y1 - y2);
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance;

    }


    /**
     * 根据当前位置计算象限
     * 和android规定的屏幕象限一致
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mCircleX);
        int tmpY = (int) (y - mCircleY);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 1 : 4;
        } else {
            return tmpY >= 0 ? 2 : 3;
        }

    }


    /**
     * 通过反正弦函数求出角度。Math.asin的值域是在 (-pi / 2 ~ pi / 2)
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mCircleX);
        double y = yTouch - (mCircleY);

        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }


    /**
     * 获得判定范围
     *
     * @param
     * @param
     */
    private XYCoordinateSet getStandardYCoordinateSet() {
        XYCoordinateSet standardYCoordinateSet = new XYCoordinateSet();
        float itemCircleAngle = itemAngle / 2;
        double minAngle = itemCircleAngle + itemAngle * (0);
        double maxAngle = itemCircleAngle + itemAngle * (mItemCount - 1);

        XYCoordinateSet maxXYCoordinateSet = getCircleItemXYCoordinateSet(minAngle);
        XYCoordinateSet minXYCoordinateSet = getCircleItemXYCoordinateSet(maxAngle);

        float minyCoordinateSet = minXYCoordinateSet.getYCoordinateSet();
        float mayCoordinateSet = maxXYCoordinateSet.getYCoordinateSet();

//        Log.e("wjq", "minyCoordinateSet = " + minyCoordinateSet + "," + "mayCoordinateSet = " + mayCoordinateSet);


        standardYCoordinateSet.setMinYCoordinateSet(minyCoordinateSet);
        standardYCoordinateSet.setMaxYCoordinateSet(mayCoordinateSet);


        return standardYCoordinateSet;
    }

    /**
     * 判断点是否在判定标准内
     *
     * @param xyCoordinateSet
     * @return
     */
    private boolean isInStandardYCoordinateSet(XYCoordinateSet xyCoordinateSet) {
        XYCoordinateSet standardCoordinateSet = getStandardYCoordinateSet();

//        Log.e("wjq", "min = " + standardCoordinateSet.getMinYCoordinateSet() + "max = " + standardCoordinateSet.getMaxYCoordinateSet());
//        Log.e("wjq","yy = " + xyCoordinateSet.getYCoordinateSet());
        if (xyCoordinateSet.getXCoordinateSet() - mCircleX > 0) {
            if (xyCoordinateSet.getYCoordinateSet() > standardCoordinateSet.getMinYCoordinateSet() && xyCoordinateSet.getYCoordinateSet() <= standardCoordinateSet.getMaxYCoordinateSet()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 自动滚动的任务
     */
    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                reviseTrail();
                return;
            } else {
                isFling = true;
                // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
                mOffsetAngle += (angelPerSecond / 20);
                // 逐渐减小这个值
                angelPerSecond /= 1.0666F;
                postDelayed(this, 30);
                invalidate();
            }
        }
    }


    private class AutoReviseRunnable implements Runnable {
        private double i;
        private double sum;
        private double angelPerSecond;


        public AutoReviseRunnable(double velocity) {
            this.angelPerSecond = velocity;
//            Log.e("wjq", "angelPerSecond = " + angelPerSecond);
            i = angelPerSecond > 0 ? 1 : -1;
        }

        public void run() {

            if (Math.abs(i) >= Math.abs(angelPerSecond)) {
//                Log.e("wjq", "final i = " + i);
                double reviseAngle = angelPerSecond - sum;
                mOffsetAngle += reviseAngle;
                invalidate();
                return;
            } else {
//                Log.e("wjq", "i = " + i);
                i = i * 1.4666F;
                sum += i / 15;
                mOffsetAngle += i / 15;
                postDelayed(this, 30);
                invalidate();
            }

        }
    }


}

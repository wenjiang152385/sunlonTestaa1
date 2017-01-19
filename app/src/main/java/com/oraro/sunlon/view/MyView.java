package com.oraro.sunlon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oraro.sunlon.Interface.MyViewCallBack;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.BaseParams;
import com.oraro.sunlon.vo.Mark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyView extends ViewGroup {

    private DisplayMetrics mOutMetrics;

    private int markWith = 50;
    private int markHeight = 80;

    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private List<TextView> textViews=new ArrayList<TextView>();
    private Mark[] markArray;
    private Map<Integer, BaseParams> paramsMap = new HashMap<Integer, BaseParams>();
    private int[][] params = {{-73, 19},
            {-36, 65},
            {-26, 129},
            {-26, -19},
            {35, -120},
            {142, 80},
            {45, 138},
            {36, -90},
            {94, -111},
            {63, 9}};

    /**
     * 屏幕长宽对象
     */
    private BaseParams mScreenParams;

    /**
     * Fragment布局原点坐标对象
     */
    private BaseParams mParams;

    private MyViewCallBack myViewCallBack;


    private int mCircle = 10;

    public MyView(Context context) {
        super(context);
        /**
         * ViewGroup它本身并没有任何可画的东西，它是一个透明的控件，因些并不会触发onDraw()
         * 要设置背景
         */
        setWillNotDraw(false);
    }

    public MyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    public void setCallBack(MyViewCallBack callBack) {
        myViewCallBack = callBack;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLines(canvas);
        drawCircles(canvas);
    }

    private void drawCircles(Canvas canvas) {
        Paint paint = null;
        if (0 != paramsMap.size()) {
            Log.e("hh","paramsMap="+paramsMap);
            for (int i = 0; i < paramsMap.size(); i++) {
                BaseParams baseParams = paramsMap.get(i);
                if (i >= 6 && i <= 8) {
                    paint = paint(false,false);
                }else {
                    paint = paint(true,false);
                }
                Log.e("sss","baseParams.getWidth()111="+baseParams.getWidth());
                canvas.drawCircle(baseParams.getWidth(),
                        baseParams.getHeight(), mCircle, paint);
            }
        }
    }

    private void drawLines(Canvas canvas) {
        Paint paint = null;
        if (0 != paramsMap.size()) {
            for (int i = 0; i < paramsMap.size(); i++) {
                BaseParams baseParams = paramsMap.get(i);
                if (i >= 6 && i <= 8) {
                    paint = paint(false,true);
                }else {
                    paint = paint(true,true);
                }
               // Log.e("sss","baseParams.getWidth()+++=="+baseParams.getWidth()+ params[i][0]);
                //Log.e("sss","baseParams.getWidth()=="+baseParams.getWidth());

                    canvas.drawLine(baseParams.getWidth() + params[i][0]* mScreenParams.getWidth()/1000,
                            baseParams.getHeight() + params[i][1]*mScreenParams.getHeight()/1000,
                            baseParams.getWidth(),
                            baseParams.getHeight(),
                            paint);
                }
        }
    }


    private Paint paint(boolean flag1, boolean flag2) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        if (flag2) {
            paint.setStrokeWidth(2);
        }
        String color = flag1 ? "#ff4800" : "#0b9c00";
        Paint.Style style = flag2 ? Paint.Style.STROKE : Paint.Style.FILL;
        paint.setColor(Color.parseColor(color));
        paint.setStyle(style);
        return paint;
    }

    public void addMarks(Map<String, Mark> map) {
        removeAllViews();
        markArray=null;
        markArray = new Mark[map.size()];
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        Set<String> Set = map.keySet();

        Iterator<String> iterator = Set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Mark mark = map.get(key);
            markArray[mark.getType()] = mark;
            //Log.e("45","mark.getType=="+mark.getType());
        }

        for (int i = 0; i < markArray.length; i++) {
          Log.e("55","type = " + markArray[i]);
            LinearLayout markLayout = (LinearLayout) mInflater.inflate(R.layout.image_item, this, false);
            ImageView viewImg = (ImageView) markLayout.findViewById(R.id.markview);
            TextView textView= (TextView) markLayout.findViewById(R.id.marktext);
            ImageView imageView = (ImageView) markLayout.findViewById(R.id.markpoints);

            switch (markArray[i].getType()) {
              //  Log.e("eee","type=="+markArray[i].getType());
                //黑六猪
                case 0:
                    viewImg.setImageResource(R.mipmap.heiliuzhu);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;
                //黑六猪抢机
                case 14:
                    viewImg.setImageResource(R.mipmap.heiliuzhuqiangji);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;
                //1.大雁基地
                case 1:
                    viewImg.setImageResource(R.mipmap.dayan);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;
                //2.大雁基地
                case 15:
                    viewImg.setImageResource(R.mipmap.dayan);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;
                //3.大雁基地
//                case 16:
//                    viewImg.setImageResource(R.mipmap.dayan);
//                    imageView.setImageResource(R.mipmap.lansedatouzhen);
//                    break;
                //绿色有机水稻
                case 2:

                    viewImg.setImageResource(R.mipmap.lvseyoujishuidaoshifanjidi);
                    imageView.setImageResource(R.mipmap.lvsedatouzhen);

                    break;
                //催芽
                case 3:

                    viewImg.setImageResource(R.mipmap.cuiyajidi);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);

                    break;
                //玉米水稻
                case 4:
                    viewImg.setImageResource(R.mipmap.yumijidi);
                    imageView.setImageResource(R.mipmap.lvsedatouzhen);


                    break;

                //催芽
                case 5:
                    viewImg.setImageResource(R.mipmap.cuiyajidi2);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);
                    break;
                //水稻科技示范区
                case 6:
                    viewImg.setImageResource(R.mipmap.shuidaoshifantian);
                    imageView.setImageResource(R.mipmap.lvsedatouzhen);

                    break;
                //京双
                case 7:
                    viewImg.setImageResource(R.mipmap.jingshuanglianghkukou);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);


                    break;
                //京双
                case 8:
                    viewImg.setImageResource(R.mipmap.jingshuangliangku);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);

                    break;


                //生产间
                case 9:
                    viewImg.setImageResource(R.mipmap.shengchanjian);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);

                    break;

                //生产间
                case 10:
                    viewImg.setImageResource(R.mipmap.miyechejian);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);
                    break;

                //生产间
                case 11:
                    viewImg.setImageResource(R.mipmap.cangchuqu);
                    imageView.setImageResource(R.mipmap.hongsedatouzhen);
                    break;

                //优质高产水稻
                case 12:
                    viewImg.setImageResource(R.mipmap.youzhigaochanshuidaoshifanjidi);
                    imageView.setImageResource(R.mipmap.lvsedatouzhen);
                    break;
                case 16:
                    viewImg.setImageResource(R.mipmap.youzhigaochanshuidaoshifanjidi);
                    imageView.setImageResource(R.mipmap.lvsedatouzhen);
                    break;

                //黑山羊
                case 13:
                    viewImg.setImageResource(R.mipmap.heishanyang);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;

                default:
                    viewImg.setImageResource(R.mipmap.ic_launcher);
                    imageView.setImageResource(R.mipmap.lansedatouzhen);
                    break;

            }
            textViews.add(textView);
            imageViews.add(viewImg);
            addView(markLayout);
            requestLayout();
            invalidate();
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = mScreenParams.getWidth();
        int height = mScreenParams.getHeight();
        Log.e("aaa","width=="+width+"height=="+height);
        markWith = (int) (width * 0.026);
        markHeight = (int) (height * 0.074);
        mCircle = (int) (width * 0.004);
        final int childCount = getChildCount();
        int originalX = 0;
        int originalY = 0;

        if (null == mParams) {
            originalX = 0;
            originalY = 0;
        } else {
//          坐标原点
            originalX = mParams.getWidth();
            originalY = mParams.getHeight();
        }

        for (int i = 0; i < childCount; i++) {
            int x = 0;
            int y = 0;
            View child = getChildAt(i);
            final int index = i;
            final View markPoints = child.findViewById(R.id.markpoints);

            int markX = (int) ((markArray[i].getxValue() / 1000) * width);
            Log.e("ss","markArray[i].getxValue()=="+markArray[0].getxValue());
            int markY = (int) ((markArray[i].getyValue() / 1000) * height);
            x = originalX + markX;
            y = originalY + markY;
            if (i == 9) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[0][0] * width  / 1000 ;
                y = y + params[0][1] * height / 1000 ;
                paramsMap.put(0, baseParams);
            } else if (i == 10) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[1][0]* width  / 1000;
                y = y + params[1][1]* height / 1000;
                paramsMap.put(1, baseParams);
            } else if (i == 11) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[2][0]* width  / 1000;
                y = y + params[2][1]* height / 1000;
                paramsMap.put(2, baseParams);
            } else if (i == 7) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[3][0]* width  / 1000;
                y = y + params[3][1]* height / 1000;
                paramsMap.put(3, baseParams);

            } else if (i == 8) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[4][0]* width  / 1000;
                y = y + params[4][1]* height / 1000;
                paramsMap.put(4, baseParams);
            } else if (i == 3) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[5][0]* width  / 1000;
                y = y + params[5][1]* height / 1000;
                paramsMap.put(5, baseParams);
            } else if (i == 6) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[6][0]* width  / 1000;
                y = y + params[6][1]* height / 1000;
                paramsMap.put(6, baseParams);
            } else if (i == 4) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[7][0]* width  / 1000;
                y = y + params[7][1]* height / 1000;
                paramsMap.put(7, baseParams);
            } else if (i == 2) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[8][0]* width  / 1000;
                y = y + params[8][1]* height / 1000;
                paramsMap.put(8, baseParams);
            } else if (i == 5) {
                BaseParams baseParams = new BaseParams();
                baseParams.setWidth(x);
                baseParams.setHeight(y + markHeight);
                x = x + params[9][0]* width  / 1000;
                y = y + params[9][1]* height / 1000;
                paramsMap.put(9, baseParams);
            }
            /**
             * mark布局定位
             */
            child.layout(x - markWith * 4,
                    (int) (y - markHeight * 2.7),
                    x + markWith * 4,
                    y + markHeight);
//            child.setBackgroundColor(Color.GRAY);
            /**
             * mark点定位
             */
            markPoints.layout(markWith * 4 - markWith / 2,
                    (int) (markHeight * 2.7),
                    markWith * 4 + markWith / 2,
                    (int) (markHeight * 3.7));
            /**
             * 弹出缩略图定位
             */
            final View markView = child.findViewById(R.id.markview);
           final  TextView markText= (TextView) child.findViewById(R.id.marktext);
            markPoints.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (markArray[index].getIp() == null && !markArray[index].getId().startsWith("X")) {
                                 myViewCallBack.callShowMark();
                    } else {
                        hiddenALLImageViews();
                        markText.layout(0, (int) (markHeight * 0.2), markWith * 8, (int) (markHeight * 0.8));
                        markView.layout(0, (int) (markHeight * 0.8), markWith * 8, (int) (markHeight * 2.7));
                        markView.setVisibility(View.VISIBLE);
                        markText.setVisibility(VISIBLE);
                        Log.e("11wjq", markArray[index].toString());

                        if (index==2){
                            markText.setText("绿色有机水稻");
                        }
//                        else  if (index==2){
//                            markText.setText("绿色有机水稻");
//                        }
                        else  if (index==12){
                            markText.setText("优质高产水稻1");
                        }else  if (index==16){
                            markText.setText("优质高产水稻2");
                        }
                        else  if (index==13){
                            markText.setText("黑山羊");
                        }else if (index==3){
                            markText.setText("绿色有机水稻枪机");
                        }
//                        else if(index==15){
//                            markText.setText("大雁基地2");
//                       }
// else if (index==16){
//                            markText.setText("大雁基地3");
//                        }
                        else {
                            markText.setText(markArray[index].getTitle()+"");
                        }
                    }
                }
            });
            markView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    myViewCallBack.callBack(markArray[index]);
                    Log.e("wjq", markArray[index].toString());
                }
            });
        }

    }
    public void hiddenALLImageViews() {
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setVisibility(View.GONE);
            textViews.get(i).setVisibility(GONE);
        }
    }
    private BaseParams getcoordinateOrigin() {
        BaseParams coordinateOrigin = new BaseParams();
        int x = mScreenParams.getWidth();
        int y = mScreenParams.getHeight();
        coordinateOrigin.setWidth(x);
        coordinateOrigin.setHeight(y);
        return coordinateOrigin;
    }
        //
    public void getDisplayMetrics(DisplayMetrics outMetrics) {
        mOutMetrics = outMetrics;
        mScreenParams = new BaseParams();
        mScreenParams.setWidth(mOutMetrics.widthPixels);
        mScreenParams.setHeight(mOutMetrics.heightPixels);
    }


    private void starActivityOrShowDialog() {

    }


}

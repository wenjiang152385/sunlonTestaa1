package com.oraro.sunlon.fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import com.oraro.sunlon.sunlontesta.R;

import java.io.InputStream;


public class MonitorFragment extends Fragment implements View.OnClickListener {


    private FrameLayout controlbtn;
    private ImageView img_Picture;
    private VideoView mvideo;


    private int status = -1;
    private final int STATUS_PLAY = 1;
    private final int STATUS_PAUSE = 0;
    private final int STATUS_RESTART = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_video, container, false);
        mvideo = (VideoView) view.findViewById(R.id.videoView);
        img_Picture = (ImageView) view.findViewById(R.id.picture);
        controlbtn = (FrameLayout) view.findViewById(R.id.controlbtn);
        controlbtn.setOnClickListener(this);
        img_Picture.setOnClickListener(this);

        Uri url = urlSite();
        img_Picture.setImageBitmap(readBitmap(getActivity(), R.raw.introduce));
        mvideo.setVisibility(View.INVISIBLE);
        mvideo.setVideoURI(url);

        status = STATUS_PAUSE;
        mvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                img_Picture.setVisibility(View.GONE);
                controlbtn.setVisibility(View.GONE);

            }
        });

       mvideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {
               status = STATUS_RESTART;
               controlbtn.setVisibility(View.VISIBLE);
               controlbtn.setBackgroundResource(R.drawable.restart);

           }
       });
        mvideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (status == STATUS_PLAY) {
                            mvideo.pause();
                            status = STATUS_PAUSE;
                            controlbtn.setVisibility(View.VISIBLE);
                            controlbtn.setBackgroundResource(R.drawable.play);
                        } else if (status == STATUS_PAUSE) {
                            mvideo.start();
                            status = STATUS_PLAY;
                            controlbtn.setVisibility(View.VISIBLE);
                            controlbtn.setBackgroundResource(R.drawable.pause);
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                controlbtn.setVisibility(View.GONE);
                            }
                        }, 1000 * 2);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;

                    default:
                        break;

                }

                return false;
            }
        });


        return view;
    }

    /**
     * @return 播发视频地址
     */
    private Uri urlSite() {
        String uri = "android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.video;

        Uri url = Uri.parse(uri);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//          mvideo .setLayoutParams(layoutParams);
//           mvideo.start();
        return url;
    }


    /**
     *
     * @param url 视频播放链接
     * @return bitmap
     */
    private Bitmap getBitmapAtTime(Uri url) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getActivity(), url);
        Bitmap bitmap = mmr.getFrameAtTime();
        return bitmap;
    }

    private Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //  获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.picture:
            case R.id.controlbtn:
                if (status == STATUS_PAUSE) {
                    mvideo.setVisibility(View.VISIBLE);
                    mvideo.start();
                    status = STATUS_PLAY;
                    controlbtn.setBackgroundResource(R.drawable.pause);

                } else if (status == STATUS_PLAY) {

                    mvideo.setVisibility(View.VISIBLE);
                    mvideo.pause();
                    status = STATUS_PAUSE;
                    controlbtn.setBackgroundResource(R.drawable.play);
                }else if (status == STATUS_RESTART) {
                    mvideo.setVisibility(View.VISIBLE);
                    mvideo.start();
                    status = STATUS_PLAY;
                    controlbtn.setVisibility(View.GONE);
                }

                break;

            default:
                break;
        }
    }


}

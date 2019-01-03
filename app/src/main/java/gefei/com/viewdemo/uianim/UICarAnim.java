package gefei.com.viewdemo.uianim;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mls.baseProject.constant.UserPre;
import com.mls.baseProject.ui.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;

/**
 * 小车左摇臂动画
 */
public class UICarAnim extends BaseActivity {


    @BindView(R.id.iv_gif)
    ImageView mIvGif;
    @BindView(R.id.iv_tran_anim)
    ImageView mIvTranAnim;
    private CountDownTimer timer;
    private int move = 100;

    @Override
    protected void initView() {
        setContentView(R.layout.ui_car_anim);
        ButterKnife.bind(this);
        initTitle("小车平移动画、加载gif");
    }

    @Override
    protected void initData(Bundle bundle) {
        setGif();
        setTranAnim("http://img10.360buyimg.com/n0/jfs/t2329/73/1394698502/152106/57618f9d/5659439fN1e22f440.jpg");

    }

    public void setGif() {
        Glide.with(this)
//                    .load("")
                    .load("http://img.zcool.cn/community/017dad58c26484a801219c773df744.gif")
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mIvGif);
    }

    public void setTranAnim(String url) {
        Glide.with(this).load(url).into(mIvTranAnim);
    }


    @OnClick(R.id.iv_tran_anim)
    public void onViewClicked() {

//        startCountDownTime(5);


        //每隔countDownInterval秒会回调一次onTick()方法
        int screenWidth = UICarAnim.this.getWindowManager().getDefaultDisplay().getWidth();
        final int mid = screenWidth / 2;
        final int width = mIvTranAnim.getWidth();
        TranslateAnimation animation = new TranslateAnimation(0, mid - width / 2, 0, 0);
        animation.setDuration(5000);
//        animation.setRepeatCount(3);
//        animation.setRepeatMode(Animation.REVERSE);
        mIvTranAnim.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.cancel();
                mIvTranAnim.setLeft(mid - width / 2);
                mIvTranAnim.setRight(mid + width / 2);
                //强制把刷新的时间延长到1000s之后，这样就不会闪屏了
                mIvTranAnim.postInvalidateDelayed(1000*1000);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void startCountDownTime(final long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 需要注意的是：如果更新UI，界面消失的时候需要cancel，否则会异常
         * 有onTick，onFinsh、cancel和start方法
         */

        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法

                Logger.e("onTick" + move);
                TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 0);
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(950);
//        animation.setRepeatCount(3);
//        animation.setRepeatMode(Animation.REVERSE);
                mIvTranAnim.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
//                        int screenWidth = UICarAnim.this.getWindowManager().getDefaultDisplay().getWidth();
//                        int mid = screenWidth / 2;
                        move = move + 100;
                        int width = mIvTranAnim.getWidth();
                        mIvTranAnim.setLeft(move - width);
                        mIvTranAnim.setRight(move);
                        Logger.e("onAnimationEnd" + move);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onFinish() {
//
            }
        };
        timer.start();// 开始计时
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel(); // 取消

        }
    }

}

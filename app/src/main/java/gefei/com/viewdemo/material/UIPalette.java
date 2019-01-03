package gefei.com.viewdemo.material;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;

/**
 * 调色板，可以通过获取图片的颜色来设置文字颜色
 */
public class UIPalette extends BaseActivity {


    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected void initView() {
        setContentView(R.layout.ui_palette);
        ButterKnife.bind(this);
        initTitle("UIPalette");
    }

    @Override
    protected void initData(Bundle bundle) {
        Bitmap bmp = getBitmap(UIPalette.this, R.drawable.ic_launcher);
        Palette.generateAsync(bmp, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if(null != swatch) {
                    mIv.setBackgroundColor(swatch.getRgb());
                    mTv.setTextColor(swatch.getTitleTextColor());
                }
            }
        });

    }
    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }


}

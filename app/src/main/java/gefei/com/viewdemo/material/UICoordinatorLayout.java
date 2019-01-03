package gefei.com.viewdemo.material;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.githang.statusbar.StatusBarCompat;
import com.mls.baseProject.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.adapter.RecyclerViewAdapter;

/**
 * 折叠的toolBar
 */
public class UICoordinatorLayout extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.coordinator_recycler)
    RecyclerView mCoordinatorRecycler;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.coordinator_root)
    CoordinatorLayout mCoordinatorRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_coordinator_layout);
        ButterKnife.bind(this);
        mCollapsingLayout.setTitle("coordinatorLayout");
        mCollapsingLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryIcons));
        setSupportActionBar(mToolbar);
        mCoordinatorRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCoordinatorRecycler.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            res.add("item content :" + i);
        }
        mCoordinatorRecycler.setAdapter(new RecyclerViewAdapter(res).setOnItemClickListener(this));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置有返回键

    }


    @Override
    public void onItemClick(View view, int position) {
        Snackbar.make(mCoordinatorRecycler, position + ":click", Snackbar.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAppbar.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAppbar.removeOnOffsetChangedListener(this);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mRefreshLayout.setEnabled(verticalOffset == 0);
        //根据滑动距离来设置透明度
        int newalpha = 255 + verticalOffset;
        newalpha = newalpha < 0 ? 0 : newalpha;
        mBackdrop.setAlpha(newalpha);
    }
    //toolBar左上角返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.zzx.spanzy.mymaterialdesign;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzx.spanzy.mymaterialdesign.adapter.AnimalAdapter;
import com.zzx.spanzy.mymaterialdesign.entity.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Animal[] animals = {
            new Animal("bull",R.drawable.bull),
            new Animal("chick",R.drawable.chick),
            new Animal("crab",R.drawable.crab),
            new Animal("fox",R.drawable.fox),
            new Animal("hedgehog",R.drawable.hedgehog),
            new Animal("hippopotamus",R.drawable.hippopotamus),
            new Animal("koala",R.drawable.koala),
            new Animal("lemur",R.drawable.lemur),
            new Animal("pig",R.drawable.pig),
            new Animal("tiger",R.drawable.tiger),
            new Animal("whale",R.drawable.whale),
            new Animal("zebra",R.drawable.zebra)
    };

    private List<Animal> animalList = new ArrayList<>();
    private AnimalAdapter adapter ;
    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);//注意是v7包中的toolbar
        setSupportActionBar(toolbar);//将toolbar设置到actionbar的位置

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
        actionBar.setTitle("Animals");
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

        initAnimals();
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AnimalAdapter(animalList);
        recyclerView.setAdapter(adapter);


//        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);


        smartRefreshLayout = findViewById(R.id.smart_refresh);
        smartRefreshLayout.setEnableAutoLoadMore(false);

        //禁止越界拖动（1.0.4以上版本）
        smartRefreshLayout.setEnableOverScrollDrag(false);
        //关闭越界回弹功能
//        smartRefreshLayout.setEnableOverScrollBounce(false);
//        //设置 Header 为 贝塞尔雷达 样式
//        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        //设置 Footer 为 球脉冲 样式
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        ClassicsFooter classicsFooter = new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale);
        smartRefreshLayout.setRefreshFooter(classicsFooter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//                refreshLayout.autoLoadMore();
                addData();
                adapter.notifyDataSetChanged();
                Log.i("test","animalList.size()%10="+(animalList.size()%10));
                if ((animalList.size()%10) == 0){
                    Log.i("test","more");
//                    refreshLayout.finishLoadMore(500, true,false);
                    refreshLayout.finishLoadMore();
                }else {
                    Log.i("test","nomore");
//                    refreshLayout.finishLoadMoreWithNoMoreData();
                    refreshLayout.finishLoadMore(500, true,true);
                }


            }
        });
    }



//    private void refreshAnimals() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(500);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        initAnimals();
//                        adapter.notifyDataSetChanged();
//                        smartRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        }).start();
//    }

    /**
     * 初始化数据
     */
    private void initAnimals() {
        animalList.clear();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int index = random.nextInt(animals.length);
            animalList.add(animals[index]);
        }

    }
    public void addData(){
        if (animalList.size()>=50){
            for (int i = 0; i < 3; i++) {
                Random random = new Random();
                int index = random.nextInt(animals.length);
                animalList.add(animals[index]);
            }
            return;
        }
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int index = random.nextInt(animals.length);
            animalList.add(animals[index]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.backup:
                Toast.makeText(this,"you clicked "+item.getTitle(),Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.delete:
                Toast.makeText(this,"you clicked "+item.getTitle(),Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.settings:
                Toast.makeText(this,"you clicked "+item.getTitle(),Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return true;
    }
}

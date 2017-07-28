package com.example.project.limolive.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.activity.MainActivity;
import com.example.project.limolive.presenter.WellComeHelper;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 欢迎页by hwj in 2016/08/31
 */
public class WelcomeActivity extends Activity {

    private WellComeHelper helper;
    private ViewPager mViewpager;
    private ArrayList<Integer> imageList;
    private IvAdapter ivAdapter;
    private LinearLayout layout;
//    private LocationClient mLocationClient = null;
//    private BDLocationListener myListener = new MyLocationListener();
//    private SPUtil sp;
//    private Double lon, lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        mViewpager=findViewById(R.id.vp_Welcome_images);
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        sp = SPUtil.getInstance(this);
//        initLocation();
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        mLocationClient.start();
//        requestPermission();
      //  PgyCrashManager.register(this);
      //  PgyUpdateManager.register(this,getString(R.string.file_provider));
      //  checkUp();


        helper = new WellComeHelper(this);
        if(helper.isLogin()){
            mViewpager.setBackground(getDrawable(R.drawable.welcome_bg));
        }else {
            initImage();
            initDate();
        }


     //   helper.startAlpha(findViewById(R.id.ll_root_layout));
        helper.intoNext();
    }

    private void initDate() {
        ivAdapter=new IvAdapter(this,imageList);

        mViewpager.setAdapter(ivAdapter);

    }

    private void initImage() {
        imageList=new ArrayList<>();
        imageList.add(R.drawable.item1);
        imageList.add(R.drawable.item2);
        imageList.add(R.drawable.item3);
    }


    class IvAdapter extends PagerAdapter {
        Context context;
        ArrayList<Integer> goodsList;

        public IvAdapter(Context context, ArrayList<Integer> goodsList) {
            this.context = context;
            this.goodsList = goodsList;
        }

        @Override
        public int getCount() {
            return goodsList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(goodsList.get(position));
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv= (ImageView) object;
            container.removeView(iv);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  PgyUpdateManager.unregister();
      //  PgyCrashManager.unregister();
    }

    private void checkUp(){
        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(WelcomeActivity.this,getString(R.string.file_provider),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        Log.i("主人有新的版本更新哟",result.toString());
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setTitle("更新")
                                .setMessage("主人有新的版本更新哟...")
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                String url;
                                                JSONObject jsonData;
                                                try {
                                                    jsonData = new JSONObject(
                                                            result);
                                                    if ("0".equals(jsonData
                                                            .getString("code"))) {
                                                        JSONObject jsonObject = jsonData
                                                                .getJSONObject("data");
                                                        url = jsonObject
                                                                .getString("downloadURL");

                                                        startDownloadTask(
                                                                WelcomeActivity.this,
                                                                url);
                                                    }

                                                } catch (JSONException e) {
                                                    // TODO Auto-generated
                                                    // catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        //  ToastUtils.showCustom(MainActivity.this, "已经是最新版本", Toast.LENGTH_SHORT);
                    }
                });
    }
}

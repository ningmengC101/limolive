package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.CommitOrdersAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.order.CommitOrdersBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.views.PayReadyActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.zhifubao.zhifuPayActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 提交订单Activity
 *
 * @author ZL on 2017/2/24
 */

public class CommitOrdersActivity extends BaseActivity implements View.OnClickListener {

    private ListView commit_listView;
    private TextView name, phone, address, price;
    private RelativeLayout commit_order_rl;
    private LinearLayout commit_order_ll;
    private List<CommitOrdersBean.CartList> list;
    private List<CommitOrdersBean.CartList.Datas> dateList;
    private CommitOrdersAdapter adapter;
    private CommitOrdersBean c;
    private String ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_order);

        initView();
    }
    private void initView() {
        loadTitle();
        list = new ArrayList<>();
        dateList=new ArrayList<>();
        ids = getIntent().getStringExtra("ids");
        getDatas();
        commit_listView = (ListView) findViewById(R.id.commit_listView);
        name = (TextView) findViewById(R.id.commit_order_name);
        phone = (TextView) findViewById(R.id.commit_order_phone);
        address = (TextView) findViewById(R.id.commit_order_address);
        price = (TextView) findViewById(R.id.commit_order_price);
        commit_order_rl = (RelativeLayout) findViewById(R.id.commit_order_rl);
        commit_order_ll = (LinearLayout) findViewById(R.id.commit_order_ll);
        adapter = new CommitOrdersAdapter(this, list,dateList);
        commit_listView.setAdapter(adapter);
        commit_order_rl.setOnClickListener(this);
        commit_order_ll.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadTitle() {
        setTitleString("结算中心");
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDatas() {
        /**
         * 获取订单详情
         */
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Log.i("main","getIntent().getStringExtra(\"ids\")="+getIntent().getStringExtra("ids"));
        Api.commitCart(LoginManager.getInstance().getUserID(this),ids, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Log.i("main", "commitCart.." + apiResponse.toString());
                    c = JSON.parseObject(apiResponse.getData(), CommitOrdersBean.class);
                    list.clear();
                    list.addAll(c.getCartList());
                    Log.i("main", "list.." + list.size());
                    dateList.addAll(list.get(0).getData());
                    name.setText(c.getAddressList().getConsignee());
                    phone.setText(c.getAddressList().getMobile());
                    address.setText("收货地址 : " + c.getAddressList().getAddress());
                    price.setText("￥" + c.getTotalPrice());
                    adapter.notifyDataSetChanged();
                } else {
                    startActivity(new Intent(CommitOrdersActivity.this, ChangeInfoActivity.class).putExtra(ChangeInfoActivity.INFO_TYPE, ChangeInfoActivity.ADDRESS));
                    ToastUtils.showShort(CommitOrdersActivity.this, apiResponse.getMessage());
                    finish();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                ToastUtils.showShort(CommitOrdersActivity.this, errMessage);
            }
        });

//        Api.getGoodsInf(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"),getIntent().getIntExtra("num",0) ,new ApiResponseHandler(this) {
//            @Override
//            public void onSuccess(ApiResponse apiResponse) {
//                if (apiResponse.getCode() == Api.SUCCESS) {
//                    CommitOrderBean c = JSON.parseObject(apiResponse.getData(),CommitOrderBean.class);
//                    list.clear();
//                    list.add(c);
//                    name.setText(c.getAddressList().getConsignee());
//                    phone.setText(c.getAddressList().getMobile());
//                    address.setText("收货地址 : "+c.getAddressList().getAddress());
//                    price.setText("￥"+c.getTotalPrice());
//                    adapter.notifyDataSetChanged();
//                } else {
//                    ToastUtils.showShort(CommitOrdersActivity.this, apiResponse.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(String errMessage) {
//                ToastUtils.showShort(CommitOrdersActivity.this, errMessage);
//                super.onFailure(errMessage);
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_order_rl:
                Intent intent = new Intent(this, ChangeInfoActivity.class);
                intent.putExtra(ChangeInfoActivity.INFO_TYPE, ChangeInfoActivity.ADDRESS);
                startActivity(intent);
                break;
            case R.id.commit_order_ll:
                Intent intent1 = new Intent(this, PayReadyActivity.class);
                intent1.putExtra("type", "3");
                intent1.putExtra("cobs", c);
                intent1.putExtra("beizhus", adapter.getBeizhu());
                intent1.putExtra("ids", ids);
                startActivity(intent1);
                break;
        }
    }
}

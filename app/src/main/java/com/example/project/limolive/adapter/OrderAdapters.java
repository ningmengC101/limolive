package com.example.project.limolive.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.CommitOrderActivity;
import com.example.project.limolive.activity.ProductDescriptionActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.GoodsStandard;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderAdapters extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;
    private OrderBean orderBean;
    private String type="";
    AlertDialog dialog = null;

    public OrderAdapters(Context context, List<OrderBean> list, String type) {
        this.context = context;
        this.list = list;
        this.type=type;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if ("".equals(type)){
            return 0;//全部
        }else if ("WAITPAY".equals(type)){
            return 1;//待发货
        }else if ("WAITSEND".equals(type)){
            return 2;//已完成
        }
       return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1 = null;
        ViewHolder vh2 = null;
        ViewHolder vh3 = null;

        orderBean = list.get(i);
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case 0://全部
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
                    vh1.type=view.findViewById(R.id.tv_type);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    vh1.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh1.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh1);
                    break;
                case 1://待发货
                    vh2 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh2.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh2.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh2.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh2.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh2.price = (TextView) view.findViewById(R.id.price);
                    vh2.type=view.findViewById(R.id.tv_type);
                    vh2.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh2.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh2);
                    break;
                case 2://已完成
                    vh3 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh3.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh3.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh3.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh3.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh3.price = (TextView) view.findViewById(R.id.price);
                    vh3.type=view.findViewById(R.id.tv_type);
                    vh3.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh3.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh3);
                    break;

            }


        } else {
            switch (type) {
                case 0:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 1:
                    vh2 = (ViewHolder) view.getTag();
                    break;
                case 2:
                    vh3 = (ViewHolder) view.getTag();
                    break;

            }

        }

        String str="";
        switch (orderBean.getOrder_status_type()){//1、待发货，2、待支付，3、待收货，4、待评价，5、已取消，6、已完成
            case "1":
                str="待发货";//`
                break;
            case "3":
                str="待收货";
                break;
            case "4":
                str="待评价";
                break;
            case "5":
                str="已取消";
                break;
            case "6":
                str="已完成";//`
                break;
        }
        switch (type){
            case 0://全部
                if (str.equals("待发货")){
                    vh1.tv_evaluate.setText("设置发货");
                    vh1.tv_evaluate.setVisibility(View.VISIBLE);
                    final ViewHolder finalVh = vh1;
                    vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show1(finalVh.tv_evaluate, finalVh.type);
                        }
                    });
                }
//                else if (str.equals("待评价")){
//                    vh1.type.setText(str);
//                    final ViewHolder finalVh5 = vh1;
//                    vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Api.myorder_sell_up(LoginManager.getInstance().getUserID(context), "1", new ApiResponseHandler(context) {
//                                @Override
//                                public void onSuccess(ApiResponse apiResponse) {
//                                    if (apiResponse.getCode()==Api.SUCCESS){
//                                        finalVh5.tv_evaluate.setText("已确认");
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
                else {
                    vh1.tv_evaluate.setVisibility(View.GONE);
                    vh1.type.setText(str);
                }
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                vh1.price.setText("共"+orderBean.getGoods_list().get(0).getGoods_num()+"件  合计￥"+orderBean.getTotal_amount());
                break;

            case 1://待发货
                if (str.equals("待发货")) {
                    vh2.type.setText(str);
                    vh2.tv_evaluate.setText("设置发货");
                    vh2.tv_evaluate.setVisibility(View.VISIBLE);
                    final ViewHolder finalVh = vh1;
                    final ViewHolder finalVh2 = vh3;
                    vh2.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show1(finalVh.tv_evaluate, finalVh2.type);
                        }
                    });
                }

                vh2.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh2.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh2.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 2://已完成
                if (str.equals("已完成")){
                    vh3.type.setText(str);
                    vh3.tv_evaluate.setVisibility(View.GONE);
                }
                vh3.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh3.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh3.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
        }
        return view;
    }
    private EditText et_size,et_num;
    private Button btn_fig;
    private Dialog dialogs;
    private void show1(final TextView tv,final TextView tv1) {
        View view = View.inflate(context, R.layout.dialogs1, null);
        et_size = view.findViewById(R.id.et_size);
        et_num = view.findViewById(R.id.et_num);
        btn_fig = view.findViewById(R.id.btn_fig);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("请输入物流信息")
                .setView(view);
        dialogs = builder.show();
        btn_fig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shipping_code=et_num.getText().toString();
                String  shipping_name=et_size.getText().toString();
                setshipping(shipping_code,shipping_name,tv,tv1);
            }
        });

    }

    private void setshipping(String shipping_code, String shipping_name, final TextView tv,final TextView tv1) {
        if (TextUtils.isEmpty(shipping_code)){
            ToastUtils.showShort(context,"物流单号为空");
            return;
        }
        if (TextUtils.isEmpty(shipping_name)){
            ToastUtils.showShort(context,"物流商家名称为空");
            return;
        }
        Api.myorder_shipping(orderBean.getOrder_id(), shipping_code, shipping_name, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    tv.setText("已发货");
                    tv1.setText("已发货");
                    if (dialogs!=null){
                        dialogs.dismiss();
                    }
                }
            }
        });
    }

//    private void orderReturn(String reason, final TextView textView) {
//        Api.orderReturn(LoginManager.getInstance().getUserID(context)
//                , orderBean.getOrder_id()
//                , orderBean.getOrder_sn()
//                , orderBean.getOrder_sn()
//                , orderBean.getGoods_list().get(0).getGoods_id()
//                , "0"
//                , reason
//                , orderBean.getGoods_list().get(0).getGood_standard_size()
//                , new ApiResponseHandler(context) {
//                    @Override
//                    public void onSuccess(ApiResponse apiResponse) {
//                        int code = apiResponse.getCode();
//                        switch (code){
//                            case 0:
//                                ToastUtils.showShort(context,"退货申请成功");
//                                break;
//                            case -2:
//                                ToastUtils.showShort(context,"退货申请失败");
//                                break;
//                            case -3:
//                                ToastUtils.showShort(context,"已申请过");
//                                break;
//                        }
//                    }
//                });
//    }

    private class ViewHolder {
        private TextView store, desc, count, price,type,tv_evaluate;
        private ImageView iv;
    }
}
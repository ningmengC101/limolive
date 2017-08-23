package com.example.project.limolive.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.order.OrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;
    private OrderBean orderBean;
    private String type="";

    public OrderAdapter(Context context, List<OrderBean> list,String type) {
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
            return 1;//待支付
        }else if ("WAITSEND".equals(type)){
            return 2;//待发货
        }else if ("WAITRECEIVE".equals(type)){
            return 3;//待收货
        }else if ("WAITCCOMMENT".equals(type)){
            return 4;//待评价
        }else if ("FINISH".equals(type)){
            return 5;//售后
        }
       return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1 = null;
        ViewHolder vh2 = null;
        ViewHolder vh3 = null;
        ViewHolder vh4 = null;
        ViewHolder vh5 = null;
        ViewHolder vh6 = null;
        orderBean = list.get(i);
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case 0://全部
                    Log.i("热门商品类型","type="+type);
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
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
                case 1://待支付
                    Log.i("热门商品类型","type="+type);
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
                case 2://待发货
                    Log.i("热门商品类型","type="+type);
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
                case 3://待收货
                    Log.i("热门商品类型","type="+type);
                    vh4 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh4.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh4.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh4.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh4.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh4.price = (TextView) view.findViewById(R.id.price);
                    vh4.type=view.findViewById(R.id.tv_type);
                    vh4.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh4.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh4);
                    break;
                case 4://待评价
                    Log.i("热门商品类型","type="+type);
                    vh5 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh5.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh5.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh5.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh5.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh5.price = (TextView) view.findViewById(R.id.price);
                    vh5.type=view.findViewById(R.id.tv_type);
                    vh5.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh5.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh5);
                    break;
                case 5://售后
                    Log.i("热门商品类型","type="+type);
                    vh6 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh6.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh6.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh6.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh6.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh6.price = (TextView) view.findViewById(R.id.price);
                    vh6.type=(TextView) view.findViewById(R.id.tv_type);
                    vh6.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh6.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh6);
                    break;
            }


        } else {
            switch (type) {
                case 0:
                    Log.i("热门商品类型","type="+type);
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 1:
                    Log.i("热门商品类型","type="+type);
                    vh2 = (ViewHolder) view.getTag();
                    break;
                case 2:
                    Log.i("热门商品类型","type="+type);
                    vh3 = (ViewHolder) view.getTag();
                    break;
                case 3:
                    Log.i("热门商品类型","type="+type);
                    vh4 = (ViewHolder) view.getTag();
                    break;
                case 4:
                    Log.i("热门商品类型","type="+type);
                    vh5 = (ViewHolder) view.getTag();
                case 5:
                    Log.i("热门商品类型","type="+type);
                    vh6 = (ViewHolder) view.getTag();
                    break;

            }

        }

        String str="";
        switch (orderBean.getOrder_status_type()){//1、待发货，2、待支付，3、待收货，4、待评价，5、已取消，6、已完成
            case "1":
                str="待发货";
                break;
            case "2":
                str="待支付";
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
                str="已完成";
                break;
        }
        switch (type){
            case 0://全部
                if (str.equals("待评价")){
                    vh1.tv_evaluate.setText("评价");
                    vh1.tv_evaluate.setVisibility(View.VISIBLE);
                }else if ("已完成".equals(str)){
                    vh1.tv_evaluate.setText("退货申请");
                    vh1.tv_evaluate.setVisibility(View.VISIBLE);
                }else if ("待支付".equals(str)){
                    vh1.tv_evaluate.setText("去支付");
                    vh1.tv_evaluate.setVisibility(View.VISIBLE);
                }else if (str.equals("待发货")){
                vh1.tv_evaluate.setText("查看物流");
                vh1.tv_evaluate.setVisibility(View.VISIBLE);
                }else {
                    vh3.tv_evaluate.setVisibility(View.GONE);
                }
                vh1.type.setText(str);
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                vh1.price.setText("共"+orderBean.getGoods_list().get(0).getGoods_num()+"件  合计￥"+orderBean.getTotal_amount());
                break;
            case 1://待支付
                if (str.equals("待支付")){
                    vh2.tv_evaluate.setText("去支付");
                    vh2.tv_evaluate.setVisibility(View.VISIBLE);
                }
                vh2.type.setText(str);
                vh2.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh2.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh2.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 2://待发货
                if (str.equals("待发货")){
                    vh3.tv_evaluate.setText("查看物流");
                    vh3.tv_evaluate.setVisibility(View.VISIBLE);
                }
                vh3.type.setText(str);
                vh3.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh3.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh3.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 3://待收货
                if (str.equals("待收货")){
                    vh4.tv_evaluate.setText("查看物流");
                    vh4.tv_evaluate.setVisibility(View.VISIBLE);
                }
                vh4.type.setText(str);
                vh4.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh4.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh4.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 4://待评价
                if (str.equals("待评价")){
                    vh5.tv_evaluate.setText("评价");
                    vh5.tv_evaluate.setVisibility(View.VISIBLE);
                }
                vh5.type.setText(str);
                vh5.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh5.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh5.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 5://售后
                vh6.type.setText(str);
                if ("已完成".equals(str)){
                    vh6.tv_evaluate.setText("退货申请");
                    vh6.tv_evaluate.setVisibility(View.VISIBLE);
                }
                vh6.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh6.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh6.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
        }
        return view;
    }

    private class ViewHolder {
        private TextView store, desc, count, price,type,tv_evaluate;
        private ImageView iv;
    }
}

package com.example.project.limolive.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.GoodInfoBean;
import com.example.project.limolive.bean.taowu.GoodsContentBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;

import java.util.List;

/**
 * 作者：hpg on 2017/1/6 13:40
 * 功能：
 */
public class GoodsManageAdapter extends BaseAdapter {
    private Context context;
    //GoodsContentBean
    private List<GoodInfoBean> goodsContentBeans;

    public GoodsManageAdapter(Context context, List<GoodInfoBean> goodsContentBeans) {
        this.context = context;
        this.goodsContentBeans = goodsContentBeans;
        Log.i("商品管理","goodsContentBeans...."+goodsContentBeans.toString());
    }

    @Override
    public int getCount() {
        return goodsContentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsContentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.goodsmg_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods_iicon = (ImageView) convertView.findViewById(R.id.iv_goods_iicon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);//描述
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);//价格
            viewHolder.tv_repertoryNum = (TextView) convertView.findViewById(R.id.tv_repertoryNum);//库存数量
            viewHolder.tv_del = (TextView) convertView.findViewById(R.id.tv_del);//库存数量
            viewHolder.tv_del.setVisibility(View.VISIBLE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (goodsContentBeans.get(position) != null) {
            Log.i("商品管理","goodsContentBeans.get(position)...."+goodsContentBeans.get(position).toString());
            Log.i("商品管理","position...."+position);
            if (null != goodsContentBeans.get(position).getOriginal_img() && !"".equals(goodsContentBeans.get(position).getOriginal_img())
                    ) {
                Glide.with(context).load(ApiHttpClient.API_PIC + goodsContentBeans.get(position).getOriginal_img()).into(viewHolder.iv_goods_iicon);
            } else {
                viewHolder.iv_goods_iicon.setBackgroundResource(R.drawable.button_bg1);
            }
            viewHolder.tv_name.setText(goodsContentBeans.get(position).getGoods_name());
            viewHolder.tv_price.setText(goodsContentBeans.get(position).getShop_price());
            viewHolder.tv_repertoryNum.setText(goodsContentBeans.get(position).getStore_count());
            viewHolder.tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delShopp(goodsContentBeans.get(position));
                }
            });
        }

        return convertView;
    }

    private void delShopp(final GoodInfoBean b) {
        Api.delShopp(LoginManager.getInstance().getUserID(context), b.getGoods_id(), new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (Api.SUCCESS==apiResponse.getCode()){
                    goodsContentBeans.remove(b);
                    notifyDataSetChanged();
                    ToastUtils.showShort(context,"删除成功");
                }else if (apiResponse.getCode()==-1){
                    ToastUtils.showShort(context,"没有此用户");
                }else if (apiResponse.getCode()==-2){
                    ToastUtils.showShort(context,"参数有误");
                }else if (apiResponse.getCode()==-3){
                    ToastUtils.showShort(context,"删除失败");
                }
            }
        });
    }

    private class ViewHolder {
        ImageView iv_goods_iicon;
        TextView tv_name;
        TextView tv_price;
        TextView tv_repertoryNum;
        TextView tv_del;
    }
}

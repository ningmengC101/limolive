package com.example.project.limolive.fragment;

import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.FollowAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FollowLiveBeans;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.home.HomeListBeens;
import com.example.project.limolive.helper.LoginManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.R.id.tl_tab_layouts;

/**
 * Created by AAA on 2017/8/14.
 */

public class FollowFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<HomeListBeens> newLiveList;
    private int page=1;
    private GridLayoutManager gm;
    private FollowAdapter adapter;
    private TextView tv_islive;

    public FollowFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(R.layout.fragment_follow, inflater, container);
        setRecyclerView();
        initListener();
        initDate();
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        newLiveList= new ArrayList<>();



//        for (int i=0;i<10;i++){
//            newLiveList.add(new FollowLiveBeans("123","nick","1212","123","","",true));
//        }
    }

    private void setRecyclerView() {
        tv_islive= (TextView) findViewById(R.id.tv_isLive);
        recyclerView= (RecyclerView) findViewById(R.id.rv_follows);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_DownFollow);
        adapter=new FollowAdapter(newLiveList,getContext());
        gm=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(adapter);
    }

    private void initDate() {
        getData();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                newLiveList.clear();
                getData();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int positon = gm.findLastVisibleItemPosition();
                if (adapter!=null&&positon==adapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE){
                    page++;
                    getData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        Api.FollowDate(LoginManager.getInstance().getUserID(getActivity()),page, new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    String data = apiResponse.getData();
                    Log.i("直播列表","data.toString()="+data.toString());
                    newLiveList.addAll(JSONArray.parseArray(data, HomeListBeens.class));
                    adapter.notifyDataSetChanged();
                }
                if (newLiveList.size()>0){
                    tv_islive.setVisibility(View.GONE);
                }else {
                    tv_islive.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}

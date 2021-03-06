package com.example.project.limolive.tencentlive.views;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.activity.ExchangeActivity;
import com.example.project.limolive.activity.MainActivity;
import com.example.project.limolive.activity.MyWalletActivity;
import com.example.project.limolive.activity.RanksActivity;
import com.example.project.limolive.adapter.LiveRankConsumptionAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.ChipBeatBean;
import com.example.project.limolive.bean.CoinListBean;
import com.example.project.limolive.bean.DiceBean;
import com.example.project.limolive.bean.LiveRechargeBean;
import com.example.project.limolive.bean.PokerBean;
import com.example.project.limolive.bean.RechargeLiveBean;
import com.example.project.limolive.bean.StatusBean;
import com.example.project.limolive.bean.SystemMsgBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.live.LivesInfoBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.pay.wx.WXPayUtils;
import com.example.project.limolive.tencentlive.adapters.MembersHeadAdapter;
import com.example.project.limolive.tencentlive.giftpage.BasePager;
import com.example.project.limolive.tencentlive.giftpage.Page_one;
import com.example.project.limolive.tencentlive.giftpage.Page_two;
import com.example.project.limolive.tencentlive.model.AvMemberInfo;
import com.example.project.limolive.tencentlive.model.GiftVo;
import com.example.project.limolive.tencentlive.utils.SPUtil;
import com.example.project.limolive.tencentlive.views.customviews.GiftShowManager;
import com.example.project.limolive.tencentlive.views.customviews.HorizontalListView;
import com.example.project.limolive.tencentlive.views.customviews.PeriscopeLayout;
import com.example.project.limolive.utils.Constant;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.SoundPlayUtils;
import com.example.project.limolive.view.BlurMaskFilterView;
import com.example.project.limolive.view.MarqueeTextView;
import com.example.project.limolive.view.BabyPopupWindow;
import com.example.project.limolive.tencentlive.adapters.ChatMsgListAdapter;
import com.example.project.limolive.tencentlive.model.ChatEntity;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveInfoJson;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.LiveHelper;
import com.example.project.limolive.tencentlive.presenters.viewinface.LiveListView;
import com.example.project.limolive.tencentlive.presenters.viewinface.LiveView;
import com.example.project.limolive.tencentlive.presenters.viewinface.ProfileView;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.example.project.limolive.tencentlive.views.customviews.InputTextMsgDialog;
import com.example.project.limolive.tencentlive.views.customviews.MembersDialog;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.DiceGameDialog;
import com.example.project.limolive.view.HostInfoPopupWindow;
import com.example.project.limolive.view.ManberInfoPopupWindow;
import com.example.project.limolive.view.PokerGameDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.av.TIMAvManager;
import com.tencent.av.sdk.AVView;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.ilivesdk.view.AVVideoView;
import com.tencent.livesdk.ILVLiveManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * Live直播类
 */
public class LiveingActivity extends BaseActivity implements LiveView, View.OnClickListener, ProfileView, LiveListView {
    private static final String TAG = LiveingActivity.class.getSimpleName();
    private static final int GETPROFILE_JOIN = 0x200;
    private LiveHelper mLiveHelper;
    public static ArrayList<ChatEntity> mArrayListChatEntity;
    public static ArrayList<ChatEntity> mArrayListPresent;
    public static ChatMsgListAdapter mChatMsgListAdapter;
    private static final int MINFRESHINTERVAL = 500;
    private static final int UPDAT_WALL_TIME_TIMER_TASK = 1;
    private static final int TIMEOUT_INVITE = 2;
    private boolean mBoolRefreshLock = false;
    private boolean mBoolNeedRefresh = false;
    private final Timer mTimer = new Timer();
    private ArrayList<ChatEntity> mTmpChatList = new ArrayList<ChatEntity>();//缓冲队列
    private TimerTask mTimerTask = null;
    private static final int REFRESH_LISTVIEW = 5;
    private Dialog mMemberDg, inviteDg;
    private ImageView mHeadIcon;
    private LinearLayout mHostLayout, mHostLeaveLayout;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private long mSecond = 0;
    private String formatTime;
    private Timer mHearBeatTimer, mVideoTimer;
    private VideoTimerTask mVideoTimerTask;//计时器
    private ObjectAnimator mObjAnim;
    private int thumbUp = 0;
    private long admireTime = 0;
    private int watchCount = 0;
    private static boolean mBeatuy = false;
    private static boolean mWhite = true;
    private boolean bCleanMode = false;
    private boolean mProfile;
    private boolean bFirstRender = true;
    private boolean bInAvRoom = false, bSlideUp = false, bDelayQuit = false;
    private LinearLayout mBeautySettings;
    private String backGroundId;
    private SeekBar mBeautyBar;
    private TextView tvMembers;
    private AVRootView mRootView;
    private LinearLayout head_up_layout;
    private Dialog mDetailDialog;
    private DisplayMetrics dm;
    private ArrayList<String> mRenderUserList = new ArrayList<>();
    private List<AvMemberInfo> avMemberInfos;
    private MembersHeadAdapter HeadAdapter;
    public static String GETMENBERINFO = "GETMENBERINFO";
    public static String GETMENBERINFOS = "GETMENBERINFOS";
    public MyReceiver myReceiver;
    public MyReceiver1 myReceiver1;
    public InputMethodManager manager;
    public boolean isStop = false;
    private Dialog dialog1;
    private ArrayList<BasePager> mPagerList;
    private PeriscopeLayout mHeartLayout;
    private RelativeLayout count_layout;
    private ImageView giftcount_layout;
    private TextView live_room_gift_count;
    private TextView live_room_gift_sendname;
    private Button iv_giftGiveing_button;
    private TextView live_room_gift_pay;
    private TextView live_room_gift_money;
    private Dialog presentdialog;
    private RelativeLayout go_phb_Layout;
    private LinearLayout giftCon;//礼物的里列表的外层ViewGroup
    private View viewd;
    private SPUtil sp;
    private int present_num_show = 0;
    private int p_type;
    private String presentMessage;
    public static ImageView gif_donghua;
    public static TextView zaixian_member;
    private static final int PRESENT_MSG = 12;
    private Animation liwu;
    private Animation enter;
    private Animation exit;
    public static GiftShowManager giftManger;
    private TIMConversation mConversation;
    public static final int NIMOBI = 3333;
    public static final int GUANZHU = 4444;
    public static final int UNGUANZHU = 5555;
    public static final int PAIHANG = 6666;
    public static final int GETSYSMSG = 77777;
    public static final int GIFT_STOP_ADMIN = 88888;
    public static final int UNTADE_NUM = 99999;
    private int score = 0;
    ArrayList<SystemMsgBean> sysMsgList=null;

    private int startnmb=0,stopnmb=0;
    private RelativeLayout rl_anim;
    private ImageView iv_ggg;
    private TextView tv_game;
    private TextView tv_gamesd;
    private SoundPlayUtils soundPlayUtils;
    private RelativeLayout rl_nn_choice;
    private RelativeLayout rl_anim_win;
    private LinearLayout ll_play,ll_niuniu,ll_dice;
    private TextView tv_play;
    private ImageView iv_popularity;
    private RelativeLayout rl_tuhaobang,rl_chongzhi,rl_maxGift;
    private TextView marqueeTextView,at_tuhaobang,at_maxGift;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
        setContentView(R.layout.activity_liveing);
        rl_anim= (RelativeLayout) findViewById(R.id.rl_anim);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        checkPermission();
        mLiveHelper = new LiveHelper(this, this, mHandler);
        sp = SPUtil.getInstance(LiveingActivity.this);

        //注册广播接收者
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GETMENBERINFO);
        registerReceiver(myReceiver, filter);

        //注册广播接收者
        myReceiver1 = new MyReceiver1();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(GETMENBERINFOS);
        registerReceiver(myReceiver1, filter1);

        liwu = (Animation) AnimationUtils.loadAnimation(this, R.anim.liwu);

        //   enter = (Animation) AnimationUtils.loadAnimation(this, R.anim.act_open_enter);
        //  exit = (Animation) AnimationUtils.loadAnimation(this, R.anim.act_open_exit);
        initView();
        backGroundId = CurLiveInfo.getHostID();
        //Log.i("LiveingActivity", "backGroundId..." + backGroundId);
        //进入房间流程
        mLiveHelper.startEnterRoom();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mLiveHelper.getSystemMsg();
        isTrue=true;
        //t.start();
        //t1.start();
        soundPlayUtils = SoundPlayUtils.init(LiveingActivity.this);
        live_coinLists();

    }
    private List<LiveRechargeBean> liveRechargeList=new ArrayList<>();
    private void live_recharge_list_de() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.live_recharge_list_de(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("直播排行","直播排行apiResponse="+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        liveRechargeList.clear();
                        liveRechargeList.addAll(JSONArray.parseArray(apiResponse.getData(), LiveRechargeBean.class));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void getSystemMsg(){
        ChatEntity entity=null;
        for (SystemMsgBean msgBean : sysMsgList) {
            entity = new ChatEntity();
            entity.setSenderName(msgBean.getNotice_name());
            entity.setContext(msgBean.getNotice_content());
            entity.setType(Constants.HOST_BACK);
            notifyRefreshListView(entity);
        }
    }

    /**
     * 飘屏动画
     */
    List<ApiResponse> apiList=null;
    private Handler pHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if (apiList==null){
                        apiList=new ArrayList<>();
                    }
                    if (msg.obj!=null){
                        ApiResponse api= (ApiResponse) msg.obj;
                        //Log.i("飘屏动画","添加飘屏元素api="+api.toString());
                        apiList.add(api);
                        //Log.i("飘屏动画","添加飘屏元素apiList.size()="+apiList.size());
                        pHandler.sendEmptyMessageAtTime(1,500);
                    }

                    break;
                case 1:
                    if (apiList!=null&&apiList.size()!=0){
                        //Log.i("飘屏动画",".getVisibility()==View.GONE"+(rl_tuhaobang.getVisibility()==View.GONE)+(rl_chongzhi.getVisibility()==View.GONE)+(rl_maxGift.getVisibility()==View.GONE));
                        if (rl_tuhaobang.getVisibility()==View.GONE&&rl_chongzhi.getVisibility()==View.GONE&&rl_maxGift.getVisibility()==View.GONE){//没在显示则开始显示
                            final ApiResponse remove = apiList.remove(0);
                            //Log.i("飘屏动画","remove.getMessage()="+remove.getMessage());
                            //开始动画效果 动画执行完成后继续执行此方法直到apilist没有数据时结束
                            switch (remove.getCode()){
                                case 3://充值飘屏
                                    //Log.i("飘屏动画","进入充值飘屏remove.getMessage()");
                                    rl_chongzhi.setVisibility(View.VISIBLE);
                                    rl_tuhaobang.setVisibility(View.GONE);
                                    rl_maxGift.setVisibility(View.GONE);

                                    marqueeTextView.setText("  "+remove.getMessage());
                                    Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.admin_live_translate);
                                    rl_chongzhi.startAnimation(animation);
                                    animation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                            rl_chongzhi.clearAnimation();
                                            rl_chongzhi.setVisibility(View.GONE);
                                            rl_tuhaobang.setVisibility(View.GONE);
                                            rl_maxGift.setVisibility(View.GONE);
                                            marqueeTextView.setText("");
                                            at_tuhaobang.setText("");
                                            at_maxGift.setText("");
                                            pHandler.sendEmptyMessageAtTime(1,500);

//                                            Message msg = Message.obtain();
//                                            msg.what=2;
//                                            msg.obj=marqueeTextView;
//                                            pHandler.sendMessageDelayed(msg,2000);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });


                                    break;
                                case 2://大礼物飘屏  柠檬啪
                                    //Log.i("飘屏动画","进入大礼物飘屏remove.getMessage()");
                                    rl_chongzhi.setVisibility(View.GONE);
                                    rl_tuhaobang.setVisibility(View.GONE);
                                    rl_maxGift.setVisibility(View.VISIBLE);

                                    String sendName="";
                                    String hostName="";
                                    String body="";
                                    String message = remove.getMessage();
                                    String data = remove.getData();
                                    if (data!=null){
                                        List<HomeListBeen> homeListBeens = JSONArray.parseArray(data, HomeListBeen.class);
                                        if (homeListBeens!=null&&homeListBeens.size()>0){
                                            final HomeListBeen item = homeListBeens.get(0);
                                            rl_maxGift.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                                                        return;
                                                    }
                                                    View inflate = View.inflate(LiveingActivity.this, R.layout.bg_live_window, null);
                                                    ImageView back =(ImageView) inflate.findViewById(R.id.back);

                                                    TextView tv_go =(TextView) inflate.findViewById(R.id.tv_go);
                                                    final Dialog alertDialog = new AlertDialog.Builder(LiveingActivity.this)
                                                            .setView(inflate)
                                                            .create();
                                                    back.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (alertDialog!=null){
                                                                alertDialog.dismiss();
                                                            }
                                                        }
                                                    });
                                                    tv_go.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (!item.getAvRoomId().equals(CurLiveInfo.getRoomNum())){
                                                                if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {

                                                                }else {
                                                                    mLiveHelper.sendGroupCmd(Constants.AVIMCMD_EXITLIVE, "");
                                                                }
                                                                mLiveHelper.startExitRoom();
                                                                clearOldData();
                                                                LiveingActivity.this.finish();
                                                                Intent intent = new Intent();
                                                                intent.setClass(LiveingActivity.this, MainActivity.class);
                                                                StringBuilder str = new StringBuilder();
                                                                String s = str
                                                                        .append(item.getHost().getUid())
                                                                        .append(",")
                                                                        .append(item.getHost().getUsername())
                                                                        .append(",")
                                                                        .append(item.getHost().getAvatar())
                                                                        .append(",")
                                                                        .append(item.getAvRoomId())
                                                                        .append(",")
                                                                        .append(item.getHost().getPhone())
                                                                        .append(",")
                                                                        .append(item.getWatchCount() + 1)
                                                                        .append(",")
                                                                        .append(item.getAdmireCount())
                                                                        .append(",")
                                                                        .append(item.getLbs().getAddress())
                                                                        .append(",")
                                                                        .append(item.getTitle()).toString();

                                                                intent.putExtra("isIntent",s);
                                                                startActivity(intent);

                                                            }else {
                                                                ToastUtils.showShort(LiveingActivity.this,"已在该直播间");
                                                                if (alertDialog!=null) {
                                                                    alertDialog.dismiss();
                                                                }
                                                            }
                                                        }
                                                    });

                                                    alertDialog.show();

                                                }
                                            });
                                        }

                                    }
                                    if (message.contains("的")){
                                        String[] strs = message.split("的");
                                        if (strs.length>1){
                                            String str = strs[0];
                                            body="的"+strs[1];
                                            if (str.contains("在")){
                                                String[] ss = str.split("在");
                                                sendName=ss[0];
                                                hostName=ss[1];

                                            }
                                        }
                                    }
                                    String z="在";
                                    SpannableString spanString = new SpannableString(sendName+ z + hostName+body);
                                    spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, sendName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    spanString.setSpan(new ForegroundColorSpan(Color.RED), sendName.length()+z.length(), sendName.length()+z.length()+hostName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                    at_maxGift.setText(spanString);
                                    Animation animation1 = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.admin_live_translate);
                                    rl_maxGift.startAnimation(animation1);
                                    animation1.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
//结束滚动
                                            rl_chongzhi.setVisibility(View.GONE);
                                            rl_tuhaobang.setVisibility(View.GONE);
                                            rl_maxGift.setVisibility(View.GONE);
                                            marqueeTextView.setText("");
                                            at_tuhaobang.setText("");
                                            at_maxGift.setText("");
                                            //Log.i("飘屏动画","结束");
                                            pHandler.sendEmptyMessageAtTime(1,500);

//                                            Message msg = Message.obtain();
//                                            msg.what=2;
//                                            msg.obj=at_maxGift;
//                                            pHandler.sendMessageDelayed(msg,2000);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });

                                    break;
                                case 1://消费到指定数额进入直播间漂屏接口
                                    //Log.i("飘屏动画","进入消费到指定数额进入直播间漂屏接口remove.getMessage()");
                                    rl_chongzhi.setVisibility(View.GONE);
                                    rl_tuhaobang.setVisibility(View.VISIBLE);
                                    rl_maxGift.setVisibility(View.GONE);
                                    at_tuhaobang.setText("  "+remove.getMessage());
                                    Animation animation2 = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.admin_live_translate);
                                    rl_tuhaobang.startAnimation(animation2);
                                    animation2.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            rl_tuhaobang.clearAnimation();

                                            rl_chongzhi.setVisibility(View.GONE);
                                            rl_tuhaobang.setVisibility(View.GONE);
                                            rl_maxGift.setVisibility(View.GONE);
                                            marqueeTextView.setText("");
                                            at_tuhaobang.setText("");
                                            at_maxGift.setText("");
                                            pHandler.sendEmptyMessageAtTime(1,500);
//                                            Message msg = Message.obtain();
//                                            msg.what=2;
//                                            msg.obj=at_tuhaobang;
//                                            pHandler.sendMessageDelayed(msg,2000);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    break;
                            }
                            //startAnimtions();
                        }/*else {
                            pHandler.sendEmptyMessageAtTime(1,2000);
                        }*/
                    }
                    break;
                case 2://开始文字滚动
                    if (msg.obj!=null){
                        final MarqueeTextView tv= (MarqueeTextView) msg.obj;
                        tv.setSelected(true);
                        tv.setOnMarqueeCompleteListener(new MarqueeTextView.OnMarqueeCompleteListener() {
                            @Override
                            public void onMarqueeComplete() {
                                tv.startStopMarquee(false);
                                //结束滚动
                                rl_chongzhi.setVisibility(View.GONE);
                                rl_tuhaobang.setVisibility(View.GONE);
                                rl_maxGift.setVisibility(View.GONE);
                                marqueeTextView.setText("");
                                at_tuhaobang.setText("");
                                at_maxGift.setText("");
                                pHandler.sendEmptyMessageAtTime(1,500);

                            }
                        });
                    }

                    break;
            }
            return false;
        }
    });

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GIFT_STOP_ADMIN:
                    //tv_admin.setText("");
                    break;
                case GETSYSMSG://系统消息显示
                    sysMsgList.clear();
                    String json = (String) msg.obj;
                    sysMsgList.addAll(JSON.parseArray(json, SystemMsgBean.class));
                    //Log.i("系统消息","Handler="+sysMsgList.toString());
                    getSystemMsg();
                    break;
                case UPDAT_WALL_TIME_TIMER_TASK:
                    updateWallTime();
                    break;
                case REFRESH_LISTVIEW:
                    doRefreshListView();
                    break;
                case TIMEOUT_INVITE:
                    String id = "" + msg.obj;
                    cancelInviteView(id);
                    mLiveHelper.sendGroupCmd(Constants.AVIMCMD_MULTI_HOST_CANCELINVITE, id);
                    break;
                case 2222://如果是处理显示礼物的消息  自己的钻石
                 /*   String lemon_coin = (String) msg.obj;
                    live_room_gift_money.setText(lemon_coin);*/
                    getSelCoins();
                    break;
                case NIMOBI://如果是处理显示礼物的消息   //主播的魅力值
                    //Log.i("NIMOBI", "NIMOBI" + "走没有");
               /*     //Log.i("NIMOBI","lemon_coins"+lemon_coins);
                    //Log.i("NIMOBI","NIMOBI"+"走没有");
                    String nimengbi = (String) msg.obj;
                    lemon_coins=String.valueOf(Integer.valueOf(lemon_coins)+10);
                    tv_NMB.setText(lemon_coins);*/
                    getHostCoins();
                    break;
                case GUANZHU://同步关注数量
                    //getFollow();
                    break;
                case PAIHANG://更新排行头像
                    groupMemberInfos1();
                    break;
                case UNTADE_NUM://更新人数
                    tvMembers.setText(CurLiveInfo.getMembers()+"在线");
                    break;
            }
            return false;
        }
    });


    /**
     * 时间格式化
     */
    private void updateWallTime() {

        String hs, ms, ss;

        long h, m, s;
        h = mSecond / 3600;
        m = (mSecond % 3600) / 60;
        s = (mSecond % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (hs.equals("00")) {
            formatTime = ms + ":" + ss;
        } else {
            formatTime = hs + "-" + ms + "-" + ss;
        }
    }

    /**
     * 初始化UI
     */
    private TextView BtnBack, baby_btn, BtnSwitch, BtnBeauty, BtnFlash, share_btn, tv_chat, mBeautyConfirm, tv_guanzhu, send_gift;
    private TextView inviteView1, inviteView2, inviteView3;
    public static ListView mListViewMsgItems;
    private LinearLayout mHostCtrView;
    private FrameLayout mFullControllerUi, mBackgound;
    private int mBeautyRate, mWhiteRate;
    private HorizontalListView member_headList;
    private RelativeLayout rl_head;
    private HostInfoPopupWindow mHostInfoPopupWindow;
    private int Max_X, Max_Y;
    private ManberInfoPopupWindow ManberInfoPopupWindow;
    private TextView tv_NMBtext, tv_NMB;
//    private TextView tv_admin;

    private void showHeadIcon(ImageView view, String avatar) {

        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zanwu);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            SxbLog.d(TAG, "load icon: " + avatar);

            RequestManager req = Glide.with(this);
            if (avatar.toString().contains("http://")) {
                req.load(avatar).transform(new GlideCircleTransform(this)).into(view);
                //Log.i("主播头像", "微信avatar" + avatar);
            } else {
                req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(this)).into(view);
                //Log.i("主播头像", "avatar" + avatar);
            }
        }
    }

    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {

        PackageManager pm = context.getPackageManager();

        // 返回给定条件的所有ResolveInfo对象(本质上是Service)
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // 确保只有一个service匹配
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        //获取component信息并创建ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        //获取Service所在的包名
        String packageName = serviceInfo.serviceInfo.packageName;
        //获取Service的名称
        String className = serviceInfo.serviceInfo.name;
        // 通过包名和service的类名创建component
        //ComponentName用于指定打开其他应用的activity和service
        ComponentName component = new ComponentName(packageName, className);
        // 创建新的intent
        Intent explicitIntent = new Intent(implicitIntent);
        // 为intent设置指定的组件
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
    private void isPlayGone(boolean isTrues){
        if (isTrues){
            ll_play.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.push_bottom_ins);
            ll_play.startAnimation(animation);
        }else {
            Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.push_bottom_outs);
            ll_play.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ll_play.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }
    /**
     * 初始化界面
     */
    private ImageView[] poker_z=new ImageView[5];
    private void initView() {
        hideStatusBar();
        rl_tuhaobang= (RelativeLayout) findViewById(R.id.rl_tuhaobang);
        rl_chongzhi= (RelativeLayout) findViewById(R.id.rl_chongzhi);
        rl_maxGift= (RelativeLayout) findViewById(R.id.rl_maxGift);
        marqueeTextView = (TextView) findViewById(R.id.at_auto);
        at_tuhaobang = (TextView) findViewById(R.id.at_tuhaobang);
        at_maxGift = (TextView) findViewById(R.id.at_maxGift);



        tv_play= (TextView) findViewById(R.id.tv_play);
        ll_niuniu= (LinearLayout) findViewById(R.id.ll_niuniu);
        ll_dice= (LinearLayout) findViewById(R.id.ll_dice);
        ll_play= (LinearLayout) findViewById(R.id.ll_play);

        tv_play.setOnClickListener(this);
        ll_dice.setOnClickListener(this);
        ll_niuniu.setOnClickListener(this);

        iv_popularity= (ImageView) findViewById(R.id.iv_popularity);
        iv_popularity.setOnClickListener(this);
        iv_popularity.setVisibility(View.GONE);

//        tv_admin= (TextView) findViewById(R.id.tv_admin);
        tv_game= (TextView) findViewById(R.id.tv_games);//游戏
        tv_game.setOnClickListener(this);
        tv_gamesd= (TextView) findViewById(R.id.tv_gamesd);
        tv_gamesd.setOnClickListener(this);
        rl_nn_choice= (RelativeLayout) findViewById(R.id.rl_nn_choice);
        rl_anim_win= (RelativeLayout) findViewById(R.id.rl_anim_win);
        poker_z[0]= (ImageView) findViewById(R.id.iv_nn_poker1);
        poker_z[1]= (ImageView) findViewById(R.id.iv_nn_poker2);
        poker_z[2]= (ImageView) findViewById(R.id.iv_nn_poker3);
        poker_z[3]= (ImageView) findViewById(R.id.iv_nn_poker4);
        poker_z[4]= (ImageView) findViewById(R.id.iv_nn_poker5);

        sysMsgList=new ArrayList<>();//系统通知的数据
        go_phb_Layout= (RelativeLayout) findViewById(R.id.gotoPHB);//柠檬币外部布局用来 实现点击事件
        go_phb_Layout.setOnClickListener(this);
        Display My_Display = getWindow().getWindowManager().getDefaultDisplay();
        Max_X = My_Display.getWidth();
        Max_Y = My_Display.getHeight() + 300;

        mHeartLayout = (PeriscopeLayout) findViewById(R.id.heart_layout);
        mHostCtrView = (LinearLayout) findViewById(R.id.host_bottom_layout);
        mHostLeaveLayout = (LinearLayout) findViewById(R.id.ll_host_leave);//暂时离开
        mHeadIcon = (ImageView) findViewById(R.id.head_icon);
        tvMembers = (TextView) findViewById(R.id.member_counts);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        head_up_layout = (LinearLayout) findViewById(R.id.head_up_layout);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        member_headList = (HorizontalListView) findViewById(R.id.member_headList);
        tv_NMBtext = (TextView) findViewById(R.id.tv_NMBtext);
        tv_NMB = (TextView) findViewById(R.id.tv_NMB);
        paramTimer.schedule(task, 1000, 1000);
        tv_chat.setOnClickListener(this);
        //followHandle();
        avMemberInfos = new ArrayList<>();//直播时右上角显示头像的数据
        HeadAdapter = new MembersHeadAdapter(this, avMemberInfos);
        member_headList.setAdapter(HeadAdapter);
        initViewdd();
        member_headList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManberInfoPopupWindow = new ManberInfoPopupWindow(LiveingActivity.this);
                ManberInfoPopupWindow.showPopupWindow(LiveingActivity.this.findViewById(R.id.rl_main), avMemberInfos.get(position));
            }
        });


        baby_btn = (TextView) findViewById(R.id.baby_btn);//宝贝
        BtnSwitch = (TextView) findViewById(R.id.switch_cam);//相机前后摄像头
        BtnBeauty = (TextView) findViewById(R.id.beauty_btn);//美颜
        BtnFlash = (TextView) findViewById(R.id.flash_btn);//闪光灯
        share_btn = (TextView) findViewById(R.id.share_btn);//分享
        send_gift = (TextView) findViewById(R.id.send_gift);//送礼物
        mBeautyConfirm = (TextView) findViewById(R.id.qav_beauty_setting_finish);//美颜提交
        gif_donghua = (ImageView) findViewById(R.id.gif_donghua);//显示动画的控件
        //zaixian_member = (TextView) findViewById(R.id.zaixian_member);//在线人数
        //getFollow();//获取主播的关注人数
        //tvMembers.setText(CurLiveInfo.getMembers()+"在线");
        //zaixian_member.setText("" + CurLiveInfo.getMembers());

        giftCon = (LinearLayout) findViewById(R.id.gift_con);
        giftManger = new GiftShowManager(LiveingActivity.this, giftCon,rl_anim);
        giftManger.showGift();//开始显示礼物

        presentdialog = new Dialog(this, R.style.dialog);
        mHostInfoPopupWindow = new HostInfoPopupWindow(this);
        baby_btn.setOnClickListener(this);
        BtnSwitch.setOnClickListener(this);
        BtnBeauty.setOnClickListener(this);
        BtnFlash.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        send_gift.setOnClickListener(this);
        mBeautyConfirm.setOnClickListener(this);

        getHostCoins();//主播的魅力值


        //Log.i("getHostID", CurLiveInfo.getHostID());
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            //Log.i("主播身份", "1");
            rl_head.setOnClickListener(this);
            tv_guanzhu.setVisibility(View.GONE);
            inviteView1 = (TextView) findViewById(R.id.invite_view1);
            inviteView2 = (TextView) findViewById(R.id.invite_view2);
            inviteView3 = (TextView) findViewById(R.id.invite_view3);
            inviteView1.setOnClickListener(this);
            inviteView2.setOnClickListener(this);
            inviteView3.setOnClickListener(this);
            send_gift.setVisibility(View.GONE);
            initBackDialog();


            mBeautySettings = (LinearLayout) findViewById(R.id.qav_beauty_setting);
            mMemberDg = new MembersDialog(this, R.style.floag_dialog, this);//选择与主播连麦列表
            showHeadIcon(mHeadIcon, LiveMySelfInfo.getInstance().getAvatar());
            mBeautyBar = (SeekBar) (findViewById(R.id.qav_beauty_progress));
            mBeautyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SxbLog.d("SeekBar", "onStopTrackingTouch");
                    if (mProfile == mBeatuy) {
                        Toast.makeText(LiveingActivity.this, "beauty " + mBeautyRate + "%", Toast.LENGTH_SHORT).show();//美颜度
                    } else {
                        Toast.makeText(LiveingActivity.this, "white " + mWhiteRate + "%", Toast.LENGTH_SHORT).show();//美白度
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    SxbLog.d("SeekBar", "onStartTrackingTouch");
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    Log.i(TAG, "onProgressChanged " + progress);
                    if (mProfile == mBeatuy) {
                        mBeautyRate = progress;
                        ILiveSDK.getInstance().getAvVideoCtrl().inputBeautyParam(getBeautyProgress(progress));//美颜
                    } else {
                        mWhiteRate = progress;
                        ILiveSDK.getInstance().getAvVideoCtrl().inputWhiteningParam(getBeautyProgress(progress));//美白
                    }
                }
            });

            //startService(getExplicitIntent(LiveingActivity.this,new Intent("com.example.project.Services")));

        } else {  //成员观看  自己非主播身份
            initInviteDialog();
            BtnSwitch.setVisibility(View.GONE);
            BtnBeauty.setVisibility(View.GONE);
            BtnFlash.setVisibility(View.GONE);
            rl_head.setOnClickListener(this);
            List<String> ids = new ArrayList<>();
            ids.add(CurLiveInfo.getHostID());
            showHeadIcon(mHeadIcon, CurLiveInfo.getHostAvator());
//            if (!(CurLiveInfo.getMembers()>8000)){
//                nums.add("0");
//            }
            if (CurLiveInfo.getMembers()>8000){
                CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);
            }else {
                CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1+40);
            }

            live_pp();
            handler.post(myRable);
           /* mHostLayout = (LinearLayout) findViewById(R.id.head_up_layout);
            mHostLayout.setOnClickListener(this);*/
        }
        mFullControllerUi = (FrameLayout) findViewById(R.id.controll_ui);

        BtnBack = (TextView) findViewById(R.id.btn_back);
        BtnBack.setOnClickListener(this);
        tv_guanzhu.setOnClickListener(this);
        mListViewMsgItems = (ListView) findViewById(R.id.im_msg_listview);
        mArrayListChatEntity = new ArrayList<ChatEntity>();
        mArrayListPresent = new ArrayList<ChatEntity>();
        mChatMsgListAdapter = new ChatMsgListAdapter(this, mListViewMsgItems, mArrayListChatEntity,pHandler);
        mListViewMsgItems.setAdapter(mChatMsgListAdapter);

        //zaixian_member.setText("" + CurLiveInfo.getMembers());
        //tvMembers.setText(CurLiveInfo.getMembers()+"在线");
        //TODO 获取渲染层
        mRootView = (AVRootView) findViewById(R.id.av_root_view);
        iv_ggg= (ImageView) findViewById(R.id.iv_ggg);
        //TODO 设置渲染层
        ILVLiveManager.getInstance().setAvVideoView(mRootView);

        mRootView.setGravity(AVRootView.LAYOUT_GRAVITY_TOP);
        mRootView.setSubMarginY(getResources().getDimensionPixelSize(R.dimen.small_area_margin_top));//130dp
        mRootView.setSubMarginX(getResources().getDimensionPixelSize(R.dimen.small_area_marginright));//10dp
        mRootView.setSubPadding(getResources().getDimensionPixelSize(R.dimen.small_area_marginbetween));//10dp
        mRootView.setSubWidth(getResources().getDimensionPixelSize(R.dimen.small_area_width));//60dp
        mRootView.setSubHeight(getResources().getDimensionPixelSize(R.dimen.small_area_height));//110dp
        mRootView.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
            @Override
            public void onSubViewCreated() {
                for (int i = 1; i < ILiveConstants.MAX_AV_VIDEO_NUM; i++) {
                    final int index = i;
                    //    mRootView.getViewByIndex(i).setRotationMode(ILiveConstants.ROTATION_AUTO);
                    AVVideoView avVideoView = mRootView.getViewByIndex(index);
                    avVideoView.setGestureListener(new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            mRootView.swapVideoView(0, index);
                            backGroundId = mRootView.getViewByIndex(0).getIdentifier();
                            // updateHostLeaveLayout();
                            backGroundId = mRootView.getViewByIndex(0).getIdentifier();
                            if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {//自己是主播
                                if (backGroundId.equals(LiveMySelfInfo.getInstance().getId())) {//背景是自己
                                    mHostCtrView.setVisibility(View.VISIBLE);
                                } else {//背景是其他成员
                                    mHostCtrView.setVisibility(View.INVISIBLE);
                                }
                            } else {//自己成员方式
                                if (backGroundId.equals(LiveMySelfInfo.getInstance().getId())) {//背景是自己
                                } else if (backGroundId.equals(CurLiveInfo.getHostID())) {//主播自己
                                } else {
                                }
                            }
                            return super.onSingleTapConfirmed(e);
                        }
                    });
                }

                mRootView.getViewByIndex(0).setRecvFirstFrameListener(new AVVideoView.RecvFirstFrameListener() {
                    @Override
                    public void onFirstFrameRecved(int width, int height, int angle, String identifier) {
                        //主播心跳
                        if (isf==0){
                                isf=1;
                                handler.post(myRunnable1);

                                showGames();
                            if (CurLiveInfo.getHostID().equals(LoginManager.getInstance().getUserID(LiveingActivity.this))){
                                //直播时间
                                mVideoTimer = new Timer(true);
                                mVideoTimerTask = new VideoTimerTask();
                                mVideoTimer.schedule(mVideoTimerTask, 1000, 1000);
                                bFirstRender = false;
                                handler.post(myRunnable);
                                showGameNNs();
                            }else {
                                handler.postDelayed(HostRunnable,15000);
                            }
                        }
                    }
                });
            }
        });
    }
    private int isf=0;
    private void live_pp() {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }else {
            Api.live_pp(LoginManager.getInstance().getUserID(LiveingActivity.this), CurLiveInfo.getRoomNum()
                    , new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("充值","用户进入--apiResponse="+apiResponse.toString());
                        }

                        @Override
                        public void onFailure(String errMessage) {
                            //Log.i("充值","用户进入--errMessage="+errMessage.toString());
                        }
                    });
        }
    }

    private void startAnimtions() {
        Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.admin_translate);
        //tv_admin.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    //mHandler.sendEmptyMessage(GIFT_STOP_ADMIN);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mLiveHelper.startRecord(new ILiveRoomOption(""));
        ILiveRoomManager.getInstance().onResume();
        getIntegrals();
        getIntegralsd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILiveRoomManager.getInstance().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mLiveHelper.stopPush();
    }

    private Handler handler = new Handler();

    private Runnable myRunnable = new Runnable() {//主播心跳
        public void run() {
            if (!isStop == true) {
                handler.postDelayed(this, 10000);
                sendHeartbeat(CurLiveInfo.getHost_phone(), CurLiveInfo.getAdmires(), CurLiveInfo.getMembers(), (int) mSecond);//最后一个参数 直播时长 时间戳
                //Log.i("同步心跳", mSecond + "");
            }
        }
    };
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    private Runnable HostRunnable = new Runnable() {
        public void run() {
            if (!isStop == true) {
                handler.postDelayed(this, 10000);
                Api.get_live_number(CurLiveInfo.getRoomNum(), get_live_number);
                //Log.i("同步心跳", mSecond + "");
            }
        }
    };

    private Runnable myRunnable1 = new Runnable() {//飘心动画
        public void run() {
            if (!isStop == true) {
                handler.postDelayed(this, 2000);
                // mHeartLayout.addFavor();
                mHeartLayout.addHeart(Max_X, Max_Y);
            }
        }
    };
    private Runnable myRable = new Runnable() {//检测主播是否还在直播
        public void run() {
            if (!isStop == true) {
                time_span();
                handler.postDelayed(this, 30000);
            }
        }
    };
    String time="";
    private void time_span() {
        Api.time_span(CurLiveInfo.getRoomNum(), time_span);
    }
    //检测直播间的心跳
    ApiResponseHandler time_span=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("退出异常","退出异常apiResponse="+apiResponse+"  time="+time);
            if (apiResponse.getCode()==Api.SUCCESS){
                String t = apiResponse.getData();
                if (isTrue&&time.equals(t)){
                    Api.stopLiveRoom(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            if (apiResponse.getCode() == Api.SUCCESS) {
                            }
                            //Log.i("解散群组", apiResponse.toString());
                        }

                        @Override
                        public void onFailure(String errMessage) {
                            //Log.i("解散群组", errMessage);
                        }
                    });

                    LiveingActivity.this.finish();
                }else {
                    time=t;
                }
                //Log.i("退出异常","退出异常1apiResponse="+apiResponse+"  time="+time);
            }
        }
    };
    //人数的心跳
    ApiResponseHandler get_live_number=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("人数","get_live_number.apiResponse"+apiResponse.getData());
            if (apiResponse.getCode()==Api.SUCCESS){
                if (isNumeric(apiResponse.getData())){
                    int i = Integer.parseInt(apiResponse.getData());
                    CurLiveInfo.setMembers(i);
                    tvMembers.setText(CurLiveInfo.getMembers()+"在线");
                }
            }
        }
    };
    //主播上传状态心跳
    ApiResponseHandler sendHeartbeat=new ApiResponseHandler(this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("同步心跳", apiResponse.toString());
        }

        @Override
        public void onFailure(String errMessage) {
            super.onFailure(errMessage);
            //Log.i("同步心跳", errMessage);
        }
    };
    //主播上传色子游戏状态心跳
    ApiResponseHandler dicegame_state_add= new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("status","api="+apiResponse.toString());
        }
    };
    //主播上传牛牛游戏状态心跳
    ApiResponseHandler niuniu_state_add= new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {

            //Log.i("牛牛","apiResponse="+apiResponse.toString());
        }
    };
    //获取游戏牌数
    ApiResponseHandler niuniu_dice_list_data=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("牛牛","xxx——游戏牌数apiResponse="+apiResponse.toString());
            if (apiResponse.getCode()==Api.SUCCESS){
                pokerBeen= JSONArray.parseArray(apiResponse.getData(), PokerBean.class);
                Map<Integer, String[]> chip_ch = pokerBeen.get(0).getChip_ch();//第0桌
                setDrawPokers(chip_ch,poker_z);
                Map<Integer, String[]> chip_ch1 = pokerBeen.get(1).getChip_ch();//第1桌
                setDrawPokers(chip_ch1,poker_one);
                Map<Integer, String[]> chip_ch2 = pokerBeen.get(2).getChip_ch();//第2桌
                setDrawPokers(chip_ch2,poker_two);
                Map<Integer, String[]> chip_ch3 = pokerBeen.get(3).getChip_ch();//第3桌
                setDrawPokers(chip_ch3,poker_three);


                draws[0] =getPoker(chip_ch.get(4)[1])[Integer.parseInt(chip_ch.get(4)[0]) - 1];//获取最后一张牌
                draws[1] =getPoker(chip_ch1.get(4)[1])[Integer.parseInt(chip_ch1.get(4)[0]) - 1];//获取最后一张牌
                draws[2] =getPoker(chip_ch2.get(4)[1])[Integer.parseInt(chip_ch2.get(4)[0]) - 1];//获取最后一张牌
                draws[3] =getPoker(chip_ch3.get(4)[1])[Integer.parseInt(chip_ch3.get(4)[0]) - 1];//获取最后一张牌

                startfpAnim(draws);

            }else {
                if (isTrue){
                    startAnimtionsd();
                }
            }
        }

        @Override
        public void onFailure(String errMessage) {
            super.onFailure(errMessage);
            if (isTrue){
                startAnimtionsd();
            }
        }
    };
    //获取牛牛游戏下注心跳
    ApiResponseHandler niuniu_user_integral_heartbeat=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("牛牛","下注心跳回调apiResponse="+apiResponse.toString());
            if (apiResponse.getCode()==Api.SUCCESS){
                lists.clear();
                String data = apiResponse.getData();
                lists.addAll(JSONArray.parseArray(data, ChipBeatBean.class));

                for (int i = lists.size() - 1; i >= 0; i--) {
                    ChipBeatBean chipBeatBean = lists.get(i);
                    if (chipBeatBean==null){
                        return;
                    }
                    if (chipBeatBean.getTable_number().equals("1")){
                        String string = tv_nn_stake_bet_total_num_1.getText().toString();
                        int i1 = Integer.parseInt(string);
                        if (chipBeatBean.getBet_money_sum()>i1){
                            int i2 = chipBeatBean.getBet_money_sum() - i1;
                            //向1号池添加筹码
                            stakeGiftsd(rl_nn_anim_stake1,i2);//添加textView；

                            tv_nn_stake_bet_total_num_1.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                        }
                    }else if (chipBeatBean.getTable_number().equals("2")){
                        String string = tv_nn_stake_bet_total_num_2.getText().toString();
                        int i1 = Integer.parseInt(string);
                        if (chipBeatBean.getBet_money_sum()>i1){
                            int i2 = chipBeatBean.getBet_money_sum() - i1;
                            //向2号池添加筹码
                            stakeGiftsd(rl_nn_anim_stake2,i2);
                            tv_nn_stake_bet_total_num_2.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                        }
                    }else if (chipBeatBean.getTable_number().equals("3")){
                        String string = tv_nn_stake_bet_total_num_3.getText().toString();
                        int i1 = Integer.parseInt(string);
                        if (chipBeatBean.getBet_money_sum()>i1){
                            int i2 = chipBeatBean.getBet_money_sum() - i1;
                            //向3号池添加筹码
                            stakeGiftsd(rl_nn_anim_stake3,i2);
                            tv_nn_stake_bet_total_num_3.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                        }
                    }
                }
            }
        }
    };
    //请求发牌接口
    ApiResponseHandler niuniu_dice_list=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("牛牛","请求摇色子="+apiResponse+"CurLiveInfo.getRoomNum()="+CurLiveInfo.getRoomNum());
            if (apiResponse.getCode()==Api.SUCCESS){
            }else {
                if (isTrue){
                    dice_listsd();
                }
            }
        }

        @Override
        public void onFailure(String errMessage) {
            super.onFailure(errMessage);
            if (isTrue){
                dice_listsd();
            }
        }
    };
    //牛牛清除上一局数据接口
    ApiResponseHandler niuniu_dice_shang=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("牛牛","xxx——游戏下一局开始前清除上一局数据接口");
            if (apiResponse.getCode()==Api.SUCCESS){

            }else {
                if (isTrue){
                    dice_shang();
                }
            }
        }

        @Override
        public void onFailure(String errMessage) {
            super.onFailure(errMessage);
            dice_shang();
        }
    };
    //色子清除上一局数据接口
    ApiResponseHandler dice_shang=new ApiResponseHandler(LiveingActivity.this) {
        @Override
        public void onSuccess(ApiResponse apiResponse) {
            //Log.i("色子","xxx——游戏下一局开始前清除上一局数据接口");
            if (apiResponse.getCode()==Api.SUCCESS){

            }else {
                if (isTrue){
                    dice_shang();
                }
            }
        }

        @Override
        public void onFailure(String errMessage) {
            super.onFailure(errMessage);
            dice_shang();
        }
    };

    private void sendHeartbeat(String host_phone, int admire_count, int watch_count, int time_span) {
        Api.getHeartBeat(host_phone, admire_count, watch_count, time_span,sendHeartbeat);
    }

    /**
     * 记时器
     */
    private class VideoTimerTask extends TimerTask {
        private Intent intent=new Intent();
        {
            intent.setAction("com.example.project.limolive.service");
            intent.putExtra("111","111");
        }
        public void run() {
            SxbLog.i(TAG, "timeTask ");
            ++mSecond;
            if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST){
                sendBroadcast(intent);
                mHandler.sendEmptyMessage(UPDAT_WALL_TIME_TIMER_TASK);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
            dice_shang();
            dice_shangs();
        }

        handler.removeCallbacks(myRable);
        handler.removeCallbacks(myRunnable1);
        handler.removeCallbacks(myRunnable);
        game_handler.removeCallbacks(Host_Runnable);
        //game_handler.removeCallbacks(stake_Runnable);
        Poker_Host_handler.removeCallbacks(Poker_Runnable);
        if (giftManger!=null){
            giftManger.disPlay();

        }
        if (mArrayListChatEntity!=null){
            mArrayListChatEntity.clear();
        }
        mArrayListChatEntity=null;
        if (mArrayListPresent!=null){
            mArrayListPresent.clear();
        }
        mChatMsgListAdapter=null;
        gif_donghua=null;
        mListViewMsgItems=null;
        giftManger=null;
        Order_sn=null;
        unregisterReceiver(myReceiver);
        unregisterReceiver(myReceiver1);
        isStop = true;
        if (instance!=null){
            instance.onDesPlay();
        }
        if (instancePoker!=null){
            instancePoker.onDesPlay();
        }
        if (mDetailDialog!=null){
            mDetailDialog.dismiss();
        }

        if (backDialog!=null){
            backDialog.dismiss();
        }
        SoundPlayUtils.desPlay();
        instance=null;
        watchCount = 0;
        isTrue=false;
        super.onDestroy();
        if (null != mHearBeatTimer) {
            mHearBeatTimer.cancel();
            mHearBeatTimer = null;
        }
        if (null != mVideoTimer) {
            mVideoTimer.cancel();
            mVideoTimer = null;
        }
        if (null != paramTimer) {
            paramTimer.cancel();
            paramTimer = null;
        }

        inviteViewCount = 0;
        thumbUp = 0;
        CurLiveInfo.setMembers(0);
        CurLiveInfo.setAdmires(0);
        CurLiveInfo.setCurrentRequestCount(0);
        CurLiveInfo.setMaxmembers(0);
        if (mLiveHelper!=null){
            mLiveHelper.onDestory();
        }
    }

    /**
     * 点击Back键
     */
    @Override
    public void onBackPressed() {
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {

        } else {
            mLiveHelper.sendGroupCmd(Constants.AVIMCMD_EXITLIVE, "");
        }
        if (bInAvRoom) {
            bDelayQuit = false;
            quiteLiveByPurpose();
        } else {
            clearOldData();
            finish();
        }
    }



    /**
     * 主动退出直播
     */
    private void quiteLiveByPurpose() {
        //Log.i("监控主播退出了房间", "quiteLiveByPurpose");
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            if (backDialog.isShowing() == false){

                backDialog.show();
                if (bitmap==null){
                    printScreen(mRootView);
                }
            }

        } else {
            mLiveHelper.startExitRoom();
        }
    }


    private Dialog backDialog;

    private void initBackDialog() {
        backDialog = new Dialog(this, R.style.dialog);
        backDialog.setContentView(R.layout.dialog_end_live);
        TextView tvSure = (TextView) backDialog.findViewById(R.id.btn_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDialog.dismiss();
                if (null != mLiveHelper) {
                    if (instancePoker!=null){
                        instancePoker.hide();
                        instancePoker.dismiss();
                    }
                    if (instance!=null){
                        instance.hide();
                        instance.dismiss();
                    }
                    initDetailDailog();
                    mLiveHelper.startExitRoom();
                }
            }
        });
        TextView tvCancel = (TextView) backDialog.findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDialog.cancel();
            }
        });
    }
    public Bitmap bitmap;
    public void printScreen(View view) {
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        bitmap = view.getDrawingCache();

        //bitmap = activityShot(this);
    }
    public static Bitmap activityShot(Activity activity) {
         /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        WindowManager windowManager = activity.getWindowManager();

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
                height-statusBarHeight);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }
    private void updateHostLeaveLayout() {
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            return;
        } else {
            // 退出房间或主屏为主播且无主播画面显示主播已离开
            if (!bInAvRoom || (CurLiveInfo.getHostID().equals(backGroundId) && !mRenderUserList.contains(backGroundId))) {
                mHostLeaveLayout.setVisibility(View.VISIBLE);
            } else {//
                mHostLeaveLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 被动退出直播
     */
    private void quiteLivePassively() {
        //Log.i("监控主播退出了房间", "quiteLivePassively");
        mLiveHelper.perpareQuitRoom(false);
//        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void readyToQuit() {
        //Log.i("监控主播退出了房间", "readyToQuit");
        mLiveHelper.startExitRoom();
    }

    /**
     * 完成进出房间流程
     *
     * @param id_status
     * @param isSucc
     */
    @Override
    public void enterRoomComplete(int id_status, boolean isSucc) {
        //Toast.makeText(LiveingActivity.this, "进入房间  " + id_status + " 成功 " + isSucc, Toast.LENGTH_SHORT).show();
        //必须得进入房间之后才能初始化UI
        //mEnterRoomHelper.initAvUILayer(avView);
        bInAvRoom = true;
        bDelayQuit = true;

        if (isSucc == true) {
            //IM初始化
            if (id_status == Constants.HOST) {//主播方式加入房间成功
                //开启摄像头渲染画面
                SxbLog.i(TAG, "createlive enterRoomComplete isSucc" + isSucc);
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putBoolean("living", true);
                editor.apply();
            } else {
                //发消息通知上线
                mLiveHelper.sendGroupCmd(Constants.AVIMCMD_ENTERLIVE, "");
            }
            if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
//                if (bFirstRender) {
//                    //主播心跳
//                    handler.post(myRunnable);
//                    //直播时间
//                    mVideoTimer = new Timer(true);
//                    mVideoTimerTask = new VideoTimerTask();
//                    mVideoTimer.schedule(mVideoTimerTask, 1000, 1000);
//                    bFirstRender = false;
//                }


            }
        }
    }


    @Override
    public void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo) {
        //Log.i("监控主播退出了房间", "quiteRoomComplete");

        notifyServerLiveEnd();//通知server 我下线了

        //完全退出直播  通知服务器 我下线了
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            if ((getBaseContext() != null) && (null != mDetailDialog) && (mDetailDialog.isShowing() == false)) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putBoolean("living", false);
                editor.apply();

            }

            TIMGroupManager.getInstance().deleteGroup(CurLiveInfo.getChatRoomId(), new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {

                    //错误码code和错误描述desc，可用于定位请求失败原因
                    //错误码code列表请参见错误码表
                    Log.d("解散群组", "login failed. code: " + code + " errmsg: " + desc);
                }

                @Override
                public void onSuccess() {
                    //解散群组成功
                    Log.d("解散群组", "成功d");
                    // updateHostLeaveLayout();
                }
            });
        } else {
            clearOldData();
            finish();
        }

        bInAvRoom = false;
        bDelayQuit = false;
        updateHostLeaveLayout();
        //解散群组

    }

    //private TextView mDetailTime, mDetailAdmires, mDetailWatchCount, btn_save, btn_delete,tv_get_NMB,tv_user_name,tv_share;
    //private ImageView iv_chat, iv_qq, iv_weibo, iv_colect;
    //private SimpleDraweeView iv_user_head;
    //private ImageView bgs1;
    private void initDetailDailog() {
        mDetailDialog = new Dialog(this, R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_live_details);
        TextView mDetailTime = (TextView) mDetailDialog.findViewById(R.id.tv_time);

        ImageView bgs1= (ImageView)mDetailDialog.findViewById(R.id.bgs1);

        TextView tv_user_name = (TextView) mDetailDialog.findViewById(R.id.tv_user_name);
        TextView tv_share = (TextView) mDetailDialog.findViewById(R.id.tv_share);
        SimpleDraweeView iv_user_head = (SimpleDraweeView) mDetailDialog.findViewById(R.id.iv_user_head);
        setNameAvatar(tv_user_name,iv_user_head);

        TextView mDetailAdmires = (TextView) mDetailDialog.findViewById(R.id.tv_admires);
        TextView tv_get_NMB=(TextView) mDetailDialog.findViewById(R.id.tv_get_NMB);//————————————————————
        TextView mDetailWatchCount = (TextView) mDetailDialog.findViewById(R.id.tv_members);
        TextView btn_save = (TextView) mDetailDialog.findViewById(R.id.btn_save);
        TextView btn_delete = (TextView) mDetailDialog.findViewById(R.id.btn_delete);

//        iv_chat = (ImageView) mDetailDialog.findViewById(R.id.iv_chat);
//        iv_qq = (ImageView) mDetailDialog.findViewById(R.id.iv_qq);
//        iv_weibo = (ImageView) mDetailDialog.findViewById(R.id.iv_weibo);
//        iv_colect = (ImageView) mDetailDialog.findViewById(R.id.iv_colect);

        mDetailDialog.setCancelable(false);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailDialog.dismiss();
                LiveingActivity.this.finish();
                Intent intent = new Intent();
                intent.setClass(LiveingActivity.this, MainActivity.class);
                intent.putExtra("share","share");
                startActivity(intent);
            }
        });
        if (bitmap!=null) {
            bgs1.setImageBitmap(bitmap);
        }

        TextView btn_sure = (TextView) mDetailDialog.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
                LiveingActivity.this.finish();
                Intent intent = new Intent();
                intent.setClass(LiveingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存直播
                ToastUtils.showShort(LiveingActivity.this, "保存直播");
                mDetailDialog.dismiss();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除直播
                ToastUtils.showShort(LiveingActivity.this, "删除直播");
                mDetailDialog.dismiss();
            }
        });

        mDetailTime.setText(formatTime);
        mDetailAdmires . setText("" + CurLiveInfo.getAdmires());
        //tv_get_NMB.setText(""+Hostlemon_coins);
        mDetailWatchCount.setText("" +CurLiveInfo.getMaxMembers());
        if (stopnmb==0){
            tv_get_NMB.setText(""+0);//stopnmb为0说明获取柠檬币接口只被掉用了一次 说明收益为0
        }else {
            tv_get_NMB.setText(""+(stopnmb-startnmb));
        }
        mDetailDialog.show();
    }

    private void setNameAvatar(TextView tv_user_name,SimpleDraweeView iv_user_head) {
        tv_user_name.setText(LiveMySelfInfo.getInstance().getNickName());
        if (LiveMySelfInfo.getInstance().getAvatar().toString().contains("http://")) {
            iv_user_head.setImageURI(LiveMySelfInfo.getInstance().getAvatar());
        } else {
            iv_user_head.setImageURI(ApiHttpClient.API_PIC+LiveMySelfInfo.getInstance().getAvatar());
        }

    }


    /**
     * 成员状态变更 加入或退出房间
     *
     * @param id
     * @param name
     */
    List<String> nums=new ArrayList<>();//进入
    List<String> nums1=new ArrayList<>();//退出
    @Override
    public void memberJoin(String id, String name) {
        SxbLog.d(TAG, LogConstants.ACTION_VIEWER_ENTER_ROOM + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "on member join" +
                LogConstants.DIV + "进入房间" + id);
        watchCount++;
        //Log.i("进入房间", "id" + id + "   " + "name" + name + "    " + "watchCount" + watchCount);
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "进入房间", Constants.MEMBER_ENTER,id);
        nums.add("0");
        if (CurLiveInfo.getMembers()>8000){
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);
        }else {
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1+40);
        }

        //zaixian_member.setText("" + CurLiveInfo.getMembers());
        tvMembers.setText(CurLiveInfo.getMembers()+"在线");
        if (bitmap==null){
            printScreen(mRootView);
        }

    }
    boolean is=false;//锁
    boolean isTrue=true;
//    Thread t=new Thread(){
//        @Override
//        public void run() {
////            for (;isTrue;){
////                int j=100;
//////                if (CurLiveInfo.getMembers()<8000){
////                    if (nums.size()>0){
////                        int members = CurLiveInfo.getMembers();
////                        //Log.i("直播","进入前members="+members);
////                        try {
////                            if (is==false){
//////                                //Log.i("直播","进入is="+is);
////                                is=true;
//////                                //Log.i("直播","进入is="+is);
////                                for (;j>0&&isTrue;j--){
//////                                    //Log.i("直播","进入j="+j);
////                                    int members1 = CurLiveInfo.getMembers();
////                                        CurLiveInfo.setMembers(members1 +1);
////                                        Message msg = Message.obtain();
////                                        msg.what = UNTADE_NUM;
////                                        mHandler.sendMessage(msg);
////                                    //Log.i("直播","进入时members="+members1);
//////                                    //Log.i("直播","进入结束=(members1-members)="+(members1-members));
////                                    if ((members1-members)>=40){
////                                        members=0;
////                                        members1=0;
////                                        j=0;
////                                        is=false;
//////                                        //Log.i("直播","进入结束=is="+is);
////                                        if (nums.size()>0){
////                                            nums.remove(0);
////                                        }
////                                    }
////                                }
////                                Thread.sleep(100);
////                            }
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////                    }
//////                }else {
//////                    if (nums.size()>0){
//////                        nums.remove(0);
//////                    }
//////                }
////            }
//        }
//    };
//    Thread t1=new Thread(){
//        @Override
//        public void run() {
//            for (;isTrue;){
//
//                int j=100;
////                if (CurLiveInfo.getMembers()<8000){
//                    if (nums1.size()>0){
//                        try {
//                            if (is==false){
//                                int members = CurLiveInfo.getMembers();
//                                //Log.i("直播","退出前members="+members);
////                                //Log.i("直播","退出，is="+is);
//                                is=true;
////                                //Log.i("直播","退出，is="+is);
//                                for (;j>0&&isTrue;j--){
//
////                                    //Log.i("直播","退出，j="+j);
//                                    int members1 = CurLiveInfo.getMembers();
//                                    //Log.i("直播","退出时members="+members1);
//                                    CurLiveInfo.setMembers(members1 - 1);
//                                        Message msg = Message.obtain();
//                                        msg.what = UNTADE_NUM;
//                                        mHandler.sendMessage(msg);
//
//                                    if ((members-members1)>=40){
////                                        //Log.i("直播","退出结束=is="+is);
//                                        members=0;
//                                        members1=0;
//                                        j=0;
//                                        is=false;
////                                        //Log.i("直播","退出结束=is="+is);
//                                        if (nums1.size()>0){
//                                            nums1.remove(0);
//                                        }
//                                    }
//                                    Thread.sleep(100);
//                                }
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
////                }else {
////                    if (nums1.size()>0){
////                        nums1.remove(0);
////                    }
////                }
//
//            }
//        }
//    };

    @Override
    public void memberQuit(String id, String name) {
        watchCount--;
        nums1.add("0");
        //Log.i("退出房间", "id" + id + "   " + "name" + name + "    " + "watchCount" + watchCount);
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "退出房间", Constants.MEMBER_EXIT,id);
        if (CurLiveInfo.getMembers()>8000){
            if (CurLiveInfo.getMembers() > 0) {
                CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1);
            }
        }else {
            if (CurLiveInfo.getMembers() > 0) {
                CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1-40);
            }
        }


        tvMembers.setText(CurLiveInfo.getMembers()+"在线");
    }

    @Override
    public void hostLeave(String id, String name) {
        //Log.i("监控主播退出了房间", "hostLeave");
        refreshTextListView("主播：", "暂时离开", Constants.HOST_LEAVE,id);
        //主播离开
        quiteLivePassively();
        bDelayQuit = false;
        updateHostLeaveLayout();
        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
            //initDetailDailog();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
      /*  switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 0:
                mLiveHelper.createRoom_Into();
                break;
            default:
                break;
        }*/
    }

    /**
     * 通知用户UserServer房间
     */
    public void notifyServerLiveEnd() {
        //Log.i("退出房间", "走进来没");
        Api.stopLiveRoom(LiveMySelfInfo.getInstance().getMyRoomNum(), new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                }else if (apiResponse.getCode()==-2){
//                    Api.stopLiveRoom(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
//                        @Override
//                        public void onSuccess(ApiResponse apiResponse) {
//                            if (apiResponse.getCode() == Api.SUCCESS) {
//                            }
//                            //Log.i("解散群组", apiResponse.toString());
//                        }
//
//                        @Override
//                        public void onFailure(String errMessage) {
//                            super.onFailure(errMessage);
//                            //Log.i("解散群组", errMessage);
//                        }
//                    });
                }
                //Log.i("异常退出检测", apiResponse.toString());
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                //Log.i("异常退出检测", errMessage);

            }
        });
    }

    @Override
    public void hostBack(String id, String name) {
        //Log.i("监控主播退出了房间", "hostBack");
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "回到房间", Constants.HOST_BACK,id);
    }

    private float getBeautyProgress(int progress) {
        SxbLog.d("shixu", "progress: " + progress);
        return (9.0f * progress / 100.0f);
    }

    @Override
    public void showInviteDialog() {
        if ((inviteDg != null) && (getBaseContext() != null) && (inviteDg.isShowing() != true)) {
            inviteDg.show();
        }
    }

    @Override
    public void hideInviteDialog() {
        if ((inviteDg != null) && (inviteDg.isShowing() == true)) {
            inviteDg.dismiss();
        }
    }


    @Override
    public void refreshText(String text, String name) {
        if (text != null) {
            //refreshTextListView(name, text, Constants.TEXT_TYPE);
        }
    }

    @Override
    public void refreshText(String text, String name, String phone) {
        refreshTextListView(name, text,phone, Constants.TEXT_TYPE);
    }

    @Override
    public void refreshThumbUp(String id, String name) {
        //  CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
        //  refreshTextListView(TextUtils.isEmpty(name) ? id : name, "关注", Constants.AVIMCMD_PRAISE);

    }

    @Override
    public void refreshUI(String id) {
        //当主播选中这个人，而他主动退出时需要恢复到正常状态
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST)
            if (!backGroundId.equals(CurLiveInfo.getHostID()) && backGroundId.equals(id)) {
                backToNormalCtrlView();
            }
    }


    private int inviteViewCount = 0;

    @Override
    public boolean showInviteView(String id) {
        SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "invite up show" +
                LogConstants.DIV + "id " + id);
        int requetCount = 1 + inviteViewCount;
        if (requetCount > 3) {
            Toast.makeText(LiveingActivity.this, "最多只能邀请三个连麦哟", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hasInvited(id)) {
            Toast.makeText(LiveingActivity.this, "连麦成功", Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (requetCount) {
            case 1:
                inviteView1.setText(id);
                inviteView1.setVisibility(View.VISIBLE);
                inviteView1.setTag(id);
                break;
            case 2:
                inviteView2.setText(id);
                inviteView2.setVisibility(View.VISIBLE);
                inviteView2.setTag(id);
                break;
            case 3:
                inviteView3.setText(id);
                inviteView3.setVisibility(View.VISIBLE);
                inviteView3.setTag(id);
                break;
        }
        mLiveHelper.sendC2CCmd(Constants.AVIMCMD_MUlTI_HOST_INVITE, "", id);
        inviteViewCount++;
        //30s超时取消
        Message msg = new Message();
        msg.what = TIMEOUT_INVITE;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, 30 * 1000);
        return true;
    }


    /**
     * 判断是否邀请过同一个人
     *
     * @param id
     * @return
     */
    private boolean hasInvited(String id) {
        if (id.equals(inviteView1.getTag())) {
            return true;
        }
        if (id.equals(inviteView2.getTag())) {
            return true;
        }
        if (id.equals(inviteView3.getTag())) {
            return true;
        }
        return false;
    }

    @Override
    public void cancelInviteView(String id) {
        if ((inviteView1 != null) && (inviteView1.getTag() != null)) {
            if (inviteView1.getTag().equals(id)) {
            }
            if (inviteView1.getVisibility() == View.VISIBLE) {
                inviteView1.setVisibility(View.INVISIBLE);
                inviteView1.setTag("");
                inviteViewCount--;
            }
        }

        if (inviteView2 != null && inviteView2.getTag() != null) {
            if (inviteView2.getTag().equals(id)) {
                if (inviteView2.getVisibility() == View.VISIBLE) {
                    inviteView2.setVisibility(View.INVISIBLE);
                    inviteView2.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView2 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView2 is null");
        }

        if (inviteView3 != null && inviteView3.getTag() != null) {
            if (inviteView3.getTag().equals(id)) {
                if (inviteView3.getVisibility() == View.VISIBLE) {
                    inviteView3.setVisibility(View.INVISIBLE);
                    inviteView3.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView3 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView3 is null");
        }
    }

    @Override
    public void cancelMemberView(String id) {
        //Log.i("监控主播退出了房间", "cancelMemberView");
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
        } else {
            //TODO 主动下麦 下麦；
            SxbLog.d(TAG, LogConstants.ACTION_VIEWER_UNSHOW + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "start unShow" +
                    LogConstants.DIV + "id " + id);
            mLiveHelper.downMemberVideo();
        }
        mLiveHelper.sendGroupCmd(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, id);
        mRootView.closeUserView(id, AVView.VIDEO_SRC_TYPE_CAMERA, true);
        backToNormalCtrlView();
    }


    private void showReportDialog() {
        final Dialog reportDialog = new Dialog(this, R.style.report_dlg);
        reportDialog.setContentView(R.layout.dialog_live_report);

        TextView tvReportDirty = (TextView) reportDialog.findViewById(R.id.btn_dirty);
        TextView tvReportFalse = (TextView) reportDialog.findViewById(R.id.btn_false);
        TextView tvReportVirus = (TextView) reportDialog.findViewById(R.id.btn_virus);
        TextView tvReportIllegal = (TextView) reportDialog.findViewById(R.id.btn_illegal);
        TextView tvReportYellow = (TextView) reportDialog.findViewById(R.id.btn_yellow);
        TextView tvReportCancel = (TextView) reportDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    default:
                        reportDialog.cancel();
                        break;
                }
            }
        };

        tvReportDirty.setOnClickListener(listener);
        tvReportFalse.setOnClickListener(listener);
        tvReportVirus.setOnClickListener(listener);
        tvReportIllegal.setOnClickListener(listener);
        tvReportYellow.setOnClickListener(listener);
        tvReportCancel.setOnClickListener(listener);

        reportDialog.setCanceledOnTouchOutside(true);
        reportDialog.show();
    }

    private void showHostDetail() {
        Dialog hostDlg = new Dialog(this, R.style.host_info_dlg);
        hostDlg.setContentView(R.layout.host_info_layout);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window dlgwin = hostDlg.getWindow();
        WindowManager.LayoutParams lp = dlgwin.getAttributes();
        dlgwin.setGravity(Gravity.TOP);
        lp.width = (int) (display.getWidth()); //设置宽度

        hostDlg.getWindow().setAttributes(lp);
        hostDlg.show();

        TextView tvHost = (TextView) hostDlg.findViewById(R.id.tv_host_name);
        tvHost.setText(CurLiveInfo.getHostName());
        ImageView ivHostIcon = (ImageView) hostDlg.findViewById(R.id.iv_host_icon);
        showHeadIcon(ivHostIcon, CurLiveInfo.getHostAvator());
        TextView tvLbs = (TextView) hostDlg.findViewById(R.id.tv_host_lbs);
        tvLbs.setText(UIUtils.getLimitString(CurLiveInfo.getAddress(), 6));
        ImageView ivReport = (ImageView) hostDlg.findViewById(R.id.iv_report);
        ivReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDialog();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_back) {
            //如果是主播   如果不是主播 就发消息
            if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {

            } else {
                mLiveHelper.sendGroupCmd(Constants.AVIMCMD_EXITLIVE, "");
            }
            if (bInAvRoom) {
                bDelayQuit = false;
                quiteLiveByPurpose();
            } else {
                clearOldData();
                finish();
            }

        } else if (i == R.id.flash_btn) { //闪光灯
            switch (ILiveRoomManager.getInstance().getActiveCameraId()) {
                case ILiveConstants.FRONT_CAMERA:
                    Toast.makeText(LiveingActivity.this, "当前是前置摄像头", Toast.LENGTH_SHORT).show();
                    break;
                case ILiveConstants.BACK_CAMERA:
                    mLiveHelper.toggleFlashLight();
                    break;
                default:
                    Toast.makeText(LiveingActivity.this, "camera is not open", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (i == R.id.switch_cam) {//相机前后摄像头
            switch (ILiveRoomManager.getInstance().getCurCameraId()) {
                case ILiveConstants.FRONT_CAMERA:
                    //Log.i("摄像头切换", "前摄像头");
                    ILiveRoomManager.getInstance().switchCamera(ILiveConstants.BACK_CAMERA);
                    break;
                case ILiveConstants.BACK_CAMERA:
                    //Log.i("摄像头切换", "后摄像头");
                    ILiveRoomManager.getInstance().switchCamera(ILiveConstants.FRONT_CAMERA);
                    break;
            }
        } /*else if (i == R.id.head_up_layout) { //主播信息
            showHostDetail();

        }*/ else if (i == R.id.beauty_btn) {//美颜
            Log.i(TAG, "onClick " + mBeautyRate);

            mProfile = mBeatuy;
            if (mBeautySettings != null) {
                if (mBeautySettings.getVisibility() == View.GONE) {
                    mBeautySettings.setVisibility(View.VISIBLE);
                    mFullControllerUi.setVisibility(View.INVISIBLE);
                    mBeautyBar.setProgress(mBeautyRate);
                } else {
                    mBeautySettings.setVisibility(View.GONE);
                    mFullControllerUi.setVisibility(View.VISIBLE);
                }
            } else {
                SxbLog.i(TAG, "beauty_btn mTopBar  is null ");
            }

        } else if (i == R.id.qav_beauty_setting_finish) {
            mBeautySettings.setVisibility(View.GONE);
            mFullControllerUi.setVisibility(View.VISIBLE);

        } else if (i == R.id.invite_view1) {
            inviteView1.setVisibility(View.INVISIBLE);
            mLiveHelper.sendGroupCmd(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView1.getTag());

        } else if (i == R.id.invite_view2) {
            inviteView2.setVisibility(View.INVISIBLE);
            mLiveHelper.sendGroupCmd(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView2.getTag());

        } else if (i == R.id.invite_view3) {
            inviteView3.setVisibility(View.INVISIBLE);
            mLiveHelper.sendGroupCmd(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView3.getTag());

        } else if (i == R.id.baby_btn) {
            //点击宝贝按钮
            BabyPopupWindow ss = new BabyPopupWindow();
            ss.showPopupWindow(this, this.findViewById(R.id.rl_main));
        } else if (i == R.id.tv_chat) {
            inputMsgDialog();
        } else if (i == R.id.tv_guanzhu) {
            followHandle();
        } else if (i == R.id.share_btn) {
            //点分享
            share();
        } else if (i == R.id.send_gift) {
            //送礼物
            sendGift();
        } else if (i == R.id.rl_head) {
            //点击主播头像 传入 CurLiveInfo
            mHostInfoPopupWindow.showPopupWindow(this.findViewById(R.id.rl_main));
        }else if (i==R.id.gotoPHB){
            Intent intent = new Intent();
            intent.putExtra("hostid",CurLiveInfo.getHostID());
            intent.putExtra("userid", LoginManager.getInstance().getUserID(LiveingActivity.this));
            intent.setClass(LiveingActivity.this,RanksActivity.class);
            startActivity(intent);
        }else if (i==R.id.tv_games){
                showGames();//游戏
        }else if (i==R.id.tv_gamesd){
            showGameNN();//游戏
        }else if (i==R.id.tv_play){//点击游戏按钮
            isPlayGone(isPlay=!isPlay);
        }else if (i==R.id.ll_niuniu){
            showGameNN();//牛牛游戏
            if (instance!=null&&instance.isShowing()){
                if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                    instance.hide();
                    rl_dice.setVisibility(View.GONE);
                }else {
                    instance.dismiss();
                    rl_dice.setVisibility(View.GONE);
                    if (instance!=null){
                        if (timer!=null){
                            timer.cancel();
                        }
                        instance.onDesPlay();
                        instance=null;
                    }
                }
                iv_popularity.setVisibility(View.GONE);
            }
        }else if (i==R.id.ll_dice){
            showGames();//色子游戏
            if (instancePoker!=null&&instancePoker.isShowing()){
                if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                    instancePoker.hide();

                    rl_nn_choice.setVisibility(View.GONE);
                }else {
                    rl_nn_choice.setVisibility(View.GONE);
                    instancePoker.dismiss();
                    if (instancePoker!=null){
                        instancePoker.onDesPlay();
                        if (timers!=null){
                            timers.cancel();
                            timers=null;
                        }
                        instancePoker=null;
                    }
                }
                iv_popularity.setVisibility(View.GONE);
            }
        }else if (i==R.id.iv_popularity){
            showPopularityDialog();
        }
    }

    private boolean isPlay=false;

    private void showPopularityDialog() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.popularity_dialog, null);
        Dialog dialog = new Dialog(this,R.style.BottomDialog_Animation);
        dialog.setContentView(inflate);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        layoutParams.width =inflate.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) (inflate.getResources().getDisplayMetrics().heightPixels*0.5);
        inflate.setLayoutParams(layoutParams);
        inView(inflate,dialog);
        getWindow().setGravity(Gravity.BOTTOM);
        //getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
//        dialog.setCanceledOnTouchOutside(true);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();// 得到属性
        params.gravity = Gravity.BOTTOM;// 显示在底部
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);// 设置属性
    }
    List<CoinListBean> coinListBeen=new ArrayList<>();

    //获取充值数据
    private void live_coinLists() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.live_coinLists(new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("充值","apiResponse"+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                       coinListBeen.addAll(JSONArray.parseArray(apiResponse.getData(), CoinListBean.class));
                    }
                }

            });
        }
    }

    private TextView tv_10000,tv_50000,tv_100000,tv_recharged;
    private String recharge="";
    private RecyclerView rv_rank;
    private LinearLayoutManager llm;
    private LiveRankConsumptionAdapter adapter;
    private void inView(View layout, final Dialog dialog) {
        inhast(layout, dialog);
        rv_rank=layout.findViewById(R.id.rv_rank);
        llm=new LinearLayoutManager(LiveingActivity.this);
        adapter=new LiveRankConsumptionAdapter(liveRechargeList,LiveingActivity.this);
        rv_rank.setLayoutManager(llm);
        rv_rank.setAdapter(adapter);

        live_recharge_list_de();
    }

    private void inhast(View layout, final Dialog dialog) {
        layout.findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recharge="";
                dialog.dismiss();
                liveRechargeList.clear();
            }
        });
        tv_10000=layout.findViewById(R.id.tv_10000);
        tv_50000=layout.findViewById(R.id.tv_50000);
        tv_100000=layout.findViewById(R.id.tv_100000);
        if (coinListBeen.size()>0){
            tv_10000.setText(coinListBeen.get(0).getPrice());
            tv_50000.setText(coinListBeen.get(1).getPrice());
            tv_100000.setText(coinListBeen.get(2).getPrice());
        }

        tv_10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                tv_10000.setBackground(LiveingActivity.this.getDrawable(R.drawable.button_bg1));
                tv_10000.setTextColor(Color.rgb(255,255,255));
                recharge=coinListBeen.get(0).getLemon_id();
            }
        });
        tv_50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                tv_50000.setBackground(LiveingActivity.this.getDrawable(R.drawable.button_bg1));
                tv_50000.setTextColor(Color.rgb(255,255,255));
                recharge=coinListBeen.get(1).getLemon_id();
            }
        });
        tv_100000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                tv_100000.setBackground(LiveingActivity.this.getDrawable(R.drawable.button_bg1));
                tv_100000.setTextColor(Color.rgb(255,255,255));
                recharge=coinListBeen.get(2).getLemon_id();
            }
        });
        tv_recharged=layout.findViewById(R.id.tv_recharged);
        tv_recharged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //请求服务器生成订单跳转到微信支付
                recharge_de();
            }
        });
    }
    public static String Order_sn="";
    //充值
    private void recharge_de() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            if ("".equals(recharge)){
                ToastUtils.showShort(LiveingActivity.this,"请选择充值金额");
                return;
            }
            Api.recharge_de(LoginManager.getInstance().getUserID(LiveingActivity.this), recharge, CurLiveInfo.getRoomNum()
                    , new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("充值","apiResponse"+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                RechargeLiveBean rechargeLiveBean = JSONObject.parseObject(apiResponse.getData(), RechargeLiveBean.class);
                                //Log.i("充值","rechargeLiveBean="+rechargeLiveBean.toString());
                                WXPayUtils wxPayUtils = new WXPayUtils(LiveingActivity.this, Constant.WXRECHAR_URL);
                                wxPayUtils.pay("充值",rechargeLiveBean.getOrder_price(), rechargeLiveBean.getOrder_sn());
                                Order_sn=rechargeLiveBean.getOrder_sn();
                            }
                        }
                    });
        }
    }

    private void reset() {
        tv_10000.setBackground(LiveingActivity.this.getDrawable(R.drawable.btns_bgfw));
        tv_10000.setTextColor(Color.argb(159,0,0,0));
        tv_10000.setText(coinListBeen.get(0).getPrice());

        tv_50000.setBackground(LiveingActivity.this.getDrawable(R.drawable.btns_bgfw));
        tv_50000.setTextColor(Color.argb(159,0,0,0));
        tv_50000.setText(coinListBeen.get(1).getPrice());

        tv_100000.setBackground(LiveingActivity.this.getDrawable(R.drawable.btns_bgfw));
        tv_100000.setTextColor(Color.argb(159,0,0,0));
        tv_100000.setText(coinListBeen.get(2).getPrice());
    }

    /**
     * 牛牛游戏
     */
//--------------------------------------------------------------
    private PokerGameDialog instancePoker;
    private Handler Poker_Host_handler=new Handler();

    private Runnable Poker_Runnable=new Runnable() {
        @Override
        public void run() {
            if(!isStop==true){
                //请求服务器 上传自己状态
                poker_game_state_add();
                Poker_Host_handler.postDelayed(Poker_Runnable,2000);//主播游戏状态心跳
            }

        }
    };

    private void poker_game_state_add() {
        //Log.i("牛牛","心跳数据上传this_statusd="+this_statusd+"-,ls="+ls+"-,CurLiveInfo.getRoomNum()="+CurLiveInfo.getRoomNum());
        Api.niuniu_state_add(CurLiveInfo.getRoomNum(), String.valueOf(ls), this_statusd,niuniu_state_add);
    }
    private double quota=0;
    private int actual=0;
    private ValueAnimator anim=null;//按钮闪烁

    private void updatas(int val) {
        switch (actual){
            case 1:
                iv_nn_chip10.setImageAlpha(val);
                iv_nn_chip25.setImageAlpha(val);
                iv_nn_chip50.setImageAlpha(val);
                iv_nn_chip100.setImageAlpha(val);

                break;
            case 2:
                iv_nn_chip10.setImageAlpha(val);
                iv_nn_chip25.setImageAlpha(val);
                iv_nn_chip50.setImageAlpha(val);
                break;
            case 3:
                iv_nn_chip10.setImageAlpha(val);
                iv_nn_chip25.setImageAlpha(val);
                break;
            case 4:
                iv_nn_chip10.setImageAlpha(val);

                break;
            case 5:
                break;

        }

    }

    private Handler dice_poker_handler=new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_CODE_END_GIFT://动画结束，
                    break;
                case MSG_CODE_END_YAZU://休息结束，开始显示押注
                    this_statusd=STATUS_BET_TYPE;
                    iv_nn_chip10.setImageAlpha(128);
                    iv_nn_chip25.setImageAlpha(128);
                    iv_nn_chip50.setImageAlpha(128);
                    iv_nn_chip100.setImageAlpha(128);
                    actual=0;
                    if (anim==null){
                        anim= ValueAnimator.ofArgb(128, 0, 128, 0, 128,0,130);
                        anim.setDuration(3000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int val = (int) valueAnimator.getAnimatedValue();
                                updatas(val);
                            }

                        });
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                iv_nn_chip10.setImageAlpha(255);
                                bl_choice_status=1;
                                bl_choice1s=1;
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });

                    }

                    double v = Double.parseDouble(tv_nn_price.getText().toString());
                    if (v>0){
                        quota=v/5;//重新计算消费额度
                        if (quota>=500){//全部可点击
                            rl_nn_chip10.setEnabled(true);
                            rl_nn_chip25.setEnabled(true);
                            rl_nn_chip50.setEnabled(true);
                            rl_nn_chip100.setEnabled(true);
                            actual=1;

                        }else if (quota>=100){
                            rl_nn_chip10.setEnabled(true);
                            rl_nn_chip25.setEnabled(true);
                            rl_nn_chip50.setEnabled(true);
                            rl_nn_chip100.setEnabled(false);
                            actual=2;
                        }else if (quota>=50){
                            rl_nn_chip10.setEnabled(true);
                            rl_nn_chip25.setEnabled(true);
                            rl_nn_chip50.setEnabled(false);
                            rl_nn_chip100.setEnabled(false);
                            actual=3;
                        }else if (quota>=10){
                            rl_nn_chip10.setEnabled(true);
                            rl_nn_chip25.setEnabled(false);
                            rl_nn_chip50.setEnabled(false);
                            rl_nn_chip100.setEnabled(false);
                            actual=4;
                        }else {
                            rl_nn_chip10.setEnabled(false);
                            rl_nn_chip25.setEnabled(false);
                            rl_nn_chip50.setEnabled(false);
                            rl_nn_chip100.setEnabled(false);
                            actual=5;
                        }
                        anim.start();
                    }else {
                        quota=0;
                    }
                    //Log.i("牛牛","STATUS_BET_TYPE——休息结束，开始显示押注");
                    rl_nn_anim_stake1.removeAllViews();
                    rl_nn_anim_stake2.removeAllViews();//色子数量容器
                    rl_nn_anim_stake3.removeAllViews();
                    //game_handler.post(stake_Runnable);//心跳开始
                    rl_nn_anim_stake1.setVisibility(View.VISIBLE);
                    rl_nn_anim_stake2.setVisibility(View.VISIBLE);
                    rl_nn_anim_stake3.setVisibility(View.VISIBLE);
                    iv_nn_anim1.setVisibility(View.VISIBLE);
                    iv_nn_anim2.setVisibility(View.VISIBLE);
                    iv_nn_anim3.setVisibility(View.VISIBLE);
                    if (msg.arg2==9){
                        startCountDownTime_tv_numsd(msg.arg1,0);
                    }else {
                        startCountDownTime_tv_numsd(30,0);
                    }
                    break;
                case MSG_CODE_END_REST://输赢动画显示结束，开始休息时间
                    //Log.i("牛牛","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间");
                    if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                        dice_shangs();
                    }
                    getIntegralsd();
                    //msgs=0;
                    tv_nn_stake_bet_total_num_1.setText("0");
                    tv_nn_stake_bet_total_num_2.setText("0");
                    tv_nn_stake_bet_total_num_3.setText("0");
                    rl_anim_win.setBackground(null);
                    rl_anim_win1.setBackground(null);
                    rl_anim_win2.setBackground(null);
                    rl_anim_win3.setBackground(null);
                    iv_nn_poker1_1.setVisibility(View.INVISIBLE);
                    iv_nn_poker1_2.setVisibility(View.INVISIBLE);
                    iv_nn_poker1_3.setVisibility(View.INVISIBLE);
                    iv_nn_poker1_4.setVisibility(View.INVISIBLE);
                    iv_nn_poker1_5.setVisibility(View.INVISIBLE);
                    iv_nn_poker2_1.setVisibility(View.INVISIBLE);
                    iv_nn_poker2_2.setVisibility(View.INVISIBLE);
                    iv_nn_poker2_3.setVisibility(View.INVISIBLE);
                    iv_nn_poker2_4.setVisibility(View.INVISIBLE);
                    iv_nn_poker2_5.setVisibility(View.INVISIBLE);
                    iv_nn_poker3_1.setVisibility(View.INVISIBLE);
                    iv_nn_poker3_2.setVisibility(View.INVISIBLE);
                    iv_nn_poker3_3.setVisibility(View.INVISIBLE);
                    iv_nn_poker3_4.setVisibility(View.INVISIBLE);
                    iv_nn_poker3_5.setVisibility(View.INVISIBLE);
                    poker_z[0].setVisibility(View.INVISIBLE);
                    poker_z[1].setVisibility(View.INVISIBLE);
                    poker_z[2].setVisibility(View.INVISIBLE);
                    poker_z[3].setVisibility(View.INVISIBLE);
                    poker_z[4].setVisibility(View.INVISIBLE);

                    first_p=0;
                    doubles_p=0;
                    third_p=0;
                    bl_choice1s=0;

                    tv_nn_stake_num1.setText("0");
                    tv_nn_stake_num2.setText("0");
                    tv_nn_stake_num3.setText("0");


                    iv_nn_anim1.setVisibility(View.GONE);
                    iv_nn_anim2.setVisibility(View.GONE);
                    iv_nn_anim3.setVisibility(View.GONE);
                    this_statusd=STATUS_REST_TYPE;
                    // 开始休息时间-->
                    if (msg.arg2==9){
                        //Log.i("牛牛","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间，msg.arg2==9"+(msg.arg2==9));
                        startCountDownTime_tv_numsd(msg.arg1,1);
                    }else {
                        //Log.i("牛牛","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间，msg.arg2==9"+(msg.arg2==9));
                        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                            //Log.i("牛牛","CurLiveInfo.getHostID()");
                            startCountDownTime_tv_numsd(5,1);
                        }else {
                            //Log.i("牛牛","CurLiveInfo.getHostID()1");
                            if (instancePoker!=null&&instancePoker.isShowing()){
                                mLiveHelper.showProgressDialog("正在获取游戏状态...");
                            }
                            dicegame_state_listd(STATUS_ANIM_TYPE);
                        }
                    }
                    break;
                case MSG_CODE_END_WIN_OR_LOSE://押注结束，开始色子动画效果 请求服务器
                    //Log.i("牛牛","MSG_CODE_END_WIN_OR_LOSE——押注结束，开始色子动画效果 请求服务器");
                    if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                        dice_listsd();//如果是主播 请求发牌
                    }
                    rl_nn_anim_stake1.removeAllViews();
                    rl_nn_anim_stake2.removeAllViews();//筹码数量容器
                    rl_nn_anim_stake3.removeAllViews();

                    tv_nn_num.setText("开始");
                    rl_nn_anim_stake1.setVisibility(View.GONE);
                    rl_nn_anim_stake2.setVisibility(View.GONE);
                    rl_nn_anim_stake3.setVisibility(View.GONE);

                    rl_nn_choice1.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
                    rl_nn_choice2.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
                    rl_nn_choice3.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));

                    iv_nn_anim1.setVisibility(View.GONE);
                    iv_nn_anim2.setVisibility(View.GONE);
                    iv_nn_anim3.setVisibility(View.GONE);

                    this_statusd=STATUS_ANIM_TYPE;
                    startAnimtionsd();//开始发牌
                    //Log.i("牛牛","api="+"CurLiveInfo.getRoomNum()="+CurLiveInfo.getRoomNum());
                    break;
            }
            return false;
        }
    });
    private int[] fk=new int[]{
            R.drawable.fk1,
            R.drawable.fk2,
            R.drawable.fk3,
            R.drawable.fk4,
            R.drawable.fk5,
            R.drawable.fk6,
            R.drawable.fk7,
            R.drawable.fk8,
            R.drawable.fk9,
            R.drawable.fk10,
            R.drawable.fk11,
            R.drawable.fk12,
            R.drawable.fk13,
    };
    private int[] ht=new int[]{
            R.drawable.hei1,
            R.drawable.hei2,
            R.drawable.hei3,
            R.drawable.hei4,
            R.drawable.hei5,
            R.drawable.hei6,
            R.drawable.hei7,
            R.drawable.hei8,
            R.drawable.hei9,
            R.drawable.hei10,
            R.drawable.hei11,
            R.drawable.hei12,
            R.drawable.hei13,
    };
    private int[] hot=new int[]{
            R.drawable.ht1,
            R.drawable.ht2,
            R.drawable.ht3,
            R.drawable.ht4,
            R.drawable.ht5,
            R.drawable.ht6,
            R.drawable.ht7,
            R.drawable.ht8,
            R.drawable.ht9,
            R.drawable.ht10,
            R.drawable.ht11,
            R.drawable.ht12,
            R.drawable.ht13,
    };
    private int[] mh=new int[]{
            R.drawable.mh1,
            R.drawable.mh2,
            R.drawable.mh3,
            R.drawable.mh4,
            R.drawable.mh5,
            R.drawable.mh6,
            R.drawable.mh7,
            R.drawable.mh8,
            R.drawable.mh9,
            R.drawable.mh10,
            R.drawable.mh11,
            R.drawable.mh12,
            R.drawable.mh13,
    };
    private int[] an_n=new int[]{
            R.drawable.n,
            R.drawable.n1,
            R.drawable.n2,
            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
            R.drawable.n6,
            R.drawable.n7,
            R.drawable.n8,
            R.drawable.n9,
            R.drawable.n10,
    };

    private int[] getPoker(String s){
        switch (s){
            case "1":
                return hot;
            case "2":
                return mh;
            case "3":
                return fk;
            case "4":
                return ht;
        }
     return null;
    }
    List<PokerBean> pokerBeen;
    int[] draws=new int[4];
    private void startAnimtionsd() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.niuniu_dice_list_data(CurLiveInfo.getRoomNum(), niuniu_dice_list_data);
        }
    }

    private void startfpAnim(int[] draw) {
        iv_poker_fp.setVisibility(View.VISIBLE);
        Message msg = Message.obtain();
        msg.what=11;
        msg.obj=draw;
        hanpoker.sendMessage(msg);
    }
    private Handler hanpoker=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 11:
                    int[] location1 = new int[2];
                    iv_poker_fp.getLocationOnScreen(location1);
                    int startX1 = location1[0];
                    int startY1 = location1[1];//被按按钮相对与屏幕的坐标
                    if (instancePoker!=null&&instancePoker.isShowing()&&rl_nn_choice.getVisibility()==View.VISIBLE){
                        f1pokerz(startX1,startY1,poker_z[0],0);
                        SoundPlayUtils.play(3);//发牌声音
                    }
                    f1poker(startX1,startY1,iv_nn_poker1_1,0,ANIM_TIME_FLOP);
                    f1poker(startX1,startY1,iv_nn_poker2_1,0,ANIM_TIME_FLOP+100);
                    f1poker(startX1,startY1,iv_nn_poker3_1,11,ANIM_TIME_FLOP+200);
                    break;
                case 22:
                    int[] location2 = new int[2];
                    iv_poker_fp.getLocationOnScreen(location2);
                    int startX2 = location2[0];
                    int startY2 = location2[1];//被按按钮相对与屏幕的坐标
                    if (instancePoker!=null&&instancePoker.isShowing()&&rl_nn_choice.getVisibility()==View.VISIBLE){
                        f1pokerz(startX2,startY2,poker_z[1],0);
                        SoundPlayUtils.play(3);//发牌声音
                    }
                    f1poker(startX2,startY2,iv_nn_poker1_2,0,ANIM_TIME_FLOP);
                    f1poker(startX2,startY2,iv_nn_poker2_2,0,ANIM_TIME_FLOP+100);
                    f1poker(startX2,startY2,iv_nn_poker3_2,22,ANIM_TIME_FLOP+200);
                    break;
                case 33:
                    int[] location3 = new int[2];
                    iv_poker_fp.getLocationOnScreen(location3);
                    int startX3 = location3[0];
                    int startY3 = location3[1];//被按按钮相对与屏幕的坐标
                    if (instancePoker!=null&&instancePoker.isShowing()&&rl_nn_choice.getVisibility()==View.VISIBLE){
                        f1pokerz(startX3,startY3,poker_z[2],0);
                        SoundPlayUtils.play(3);//发牌声音
                    }
                    f1poker(startX3,startY3,iv_nn_poker1_3,0,ANIM_TIME_FLOP);
                    f1poker(startX3,startY3,iv_nn_poker2_3,0,ANIM_TIME_FLOP+100);
                    f1poker(startX3,startY3,iv_nn_poker3_3,33,ANIM_TIME_FLOP+200);
                    break;
                case 44:
                    int[] location4 = new int[2];
                    iv_poker_fp.getLocationOnScreen(location4);
                    int startX4 = location4[0];
                    int startY4 = location4[1];//被按按钮相对与屏幕的坐标
                    if (instancePoker!=null&&instancePoker.isShowing()&&rl_nn_choice.getVisibility()==View.VISIBLE){
                        f1pokerz(startX4,startY4,poker_z[3],0);
                        SoundPlayUtils.play(3);//发牌声音
                    }
                    f1poker(startX4,startY4,iv_nn_poker1_4,0,ANIM_TIME_FLOP);
                    f1poker(startX4,startY4,iv_nn_poker2_4,0,ANIM_TIME_FLOP+100);
                    f1poker(startX4,startY4,iv_nn_poker3_4,44,ANIM_TIME_FLOP+200);
                    break;
                case 55:
                    int[] location5 = new int[2];
                    iv_poker_fp.getLocationOnScreen(location5);
                    int startX5 = location5[0];
                    int startY5 = location5[1];//被按按钮相对与屏幕的坐标
                    if (instancePoker!=null&&instancePoker.isShowing()&&rl_nn_choice.getVisibility()==View.VISIBLE){
                        f1pokerz(startX5,startY5,poker_z[4],0);
                        SoundPlayUtils.play(3);//发牌声音
                    }
                    f1poker(startX5,startY5,iv_nn_poker1_5,0,ANIM_TIME_FLOP);
                    f1poker(startX5,startY5,iv_nn_poker2_5,0,ANIM_TIME_FLOP+100);
                    f1poker(startX5,startY5,iv_nn_poker3_5,55,ANIM_TIME_FLOP+200);
                    break;
                case 66:
                    ObjectAnimator circle_anim=ObjectAnimator.ofFloat(iv_nn_poker1_5, "rotationY", 0,89);
                    circle_anim.setDuration(500);
                    circle_anim.start();
                    circle_anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            iv_nn_poker1_5.setImageDrawable(LiveingActivity.this.getDrawable(draws[1]));
                            ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(iv_nn_poker1_5, "rotationY", 90,0);
                            circle_anim1.setDuration(500);
                            circle_anim1.start();
                            circle_anim1.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    //显示是否有牛动画
                                    Animation Animation = (Animation) AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.anim_round_rotate);
                                    ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(rl_anim_win1, "alpha", 0.0f,1.0f);
                                    ObjectAnimator circle_animX=ObjectAnimator.ofFloat(rl_anim_win1, "scaleX", 0.0f,1.0f);
                                    ObjectAnimator circle_animY=ObjectAnimator.ofFloat(rl_anim_win1, "scaleY", 0.0f,1.0f);
                                    AnimatorSet animset=new AnimatorSet();
                                    animset.play(circle_anim1).with(circle_animX).with(circle_animY);

                                    String point_sum = pokerBeen.get(1).getPoint_sum();
                                    int i = Integer.parseInt(point_sum);
                                    rl_anim_win1.setBackground(LiveingActivity.this.getDrawable(an_n[i]));
                                    animset.setDuration(ANIM_TIME_IS_NN).start();

                                    Message msg = Message.obtain();
                                    msg.what=77;
                                    hanpoker.sendMessage(msg);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    break;
                case 77:

                    ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(iv_nn_poker2_5, "rotationY", 0,89);
                    circle_anim1.setDuration(500);
                    circle_anim1.start();
                    circle_anim1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            iv_nn_poker2_5.setImageDrawable(LiveingActivity.this.getDrawable(draws[2]));
                            ObjectAnimator circle_anim11=ObjectAnimator.ofFloat(iv_nn_poker2_5, "rotationY", 90,0);
                            circle_anim11.setDuration(500);
                            circle_anim11.start();
                            circle_anim11.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    //显示是否有牛动画
                                    ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(rl_anim_win2, "alpha", 0.0f,1.0f);

                                    ObjectAnimator circle_animX=ObjectAnimator.ofFloat(rl_anim_win2, "scaleX", 0.0f,1.0f);
                                    ObjectAnimator circle_animY=ObjectAnimator.ofFloat(rl_anim_win2, "scaleY", 0.0f,1.0f);
                                    AnimatorSet animset=new AnimatorSet();
                                    animset.play(circle_anim1).with(circle_animX).with(circle_animY);

                                    String point_sum = pokerBeen.get(2).getPoint_sum();
                                    int i = Integer.parseInt(point_sum);
                                    rl_anim_win2.setBackground(LiveingActivity.this.getDrawable(an_n[i]));
                                    animset.setDuration(ANIM_TIME_IS_NN).start();

                                    Message msg = Message.obtain();
                                    msg.what=88;
                                    hanpoker.sendMessage(msg);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    break;
                case 88:
                    ObjectAnimator circle_anim2=ObjectAnimator.ofFloat(iv_nn_poker3_5, "rotationY", 0,89);
                    circle_anim2.setDuration(500);
                    circle_anim2.start();
                    circle_anim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            iv_nn_poker3_5.setImageDrawable(LiveingActivity.this.getDrawable(draws[3]));
                            ObjectAnimator circle_anim11=ObjectAnimator.ofFloat(iv_nn_poker3_5, "rotationY", 90,0);
                            circle_anim11.setDuration(500);
                            circle_anim11.start();
                            circle_anim11.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    //显示是否有牛动画
                                    ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(rl_anim_win3, "alpha", 0.0f,1.0f);

                                    ObjectAnimator circle_animX=ObjectAnimator.ofFloat(rl_anim_win3, "scaleX", 0.0f,1.0f);
                                    ObjectAnimator circle_animY=ObjectAnimator.ofFloat(rl_anim_win3, "scaleY", 0.0f,1.0f);
                                    AnimatorSet animset=new AnimatorSet();
                                    animset.play(circle_anim1).with(circle_animX).with(circle_animY);

                                    String point_sum = pokerBeen.get(3).getPoint_sum();
                                    int i = Integer.parseInt(point_sum);
                                    rl_anim_win3.setBackground(LiveingActivity.this.getDrawable(an_n[i]));
                                    animset.setDuration(ANIM_TIME_IS_NN).start();

                                    Message msg = Message.obtain();
                                    msg.what=99;
                                    hanpoker.sendMessage(msg);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    break;
                case 99:
                    ObjectAnimator circle_anim3=ObjectAnimator.ofFloat(poker_z[4], "rotationY", 0,89);
                    circle_anim3.setDuration(500);
                    circle_anim3.start();
                    circle_anim3.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            poker_z[4].setImageDrawable(LiveingActivity.this.getDrawable(draws[0]));
                            ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(poker_z[4], "rotationY", 90,0);
                            circle_anim1.setDuration(500);
                            circle_anim1.start();
                            circle_anim1.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    //显示是否有牛动画
                                    ObjectAnimator circle_anim1=ObjectAnimator.ofFloat(rl_anim_win, "alpha", 0.0f,1.0f);

                                    ObjectAnimator circle_animX=ObjectAnimator.ofFloat(rl_anim_win, "scaleX", 0.0f,1.0f);
                                    ObjectAnimator circle_animY=ObjectAnimator.ofFloat(rl_anim_win, "scaleY", 0.0f,1.0f);
                                    AnimatorSet animset=new AnimatorSet();
                                    animset.play(circle_anim1).with(circle_animX).with(circle_animY);

                                    String point_sum = pokerBeen.get(0).getPoint_sum();
                                    int i = Integer.parseInt(point_sum);
                                    rl_anim_win.setBackground(LiveingActivity.this.getDrawable(an_n[i]));
                                    animset.setDuration(ANIM_TIME_IS_NN).start();
                                    animset.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            Message msg = Message.obtain();
                                            msg.what=MSG_CODE_END_REST;
                                            getAnimIntegrals();
                                            dice_poker_handler.sendMessageDelayed(msg,3000);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    });
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    break;
            }
        }
    };

    private void f1poker(int startX, int startY, final ImageView toiv, final int type,int time) {

        int width = iv_nn_poker1_3.getWidth();
        int height = iv_nn_poker1_3.getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/2, height/2);
        final TextView textView = new TextView(LiveingActivity.this);
        textView.setBackground(LiveingActivity.this.getDrawable(R.drawable.pokerbj));
        textView.setLayoutParams(params);
        int heightPixels = layout.getResources().getDisplayMetrics().heightPixels;
        textView.setX(startX);//牌出现的位置
        textView.setY((int)(heightPixels*0.40-(heightPixels-startY)+1));
        int[] location1 = new int[2];
        toiv.getLocationOnScreen(location1);
        int stopX = location1[0];
        int stopY = location1[1];//控件相对与屏幕的坐标
        //int y = (int) (heightPixels * 0.40 - (heightPixels - stopY) + 1);
        rl_nn_anims.addView(textView);//把牌添加到容器中
        //TranslateAnimation animation=new TranslateAnimation(0,stopX-startX,0,stopY-startY);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                // 移动
                ObjectAnimator.ofFloat(textView, "translationX", startX, stopX-startX+30),
                ObjectAnimator.ofFloat(textView, "translationY", (int)(heightPixels*0.40-(heightPixels-startY)+1), stopY-startY+20));

        set.setDuration(time).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rl_nn_anims.removeView(textView);
                toiv.setVisibility(View.VISIBLE);//显示牌面
                switch (type){
                    case 11:
                        poker_z[0].setVisibility(View.VISIBLE);
                        Message msg1 = Message.obtain();
                        msg1.what=22;
                        hanpoker.sendMessage(msg1);
                        break;
                    case 22:
                        poker_z[1].setVisibility(View.VISIBLE);
                        Message msg2 = Message.obtain();
                        msg2.what=33;
                        hanpoker.sendMessage(msg2);
                        break;
                    case 33:
                        poker_z[2].setVisibility(View.VISIBLE);
                        Message msg3 = Message.obtain();
                        msg3.what=44;
                        hanpoker.sendMessage(msg3);
                        break;
                    case 44:
                        poker_z[3].setVisibility(View.VISIBLE);
                        Message msg4 = Message.obtain();
                        msg4.what=55;
                        hanpoker.sendMessage(msg4);
                        break;
                    case 55:
                        poker_z[4].setVisibility(View.VISIBLE);
                        Message msg5 = Message.obtain();
                        msg5.what=66;
                        hanpoker.sendMessage(msg5);
                        break;
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
    private void f1Num(String var,int time) {
        int[] location1 = new int[2];
        tv_nn_price.getLocationOnScreen(location1);
        int stopX = location1[0];
        int stopY = location1[1];//控件相对与屏幕的坐标
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(LiveingActivity.this);
        textView.setLayoutParams(params);
        int heightPixels = layout.getResources().getDisplayMetrics().heightPixels;
        textView.setX(stopX);//牌出现的位置
        textView.setY((int)(heightPixels*0.40-(heightPixels-stopY)+1));
        textView.setTextColor(Color.rgb(0xff,0xba,0x15));
        SpannableString spanString = new SpannableString(var);
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        spanString.setSpan(boldStyle, 0, var.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(var);
        rl_nn_anims.addView(textView);//把牌添加到容器中
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(textView, "scaleX", 0.0f,1.5f))
                .with(ObjectAnimator.ofFloat(textView, "scaleY", 0.0f,1.5f))
                .with(ObjectAnimator.ofFloat(textView, "translationY", (int)(heightPixels*0.40-(heightPixels-stopY)), (int)(heightPixels*0.40-(heightPixels-stopY))-100));

        set.setDuration(time).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rl_nn_anims.removeView(textView);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
    private void f1pokerz(int startX, int startY, final ImageView toiv, final int type) {

        int width = iv_nn_poker1_3.getWidth();
        int height = iv_nn_poker1_3.getHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/2, height/2);
        final TextView textView = new TextView(LiveingActivity.this);
        textView.setBackground(LiveingActivity.this.getDrawable(R.drawable.pokerbj));
        textView.setLayoutParams(params);
        textView.setX(startX);//牌出现的位置
        textView.setY(startY);
        int[] location1 = new int[2];
        toiv.getLocationOnScreen(location1);
        int stopX = location1[0];
        int stopY = location1[1];//控件相对与屏幕的坐标
        //int y = (int) (heightPixels * 0.40 - (heightPixels - stopY) + 1);
        rl_anim.addView(textView);//把牌添加到容器中
        //TranslateAnimation animation=new TranslateAnimation(0,stopX-startX,0,stopY-startY);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                // 移动
                ObjectAnimator.ofFloat(textView, "translationX", startX, stopX-startX+30),
                ObjectAnimator.ofFloat(textView, "translationY", startY, -(stopY-startY)-100));

        set.setDuration(500).start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rl_anim.removeView(textView);
                toiv.setVisibility(View.VISIBLE);//显示牌面
                switch (type){
                    case 11:
                        poker_z[0].setVisibility(View.VISIBLE);
                        Message msg1 = Message.obtain();
                        msg1.what=22;
                        hanpoker.sendMessage(msg1);
                        break;
                    case 22:
                        poker_z[1].setVisibility(View.VISIBLE);
                        Message msg2 = Message.obtain();
                        msg2.what=33;
                        hanpoker.sendMessage(msg2);
                        break;
                    case 33:
                        poker_z[2].setVisibility(View.VISIBLE);
                        Message msg3 = Message.obtain();
                        msg3.what=44;
                        hanpoker.sendMessage(msg3);
                        break;
                    case 44:
                        poker_z[3].setVisibility(View.VISIBLE);
                        Message msg4 = Message.obtain();
                        msg4.what=55;
                        hanpoker.sendMessage(msg4);
                        break;
                    case 55:
                        poker_z[4].setVisibility(View.VISIBLE);
                        Message msg5 = Message.obtain();
                        msg5.what=66;
                        hanpoker.sendMessage(msg5);
                        break;
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void setDrawPokers(Map<Integer, String[]> chip_ch,ImageView[] poker_num) {
        if (chip_ch!=null&&chip_ch.size()>1){
            for (int i = 0; i < chip_ch.size(); i++) {
                if (chip_ch.size()-1==i){
                    poker_num[i].setImageDrawable(LiveingActivity.this.getDrawable(R.drawable.pokerbj));
                    poker_num[i].setVisibility(View.INVISIBLE);
                }else {
                    String[] poker1 = chip_ch.get(i);

                    int[] poker = getPoker(poker1[1]);//获取相应花色
                    int draw = poker[Integer.parseInt(poker1[0]) - 1];//获取相应的牌的资源

                    poker_num[i].setImageDrawable(LiveingActivity.this.getDrawable(draw));
                    poker_num[i].setVisibility(View.INVISIBLE);
                }

            }

        }
    }

    private void dice_shangs() {
            if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
                ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
                return;
            }else {
                Api.niuniu_dice_shang(CurLiveInfo.getRoomNum(), niuniu_dice_shang);
            }
    }

    private void dice_listsd() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.niuniu_dice_list(CurLiveInfo.getRoomNum(), niuniu_dice_list);
        }
    }

    //private StatusBean statusBean;

    private void dicegame_state_listd(final int type) {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.niuniu_state_list(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("牛牛","获取游戏状态api="+apiResponse.toString());
                    mLiveHelper.hideProgressDialog();
                    if (apiResponse.getCode()==Api.SUCCESS){
                        String data = apiResponse.getData();
                        statusBean = JSON.parseObject(data, StatusBean.class);
                        if (String.valueOf(type).equals(statusBean.getGame_state())&&isTrue){
                            dicegame_state_listd(type);
                            return;
                        }
                        if ("2".equals(statusBean.getGame_state())){//动画状态 或结束状态
                            //显示动画
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_WIN_OR_LOSE;
                            msg.arg2=9;
                            dice_poker_handler.sendMessage(msg);
                        }else if("3".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_GIFT;
                            msg.arg2=9;
                            dice_poker_handler.sendMessage(msg);
                        }else if("0".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_REST;
                            msg.arg1=Integer.parseInt(statusBean.getRest_count_down());
                            msg.arg2=9;
                            dice_poker_handler.sendMessage(msg);
                        }else if ("1".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_YAZU;
                            msg.arg1=Integer.parseInt(statusBean.getBet_count_down());
                            msg.arg2=9;
                            dice_poker_handler.sendMessage(msg);
                        }
                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    dicegame_state_listd(10000);
                }
            });
        }
    }

    View layout;
    private void showGameNN() {
        if (instancePoker==null){
            layout = LayoutInflater.from(LiveingActivity.this).inflate(R.layout.dialog_game_nn, null);
            instancePoker = PokerGameDialog.getInstance(LiveingActivity.this);
            instancePoker.initDialog(layout);
            initViewsPoker(layout);
            instancePoker.setContentView(layout);
            instancePoker.setCancelable(false);
            instancePoker.show();
            getIntegralsd();//显示积分
            WindowManager.LayoutParams params = instancePoker.getWindow().getAttributes();// 得到属性
            params.gravity = Gravity.BOTTOM;// 显示在底部
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            rl_nn_choice.setVisibility(View.VISIBLE);
            instancePoker.getWindow().setAttributes(params);// 设置属性

            if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                Poker_Host_handler.postDelayed(Poker_Runnable,1000);
                Message msg = Message.obtain();
                msg.what=MSG_CODE_END_REST;
                dice_poker_handler.sendMessage(msg);
            }else {
                if (instancePoker!=null&&instancePoker.isShowing()){
                    mLiveHelper.showProgressDialog("正在获取游戏状态...");
                }
                dicegame_state_listd(10000);//获取游戏状态
            }
        }else {
            instancePoker.show();
            rl_nn_choice.setVisibility(View.VISIBLE);
        }
    }
    private void showGameNNs() {
        if (instancePoker==null){
            layout = LayoutInflater.from(LiveingActivity.this).inflate(R.layout.dialog_game_nn, null);
            instancePoker = PokerGameDialog.getInstance(LiveingActivity.this);
            instancePoker.initDialog(layout);
            initViewsPoker(layout);
            instancePoker.setContentView(layout);
            instancePoker.setCancelable(false);
            getIntegralsd();//显示积分
            WindowManager.LayoutParams params = instancePoker.getWindow().getAttributes();// 得到属性
            params.gravity = Gravity.BOTTOM;// 显示在底部
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            rl_nn_choice.setVisibility(View.GONE);
            instancePoker.getWindow().setAttributes(params);// 设置属性

            if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                Poker_Host_handler.postDelayed(Poker_Runnable,1000);
                Message msg = Message.obtain();
                msg.what=MSG_CODE_END_REST;
                dice_poker_handler.sendMessage(msg);
            }else {
                if (instancePoker!=null&&instancePoker.isShowing()){
                    mLiveHelper.showProgressDialog("正在获取游戏状态...");
                }
                dicegame_state_listd(10000);//获取游戏状态
            }
        }else {
            instancePoker.show();
            rl_nn_choice.setVisibility(View.VISIBLE);
        }
    }

    private void getIntegralsd(){
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.getIntegral(LoginManager.getInstance().getUserID(LiveingActivity.this),
                    new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("游戏1","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("integral");
                                //Log.i("游戏1","integral:"+diamonds_coins);
                                if (tv_nn_price!=null){
                                    tv_nn_price.setText(diamonds_coins);
                                }
                            }
                        }
                    });
        }

    }
    private void getAnimIntegralsd(){
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.getIntegral(LoginManager.getInstance().getUserID(LiveingActivity.this),
                    new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("游戏1","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("integral");
                                //Log.i("游戏1","integral:"+diamonds_coins);
                                Float v = Float.parseFloat(diamonds_coins);
                                String s = tv_price.getText().toString();
                                Float v1=0f;
                                if (!"".equals(s)){
                                     v1=Float.parseFloat(s);
                                }
                                int vs= (int) (v-v1);//变化量
                                if (vs>0){
                                    f1Num1("+"+String.valueOf(vs),2000);
                                }else if (vs<0){
                                    f1Num1(String.valueOf(vs),2000);

                                }
                                ValueAnimator anim=null;
                                if (vs>0){
                                    anim=ValueAnimator.ofFloat(v1,v);
                                }else {
                                    anim=ValueAnimator.ofFloat(v,v1);
                                }
                                anim.setDuration(2000);
                                anim.start();
                                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float currentValue = (float) animation.getAnimatedValue();
                                        int integer =Math.round(currentValue);
                                        tv_price.setText(String.valueOf((double) integer));
                                    }
                                });
                            }
                        }
                    });
        }

    }
    private void getAnimIntegrals(){
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.getIntegral(LoginManager.getInstance().getUserID(LiveingActivity.this),
                    new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("游戏1","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("integral");
                                //Log.i("游戏1","integral:"+diamonds_coins);
                                Float v = Float.parseFloat(diamonds_coins);
                                Float v1 =Float.parseFloat(tv_nn_price.getText().toString());
                                int vs= (int) (v-v1);//变化量
                                if (vs>0){

                                    f1Num("+"+String.valueOf(vs),2000);
                                }else if (vs<0){
                                    f1Num(String.valueOf(vs),2000);

                                }
                                ValueAnimator anim=null;
                                if (vs>0){
                                    anim=ValueAnimator.ofFloat(v1,v);
                                }else {
                                    anim=ValueAnimator.ofFloat(v,v1);
                                }
                                anim.setDuration(2000);
                                anim.start();
                                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float currentValue = (float) animation.getAnimatedValue();
                                        int integer =Math.round(currentValue);
                                        tv_nn_price.setText(String.valueOf((double) integer));
                                    }
                                });
                            }
                        }
                    });
        }

    }
    private TextView tv_nn_num;//倒计时
    private RelativeLayout tv_nn_back;//退出键
    private TextView tv_nn_stake_num1,tv_nn_stake_num2,tv_nn_stake_num3;//自己押注数量
    private TextView tv_nn_stake_bet_total_num_1,tv_nn_stake_bet_total_num_2,tv_nn_stake_bet_total_num_3;//自己押注数量
    private TextView iv_nn_anim1,iv_nn_anim2,iv_nn_anim3;//押注状态图片
//    private TextView iv_nn_win1,iv_nn_win2,iv_nn_win3;//输赢动画
    private RelativeLayout rl_nn_anim_stake1,rl_nn_anim_stake2,rl_nn_anim_stake3;//点击押注时随机筹码的容器
    private ImageView iv_nn_poker1_1,iv_nn_poker1_2,iv_nn_poker1_3,iv_nn_poker1_4,iv_nn_poker1_5;//扑克
    private ImageView iv_nn_poker2_1,iv_nn_poker2_2,iv_nn_poker2_3,iv_nn_poker2_4,iv_nn_poker2_5;//扑克
    private ImageView iv_nn_poker3_1,iv_nn_poker3_2,iv_nn_poker3_3,iv_nn_poker3_4,iv_nn_poker3_5;//扑克
    private ImageView poker_one[]=new ImageView[5];
    private ImageView poker_two[]=new ImageView[5];
    private ImageView poker_three[]=new ImageView[5];
    private TextView tv_nn_price,tv_nn_recharge;//积分数量和充值
    private RelativeLayout rl_nn_chip10,rl_nn_chip25,rl_nn_chip50,rl_nn_chip100;//筹码的点击事件容器
    private BlurMaskFilterView iv_nn_chip10,iv_nn_chip25,iv_nn_chip50,iv_nn_chip100;//筹码控件
    private RelativeLayout rl_nn_anims;//牛牛动画容器
    private RelativeLayout rl_nn_choice1,rl_nn_choice2,rl_nn_choice3;
    private ImageView iv_poker_fp;

    private int bl_choice1s=0;//选择的状态 0：没有选择任何一个盘 1：1盘 2：2盘 3：3盘
    private int bl_choice_status=0;//点击的状态 0：没有点击任何一个盘 1：1盘 2：2盘 3：3盘
    private int first_p=0,doubles_p=0,third_p=0;//自己向盘子里添加的积分数量
    private RelativeLayout rl_anim_win1,rl_anim_win2,rl_anim_win3;
    private static final int ANIM_TIME_IS_NN=1000;//是否有牛动画时长
    private static final int ANIM_TIME_FLOP=500;//翻牌动画时长
    private static final int ANIM_TIME_LICENCE=1000;//发牌动画时长

    private void initViewsPoker(View layout) {
        tv_nn_num=layout.findViewById(R.id.tv_nn_num);
        tv_nn_back=layout.findViewById(R.id.tv_nn_back);
        iv_poker_fp=layout.findViewById(R.id.iv_poker_fp);

        tv_nn_stake_num1=layout.findViewById(R.id.tv_nn_stake_num1);
        tv_nn_stake_num2=layout.findViewById(R.id.tv_nn_stake_num2);
        tv_nn_stake_num3=layout.findViewById(R.id.tv_nn_stake_num3);

        rl_anim_win1=layout.findViewById(R.id.rl_anim_win1);
        rl_anim_win2=layout.findViewById(R.id.rl_anim_win2);
        rl_anim_win3=layout.findViewById(R.id.rl_anim_win3);

        tv_nn_stake_bet_total_num_1=layout.findViewById(R.id.tv_nn_stake_bet_total_num_1);
        tv_nn_stake_bet_total_num_2=layout.findViewById(R.id.tv_nn_stake_bet_total_num_2);
        tv_nn_stake_bet_total_num_3=layout.findViewById(R.id.tv_nn_stake_bet_total_num_3);

        iv_nn_anim1=layout.findViewById(R.id.iv_nn_anim1);
        iv_nn_anim2=layout.findViewById(R.id.iv_nn_anim2);
        iv_nn_anim3=layout.findViewById(R.id.iv_nn_anim3);

        rl_nn_anim_stake1=layout.findViewById(R.id.rl_nn_anim_stake1);
        rl_nn_anim_stake2=layout.findViewById(R.id.rl_nn_anim_stake2);
        rl_nn_anim_stake3=layout.findViewById(R.id.rl_nn_anim_stake3);

        iv_nn_poker1_1=layout.findViewById(R.id.iv_nn_poker1_1);
        iv_nn_poker1_2=layout.findViewById(R.id.iv_nn_poker1_2);
        iv_nn_poker1_3=layout.findViewById(R.id.iv_nn_poker1_3);
        iv_nn_poker1_4=layout.findViewById(R.id.iv_nn_poker1_4);
        iv_nn_poker1_5=layout.findViewById(R.id.iv_nn_poker1_5);
        poker_one[0]=iv_nn_poker1_1;
        poker_one[1]=iv_nn_poker1_2;
        poker_one[2]=iv_nn_poker1_3;
        poker_one[3]=iv_nn_poker1_4;
        poker_one[4]=iv_nn_poker1_5;
        iv_nn_poker2_1=layout.findViewById(R.id.iv_nn_poker2_1);
        iv_nn_poker2_2=layout.findViewById(R.id.iv_nn_poker2_2);
        iv_nn_poker2_3=layout.findViewById(R.id.iv_nn_poker2_3);
        iv_nn_poker2_4=layout.findViewById(R.id.iv_nn_poker2_4);
        iv_nn_poker2_5=layout.findViewById(R.id.iv_nn_poker2_5);
        poker_two[0]=iv_nn_poker2_1;
        poker_two[1]=iv_nn_poker2_2;
        poker_two[2]=iv_nn_poker2_3;
        poker_two[3]=iv_nn_poker2_4;
        poker_two[4]=iv_nn_poker2_5;
        iv_nn_poker3_1=layout.findViewById(R.id.iv_nn_poker3_1);
        iv_nn_poker3_2=layout.findViewById(R.id.iv_nn_poker3_2);
        iv_nn_poker3_3=layout.findViewById(R.id.iv_nn_poker3_3);
        iv_nn_poker3_4=layout.findViewById(R.id.iv_nn_poker3_4);
        iv_nn_poker3_5=layout.findViewById(R.id.iv_nn_poker3_5);
        poker_three[0]=iv_nn_poker3_1;
        poker_three[1]=iv_nn_poker3_2;
        poker_three[2]=iv_nn_poker3_3;
        poker_three[3]=iv_nn_poker3_4;
        poker_three[4]=iv_nn_poker3_5;
        tv_nn_price=layout.findViewById(R.id.tv_nn_price);
        tv_nn_recharge=layout.findViewById(R.id.tv_nn_recharge);

        rl_nn_chip10=layout.findViewById(R.id.rl_nn_chip10);
        rl_nn_chip25=layout.findViewById(R.id.rl_nn_chip25);
        rl_nn_chip50=layout.findViewById(R.id.rl_nn_chip50);
        rl_nn_chip100=layout.findViewById(R.id.rl_nn_chip100);

        iv_nn_chip10=layout.findViewById(R.id.iv_nn_chip10);
        iv_nn_chip25=layout.findViewById(R.id.iv_nn_chip25);
        iv_nn_chip50=layout.findViewById(R.id.iv_nn_chip50);
        iv_nn_chip100=layout.findViewById(R.id.iv_nn_chip100);

        rl_nn_anims=layout.findViewById(R.id.rl_nn_anims);

        rl_nn_choice1=layout.findViewById(R.id.rl_nn_choice1);
        rl_nn_choice2=layout.findViewById(R.id.rl_nn_choice2);
        rl_nn_choice3=layout.findViewById(R.id.rl_nn_choice3);

        initEventsd();
    }
    private OnClicks os;
    private void initEventsd() {
        os = new OnClicks();
        tv_nn_back.setOnClickListener(os);
        tv_nn_recharge.setOnClickListener(os);
        rl_nn_chip10.setOnClickListener(os);
        rl_nn_chip25.setOnClickListener(os);
        rl_nn_chip50.setOnClickListener(os);
        rl_nn_chip100.setOnClickListener(os);
        rl_nn_choice1.setOnClickListener(os);
        rl_nn_choice2.setOnClickListener(os);
        rl_nn_choice3.setOnClickListener(os);
    }
    private void showDialog() {
        Dialog alertDialog = new AlertDialog.Builder(LiveingActivity.this).
                setTitle("提示").
                setMessage("当前积分余额不足,需兑换积分才能继续押注,是否去充值?").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sendPres = new Intent(LiveingActivity.this, ExchangeActivity.class);
                        startActivity(sendPres);
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                create();

        alertDialog.show();
    }
    private class OnClicks implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_nn_back://退出
                    if (instancePoker!=null&&instancePoker.isShowing()){
                        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                        instancePoker.hide();
                        rl_nn_choice.setVisibility(View.GONE);
                        }else {
                            rl_nn_choice.setVisibility(View.GONE);
                            instancePoker.dismiss();
                            if (instancePoker!=null){
                                instancePoker.onDesPlay();
                                if (timers!=null){
                                    timers.cancel();
                                    timers=null;
                                }
                                instancePoker=null;
                            }
                        }
                        iv_popularity.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tv_nn_recharge://充值
                    startActivity(new Intent(LiveingActivity.this, ExchangeActivity.class));
                    break;
                case R.id.rl_nn_chip10://向盘里添加10积分
                    if(this_statusd==STATUS_BET_TYPE){//quota=0,actual=0;
                        bl_choice1s=1;
                        betsd();
                        iv_nn_chip10.setImageAlpha(255);
                        //rl_nn_chip10.setBackground(LiveingActivity.this.getDrawable(R.drawable.games_bgs1));
                    }
                    break;
                case R.id.rl_nn_chip25://向盘里添加50积分
                    bl_choice1s=2;
                    if(this_statusd==STATUS_BET_TYPE){
                        betsd();
                        iv_nn_chip25.setImageAlpha(255);
                        //rl_nn_chip25.setBackground(LiveingActivity.this.getDrawable(R.drawable.games_bgs1));
                    }
                    break;
                case R.id.rl_nn_chip50://向盘里添加100积分
                    bl_choice1s=3;
                    if(this_statusd==STATUS_BET_TYPE){
                        betsd();
                        iv_nn_chip50.setImageAlpha(255);
                        //rl_nn_chip50.setBackground(LiveingActivity.this.getDrawable(R.drawable.games_bgs1));
                    }
                    break;
                case R.id.rl_nn_chip100://向盘里添加100积分
                    bl_choice1s=4;
                    if(this_statusd==STATUS_BET_TYPE){
                        betsd();
                        iv_nn_chip100.setImageAlpha(255);
                        //rl_nn_chip100.setBackground(LiveingActivity.this.getDrawable(R.drawable.games_bgs1));
                    }
                    break;
                case R.id.rl_nn_choice1://选择第1个
                    if(this_statusd==STATUS_BET_TYPE){
                        bl_choice_status=1;
                        setDice();

                    }
                    break;
                case R.id.rl_nn_choice2://选择第2个
                    if(this_statusd==STATUS_BET_TYPE){
                        bl_choice_status=2;
                        setDice();
                    }
                    break;
                case R.id.rl_nn_choice3://选择第3个
                    if(this_statusd==STATUS_BET_TYPE){
                        bl_choice_status=3;
                        setDice();
                    }
                    break;
            }

        }
    }

    private void setDice() {
        iv_nn_anim1.setVisibility(View.GONE);
        iv_nn_anim2.setVisibility(View.GONE);
        iv_nn_anim3.setVisibility(View.GONE);
            int i = choice1(bl_choice1s);
            if(this_statusd==STATUS_BET_TYPE){
                if (quota<i){
                    showDialog();
                }else {
                    if (i==0){
                        ToastUtils.showShort(LiveingActivity.this, "请选择押注的筹码");
                    }else {
                        quota=quota-i;
                        double v = Double.parseDouble(tv_nn_price.getText().toString());
                        v=v-i;
                        tv_nn_price.setText(String.valueOf(v));
                        betServices(bl_choice_status,""+i,getChipDraws(bl_choice1s), rl_nn_anims, getDraw(bl_choice1s), i);
                    }
                }
            }


    }

    private BlurMaskFilterView getChipDraws(int i) {
        switch (i){
            case 1:
                return iv_nn_chip10;
            case 2:
                return iv_nn_chip25;
            case 3:
                return iv_nn_chip50;
            case 4:
                return iv_nn_chip100;
        }
        return null;

    }
    private ImageView getChipDiceDraws(int i) {
        switch (i){
            case 1:
                return iv_chip10;
            case 2:
                return iv_chip25;
            case 3:
                return iv_chip50;
            case 4:
                return iv_chip100;
        }
        return null;

    }

    private int getDraw(int i) {
        switch (i){
            case 1:
                return R.drawable.chip10;
            case 2:
                return R.drawable.chip25;
            case 3:
                return R.drawable.chip50;
            case 4:
                return R.drawable.chip100;
        }
        return 0;
    }

    private int choice1(int i) {
        switch (i){
            case 1:
                return 10;
            case 2:
                return 50;
            case 3:
                return 100;
            case 4:
                return 500;
        }
        return 0;
    }

    private void betServices(final int bl_choice_status, final String bet_money, final BlurMaskFilterView iv_chip10, final RelativeLayout rl_anims, final int chip10, final int i) {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            giftsd(bl_choice_status,iv_chip10, rl_anims, bet_money, chip10, i);
            SoundPlayUtils.play(1);//下注声音
            Api.niuniu_user_integral_reduce(LoginManager.getInstance().getUserID(LiveingActivity.this)
                    , bet_money
                    , CurLiveInfo.getRoomNum()
                    , String.valueOf(bl_choice_status), new ApiResponseHandler(LiveingActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("游戏1","下注-数据="+apiResponse.toString()+",bet_money="+bet_money);
                            if (apiResponse.getCode()==Api.SUCCESS){
//                                giftsd(bl_choice_status,iv_chip10, rl_anims, bet_money, chip10, i);
//                                SoundPlayUtils.play(1);//下注声音
                            }else {
                                ToastUtils.showShort(LiveingActivity.this,"下注失败");
                            }
                        }

                        @Override
                        public void onFailure(String errMessage) {
                            super.onFailure(errMessage);
                            ToastUtils.showShort(LiveingActivity.this,"连接服务器失败");
                        }
                    });
        }
    }

    private void giftsd(int bl_choice_status,BlurMaskFilterView iv_chip10, RelativeLayout rl_anims, String string, int chip10, int i) {
//        if (View.GONE == iv_nn_win1.getVisibility()) {
            switch (bl_choice_status) {
                case 1:
                    stakeGifts(new TextView(LiveingActivity.this), iv_chip10, rl_nn_anim_stake1, rl_anims, string, chip10);//添加textView；
                    break;
                case 2:
                    stakeGifts(new TextView(LiveingActivity.this), iv_chip10, rl_nn_anim_stake2, rl_anims, string, chip10);//添加textView；
                    break;
                case 3:
                    stakeGifts(new TextView(LiveingActivity.this), iv_chip10, rl_nn_anim_stake3, rl_anims, string, chip10);//添加textView；
                    break;
            }
            addIntegrals(bl_choice_status,i);
//        } else {
//            ToastUtils.showShort(LiveingActivity.this, "请选择押注的盘口");
//        }
    }

    private void stakeGifts(final TextView textView
            , BlurMaskFilterView imageView, RelativeLayout rl_anim_stake
            , final RelativeLayout rl_anims,String string,int draw) {

        int width = imageView.getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/3*4, width/3*4);
        int width1 = rl_anim_stake.getWidth();//父容器的宽
        int height1 = rl_anim_stake.getHeight();//父容器的高
        Random random =new Random();//随机
        if (width1==0||height1==0){
            return;
        }
        int x = random.nextInt(width1-50);
        int y = random.nextInt(height1-50);
        textView.setLayoutParams(params);
        textView.setBackground(LiveingActivity.this.getDrawable(draw));
        textView.setX(x);
        textView.setY(y);
        textView.setVisibility(View.INVISIBLE);
        rl_anim_stake.addView(textView);
        final TextView textView1 = new TextView(LiveingActivity.this);


        textView1.setLayoutParams(params);
        textView1.setBackground(LiveingActivity.this.getDrawable(draw));
        int[] location1 = new int[2];
        imageView.getLocationOnScreen(location1);
        int x1 = location1[0];
        int y1 = location1[1];//被按按钮相对与屏幕的坐标


        int[] location2 = new int[2];//随机出现的textview 相对与屏幕的位置
        textView.getLocationOnScreen(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        int heightPixels = layout.getResources().getDisplayMetrics().heightPixels;
        textView1.setX(x2);
        textView1.setY((int)(heightPixels*0.40-(heightPixels-y2)+1));

        TranslateAnimation animation=new TranslateAnimation(x1-x2,0,y1-y2,0);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(500);
        rl_anims.addView(textView1);
        textView1.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_anims.removeView(textView1);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        switch (bl_choice1s){
            case 1:
                break;
            case 2:
                rl_nn_anim_stake2.setVisibility(View.VISIBLE);
                break;
            case 3:
                rl_nn_anim_stake3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void addIntegrals(int bl_choice_status,int i) {
        switch (bl_choice_status){
            case 0:
                ToastUtils.showShort(LiveingActivity.this,"请选择押注的筹码");
                break;
            case 1:
                first_p=first_p+i;
                tv_nn_stake_num1.setText(""+first_p);
                break;
            case 2:
                doubles_p=doubles_p+i;
                tv_nn_stake_num2.setText(""+doubles_p);
                break;
            case 3:
                third_p=third_p+i;
                tv_nn_stake_num3.setText(""+third_p);
                break;
        }
    }

    /**
     * 影藏押注图片
     */
    private void betsd() {
        iv_nn_anim1.setVisibility(View.GONE);
        iv_nn_anim2.setVisibility(View.GONE);
        iv_nn_anim3.setVisibility(View.GONE);
//        rl_nn_choice1.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
//        rl_nn_choice2.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
//        rl_nn_choice3.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
        iv_nn_chip10.setImageAlpha(128);
        iv_nn_chip25.setImageAlpha(128);
        iv_nn_chip50.setImageAlpha(128);
        iv_nn_chip100.setImageAlpha(128);
    }

    /**
     *  倒计时器 根据
     * @param time 秒数
     * @param type 0 押注倒计时 1 休息倒计时
     */
    CountDownTimer timers;
    private int ls=0;
    private void startCountDownTime_tv_numsd(long time, final int type) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        timers = new CountDownTimer(time * 1000+1050, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法

                if (!LiveingActivity.this.isFinishing()){
                    ls = (int) (millisUntilFinished / 1000 - 1);
                    if (type==0){
                        tv_nn_num.setText("押注时间"+ls+"秒");
                        if (ls%2==0){
                            //Log.i("牛牛","下注心跳——"+ls+"s");
                            heartBeats();
                        }
                    }else if (type==1){
                        tv_nn_num.setText("休息"+ls+"秒");
                        //Log.i("牛牛","休息时间——"+ls+"s");
                    }
                    if (ls<=1){
                        rl_nn_chip10.setEnabled(false);
                        rl_nn_chip25.setEnabled(false);
                        rl_nn_chip50.setEnabled(false);
                        rl_nn_chip100.setEnabled(false);
                    }
                }

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish -- 倒计时结束");
                if (!LiveingActivity.this.isFinishing()){

                    if (type==0){

                        //Log.i("牛牛","押注倒计时结束");
                        Message msg = Message.obtain();
                        msg.what=MSG_CODE_END_WIN_OR_LOSE;
                        dice_poker_handler.sendMessage(msg);

                    }else if (type==1){
                        //Log.i("牛牛","休息倒计时结束");
                        Message msg = Message.obtain();
                        msg.what=MSG_CODE_END_YAZU;
                        dice_poker_handler.sendMessage(msg);

                    }
                    rl_nn_chip10.setEnabled(true);
                    rl_nn_chip25.setEnabled(true);
                    rl_nn_chip50.setEnabled(true);
                    rl_nn_chip100.setEnabled(true);
                }
                timers.cancel();
                //封盘 开始旋转动画
            }
        };
        timers.start();// 开始计时
        //timer.cancel(); // 取消
    }
    List<ChipBeatBean> lists=new ArrayList<>();
    private void heartBeats() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.niuniu_user_integral_heartbeat(CurLiveInfo.getRoomNum(), niuniu_user_integral_heartbeat);
        }
    }

    private void stakeGiftsd(RelativeLayout rl_anim_stake, int i2) {
        iv_nn_anim1.setVisibility(View.GONE);
        iv_nn_anim2.setVisibility(View.GONE);
        iv_nn_anim3.setVisibility(View.GONE);
        if(i2>=500){
            for (int i=0;i<i2/500;i++){
                addTexts(rl_anim_stake,R.drawable.chip100,"500");
            }
        }else if (i2>=100){
            for (int i=0;i<i2/100;i++){
                addTexts(rl_anim_stake,R.drawable.chip50,"100");
            }
        }else if(i2>=50){
            for (int i=0;i<i2/50;i++){
                addTexts(rl_anim_stake,R.drawable.chip25,"50");
            }
        }else if (i2>=10){
            for (int i=0;i<i2/10;i++){
                addTexts(rl_anim_stake,R.drawable.chip10,"10");
            }
        }
    }
    private Random random =new Random();//随机
    private void addTexts(RelativeLayout rl_anim_stake,int draw,String str) {
        TextView textView=new TextView(LiveingActivity.this);
        int width = iv_nn_chip100.getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/3*4, width/3*4);
        int width1 = rl_anim_stake.getWidth();//父容器的宽
        int height1 = rl_anim_stake.getHeight();//父容器的高
        if (width1>0&&height1>0){
            int x = random.nextInt(width1-50);
            int y = random.nextInt(height1-50);
            textView.setLayoutParams(params);
            textView.setBackground(LiveingActivity.this.getDrawable(draw));
            textView.setX(x);
            textView.setY(y);
            rl_anim_stake.addView(textView);
        }
    }

    //------------------------------------------------------------


    /**
     * 色子游戏 dialog
     */
    private DiceGameDialog instance;
    private TextView /*倒计时*/tv_num,/*自己的积分*/tv_price,/*充值*/tv_recharge;
    private RelativeLayout tv_back;/*后退键*/
    private TextView /*第1个盘的押注数*/tv_stake_num1,/*第2个盘的押注数*/tv_stake_num2,/*第3个盘的押注数*/tv_stake_num3;
    private TextView /*第1个盘的押注数*/tv_stake_bet_total_num_1,/*第2个盘的押注数*/tv_stake_bet_total_num_2,/*第3个盘的押注数*/tv_stake_bet_total_num_3;
    private TextView /*游戏押注状态图片*/iv_anim1,iv_anim2,iv_anim3;
    private TextView /*输赢显示控件*/iv_win1,iv_win2,iv_win3;
    private TextView /*最终点数显示*/tv_point,tv_point1,tv_point2,tv_point3;
    private RelativeLayout /*积分点击事件*/rl_chip10,rl_chip25,rl_chip50,rl_chip100;
    private RelativeLayout /*选择某一个盘押注*/rl_choice1,rl_choice2,rl_choice3;
    private ImageView iv_chip10,iv_chip25,iv_chip50,iv_chip100;//动画控件
    private ImageView dice1,dice2,dice3,dice1_1,dice2_1,dice3_1,dice1_2,dice2_2,dice3_2,dice1_3,dice2_3,dice3_3;//动画控件
    private RelativeLayout rl_status,rl_status1,rl_status2,rl_status3;//色子控件父容器
    private RelativeLayout rl_dice;//色子控件父容器

    private OnClick o;
    private int first=0,doubles=0,third=0;//自己向盘子里添加的积分数量
    private int bl_choice1=0;//选择的状态 0：没有选择任何一个盘 1：1盘 2：2盘 3：3盘
    private int is_dice_num=0;
    private int bl_chip_status=0;//点击的状态
    private RelativeLayout rl_anim_stake1,rl_anim_stake2,rl_anim_stake3;//盘里增加积分时 添加图片的父容器
    private RelativeLayout rl_anims;
    private View view;

    private void showGames() {
        if (instance==null){
            view = LayoutInflater.from(this).inflate(R.layout.dialog_game_dice, null);
            instance = DiceGameDialog.getInstance(this);
            instance.initDialog(view);
            initViews(view);
            instance.setContentView(view);
            instance.setCancelable(false);
            instance.show();
            rl_dice.setVisibility(View.VISIBLE);
            getIntegrals();//显示积分
            WindowManager.LayoutParams params = instance.getWindow().getAttributes();// 得到属性
            params.gravity = Gravity.BOTTOM;// 显示在底部
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            instance.getWindow().setAttributes(params);// 设置属性

//            WindowManager windowManager =this.getWindowManager();
//            Display display = windowManager.getDefaultDisplay();
//            WindowManager.LayoutParams lp = instance.getWindow().getAttributes();
//            lp.width = (int)(display.getWidth()); //设置宽度
//            instance.getWindow().setAttributes(lp);
            if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                game_handler.postDelayed(Host_Runnable,1000);
                Message msg = Message.obtain();
                msg.what=MSG_CODE_END_REST;
                dice_handler.sendMessage(msg);
            }else {
                //Log.i("色子","观众方式打开游戏");
                if (instance!=null&&instance.isShowing()){
                    mLiveHelper.showProgressDialog("正在获取游戏状态...");
                }
                dicegame_state_list(10000);
            }
        }else {
            instance.show();
            rl_dice.setVisibility(View.VISIBLE);
        }

    }

    private void initViewdd() {
        tv_point= (TextView) findViewById(R.id.tv_point);
        dice1= (ImageView) findViewById(R.id.dice1);
        dice2= (ImageView) findViewById(R.id.dice2);
        dice3= (ImageView) findViewById(R.id.dice3);
        rl_status= (RelativeLayout) findViewById(R.id.rl_status);
        rl_dice= (RelativeLayout) findViewById(R.id.rl_dice);
        rl_dice.setVisibility(View.GONE);
    }

    private void getIntegrals(){
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }else {
            Api.getIntegral(LoginManager.getInstance().getUserID(this),
                    new ApiResponseHandler(this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            //Log.i("积分","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("integral");
                                //Log.i("积分","integral:"+diamonds_coins);
                                if (tv_price!=null){
                                    tv_price.setText(diamonds_coins);
                                }
                            }
                        }
                    });
        }

    }


    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_back://退出
                    if (instance!=null&&instance.isShowing()){
                        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                            instance.hide();
                            rl_dice.setVisibility(View.GONE);
                        }else {
                            instance.dismiss();
                            rl_dice.setVisibility(View.GONE);
                            if (instance!=null){
                                if (timer!=null){
                                    timer.cancel();
                                }
                                instance.onDesPlay();
                                instance=null;
                            }
                        }
                        iv_popularity.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.tv_recharge://充值
                    startActivity(new Intent(LiveingActivity.this, ExchangeActivity.class));
                    break;
                case R.id.rl_chip10://向盘里添加10积分
                    if(this_status==STATUS_BET_TYPE){
                        bl_chip_status=1;
                        bets();
                        iv_chip10.setImageAlpha(255);
                    }
                    break;
                case R.id.rl_chip25://向盘里添加25积分
                    if(this_status==STATUS_BET_TYPE){
                        bl_chip_status=2;
                        bets();
                        iv_chip25.setImageAlpha(255);
                    }
                    break;
                case R.id.rl_chip50://向盘里添加50积分
                    if(this_status==STATUS_BET_TYPE){
                        bl_chip_status=3;
                        bets();
                        iv_chip50.setImageAlpha(255);
                    }
                    break;
                case R.id.rl_chip100://向盘里添加100积分
                    if(this_status==STATUS_BET_TYPE){
                        bl_chip_status=4;
                        bets();
                        iv_chip100.setImageAlpha(255);
                    }
                    break;
                case R.id.rl_choice1://选择第1个
                    if(this_status==STATUS_BET_TYPE){
                        bl_choice1=1;
                        iv_anim1.setVisibility(View.GONE);
                        iv_anim2.setVisibility(View.GONE);
                        iv_anim3.setVisibility(View.GONE);
                        if (!(is_dice_num>choice1(bl_chip_status))){
                            showDialog();
                        }else {
                            if (bl_chip_status==0){
                                ToastUtils.showShort(LiveingActivity.this, "请选择押注的筹码");
                            }else {
                                is_dice_num=is_dice_num-choice1(bl_chip_status);
                                tv_price.setText(String.valueOf(is_dice_num));
                                betService(""+choice1(bl_chip_status),getChipDiceDraws(bl_chip_status), rl_anims, getDraw(bl_chip_status), choice1(bl_chip_status));
                            }
                        }


                    }
                    break;
                case R.id.rl_choice2://选择第2个
                    if(this_status==STATUS_BET_TYPE){
                        bl_choice1=2;
                        iv_anim1.setVisibility(View.GONE);
                        iv_anim2.setVisibility(View.GONE);
                        iv_anim3.setVisibility(View.GONE);
                        if (!(is_dice_num>choice1(bl_chip_status))){
                            showDialog();
                        }else {
                            if (bl_chip_status==0){
                                ToastUtils.showShort(LiveingActivity.this, "请选择押注的筹码");
                            }else {
                                is_dice_num=is_dice_num-choice1(bl_chip_status);
                                tv_price.setText(String.valueOf(is_dice_num));
                                betService(""+choice1(bl_chip_status),getChipDiceDraws(bl_chip_status), rl_anims, getDraw(bl_chip_status), choice1(bl_chip_status));
                            }
                        }
                    }
                    break;
                case R.id.rl_choice3://选择第3个
                    if(this_status==STATUS_BET_TYPE){
                        bl_choice1=3;
                        iv_anim1.setVisibility(View.GONE);
                        iv_anim2.setVisibility(View.GONE);
                        iv_anim3.setVisibility(View.GONE);
                        if (!(is_dice_num>choice1(bl_chip_status))){
                            showDialog();
                        }else {
                            if (bl_chip_status==0){
                                ToastUtils.showShort(LiveingActivity.this, "请选择押注的筹码");
                            }else {
                                is_dice_num=is_dice_num-choice1(bl_chip_status);
                                tv_price.setText(String.valueOf(is_dice_num));
                                betService(""+choice1(bl_chip_status),getChipDiceDraws(bl_chip_status), rl_anims,getDraw(bl_chip_status), choice1(bl_chip_status));
                            }
                        }
                    }
                    break;
            }

        }
    }


    private void betService(final String bet_money, final ImageView iv_chip10, final RelativeLayout rl_anims, final int chip10, final int i) {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            gifts(iv_chip10, rl_anims, chip10, i);
            SoundPlayUtils.play(1);
            Api.dice_user_integral_reduce(LoginManager.getInstance().getUserID(LiveingActivity.this)
                    , bet_money
                    , CurLiveInfo.getRoomNum()
                    , String.valueOf(bl_choice1), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("游戏","下注-数据="+apiResponse.toString()+",bet_money="+bet_money);
                    if (apiResponse.getCode()==Api.SUCCESS){

                    }else {
                        ToastUtils.showShort(LiveingActivity.this,"下注失败");
                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(LiveingActivity.this,"连接服务器失败");
                }
            });
        }
    }

    private void gifts(ImageView iv_chip10, RelativeLayout rl_anims, int chip10, int i) {
        if (View.GONE == iv_win1.getVisibility()) {
            switch (bl_choice1) {
                case 1:
                    stakeGift(new TextView(LiveingActivity.this), iv_chip10, rl_anim_stake1, rl_anims, chip10);//添加textView；
                    break;
                case 2:
                    stakeGift(new TextView(LiveingActivity.this), iv_chip10, rl_anim_stake2, rl_anims, chip10);//添加textView；
                    break;
                case 3:
                    stakeGift(new TextView(LiveingActivity.this), iv_chip10, rl_anim_stake3, rl_anims,  chip10);//添加textView；
                    break;
            }
            addIntegral(i);
        } else {
            ToastUtils.showShort(LiveingActivity.this, "请选择押注的盘口");
        }
    }

    private void stakeGift(final TextView textView, ImageView imageView, RelativeLayout rl_anim_stake, final RelativeLayout rl_anims,int draw) {

        int width = imageView.getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/3*4, width/3*4);
        int width1 = rl_anim_stake.getWidth();//父容器的宽
        int height1 = rl_anim_stake.getHeight();//父容器的高
        Random random =new Random();//随机
        int x = random.nextInt(width1-40);
        int y = random.nextInt(height1-40);
        textView.setLayoutParams(params);
        textView.setBackground(LiveingActivity.this.getDrawable(draw));

        textView.setX(x);
        textView.setY(y);
        textView.setVisibility(View.INVISIBLE);
        rl_anim_stake.addView(textView);
        final TextView textView1 = new TextView(LiveingActivity.this);


        textView1.setLayoutParams(params);
        textView1.setBackground(LiveingActivity.this.getDrawable(draw));
        int[] location1 = new int[2];
        imageView.getLocationOnScreen(location1);
        int x1 = location1[0];
        int y1 = location1[1];//被按按钮相对与屏幕的坐标


        int[] location2 = new int[2];//随机出现的textview 相对与屏幕的位置
        textView.getLocationOnScreen(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        int heightPixels = view.getResources().getDisplayMetrics().heightPixels;
        textView1.setX(x2);
        textView1.setY((int)(heightPixels*0.40-(heightPixels-y2)+1));

        TranslateAnimation animation=new TranslateAnimation(x1-x2,0,y1-y2,0);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(500);
        rl_anims.addView(textView1);
        textView1.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_anims.removeView(textView1);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        switch (bl_choice1){
            case 1:
                break;
            case 2:
                rl_anim_stake2.setVisibility(View.VISIBLE);
                break;
            case 3:
                rl_anim_stake3.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 影藏押注图片
     */
    private void bets() {
        iv_anim1.setVisibility(View.GONE);
        iv_anim2.setVisibility(View.GONE);
        iv_anim3.setVisibility(View.GONE);

        iv_chip10.setImageAlpha(128);
        iv_chip25.setImageAlpha(128);
        iv_chip50.setImageAlpha(128);
        iv_chip100.setImageAlpha(128);
    }

    private void addIntegral(int i) {
        switch (bl_choice1){
            case 0:
                ToastUtils.showShort(LiveingActivity.this,"请选择押注的盘口");
                break;
            case 1:
                first=first+i;

                tv_stake_num1.setText(""+first);
                break;
            case 2:
                doubles=doubles+i;
                tv_stake_num2.setText(""+doubles);
                break;
            case 3:
                third=third+i;
                tv_stake_num3.setText(""+third);
                break;
        }
    }

    public static final int STATUS_REST_TYPE=0;//休息状态5
    public static final int STATUS_BET_TYPE=1;//下注状态30
    public static final int STATUS_ANIM_TYPE=2;//动画状态10
    public static final int STATUS_END_TYPE=3;//结束状态10
    private int this_status;//当前游戏状态
    private int this_statusd;//当前游戏状态

    private static final int MSG_CODE_END_GIFT=0x1111;//色子动画结束，开始显示输赢结果和各盘点数
    private static final int MSG_CODE_END_YAZU=0x2222;//休息结束，开始显示押注
    private static final int MSG_CODE_END_REST=0x3333;//输赢动画显示结束，开始休息时间
    private static final int MSG_CODE_END_WIN_OR_LOSE=0x4444;//押注结束，开始色子动画效果


    private Handler game_handler=new Handler();

    private boolean bool=true;
    private Runnable stake_Runnable=new Runnable() {//押注状态心跳
        @Override
        public void run() {
            if (this_status==STATUS_BET_TYPE&&!isStop==true&&bool){
                //请求服务器 根据返回值设置动画效果
                //heartbeat();
                game_handler.postDelayed(stake_Runnable,2000);//延迟1秒执行
            }
        }
    };
    List<ChipBeatBean> list=new ArrayList<>();
    private void heartBeat() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.user_integral_heartbeat(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("色子","下注心跳回调apiResponse="+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        list.clear();
                        String data = apiResponse.getData();
                        list.addAll(JSONArray.parseArray(data, ChipBeatBean.class));
                        for (int i = list.size() - 1; i >= 0; i--) {
                            ChipBeatBean chipBeatBean = list.get(i);
                            if (chipBeatBean.getTable_number().equals("1")){
                                String string = tv_stake_bet_total_num_1.getText().toString();
                                int i1 = Integer.parseInt(string);
                                if (chipBeatBean.getBet_money_sum()>i1){
                                    int i2 = chipBeatBean.getBet_money_sum() - i1;
                                    //向1号池添加筹码
                                    stakeGifts(rl_anim_stake1,i2);//添加textView；

                                    tv_stake_bet_total_num_1.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                                }
                            }else if (chipBeatBean.getTable_number().equals("2")){
                                String string = tv_stake_bet_total_num_2.getText().toString();
                                int i1 = Integer.parseInt(string);
                                if (chipBeatBean.getBet_money_sum()>i1){
                                    int i2 = chipBeatBean.getBet_money_sum() - i1;
                                    //向2号池添加筹码
                                    stakeGifts(rl_anim_stake2,i2);
                                    tv_stake_bet_total_num_2.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                                }
                            }else if (chipBeatBean.getTable_number().equals("3")){
                                String string = tv_stake_bet_total_num_3.getText().toString();
                                int i1 = Integer.parseInt(string);
                                if (chipBeatBean.getBet_money_sum()>i1){
                                    int i2 = chipBeatBean.getBet_money_sum() - i1;
                                    //向3号池添加筹码
                                    stakeGifts(rl_anim_stake3,i2);
                                    tv_stake_bet_total_num_3.setText(String.valueOf(chipBeatBean.getBet_money_sum()));
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void stakeGifts(RelativeLayout rl_anim_stake, int i2) {
        iv_anim1.setVisibility(View.GONE);
        iv_anim2.setVisibility(View.GONE);
        iv_anim3.setVisibility(View.GONE);
        if(i2>=500){
            for (int i=0;i<i2/500;i++){
                addText(rl_anim_stake,R.drawable.chip100,"500");
            }
        }else if (i2>=100){
            for (int i=0;i<i2/100;i++){
                addText(rl_anim_stake,R.drawable.chip50,"100");
            }
        }else if(i2>=50){
            for (int i=0;i<i2/50;i++){
                addText(rl_anim_stake,R.drawable.chip25,"50");
            }
        }else if (i2>=10){
            for (int i=0;i<i2/10;i++){
                addText(rl_anim_stake,R.drawable.chip10,"10");
            }
        }
    }

    private void addText(RelativeLayout rl_anim_stake,int draw,String str) {
        TextView textView=new TextView(LiveingActivity.this);
        int width = iv_chip100.getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width/3*4, width/3*4);
        int width1 = rl_anim_stake.getWidth();//父容器的宽
        int height1 = rl_anim_stake.getHeight();//父容器的高
        int x =0;
        int y=0;
        if (width1>0&&height1>0){
            x = random.nextInt(width1-20);
            y = random.nextInt(height1-20);
            textView.setLayoutParams(params);
            textView.setBackground(LiveingActivity.this.getDrawable(draw));
            textView.setX(x);
            textView.setY(y);
            rl_anim_stake.addView(textView);
        }
    }

    private Runnable Host_Runnable=new Runnable() {
        @Override
        public void run() {
            if(!isStop==true){
                //请求服务器 上传自己状态
                dicegame_state_add();
                game_handler.postDelayed(Host_Runnable,2000);//主播游戏状态心跳
            }

        }


    };
    private StatusBean statusBean;

    private void dicegame_state_list(final int type) {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            //Log.i("色子","观众方式打开游戏--执行获取状态前");
            Api.dicegame_state_list(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    mLiveHelper.hideProgressDialog();
                    //Log.i("色子","回调获取游戏状态api="+apiResponse.toString());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        String data = apiResponse.getData();
                        statusBean = JSON.parseObject(data, StatusBean.class);
                        if (String.valueOf(type).equals(statusBean.getGame_state())&&isTrue){
                            dicegame_state_list(type);
                            return;
                        }
                        if ("2".equals(statusBean.getGame_state())){//动画状态 或结束状态
                            //显示动画
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_WIN_OR_LOSE;
                            msg.arg2=9;
                            msgs=0;
                            dice_handler.sendMessage(msg);
                        }else if("3".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_GIFT;
                            msgs=4;
                            msg.arg2=9;
                            dice_handler.sendMessage(msg);
                        }else if("0".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_REST;
                            msg.arg1=Integer.parseInt(statusBean.getRest_count_down());
                            msg.arg2=9;
                            dice_handler.sendMessage(msg);
                        }else if ("1".equals(statusBean.getGame_state())){
                            Message msg = Message.obtain();
                            msg.what=MSG_CODE_END_YAZU;
                            msg.arg1=Integer.parseInt(statusBean.getBet_count_down());
                            msg.arg2=9;
                            dice_handler.sendMessage(msg);
                        }


                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    //Log.i("色子","失败回调="+errMessage);
                    dicegame_state_list(1000);
                }
            });
        }
    }
    private void dicegame_state_add() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.dicegame_state_add(CurLiveInfo.getRoomNum(), this_status, String.valueOf(l),dicegame_state_add);
        }

    }

    int msgs=0;
    private Handler dice_handler=new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_CODE_END_GIFT://色子动画结束，开始显示输赢结果和各盘点数
                    if (msg.arg1==0){
                        msgs++;
                    }
                    if (msgs==4){
                        this_status=STATUS_END_TYPE;
                        //Log.i("色子","MSG_CODE_END_GIFT——色子动画结束，开始显示输赢结果和各盘点数");
                        tv_point1.setVisibility(View.VISIBLE);
                        tv_point2.setVisibility(View.VISIBLE);
                        tv_point3.setVisibility(View.VISIBLE);
                        tv_point.setVisibility(View.VISIBLE);
                        dice_list_data();
                        //输赢动画结束 开始下一轮
                    }
                    break;
                case MSG_CODE_END_YAZU://休息结束，开始显示押注
                    this_status=STATUS_BET_TYPE;
                    iv_chip10.setImageAlpha(128);
                    iv_chip25.setImageAlpha(128);
                    iv_chip50.setImageAlpha(128);
                    iv_chip100.setImageAlpha(128);
                    String s = tv_price.getText().toString();
                    if (!"".equals(s)){
                        is_dice_num= (int) Double.parseDouble(s);//计算本次可押注金额
                    }
                    if (is_dice_num>=10){
                        iv_chip10.setImageAlpha(255);
                        bl_chip_status=1;
                    }else {
                        bl_chip_status=0;
                    }
                    //Log.i("色子","STATUS_BET_TYPE——休息结束，开始显示押注");
                    rl_anim_stake1.removeAllViews();
                    rl_anim_stake2.removeAllViews();//色子数量容器
                    rl_anim_stake3.removeAllViews();
                    bool=true;
                    //game_handler.post(stake_Runnable);//心跳开始
                    rl_anim_stake1.setVisibility(View.VISIBLE);
                    rl_anim_stake2.setVisibility(View.VISIBLE);
                    rl_anim_stake3.setVisibility(View.VISIBLE);
                    iv_anim1.setVisibility(View.VISIBLE);
                    iv_anim2.setVisibility(View.VISIBLE);
                    iv_anim3.setVisibility(View.VISIBLE);
                    if (msg.arg2==9){
                        startCountDownTime_tv_num(msg.arg1,0);
                    }else {
                        startCountDownTime_tv_num(30,0);
                    }
                    break;
                case MSG_CODE_END_REST://输赢动画显示结束，开始休息时间
                    //Log.i("色子","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间");
                    if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                        dice_shang();
                    }
                    this_status=STATUS_REST_TYPE;
                    isAnim_dice=0;
                    getIntegrals();
                    msgs=0;
                    tv_stake_bet_total_num_1.setText("0");
                    tv_stake_bet_total_num_2.setText("0");
                    tv_stake_bet_total_num_3.setText("0");

                    iv_win1.setVisibility(View.GONE);
                    iv_win2.setVisibility(View.GONE);
                    iv_win3.setVisibility(View.GONE);

                    tv_point1.setText("");
                    tv_point2.setText("");
                    tv_point3.setText("");

                    tv_point1.setVisibility(View.GONE);
                    tv_point2.setVisibility(View.GONE);
                    tv_point3.setVisibility(View.GONE);

                    first=0;
                    doubles=0;
                    third=0;
                    bl_choice1=0;

                    tv_stake_num1.setText("0");
                    tv_stake_num2.setText("0");
                    tv_stake_num3.setText("0");

                    rl_status1.setVisibility(View.GONE);
                    rl_status2.setVisibility(View.GONE);
                    rl_status3.setVisibility(View.GONE);

                    iv_anim1.setVisibility(View.GONE);
                    iv_anim2.setVisibility(View.GONE);
                    iv_anim3.setVisibility(View.GONE);
                    // 开始休息时间-->
                    if (msg.arg2==9){
                        //Log.i("色子","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间，msg.arg2==9"+(msg.arg2==9));
                        startCountDownTime_tv_num(msg.arg1,1);
                    }else {
                        //Log.i("色子","MSG_CODE_END_REST——输赢动画显示结束，开始休息时间，msg.arg2==9"+(msg.arg2==9));
                        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                            //Log.i("色子","CurLiveInfo.getHostID()");
                            startCountDownTime_tv_num(5,1);
                        }else {
                            //Log.i("色子","CurLiveInfo.getHostID()1");
                            if (instance!=null&&instance.isShowing()){
                                mLiveHelper.showProgressDialog("正在获取游戏状态...");
                            }
                            dicegame_state_list(STATUS_END_TYPE);
                        }
                    }
                    break;
                case MSG_CODE_END_WIN_OR_LOSE://押注结束，开始色子动画效果 请求服务器
                    //Log.i("色子","MSG_CODE_END_WIN_OR_LOSE——押注结束，开始色子动画效果 请求服务器");
                    this_status=STATUS_ANIM_TYPE;
                    rl_anim_stake1.removeAllViews();
                    rl_anim_stake2.removeAllViews();//色子数量容器
                    rl_anim_stake3.removeAllViews();

                    tv_num.setText("开始");
                    rl_anim_stake1.setVisibility(View.GONE);
                    rl_anim_stake2.setVisibility(View.GONE);
                    rl_anim_stake3.setVisibility(View.GONE);

                    rl_choice1.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
                    rl_choice2.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));
                    rl_choice3.setBackground(LiveingActivity.this.getDrawable(R.drawable.game_bgs));

                    iv_anim1.setVisibility(View.GONE);
                    iv_anim2.setVisibility(View.GONE);
                    iv_anim3.setVisibility(View.GONE);
                    if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                        dice_list();//如果是主播 请求摇塞子
                    }else {
                        //CountDownTimer(10);
                        startAnimtion();
                        //Log.i("色子","api="+"CurLiveInfo.getRoomNum()="+CurLiveInfo.getRoomNum());
                    }
                    break;
            }
            return false;
        }
    });
    private List<Integer> dice=new ArrayList<>();
    private int[] draw=new int[]{R.drawable.dice_1,R.drawable.dice_2,R.drawable.dice_3,R.drawable.dice_4,R.drawable.dice_5,R.drawable.dice_6};
    private void dice_list_data() {
        winList.clear();
        Api.dice_list_data(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //Log.i("色子","输赢显示api="+apiResponse.toString());
                if (apiResponse.getCode()==Api.SUCCESS){
                    List<DiceBean> dicelist = JSONArray.parseArray(apiResponse.getData(), DiceBean.class);

                    dice.clear();
                    DiceBean diceBean = dicelist.get(0);// 1号桌
                    //Log.i("色子","diceBean1="+ diceBean.toString());
                    String[] chip_ch = diceBean.getChip_ch();

                    for (int i = 0; i < chip_ch.length; i++) {
                        if (chip_ch[i].length()>1){
                            chip_ch[i]=String.valueOf(chip_ch[i].charAt(chip_ch[i].length()-1));
                        }
                        dice.add(Integer.parseInt(chip_ch[i]));
                        //Log.i("色子","庄家桌点数="+Integer.parseInt(chip_ch[i]));
                    }
                    tv_point.setText(String.valueOf(dice.get(0) + dice.get(1) + dice.get(2))+"点");
                    dice1.setBackgroundResource(draw[dice.get(0)-1]);
                    dice2.setBackgroundResource(draw[dice.get(1)-1]);
                    dice3.setBackgroundResource(draw[dice.get(2)-1]);
                    //Log.i("色子","庄家桌点数dice="+ dice.get(0) + dice.get(1) + dice.get(2));

                    dice.clear();
                    DiceBean diceBean1 = dicelist.get(1);// 1号桌
                    //Log.i("色子","diceBean1="+ diceBean1.toString());
                    String[] chip_ch1 = diceBean1.getChip_ch();
                    for (int i = 0; i < chip_ch1.length; i++) {
                        if (chip_ch1[i].length() > 1) {
                            chip_ch1[i] = String.valueOf(chip_ch1[i].charAt(chip_ch1[i].length() - 1));
                        }
                    }
                    for (int i = 0; i < chip_ch1.length; i++) {
                        dice.add(Integer.parseInt(chip_ch1[i]));
                        //Log.i("色子","一号桌点数="+Integer.parseInt(chip_ch1[i]));
                    }
                    if ("闲赢".equals(diceBean1.getWinorlose())){
                        winList.add(iv_win1);
                        iv_win1.setVisibility(View.VISIBLE);
                    }
                    tv_point1.setText(String.valueOf(dice.get(0) + dice.get(1) + dice.get(2))+"点");
                    dice1_1.setBackgroundResource(draw[dice.get(0)-1]);
                    dice2_1.setBackgroundResource(draw[dice.get(1)-1]);
                    dice3_1.setBackgroundResource(draw[dice.get(2)-1]);
                    //Log.i("色子","一号桌dice="+ dice.get(0) + dice.get(1) + dice.get(2));

                    dice.clear();
                    DiceBean diceBean2 = dicelist.get(2);// 2号桌
                    //Log.i("色子","diceBean2="+ diceBean2.toString());
                    String[] chip_ch2 = diceBean2.getChip_ch();
                    for (int i = 0; i < chip_ch2.length; i++) {
                        if (chip_ch2[i].length()>1){
                            chip_ch2[i]=String.valueOf(chip_ch2[i].charAt(chip_ch2[i].length()-1));
                        }
                        dice.add(Integer.parseInt(chip_ch2[i]));
                        //Log.i("色子","2号桌点数="+Integer.parseInt(chip_ch2[i]));
                    }
                    if ("闲赢".equals(diceBean2.getWinorlose())){
                        winList.add(iv_win2);
                        iv_win2.setVisibility(View.VISIBLE);
                    }
                    tv_point2.setText(String.valueOf(dice.get(0) + dice.get(1) + dice.get(2))+"点");
                    dice1_2.setBackgroundResource(draw[dice.get(0)-1]);
                    dice2_2.setBackgroundResource(draw[dice.get(1)-1]);
                    dice3_2.setBackgroundResource(draw[dice.get(2)-1]);
                    //Log.i("色子","2号桌dice="+ dice.get(0) + dice.get(1) + dice.get(2));

                    dice.clear();
                    DiceBean diceBean3 = dicelist.get(3);// 2号桌
                    //Log.i("色子","diceBean3="+ diceBean3.toString());
                    String[] chip_ch3 = diceBean3.getChip_ch();
                    for (int i = 0; i < chip_ch3.length; i++) {
                        if (chip_ch3[i].length()>1){
                            chip_ch3[i]=String.valueOf(chip_ch3[i].charAt(chip_ch3[i].length()-1));
                        }
                        dice.add(Integer.parseInt(chip_ch3[i]));
                        //Log.i("色子","3号桌点数="+Integer.parseInt(chip_ch3[i]));
                    }
                    if ("闲赢".equals(diceBean3.getWinorlose())){
                        winList.add(iv_win3);
                        iv_win3.setVisibility(View.VISIBLE);
                    }
                    tv_point3.setText(String.valueOf(dice.get(0) + dice.get(1) + dice.get(2))+"点");
                    dice1_3.setBackgroundResource(draw[dice.get(0)-1]);
                    dice2_3.setBackgroundResource(draw[dice.get(1)-1]);
                    dice3_3.setBackgroundResource(draw[dice.get(2)-1]);
                    //Log.i("色子","3号桌dice="+ dice.get(0) + dice.get(1) + dice.get(2));
                    //输赢动画
                    winAnim();
                }else {
                    dice_list_data();
                }

            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                dice_list_data();
            }
        });
    }
    private List<TextView> winList=new ArrayList<>();
    private int isAnim_dice=0;
    private void winAnim() {

        //Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.translate_dice1);
//        ObjectAnimator animation = ObjectAnimator.ofFloat(rl_status, "alpha",0.5f);
//        animation.setDuration(4000);
        ObjectAnimator animation;
        int size = winList.size();
        for (int i = 0; i < size; i++) {
            TextView textView = winList.get(i);
            animation = ObjectAnimator.ofFloat(textView, "alpha",0.5f,1.0f);
            animation.setDuration(4000);
//            textView.startAnimation(animation);
            animation.start();
            if (i==0){
                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        getAnimIntegralsd();
                        if (LoginManager.getInstance().getUserID(LiveingActivity.this).equals(CurLiveInfo.getHostID())){
                            isAnim_dice=1;
                            Message msg1 = Message.obtain();
                            msg1.what=MSG_CODE_END_REST;
                            dice_handler.sendMessageDelayed(msg1,2000);
                            isAnim_dice=0;
                        }else {
                            isAnim_dice=1;
                            Message msg1 = Message.obtain();
                            msg1.what=MSG_CODE_END_REST;
                            dice_handler.sendMessageDelayed(msg1,4000);
                            isAnim_dice=0;
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }

        }
        if (size==0){
            isAnim_dice=1;
            Message msg1 = Message.obtain();
            msg1.what=MSG_CODE_END_REST;
            dice_handler.sendMessageDelayed(msg1,3000);
            return;
        }


    }
    private void f1Num1(String var,int time) {
        int[] location1 = new int[2];
        tv_price.getLocationOnScreen(location1);
        int stopX = location1[0];
        int stopY = location1[1];//控件相对与屏幕的坐标
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(LiveingActivity.this);
        textView.setLayoutParams(params);
        int heightPixels = layout.getResources().getDisplayMetrics().heightPixels;
        textView.setX(stopX);//牌出现的位置
        textView.setY((int)(heightPixels*0.40-(heightPixels-stopY)+1));
        textView.setMaxLines(1);
        textView.setTextColor(Color.rgb(0xff,0xba,0x15));
        SpannableString spanString = new SpannableString(var);
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        spanString.setSpan(boldStyle, 0, var.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(var);
        rl_anims.addView(textView);//把牌添加到容器中
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(textView, "scaleX", 0.0f,1.5f))
                .with(ObjectAnimator.ofFloat(textView, "scaleY", 0.0f,1.5f))
                .with(ObjectAnimator.ofFloat(textView, "translationY", (int)(heightPixels*0.40-(heightPixels-stopY)), (int)(heightPixels*0.40-(heightPixels-stopY))-100));

        set.setDuration(time).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rl_anims.removeView(textView);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void dice_list() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.dice_list(CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("色子","请求摇色子="+apiResponse+"CurLiveInfo.getRoomNum()="+CurLiveInfo.getRoomNum());
                    if (apiResponse.getCode()==Api.SUCCESS){
                        //CountDownTimer(10);
                        startAnimtion();
                    }else {
                        if (isTrue){
                            dice_list();
                        }
                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    // dice_list();
                }
            });
        }
    }

    private void dice_shang() {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.dice_shang(CurLiveInfo.getRoomNum(), dice_shang);
        }
    }

    /**
     *  倒计时器 根据
     * @param time 秒数
     * @param type 0 押注倒计时 1 休息倒计时
     */
    CountDownTimer timer;
    private int l=0;
    private void startCountDownTime_tv_num(long time, final int type) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
         timer = new CountDownTimer(time * 1000+1050, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法

                if (!LiveingActivity.this.isFinishing()){
                    l = (int) (millisUntilFinished / 1000 - 1);
                    if (type==0){
                        tv_num.setText("押注时间"+l+"秒");
                        if (l%2==0){
                            //Log.i("色子","下注心跳——"+l+"s");
                            heartBeat();
                        }
                    }else if (type==1){
                        tv_num.setText("休息"+l+"秒");
                        //Log.i("色子","休息时间——"+l+"s");
                    }
                    if (l<=1){
                        rl_chip10.setEnabled(false);
                        rl_chip25.setEnabled(false);
                        rl_chip50.setEnabled(false);
                        rl_chip100.setEnabled(false);
                    }
                }

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish -- 倒计时结束");
                if (!LiveingActivity.this.isFinishing()){

                    if (type==0){

                        //Log.i("色子","押注倒计时结束");
                        Message msg = Message.obtain();
                        msg.what=MSG_CODE_END_WIN_OR_LOSE;
                        dice_handler.sendMessage(msg);
                        timer.cancel();
                    }else if (type==1){
                        //Log.i("色子","休息倒计时结束");
                        Message msg = Message.obtain();
                        msg.what=MSG_CODE_END_YAZU;
                        dice_handler.sendMessage(msg);
                        timer.cancel();
                    }
                    rl_chip10.setEnabled(true);
                    rl_chip25.setEnabled(true);
                    rl_chip50.setEnabled(true);
                    rl_chip100.setEnabled(true);
                }
                //封盘 开始旋转动画
            }
        };
        timer.start();// 开始计时
        //timer.cancel(); // 取消
    }
    private void CountDownTimer(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
         timer = new CountDownTimer(time * 1000+1050, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                int l = (int) (millisUntilFinished / 1000 - 1);
                //Log.i("游戏","动画 l="+l+"s");
                if (!LiveingActivity.this.isFinishing()){
                    if (isAnim_dice==1){
                        isAnim_dice=-1;
                        Message msg1 = Message.obtain();
                        msg1.what=MSG_CODE_END_REST;
                        dice_handler.sendMessageDelayed(msg1,3000);

                    }
                    if (l==0&&isAnim_dice==0){
                        isAnim_dice=2;
                    }
                    //Log.i("游戏","动画 isAnim_dice="+isAnim_dice+"s");
                }

            }

            @Override
            public void onFinish() {
                //封盘 开始旋转动画
                if (!LiveingActivity.this.isFinishing()){
                    if (isAnim_dice==2){
                        Message msg1 = Message.obtain();
                        msg1.what=MSG_CODE_END_REST;
                        dice_handler.sendMessageDelayed(msg1,2000);
                        isAnim_dice=0;
                    }
                }
            }
        };
        timer.start();// 开始计时
        //timer.cancel(); // 取消
    }
    //dice1_1,dice2_1,dice3_1,dice1_2,dice2_2,dice3_2,dice1_3,dice2_3,dice3_3
    private void startAnimtion() {
        if (this_status==STATUS_ANIM_TYPE||this_status==STATUS_END_TYPE){
            showGift();

            rl_status1.setVisibility(View.VISIBLE);
            showGift1();

            rl_status2.setVisibility(View.VISIBLE);
            showGift2();

            rl_status3.setVisibility(View.VISIBLE);
            showGift3();
        }

    }

    private void showGift() {
        //Animation animation = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.translate_dice);
        ObjectAnimator animation = ObjectAnimator.ofFloat(rl_status, "alpha",0.2f,1.0f);
        animation.setDuration(3500);
        dice1.setBackgroundResource(R.drawable.animation_dice);
        dice2.setBackgroundResource(R.drawable.animation_dice);
        dice3.setBackgroundResource(R.drawable.animation_dice);
        final AnimationDrawable background1 = (AnimationDrawable) dice1.getBackground();
        final AnimationDrawable background2 = (AnimationDrawable) dice2.getBackground();
        final AnimationDrawable background3 = (AnimationDrawable) dice3.getBackground();
        background1.start();
        background2.start();
        background3.start();
        animation.start();
        if (instance!=null&&instance.isShowing()&&rl_dice.getVisibility()==View.VISIBLE){
            SoundPlayUtils.play(2);
        }
        animation.addListener(new Animator.AnimatorListener() {
                                  @Override
                                  public void onAnimationStart(Animator animator) {

                                  }

                                  @Override
                                  public void onAnimationEnd(Animator animator) {
//显示点数 盘3
                background1.stop();
                background2.stop();
                background3.stop();
                Message msg = Message.obtain();
                msg.what=MSG_CODE_END_GIFT;
                msg.arg1=0;
                dice_handler.sendMessage(msg);
                                  }

                                  @Override
                                  public void onAnimationCancel(Animator animator) {

                                  }

                                  @Override
                                  public void onAnimationRepeat(Animator animator) {

                                  }
                              }
//                new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                SoundPlayUtils.play(2);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //显示点数 盘3
//                background1.stop();
//                background2.stop();
//                background3.stop();
//                Message msg = Message.obtain();
//                msg.what=MSG_CODE_END_GIFT;
//                msg.arg1=0;
//                dice_handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        }
        );
    }

    private void showGift3() {
        //Animation animation3 = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.translate_dice);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(rl_status3, "alpha",0.2f,1.0f);
        animation3.setDuration(3500);
        dice1_3.setBackgroundResource(R.drawable.animation_dice);
        dice2_3.setBackgroundResource(R.drawable.animation_dice);
        dice3_3.setBackgroundResource(R.drawable.animation_dice);
        final AnimationDrawable background1 = (AnimationDrawable) dice1_3.getBackground();
        final AnimationDrawable background2 = (AnimationDrawable) dice2_3.getBackground();
        final AnimationDrawable background3 = (AnimationDrawable) dice3_3.getBackground();
        background1.start();
        background2.start();
        background3.start();
        //rl_status3.startAnimation(animation3);
        animation3.start();
        animation3.addListener(new Animator.AnimatorListener() {
                                                        @Override
                                                        public void onAnimationStart(Animator animator) {

                                                        }

                                                        @Override
                                                        public void onAnimationEnd(Animator animator) {
//显示点数 盘3
                                                            background1.stop();
                                                            background2.stop();
                                                            background3.stop();
                                                            Message msg = Message.obtain();
                                                            msg.what=MSG_CODE_END_GIFT;
                                                            msg.arg1=0;
                                                            dice_handler.sendMessage(msg);
                                                        }

                                                        @Override
                                                        public void onAnimationCancel(Animator animator) {

                                                        }

                                                        @Override
                                                        public void onAnimationRepeat(Animator animator) {

                                                        }
                                                    }
//                new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //显示点数 盘3
//                background1.stop();
//                background2.stop();
//                background3.stop();
//                    Message msg = Message.obtain();
//                    msg.what=MSG_CODE_END_GIFT;
//                    msg.arg1=0;
//                    dice_handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        }
        );
    }

    private void showGift2() {
//        Animation animation2 = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.translate_dice);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(rl_status2, "alpha",0.2f,1.0f);
        animation2.setDuration(3500);
        dice1_2.setBackgroundResource(R.drawable.animation_dice);
        dice2_2.setBackgroundResource(R.drawable.animation_dice);
        dice3_2.setBackgroundResource(R.drawable.animation_dice);
        final AnimationDrawable background1 = (AnimationDrawable) dice1_2.getBackground();
        final AnimationDrawable background2 = (AnimationDrawable) dice2_2.getBackground();
        final AnimationDrawable background3 = (AnimationDrawable) dice3_2.getBackground();
        background1.start();
        background2.start();
        background3.start();
        //rl_status2.startAnimation(animation2);
        animation2.start();
        animation2.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animator) {
//显示点数 盘3
                                                background1.stop();
                                                background2.stop();
                                                background3.stop();
                                                Message msg = Message.obtain();
                                                msg.what=MSG_CODE_END_GIFT;
                                                msg.arg1=0;
                                                dice_handler.sendMessage(msg);
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animator) {

                                            }
                                        }
//                new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //显示点数 盘2
//                background1.stop();
//                background2.stop();
//                background3.stop();
//                Message msg = Message.obtain();
//                msg.what = MSG_CODE_END_GIFT;
//                msg.arg1=0;
//                dice_handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        }
        );
    }

    private void showGift1() {
        //Animation animation1 = AnimationUtils.loadAnimation(LiveingActivity.this, R.anim.translate_dice);
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(rl_status1, "alpha",0.2f,1.0f);
        animation1.setDuration(3500);
        dice1_1.setBackgroundResource(R.drawable.animation_dice);
        dice2_1.setBackgroundResource(R.drawable.animation_dice);
        dice3_1.setBackgroundResource(R.drawable.animation_dice);
        final AnimationDrawable background1 = (AnimationDrawable) dice1_1.getBackground();
        final AnimationDrawable background2 = (AnimationDrawable) dice2_1.getBackground();
        final AnimationDrawable background3 = (AnimationDrawable) dice3_1.getBackground();
        background1.start();
        background2.start();
        background3.start();
        animation1.start();
        //rl_status1.startAnimation(animation1);
        animation1.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animator) {
//显示点数 盘3
                                                background1.stop();
                                                background2.stop();
                                                background3.stop();
                                                Message msg = Message.obtain();
                                                msg.what=MSG_CODE_END_GIFT;
                                                msg.arg1=0;
                                                dice_handler.sendMessage(msg);
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animator) {

                                            }
                                        }
//                new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //显示点数 盘1
//                background1.stop();
//                background2.stop();
//                background3.stop();
//
//                Message msg = Message.obtain();
//                msg.what = MSG_CODE_END_GIFT;
//                msg.arg1=0;
//                dice_handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        }
        );
    }


    private void initViews(View view) {
        tv_num=(TextView) view.findViewById(R.id.tv_num);
        tv_back=(RelativeLayout) view.findViewById(R.id.tv_back);
        tv_price=(TextView) view.findViewById(R.id.tv_price);
        tv_recharge=(TextView) view.findViewById(R.id.tv_recharge);

        iv_anim1=(TextView) view.findViewById(R.id.iv_anim1);
        iv_anim2=(TextView) view.findViewById(R.id.iv_anim2);
        iv_anim3=(TextView) view.findViewById(R.id.iv_anim3);

        iv_win1=(TextView) view.findViewById(R.id.iv_win1);
        iv_win2=(TextView) view.findViewById(R.id.iv_win2);
        iv_win3=(TextView) view.findViewById(R.id.iv_win3);

        tv_stake_num1=(TextView) view.findViewById(R.id.tv_stake_num1);
        tv_stake_num2=(TextView) view.findViewById(R.id.tv_stake_num2);
        tv_stake_num3=(TextView) view.findViewById(R.id.tv_stake_num3);

        tv_stake_bet_total_num_1=(TextView) view.findViewById(R.id.tv_stake_bet_total_num_1);
        tv_stake_bet_total_num_2=(TextView) view.findViewById(R.id.tv_stake_bet_total_num_2);
        tv_stake_bet_total_num_3=(TextView) view.findViewById(R.id.tv_stake_bet_total_num_3);

        tv_point1=(TextView) view.findViewById(R.id.tv_point1);
        tv_point2=(TextView) view.findViewById(R.id.tv_point2);
        tv_point3=(TextView) view.findViewById(R.id.tv_point3);

        rl_chip10=(RelativeLayout) view.findViewById(R.id.rl_chip10);
        rl_chip25=(RelativeLayout) view.findViewById(R.id.rl_chip25);
        rl_chip50=(RelativeLayout) view.findViewById(R.id.rl_chip50);
        rl_chip100=(RelativeLayout) view.findViewById(R.id.rl_chip100);

        rl_chip100=(RelativeLayout) view.findViewById(R.id.rl_chip100);
        rl_chip100=(RelativeLayout) view.findViewById(R.id.rl_chip100);
        rl_chip100=(RelativeLayout) view.findViewById(R.id.rl_chip100);

        rl_status1=(RelativeLayout) view.findViewById(R.id.rl_status1);
        rl_status2=(RelativeLayout) view.findViewById(R.id.rl_status2);
        rl_status3=(RelativeLayout) view.findViewById(R.id.rl_status3);

        rl_anim_stake1=(RelativeLayout) view.findViewById(R.id.rl_anim_stake1);
        rl_anim_stake2=(RelativeLayout) view.findViewById(R.id.rl_anim_stake2);
        rl_anim_stake3=(RelativeLayout) view.findViewById(R.id.rl_anim_stake3);

        rl_anims= (RelativeLayout) view.findViewById(R.id.rl_anims);

        rl_choice1=(RelativeLayout) view.findViewById(R.id.rl_choice1);
        rl_choice2=(RelativeLayout) view.findViewById(R.id.rl_choice2);
        rl_choice3=(RelativeLayout) view.findViewById(R.id.rl_choice3);

        iv_chip10=(ImageView) view.findViewById(R.id.iv_chip10);
        iv_chip25=(ImageView) view.findViewById(R.id.iv_chip25);
        iv_chip50=(ImageView) view.findViewById(R.id.iv_chip50);
        iv_chip100=(ImageView) view.findViewById(R.id.iv_chip100);

        dice1_1=(ImageView) view.findViewById(R.id.dice1_1);
        dice2_1=(ImageView) view.findViewById(R.id.dice2_1);
        dice3_1=(ImageView) view.findViewById(R.id.dice3_1);

        dice1_2=(ImageView) view.findViewById(R.id.dice1_2);
        dice2_2=(ImageView) view.findViewById(R.id.dice2_2);
        dice3_2=(ImageView) view.findViewById(R.id.dice3_2);

        dice1_3=(ImageView) view.findViewById(R.id.dice1_3);
        dice2_3=(ImageView) view.findViewById(R.id.dice2_3);
        dice3_3=(ImageView) view.findViewById(R.id.dice3_3);

        initEvents();
    }

    private void initEvents() {
        o = new OnClick();
        tv_back.setOnClickListener(o);
        tv_recharge.setOnClickListener(o);
        rl_chip10.setOnClickListener(o);
        rl_chip25.setOnClickListener(o);
        rl_chip50.setOnClickListener(o);
        rl_chip100.setOnClickListener(o);
        rl_choice1.setOnClickListener(o);
        rl_choice2.setOnClickListener(o);
        rl_choice3.setOnClickListener(o);

    }

































    private void sendGift() {
        dialog1 = new Dialog(this, R.style.dialog);
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        viewd = inflaterDl.inflate(R.layout.present_dialog, null);
        dialog1.show();
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();// 得到属性
        params.gravity = Gravity.BOTTOM;// 显示在底部
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog1.getWindow().setAttributes(params);// 设置属性
        dialog1.getWindow().setContentView(viewd);// 把自定义view加上去
        ViewPager page_prient = (ViewPager) viewd.findViewById(R.id.page_prient);
        // 初始化3个子页面
        mPagerList = new ArrayList<BasePager>();
        mPagerList.add(new Page_one(this));
        mPagerList.add(new Page_two(this));
        //mPagerList.add(new Page_three(this));
        ContentAdapter contentAdapter = new ContentAdapter();
        page_prient.setAdapter(contentAdapter);
        page_prient.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPagerList.get(position).initData();// 获取当前被选中的页面, 初始化该页面数据
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerList.get(0).initData();// 初始化首页数据
        //count_layout = (RelativeLayout) viewd.findViewById(R.id.count_layout);
        //giftcount_layout = (ImageView) viewd.findViewById(R.id.giftcount_layout);
        //live_room_gift_count = (TextView) viewd.findViewById(R.id.live_room_gift_count);
        //live_room_gift_sendname = (TextView) viewd.findViewById(R.id.live_room_gift_sendname);
        iv_giftGiveing_button = (Button) viewd.findViewById(R.id.iv_giftGiveing_button);

        live_room_gift_pay = (TextView) viewd.findViewById(R.id.live_room_gift_pay);//点击充值
        live_room_gift_money = (TextView) viewd.findViewById(R.id.live_room_gift_money);//柠檬币
        getSelCoins();//观众的钻石
        //live_room_gift_sendname.setText("主播: " + CurLiveInfo.getHostName());
        live_room_gift_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //充值
                Intent sendPres = new Intent(LiveingActivity.this, MyWalletActivity.class);
                startActivity(sendPres);
                dialog1.dismiss();

            }
        });
//        count_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  PopuGiftCount mPopuGiftCount = new PopuGiftCount(LiveingActivity.this, viewd, live_room_gift_count);
//                //  mPopuGiftCount.showPopuGftCount();
//            }
//        });
        iv_giftGiveing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(live_room_gift_money.getText())) {
                    return;
                }
               /* if (iv_giftGiveing_button.getText().equals("发送")) {
                    iv_giftGiveing_button.setEnabled(false);
                    MyTimer timer = new MyTimer();
                    timer.start();
                } else {*/
                //如果自己的柠檬币 小于1, 请求接口 获得库存
                p_type = Integer.parseInt(sp.getString("p_type"));//礼物id
                score = Integer.parseInt(sp.getString("score"));//礼物id
                sendGiftToServer("1",p_type,score);

                //}
            }
        });
    }

    /*********
     * 送礼物接口
     * 数量
     * 分值
     */
    private void sendGiftToServer(String amount, final int p_type, int score) {

        if (NetWorkUtil.isNetworkConnected(this)) {
            //Log.i("送礼物接口", "getUserID.." + LoginManager.getInstance().getUserID(LiveingActivity.this));
            //Log.i("送礼物接口", "getHostID.." + CurLiveInfo.getHostID());
            //Log.i("送礼物接口", "amount.." + amount);
            //Log.i("送礼物接口", "score.." + score);
            Api.sendGift(LoginManager.getInstance().getUserID(LiveingActivity.this)
                    , CurLiveInfo.getHostID()
                    , "1"
                    , score
                    , new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("送礼物接口", "apiResponse.." + apiResponse.toString());
                    if (apiResponse.getCode() == 0) {
                        if (
                                p_type==4
//                                        ||
//                                        p_type==5||
//                                        p_type==6||
//                                        p_type==7||
//                                        p_type==12||
//                                        p_type==13||
//                                        p_type==14||
//                                        p_type==15
                                ){
                            String str="";
                            switch (p_type){
                                case 4:
                                    str="柠檬啪";
                                    break;
//                                case 5:
//                                    str="飞心";
//                                    break;
//                                case 6:
//                                    str="魔棒";
//                                    break;
//                                case 7:
//                                    str="钻戒";
//                                    break;
//                                case 12:
//                                    str="蛋糕";
//                                    break;
//                                case 13:
//                                    str="城堡";
//                                    break;
//                                case 14:
//                                    str="跑车";
//                                    break;
//                                case 15:
//                                    str="1314";
//                                    break;
                            }

                            live_gift_pp(str, LoginManager.getInstance().getHostName(LiveingActivity.this));
                        }
                        //点击发送 请求网络接口 把发送的礼物信息给后台记录 主播id 自己id 礼物id 礼物数量  数据发送成功  才把礼物发给队列 预备播放
                        present_num_show = Integer.parseInt(sp.getString("present_num_show"));//数量
                        present_num_show++;
                        presentMessage = LiveMySelfInfo.getInstance().getPhone() + "&" + PRESENT_MSG + "&" + LiveMySelfInfo.getInstance().getNickName()
                                + "&" + LiveMySelfInfo.getInstance().getAvatar() + "&" + p_type + "&" + present_num_show;
                        //Log.i("发送的消息", "发送端" + presentMessage);
                        TIMMessage Tim = new TIMMessage();
                        TIMCustomElem elem = new TIMCustomElem();
                        elem.setData(presentMessage.getBytes());
                        if (1 == Tim.addElement(elem))
                            Toast.makeText(getApplicationContext(), "enter error", Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                String customText = new String(elem.getData(), "UTF-8");
                                String splitItems[] = customText.split("&");
                                final String present_name = String.valueOf(splitItems[2]);
                                String present_i = String.valueOf(splitItems[1]);
                                final String present_image = String.valueOf(splitItems[3]);
                                final String present_type = String.valueOf(splitItems[4]);
                                String present_num = String.valueOf(splitItems[5]);

                                ChatEntity en = new ChatEntity();
                                en.setMessage_type(present_i);
                                en.setPresent_name(present_name);
                                en.setPresenr_type(present_type);
                                en.setSend_phone(LoginManager.getInstance().getPhone(LiveingActivity.this));
                                mArrayListPresent.add(en);
                                mArrayListChatEntity.add(en);
                                mChatMsgListAdapter.notifyDataSetChanged();
/*
                                if (p_type == 16 || p_type == 17 || p_type == 18 || p_type == 19 || p_type == 20 || p_type == 21 || p_type == 22) {
                                    if (p_type == 16) {

                                        gif_donghua.setImageResource(R.drawable.present_16);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 17) {
                                        gif_donghua.setImageResource(R.drawable.present_17);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 18) {
                                        gif_donghua.setImageResource(R.drawable.present_18);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 19) {
                                        gif_donghua.setImageResource(R.drawable.present_19);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 20) {
                                        gif_donghua.setImageResource(R.drawable.present_20);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 21) {
                                        gif_donghua.setImageResource(R.drawable.present_21);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    } else if (p_type == 22) {
                                        gif_donghua.setImageResource(R.drawable.present_22);
                                        gif_donghua.startAnimation(liwu);
                                        gif_donghua.clearAnimation();
                                    }
                                } else {*/
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GiftVo vo = new GiftVo();
                                        vo.setName(LiveMySelfInfo.getInstance().getNickName());
                                        vo.setHeard(LiveMySelfInfo.getInstance().getAvatar());
                                        vo.setType(String.valueOf(p_type));
                                        vo.setNum(Integer.parseInt("1"));
                                        giftManger.addGift(vo);
                                    }
                                }).start();
                                //  }

                                //同步显示魅力值
                                Message msg = new Message();
                                msg.obj = "hostCoins";
                                msg.what = NIMOBI;
                                mHandler.sendMessage(msg);

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        mConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, CurLiveInfo.getChatRoomId());
                        mConversation.sendMessage(Tim, new TIMValueCallBack<TIMMessage>() {
                            @Override
                            public void onError(int i, String s) {
                                Log.e(TAG, "enter error" + i + ": " + s);
                            }

                            @Override
                            public void onSuccess(TIMMessage timMessage) {

                            }
                        });

                        Message msg = new Message();
                        msg.obj = "selCoins";
                        msg.what = 2222;
                        mHandler.sendMessage(msg);
                    } else if (apiResponse.getCode() == -3) {

                        Dialog alertDialog = new AlertDialog.Builder(LiveingActivity.this).
                                setTitle("提示").
                                setMessage("当前余额不足,充值才能继续送礼,是否去充值?").
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent sendPres = new Intent(LiveingActivity.this, MyAccountActivity.class);
                                        startActivity(sendPres);
                                    }
                                }).
                                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presentdialog.dismiss();
                                    }
                                }).
                                create();

                        alertDialog.show();
                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage());
                    }
                }
            });
        } else {
            ToastUtils.showShort(LiveingActivity.this, "网络异常，请检查您的网络~");
        }

    }

    private void live_gift_pp(String giftName,String userName) {
        if (!NetWorkUtil.isNetworkConnected(LiveingActivity.this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.live_gift_pp(giftName, userName, CurLiveInfo.getRoomNum(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                }

                @Override
                public void onFailure(String errMessage) {

                }
            });
        }
    }


    /*********
     * 显示的魅力值
     */
    String Hostlemon_coins="";
    boolean isfast=true;
    private void getHostCoins() {

        if (NetWorkUtil.isNetworkConnected(this)) {
            Api.getHaveCoins(CurLiveInfo.getHostID(), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("获取主播剩余柠檬币", "apiResponse.." + apiResponse.toString());
                    if (apiResponse.getCode() == 0) {
                        JSONObject parse = JSON.parseObject(apiResponse.getData());
                        Hostlemon_coins = parse.getString("lemon_coins");
                        tv_NMB.setText(Hostlemon_coins);
                        if (isfast){
                            if (Hostlemon_coins!=null&&Hostlemon_coins!=""){
                                startnmb=Integer.parseInt(Hostlemon_coins);//开始时的魅力值
                            }
                            isfast=false;
                        }else {
                            if (Hostlemon_coins!=null&&Hostlemon_coins!=""){
                                stopnmb=Integer.parseInt(Hostlemon_coins);//结束时的魅力值
                            }
                        }

                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage());
                    }
                }
            });

        } else {
            ToastUtils.showShort(LiveingActivity.this, "网络异常，请检查您的网络~");
        }
    }

    /*********
     * 获取观众自己的钻石
     */
    private void getSelCoins() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            Api.getDiamonds(LoginManager.getInstance().getUserID(LiveingActivity.this), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("获取成员剩余钻石", "apiResponse.." + apiResponse.toString());
                    if (apiResponse.getCode() == 0) {
                        JSONObject parse = JSON.parseObject(apiResponse.getData());
                        String diamonds_coins = parse.getString("diamonds_coins");
                        //Log.i("获取成员剩余钻石", "1走没有");
                        live_room_gift_money.setText(diamonds_coins);
                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage());
                    }
                }
            });

        } else {
            ToastUtils.showShort(LiveingActivity.this, "网络异常，请检查您的网络~");
        }

    }

    private class MyTimer extends CountDownTimer {

        public MyTimer() {
            super(10000, 1000);
        }

        @Override
        public void onFinish() {
            iv_giftGiveing_button.setEnabled(true);
            iv_giftGiveing_button.setText("发送");
        }

        @Override
        public void onTick(long remainMillseconds) {
            iv_giftGiveing_button.setText((remainMillseconds / 1000) + "s");
        }
    }

    private class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

    }

    //for 测试获取测试参数
    private boolean showTips = false;
    Timer paramTimer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (showTips) {
                        String strTips = ILiveSDK.getInstance().getAVContext().getRoom().getQualityParam();
                        String[] tips = strTips.split(",");
                        int loss_rate_recv = 0, loss_rate_send = 0, loss_rate_recv_udt = 0, loss_rate_send_udt = 0;
                        for (String tip : tips) {
                            if (tip.contains("loss_rate_recv")) {
                                loss_rate_recv = getQuality(tip);
                            }
                            if (tip.contains("loss_rate_send")) {
                                loss_rate_send = getQuality(tip);
                            }
                            if (tip.contains("loss_rate_recv_udt")) {
                                loss_rate_recv_udt = getQuality(tip);
                            }
                            if (tip.contains("loss_rate_send_udt")) {
                                loss_rate_send_udt = getQuality(tip);
                            }
                        }
                        strTips = praseString(strTips);

                        if (loss_rate_recv > 4000 || loss_rate_send > 4000 || loss_rate_recv_udt > 2000 || loss_rate_send_udt > 500) {
                        }
                        //黄色示警
                        else if (loss_rate_recv > 2000 || loss_rate_send > 2000 || loss_rate_recv_udt > 1000 || loss_rate_send_udt > 300) {
                        } else {
                        }

                        //网络质量(暂时用丢包率表示)
                        int status = 0;
                        // 如果下行为0，证明有可能是主播端，没有下行视频，那么要看上行视频
                        if (loss_rate_recv == 0) {
                            if (loss_rate_send > 4000) {
                                status = 3;//红色警告
                            } else if (loss_rate_send > 2000) {
                                status = 2;//黄色警告
                            } else {
                                status = 1;//正常
                            }
                        } else {
                            if (loss_rate_recv > 4000) {
                                status = 3;//红色警告
                            } else if (loss_rate_recv > 2000) {
                                status = 2;//黄色警告
                            } else {
                                status = 1;//正常
                            }
                        }
                        switch (status) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    }
                }
            });
        }
    };

    private int getQuality(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
            }
        }
        return res;
    }

    //for 测试 解析参数
    private String praseString(String video) {
        if (video.length() == 0) {
            return "";
        }
        String result = "";
        String splitItems[];
        String tokens[];
        splitItems = video.split("\\n");
        for (int i = 0; i < splitItems.length; ++i) {
            if (splitItems[i].length() < 2)
                continue;

            tokens = splitItems[i].split(":");
            if (tokens[0].length() == "mainVideoSendSmallViewQua".length()) {
                continue;
            }
            if (tokens[0].endsWith("BigViewQua")) {
                tokens[0] = "mainVideoSendViewQua";
            }
            if (tokens[0].endsWith("BigViewQos")) {
                tokens[0] = "mainVideoSendViewQos";
            }
            result += tokens[0] + ":\n" + "\t\t";
            for (int j = 1; j < tokens.length; ++j)
                result += tokens[j];
            result += "\n\n";
        }
        return result;
    }


    private void backToNormalCtrlView() {
        if (LiveMySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            backGroundId = CurLiveInfo.getHostID();
            mHostCtrView.setVisibility(View.VISIBLE);
        } else {
            backGroundId = CurLiveInfo.getHostID();
        }
    }

    /**
     * 发消息弹出框
     */
    private void inputMsgDialog() {
        InputTextMsgDialog inputMsgDialog = new InputTextMsgDialog(this, R.style.inputdialog, this);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = inputMsgDialog.getWindow().getAttributes();

        lp.width = (int) (display.getWidth()); //设置宽度
        inputMsgDialog.getWindow().setAttributes(lp);
        inputMsgDialog.setCancelable(true);
        inputMsgDialog.show();
    }


    /**
     * 主播邀请应答框  是否同意答应互动邀请
     */
    private void initInviteDialog() {
        inviteDg = new Dialog(this, R.style.dialog);
        inviteDg.setContentView(R.layout.invite_dialog);
        TextView hostId = (TextView) inviteDg.findViewById(R.id.host_id);
        hostId.setText(CurLiveInfo.getHostID());
        TextView agreeBtn = (TextView) inviteDg.findViewById(R.id.invite_agree);
        TextView refusebtn = (TextView) inviteDg.findViewById(R.id.invite_refuse);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "accept invite" +
                        LogConstants.DIV + "host id " + CurLiveInfo.getHostID());
                //上麦 ；TODO 上麦 上麦 上麦 ！！！！！；
                mLiveHelper.sendC2CCmd(Constants.AVIMCMD_MUlTI_JOIN, "", CurLiveInfo.getHostID());
                mLiveHelper.upMemberVideo();
                inviteDg.dismiss();
            }
        });

        refusebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiveHelper.sendC2CCmd(Constants.AVIMCMD_MUlTI_REFUSE, "", CurLiveInfo.getHostID());
                inviteDg.dismiss();
            }
        });

        Window dialogWindow = inviteDg.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }


    /**
     * 消息刷新显示
     *
     * @param name    发送者
     * @param context 内容
     * @param type    类型 （上线线消息和 聊天消息）
     */
    public void refreshTextListView(String name, String context, int type,String phone) {
        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        entity.setType(type);
        entity.setSend_phone(phone);
        //mArrayListChatEntity.add(entity);
        notifyRefreshListView(entity);
        //mChatMsgListAdapter.notifyDataSetChanged();

        mListViewMsgItems.setVisibility(View.VISIBLE);
        SxbLog.d(TAG, "refreshTextListView height " + mListViewMsgItems.getHeight());

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }
    }
    /**
     * 消息刷新显示
     *
     * @param name    发送者
     * @param context 内容
     * @param type    类型 （上线线消息和 聊天消息）
     */
    public void refreshTextListView(String name, String context,String phone, int type) {
        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        entity.setType(type);
        entity.setSend_phone(phone);
        //mArrayListChatEntity.add(entity);
        notifyRefreshListView(entity);
        //mChatMsgListAdapter.notifyDataSetChanged();

        mListViewMsgItems.setVisibility(View.VISIBLE);
        SxbLog.d(TAG, "refreshTextListView height " + mListViewMsgItems.getHeight());

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }
    }


    /**
     * 通知刷新消息ListView
     */
    private void notifyRefreshListView(ChatEntity entity) {
        mBoolNeedRefresh = true;
        mTmpChatList.add(entity);
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
    }


    /**
     * 刷新ListView并重置状态
     */
    private void doRefreshListView() {
        if (mBoolNeedRefresh) {
            mBoolRefreshLock = true;
            mBoolNeedRefresh = false;
            if (mArrayListChatEntity!=null){
                mArrayListChatEntity.addAll(mTmpChatList);
            }
            if (mTmpChatList!=null){
                mTmpChatList.clear();
            }
            if (mChatMsgListAdapter!=null){
                mChatMsgListAdapter.notifyDataSetChanged();
            }

            if (null != mTimerTask) {
                mTimerTask.cancel();
            }
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    SxbLog.v(TAG, "doRefreshListView->task enter with need:" + mBoolNeedRefresh);
                    mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
                }
            };
            //mTimer.cancel();
            mTimer.schedule(mTimerTask, MINFRESHINTERVAL);
        } else {
            mBoolRefreshLock = false;
        }
    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {

    }

    @Override
    public void updateUserInfo(int requestCode, List<TIMUserProfile> profiles) {
        //Log.i("进入房间", "requestCode" + requestCode);
        if (null != profiles) {
            switch (requestCode) {
                case GETPROFILE_JOIN:
                    for (TIMUserProfile user : profiles) {
                        //zaixian_member.setText("" + CurLiveInfo.getMembers());
                        //tvMembers.setText(CurLiveInfo.getMembers()+"在线");
                        SxbLog.w(TAG, "get nick name:" + user.getNickName());
                        SxbLog.w(TAG, "get remark name:" + user.getRemark());
                        SxbLog.w(TAG, "get avatar:" + user.getFaceUrl());
                        //Log.i("进入房间", "updateUserInfo" + user.toString());
                        if (!TextUtils.isEmpty(user.getNickName())) {
                            refreshTextListView(user.getNickName(), "进入房间", Constants.MEMBER_ENTER,user.getIdentifier());
                        } else {
                            refreshTextListView(user.getIdentifier(), "进入房间", Constants.MEMBER_ENTER,user.getIdentifier());
                        }
                    }
                    break;
            }
        }
    }


    /**
     * 推流成功
     *
     * @param streamRes
     */
    @Override
    public void pushStreamSucc(TIMAvManager.StreamRes streamRes) {

    }

    /**
     * 将地址黏贴到黏贴版
     *
     * @param url
     * @param url2
     */
    private void ClipToBoard(final String url, final String url2) {
        SxbLog.i(TAG, "ClipToBoard url " + url);
        SxbLog.i(TAG, "ClipToBoard url2 " + url2);
        if (url == null) return;
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(R.layout.clip_dialog);
        TextView urlText = ((TextView) dialog.findViewById(R.id.url1));
        TextView urlText2 = ((TextView) dialog.findViewById(R.id.url2));
        Button btnClose = ((Button) dialog.findViewById(R.id.close_dialog));
        urlText.setText(url);
        urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clip = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                clip.setText(url);
                Toast.makeText(LiveingActivity.this, getResources().getString(R.string.clip_tip), Toast.LENGTH_SHORT).show();
            }
        });
        if (url2 == null) {
            urlText2.setVisibility(View.GONE);
        } else {
            urlText2.setText(url2);
            urlText2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clip = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    clip.setText(url2);
                    Toast.makeText(LiveingActivity.this, getResources().getString(R.string.clip_tip), Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 停止推流成功
     */
    @Override
    public void stopStreamSucc() {

    }

    @Override
    public void startRecordCallback(boolean isSucc) {

    }

    @Override
    public void stopRecordCallback(boolean isSucc, List<String> files) {
        if (isSucc == true) {

        }
    }

    //检查权限
    void checkPermission() {
        //Log.i("手机sdk版本", "Build.VERSION.SDK_INT..." + Build.VERSION.SDK_INT);
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Log.i("手机sdk版本", "Build.VERSION.SDK_INT..." + Build.VERSION.SDK_INT);
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.CAMERA);
            }
            if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            }
            if ((checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.WAKE_LOCK);
            }
            if ((checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            }
            if (permissionsList.size() != 0) {
                //Log.i("手机sdk版本", "permissionsList.size()" + permissionsList.size());
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        }
    }

    // 清除老房间数据
    private void clearOldData() {
        if (mArrayListChatEntity!=null){
            mArrayListChatEntity.clear();
        }
        mBoolNeedRefresh = true;
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
        mRootView.clearUserView();
    }

    @Override
    public void showFirstPage(ArrayList<LiveInfoJson> livelist) {
        int index = 0, oldPos = 0;
        for (; index < livelist.size(); index++) {
            if (livelist.get(index).getAvRoomId() == Integer.parseInt(CurLiveInfo.getRoomNum())) {
                oldPos = index;
                index++;
                break;
            }
        }
        if (bSlideUp) {
            index -= 2;
        }
        LiveInfoJson info = livelist.get((index + livelist.size()) % livelist.size());
        //Log.i("直播的信息", "info.." + info.toString());
        SxbLog.v(TAG, "ILVB-DBG|showFirstPage->index:" + index + "/" + oldPos + "|room:" + info.getHost().getUid() + "/" + CurLiveInfo.getHostID());

        if (null != info) {
            LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
            LiveMySelfInfo.getInstance().setJoinRoomWay(false);
            CurLiveInfo.setHostID(info.getHost().getUid());
            CurLiveInfo.setHostName(info.getHost().getUsername());
            CurLiveInfo.setHostAvator(info.getHost().getAvatar());
            CurLiveInfo.setRoomNum(String.valueOf(info.getAvRoomId()));
            CurLiveInfo.setMembers(info.getWatchCount() + 1); // 添加自己
            CurLiveInfo.setAdmires(info.getAdmireCount());
            CurLiveInfo.setAddress(info.getLbs().getAddress());

            backGroundId = CurLiveInfo.getHostID();

            showHeadIcon(mHeadIcon, CurLiveInfo.getHostAvator());
            if (!TextUtils.isEmpty(CurLiveInfo.getHostName())) {
            } else {
            }
            //zaixian_member.setText("" + CurLiveInfo.getMembers());
            tvMembers.setText(CurLiveInfo.getMembers()+"在线");
            //进入房间流程
            mLiveHelper.startEnterRoom();
        }
    }

    /**
     * 观看直播成员列表
     */
    private void groupMemberInfo() {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        } else {
            //Log.i("成员列表", CurLiveInfo.getChatRoomId().toString());
            //Log.i("成员列表", CurLiveInfo.getHostID());
            mLiveHelper.showProgressDialog("加载中...");
            Api.groupMemberInfo(LoginManager.getInstance().getUserID(this), CurLiveInfo.getChatRoomId(),"1","1", new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    mLiveHelper.hideProgressDialog();
                    //Log.i("成员列表", "data=s"+apiResponse.toString());
                    if (apiResponse.getCode() == Api.SUCCESS) {
                        String data = apiResponse.getData();
                        //Log.i("直播","data=s"+data.toString());

                        LivesInfoBean livesInfoBean = JSON.parseObject(data, LivesInfoBean.class);
                        List<AvMemberInfo> list = livesInfoBean.getLives();
                        avMemberInfos.clear();
                        avMemberInfos.addAll(list);

                        //Log.i("Main","Menbers="+CurLiveInfo.getAdmires()+",avMemberInfos="+avMemberInfos.size()+"list="+list.size());
//                        String user_robot = LiveMySelfInfo.getInstance().getUser_robot();
//                        if (user_robot!=null&&user_robot.length()>0&&!"".equals(user_robot)){
//                            int i = Integer.parseInt(user_robot);
//                            CurLiveInfo.setMembers(avMemberInfos.size()+CurLiveInfo.getAdmires()+i);
//                        }else {
                            CurLiveInfo.setMembers(avMemberInfos.size()+CurLiveInfo.getAdmires());
//                        }
                        getUser_robot();
                        //tvMembers.setText(CurLiveInfo.getMembers()+"在线");
//                        if (!"0".equals(LiveMySelfInfo.getInstance().getUser_robot())){
//                            groupMemberInfos();//用户购买的机器人
//                        }
                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage().toString());
                    }
                    HeadAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                }
            });
        }
    }

    private void getUser_robot() {
        //Log.i("直播","apiResponse=");
        Api.user_robot(LoginManager.getInstance().getUserID(LiveingActivity.this), new ApiResponseHandler(LiveingActivity.this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    int members = CurLiveInfo.getMembers();
                    String data = apiResponse.getData();
                    int i = Integer.parseInt(data);
                    CurLiveInfo.setMembers(i+members);
                    tvMembers.setText(CurLiveInfo.getMembers()+"在线");
                }
            }
        });
    }

    private int page_id=1;
    private boolean isSucc=true;
    private void groupMemberInfos1() {
        Api.groupMemberInfo(LoginManager.getInstance().getUserID(this), CurLiveInfo.getChatRoomId(), String.valueOf(page_id), "2", new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //Log.i("成员列表", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    String data = apiResponse.getData();
                    //Log.i("直播","data="+data.toString());

                    LivesInfoBean livesInfoBean = JSON.parseObject(data, LivesInfoBean.class);
                    List<AvMemberInfo> list = livesInfoBean.getLives();
                    avMemberInfos.addAll(list);
                    if(isSucc){
                        isSucc=!isSucc;
                        //CurLiveInfo.setMembers(avMemberInfos.size()+CurLiveInfo.getAdmires());
                    }
                    //Log.i("直播",livesInfoBean.getPage().getPage()+","+livesInfoBean.getPage().getPageCount());
                    if (!livesInfoBean.getPage().getPage().equals(livesInfoBean.getPage().getPageCount())){
                        page_id++;
                        groupMemberInfos1();
                    }
                    //tvMembers.setText(CurLiveInfo.getMembers()+"在线");
                } else {
                    ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage().toString());
                }
                HeadAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }
    /**
     * 分享
     */
    private void share() {
        UMImage thumb = new UMImage(this, R.mipmap.logo);
        UMWeb web = new UMWeb("http://www.ningmengtv.net/");
        web.setTitle("柠檬秀");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("大家好,我正在直播哦，喜欢我的朋友赶紧来哦");//描述
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(LiveingActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LiveingActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LiveingActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("GETMENBERINFO")) {
                    groupMemberInfo();//主播加载观众列表显示
            }
        }
    }
    public class MyReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("GETMENBERINFOS")) {
                //Log.i("成员列表", "收到广播");
                    page_id=1;
                    groupMemberInfo1(String.valueOf(page_id),"1");//观众加载观众列表显示
                    groupMemberInfo1(String.valueOf(page_id),"2");//观众加载观众列表显示
            }
        }
    }

    private void groupMemberInfo1(final String page, String type) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        } else {
            //Log.i("成员列表", CurLiveInfo.getChatRoomId().toString());
            //Log.i("成员列表", CurLiveInfo.getHostID());
            Api.groupMemberInfo(LoginManager.getInstance().getUserID(this), CurLiveInfo.getChatRoomId(),page,type, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("成员列表", apiResponse.toString());
                    if (apiResponse.getCode() == Api.SUCCESS) {
                        String data = apiResponse.getData();
                        //Log.i("直播","data="+data.toString());

                        LivesInfoBean livesInfoBean = JSON.parseObject(data, LivesInfoBean.class);
                        List<AvMemberInfo> list = livesInfoBean.getLives();
                        if ("1".equals(page)){
                            avMemberInfos.clear();
                        }
                        if (!livesInfoBean.getPage().getPage().equals(livesInfoBean.getPage().getPageCount())){
                            page_id++;
                            groupMemberInfo1(String.valueOf(page_id),"2");
                        }
                        avMemberInfos.addAll(list);
                        tvMembers.setText(CurLiveInfo.getMembers()+"在线");
//
                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage().toString());
                    }
                    HeadAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                }
            });
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关注和取消关注直播
     */
    public void followHandle() {

        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        } else {
            Api.followHandle(LoginManager.getInstance().getUserID(LiveingActivity.this), CurLiveInfo.getHostID(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("关注", "apiResponse" + apiResponse.toString());
                    //Log.i("关注", "getHostID：" + CurLiveInfo.getHostID());
                    //Log.i("关注", "1参数：" + LoginManager.getInstance().getUserID(LiveingActivity.this));

                    /*JSONObject parse = (JSONObject) JSON.parse(apiResponse.getData());
                    String is_follows = parse.getString("is_follows");//是否已经关注*/
                    /*if (is_follows.equals("0")) {  //1以关注 0取消关注
                        tv_guanzhu.setText("取消关注");
                    } else {
                        tv_guanzhu.setText("关注");
                    }*/
                    if (apiResponse.getCode() == 0) {
                        //关注成功
                        tv_guanzhu.setText("取消关注");
                    } else if (apiResponse.getCode() == 1) {
                        //取消关注
                        tv_guanzhu.setText("关注");
                    } else {
                        ToastUtils.showShort(LiveingActivity.this, apiResponse.getMessage());
                    }
                    mLiveHelper.sendGroupCmd(Constants.AVIMCMD_PRAISE, "");
                    //同步关注人数
                    Message msg = new Message();
                    msg.obj = "guanzhu";
                    msg.what = GUANZHU;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(LiveingActivity.this, errMessage);
                }
            });
        }
    }

    /**
     * 主播的关注人数
     */
    public void getFollow() {

        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(LiveingActivity.this, NET_UNCONNECT);
            return;
        } else {
            Api.getFollows(CurLiveInfo.getHostID(), new ApiResponseHandler(LiveingActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    //Log.i("主播的关注人数", "apiResponse" + apiResponse.toString());

                    if (apiResponse.getCode() == 0) {
                        //主播的关注人数
                        JSONObject parse = (JSONObject) JSON.parse(apiResponse.getData());
                        String follow_nums = parse.getString("follow_nums");
                        //tvMembers.setText(follow_nums + "关注");

                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    ToastUtils.showShort(LiveingActivity.this, errMessage);
                }
            });
        }
    }
}

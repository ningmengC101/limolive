package com.example.project.limolive.tencentlive.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.utils.CrashHandler;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;


/**
 * 初始化
 * 包括imsdk等
 */
public class InitBusinessHelper {
    private static String TAG = "InitBusinessHelper";

    private InitBusinessHelper() {
    }
    private static String appVer = "1.0";

    /**
     * 初始化App
     */
    public static void initApp(final Context context) {
        //初始化avsdk imsdk
        TIMManager.getInstance().disableBeaconReport();
        LiveMySelfInfo.getInstance().getCache(context);
        switch(LiveMySelfInfo.getInstance().getLogLevel()){
        case OFF:
            TIMManager.getInstance().setLogLevel(TIMLogLevel.OFF);
            break;
        case WARN:
            TIMManager.getInstance().setLogLevel(TIMLogLevel.WARN);
            break;
        case DEBUG:
            TIMManager.getInstance().setLogLevel(TIMLogLevel.DEBUG);
            break;
        case INFO:
            TIMManager.getInstance().setLogLevel(TIMLogLevel.INFO);
            break;
        default:
            break;
        }

        // 初始化ILiveSDK
        ILiveSDK.getInstance().initSdk(context, Constants.SDK_APPID, Constants.ACCOUNT_TYPE);
        // 初始化直播模块
        ILVLiveConfig liveConfig = new ILVLiveConfig();
        liveConfig.messageListener(MessageEvent.getInstance());
        ILVLiveManager.getInstance().init(liveConfig);

        ILiveLoginManager.getInstance().setUserStatusListener(new ILiveLoginManager.TILVBStatusListener() {
            @Override
            public void onForceOffline(int error, String message) {
                switch (error){
                case ILiveConstants.ERR_KICK_OUT:
                    SxbLog.w(TAG, "onForceOffline->entered!");
                    SxbLog.d(TAG, LogConstants.ACTION_HOST_KICK + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "on force off line");
                    Toast.makeText(context, context.getString(R.string.tip_force_offline), Toast.LENGTH_SHORT).show();
                    context.sendBroadcast(new Intent(Constants.BD_EXIT_APP));
                    break;
                case ILiveConstants.ERR_EXPIRE:
                    SxbLog.w(TAG, "onUserSigExpired->entered!");
                    Toast.makeText(context, "onUserSigExpired|"+message, Toast.LENGTH_SHORT).show();
                    context.sendBroadcast(new Intent(Constants.BD_EXIT_APP));
                    break;
                }
            }
        });

        //初始化CrashReport系统
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
    }
}

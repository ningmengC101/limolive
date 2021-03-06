package com.example.project.limolive.tencentim.presenter;

import android.util.Log;

import com.example.project.limolive.tencentim.event.FriendshipEvent;
import com.example.project.limolive.tencentim.event.GroupEvent;
import com.example.project.limolive.tencentim.event.MessageEvent;
import com.example.project.limolive.tencentim.event.RefreshEvent;
import com.example.project.limolive.tencentim.model.NomalConversation;
import com.example.project.limolive.tencentim.viewfeatures.ConversationView;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 会话界面逻辑
 */
public class ConversationPresenter implements Observer {

    private static final String TAG = "ConversationPresenter";
    private ConversationView view;

    public ConversationPresenter(ConversationView view){
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        //注册刷新监听
        RefreshEvent.getInstance().addObserver(this);
        //注册好友关系链监听
        FriendshipEvent.getInstance().addObserver(this);
        //注册群关系监听
        GroupEvent.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent){
            TIMMessage msg = (TIMMessage) data;
            view.updateMessage(msg);
        }else if (observable instanceof FriendshipEvent){
            FriendshipEvent.NotifyCmd cmd = (FriendshipEvent.NotifyCmd) data;
            switch (cmd.type){
                case ADD_REQ:
                case READ_MSG:
                case ADD:
                    view.updateFriendshipMessage();
                    break;
            }
        }else if (observable instanceof GroupEvent){
            GroupEvent.NotifyCmd cmd = (GroupEvent.NotifyCmd) data;
            switch (cmd.type){
                case UPDATE:
                case ADD:
                    TIMGroupCacheInfo data1 = (TIMGroupCacheInfo) cmd.data;
                    TIMMessage lastMsg = data1.getGroupInfo().getLastMsg();
                    NomalConversation conversation = new NomalConversation(lastMsg.getConversation());
                    if (conversation.getLastMessageSummary().contains("code")&&conversation.getLastMessageSummary().equals("message")){
                    }else {
                        view.updateGroupInfo((TIMGroupCacheInfo) cmd.data);
                    }
                    break;
                case DEL:
                    view.removeConversation((String) cmd.data);
                    break;

            }
        }else if (observable instanceof RefreshEvent){
            view.refresh();
        }
    }



    public void getConversation(){
        List<TIMConversation> list = TIMManager.getInstance().getConversionList();
        List<TIMConversation> result = new ArrayList<>();
        for (TIMConversation conversation : list){
            if (conversation.getType() == TIMConversationType.System) continue;
            result.add(conversation);
            conversation.getMessage(1, null, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    if (timMessages.size() > 0) {
                        view.updateMessage(timMessages.get(0));
                    }

                }
            });

        }
        view.initView(result);
    }

    /**
     * 删除会话
     *
     * @param type 会话类型
     * @param id 会话对象id
     */
    public boolean delConversation(TIMConversationType type, String id){
        return TIMManager.getInstance().deleteConversationAndLocalMsgs(type, id);
    }


}

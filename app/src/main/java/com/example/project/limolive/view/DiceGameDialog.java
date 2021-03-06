package com.example.project.limolive.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/9/5.
 */

public class DiceGameDialog  extends Dialog{
    public static DiceGameDialog instance=null;
    public static DiceGameDialog getInstance(Context context){

        if (instance==null){
            instance=new DiceGameDialog(context, R.style.dialog);
        }
        return instance;
    }

    private DiceGameDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }



    public void initDialog(View view) {
        setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width =view.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) (view.getResources().getDisplayMetrics().heightPixels*0.40);
        view.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        //getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        setCanceledOnTouchOutside(true);

        Window dialogWindow = instance.getWindow();
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
    public void onDesPlay(){
        instance=null;
    }

}

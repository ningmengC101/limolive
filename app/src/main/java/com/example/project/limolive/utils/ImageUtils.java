package com.example.project.limolive.utils;

import android.graphics.Bitmap;

import com.example.project.limolive.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by AAA on 2017/10/18.
 */

public class ImageUtils {
    private static DisplayImageOptions options= null;
    public static DisplayImageOptions getOptions(){
        if (options==null)
            options=new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.zanwu) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.zanwu)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.zanwu)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                    .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                    .build();//构建完成
        return options;
    }
}

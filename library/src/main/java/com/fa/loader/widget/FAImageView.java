package com.fa.loader.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.URLUtil;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;
import com.fa.loader.FALoader;

/**
 * Created by leobui on 10/31/2017.
 */

public class FAImageView extends SimpleDraweeView {
    public interface Callback{
        public void onStart();
        public void onSuccess();
        public void onError();
    }
    private Callback callback;
    private int width;
    private int height;
    private boolean isCenterCrop = false;
    private int placeHolderDrawableId;
    private int errorDrawableId;
    public FAImageView placeholder(int placeHolderDrawableId){
        this.placeHolderDrawableId = placeHolderDrawableId;
        return this;
    }
    public FAImageView error(int errorDrawableId){
        this.errorDrawableId = errorDrawableId;
        return this;
    }
    public FAImageView callback(Callback callback){
        this.callback = callback;
        return this;
    }
    public FAImageView resize(int width, int height){
        this.width =width;
        this.height = height;
        return this;
    }
    public FAImageView centerCrop(){
        isCenterCrop = true;
        return this;
    }
    public FAImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }
    public FAImageView(Context context) {
        super(context);
    }

    public FAImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FAImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FAImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void loadImage(String url){
        if (url == null) return;
        if (!URLUtil.isValidUrl(url)) return;
        if (FALoader.type == FALoader.Type.FRESCO) {
            Uri uri = Uri.parse(url);
            if (placeHolderDrawableId >0){
                getHierarchy().setPlaceholderImage(placeHolderDrawableId);
            }
            if (errorDrawableId >0){
                getHierarchy().setFailureImage(errorDrawableId);
            }
            if (callback != null || (width >0 && height >0)){
                callback.onStart();

                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                            .build();

                DraweeController controller;
                controller= Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setControllerListener(new BaseControllerListener<ImageInfo>(){
                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                if (callback != null){
                                    callback.onSuccess();
                                }
                                super.onFinalImageSet(id, imageInfo, animatable);
                            }

                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                if (callback != null){
                                    callback.onError();
                                }
                                super.onFailure(id, throwable);

                            }
                        })
                        .build();
                setController(controller);

                if (width >0 && height >0) {
                    ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri)
                            .setResizeOptions(new ResizeOptions(width, height))
                            .build();
                    setController(
                            Fresco.newDraweeControllerBuilder()
                                    .setOldController(getController())
                                    .setImageRequest(request1)
                                    .build());
                }
            }else {
                setImageURI(uri);
            }
        }
        if (FALoader.type == FALoader.Type.PICASSO) {
            Picasso.with(getContext()).load(url).
                    into(this);
        }

    }

}

package com.vega.loader.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.vega.loader.FALoader;

/**
 * Created by leobui on 10/31/2017.
 */

public class FAImageView extends SimpleDraweeView {
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
        if (FALoader.type == FALoader.Type.FRESCO) {
            Uri uri = Uri.parse(url);
            setImageURI(uri);
        }
        if (FALoader.type == FALoader.Type.PICASSO) {
            Picasso.with(getContext()).load(url).
                    into(this);
        }
    }
}

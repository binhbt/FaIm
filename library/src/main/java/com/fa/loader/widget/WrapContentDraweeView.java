package com.fa.loader.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by leobui on 11/29/2017.
 */
//Fix for wrap content bug
//https://stackoverflow.com/questions/33955510/facebook-fresco-using-wrap-content
public class WrapContentDraweeView extends SimpleDraweeView {

    // we set a listener and update the view's aspect ratio depending on the loaded image
    private final ControllerListener listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onIntermediateImageSet(String id,  ImageInfo imageInfo) {
            updateViewSize(imageInfo);
        }

        @Override
        public void onFinalImageSet(String id,  ImageInfo imageInfo,  Animatable animatable) {
            updateViewSize(imageInfo);
        }
    };

    public WrapContentDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WrapContentDraweeView(Context context) {
        super(context);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageURI(Uri uri, Object callerContext) {
        DraweeController controller = ((PipelineDraweeControllerBuilder)getControllerBuilder())
                .setControllerListener(listener)
                .setCallerContext(callerContext)
                .setUri(uri)
                .setOldController(getController())
                .build();
        setController(controller);
    }

    protected void updateViewSize(ImageInfo imageInfo) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if(lp.height == ViewGroup.LayoutParams.WRAP_CONTENT &&lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //do something
            if (imageInfo != null) {
                getLayoutParams().width = imageInfo.getWidth();
                getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
            }
        }

    }
}
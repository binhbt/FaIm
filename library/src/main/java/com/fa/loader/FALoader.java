package com.fa.loader;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by leobui on 10/31/2017.
 */

public class FALoader {
    public static Type type = Type.FRESCO;
    public enum Type{
        VOLLEY,
        UNIVERSAL,
        PICASSO,
        GLIDE,
        FRESCO
    }
    public static void initialize(Context ctx, Type type){
        if (type != null){
            FALoader.type = type;
        }
        if (type == Type.FRESCO || type ==null) {
            Fresco.initialize(ctx);
        }
    }
    public static void initialize(Context ctx){
        initialize(ctx, null);
    }

}

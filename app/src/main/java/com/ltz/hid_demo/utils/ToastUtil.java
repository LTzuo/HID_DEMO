package com.ltz.hid_demo.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.ltz.hid_demo.AppHelper;

/**
 * @author ltzuo
 *
 */
public class ToastUtil {
 

   
    public static void ShortToast(final Object text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(AppHelper.getAppHelper(), ""+text, Toast.LENGTH_SHORT).show();
			}
		});
    }
}

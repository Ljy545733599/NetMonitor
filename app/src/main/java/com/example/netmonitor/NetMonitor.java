package com.example.netmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

public class NetMonitor {
    private static List<OnNetworkChangeListener> onNetworkChangeListeners;
    private NetBroadcastReceiver netBroadcastReceiver;
    private Context context;

    public NetMonitor() {
        onNetworkChangeListeners = new ArrayList<>();
    }

    /**
     * 开启网络监听
     */
    public void start(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;

        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter();

        //设置action为CONNECTIVITY_ACTION
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //动态注册广播
        applicationContext.registerReceiver(netBroadcastReceiver, filter);
    }

    /**
     * 停止网络监听
     */
    public void stop() {
        if (context == null) {
            return;
        }

        if (netBroadcastReceiver != null) {
            context.unregisterReceiver(netBroadcastReceiver);
        }

        context = null;
    }

    /**
     * 注册监听
     */
    public void addListener(OnNetworkChangeListener listener) {
        if (listener != null) {
            onNetworkChangeListeners.add(listener);
        }
    }

    /**
     * 注销监听
     */
    public void removeListener(OnNetworkChangeListener listener) {
        if (listener != null) {
            onNetworkChangeListeners.remove(listener);
        }
    }

    /**
     * 通知网络状态更新
     */
    private static void notifyNetworkChange() {
        for (OnNetworkChangeListener listener : onNetworkChangeListeners) {
            if (listener != null) {
                listener.onNetworkChange();
            }
        }
    }

    private static class NetBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //如果action是网络变化，则执行以下处理逻辑
            if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        notifyNetworkChange();
                    }
                }).start();
            }
        }
    }
}

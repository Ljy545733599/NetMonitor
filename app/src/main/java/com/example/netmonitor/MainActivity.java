package com.example.netmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnNetworkChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetMonitor netMonitor = new NetMonitor();
        netMonitor.start(this.getApplicationContext());
        netMonitor.addListener(this);
    }

    @Override
    public void onNetworkChange() {
        //更新操作
    }
}
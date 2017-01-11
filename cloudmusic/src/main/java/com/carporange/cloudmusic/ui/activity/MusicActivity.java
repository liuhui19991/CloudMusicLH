package com.carporange.cloudmusic.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.service.MusicService;

import java.util.List;

public class MusicActivity extends Activity {
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(MusicActivity.this, MusicService.class);
                startService(mIntent);
            }
        });
    }

    @Override
    protected void onStop() {
        stopService(mIntent);
        super.onStop();
    }

    public static boolean isRuningService(Context context, String serviceName) {

        // 校验服务是否还活着

        ActivityManager am = (ActivityManager) context

                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);

        for (ActivityManager.RunningServiceInfo info : infos) {
            String name = info.service.getClassName();
            if (serviceName.equals(name))                 return true;
        }
        return false;
    }
}

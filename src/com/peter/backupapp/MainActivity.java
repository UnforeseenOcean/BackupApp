
package com.peter.backupapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.peter.backupapp.business.AppManager;
import com.peter.backupapp.business.AppManager.Filter;
import com.peter.backupapp.business.SaveFileService;
import com.peter.backupapp.entity.AppInfo;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onSave(v);
            }
        });

    }

    public void onSave(View v) {
        List<AppInfo> apps = new AppManager(getApplicationContext())
                .getAppsInfo(Filter.SYSTEM_INSTALLED_APPS);
        String name = null;
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getPackageName().equals("com.htc.calendar")) {
                 name = apps.get(i).getAppLocation();
                 Log.i("peitao","size = "+apps.get(i).getAppSize());
                break;
            }
        }
        name = apps.get(0).getAppLocation();
        Log.i("peitao","name = "+name);
        
        SaveFileService d = new SaveFileService(getApplicationContext());
        try {
            d.saveToSdCard(name);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        String con = null;
//        try {
//            con = d.readFile(name);
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        try {
//            d.saveToSdCard("mytest2.apk", con);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

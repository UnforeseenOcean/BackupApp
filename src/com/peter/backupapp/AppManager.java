
package com.peter.backupapp;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.List;

public class AppManager {
    Context mContext;

    public AppManager(Context context) {
        mContext = context;
    }

    List<PackageInfo> getInstalledAppInfo() {
        return mContext.getPackageManager().getInstalledPackages(0);
    }

}


package com.peter.backupapp.entity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;

import java.io.File;

public class AppInfoInternal {
    private final PackageInfo mPackageInfo;
    private final Context mContext;
    
    public AppInfoInternal(Context context,PackageInfo packageInfo) {
        mContext = context;
        mPackageInfo =packageInfo;
    }

    public String getVersionName() {
        return mPackageInfo.versionName;
    }

    public String getPackageName() {
        return mPackageInfo.packageName;
    }

    public String getClassName() {
        return mPackageInfo.applicationInfo.className;
    }

    public int getVersionCode() {
        return mPackageInfo.versionCode;
    }

    public String getAppLocation() {
        return mPackageInfo.applicationInfo.publicSourceDir;
    }

    public String getAppSize() {
        return Formatter.formatFileSize(mContext, new File(
                mPackageInfo.applicationInfo.publicSourceDir).length());
    }

    public Drawable getApplicationIcon() {
        Drawable icon = mPackageInfo.applicationInfo.loadIcon(mContext.getPackageManager());

        return icon;
    }

    public CharSequence getApplicationLabel() {
        return mPackageInfo.applicationInfo.loadLabel(mContext.getPackageManager());
    }

}

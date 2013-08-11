
package com.peter.backupapp.entity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;

import java.io.File;

public class AppInfo {
    private final PackageInfo mPackageInfo;
    private final ResolveInfo mResolveInfo;
    private final Context mContext;

    public AppInfo(Context context, PackageInfo packageInfo) {
        mContext = context;
        mPackageInfo = packageInfo;
        mResolveInfo = null;
        argumentCheck();

    }

    public AppInfo(Context context, ResolveInfo resolveInfo) {

        mContext = context;
        mPackageInfo = null;
        mResolveInfo = resolveInfo;
        argumentCheck();
    }

    private void argumentCheck() {
        if (mContext == null || (mPackageInfo == null && mResolveInfo == null)) {
            throw new IllegalArgumentException("The argument should not be null.");
        }
    }

    public String getVersionName() {
        if (mPackageInfo != null) {
            return mPackageInfo.versionName;
        }

        return null;
    }

    public String getPackageName() {
        if (mPackageInfo != null) {
            return mPackageInfo.packageName;
        }

        return null;
    }

    public String getClassName() {
        if (mPackageInfo != null) {
            return mPackageInfo.applicationInfo.className;
        }

        return null;
    }

    public int getVersionCode() {
        if (mPackageInfo != null) {
            return mPackageInfo.versionCode;
        }

        return 0;
    }

    public String getAppLocation() {
        if (mPackageInfo != null) {
            return mPackageInfo.applicationInfo.publicSourceDir;
        }

        return null;
    }

    public String getAppSize() {
        if (mPackageInfo != null) {
            return Formatter.formatFileSize(mContext, new File(
                    mPackageInfo.applicationInfo.publicSourceDir).length());
        }

        return null;
    }

    public Drawable getApplicationIcon() {
        if (mPackageInfo != null) {
            Drawable icon = mPackageInfo.applicationInfo.loadIcon(mContext.getPackageManager());
            return icon;
        }

        if (mResolveInfo != null) {
            return mResolveInfo.loadIcon(mContext.getPackageManager());
        }

        return null;
    }

    public String getApplicationLabel() {
        if (mPackageInfo != null) {
            return mPackageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
        }

        if (mResolveInfo != null) {
            return mResolveInfo.loadLabel(mContext.getPackageManager()).toString();
        }

        return null;
    }
}

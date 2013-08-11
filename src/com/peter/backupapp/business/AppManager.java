
package com.peter.backupapp.business;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.peter.backupapp.entity.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppManager {

    /**
     * means already installed
     */
    public static int INSTALLED = 0;
    /**
     * means not installed
     */
    public static int UNINSTALLED = 1;
    /**
     * means already installed but lower versionCode ,can update new version
     */
    public static int INSTALLED_UPDATE = 2;

    private final Context mContext;

    public AppManager(Context context) {
        mContext = context;
    }

    public List<AppInfo> getAppsInfo(Filter filter) {
        List<AppInfo> apps = new ArrayList<AppInfo>();
        List<PackageInfo> packages = mContext.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (filter == Filter.SYSTEM_INSTALLED_APPS) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                    AppInfo appInfo = new AppInfo(mContext, packageInfo);
                    apps.add(appInfo);
                }
            } else if (filter == Filter.CUSTOM_INSTALLED_APPS) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    AppInfo appInfo = new AppInfo(mContext, packageInfo);
                    apps.add(appInfo);
                }
            } else if (filter == Filter.ALL_APPS) {
                AppInfo appInfo = new AppInfo(mContext, packageInfo);
                apps.add(appInfo);
            }
        }

        return apps;
    }

    public List<AppInfo> getMenuApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<AppInfo> apps = new ArrayList<AppInfo>();
        List<ResolveInfo> resolveInfos = mContext.getPackageManager().queryIntentActivities(intent,
                0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            AppInfo appInfo = new AppInfo(mContext, resolveInfo);
            apps.add(appInfo);
        }

        return apps;
    }

    /**
     * @param context
     * @param appPath
     */
    public static void InstatllApp(Context context, String appPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appPath),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void UninstatllApp(Context context, String pkgName) {
        Uri packageURI = Uri.parse("package:" + pkgName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void OpenApp(Context context, String pkgName) {

        Intent intent = null;
        try {
            intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
        } catch (Exception e) {
            e.printStackTrace();
            intent = null;
        }
        if (intent == null) {
            Log.w("AppManager", "open app intent == null return");
            return;
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get apk file info.<br>
     * Return null if apkPath error or the package could not be successfully
     * parsed
     * 
     * @param context
     * @param apkPath
     * @return
     */
    public static AppInfo getApkFileInfo(Context context, String apkPath) {

        File apkFile = new File(apkPath);
        if (!apkFile.exists() || !apkPath.toString().toLowerCase().endsWith(".apk")) {
            return null;
        }

        PackageManager pm = context.getPackageManager();

        PackageInfo pakinfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pakinfo == null) {
            return null;
        }

        if (pakinfo.applicationInfo.publicSourceDir == null) {
            pakinfo.applicationInfo.publicSourceDir = apkPath;
        }

        return new AppInfo(context, pakinfo);
    }

    /**
     * @param context
     * @param packageName
     * @param versionCode
     * @return
     */
    public static int getAppState(Context context, String packageName, int versionCode) {
        PackageManager pManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        Boolean isInstalled = true;
        int ret = UNINSTALLED;
        try {
            packageInfo = pManager.getPackageInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (NameNotFoundException e) {
            isInstalled = false;
        }

        if (packageInfo == null) {
            isInstalled = false;
        }

        if (isInstalled) {
            if (versionCode > packageInfo.versionCode) {
                ret = INSTALLED_UPDATE;
            } else {
                ret = INSTALLED;
            }
        }
        return ret;

    }

    public static enum Filter {
        CUSTOM_INSTALLED_APPS,
        SYSTEM_INSTALLED_APPS,
        ALL_APPS
    }

}

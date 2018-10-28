package yalimov.course.collectors;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StatFs;

import java.io.File;

import yalimov.course.Common;

public class SystemCollector
{
    private static String SystemLog = "";

    public static String getSystemLog()
    {
        return SystemLog;
    }
    public static void SetOsInfo(Context context)
    {
        SystemLog = "System information:\n" +
                    Common.DELIMITER + "\n" + "OS configuration:\n"       + "\n" + GetOsVersion() + "\n" + GetFreeSpace() + "\n" +
                    Common.DELIMITER + "\n" + "Application Information\n" + "\n" + GetAppsInfo(context) +
                    Common.DELIMITER + "\n" + "Synchronized accounts\n"   + "\n" + GetAccountsInfo(context);
    }

    private static String GetOsVersion()
    {
        return "OS version: [" + android.os.Build.VERSION.RELEASE + "]\n" +
                "SDK version: [" + Integer.toString(Build.VERSION.SDK_INT) + "]";
    }
    private static String GetFreeSpace()
    {
        File path            = android.os.Environment.getDataDirectory();
        StatFs stat          = new StatFs(path.getAbsolutePath());
        long blockSize       = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long result          = (availableBlocks * blockSize) / (1024 * 1024);

        return "Free disk space: [" + Long.toString(result)+"] Mb";
    }
    private static String GetAppsInfo(Context context)
    {
        String installed = "{Installed}:\n\n";
        String running   = "\n\n{Running}:\n\n";

        for (ApplicationInfo it : context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA))
        {
            installed += "[" + it.packageName + "]\n";

            if(context.getPackageManager().getLaunchIntentForPackage(it.packageName) != null)
            {
                running += "[" + it.packageName + "]\n";
            }
        }
        return installed + running;
    }
    private static String GetAccountsInfo(Context context)
    {
        String result = "";

        for(Account it : AccountManager.get(context).getAccounts())
        {
            result += "[" + it.name +"]\n";
        }


        if (result.isEmpty())
        {
            result += "[No synchronized accounts found]\n";
        }

        return result;
    }
}

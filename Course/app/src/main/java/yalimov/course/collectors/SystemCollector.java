package yalimov.course.collectors;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StatFs;

import java.io.File;

public class SystemCollector
{
    public static String GetOsInfo(Context context)
    {
        return GetOsVersion() + GetFreeSpace()
                + GetAppsInfo(context) + GetAccountsInfo(context);
    }

    private static String GetOsVersion()
    {
        return "OS version: [" + android.os.Build.VERSION.RELEASE + "]\n" +
                "SDK version: [" + Integer.toString(Build.VERSION.SDK_INT) + "]\n";
    }
    private static String GetFreeSpace()
    {
        File path            = android.os.Environment.getDataDirectory();
        StatFs stat          = new StatFs(path.getAbsolutePath());
        long blockSize       = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long result          = (availableBlocks * blockSize) / (1024 * 1024);

        return "Free disk space: [" + Long.toString(result)+"] Mb\n";
    }
    private static String GetAppsInfo(Context context)
    {
        String installed = "Installed:\n";
        String running   = "Running:\n";

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
        String result = "Accounts:\n";

        for(Account it : AccountManager.get(context).getAccounts())
        {
            result += "[" + it.name +"]\n";
        }
        return result;
    }
}

// Permissions -- GET_ACCOUNTS
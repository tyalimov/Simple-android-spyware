package yalimov.course.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public abstract class WiFiScanReceiver extends BroadcastReceiver
{
    Context context;
    static ArrayList<ScanResult> Networks = new ArrayList<ScanResult>();

    public WiFiScanReceiver(Context _context)
    {
        context = _context;
    }

    @Override
    public void onReceive(Context c, Intent intent)
    {
        Networks.clear();
        Networks.addAll(((WifiManager)context.getSystemService(WIFI_SERVICE)).getScanResults());
    }
}


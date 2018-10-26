package yalimov.course.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class WiFiScanReceiver extends BroadcastReceiver
{
    private static ArrayList<ScanResult> ScannedNetworks = new ArrayList<ScanResult>();

    public static ArrayList<ScanResult> getScannedNetworks()
    {
        return ScannedNetworks;
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ScannedNetworks.clear();
        ScannedNetworks.addAll(((WifiManager)context.getSystemService(WIFI_SERVICE)).getScanResults());
    }
}

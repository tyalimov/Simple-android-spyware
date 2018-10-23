package yalimov.course.network;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import yalimov.course.receivers.OnBroadcastReceiverResult;
import yalimov.course.receivers.WiFiScanReceiver;


public class WiFiDemo
{
    private Context context;
    private WifiManager wifi;
    private ArrayList<ScanResult> results = new ArrayList<ScanResult>();

    BroadcastReceiver wifi_receiver = null;

    public WiFiDemo(Context context)
    {
        this.context = context;
        wifi = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        try
        {
            if (!wifi.isWifiEnabled())
            {
                Toast.makeText(context, "Activating WiFi", Toast.LENGTH_LONG).show();
                wifi.setWifiEnabled(true);
            }
        }
        catch (Exception ex)
        {
            Log.d("WiFiDemo", "" + ex);
        }
    }
    public void scanNetworks(final OnBroadcastReceiverResult callBack)
    {
        if(wifi_receiver == null)
        {
            wifi_receiver = new WiFiScanReceiver(callBack)
            {
                @Override
                public void onReceive(Context c, Intent intent)
                {
                    Log.d("WifScanner", "onReceive");
                    List<ScanResult> temp = wifi.getScanResults();

                    callBack.onResult(temp);

                    context.unregisterReceiver(this);
                }
            };
        }
        context.registerReceiver(wifi_receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();

        Log.d("WifScanner", "scanWifiNetworks");
    }
}
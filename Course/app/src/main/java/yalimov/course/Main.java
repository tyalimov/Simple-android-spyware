package yalimov.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import yalimov.course.service.NetService;

import static yalimov.course.Common.DEBUG_TAG;

// TODO: Uploading, Getting, Saving state, Searching free networks.

public class Main extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            Intent intent = new Intent(this, NetService.class);
            startService(intent);
        }
        catch (Exception ex)
        {
            Log.d(DEBUG_TAG, "Error setting service");
        }
        finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}

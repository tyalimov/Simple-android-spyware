package mobile.lazm.mobile_1_1;


import android.app.Activity;


import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class PermissionGetter extends AppCompatActivity
{
    Activity context;
    private int PERMISSION_CODE = 1;

    public PermissionGetter (Activity _context)
    {
        this.context = _context;
    }
    public void GetPermissions(String[] Permissions)
    {
        ActivityCompat.requestPermissions(context, Permissions, PERMISSION_CODE);
    }
    @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] Results)
     {

         switch (requestCode)
         {
             case 1:
             {
                 for (int res : Results)
                 {
                     if (res == PackageManager.PERMISSION_GRANTED)
                     {
                         Log.d("kek", "PERMISSION_GRANTED");
                     }
                 }
                 break;
             }
             default:
             {
                 Log.d("kek", "POWEL NAHUI");
                 break;
             }
         }

     }
}

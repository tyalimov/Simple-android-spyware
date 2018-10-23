package yalimov.course.collectors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import java.util.Date;

public class CallsCollector
{
    static String GetCalls(Context context)
    {
        long dialed;
        Cursor cursor;

        cursor = context.getContentResolver().query(Uri.parse("content://call_log/calls"),
                new String[] { CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE },
                null, null, "Calls._ID DESC");

        String result = "";

        do
        {
            String contact_id  = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
            String number      = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            String date        = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            String duration    = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
            String type        = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
            dialed             = cursor.getLong  (cursor.getColumnIndex(CallLog.Calls.DATE));

            result += "Type: [" + type + "]\nID: [" + contact_id + "]\nNumber: [" + number +
                    "]\nDuration: [" + duration + "]\nDate: [" + date + "]\n" +
                    "Dialed: [" + new Date(dialed).toString() + "]\n";
        }
        while (cursor.moveToNext());

        return result;
    }
}

package yalimov.course.collectors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.util.Date;

import yalimov.course.Common;

public class CallsCollector
{
    private static String CallsLog = "";

    public static String getCallsLog()
    {
        return CallsLog;
    }

    public static String getStringType(String type)
    {
        String result = "";
        switch (Integer.parseInt(type))
        {
            case CallLog.Calls.OUTGOING_TYPE:
            {
                return "OUTGOING";
            }
            case CallLog.Calls.INCOMING_TYPE:
            {
                return "INCOMING";
            }
            case CallLog.Calls.MISSED_TYPE:
            {
                return "MISSED";
            }
            default:
            {
                return "UNDEFINED";
            }
        }
    }

    public static void GetCalls(Context context)
    {

        Cursor cursor;

        cursor = context.getContentResolver().query(Uri.parse("content://call_log/calls"),
                new String[] { CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE },
                null, null, "Calls._ID DESC");
        cursor.moveToFirst();

        String result = "Calls history:\n" + Common.DELIMITER + "\n";

        do
        {
            String contact_id  = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
            String number      = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            String duration    = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
            String type        = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
            long dialed        = cursor.getLong  (cursor.getColumnIndex(CallLog.Calls.DATE));

            result += "ID: [" + contact_id + "]\nType: [" + getStringType(type) + "]\nNumber: [" + number +
                    "]\nDuration: [" + duration + "]\nDate: [" + new Date(dialed).toString() + "]\n" + Common.DELIMITER + "\n";
        }
        while (cursor.moveToNext());

        CallsLog = result;
    }
}

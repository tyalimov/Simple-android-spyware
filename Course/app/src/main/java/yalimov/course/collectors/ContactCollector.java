package yalimov.course.collectors;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import java.util.ArrayList;

import yalimov.course.Common;

public class ContactCollector
{
    private static String ContactsLog = "";

    public static String getContactsLog()
    {
        return ContactsLog;
    }

    public static void getContactList(Context context)
    {
        String result = "Contacts list:" + "\n";
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0)
        {
            while (cur != null && cur.moveToNext())
            {
                String id = cur.getString( cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (pCur != null)
                    {
                        while (pCur.moveToNext())
                        {
                            String phoneNo = pCur.getString(pCur.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));

                            result += Common.DELIMITER + "\n" + "ID: [" + id + "]\n" + "Name: [" + name + "]\n"
                                    + "Number: [" + phoneNo + "]\n";
                        }
                        pCur.close();
                    }
                }
            }
        }
        if(cur != null)
        {
            cur.close();
        }
        ContactsLog = result;
    }
}

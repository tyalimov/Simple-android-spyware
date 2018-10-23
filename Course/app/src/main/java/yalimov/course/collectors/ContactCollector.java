package yalimov.course.collectors;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import java.util.ArrayList;

public class ContactCollector
{
    public static ArrayList<Contact> getContactList(Context context)
    {
        ArrayList<Contact> contactList = new ArrayList<>();
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
                            contactList.add(new Contact(id, name, phoneNo));
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
        return contactList;
    }

    public static class Contact
    {
        public String getId()
        {
            return id;
        }
        public String getName()
        {
            return name;
        }
        public String getPhone()
        {
            return phone;
        }

        private String id;
        private String name;
        private String phone;

        Contact(String id, String name, String phone)
        {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }
    }
}

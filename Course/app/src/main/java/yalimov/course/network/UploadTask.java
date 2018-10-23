package yalimov.course.network;

import android.os.AsyncTask;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static yalimov.course.Constants.DBX_ACCESS_TOKEN;

class UploadTask extends AsyncTask<String, Void, FileMetadata>
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
            InputStream in = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            FileMetadata metadata = client.files().uploadBuilder("/test_" + Integer.toString(1) + ".txt").uploadAndFinish(in);
        }
        catch (Exception ex)
        {

        }
        return null;
    }
}

package yalimov.course.collectors;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

public class GalleryCollector
{

    private static ArrayList<String> EnumerateDeviceImages(Context context)
    {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = context.getContentResolver().query(uri, projection, null,null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext())
        {
            PathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }

    public static ArrayList<Bitmap> GetDeviceImagesBitmaps(Context context)
    {
        ArrayList<Bitmap> Result = new ArrayList<>();

        for (String path : EnumerateDeviceImages(context))
        {
            File imgFile = new File(path);
            if(imgFile.exists())
            {
                Result.add(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            }
        }
        return Result;
    }
}

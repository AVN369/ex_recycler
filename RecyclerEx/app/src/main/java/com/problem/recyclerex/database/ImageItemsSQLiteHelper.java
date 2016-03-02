package com.problem.recyclerex.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.problem.recyclerex.database.DatabaseConstants.ImageList;

import java.util.ArrayList;

/**
 * Created by avin on 03/03/16.
 */
public class ImageItemsSQLiteHelper {
    public static void insertImageItems(Context context, ArrayList<ImageItemModel> imageItemModels){
        if(imageItemModels == null  || imageItemModels.size() == 0){
            return;
        }

        for(ImageItemModel imageItemModel : imageItemModels){
            insertImageItem(context, imageItemModel);
        }
    }

    private static void insertImageItem(Context context, ImageItemModel imageItemModel){
        ContentValues values = new ContentValues();
        values.put(ImageList.ID, imageItemModel.getmId());
        values.put(ImageList.TITLE, imageItemModel.getmTitle());
        values.put(ImageList.URI, imageItemModel.getmUrl());

        try {
            long id = RecyclerExSQLiteHelper.getDatabase(context).insert(DatabaseConstants.Tables.IMAGE_LIST,
                    null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageItemModel> getImageItemModels(Context context){
        ArrayList<ImageItemModel> imageItemModels = null;

        Cursor cursor = null;
        try {
            cursor = RecyclerExSQLiteHelper.getDatabase(context).query(DatabaseConstants.Tables.IMAGE_LIST,
                    new String[]{ImageList.ID, ImageList.URI, ImageList.TITLE},
                    null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor != null && cursor.getCount() > 0){
            imageItemModels = new ArrayList<>();
            while(cursor.moveToNext()) {
                ImageItemModel imageItemModel = new ImageItemModel();
                imageItemModel.setmId(cursor.getString(0));
                imageItemModel.setmUrl(cursor.getString(1));
                imageItemModel.setmTitle(cursor.getString(2));
                imageItemModels.add(imageItemModel);
            }
        }

        if(cursor != null) {
            cursor.close();
        }


        return imageItemModels;
    }
}

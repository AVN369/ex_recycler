package com.problem.recyclerex.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by avin on 03/03/16.
 */
public class ImageItemsParser {
    public static ArrayList<ImageItemModel> getImageItemModels(JSONArray jsonArray){
        if(jsonArray == null || jsonArray.length() == 0){
            return null;
        }

        ArrayList<ImageItemModel> imageItemModels = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            ImageItemModel imageItemModel = getImageItemModel(jsonArray.optJSONObject(i));
            if (imageItemModel != null) {
                imageItemModels.add(imageItemModel);
            }
        }

        return imageItemModels;
    }

    public static ImageItemModel getImageItemModel(JSONObject jsonObject){
        ImageItemModel imageItemModel = null;

        if(jsonObject != null){
            String id = jsonObject.optString("id");
            String uri = jsonObject.optString("uri");
            String title = jsonObject.optString("title");

            if(title != null && !"".equals(title) && !"null".equals(title)){
                imageItemModel = new ImageItemModel(id, uri, title);
            }
        }

        return imageItemModel;
    }
}

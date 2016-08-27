package com.bjw.photogallery;

import android.net.Uri;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class FlickrFetchr {
    private static final String API_KEY = "8227568951aca5c8921bf4c18ee1f610";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL("https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=8227568951aca5c8921bf4c18ee1f610&format=json&nojsoncallback=1");
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection
                        .getResponseMessage() + ": with" + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems() {

        List<GalleryItem> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Logger.i(jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("falid");
        }
        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) {
        try {
            JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
            JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

            for (int i = 0; i < photoJsonArray.length(); i++) {
                JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

                GalleryItem item = new GalleryItem();
                item.setId(photoJsonObject.getString("id"));
                item.setCaption(photoJsonObject.getString("title"));

                if (!photoJsonObject.has("url_s")) {
                    continue;
                }

                item.setUrl(photoJsonObject.getString("url_s"));
                Logger.d(item.toString());
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.snugjar.truthordare;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainUtils {

    private static final String TAG = "MainUtils";

    public static List<Choices> loadChoices(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "choices.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadCouples(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "couples.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            //to shuffle the cards
            Collections.shuffle(profileList);

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadSiblings(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "siblings.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            //to shuffle the cards
            Collections.shuffle(profileList);

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadFriends(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "friends.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            //to shuffle the cards
            Collections.shuffle(profileList);

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadParents(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "parents.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            //to shuffle the cards
            Collections.shuffle(profileList);

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadExes(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "exes.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            //to shuffle the cards
            Collections.shuffle(profileList);

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Choices> loadThirtySix(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "thirtysix.json"));
            List<Choices> profileList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                Choices profile = gson.fromJson(array.getString(i), Choices.class);

                profileList.add(profile);
            }

            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is = null;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}

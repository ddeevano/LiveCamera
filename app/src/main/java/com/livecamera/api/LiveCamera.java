package com.livecamera.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LiveCamera implements Serializable {

    // Attributes..
    private String id;
    private String title;
    private Location location;
    private String embed;
    private int views;

    public LiveCamera(JSONObject object) throws JSONException {

        this.id = object.getString("id");
        this.title = object.getString("title");
        this.location = new Location(object.getJSONObject("location"));
        this.embed = object.getJSONObject("player").getJSONObject("live").getString("embed");
        this.views = object.getJSONObject("statistics").getInt("views");

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }

    public String getEmbed() {
        return embed;
    }

    public int getViews() {
        return views;
    }

}

package com.livecamera.api.interfaces;

import com.livecamera.api.LiveCamera;

import java.util.ArrayList;

public interface LiveCameraCallback {

    public abstract void onLiveCameraResponse(ArrayList<LiveCamera> cameras);
    public abstract void onFailed();

}

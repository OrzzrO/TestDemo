package com.me.geekpracticedemo.model.prefs;

/**
 * Created by user on 2017/7/19.
 */

public interface PreferenceHelper {


    boolean getNoImageState();

    void setCurrentItem(int index);

    int getCurrentItem();

    void setVersionPoint(boolean b);

    boolean getVersionPoint();

    boolean getAutoCacheState();

    void setNightModeState(boolean b);

    void setNoImageState(boolean b);

    boolean getNightModeState();


    void setAutoCacheState(boolean state);
}

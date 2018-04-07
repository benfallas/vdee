package vdee.vdee.mediaPlayer;

import java.util.ArrayList;

/**
 * Radio Station class to control the urls of each Radio stations
 */
public class RadioStationUrls {
    private int index;
    private ArrayList<String> urlStations;
    private static RadioStationUrls mRadioStationUrls;

    private RadioStationUrls(){
        index = 0;
        urlStations = new ArrayList<>();
        urlStations.add("http://www.vdee.org:8000/salinas");
    }

    public static RadioStationUrls initRadioStationUrl(){
       if(mRadioStationUrls == null){
           mRadioStationUrls = new RadioStationUrls();
       }
       return mRadioStationUrls;
    }

    public String getCurrentTrack(){
        return urlStations.get(index);
    }
}

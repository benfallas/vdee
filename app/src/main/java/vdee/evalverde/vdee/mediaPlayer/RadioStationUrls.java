package vdee.evalverde.vdee.mediaPlayer;

import java.util.ArrayList;

/**
 * Radio Station class to control the urls of each Radio stations
 */
public class RadioStationUrls {
    private int index;
    private ArrayList<RadioStation> urlStations;
    private static RadioStationUrls mRadioStationUrls;

    private RadioStationUrls(){
        index = 0;
        urlStations = new ArrayList<>();
        RadioStation radioStation = new RadioStation("http://www.vdee.org:8000/salinas");
        urlStations.add(radioStation);
    }

    public static RadioStationUrls initRadioStationUrl(){
       if(mRadioStationUrls == null){
           mRadioStationUrls = new RadioStationUrls();
       }
       return mRadioStationUrls;
    }



    /**
     * Returns the current index
     */
    public RadioStation getCurrentTrack(){
        return urlStations.get(index);
    }
}

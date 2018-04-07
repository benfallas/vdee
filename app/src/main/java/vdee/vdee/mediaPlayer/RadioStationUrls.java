package vdee.vdee.mediaPlayer;

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
        RadioStation radioStation = new RadioStation("http://www.vdee.org:8000/salinas", "Voz del Evangelio Eterno");
        urlStations.add(radioStation);
        RadioStation radioStation2 = new RadioStation("http://107.215.165.202:8000/resplandecer", "Radio Resplandecer");
        urlStations.add(radioStation2);
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

    /**
     * PreviousTrack decreases the index by one
     */
    public void previousTrack() {
        if (index == 0) {
            index = urlStations.size() - 1;
        } else {
            index--;
        }
    }

    /**
     * NextTrack increases the index by one
     */
    public void nextTrack() {
        if (index == urlStations.size() - 1) {
            index = 0;
        } else {
            index++;
        }
    }
}

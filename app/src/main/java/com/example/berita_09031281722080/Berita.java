package com.example.berita_09031281722080;

/** CREATE BY :
 *  NAME : NUR HANNA
 *  NIM : 09031281722080
 *  NOV 2019
 */

public class Berita {
    private String mSumber;
    private String mJudul;
    private String mWaktu;
    private String mUrl;
    private String mImage;

    public Berita(String sumber,String judul,String waktu,String url,String image){
        mSumber = sumber;
        mJudul = judul;
        mWaktu = waktu;
        mUrl = url;
        mImage = image;

    }

    public String getSumber(){
        return mSumber;
    }

    public String getJudul(){
        return mJudul;
    }

    public String getWaktu(){
        return mWaktu;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getUrlImage(){
        return mImage;
    }
}

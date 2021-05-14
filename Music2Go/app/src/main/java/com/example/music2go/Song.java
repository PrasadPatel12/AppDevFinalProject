package com.example.music2go;

public class Song {
    String title;
    String artwork;
    String url;

    public Song(String title, String artwork, String url) {
        this.title = title;
        this.artwork = artwork;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

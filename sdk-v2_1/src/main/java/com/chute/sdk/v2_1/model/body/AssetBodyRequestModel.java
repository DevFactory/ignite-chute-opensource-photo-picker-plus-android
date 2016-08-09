package com.chute.sdk.v2_1.model.body;

import java.util.List;

public class AssetBodyRequestModel {

    /*
    Hearts, Tags, Votes -> not updating
     */

    private String caption;
    private int votes;
    private int hearts;
    private List<String> tags;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override public String toString() {
        return "AssetBodyRequestModel{" +
                "caption='" + caption + '\'' +
                ", votes=" + votes +
                ", hearts=" + hearts +
                ", tags=" + tags +
                '}';
    }
}

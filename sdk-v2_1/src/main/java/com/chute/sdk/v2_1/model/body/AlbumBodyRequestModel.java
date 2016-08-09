package com.chute.sdk.v2_1.model.body;

public class AlbumBodyRequestModel {

    /*
    Album name
     */
    private String name;
    /*
    Asset Id
     */
    private String coverAssetId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverAssetId() {
        return coverAssetId;
    }

    public void setCoverAssetId(String coverAssetId) {
        this.coverAssetId = coverAssetId;
    }

    @Override
    public String toString() {
        return "AlbumBodyRequestModel{" +
                "name='" + name + '\'' +
                ", coverAssetId='" + coverAssetId + '\'' +
                '}';
    }
}

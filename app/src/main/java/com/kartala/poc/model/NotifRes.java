package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotifRes {
    @SerializedName("multicast_id")
    @Expose
    private Long multicastId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    private Integer canonicalIds;

    public NotifRes(Long multicastId, Integer success, Integer failure, Integer canonicalIds) {
        this.multicastId = multicastId;
        this.success = success;
        this.failure = failure;
        this.canonicalIds = canonicalIds;
    }

    public Long getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(Long multicastId) {
        this.multicastId = multicastId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(Integer canonicalIds) {
        this.canonicalIds = canonicalIds;
    }
}

package com.example.retrofitexampleapplication.data;

import com.google.gson.annotations.SerializedName;

public class Purchases {

    @SerializedName("willRenew")
    private boolean willRenew;
    @SerializedName("purchaseToken")
    private String purchaseToken;
    @SerializedName("isGracePeriod")
    private boolean isGracePeriod;
    @SerializedName("isEntitlementActive")
    private boolean isEntitlementActive;
    @SerializedName("activeUntilMillisec")
    private Long activeUntilMillisec;
    @SerializedName("isFreeTrial")
    private boolean isFreeTrial;
    @SerializedName("sku")
    private String sku;
    @SerializedName("isAccountHold")
    private boolean isAccountHold;

    public boolean isWillRenew() {
        return willRenew;
    }

    public void setWillRenew(boolean willRenew) {
        this.willRenew = willRenew;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }

    public void setPurchaseToken(String purchaseToken) {
        this.purchaseToken = purchaseToken;
    }

    public boolean isGracePeriod() {
        return isGracePeriod;
    }

    public void setGracePeriod(boolean gracePeriod) {
        isGracePeriod = gracePeriod;
    }

    public boolean isEntitlementActive() {
        return isEntitlementActive;
    }

    public void setEntitlementActive(boolean entitlementActive) {
        isEntitlementActive = entitlementActive;
    }

    public Long getActiveUntilMillisec() {
        return activeUntilMillisec;
    }

    public void setActiveUntilMillisec(Long activeUntilMillisec) {
        this.activeUntilMillisec = activeUntilMillisec;
    }

    public boolean isFreeTrial() {
        return isFreeTrial;
    }

    public void setFreeTrial(boolean freeTrial) {
        isFreeTrial = freeTrial;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isAccountHold() {
        return isAccountHold;
    }

    public void setAccountHold(boolean accountHold) {
        isAccountHold = accountHold;
    }

}

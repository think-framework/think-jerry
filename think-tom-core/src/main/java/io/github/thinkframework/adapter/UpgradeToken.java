package io.github.thinkframework.adapter;

import javax.servlet.http.HttpUpgradeHandler;

public class UpgradeToken {
    private HttpUpgradeHandler httpUpgradeHandler;

    public UpgradeToken(HttpUpgradeHandler httpUpgradeHandler) {
        this.httpUpgradeHandler = httpUpgradeHandler;
    }

    public HttpUpgradeHandler getHttpUpgradeHandler() {
        return httpUpgradeHandler;
    }

    public void setHttpUpgradeHandler(HttpUpgradeHandler httpUpgradeHandler) {
        this.httpUpgradeHandler = httpUpgradeHandler;
    }
}

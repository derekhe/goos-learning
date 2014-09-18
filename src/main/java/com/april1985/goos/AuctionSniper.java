package com.april1985.goos;

/**
 * Created by sche on 9/18/14.
 */
public class AuctionSniper {
    private SniperListener sniperListener;

    public AuctionSniper(SniperListener sniperListener) {
        this.sniperListener = sniperListener;
    }

    public void auctionClosed() {
        sniperListener.sniperLost();
    }
}

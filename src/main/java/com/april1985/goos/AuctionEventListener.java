package com.april1985.goos;

import java.util.EventListener;

/**
 * Created by sche on 9/18/14.
 */
public interface AuctionEventListener extends EventListener {
    enum PriceSource {
        FromSniper, FromOtherBidder;
    }

    void auctionClosed();

    void currentPrice(int price, int increment, PriceSource fromOtherBidder);
}

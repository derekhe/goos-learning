package com.april1985.goos;

/**
 * Created by sche on 9/18/14.
 */
public interface AuctionEventListener {
    void auctionClosed();
    void currentPrice(int price, int increment);
}

package com.april1985.goos;

/**
 * Created by sche on 9/18/14.
 */
public class AuctionSniper implements AuctionEventListener {
    private SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
    }

    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource fromOtherBidder) {
        auction.bid(price + increment);
        sniperListener.sniperBidding();
    }
}

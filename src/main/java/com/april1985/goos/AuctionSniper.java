package com.april1985.goos;

/**
 * Created by sche on 9/18/14.
 */
public class AuctionSniper implements AuctionEventListener {
    private SniperListener sniperListener;
    private final Auction auction;
    private String itemId;
    private SniperSnapshot snapshot;

    public AuctionSniper(Auction auction, SniperListener sniperListener, String itemId) {
        this.sniperListener = sniperListener;
        this.auction = auction;
        this.itemId = itemId;
        this.snapshot = SniperSnapshot.joining(this.itemId);
    }

    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch (priceSource) {
            case FromSniper:
                snapshot = snapshot.winning(price);
                break;
            case FromOtherBidder:
                int bid = price + increment;
                auction.bid(bid);
                snapshot = snapshot.bidding(price, bid);
                break;
        }

        notifyChange();
    }

    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }
}

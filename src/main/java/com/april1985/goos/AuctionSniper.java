package com.april1985.goos;

/**
 * Created by sche on 9/18/14.
 */
public class AuctionSniper implements AuctionEventListener {
    private SniperListener sniperListener;
    private final Auction auction;
    private boolean isWinning = false;
    private String itemId;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
    }

    public void auctionClosed() {

        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            sniperListener.sniperWinning();
        } else {

            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperStateChanged(new SniperSnapshot(itemId, price, bid, SniperState.BIDDING));
        }
    }
}

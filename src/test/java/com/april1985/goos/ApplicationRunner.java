package com.april1985.goos;

import static com.april1985.goos.FakeAuctionServer.XMPP_HOSTNAME;

/**
 * Created by sche on 9/17/14.
 */
public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    private static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";
    private AuctionSniperDriver driver;
    private String itemId;

    public void startBiddingIn(final FakeAuctionServer auction) {
        itemId = auction.getItemId();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showSniperStatus("Joining");
    }

    public void showSniperHasLostAuction() {
        driver.showSniperStatus("Lost");
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }

    }

    public void hasShownSniperIsBidding() {
        driver.showSniperStatus("Bidding");
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showSniperStatus(itemId, winningBid, winningBid, "Winning");
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showSniperStatus(itemId, lastPrice, lastBid, "Bidding");
    }

    public void showSniperHasWonAuction(int lastPrice) {
        driver.showSniperStatus(itemId, lastPrice, lastPrice, "Won");
    }
}

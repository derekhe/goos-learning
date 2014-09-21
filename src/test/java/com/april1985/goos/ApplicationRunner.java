package com.april1985.goos;

import static com.april1985.goos.FakeAuctionServer.XMPP_HOSTNAME;
import static com.april1985.goos.MainWindow.STATUS_JOINING;
import static com.april1985.goos.MainWindow.STATUS_LOST;

/**
 * Created by sche on 9/17/14.
 */
public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    private static final String SNPIER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";
    private AuctionSniperDriver driver;
    private String itemId;

    public void startBiddingIn(final FakeAuctionServer auction) {
        itemId = auction.getItemId();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNPIER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showSniperStatus(STATUS_JOINING);
    }

    public void showSniperHasLostAuction() {
        driver.showSniperStatus(STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }

    }

    public void hasShownSniperIsBidding() {
        driver.showSniperStatus(MainWindow.STATUS_BIDDING);
    }

    public void hasShownSniperIsWinning() {
        driver.showSniperStatus(MainWindow.STATUS_WINNING);
    }

    public void showSniperHasWonAuction() {
        driver.showSniperStatus(MainWindow.STATUS_WON);
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showSniperStatus(itemId, winningBid, winningBid, MainWindow.STATUS_WINNING);
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
    }

    public void showSniperHasWonAuction(int lastPrice) {
        driver.showSniperStatus(itemId, lastPrice, lastPrice, MainWindow.STATUS_WON);
    }
}

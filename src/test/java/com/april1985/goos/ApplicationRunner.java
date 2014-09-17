package com.april1985.goos;

import static com.april1985.goos.FakeAuctionServer.XMPP_HOSTNAME;
import static com.april1985.goos.MainWindow.STATUS_JOINING;
import static com.april1985.goos.MainWindow.STATUS_LOST;

/**
 * Created by sche on 9/17/14.
 */
public class ApplicationRunner {
    private static final String SNIPER_ID = "sniper";
    private static final String SNPIER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
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
}

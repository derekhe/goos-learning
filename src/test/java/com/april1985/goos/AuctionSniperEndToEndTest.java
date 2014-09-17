package com.april1985.goos;

import com.april1985.goos.ApplicationRunner;
import com.april1985.goos.FakeAuctionServer;
import org.junit.After;
import org.junit.Test;

/**
 * Created by sche on 9/17/14.
 */
public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showSniperHasLostAuction();
    }

    @After
    public void stopAuction()
    {
        auction.stop();
    }

    @After public void stopApplication()
    {
        application.stop();
    }
}
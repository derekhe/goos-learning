package com.april1985.goos;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.april1985.goos.AuctionEventListener.PriceSource.FromOtherBidder;
import static com.april1985.goos.AuctionEventListener.PriceSource.FromSniper;

/**
 * Created by sche on 9/18/14.
 */
@RunWith(JMock.class)
public class AuctionSniperTest {
    private String ITEM_ID;
    private final Mockery context = new Mockery();
    private final Auction auction = context.mock(Auction.class);
    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
    private final States sniperStates = context.states("sniper");

    @Test
    public void reportsLostWhenAuctionClosesImmediately() {
        context.checking(new Expectations() {
            {
                one(sniperListener).sniperLost();
            }
        });

        sniper.auctionClosed();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() {
        context.checking(new Expectations() {
            {
                ignoring(auction);
                allowing(sniperListener).sniperBidding(with(any(SniperState.class)));
                then(sniperStates.is("bidding"));
                atLeast(1).of(sniperListener).sniperLost();
                when(sniperStates.is("bidding"));
            }
        });

        sniper.currentPrice(123, 45, FromOtherBidder);
        sniper.auctionClosed();
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {
            {
                ignoring(auction);
                allowing(sniperListener).sniperWinning();
                then(sniperStates.is("winning"));
                atLeast(1).of(sniperListener).sniperWon();
                when(sniperStates.is("winning"));
            }
        });

        sniper.currentPrice(123, 45, FromSniper);
        sniper.auctionClosed();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
        final int price = 1001;
        final int increment = 25;
        final int bid = price + increment;
        context.checking(new Expectations() {
            {
                one(auction).bid(price + increment);
                atLeast(1).of(sniperListener).sniperBidding(new SniperState(ITEM_ID, price, bid));
            }
        });

        sniper.currentPrice(price, increment, FromOtherBidder);
    }

    @Test
    public void reportsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperWinning();
        }});

        sniper.currentPrice(123, 45, FromSniper);
    }
}

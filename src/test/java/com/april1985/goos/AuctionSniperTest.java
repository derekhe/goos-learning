package com.april1985.goos;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.april1985.goos.AuctionEventListener.PriceSource.FromOtherBidder;
import static com.april1985.goos.AuctionEventListener.PriceSource.FromSniper;
import static com.april1985.goos.SniperState.*;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by sche on 9/18/14.
 */
@RunWith(JMock.class)
public class AuctionSniperTest {
    private String ITEM_ID = "item id";
    private final Mockery context = new Mockery();
    private final Auction auction = context.mock(Auction.class);
    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener, ITEM_ID);
    private final States sniperStates = context.states("sniper");

    @Test
    public void reportsLostWhenAuctionClosesImmediately() {
        context.checking(new Expectations() {
            {
                one(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
            }
        });

        sniper.auctionClosed();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() {
        context.checking(new Expectations() {
            {
                ignoring(auction);
                allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BIDDING)));
                then(sniperStates.is("bidding"));
                atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
                when(sniperStates.is("bidding"));
            }
        });

        sniper.currentPrice(123, 45, FromOtherBidder);
        sniper.auctionClosed();
    }

    private Matcher<SniperSnapshot> aSniperThatIs(final SniperState state) {
        return new FeatureMatcher<SniperSnapshot, SniperState>(
                equalTo(state), "sniper that is ", "was") {
            @Override
            protected SniperState featureValueOf(SniperSnapshot actual) {
                return actual.state;
            }
        };
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {
            {
                ignoring(auction);
                allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(WINNING)));
                then(sniperStates.is("winning"));
                atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(WON)));
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
                atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(ITEM_ID, price, bid, BIDDING));
            }
        });

        sniper.currentPrice(price, increment, FromOtherBidder);
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {
            {
                ignoring(auction);
                allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BIDDING)));
                then(sniperStates.is("bidding"));

                atLeast(1).of(sniperListener).sniperStateChanged(
                        new SniperSnapshot(ITEM_ID, 135, 135, WINNING));
                when(sniperStates.is("bidding"));
            }
        });

        sniper.currentPrice(123, 12, FromOtherBidder);
        sniper.currentPrice(135, 45, FromSniper);
    }
}

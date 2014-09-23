package com.april1985.goos;

/**
 * Created by sche on 9/23/14.
 */
public enum Column {
    ITEM_IDENTIFIER("Item") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {
            return snapshot.itemId;
        }
    },

    LAST_PRICE("Last Price") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {
            return snapshot.lastPrice;
        }
    },

    LAST_BID("Last Bid") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {
            return snapshot.lastBid;
        }
    },

    SNIPER_STATE("State") {
        @Override
        public Object valueIn(SniperSnapshot snapshot) {
            return MainWindow.SnipersTableModel.textFor(snapshot.state);
        }
    };


    public final String name;

    public static Column at(int offset) {
        return values()[offset];
    }

    private Column(String name) {
        this.name = name;
    }

    abstract public Object valueIn(SniperSnapshot snapshot);
}

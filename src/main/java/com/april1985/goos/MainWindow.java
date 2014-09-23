package com.april1985.goos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * Created by sche on 9/17/14.
 */
public class MainWindow extends JFrame {
    private static final String SNIPER_TABLE_NAME = "table status";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    private final SnipersTableModel snipers = new SnipersTableModel();

    public MainWindow() {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable sniperTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new JScrollPane(sniperTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPER_TABLE_NAME);
        return snipersTable;
    }

    public void sniperStatusChanged(SniperSnapshot sniperSnapshot) {
        snipers.sniperStatusChanged(sniperSnapshot);
    }

    public enum Column {
        ITEM_IDENTIFIER,
        LAST_PRICE,
        LAST_BID,
        SNIPER_STATE;

        public static Column at(int offset) {
            return values()[offset];
        }
    }

    public static class SnipersTableModel extends AbstractTableModel {
        private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
        private SniperSnapshot sniperSnapshot = STARTING_UP;
        private static String[] STATUS_TEXT = {"Joining", "Bidding", "Winning", "Lost", "Won"};

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return Column.values().length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (Column.at(columnIndex)) {
                case ITEM_IDENTIFIER:
                    return sniperSnapshot.itemId;
                case LAST_PRICE:
                    return sniperSnapshot.lastPrice;
                case LAST_BID:
                    return sniperSnapshot.lastBid;
                case SNIPER_STATE:
                    return textFor(sniperSnapshot.state);
                default:
                    throw new IllegalArgumentException("No column at " + columnIndex);
            }
        }

        private String textFor(SniperState state) {
            return STATUS_TEXT[state.ordinal()];
        }

        public void sniperStatusChanged(SniperSnapshot newSniperSnapshot) {
            sniperSnapshot = newSniperSnapshot;
            fireTableRowsUpdated(0, 0);
        }
    }
}

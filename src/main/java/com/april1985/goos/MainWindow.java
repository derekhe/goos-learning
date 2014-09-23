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
    private final SnipersTableModel snipers;

    public MainWindow(SnipersTableModel snipers) {
        super("Auction Sniper");
        this.snipers = snipers;
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

    public static class SnipersTableModel extends AbstractTableModel implements SniperListener {
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
            return Column.at(columnIndex).valueIn(sniperSnapshot);
        }

        public static String textFor(SniperState state) {
            return STATUS_TEXT[state.ordinal()];
        }

        @Override
        public void sniperStateChanged(SniperSnapshot newSniperSnapshot) {
            sniperSnapshot = newSniperSnapshot;
            fireTableRowsUpdated(0, 0);
        }
    }
}

package com.april1985.goos;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * Created by sche on 9/17/14.
 */
public class MainWindow extends JFrame {
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_WINNING = "Winning";
    public static final String STATUS_WON = "Won";
    private static final String SNIPER_TABLE_NAME = "table status";
    static String STATUS_JOINING = "Joining";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_NAME = "sniper status";
    public static final String STATUS_LOST = "Lost";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);
    private final SnipersTableModel snipers = new SnipersTableModel();

    private JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

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

    public void showStatus(String statusText) {
        snipers.setStatusText(statusText);
    }

    public class SnipersTableModel extends AbstractTableModel {
        private String statusText = STATUS_JOINING;

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return statusText;
        }

        public void setStatusText(String newStatusText) {
            this.statusText = newStatusText;
            fireTableRowsUpdated(0, 0);
        }
    }
}

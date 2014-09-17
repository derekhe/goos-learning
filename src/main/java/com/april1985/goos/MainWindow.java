package com.april1985.goos;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

import static com.april1985.goos.Main.MAIN_WINDOW_NAME;
import static com.april1985.goos.Main.SNIPER_STATUS_NAME;
import static com.april1985.goos.Main.STATUS_JOINING;

/**
 * Created by sche on 9/17/14.
 */
public class MainWindow extends JFrame {
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    private JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

    public MainWindow()
    {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);
        add(sniperStatus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
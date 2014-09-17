package com.april1985.goos;

import javax.swing.*;

import static com.april1985.goos.Main.MAIN_WINDOW_NAME;

/**
 * Created by sche on 9/17/14.
 */
public class MainWindow extends JFrame {
    public MainWindow()
    {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

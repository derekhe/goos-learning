package com.april1985.goos;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by sche on 9/18/14.
 */
public class AuctionMessageTranslator implements MessageListener {
    private AuctionEventListener listener;

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    public void processMessage(Chat chat, Message message) {
        listener.auctionClosed();
    }
}

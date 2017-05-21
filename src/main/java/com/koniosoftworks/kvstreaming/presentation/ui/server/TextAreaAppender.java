package com.koniosoftworks.kvstreaming.presentation.ui.server;

import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.io.Serializable;

/**
 * Created by max on 5/20/17.
 */
public final class TextAreaAppender extends AbstractAppender {

    public static TextArea logTextArea;

    protected TextAreaAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @Override
    public void append(LogEvent event) {

        final String logMessage = event.getMessage().toString();

        logTextArea.setText( logTextArea.getText() + logMessage );
    }
}
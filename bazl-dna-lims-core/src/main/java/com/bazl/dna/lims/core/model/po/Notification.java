package com.bazl.dna.lims.core.model.po;

import java.io.Serializable;

public class Notification implements Serializable {

    private String notification;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notification='" + notification + '\'' +
                '}';
    }
}

package io.baselogic.batch.retry.domain;

public class TextLineItem {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TextLineItem{" +
                "id='" + id + '\'' +
                '}';
    }

} // The End...

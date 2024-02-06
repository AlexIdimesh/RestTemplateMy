package org.example.model;

public class EventsTagEntity {

    private Long id;

    private String tagName;

    private String tagAuthor;

    public EventsTagEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagAuthor() {
        return tagAuthor;
    }

    public void setTagAuthor(String tagAuthor) {
        this.tagAuthor = tagAuthor;
    }

    @Override
    public String toString() {
        return "EventsTagEntity{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                ", tagAuthor='" + tagAuthor + '\'' +
                '}';
    }
}
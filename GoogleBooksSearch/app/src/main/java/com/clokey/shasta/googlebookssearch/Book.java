package com.clokey.shasta.googlebookssearch;

/**
 * Created by Shasta on 2/9/2018.
 */

public class Book
{
    private String title, author, publisher, description, previewUrl, thumbnailLink;

    public Book(String title, String author, String publisher, String description, String previewUrl, String thumbnailLink)
    {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.previewUrl = previewUrl;
        this.thumbnailLink = thumbnailLink;
    }

    //Getters for all the private variables
    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getDescription()
    {
        return description;
    }

    public String getPreviewUrl()
    {
        return previewUrl;
    }

    public String getThumbnailLink()
    {
        return thumbnailLink;
    }
}

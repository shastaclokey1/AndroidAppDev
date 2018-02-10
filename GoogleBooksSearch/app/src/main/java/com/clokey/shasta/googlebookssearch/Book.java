package com.clokey.shasta.googlebookssearch;

/**
 * Created by Shasta on 2/9/2018.
 */

public class Book
{
    private String title, author, publisher, description;

    public Book(String title, String author, String publisher, String description)
    {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
    }

    //Getters and Setters for all the private variables
    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}

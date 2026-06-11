package com.CareVibe.model;

public class CommunityPost {
    private String username;
    private String content;

    public CommunityPost(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() { return username; }
    public String getContent() { return content; }
}
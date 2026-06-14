package com.carevibe.carevibebe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "community_posts")
public class CommunityPost extends BaseEntity {

    @NotBlank(message = "Nama pengirim tidak boleh kosong")
    private String senderName;

    @NotBlank(message = "Isi cerita tidak boleh kosong")
    private String content;

    // Getter & Setter Manual (Encapsulation)
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
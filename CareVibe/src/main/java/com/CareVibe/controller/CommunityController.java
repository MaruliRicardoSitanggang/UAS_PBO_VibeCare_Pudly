package com.carevibe.carevibebe.controller;

import com.carevibe.carevibebe.entity.CommunityPost;
import com.carevibe.carevibebe.service.CommunityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping
    public List<CommunityPost> loadAllPosts() {
        return communityService.getAllPosts();
    }

    @PostMapping
    public CommunityPost uploadPost(@Valid @RequestBody CommunityPost post) {
        return communityService.savePost(post);
    }
}
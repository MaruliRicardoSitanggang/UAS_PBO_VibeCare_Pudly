package com.carevibe.carevibebe.service;

import com.carevibe.carevibebe.entity.CommunityPost;
import com.carevibe.carevibebe.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    public List<CommunityPost> getAllPosts() {
        return communityRepository.findAll();
    }

    public CommunityPost savePost(CommunityPost post) {
        return communityRepository.save(post);
    }
}
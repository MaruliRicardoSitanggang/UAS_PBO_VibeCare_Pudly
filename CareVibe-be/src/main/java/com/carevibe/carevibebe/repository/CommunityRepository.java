package com.carevibe.carevibebe.repository;

import com.carevibe.carevibebe.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityPost, Long> {
}
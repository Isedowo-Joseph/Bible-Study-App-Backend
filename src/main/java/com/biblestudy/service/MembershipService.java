package com.biblestudy.service;

import com.biblestudy.model.Membership;
import com.biblestudy.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    // Create or Update a Membership
    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    // Retrieve a Membership by ID
    public Optional<Membership> getMembershipById(Long id) {
        return membershipRepository.findById(id);
    }

    // Retrieve all Memberships
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    // Delete a Membership by ID
    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }

    // Update a Membership (partial update, e.g., specific fields)
    public void updateMembership(Long id, Membership updatedMembership) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found with id " + id));

        membership.setUserId(updatedMembership.getUserId());
        membership.setBibleStudySessionId(updatedMembership.getBibleStudySessionId());
        membershipRepository.save(membership);
    }
}

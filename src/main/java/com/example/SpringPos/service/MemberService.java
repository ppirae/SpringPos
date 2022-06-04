package com.example.SpringPos.service;

import com.example.SpringPos.domain.Member;
import com.example.SpringPos.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        memberRepository.insert(member);
    }

    public List<Member> findMembers() {
        return memberRepository.selectAll();
    }

    public Member findByEmail(String email) {
        return memberRepository.selectByEmail(email);
    }

    public Member findById(Long id) {
        return memberRepository.selectById(id);
    }
}

package com.example.SpringPos.service;

import com.example.SpringPos.domain.Member;
import com.example.SpringPos.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String email, String pwd) {
        Member findMember = memberRepository.selectByEmail(email);

        if (findMember == null) {
            return null;
        } else if (findMember.getPwd().equals(pwd)) {
            return findMember;
        } else {
            return null;
        }
    }
}

package com.example.SpringPos.controller;

import com.example.SpringPos.domain.Member;
import com.example.SpringPos.domain.MemberRank;
import com.example.SpringPos.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("memberRanks")
    public List<MemberRank> memberRanks() {
        List<MemberRank> memberRanks = new ArrayList<>();
        memberRanks.add(new MemberRank("MANAGER", "매니저"));
        memberRanks.add(new MemberRank("STAFF", "직원"));
        return memberRanks;
    }

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("members/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

package hello.hellospring.controller

import hello.hellospring.domain.Member
import hello.hellospring.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(val memberService: MemberService) {

    @GetMapping("/members/new")
    fun createForm(): String {
        return "members/create-member-form"
    }

    @PostMapping("/members/new")
    fun create(form: MemberForm): String {
        val member = Member()
        member.name = form.name

        memberService.join(member)

        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members = memberService.findMembers()
        model["members"] = members
        return "members/member-list"
    }
}
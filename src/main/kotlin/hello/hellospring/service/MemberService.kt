package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository

class MemberService(private val memberRepository: MemberRepository) {

    /*
    회원 가입
     */
    fun join(member: Member): Long {
        // 중복 회원 검증
        validateDuplicateMember(member)

        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        check(memberRepository.findByName(member.name) == null) { "이미 존재하는 회원입니다." }
    }

    /*
    전체 회원 조회
     */
    fun findMembers(): List<Member?> {
        return memberRepository.findAll()
    }

    fun findOne(memberId: Long): Member? {
        return memberRepository.findById(memberId)
    }
}
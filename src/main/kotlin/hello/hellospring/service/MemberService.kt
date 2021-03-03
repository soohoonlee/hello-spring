package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemoryMemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(val memoryMemberRepository: MemoryMemberRepository) {

    /*
    회원 가입
     */
    fun join(member: Member): Long {
        // 중복 회원 검증
        validateDuplicateMember(member)

        memoryMemberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        check(memoryMemberRepository.findByName(member.name) == null) { "이미 존재하는 회원입니다." }
    }

    /*
    전체 회원 조회
     */
    fun findMembers(): List<Member> {
        return memoryMemberRepository.findAll()
    }

    fun findOne(memberId: Long): Member? {
        return memoryMemberRepository.findById(memberId)
    }
}
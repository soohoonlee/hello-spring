package hello.hellospring.service

import hello.hellospring.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = ALL)
internal class MemberServiceIntegrationTest(val memberService: MemberService) {

    @DisplayName(value = "회원가입")
    @Test
    fun join() {
        //given
        val member = Member()
        member.name = "hello"

        //when
        val saveId = memberService.join(member)

        //then
        val findMember = memberService.findOne(saveId)
        assertThat(member.name).isEqualTo(findMember?.name)
    }

    @DisplayName(value = "중복 회원 예외")
    @Test
    internal fun duplicateMemberException() {
        //given
        val member1 = Member()
        member1.name = "spring"

        val member2 = Member()
        member2.name = "spring"

        //when
        memberService.join(member1)
        assertThatThrownBy { memberService.join(member2) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("이미 존재하는 회원입니다.")

        //then
    }
}
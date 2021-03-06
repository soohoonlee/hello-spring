package hello.hellospring

import hello.hellospring.repository.JpaMemberRepository
import hello.hellospring.repository.MemberRepository
import hello.hellospring.service.MemberService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class SpringConfig(val em: EntityManager) {

    @Bean
    fun memberService(): MemberService {
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository(): MemberRepository {
        return JpaMemberRepository(em)
    }
}
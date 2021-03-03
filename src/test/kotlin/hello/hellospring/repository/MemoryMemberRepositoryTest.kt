package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class MemoryMemberRepositoryTest {

    private var memoryMemberRepository = MemoryMemberRepository()

    @AfterEach
    internal fun afterEach() {
        memoryMemberRepository.clearStore()
    }

    @Test
    internal fun save() {
        val member = Member(name = "spring")
        memoryMemberRepository.save(member)

        val result = memoryMemberRepository.findById(member.id)
        assertThat(result).isEqualTo(member)
    }

    @Test
    internal fun findByName() {
        val member1 = Member(name = "spring1")
        memoryMemberRepository.save(member1)

        val member2 = Member(name = "spring2")
        memoryMemberRepository.save(member2)

        val result = memoryMemberRepository.findByName("spring1")
        assertThat(result).isEqualTo(member1)
    }

    @Test
    internal fun findAll() {
        val member1 = Member(name = "spring1")
        memoryMemberRepository.save(member1)

        val member2 = Member(name = "spring2")
        memoryMemberRepository.save(member2)

        val result = memoryMemberRepository.findAll()
        assertThat(result).hasSize(2)
    }
}
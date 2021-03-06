package hello.hellospring.repository

import hello.hellospring.domain.Member
import javax.persistence.EntityManager
import javax.persistence.NoResultException

class JpaMemberRepository(private val em: EntityManager): MemberRepository {
    override fun save(member: Member): Member {
        em.persist(member)
        return member
    }

    override fun findById(id: Long): Member? {
        return em.find(Member::class.java, id)
    }

    override fun findByName(name: String): Member? {
        return try {
            em.createQuery("select m from Member m where m.name = :name", Member::class.java)
                .setParameter("name", name)
                .singleResult
        } catch (nre: NoResultException) {
            null
        }

    }

    override fun findAll(): List<Member?> {
        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }
}
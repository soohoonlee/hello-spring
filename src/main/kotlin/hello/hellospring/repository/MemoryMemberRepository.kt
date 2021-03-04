package hello.hellospring.repository

import hello.hellospring.domain.Member

class MemoryMemberRepository : MemberRepository {
    private var store: HashMap<Long, Member> = HashMap()
    private var sequence: Long = 0L

    override fun save(member: Member): Member {
        member.id = ++sequence
        store[member.id] = member
        return member
    }

    override fun findById(id: Long): Member? {
        return store[id]
    }

    override fun findByName(name: String): Member? {
        return store.values.find { it.name == name }
    }

    override fun findAll(): List<Member> {
        return ArrayList(store.values)
    }

    fun clearStore() {
        store.clear()
    }
}
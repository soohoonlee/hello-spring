package hello.hellospring.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0,
    var name: String = ""
)

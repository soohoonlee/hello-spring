package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import javax.sql.DataSource

class JdbcTemplateMemberRepository(dataSource: DataSource) : MemberRepository {

    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun save(member: Member): Member {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")

        val parameters: MutableMap<String, Any> = HashMap()
        parameters["name"] = member.name

        val key = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
        member.id = key as Long
        return member
    }

    override fun findById(id: Long): Member? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM member WHERE id = ?", memberRowMapper(), id)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findByName(name: String): Member? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM member WHERE name = ?", memberRowMapper(), name)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findAll(): List<Member?> {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper())
    }

    private fun memberRowMapper(): RowMapper<Member> {
        return RowMapper { rs, rowNum ->
            Member(
                rs.getLong("id"), rs.getString("name")
            )
        }
    }
}
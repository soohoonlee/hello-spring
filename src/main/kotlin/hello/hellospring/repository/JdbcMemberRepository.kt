package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement.RETURN_GENERATED_KEYS
import javax.sql.DataSource

class JdbcMemberRepository(private val dataSource: DataSource) : MemberRepository {
    override fun save(member: Member): Member {
        var conn: Connection? = null
        var pStmt: PreparedStatement? = null
        var rs: ResultSet? = null
        val sql = "INSERT INTO member(name) VALUES(?)"
        try {
            conn = getConnection()

            pStmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)
            pStmt?.setString(1, member.name)
            pStmt?.executeUpdate()

            rs = pStmt!!.generatedKeys
            if (rs!!.next()) {
                member.id = rs.getLong(1)
            } else {
                throw SQLException("id 조회 실패")
            }
            return member
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pStmt, rs)
        }
    }

    override fun findById(id: Long): Member? {
        var conn: Connection? = null
        var pStmt: PreparedStatement? = null
        var rs: ResultSet? = null
        val sql = "SELECT * FROM member WHERE id = ?"

        try {
            conn = getConnection()

            pStmt = conn.prepareStatement(sql)
            pStmt?.setLong(1, id)

            rs = pStmt.executeQuery()
            return if (rs!!.next()) {
                val member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                member
            } else {
                null
            }
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pStmt, rs)
        }
    }

    override fun findByName(name: String): Member? {
        var conn: Connection? = null
        var pStmt: PreparedStatement? = null
        var rs: ResultSet? = null
        val sql = "SELECT * FROM member WHERE name = ?"

        try {
            conn = getConnection()

            pStmt = conn.prepareStatement(sql)
            pStmt?.setString(1, name)

            rs = pStmt.executeQuery()
            return if (rs!!.next()) {
                val member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                member
            } else {
                null
            }
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pStmt, rs)
        }
    }

    override fun findAll(): List<Member?> {
        var conn: Connection? = null
        var pStmt: PreparedStatement? = null
        var rs: ResultSet? = null
        val sql = "SELECT * FROM member"

        try {
            conn = getConnection()
            pStmt = conn.prepareStatement(sql)
            rs = pStmt.executeQuery()

            val members: MutableList<Member> = mutableListOf()
            while (rs!!.next()) {
                val member = Member()
                member.id = rs.getLong("id")
                member.name = rs.getString("name")
                members.add(member)
            }
            return members.toList()
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pStmt, rs)
        }
    }

    private fun getConnection() = DataSourceUtils.getConnection(dataSource)

    private fun close(conn: Connection?, pStmt: PreparedStatement?, rs: ResultSet?) {
        try {
            rs?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            pStmt?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            conn?.let { close(it) }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun close(conn: Connection?) = DataSourceUtils.releaseConnection(conn, dataSource)
}
package com.example.SpringPos.repository;

import com.example.SpringPos.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into MEMBER (name, email, pwd, _rank) " +
                                "values (?, ?, ?, ?)",
                        new String[] { "ID" });
                pstmt.setString(1, member.getName());
                pstmt.setString(2, member.getEmail());
                pstmt.setString(3, member.getPwd());
                if (member.getRank().equals("")) {
                    pstmt.setString(4, "STAFF");
                } else {
                    pstmt.setString(4, member.getRank());
                }
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }

    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query("select * from MEMBER",
                (ResultSet rs, int rowNum) -> {
                    Member member = new Member(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("pwd"),
                            rs.getString("_rank"));
                    member.setId(rs.getLong("ID"));
                    return member;
                });
        return results;
    }

    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where email = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("pwd"),
                                rs.getString("_rank"));
                        member.setId(rs.getLong("id"));
                        return member;
                    }
                }, email);

        return results.isEmpty() ? null : results.get(0);
    }

    public Member selectById(Long id) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where id = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("pwd"),
                                rs.getString("_rank"));
                        member.setId(rs.getLong("id"));
                        return member;
                    }
                }, id);

        return results.isEmpty() ? null : results.get(0);
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set name = ?, pwd = ?, _rank = ? where email = ?",
                member.getName(), member.getPwd(), member.getRank(), member.getEmail());
    }
}

package com.tang.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tang.model.User;

@Repository
public class UserRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", new UserRowMapper());
    }

    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject("select * from user where id=?", new Object[]{id}, new UserRowMapper());
    }

    public User create(final User user) {
        final String sql = "insert into users(userid, username) values(?,?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUserid());
                ps.setString(2, user.getUsername());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }

    public void delete(final Integer id) {
        final String sql = "delete from users where id=?";
        jdbcTemplate.update(sql,
                new Object[]{id},
                new int[]{java.sql.Types.INTEGER});
    }

    public void update(final User user) {
        jdbcTemplate.update(
                "update users set userid=?, username=? where id=?",
                new Object[]{user.getUserid(), user.getUsername(), user.getId()});
    }
}

class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserid(rs.getString("name"));
        user.setUsername(rs.getString("email"));

        return user;
    }

}

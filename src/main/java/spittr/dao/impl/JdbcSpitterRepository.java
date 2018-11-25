package spittr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spittr.dao.SpitterRepository;
import spittr.dto.Spitter;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {
	@Autowired
	@Qualifier("jdbcOperations")
	private JdbcOperations jdbcOperations;

	@Override
	public Spitter findByUsername(String username) {
		return jdbcOperations.queryForObject(FIND_BY_USERNAME_SPITTER, new SpitterRowMapper(), username);
	}

	@Override
	public Spitter addSpitter(Spitter spitter) {
		spitter.setId(new Date().getTime());
		jdbcOperations.update(INSERT_SPITTER, spitter.getId(), spitter.getUsername(), spitter.getPassword(),
				spitter.getFirstName(), spitter.getLastName(), spitter.getEmail());
		return spitter;
	}

	private static final class SpitterRowMapper implements RowMapper<Spitter> {
		@Override
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Spitter.builder().id(rs.getLong("id")).username(rs.getString("username"))
					.password(rs.getString("password")).firstName(rs.getString("firstname"))
					.lastName(rs.getString("lastname")).email(rs.getString("email")).build();
		}
	}

	private final String INSERT_SPITTER = "insert into spitter values(?,?,?,?,?,?)";
	private final String FIND_BY_USERNAME_SPITTER = "select * from spitter where username = ?";
	private final String FIND_BY_ID_SPITTER = "select * from spitter where id = ?";
	private final String FIND_ALL = "select * from spitter";

	@Override
	public List<Spitter> findAll() {
		return jdbcOperations.queryForList(FIND_ALL, Spitter.class);
	}

	@Override
	public Spitter findOne(long id) {
		return jdbcOperations.queryForObject(FIND_BY_ID_SPITTER, new SpitterRowMapper(), id);
	}
}
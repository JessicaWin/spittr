package spittr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spittr.dao.SpittleRepository;
import spittr.dto.Spittle;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {
	private final HashMap<Long, Spittle> spitterMap = new HashMap<Long, Spittle>();

	@Autowired
	DataSource dataSource;

	@Override
	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> spittleList = new ArrayList<Spittle>();
		spitterMap.forEach((id, spittle) -> {
			spittleList.add(spittle);
		});
		return spittleList;
	}

	@Override
	public Spittle findOne(long spittleId) {
		List<Spittle> spittles = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SQL_SELECT_ONE_SPITTLE_STRING);

			statement.setLong(1, spittleId);
			statement.executeQuery();
			resultSet = statement.getResultSet();
			if (resultSet.next()) {
				Spittle spittle = Spittle.builder().build();
				spittle.setId(resultSet.getLong(1));
				spittle.setSpitter(resultSet.getLong(2));
				spittle.setMessage(resultSet.getString(3));
				spittle.setTime(resultSet.getDate(4));
				spittles.add(spittle);
				return spittle;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Spittle> findRecentSpittles(int size) {
		List<Spittle> spittles = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SQL_QUERY_SPITTLE_STRING);
			statement.setInt(1, size);
			statement.executeQuery();
			resultSet = statement.getResultSet();
			while (resultSet.next()) {
				Spittle spittle = Spittle.builder().build();
				spittle.setId(resultSet.getLong(1));
				spittle.setSpitter(resultSet.getLong(2));
				spittle.setMessage(resultSet.getString(3));
				spittle.setTime(resultSet.getDate(4));
				spittles.add(spittle);
			}
			return spittles;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return spittles;
	}

	@Override
	public void save(Spittle spittle) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SQL_INSERT_SPITTLE_STRING);
			statement.setLong(1, spittle.getId());
			statement.setLong(2, spittle.getSpitter());
			statement.setString(3, spittle.getMessage());
			statement.setDate(4, new java.sql.Date(spittle.getTime().getTime()));
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final String SQL_INSERT_SPITTLE_STRING = "insert into spittle (id, message, time, latitude, longitude) values (?,?,?,?,?)";
	private static final String SQL_QUERY_SPITTLE_STRING = "select * from spittle order by id desc limit ?";
	private static final String SQL_SELECT_ONE_SPITTLE_STRING = "select * from spittle where id = ?";
}

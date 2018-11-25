package spittr.dao;

import java.util.List;

import spittr.dto.Spittle;

public interface SpittleRepository {

	List<Spittle> findRecentSpittles(int size);

	List<Spittle> findSpittles(long max, int count);

	Spittle findOne(long id);

	void save(Spittle spittle);

}

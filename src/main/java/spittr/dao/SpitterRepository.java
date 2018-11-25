package spittr.dao;

import java.util.List;

import spittr.dto.Spitter;

public interface SpitterRepository {

	Spitter addSpitter(Spitter spitter);

	Spitter findByUsername(String username);

	List<Spitter> findAll();

	Spitter findOne(long id);

}

package ru.mendeleev.hockey.dao.interfaces;

import org.springframework.jdbc.core.RowMapper;
import ru.mendeleev.hockey.entity.City;

/**
 * Интерфейс DAO-класса, работающего с таблицей {@link City}.
 */
public interface ICityDao extends IDao<City> {

	@Override
	default RowMapper<City> rowMapper() {
		return (resultSet, i) -> {
			City city = new City();
			city.setId(resultSet.getInt("id"));
			city.setName(resultSet.getString("name"));
			return city;
		};
	}

	//================================================================================================================//

}

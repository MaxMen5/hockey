package ru.mendeleev.hockey.dao.postgres;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.mendeleev.hockey.dao.interfaces.AbstractDao;
import ru.mendeleev.hockey.dao.interfaces.ICityDao;
import ru.mendeleev.hockey.entity.City;

@Component
@Lazy
public class PgCityDao extends AbstractDao<City> implements ICityDao {

}

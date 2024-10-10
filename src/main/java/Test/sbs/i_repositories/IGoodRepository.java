package Test.sbs.i_repositories;

import Test.sbs.tables.Good;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IGoodRepository extends JpaRepository<Good, Long> {
    Good findByNameAndShortName(String name, String shortName);
}

package Test.sbs.i_repositories;

import Test.sbs.tables.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Person findByPhoneNumber(String phoneNumber);
}

package Test.sbs.converters;

import Test.sbs.tables.Person;
import org.modelmapper.AbstractConverter;

public class PersonToLongConverter extends AbstractConverter<Person, Long> {
    @Override
    protected Long convert(Person person){
        return person.getId();
    }
}

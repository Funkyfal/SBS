package Test.sbs.converters;

import Test.sbs.dto.PersonDTO;
import Test.sbs.tables.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {
    private final ModelMapper modelMapper;

    public PersonConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Person toEntity(PersonDTO dto) {
        return modelMapper.map(dto, Person.class);
    }

    public PersonDTO toDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}

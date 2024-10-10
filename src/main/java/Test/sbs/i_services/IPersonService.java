package Test.sbs.i_services;

import Test.sbs.dto.PersonDTO;

import java.util.List;

public interface IPersonService {
    List<PersonDTO> getAllPerson();

    PersonDTO getOnePerson(Long id);

    PersonDTO addNewPerson(PersonDTO newPersonDTO);

    PersonDTO replacePerson(PersonDTO newPerson, Long id);

    void deletePerson(Long id);
}

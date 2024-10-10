package Test.sbs.services_impl;

import Test.sbs.converters.PersonConverter;
import Test.sbs.dto.PersonDTO;
import Test.sbs.exceptions.CustomException;
import Test.sbs.exceptions.ErrorCode;
import Test.sbs.i_repositories.IPersonRepository;
import Test.sbs.i_services.IPersonService;
import Test.sbs.tables.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements IPersonService {
    private final IPersonRepository personRepository;
    private final PersonConverter personConverter;
    private static final Logger LOGGER = Logger.getLogger(PersonServiceImpl.class.getName());

    @Autowired
    public PersonServiceImpl(IPersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    public List<PersonDTO> getAllPerson() {
        LOGGER.info("Getting list of existing person.");
        return personRepository.findAll()
                .stream()
                .map(personConverter::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getOnePerson(Long id) {
        LOGGER.info("Getting a person with id " + id);
        return personConverter.toDTO(personRepository.findById(id)
                .orElseThrow(() -> {
            LOGGER.warning("Failed to get a person with id " + id);
            return new CustomException(ErrorCode.PERSON_NOT_FOUND);
        }));
    }

    public Person getOnePersonForAnOrder(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Failed to get a person with id " + id);
                    return new CustomException(ErrorCode.PERSON_NOT_FOUND);
                });
    }

    public PersonDTO addNewPerson(PersonDTO newPersonDTO) {
        LOGGER.info("Adding a new person.");
        Person newPerson = personConverter.toEntity(newPersonDTO);
        Person createdPerson = personRepository.save(newPerson);
        return personConverter.toDTO(createdPerson);
    }

    public PersonDTO replacePerson(PersonDTO newPersonDTO, Long id) {
        Person newPerson = personConverter.toEntity(newPersonDTO);
        LOGGER.info("Replacing or adding a person with id " + id);
        return personRepository.findById(id).map(person -> {
            person.setName(newPerson.getName());
            person.setSecondName(newPerson.getSecondName());
            person.setMiddleName(newPerson.getMiddleName());
            person.setPostCode(newPerson.getPostCode());
            person.setPhoneNumber(newPerson.getPhoneNumber());
            person.setAddress(newPerson.getAddress());
            person.setCity(newPerson.getCity());
            Person createdPerson = personRepository.save(person);
            return personConverter.toDTO(createdPerson);
        }).orElseGet(() -> {
            newPerson.setId(id);
            Person createdPerson = personRepository.save(newPerson);
            return personConverter.toDTO(createdPerson);
        });
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
        LOGGER.info("Deleted person with id " + id);
    }

    public Long getIdForTheTests(String phoneNumber){
        return personRepository.findByPhoneNumber(phoneNumber).getId();
    }
}

package Test.sbs.rest_controllers;

import Test.sbs.dto.PersonDTO;
import Test.sbs.services_impl.PersonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class PersonRestController {
    private final PersonServiceImpl personServiceImpl;

    PersonRestController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping("/person")
    public ResponseEntity<List<PersonDTO>> getAllPerson() {
        return ResponseEntity.ok(personServiceImpl.getAllPerson());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDTO> getOnePerson(@PathVariable Long id) {
        return ResponseEntity.ok(personServiceImpl.getOnePerson(id));
    }

    @PostMapping("/person")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        PersonDTO createdPersonDTO = personServiceImpl.addNewPerson(personDTO);
        return ResponseEntity
                .created(URI.create("/person/"))
                .body(createdPersonDTO);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDTO> replacePerson(@RequestBody PersonDTO personDTO, @PathVariable Long id) {
        return ResponseEntity.created(URI.create("/person/" + id))
                .body(personServiceImpl.replacePerson(personDTO, id));
    }

    @DeleteMapping("/person/{id}")
    void deletePerson(@PathVariable Long id) {
        personServiceImpl.deletePerson(id);
    }
}

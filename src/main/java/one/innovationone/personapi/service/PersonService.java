package one.innovationone.personapi.service;


import lombok.AllArgsConstructor;
import one.innovationone.personapi.dto.response.MessageResponseDTO;
import one.innovationone.personapi.dto.request.PersonDTO;
import one.innovationone.personapi.entity.Person;
import one.innovationone.personapi.exception.PersonNoFoundException;
import one.innovationone.personapi.mapper.PersonMapper;
import one.innovationone.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "created person with ID");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNoFoundException {
        Person person = verifyifExists(id);
       return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNoFoundException{
        verifyifExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNoFoundException {

        verifyifExists(id);

        Person personToUpdate= personMapper.toModel(personDTO);

        Person updatePerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatePerson.getId(), "Update person with ID");
    }

    private Person verifyifExists(Long id) throws PersonNoFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNoFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}

package one.innovationone.personapi.service;


import one.innovationone.personapi.dto.response.MessageResponseDTO;
import one.innovationone.personapi.dto.request.PersonDTO;
import one.innovationone.personapi.entity.Person;
import one.innovationone.personapi.mapper.PersonMapper;
import one.innovationone.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    @Autowired
//    public PersonService() {
//    }

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return MessageResponseDTO
                .builder()
                .message("created person with ID" + savedPerson.getId())
                .build();
    }
}

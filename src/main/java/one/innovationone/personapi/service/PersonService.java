package one.innovationone.personapi.service;


import one.innovationone.personapi.dto.response.MessageResponseDTO;
import one.innovationone.personapi.dto.request.PersonDTO;
import one.innovationone.personapi.entity.Person;
import one.innovationone.personapi.exception.PersonNoFoundException;
import one.innovationone.personapi.mapper.PersonMapper;
import one.innovationone.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    @Autowired

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

    private Person verifyifExists(Long id) throws PersonNoFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNoFoundException(id));
    }
}

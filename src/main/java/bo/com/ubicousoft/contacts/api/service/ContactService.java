package bo.com.ubicousoft.contacts.api.service;

import bo.com.ubicousoft.contacts.api.entity.Contact;
import bo.com.ubicousoft.contacts.api.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {

    final ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        Optional<Contact> existingContact = contactRepository.findById(id);

        if (existingContact.isPresent()) {
            Contact contactToUpdate = existingContact.get();

            contactToUpdate.setNombre(updatedContact.getNombre());
            contactToUpdate.setPrimerApellido(updatedContact.getPrimerApellido());
            contactToUpdate.setSegundoApellido(updatedContact.getSegundoApellido());
            contactToUpdate.setDireccion(updatedContact.getDireccion());
            contactToUpdate.setCelular(updatedContact.getCelular());

            return contactRepository.save(contactToUpdate);
        }
        return null;

    }
}

package bo.com.ubicousoft.contacts.api.controller;

import bo.com.ubicousoft.contacts.api.entity.Contact;
import bo.com.ubicousoft.contacts.api.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllContacts() {
        Map<String, Object> response = new HashMap<>();
        List<Contact> contacts = new ArrayList<>();
        try {
            contactService.getAllContacts().forEach(contacts::add);
            response.put("contacts", contacts);
            response.put("message", "Lista de contactos registrados");
        } catch (Exception e) {
            response.put("message", "Ocurrio un problema, por favor intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "No se encontraron contactos registrados");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getContactById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Contact> contact = contactService.getContactById(id);
            if (contact.isPresent()) {
                response.put("contacto", contact.get());
                response.put("mensaje", "El contacto con ID: " + id + " fue recuperado correctamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.put("mensaje", "Ocurrio un problema, por favor intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "No se encontro al contacto con el ID: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createContact(@Valid @RequestBody Contact contact, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("El campo: '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Contact newContact = contactService.saveContact(contact);
            response.put("contact",newContact);
            response.put("message","Su contacto se registro satisfactoriamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            response.put("message", "Ocurrio un problema, por favor intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateContact(@PathVariable Long id, @Valid @RequestBody Contact updatedContact, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("El campo: '%s' %s", err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Contact updateContact = contactService.updateContact(id, updatedContact);
            if (updateContact != null) {
                response.put("contact",updateContact);
                response.put("message","Su contacto se actualizo satisfactoriamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("mensaje", "No se encontro al contacto con el ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            response.put("message", "Ocurrio un problema, por favor intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
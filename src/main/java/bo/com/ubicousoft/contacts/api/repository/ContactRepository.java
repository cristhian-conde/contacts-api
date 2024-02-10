package bo.com.ubicousoft.contacts.api.repository;

import bo.com.ubicousoft.contacts.api.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}

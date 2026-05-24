package com.automationpractice.service;

import com.automationpractice.model.Contact;
import com.automationpractice.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateStatus(Long id, Contact.Status status) {
        return contactRepository;
            return contactRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Contact not found: " + id));
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public long count() {
        return contactRepository.count();
    }
}

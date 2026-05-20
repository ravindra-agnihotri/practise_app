package com.automationpractice.repository;

import com.automationpractice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByStatus(Contact.Status status);
    List<Contact> findByPriority(Contact.Priority priority);
    List<Contact> findByEmailContainingIgnoreCase(String email);
}

package com.ociapi.OCIAPI.services.impl;

import com.ociapi.OCIAPI.exceptions.UsernameExistsRuntimeException;
import com.ociapi.OCIAPI.repositories.SenderRepository;
import com.ociapi.OCIAPI.repositories.model.Sender;
import com.ociapi.OCIAPI.services.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;

    @Override
    public List<Sender> getAll() {
        return senderRepository.getSenders();
    }

    @Override
    public Sender getOneById(Long id) {
        return senderRepository.getSenders()
                .stream()
                .filter(s -> Objects.equals(s.getId(), id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Sender create(Sender sender) {
        var senders = senderRepository.getSenders();
        sender.setId(getNextSenderID());
        checkIfUsernameExists(sender);
        senders.add(sender);
        return sender;
    }

    @Override
    public Sender update(Sender sender, Long id) {
        checkIfUsernameExists(sender);
        var existingSender = getOneById(id);
        existingSender.setUsername(sender.getUsername());
        existingSender.setPassword(sender.getPassword());
        existingSender.setCompanyId(sender.getCompanyId());
        existingSender.setCompanyBu(sender.getCompanyBu());
        existingSender.setSoftwareId(sender.getSoftwareId());
        existingSender.setFile(sender.getFile());
        return existingSender;
    }

    @Override
    public void deleteById(Long id) {
        senderRepository.getSenders()
                .removeIf(s -> Objects.equals(s.getId(), id));
    }

    private Long getNextSenderID() {
        var senders = senderRepository.getSenders()
                .stream()
                .mapToLong(Sender::getId)
                .max()
                .orElse(0);
        return senders + 1;
    }

    private void checkIfUsernameExists(Sender sender) {
        var matched = senderRepository.getSenders()
                .stream()
                .anyMatch(s -> Objects.equals(s.getUsername(), sender.getUsername()));
        if (matched)
            throw new UsernameExistsRuntimeException("Username: " + sender.getUsername() + " exists");

    }

}

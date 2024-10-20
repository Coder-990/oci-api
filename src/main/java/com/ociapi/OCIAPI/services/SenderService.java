package com.ociapi.OCIAPI.services;

import com.ociapi.OCIAPI.repositories.model.Sender;

import java.util.List;

public interface SenderService {

    List<Sender> getAll();

    Sender getOneById(Long id);

    Sender create(Sender sender);

    Sender update(Sender sender, Long id);

    void deleteById(Long id);
}

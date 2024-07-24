package com.application.service;

import com.application.model.Status;
import com.application.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public Status getStatusFromTitle(String title) {
        return statusRepository.findByTitle(title);
    }

}

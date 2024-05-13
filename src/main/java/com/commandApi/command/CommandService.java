package com.commandApi.command;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A class to manage the logic of the application
 */
@Service
public class CommandService {

    private final CommandRepository commandRepository;

    @Autowired
    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    /**
     * get all the users form the database
     * @return all the users form the database
     */
    public List<Command> getCommands() {
        return commandRepository.findAll();
    }

    public Map<String, String> authenticateCommand(String name) {
        Map<String, String> response = new HashMap<>();

        Optional<Command> clientOptional = commandRepository.findByName(name);

        if (clientOptional.isPresent()) {
            response.put("message", "Authentication success");
        } else {
            response.put("message", "Authentication failed");
        }
        return response;
    }

    public void addNewCommand(Command command) {
        // TODO
    }

    public void deleteCommand(Long commandId) {
        // TODO
    }

    @Transactional  //pour ne pas utiliser des requete sql
    public void updateCommand(Long commandId, String name) {
        // TODO
    }
}


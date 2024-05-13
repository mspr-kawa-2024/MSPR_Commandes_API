package com.commandApi.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * A class to set the control to the user recovered from the database
 */
@RestController
@RequestMapping(path = "api/v1/command")
@CrossOrigin(origins = "http://localhost:3001",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE})
public class CommandController {

    private final CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping
    public List<Command> getCommands() {
        return commandService.getCommands();
    }

    @PostMapping
    public void registerNewCommand(@RequestBody Command command) {
        commandService.addNewCommand(command);
    }

    @PutMapping(path = "{commandId}")
    public void updateCommand(@PathVariable("commandId") Long commandId,
                             @RequestParam(required = false) String name){
        commandService.updateCommand(commandId, name);
    }
    @DeleteMapping(path = "{commandId}")
    public void deleteCommand(@PathVariable("commandId") Long commandId){
        commandService.deleteCommand(commandId);
    }

}


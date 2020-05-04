package com.iastate.web.hydrogen.controller;

import com.iastate.web.hydrogen.service.DockerService;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    @Autowired
    private DockerService dockerService;

    @GetMapping
    public String getHome(Model model) {
        String[] command = {"sh", "-c", "ls"};

        try {
            System.out.println(dockerService.runCommand(command));
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }
}

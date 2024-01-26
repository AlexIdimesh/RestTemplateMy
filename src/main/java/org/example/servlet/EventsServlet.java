package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.EventsEntity;
import org.example.service.EventsService;
import org.example.servlet.dto.IncomingDto;
import org.example.servlet.dto.OutGoingDto;
import org.example.servlet.mapper.EventsDtomapper;

import java.io.IOException;
import java.util.UUID;


@WebServlet(name = "SimpleServlet", value = "/simple")
public class EventsServlet extends HttpServlet {
    private EventsService service;
    private EventsDtomapper dtomapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();// Our Id from request
        EventsEntity byId = service.findById(uuid);
        OutGoingDto outGoingDto = dtomapper.map(byId);
        // return our DTO
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EventsEntity eventsEntity = dtomapper.map(new IncomingDto());
        EventsEntity saved = service.save(eventsEntity);
        OutGoingDto map = dtomapper.map(saved);
        // return our DTO, not necessary
    }
}

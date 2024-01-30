package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.EventsEntity;
import org.example.service.EventsService;
import org.example.service.impl.EventsServiceImpl;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.mapper.EventsMapperMapstruct;
import org.example.util.JsonConvector;
import org.example.util.JsonConvectorImpl;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventsServlet", value = "/events")
public class EventsServlet extends HttpServlet {
    private EventsServiceImpl service;
    private EventsMapperMapstruct dtomapper;

    private JsonConvectorImpl jsonMapper;


    public EventsServlet() {
    }

    public EventsServlet(EventsServiceImpl service, EventsMapperMapstruct dtomapper, JsonConvectorImpl jsonMapper) {
        this.service = service;
        this.dtomapper = dtomapper;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long eventId = Long.parseLong(req.getParameter("eventId"));
        EventDTO eventDTO = service.findById(eventId) ;
        String json = jsonMapper.toJson(eventDTO) ;
        resp.setContentType("application/json");
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.println(json);
    }
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventCity = req.getParameter("eventCity");
        String eventName = req.getParameter("eventName");

        EventDTO eventDTO = new EventDTO();
        eventDTO.setName(eventName);
        eventDTO.setCity(eventCity);

        EventsEntity eventsEntity = dtomapper.eventDTOToEvent(eventDTO);

        service.save(eventsEntity);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Event saved successfully");

    }
}
//    private String convertObjectToJson(Object dto) {
//        try {
//            return new ObjectMapper().writeValueAsString(dto);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

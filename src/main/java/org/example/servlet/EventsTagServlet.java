package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.EventsTagServiceImpl;
import org.example.service.serverImpl.EventsTagService;
import org.example.servlet.dto.EventsTagDTO;
import org.example.util.json.jsonEventsTag.JsonConvectorTag;
import org.example.util.json.jsonEventsTag.JsonConvectorTagImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventsTagEntity", value = "/tag")
public class EventsTagServlet extends HttpServlet {
    private EventsTagService service = new EventsTagServiceImpl();

    private JsonConvectorTag jsonMapper = new JsonConvectorTagImpl();
    private static final String PARAMETER_ID = "id";
    private static final String CONTENT_JSON = "application/json";

    public EventsTagServlet() {
    }

    public EventsTagServlet(EventsTagService service, JsonConvectorTag jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String eventTagId = req.getParameter("id");
        if (eventTagId != null) {
            EventsTagDTO eventsTagDTO = service.findById(Long.parseLong(eventTagId));
            String json = jsonMapper.toJson(eventsTagDTO);
            if (eventsTagDTO != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ServletUtil.sendJsonResponse(json, resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            List<EventsTagDTO> eventDTOs;
            try {
                eventDTOs = service.findAll();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = jsonMapper.toJson(eventDTOs);
            if (!eventDTOs.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ServletUtil.sendJsonResponse(json, resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String jsonBody = ServletUtil.getJsonBody(req);
            var bodyDto = jsonMapper.toDTO(jsonBody);
            var resultDto = service.save(bodyDto);
            if (resultDto != null && resultDto.getId() != null) {
                String jsonResp = jsonMapper.toJson(resultDto);
                resp.setContentType(CONTENT_JSON);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                ServletUtil.sendJsonResponse(jsonResp, resp);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.parseLong(req.getParameter(PARAMETER_ID));
        if (service.deleteById(id)) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String jsonBody = ServletUtil.getJsonBody(req);
            var updatedDto = jsonMapper.toDTO(jsonBody);
            EventsTagDTO eventDTOSave = null;
            if(updatedDto.getId() != null) {
                eventDTOSave = service.upDated(updatedDto);
            }
            if (eventDTOSave != null && eventDTOSave.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

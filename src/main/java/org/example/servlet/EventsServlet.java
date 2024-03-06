package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.EventsServiceImpl;
import org.example.service.serverImpl.EventsService;
import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;
import org.example.util.json.jsonCombined.JsonConvectorCombined;
import org.example.util.json.jsonCombined.JsonConvectorCombinedImpl;
import org.example.util.json.jsonEvent.JsonConvector;
import org.example.util.json.jsonEvent.JsonConvectorImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventsServlet", value = "/events")
public class EventsServlet extends HttpServlet {
    private  EventsService service = new EventsServiceImpl();
    private JsonConvector jsonMapper = new JsonConvectorImpl();
    private JsonConvectorCombined jsonMapperComb = new JsonConvectorCombinedImpl();
    private static final String PARAMETER_ID = "id";
    private static final String CONTENT_JSON = "application/json";


    public EventsServlet() {
    }

    public EventsServlet(EventsService service, JsonConvector jsonMapper, JsonConvectorCombined jsonMapperComb) {
        this.service = service;
        this.jsonMapper = jsonMapper;
        this.jsonMapperComb = jsonMapperComb;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String eventId = req.getParameter("id");
        String eventsTagId = req.getParameter("tagId");
        if (eventsTagId != null) {
            List<CombinedEntityDTO> eventsTagDTO = service.findEventTagsByEventId(Long.parseLong(eventsTagId));
            String json = jsonMapperComb.toJson(eventsTagDTO);
            if (eventsTagDTO == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ServletUtil.sendJsonResponse(json,resp);
        }
        if (eventId != null) {
            EventDTO eventDTO = service.findById(Long.parseLong(eventId));
            String json = jsonMapper.toJson(eventDTO);
            if (eventDTO != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ServletUtil.sendJsonResponse(json, resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            List<EventDTO> eventDTOs;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
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
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) {
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
            EventDTO eventDTOSave = null;
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



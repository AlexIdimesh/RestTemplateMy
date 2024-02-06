package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.serverImpl.EventsService;
import org.example.service.impl.EventsServiceImpl;
import org.example.servlet.dto.CombinedEntityDTO;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;
import org.example.util.json.jsonCombined.JsonConvectorCombined;
import org.example.util.json.jsonCombined.JsonConvectorCombinedImpl;
import org.example.util.json.jsonEvent.JsonConvector;
import org.example.util.json.jsonEvent.JsonConvectorImpl;
import org.example.util.json.jsonEventsTag.JsonConvectorTag;
import org.example.util.json.jsonEventsTag.JsonConvectorTagImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventsServlet", value = "/events")
public class EventsServlet extends HttpServlet {
    private final EventsService service = new EventsServiceImpl();
    private final JsonConvector jsonMapper = new JsonConvectorImpl();
    private final JsonConvectorCombined jsonMapperComb = new JsonConvectorCombinedImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String eventId = req.getParameter("id");
        String eventsTagId = req.getParameter("tagId");
        if (eventsTagId != null) {
            List<CombinedEntityDTO> eventsTagDTO = service.findEventTagsByEventId(Long.parseLong(eventsTagId));
            String json = jsonMapperComb.toJson(eventsTagDTO);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (eventId != null) {
            EventDTO eventDTO = service.findById(Long.parseLong(eventId));
            String json = jsonMapper.toJson(eventDTO);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            List<EventDTO> eventDTOs;
            try {
                eventDTOs = service.findAll();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = jsonMapper.toJson(eventDTOs);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Event saved successfully");
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String requestBodyString = requestBody.toString();
        EventDTO eventDTO = jsonMapper.toDTO(requestBodyString);
        service.save(eventDTO);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String eventIdParam = req.getParameter("id");
        boolean deleteResult = service.deleteById(Long.valueOf(eventIdParam));
        if (deleteResult) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Event with ID " + true + " has been successfully deleted");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to delete event with ID " + false);
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Event saved successfully");
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String requestBodyString = requestBody.toString();
        EventDTO eventDTO = jsonMapper.toDTO(requestBodyString);
        service.upDated(eventDTO);
    }
}



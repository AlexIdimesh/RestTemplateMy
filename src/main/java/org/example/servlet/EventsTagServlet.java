package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.EventsTagServiceImpl;
import org.example.service.serverImpl.EventsTagService;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.EventsTagDTO;
import org.example.util.json.jsonEventsTag.JsonConvectorTag;
import org.example.util.json.jsonEventsTag.JsonConvectorTagImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EventsTagEntity", value = "/tag")
public class EventsTagServlet extends HttpServlet {
    private final EventsTagService service = new EventsTagServiceImpl();

    private final JsonConvectorTag jsonMapper = new JsonConvectorTagImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        String eventId = req.getParameter("tagId");
        if (eventId != null) {
            EventsTagDTO eventsTagDTO = service.findById(Long.parseLong(eventId));
            String json = jsonMapper.toJson(eventsTagDTO);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            List<EventsTagDTO> eventsTagDTOS;
            try{
                eventsTagDTOS = service.findAll();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = jsonMapper.toJson(eventsTagDTOS);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        EventsTagDTO eventsTagDTO = jsonMapper.toDTO(requestBodyString);
        EventsTagDTO saveDto = service.save(eventsTagDTO);
        if (saveDto != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Event save successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to save event");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String eventIdParam = req.getParameter("tagId");
        boolean deleteResult = service.deleteById(Long.valueOf(eventIdParam));
        if (deleteResult) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Event with ID " + eventIdParam + " has been successfully deleted");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to delete event with ID " + eventIdParam);
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
        EventsTagDTO eventsTagDTO = jsonMapper.toDTO(requestBodyString);
        EventsTagDTO updatedEvent = service.upDated(eventsTagDTO);
        if (updatedEvent != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Event updated successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to update event");
        }
    }
}

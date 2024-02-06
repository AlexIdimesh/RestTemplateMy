package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.ParticipantsServiceImpl;
import org.example.service.serverImpl.ParticipantsService;
import org.example.servlet.dto.ParticipantsDTO;
import org.example.util.json.jsonParticipant.JsonConvectorsPar;
import org.example.util.json.jsonParticipant.JsonConvectorsParImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ParticipantsEntity", value = "/participants")
public class ParticipantsServlet extends HttpServlet {

   private final ParticipantsService service = new ParticipantsServiceImpl();

   private final JsonConvectorsPar jsonMapper = new JsonConvectorsParImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        String participantsId = req.getParameter("id");
        String participantsIdEvent = req.getParameter("parId");
        if (participantsId != null) {
            ParticipantsDTO participantsDTO = service.findById(Long.parseLong(participantsId));
            String json = jsonMapper.toJson(participantsDTO);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
         if (participantsIdEvent != null) {
                List<ParticipantsDTO> participantsDTO = service.findByIdEvents(Long.parseLong(participantsIdEvent));
                String json = jsonMapper.toJson(participantsDTO);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            List<ParticipantsDTO> participantsDTOS;
            try {
                participantsDTOS = service.findAll();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = jsonMapper.toJson(participantsDTOS);
            try (PrintWriter printWriter = resp.getWriter()) {
                printWriter.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
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
        ParticipantsDTO participantsDTO = jsonMapper.toDTO(requestBodyString);
        service.save(participantsDTO);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String participantId = req.getParameter("parId");
        boolean deleteResult = service.deleteById(Long.valueOf(participantId));
        if (deleteResult) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Event with ID " + participantId + " has been successfully deleted");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to delete event with ID " + participantId);
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
        ParticipantsDTO participantsDTO = jsonMapper.toDTO(requestBodyString);
        service.upDated(participantsDTO);
    }
}

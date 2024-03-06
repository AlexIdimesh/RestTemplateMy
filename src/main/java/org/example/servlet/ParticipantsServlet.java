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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ParticipantsEntity", value = "/participants")
public class ParticipantsServlet extends HttpServlet {

    private ParticipantsService service = new ParticipantsServiceImpl();

    private JsonConvectorsPar jsonMapper = new JsonConvectorsParImpl();
    private static final String PARAMETER_ID = "id";
    private static final String CONTENT_JSON = "application/json";

    public ParticipantsServlet(ParticipantsService service, JsonConvectorsPar jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }

    public ParticipantsServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String participantsId = req.getParameter("id");
        String participantsEventsId = req.getParameter("tagId");
        if (participantsEventsId != null) {
            ParticipantsDTO participantsDTO = service.findById(Long.parseLong(participantsEventsId));
            String json = jsonMapper.toJson(participantsDTO);
            if (participantsDTO == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ServletUtil.sendJsonResponse(json, resp);
        }
        if (participantsId != null) {
            ParticipantsDTO participantsDTO = service.findById(Long.parseLong(participantsId));
            String json = jsonMapper.toJson(participantsDTO);
            if (participantsDTO != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ServletUtil.sendJsonResponse(json, resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            List<ParticipantsDTO> participantsDTOS;
            try {
                participantsDTOS = service.findAll();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = jsonMapper.toJson(participantsDTOS);
            if (!participantsDTOS.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                ServletUtil.sendJsonResponse(json, resp);
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){
        try {
            String jsonBody = ServletUtil.getJsonBody(req);
            var updatedDto = jsonMapper.toDTO(jsonBody);
            ParticipantsDTO participantsDTO = null;
            if (updatedDto.getId() != null) {
                participantsDTO = service.upDated(updatedDto);
            }
            if (participantsDTO != null && participantsDTO.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

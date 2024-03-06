package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.serverImpl.ParticipantsService;
import org.example.servlet.dto.EventDTO;
import org.example.servlet.dto.ParticipantsDTO;
import org.example.util.json.jsonParticipant.JsonConvectorsParImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ParticipantsServletTest {

    @Mock
    private ParticipantsService service;
    @Mock
    private JsonConvectorsParImpl jsonConvectorsPar;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    private ParticipantsServlet servlet;

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String PARAMETER_ID = "id";
    private static final String JSON_TEST = "test";
    private static final Long LONG_ID = 1L;

    @BeforeEach
    void setup() {
        servlet = new ParticipantsServlet(service,jsonConvectorsPar);
    }

    @Test
    void doGetWhenParameterPresentAndValidThenReturnDto() throws IOException {
        var dtoMock = mock(ParticipantsDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
            when(service.findById(LONG_ID)).thenReturn(dtoMock);
            when(jsonConvectorsPar.toJson(dtoMock)).thenReturn(JSON_TEST);

            servlet.doGet(request, response);

            verify(request, times(1)).getParameter(PARAMETER_ID);
            verify(service, times(1)).findById(LONG_ID);
            verify(jsonConvectorsPar, times(1)).toJson(dtoMock);
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)), times(1));
        }
    }

    @Test
    void doGetWhenParameterInvalidThenHandleNumberFormatException() throws IOException {

        when(request.getParameter(PARAMETER_ID)).thenReturn("1");

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
    }

    @Test
    void doGetWhenNullDtoThenResponseNotFound() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.findById(LONG_ID)).thenReturn(null);

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetWhenParameterAbsentThenReturnList() throws SQLException, ClassNotFoundException, IOException {
        List<ParticipantsDTO> orders = Arrays.asList(new ParticipantsDTO(), new ParticipantsDTO());

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            when(request.getParameter(PARAMETER_ID)).thenReturn(null);
            when(service.findAll()).thenReturn(orders);
            when(jsonConvectorsPar.toJson(orders)).thenReturn(JSON_TEST);

            servlet.doGet(request, response);

            verify(request, times(1)).getParameter(PARAMETER_ID);
            verify(service, times(1)).findAll();
            verify(jsonConvectorsPar, times(1)).toJson(orders);
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)));
        }
    }
    @Test
    void doGetWhenEmptyListThenResponseNotFound() throws SQLException, ClassNotFoundException, IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(null);
        when(service.findAll()).thenReturn(new ArrayList<>());

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findAll();
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    @Test
    void doPostWhenBodyPresentedAndDtoIdNullThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dto = spy(ParticipantsDTO.class);
        var dtoSaved = spy(ParticipantsDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorsPar.toDTO(jsonBody)).thenReturn(dto);
            when(service.save(dto)).thenReturn(dtoSaved);

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorsPar, times(1)).toDTO(anyString());
            verify(service, times(1)).save(any(ParticipantsDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPostWhenSendJsonThrowsIOThenHandleException() {
        String jsonBody = "test";
        var dto = spy(ParticipantsDTO.class);
        var dtoSaved = spy(ParticipantsDTO.class);
        dtoSaved.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorsPar.toDTO(jsonBody)).thenReturn(dto);
            when(service.save(dto)).thenReturn(dtoSaved);
            when(jsonConvectorsPar.toJson(dtoSaved)).thenReturn(jsonBody);
            mockedStatic.when(() -> ServletUtil.sendJsonResponse(jsonBody, response)).thenThrow(new IOException("Mocked"));

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorsPar, times(1)).toDTO(anyString());
            verify(service, times(1)).save(any(ParticipantsDTO.class));
            verify(jsonConvectorsPar, times(1)).toJson(any(ParticipantsDTO.class));
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)));
        }
    }

    @Test
    void doPostWhenGetJsonBodyThrowsIOThenHandleException() {
        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenThrow(new IOException("Mock"));

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
        }
    }
    @Test
    void doPutWhenBodyPresentedAndUpdateFailThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dtoUpdate = spy(ParticipantsDTO.class);
        dtoUpdate.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorsPar.toDTO(jsonBody)).thenReturn(dtoUpdate);
            when(service.upDated(dtoUpdate)).thenReturn(dtoUpdate);

            servlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorsPar, times(1)).toDTO(anyString());
            verify(service, times(1)).upDated(any(ParticipantsDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPutWhenBodyPresentedButWithNullIdThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dto = spy(ParticipantsDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorsPar.toDTO(jsonBody)).thenReturn(dto);

            servlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorsPar, times(1)).toDTO(jsonBody);
            verify(service, never()).upDated(any(ParticipantsDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPutWhenGetJsonBodyThrowsIOThenHandleException() {
        var dtoSaved = spy(EventDTO.class);
        dtoSaved.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenThrow(new IOException("Mocked IOException"));

            servlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
        }
    }

    @Test
    void doDeleteWhenParameterInvalidThenHandleNumberFormatException() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn("1");
        servlet.doDelete(request, response);
        verify(request, times(1)).getParameter(PARAMETER_ID);
    }

    @Test
    void doDeleteWhenParameterValidThenDelete() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(true);
        servlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDeleteWhenNoSuchDtoThenResponseBadRequest() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(false);

        servlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

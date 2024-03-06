package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.impl.EventsTagServiceImpl;
import org.example.servlet.dto.EventsTagDTO;
import org.example.util.json.jsonEventsTag.JsonConvectorTag;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventsTagServletTest {
    @Mock
    private EventsTagServiceImpl service;
    @Mock
    private JsonConvectorTag jsonConvectorTag;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private EventsTagServlet tagServlet;

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String PARAMETER_ID = "id";
    private static final String JSON_TEST = "test";
    private static final Long LONG_ID = 1L;

    @BeforeEach
    void setup() {
        tagServlet = new EventsTagServlet(service,jsonConvectorTag);
    }

    @Test
    void doGetWhenParameterPresentAndValidThenReturnDto() throws IOException {
        var dtoMock = mock(EventsTagDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
            when(service.findById(LONG_ID)).thenReturn(dtoMock);
            when(jsonConvectorTag.toJson(dtoMock)).thenReturn(JSON_TEST);

            tagServlet.doGet(request, response);

            verify(request, times(1)).getParameter(PARAMETER_ID);
            verify(service, times(1)).findById(LONG_ID);
            verify(jsonConvectorTag, times(1)).toJson(dtoMock);
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)), times(1));
        }
    }

    @Test
    void doGetWhenParameterInvalidThenHandleNumberFormatException() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn("1");
        tagServlet.doGet(request, response);
        verify(request, times(1)).getParameter(PARAMETER_ID);
    }

    @Test
    void doGetWhenNullDtoThenResponseNotFound() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.findById(LONG_ID)).thenReturn(null);

        tagServlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetWhenParameterAbsentThenReturnList() throws SQLException, ClassNotFoundException, IOException {
        List<EventsTagDTO> orders = Arrays.asList(new EventsTagDTO(),new EventsTagDTO());

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            when(request.getParameter(PARAMETER_ID)).thenReturn(null);
            when(service.findAll()).thenReturn(orders);
            when(jsonConvectorTag.toJson(orders)).thenReturn(JSON_TEST);

            tagServlet.doGet(request, response);

            verify(request, times(1)).getParameter(PARAMETER_ID);
            verify(service, times(1)).findAll();
            verify(jsonConvectorTag, times(1)).toJson(orders);
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)));
        }
    }
    @Test
    void doGetWhenEmptyListThenResponseNotFound() throws SQLException, ClassNotFoundException, IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(null);
        when(service.findAll()).thenReturn(new ArrayList<>());

        tagServlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findAll();
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
    @Test
    void doPostWhenBodyPresentedAndDtoIdNullThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dto = spy(EventsTagDTO.class);
        var dtoSaved = spy(EventsTagDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorTag.toDTO(jsonBody)).thenReturn(dto);
            when(service.save(dto)).thenReturn(dtoSaved);

            tagServlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorTag, times(1)).toDTO(anyString());
            verify(service, times(1)).save(any(EventsTagDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPostWhenSendJsonThrowsIOThenHandleException() throws IOException {
        String jsonBody = "test";
        var dto = spy(EventsTagDTO.class);
        var dtoSaved = spy(EventsTagDTO.class);
        dtoSaved.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorTag.toDTO(jsonBody)).thenReturn(dto);
            when(service.save(dto)).thenReturn(dtoSaved);
            when(jsonConvectorTag.toJson(dtoSaved)).thenReturn(jsonBody);
            mockedStatic.when(() -> ServletUtil.sendJsonResponse(jsonBody, response)).thenThrow(new IOException("Mocked"));

            tagServlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorTag, times(1)).toDTO(anyString());
            verify(service, times(1)).save(any(EventsTagDTO.class));
            verify(jsonConvectorTag, times(1)).toJson(any(EventsTagDTO.class));
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
            mockedStatic.verify(() -> ServletUtil.sendJsonResponse(anyString(), eq(response)));
        }
    }

    @Test
    void doPostWhenGetJsonBodyThrowsIOThenHandleException() throws IOException {
        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenThrow(new IOException("Mock"));

            tagServlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
        }
    }
    @Test
    void doPutWhenBodyPresentedAndUpdateFailThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dtoUpdate = spy(EventsTagDTO.class);
        dtoUpdate.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorTag.toDTO(jsonBody)).thenReturn(dtoUpdate);
            when(service.upDated(dtoUpdate)).thenReturn(dtoUpdate);

            tagServlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorTag, times(1)).toDTO(anyString());
            verify(service, times(1)).upDated(any(EventsTagDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPutWhenBodyPresentedButWithNullIdThenBadRequest() throws IOException {
        String jsonBody = "test";
        var dto = spy(EventsTagDTO.class);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonConvectorTag.toDTO(jsonBody)).thenReturn(dto);

            tagServlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonConvectorTag, times(1)).toDTO(jsonBody);
            verify(service, never()).upDated(any(EventsTagDTO.class));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void doPutWhenGetJsonBodyThrowsIOThenHandleException() throws IOException {
        var dtoSaved = spy(EventsTagDTO.class);
        dtoSaved.setId(1L);

        try (MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenThrow(new IOException("Mocked IOException"));

            tagServlet.doPut(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
        }
    }

    @Test
    void doDeleteWhenParameterInvalidThenHandleNumberFormatException() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn("1");
        tagServlet.doDelete(request, response);
        verify(request, times(1)).getParameter(PARAMETER_ID);
    }

    @Test
    void doDeleteWhenParameterValidThenDelete() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(true);
        tagServlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDeleteWhenNoSuchDtoThenResponseBadRequest() throws IOException {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(false);

        tagServlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

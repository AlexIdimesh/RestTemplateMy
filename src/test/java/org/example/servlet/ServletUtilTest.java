package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServletUtilTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test
    void testGetJsonBody() throws IOException, IOException {
        BufferedReader reader = mock(BufferedReader.class);
        String json = "{'key': 'value'}";

        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of(json));

        String result = ServletUtil.getJsonBody(request);

        assertEquals(json, result);
    }
    @Test
    void testSendJsonResponseThrowsIOException() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Mocked"));

        assertThrows(IOException.class, () -> ServletUtil.sendJsonResponse("test", response));
    }
}

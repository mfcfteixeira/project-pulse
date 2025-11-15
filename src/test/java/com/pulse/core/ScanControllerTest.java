package com.pulse.core;

import com.pulse.core.controller.ScanController;
import com.pulse.core.service.FileScanService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScanController.class)
class ScanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileScanService fileScanService;

    @Test
    void shouldReturnOkWhenScanCompletes() throws Exception {
        // Prepare
        List<Path> returned = List.of(Path.of("."));

        //Condition
        when(fileScanService.scanPath(eq("test"), any(Path.class))).thenReturn(returned);

        // Execution
        mockMvc.perform(post("/scan").param("sourceName", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Scan completed"));

        // Assertion: service called once
        verify(fileScanService, times(1)).scanPath(eq("test"), any(Path.class));
    }

    @Test
    void shouldReturnErrorWhenServiceThrowsIOException() throws Exception {
        // Condition
        when(fileScanService.scanPath(eq("test"), any(Path.class)))
                .thenThrow(new IOException("Disk error"));

        // Execution & Assertion
        mockMvc.perform(post("/scan").param("sourceName", "test"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Disk error")));

        // Assertion: service called once
        verify(fileScanService, times(1)).scanPath(eq("test"), any(Path.class));
    }

    @Test
    void shouldForwardCustomRootPathToService() throws Exception {
        // Prepare
        List<Path> returned = List.of(Path.of("/tmp/somewhere"));

        //Condition
        when(fileScanService.scanPath(eq("test"), any(Path.class))).thenReturn(returned);
        ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);

        // Execution
        mockMvc.perform(post("/scan")
                        .param("sourceName", "test")
                        .param("root", "/tmp/somewhere"))
                .andExpect(status().isOk());

        // Assertion: captured path equals provided root
        verify(fileScanService).scanPath(eq("test"), pathCaptor.capture());
        assertEquals(Path.of("/tmp/somewhere"), pathCaptor.getValue());
    }
}

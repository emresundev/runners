package dev.emresun.runners.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(RunController.class)
class RunControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private JdbcClientRunRepository runRepository;

    @Test
    void findAll() throws Exception {
        List<Run> mockRuns = List.of(
                new Run(1, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR),
                new Run(2, "Evening Run", LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), 10, Location.OUTDOOR)
        );
        when(runRepository.findAll()).thenReturn(mockRuns);

        mockMvc.perform(get("/api/runs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockRuns.size()))
                .andExpect(jsonPath("$[0].name").value("Morning Run"));
    }

    @Test
    void findById() throws Exception {
        Run mockRun = new Run(1, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR);
        when(runRepository.findById(1)).thenReturn(Optional.of(mockRun));

        mockMvc.perform(get("/api/runs/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Morning Run"));

        when(runRepository.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/runs/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        Run newRun = new Run(null, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR);

        mockMvc.perform(post("/api/runs")
                        .contentType("application/json")
                        .content("""
                                {
                                    "name": "Morning Run",
                                    "startTime": "2023-11-18T07:00:00",
                                    "endTime": "2023-11-18T08:00:00",
                                    "distance": 5,
                                    "location": "INDOOR"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(runRepository, times(1)).create(any(Run.class));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api/runs/1")
                        .contentType("application/json")
                        .content("""
                                {
                                    "name": "Evening Run",
                                    "startTime": "2023-11-18T05:00:00",
                                    "endTime": "2023-11-18T06:00:00",
                                    "distance": 8,
                                    "location": "OUTDOOR"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(runRepository, times(1)).update(any(Run.class), eq(1));
    }

    @Test
    public void shouldDeleteRun() throws Exception {
        mockMvc.perform(delete("/api/runs/1"))
                .andExpect(status().isNoContent());
    }



    @Test
    void findByLocation() throws Exception {
        List<Run> mockRuns = List.of(
                new Run(1, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR)
        );
        when(runRepository.findByLocation("Indoor")).thenReturn(mockRuns);

        mockMvc.perform(get("/api/runs/location/Indoor"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockRuns.size()))
                .andExpect(jsonPath("$[0].name").value("Morning Run"));
    }
}
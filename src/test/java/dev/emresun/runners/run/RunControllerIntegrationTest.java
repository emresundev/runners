package dev.emresun.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntegrationTest {

    @LocalServerPort
    private int port;
    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void findAllRuns() {
        Run[] runs = restClient.get().uri("/api/runs").retrieve().body(new ParameterizedTypeReference<>() {
        });
        assertEquals(10, runs.length);
    }

    @Test
    void findRunById() {
        Run run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        assertEquals("Noon Run", run.name());
    }

    @Test
    void createRun() {
        Run run = new Run(11, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR);
        restClient.post()
                .uri("/api/runs")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        Run[] runs = restClient.get().uri("/api/runs").retrieve().body(new ParameterizedTypeReference<Run[]>() {
        });


        assertEquals(11, runs.length);
    }

    @Test
    void updateRun() {
        Run run = new Run(1, "Morning Run", LocalDateTime.now().minusHours(1), LocalDateTime.now(), 5, Location.INDOOR);
        restClient.put()
                .uri("/api/runs/1")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        Run updatedRun = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        assertEquals("Morning Run", updatedRun.name());
    }

    @Test
    void deleteRun() {
        restClient.delete()
                .uri("/api/runs/1")
                .retrieve()
                .toBodilessEntity();

        Run[] runs = restClient.get().uri("/api/runs").retrieve().body(new ParameterizedTypeReference<Run[]>() {
        });
        assertEquals(9, runs.length);
    }


}
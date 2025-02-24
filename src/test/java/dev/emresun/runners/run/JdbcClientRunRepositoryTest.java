package dev.emresun.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(JdbcClientRunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcClientRunRepositoryTest {

    @Autowired
   private JdbcClientRunRepository repository;

    private static final LocalDateTime BASE_TIME = LocalDateTime.of(2024, 1, 1, 10, 0);


    @BeforeEach
    void setUp() {
        repository.create(new Run(1,
                "Monday Morning Run",
                BASE_TIME,
                BASE_TIME.plusMinutes(30),
                3,
                Location.INDOOR));

        repository.create(new Run(2,
                "Wednesday Evening Run",
                BASE_TIME,
                BASE_TIME.plusMinutes(45),
                6,
                Location.INDOOR));
    }


    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.name());
        assertEquals(3, run.distance());
    }


    @Test
    void shouldNotFindRunWithInvalidId() {
        var run = repository.findById(3);
        assertTrue(run.isEmpty());
    }

    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Friday Morning Run",
                BASE_TIME,
                BASE_TIME.plusMinutes(30),
                3,
                Location.INDOOR));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() {
        repository.update(new Run(1,
                "Monday Morning Run",
                BASE_TIME,
                BASE_TIME.plusMinutes(30),
                5,
                Location.OUTDOOR), 1);
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.name());
        assertEquals(5, run.distance());
        assertEquals(Location.OUTDOOR, run.location());
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }

    @Test
    void shouldCountRuns() {
        assertEquals(2, repository.count());
    }

    @Test
    void shouldSaveAllRuns() {
        repository.saveAll(List.of(
                new Run(3,
                        "Friday Morning Run",
                        BASE_TIME,
                        BASE_TIME.plusMinutes(30),
                        3,
                        Location.INDOOR),
                new Run(4,
                        "Saturday Morning Run",
                        BASE_TIME,
                        BASE_TIME.plusMinutes(45),
                        4,
                        Location.OUTDOOR)
        ));
        List<Run> runs = repository.findAll();
        assertEquals(4, runs.size());
    }

    @Test
    void shouldNotCreateIfIdExists() {
        var exception = assertThrows(IllegalStateException.class, () ->
                repository.create(new Run(1,
                        "Non-Existent Run",
                        BASE_TIME,
                        BASE_TIME.plusMinutes(45),
                        4,
                        Location.INDOOR))
        );

        assertEquals("Failed to create run Non-Existent Run", exception.getMessage());
    }

    @Test
    void shouldNotDeleteIfIdDoesNotExist() {
        var exception = assertThrows(IllegalStateException.class, () ->
                repository.delete(3)
        );

        assertEquals("Failed to delete run", exception.getMessage());
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldNotUpdateIfIdDoesNotExist() {
        var exception = assertThrows(IllegalStateException.class, () ->
                repository.update(new Run(3,
                        "Non-Existent Run",
                        BASE_TIME,
                        BASE_TIME.plusMinutes(45),
                        4,
                        Location.INDOOR), 3)
        );

        assertEquals("Failed to update run", exception.getMessage());
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size());
    }
}
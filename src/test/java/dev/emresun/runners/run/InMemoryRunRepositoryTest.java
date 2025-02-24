package dev.emresun.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class InMemoryRunRepositoryTest {

    InMemoryRunRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRunRepository();
        repository.create(new Run(1, "Morning Run", LocalDateTime.now(),LocalDateTime.now().plusHours(1),10, Location.INDOOR));
        repository.create(new Run(2, "Evening Run", LocalDateTime.now().plusHours(7),LocalDateTime.now().plusHours(8),10, Location.OUTDOOR));

    }

    @Test
    void findAll() {
        assertEquals(2, repository.findAll().size(), "Should return 2 runs");
    }

    @Test
    void findById() {
        assertTrue(repository.findById(1).isPresent(), "Should return run with id 1");
        assertFalse(repository.findById(3).isPresent(), "Should return empty optional");
    }

    @Test
    void create() {
        repository.create(new Run(3, "Alice", LocalDateTime.now(),LocalDateTime.now().plusHours(1),10, Location.INDOOR));
        assertEquals(3, repository.findAll().size(), "Should create a new run");
    }

    @Test
    void update() {
        repository.update(new Run(1, "John", LocalDateTime.now(),LocalDateTime.now().plusHours(1),10, Location.INDOOR), 1);
        assertEquals("John", repository.findById(1).get().name(), "Should update run with id 1");
    }

    @Test
    void delete() {
        repository.delete(1);
        assertEquals(1, repository.findAll().size(), "Should delete run with id 1");
    }

    @Test
    void count() {
        assertEquals(2, repository.count(), "Should return the number of runs which is 2");
    }

    @Test
    void findByLocation() {
        assertEquals(1, repository.findByLocation(Location.INDOOR).size(), "Should return 1 run");
    }


}
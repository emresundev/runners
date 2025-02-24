package dev.emresun.runners.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRunRepository implements RunRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);
    private final List<Run> runs = new ArrayList<>();

    public List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById(Integer id) {
        return runs.stream()
                .filter(run -> run.id().equals(id))
                .findFirst();
    }

    public void create(Run run) {
        runs.add(run);
    }

    public void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isEmpty()) {
            throw new RunNotFoundException();
        }
        runs.remove(existingRun.get());
        runs.add(run);
    }

    public void delete(Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isEmpty()) {
            throw new RunNotFoundException();
        }
        runs.remove(existingRun.get());
    }

    public int count() {
        return runs.size();
    }

    public void saveAll(List<Run> runs) {
        this.runs.addAll(runs);
    }

    public List<Run> findByLocation(Location location) {
        return runs.stream()
                .filter(run -> run.location().equals(location))
                .toList();
    }

    @PostConstruct
    private void init(){
        log.info("Loading sample data");
        runs.add(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(45), 5, Location.INDOOR));
        runs.add(new Run(2, "Evening Run", LocalDateTime.now().plusHours(8), LocalDateTime.now().plusHours(9), 10, Location.OUTDOOR));
    }
}

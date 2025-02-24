package dev.emresun.runners.run;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/runs")
public class RunController {

    private final JdbcClientRunRepository runRepository;

    public RunController(JdbcClientRunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping()
    public List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    public Run findById(@PathVariable int id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void create(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping({"/{id}"})
    void update(@Valid @RequestBody Run run, @PathVariable int id) {
        runRepository.update(run, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }

    @GetMapping("/location/{location}")
    public List<Run> findByLocation(@PathVariable String location) {
        return runRepository.findByLocation(location);
    }



}

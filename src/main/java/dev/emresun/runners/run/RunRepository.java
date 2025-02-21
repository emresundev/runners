package dev.emresun.runners.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Repository
public class RunRepository {
    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(int id) {
        return jdbcClient.sql("select * from Run where id = :id")
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("insert into Run (name, start_time, end_time, distance, location) values (?, ?, ?, ?, ?)")
                .params(run.name(), run.startTime(), run.endTime(), run.distance(), run.location())
                .update();

        Assert.state(updated == 1, "Failed to insert run");
    }

    public void update(Run run, int id) {
        var updated = jdbcClient.sql("update Run set name = ?, start_time = ?, end_time = ?, distance = ?, location = ? where id = ?")
                .params(run.name(), run.startTime(), run.endTime(), run.distance(), run.location(), run.id())
                .update();

        Assert.state(updated == 1, "Failed to update run");
    }

    public void delete(int id) {
        var updated = jdbcClient.sql("delete from Run where id = ?")
                .params(id)
                .update();

        Assert.state(updated == 1, "Failed to delete run");
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public int count() {
        return jdbcClient.sql("select count(*) from Run")
                .query()
                .listOfRows()
                .size();
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from Run where location = :location")
                .params(location)
                .query(Run.class)
                .list();
    }
}

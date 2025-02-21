package dev.emresun.runners.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Repository
public class JdbcClientRunRepository  {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(int id) {
        return jdbcClient.sql("select * from run where id = :id")
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id,name,start_time,end_time,distance,location) values(?,?,?,?,?,?)")
                .params(List.of(run.id(),run.name(),run.startTime(),run.endTime(),run.distance(),run.location().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.name());
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


    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from Run where location = :location")
                .params(location)
                .query(Run.class)
                .list();
    }

    public int count() {
        return jdbcClient.sql("select count(*) from run")
                .query(Integer.class)
                .single();
    }
}

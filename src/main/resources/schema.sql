CREATE TABLE IF NOT EXISTS Run (
    id INT,
    name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    distance INT NOT NULL,
    location VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
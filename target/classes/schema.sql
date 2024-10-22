CREATE TABLE IF NOT EXISTS general_settings (
    id BIGINT PRIMARY KEY,
    cron_expression VARCHAR(255),
    time_zone VARCHAR(255)
);

-- Inserindo uma configuração inicial
INSERT INTO general_settings (id, cron_expression, time_zone)
VALUES (1, '0 7,9,11 11 ? * *', 'America/Sao_Paulo');


CREATE TABLE shedlock (
  name VARCHAR(64),
  lock_until TIMESTAMP(3) NULL,
  locked_at TIMESTAMP(3) NULL,
  locked_by VARCHAR(255),
  PRIMARY KEY (name)
);

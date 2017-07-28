-- PostgreSQL database

CREATE TABLE key (
  id      BIGSERIAL NOT NULL PRIMARY KEY,
  key_one VARCHAR(255),
  key_two VARCHAR(255),
  prefix  VARCHAR(255),
  default_link  VARCHAR(255)
);

CREATE TABLE rule (
  id           BIGSERIAL NOT NULL PRIMARY KEY,
  is_default   BOOLEAN,
  strategy     INT,
  time_pattern VARCHAR(255),
  key_id       BIGINT,
  FOREIGN KEY (key_id) REFERENCES key (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE web_site (
  id           BIGSERIAL    NOT NULL PRIMARY KEY,
  site         VARCHAR(255),
  name         VARCHAR(255) NOT NULL,
  strategy     INT,
  key_one      VARCHAR(255),
  key_two      VARCHAR(255),
  title        BIGINT,
  link         BIGINT,
  image        BIGINT,
  description  BIGINT,
  time         BIGINT,
  title_enable BOOLEAN DEFAULT FALSE,
  link_enable  BOOLEAN DEFAULT FALSE,
  image_enable BOOLEAN DEFAULT FALSE,
  desc_enable  BOOLEAN DEFAULT FALSE,
  time_enable  BOOLEAN DEFAULT FALSE,
  UNIQUE (name),
  FOREIGN KEY (title) REFERENCES rule (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (link) REFERENCES rule (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (image) REFERENCES rule (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (description) REFERENCES rule (id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (time) REFERENCES rule (id) ON UPDATE CASCADE ON DELETE CASCADE
);


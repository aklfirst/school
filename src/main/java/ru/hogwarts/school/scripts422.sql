CREATE TABLE drivers_list (
    id SERIAL,
    name TEXT UNIQUE not null,
    age INTEGER not null check ( age>18 ),
    licence_exists BOOLEAN,
    car_id INTEGER
);

ALTER TABLE drivers_list
    ADD CONSTRAINT name_age_unique UNIQUE (name, age);

ALTER TABLE drivers_list
    RENAME COLUMN id TO driver_id;


ALTER TABLE drivers_list
    ADD PRIMARY KEY (driver_id);

ALTER TABLE drivers_list
ALTER COLUMN name TYPE VARCHAR;

ALTER TABLE drivers_list
    ADD CONSTRAINT name UNIQUE(name);

ALTER TABLE drivers_list
    ADD CONSTRAINT car_id foreign key (car_id) REFERENCES car_list (car_id);


DROP TABLE drivers_list;

CREATE TABLE drivers_list (
                              driver_id SERIAL PRIMARY KEY,
                              name VARCHAR UNIQUE not null,
                              age INTEGER not null check ( age>18 ),
                              licence_exists BOOLEAN,
                              car_id INTEGER REFERENCES car_list(car_id)
);

ALTER TABLE drivers_list
    ADD CONSTRAINT name_age_unique UNIQUE (name, age);



CREATE TABLE car_list (
                          car_id SERIAL PRIMARY KEY,
                              car_brand TEXT not null,
                              car_model TEXT UNIQUE not null,
                              car_price NUMERIC not null check ( car_price>0 )
                              );
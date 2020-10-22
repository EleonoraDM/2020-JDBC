package models;

import java.sql.Connection;

abstract class ExerciseImpl implements Exercise {
    private Connection connection;

    protected ExerciseImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection accessConnection() {
        return connection;
    }
}

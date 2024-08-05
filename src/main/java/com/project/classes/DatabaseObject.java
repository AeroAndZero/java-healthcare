package com.project.classes;

public abstract class DatabaseObject extends Thread {
    public static enum OPERATION{
        GET,
        ADD,
        EDIT,
        DELETE
    }

    public abstract void setOperation(OPERATION operation);
    public abstract void execute();
    public abstract void run();
}

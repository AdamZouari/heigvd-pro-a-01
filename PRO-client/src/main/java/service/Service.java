package service;

public abstract class Service {

    private String url;

    public abstract void connect();

    public abstract void disconnect();
}

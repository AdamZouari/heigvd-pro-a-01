public abstract class Service {

    private String username;
    private String password;
    private String url;

    public abstract void connect();
    public abstract void connect(String username, String password);
    public abstract void disconnect();
}

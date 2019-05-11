package database.Entities;

public class User {

    public enum LANGUE {FR,EN}

    private String username;
    private String telegramUsername; // TODO CHANGE MODELISATION
    private int id;
    private String password;
    private String preference; //Json
    private LANGUE langue;

    public User() {

    }

    public User(String username, String telegramUsername, String password,String preference,int id){
        this.id = id;
        this.username = username;
        this.telegramUsername = telegramUsername;
        this.preference = preference;
        this.password = password;
        this.langue = LANGUE.EN; // anglais par defaut
    }


}

package database.Entities;


public class User {

    enum LANGUE {FR,EN}

    private String username;
    private int id;
    private String password;
    private String preference; //Json
    private LANGUE langue;

    public User() {

    }

    public User(String username,String password,String preference,int id){
        this.id = id;
        this.username = username;
        this.preference = preference;
        this.password = password;
        this.langue = LANGUE.EN; // anglais pas defaut
    }


}

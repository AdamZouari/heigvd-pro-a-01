package entities;


public class User {

    public enum LANGUE {FR,EN}

    private String username;
    private String telegramUsername;
    private int id;
    private String hashPassword;
    private String rules; //Json
    private LANGUE langue = LANGUE.EN; // anglais pas defaut

    public User() {

    }

    public User(int id, String username, String telegramUsername,String hashPassword,String rules, LANGUE langue){
        this(id,username, telegramUsername,hashPassword,rules);
        this.langue = langue;

    }

    public User(int id, String username,  String telegramUsername, String hashPassword,String rules){

        this.id = id;
        this.username = username;
        this.telegramUsername = telegramUsername;
        this.hashPassword = hashPassword;
        this.rules = rules;

    }

    public String toString(){
        return id + " " + username + " "+ telegramUsername+ " " + hashPassword + " " + rules + " " + langue;
    }

    public String getUsername() {
        return username;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public int getId() {
        return id;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getRules() {
        return rules;
    }

    public LANGUE getLangue() {
        return langue;
    }
}

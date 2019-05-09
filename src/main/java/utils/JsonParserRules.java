package utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class JsonParserRules {


    public void parseRule() throws IOException, ParseException {
        // parsing file "rules.json"
        org.json.simple.parser.JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(new FileReader("rules.json"));


    }
}

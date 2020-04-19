package Util;

import DTO.Patient;
import DTO.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;


public class JsonConverter {

    private final Gson gson;

    public JsonConverter() {

        gson = new GsonBuilder().create();
    }

    public String convertToJson(List<Patient> patients) {

        JsonArray jarray = gson.toJsonTree(patients).getAsJsonArray();


        return jarray.toString();
    }

    public String convertToJson(Patient patient) {

        JsonObject jsonObject = gson.toJsonTree(patient).getAsJsonObject();

        return jsonObject.toString();
    }

    public Patient convertToObject(String jsonString){
       return gson.fromJson(jsonString,Patient.class);
    }
    public String convertToJson(Response response) {

        JsonObject jsonObject = gson.toJsonTree(response).getAsJsonObject();

        return jsonObject.toString();
    }
}
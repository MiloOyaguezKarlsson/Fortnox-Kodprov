package services;

import managers.BoxManager;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("/")
public class BoxService {
    @EJB
    BoxManager boxManager;

    @GET
    @Path("/listboxes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoxes(){
        return Response.ok().build();
    }

    @POST
    @Path("/addbox")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postBox(String body){
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject boxToAdd = reader.readObject();
        String reciever = boxToAdd.getString("reciever");
        double weight = Double.parseDouble(boxToAdd.getString("weight"));
        String color = boxToAdd.getString("color");
        String destination = boxToAdd.getString("destination");
        int response = boxManager.postBox(reciever, weight, color, destination);

        return Response.status(response).entity(body).build();
    }
}

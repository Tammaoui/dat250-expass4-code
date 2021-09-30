package no.hvl.dat110.rest.counters;

import com.google.gson.Gson;
import model.Todo;

import java.util.ArrayList;

import static spark.Spark.*;

public class TodoRestService {

    public ArrayList<Todo> dummyDb = new ArrayList<Todo>();



    public void Get() {
        get("/todo/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            for (Todo todo : dummyDb) {
                if(todo.id == Long.parseLong(id)) {
                    return todo.toJson();
                }
            }
            return "No Todo with given Id found";
        });
    }

    public void Put() {
        put("/todo/:id", (req, res) -> {
            Todo toEdit = new Gson().fromJson(req.body(), Todo.class);
            for (int i = 0; i < dummyDb.size(); i++) {
                if(dummyDb.get(i) == toEdit) {
                }
            }
            return null;
        });
    }

    public void Post() {
        post("/todo/", (req, res) -> {
            res.type("application/json");
            Todo newTodo = new Gson().fromJson(req.body(), Todo.class);

            dummyDb.add(newTodo);

            return newTodo.toJson();
        });

    }

    public void Delete() {
        delete("/todo/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            for (int i = 0; i < dummyDb.size(); i++) {
                if(dummyDb.get(i).id == Long.parseLong(id)) {
                    dummyDb.remove(dummyDb.get(i));
                    return "Succesfully deleted";
                }
            }
            return "No Todo with given Id found";
        });
    }

}

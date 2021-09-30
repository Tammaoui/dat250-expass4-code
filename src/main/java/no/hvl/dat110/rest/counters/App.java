package no.hvl.dat110.rest.counters;

import com.google.gson.Gson;
import model.Todo;

import java.util.ArrayList;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
    public static ArrayList<Todo> dummyDb = new ArrayList<Todo>();


    public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8081);
		}

		counters = new Counters();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		get("/hello", (req, res) -> "Hello World!");
		
        get("/counters", (req, res) -> counters.toJson());
 
        get("/counters/red", (req, res) -> counters.getRed());

        get("/counters/green", (req, res) -> counters.getGreen());

        // TODO: put for green/red and in JSON
        // variant that returns link/references to red and green counter
        put("/counters", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	counters = gson.fromJson(req.body(), Counters.class);
        
            return counters.toJson();
        	
        });

        // Exercise 2:

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

        post("/todo/", (req, res) -> {
            res.type("application/json");
            Todo newTodo = new Gson().fromJson(req.body(), Todo.class);

            for (int i = 0; i < dummyDb.size(); i++) {
                if(dummyDb.get(i).id == newTodo.id)  {
                    return "Todo with this ID already exists.";
                }
            }
            dummyDb.add(newTodo);
            return newTodo.toJson();
        });

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

        put("/todo/:id", (req, res) -> {
            Todo toEdit = new Gson().fromJson(req.body(), Todo.class);
            boolean hasEdited = false;
            for (int i = 0; i < dummyDb.size(); i++) {
                if(dummyDb.get(i) == toEdit) {
                    // Edit this one.
                    Todo current = dummyDb.get(i);
                    if(toEdit.getDescription() != current.getDescription()) {
                        current.setDescription(toEdit.getDescription());
                        hasEdited = true;
                    }
                    if(toEdit.getSummary() != current.getSummary()) {
                        current.setSummary(toEdit.getSummary());
                        hasEdited= true;
                    }
                    if(hasEdited) return "Todo updated";
                }
            }
            return "Todo not updated";
        });

    }
    
}

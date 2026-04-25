import java.time.LocalDateTime; //Java built-in class(stores timestamps)
import java.time.format.DateTimeFormatter; //Java built-in class(needed for JSON storage)

//Object-creating class (task object specifically)
public class Task{ 
    private static int lastID = 0; 
    //"static" means this IV is shared among all task objects
    // makes sure that each new task gets a unique ID
    private int id; //unique identifier
    String description; //What is the task?
    private StatusTracker status; //Is taks completed or not?
    private LocalDateTime createdAt; //When was this task created?
    private LocalDateTime updatedAt; //When was task last modified?

    private static final DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Standard format for saving date/time in JSON.
    // Example: 2026-04-25T14:30:00
    // static final → constant shared by all objects.

    public Task (String d){ //runs when a new task is created
        this.id = ++lastID; 
        //increments lastID first, then assigns it [pre-increment]
        // each new task gets a new, unique ID
        this.description = d; //stores task description
        this.status = StatusTracker.TODO; //default state when task is created is "Todo"
        this.createdAt = LocalDateTime.now(); //sets timestamp to current time
        this.updatedAt = LocalDateTime.now(); //sets timestamp to current time
    }

    public int getID(){ //Allow other parts of program to access task's ID
        return id;
    }

    public void markInProgress(){ //changes task status
        this.status = StatusTracker.IN_PROGRESS; //updates status to "In Progress"
        this.updatedAt = LocalDateTime.now(); //Update timestamp to record when change took place
    }

    public String toJson(){ //Converts object into a JSON string (easy to save to json file)
         return "{\"id\":\"" +  //starts JSON string
                    id + //adds task ID
                    "\", \"description\":\"" + //adds clean description and removes extra spaces
                    description.strip() +  //adds clean description and removes extra spaces
                    "\", \"status\":\"" + //adds Status as string
                    status.toString() + //adds Status as string
                    "\", \"createdAt\":\"" + //Formats created time into String
                    createdAt.format(formatter) +  //Formats created time into String
                    "\", \"updatedAt\":\"" +  //Formats updated time into String
                    updatedAt.format(formatter) + //Formats updated time into String
                    "\"}"; //ends JSON

                    //Final Result = {"id":"1","description":"Homework","status":"Todo","createdAt":"...","updatedAt":"..."}
    }

    public static Task fromJson(String json){ //Takes JSON string and reconstructs a task object
        json = json.replace("{", "").replace("}", "").replace("\"", ""); 
        //removes braces({}) and quotes("")
        // simplifies parsing
        String[] json1 = json.split(","); // splits String into its different parts (id, desc, status, time)

        String id = json1[0].split(":")[1].strip(); //gets ID value
        String description = json1[1].split(":")[1].strip(); //gets task description
        String statusString = json1[2].split(":")[1].strip(); //gets status text
        String createdAtStr = json1[3].split("[a-z]:")[1].strip();
        //extracts timestamps
        //slightly hacky split in order to avoid splitting time colons
        String updatedAtStr = json1[4].split("[a-z]:")[1].strip();

        StatusTracker status = StatusTracker.valueOf(statusString.toUpperCase().replace(" ", "_")); //converts text like "in progress" to "IN_PROGRESS"

        Task task = new Task(description); //creates new task w/ wrong ID
        task.id = Integer.parseInt(id); //fixes ID
        task.status = status; //fixes status
        task.createdAt = LocalDateTime.parse(createdAtStr, formatter);
        //restores timestamps
        task.updatedAt = LocalDateTime.parse(updatedAtStr, formatter);

        if(Integer.parseInt(id) > lastID){ //ensure future tasks don't reuse old IDs
            lastID = Integer.parseInt(id);
        }

        return task; //task is finallized and returned
    }

    public StatusTracker getStatus(){ // lets other parts of the program keep track of status
        return status;
    } 

    @Override
    public String toString() { //defines how task is printed in CLI
        return "id: " + id + ", description: " + description.strip() + ", status: " + status.toString() +
                ", createdAt: " + createdAt.format(formatter) + ", updatedAt: " + updatedAt.format(formatter);
    }

    public void updateDescription(String new_description) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDescription'");
    }

    public void markDone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'markDone'");
    }
}

public enum StatusTracker{ //Declares an enum(defines a fixed set of constants) named Status
    //Possible values of the Status enum
    //Each represents a task state in my tracker
    //Value in parenthesis is passed to constructor below
    TODO("Todo"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    //Private Instance Variable
    //Each enum state will have it's own "num"
    //Stores the readable version of each state's status
    private final String label;

    //Enum Constructor(runs once for each enum constant when the program starts)
    //Example:
    //TO DO = "Todo"
    //IN_PROGRESS = "In Progress"
    //DONE = "Done"
    StatusTracker(String num) {
        this.label = num; //Assigns the passed-in value to the Instance Variable
    }

    //Accessor Method for the num Instance Variable
    //Allows the I.V. to be accessed by other parts of the program
    public String getLabel(){ 
        return label;
    }

    @Override //Overrides the default toString() method
    //By default, enums return their name (i.e "TO DO");
    //However, this override makes it return the nicer formatted string(i.e. "Todo");
    public String toString(){
        return label;
    }
}
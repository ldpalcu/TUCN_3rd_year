package business;

public abstract class Operations {

    public abstract void  update();

    public abstract void create();

    public abstract void delete();

    public abstract void viewObject();

    public void  execute(String option){

        switch(option){
            case "Add" :
                create();
                break;
            case "Select":
                viewObject();
                break;
            case "Delete":
                delete();
                break;
            case "Update":
                update();
                break;
        }
    }
}

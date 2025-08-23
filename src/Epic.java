import java.util.ArrayList;
public class Epic extends Task{

    ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name,String definition,Status status){
        super(name,definition,status);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteAllSubtasks(){
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);

    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getIdNumber() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks.size() +
                '}';
    }


}


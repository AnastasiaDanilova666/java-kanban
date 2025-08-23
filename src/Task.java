import java.util.Objects;
public class Task {
    private String name;
    private String definition;
    private int idNumber;
    private Status status;

    public Task(String name, String definition,Status status) {
        this.name = name;
        this.definition = definition;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(name,otherTask.name)&&
                Objects.equals(definition,otherTask.definition)&&
                (idNumber == otherTask.idNumber);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name,definition,idNumber);
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + idNumber +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

}

public class Subtask extends Task{
    private int epicId;

    public Subtask(String name,String definition ,Status status, int epicId){
        super(name,definition,status);
        this.epicId = epicId;
    }
    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getIdNumber() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}

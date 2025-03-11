import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void updateEpicStatus() {
        if (subtasks.isEmpty()) {
            setStatus(Status.NEW);  // Используем метод-setter
            return;
        }
        boolean allDone = subtasks.stream().allMatch(subtask -> subtask.getStatus() == Status.DONE);
        boolean anyInProgress = subtasks.stream().anyMatch(subtask -> subtask.getStatus() == Status.IN_PROGRESS);

        if (allDone) {
            this.status = Status.DONE;
        } else if (anyInProgress) {
            this.status = Status.IN_PROGRESS;
        } else {
            this.status = Status.NEW;
        }
    }

    // Переопределяем toString для эпика
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksCount=" + subtasks.size() +
                '}';
    }

}
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks;
    private Map<Integer, Subtask> subtasks;
    private Map<Integer, Epic> epics;
    private int idCounter;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        idCounter = 1;
    }

    private int generateId() {
        return idCounter++;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(title, description, generateId(), Status.NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    public Subtask createSubtask(String title, String decription, int epicId) {
        Subtask subtask = new Subtask(title, decription, generateId(), Status.NEW, epicId);
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(epicId)) {
            epics.get(epicId).addSubtask(subtask);
            epics.get(epicId).updateEpicStatus();
        }
        return subtask;
    }

    public Epic createEpic(String title, String description) {
        Epic epic = new Epic(title, description, generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    public void deleteTask(int id) {
        tasks.remove(id);
    }
    public void deleteSubtasks(int id) {
        subtasks.remove(id);
    }
    public void deleteEpic(int id) {
        epics.remove(id);
    }
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).updateEpicStatus();
        }
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.updateEpicStatus();
    }
}
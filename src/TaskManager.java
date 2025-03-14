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

    public Task createTask(String title, String description) {
        Task task = new Task(title, description, generateId(), Status.NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    public Subtask createSubtask(String title, String description, int epicId) {
        Subtask subtask = new Subtask(title, description, generateId(), Status.NEW, epicId);
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

    public List<Epic> getAllEpics() { // Метод для получения всех эпиков добавлен
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtaskToDelete = subtasks.remove(id);
            Epic relatedEpic = epics.get(subtaskToDelete.getEpicId());
            if (relatedEpic != null) {
                relatedEpic.getSubtasks().remove(subtaskToDelete); // Удаляем из эпика
                relatedEpic.updateEpicStatus(); // Обновление статуса эпика
            }
        }
    }

    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epicToDelete = epics.remove(id);
            for (Subtask subtask : epicToDelete.getSubtasks()) {
                subtasks.remove(subtask.getId()); // Удаляем связанные подзадачи
            }
        }
    }
    // Метод для удаления всех эпиков и связанных подзадач добавлен
    public void deleteAllEpics() {
        for (Epic epic : new ArrayList<>(epics.values())) {
            deleteEpic(epic.getId()); // Удаляем каждый эпик с помощью существующего метода
        }
    }

    // Получение списка всех подзадач определённого эпика добавлен
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = getEpic(epicId);
        return epic != null ? epic.getSubtasks() : new ArrayList<>(); // Возвращаем подзадачи эпика или пустой список
    }

    // Удаление всех задач
    public void deleteAllTasks() {
        tasks.clear();
    }

    // Удаление всех подзадач
    public void deleteAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtasks().remove(subtask); // Удаляем подзадачу из эпика
                epic.updateEpicStatus(); // Обновление статуса эпика
            }
        }
        subtasks.clear();
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

    private int generateId() {
        return idCounter++;
    }
}
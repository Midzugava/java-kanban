public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("Переезд", "Организовать переезд на новую квартиру");
        Task task2 = taskManager.createTask("Купить продукты", "Сходить в магазин за продуктами");

        Epic epic1 = taskManager.createEpic("Подготовка к отпуску", "Подготовка всех дел перед поездкой");
        Subtask subtask1 = taskManager.createSubtask("Купить билеты",
                "Купить билеты на самолет", epic1.getId());
        Subtask subtask2 = taskManager.createSubtask("Забронировать отель",
                "Забронировать отель на всермя отпуска", epic1.getId());

        System.out.println("Все задачи");
        System.out.println(taskManager.getAllTasks());

        System.out.println("Все подзадачи");
        System.out.println(taskManager.getAllSubtasks());

        // Изменение статуса подзадачи
        subtask1.setStatus(Status.DONE); 
        taskManager.updateSubtask(subtask1); // Обновление подзадачи

        // Меняем статус подзадачи
        subtask1 = new Subtask(subtask1.getTitle(), subtask1.getDescription(), subtask1.getId(),
                Status.DONE, epic1.getId());
        taskManager.updateSubtask(subtask1);

        epic1.updateEpicStatus();
        taskManager.updateEpic(epic1);
        System.out.println("Статус после обновления подзадач: " + epic1.getStatus());

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpic(epic1.getId());
    }
}
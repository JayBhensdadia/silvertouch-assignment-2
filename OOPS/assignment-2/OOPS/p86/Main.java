import java.util.ArrayList;
import java.util.List;

class TeamMember {
    private String memberId;
    private String memberName;
    private List<Task> assignedTasks;

    public TeamMember(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.assignedTasks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void assignTask(Task task) {
        assignedTasks.add(task);
        System.out.println("Task '" + task.getTaskName() + "' assigned to team member '" + memberName + "'.");
    }

    public void completeTask(Task task) {
        if (assignedTasks.contains(task)) {
            assignedTasks.remove(task);
            task.setCompleted(true);
            System.out.println("Task '" + task.getTaskName() + "' completed by team member '" + memberName + "'.");
        } else {
            System.out.println("Team member '" + memberName + "' is not assigned to task '" + task.getTaskName() + "'.");
        }
    }
}

class Task {
    private String taskId;
    private String taskName;
    private String description;
    private boolean completed;

    public Task(String taskId, String taskName, String description) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.completed = false;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

class Project {
    private String projectId;
    private String projectName;
    private List<Task> tasks;

    public Project(String projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.tasks = new ArrayList<>();
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task '" + task.getTaskName() + "' added to project '" + projectName + "'.");
    }

    public void displayProjectStatus() {
        System.out.println("Project Status for Project '" + projectName + "':");
        for (Task task : tasks) {
            String status = task.isCompleted() ? "Completed" : "Pending";
            System.out.println("Task: " + task.getTaskName() + ", Status: " + status);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TeamMember member1 = new TeamMember("TM001", "hari");
        TeamMember member2 = new TeamMember("TM002", "om");

        Task task1 = new Task("T001", "Design Feature A", "Design the new feature for the project.");
        Task task2 = new Task("T002", "Implement Feature A", "Implement the new feature based on the design.");

        Project project = new Project("P001", "Project X");

        project.addTask(task1);
        project.addTask(task2);

        member1.assignTask(task1);
        member2.assignTask(task2);

        member1.completeTask(task1);

        project.displayProjectStatus();
    }
}

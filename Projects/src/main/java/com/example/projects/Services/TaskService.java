package com.example.projects.Services;
import com.example.projects.Entities.Status;
import com.example.projects.Entities.Tasks;
import com.example.projects.Repositories.TasksRep;
import jakarta.el.MethodNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TasksRep tasksRepo;

    @Override
    public List<Tasks> getTasksByProjectId(Long projectId) {
        return tasksRepo.findByProjectId(projectId);
    }

    @Override
    public void moveTask(Long id, Status newStatus) {
        Optional<Tasks> optionalTask = tasksRepo.findById(id);
        if (optionalTask.isPresent()) {
            Tasks task = optionalTask.get();
            task.setStatus(newStatus);
            tasksRepo.save(task);
        } else {
            throw new MethodNotFoundException("Task not found with id: " + id);
        }
    }

    @Override
    public List<Tasks> getTasksByStatus(Status status) {
        return tasksRepo.findByStatus(status);
    }
}

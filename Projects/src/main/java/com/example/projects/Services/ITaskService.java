package com.example.projects.Services;

import com.example.projects.Entities.Status;
import com.example.projects.Entities.Tasks;

import java.util.List;

public interface ITaskService {

    void moveTask(Long id, Status newStatus);
    List<Tasks> getTasksByStatus(Status status);
    List<Tasks> getTasksByProjectId(Long projectId);
}
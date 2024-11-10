package com.example.projects.Services;


import com.example.projects.Entities.GroupProject;


import java.util.List;

public interface IGroupProjectService {
    List<GroupProject> Getgroups();

    GroupProject getById(long id);
    GroupProject updategroup(GroupProject groupProject);
    void removegroup(long id);
    GroupProject addgroupAndAssignToproject(GroupProject groupProject, long id );

    List<GroupProject> getProjectsForUser(long studentId);
    boolean isGroupAssignedToProject(long projectId);
    void assignStudentsToGroup(long projectId);
}
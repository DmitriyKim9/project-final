package com.javarush.jira.bugtracking.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskTagService {
    private final TaskTagRepository repository;
//    private final Handlers.TaskExtHandler handler;

    public List<Long> getAllTasksForTagNames(List<String> tags) {
        return repository.findAllTasksByTagNames(tags);
    }

    public List<String> getAllTagsForTask(long id) {
        return repository.findAllTagsForTask(id);
    }

    public void updateTagsForTask(long id, List<String> taskTags) {
        List<TaskTag> oldTaskTags = repository.findAllTaskTagsForTask(id);
        repository.deleteAll(oldTaskTags);
        for (String taskTag :
                taskTags) {
            repository.saveAndFlush(new TaskTag(new TaskTagCompositePrimaryKey(id, taskTag)));

        }
    }
}

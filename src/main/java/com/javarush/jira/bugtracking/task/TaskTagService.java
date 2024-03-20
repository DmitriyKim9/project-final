package com.javarush.jira.bugtracking.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskTagService {
    private final TaskTagRepository taskTagRepository;
//    private final Handlers.TaskExtHandler handler;

    public List<Long> getAllTasksForTagNames(List<String> tags) {
        return taskTagRepository.findAllTasksByTagNames(tags);
    }

    public List<String> getAllTagsForTask(long id) {
        return taskTagRepository.findAllTagsForTask(id);
    }

    public void updateTagsForTask(long id, List<String> taskTags) {
        List<TaskTag> oldTaskTags = taskTagRepository.findAllTaskTagsForTask(id);
        taskTagRepository.deleteAll(oldTaskTags);
        for (String taskTag :
                taskTags) {
            taskTagRepository.saveAndFlush(new TaskTag(new TaskTagCompositePrimaryKey(id, taskTag)));

        }
    }
}

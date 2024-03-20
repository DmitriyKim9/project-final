package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskTagRepository extends BaseRepository<TaskTag> {
    @Query(value = "SELECT DISTINCT t.id " +
            "FROM Task t JOIN TaskTag tt " +
            "ON t.id=tt.taskTagCompositePrimaryKey.taskId " +
            "WHERE tt.taskTagCompositePrimaryKey.tag IN (:taskTags)")
    List<Long> findAllTasksByTagNames(List<String> taskTags);

    @Query("SELECT tt.taskTagCompositePrimaryKey.tag " +
            "FROM Task t JOIN TaskTag tt " +
            "ON t.id=tt.taskTagCompositePrimaryKey.taskId " +
            "WHERE t.id=:taskId")
    List<String> findAllTagsForTask(long taskId);

    @Query("SELECT tt " +
            "FROM Task t JOIN TaskTag tt " +
            "ON t.id=tt.taskTagCompositePrimaryKey.taskId " +
            "WHERE t.id=:taskId")
    List<TaskTag> findAllTaskTagsForTask(long taskId);
}

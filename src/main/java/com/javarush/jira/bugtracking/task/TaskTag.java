package com.javarush.jira.bugtracking.task;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskTag {

    @EmbeddedId
    private TaskTagCompositePrimaryKey taskTagCompositePrimaryKey;

}


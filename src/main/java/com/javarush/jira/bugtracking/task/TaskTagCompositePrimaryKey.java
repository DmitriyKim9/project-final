package com.javarush.jira.bugtracking.task;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskTagCompositePrimaryKey implements Serializable {

    @Column(name = "task_id")
    private long taskId;

    @Column(name = "tag")
    private String tag;
}

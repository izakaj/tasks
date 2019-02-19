package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "my_task", "task_content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(new Long(1L), task.getId());
        assertEquals("my_task", task.getTitle());
        assertEquals("task_content", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "my_task", "task_content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(new Long(1L), taskDto.getId());
        assertEquals("my_task", taskDto.getTitle());
        assertEquals("task_content", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "my_task", "task_content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());
        taskList.forEach(taskDto -> {
            assertEquals(new Long(1L), taskDto.getId());
            assertEquals("my_task", taskDto.getTitle());
            assertEquals("task_content", taskDto.getContent());
        });
    }

    @Test
    public void testMapToTaskList() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "my_task", "task_content");
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto);
        //When
        List<Task> taskList = taskMapper.mapToTaskList(taskDtoList);
        //Then
        assertNotNull(taskList);
        assertEquals(1, taskList.size());
        taskList.forEach(task -> {
            assertEquals(new Long(1L), task.getId());
            assertEquals("my_task", task.getTitle());
            assertEquals("task_content", task.getContent());
        });
    }

}
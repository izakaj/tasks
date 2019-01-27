package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository repository;

    @Test
    public void shouldGetAllTasks() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(repository.findAll()).thenReturn(taskList);

        //When
        List<Task> tasks = dbService.getAllTasks();

        //Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        tasks.forEach(theTask -> {
            assertEquals(new Long(1), theTask.getId());
            assertEquals("test_title", theTask.getTitle());
            assertEquals("test_content", theTask.getContent());
        });
    }

    @Test
    public void shouldGetTask() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        Long id = 1L;
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> taskOptional = dbService.getTaskById(id);
        List<Task> tasks = taskOptional
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());

        //Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        tasks.forEach(theTask -> {
            assertEquals(new Long(1), theTask.getId());
            assertEquals("test_title", theTask.getTitle());
            assertEquals("test_content", theTask.getContent());
        });
    }

    @Test
    public void shouldSaveTask() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");

        when(repository.save(task)).thenReturn(task);

        //When
        Task theTask = dbService.saveTask(task);

        //Then
        assertEquals(new Long(1), theTask.getId());
        assertEquals("test_title", theTask.getTitle());
        assertEquals("test_content", theTask.getContent());
    }

    @Test
    public void shouldDeleteTaskById() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        Long id = 1L;
        doNothing().when(repository).deleteById(1L);

        //When
        dbService.deleteTaskById(id);

        //Then
        verify(repository, times(1)).deleteById(1L);
    }
}
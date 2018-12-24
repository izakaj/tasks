package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final DbService dbService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<Task> getTasks() {
        return taskMapper.mapToTaskList(dbService.getAllTasks());
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable("id") Long taskId) throws TaskNotFoundException {
        Task task = dbService.getTaskById(taskId).orElseThrow(TaskNotFoundException::new);
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long taskId) {
        dbService.deleteTaskById(taskId);
    }

    @Transactional
    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable("id") Long taskId, @RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        dbService.saveTask(task);
        return taskMapper.mapToTaskDto(task);
    }

    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto) {
        dbService.saveTask(taskMapper.mapToTask(taskDto));
    }
}

package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test",
                "Test description",
                "top",
                "1"
        );
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "323",
                "Test",
                "http://test.com"
        );

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        doNothing().when(emailService).send(any(Mail.class));

        //When
        CreatedTrelloCardDto theCreatedCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("323", theCreatedCard.getId());
        assertEquals("Test", theCreatedCard.getName());
        assertEquals("http://test.com", theCreatedCard.getShortUrl());
        verify(emailService, times(1)).send(any(Mail.class));
    }
}
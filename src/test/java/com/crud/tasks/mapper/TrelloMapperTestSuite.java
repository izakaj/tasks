package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTestSuite {


    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        List<TrelloListDto> list1 = new ArrayList<>();
        list1.add(new TrelloListDto("0001", "To Do",false));
        list1.add(new TrelloListDto("0002", "Done",true));
        boardDtoList.add(new TrelloBoardDto("1111", "First board", list1));
        List<TrelloListDto> list2 = new ArrayList<>();
        list2.add(new TrelloListDto("0003", "Priorities",false));
        list2.add(new TrelloListDto("0004", "Other",true));
        boardDtoList.add(new TrelloBoardDto("2222", "Second board", list2));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(boardDtoList);

        //Then
        assertEquals(2, trelloBoards.size());
    }

    @Test
    public void testMapToBoardDtos() {
        //Given
        List<TrelloBoard> boardList = new ArrayList<>();
        List<TrelloList> list1 = new ArrayList<>();
        list1.add(new TrelloList("0001", "To Do",false));
        list1.add(new TrelloList("0002", "Done",true));
        boardList.add(new TrelloBoard("1111", "First board", list1));
        List<TrelloList> list2 = new ArrayList<>();
        list2.add(new TrelloList("0003", "Priorities",false));
        list2.add(new TrelloList("0004", "Other",true));
        boardList.add(new TrelloBoard("2222", "Second board", list2));

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardDtos(boardList);

        //Then
        assertEquals(2, trelloBoardDtos.size());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> theListDto = new ArrayList<>();
        theListDto.add(new TrelloListDto("0001", "To Do",false));
        theListDto.add(new TrelloListDto("0002", "In progress",false));
        theListDto.add(new TrelloListDto("0003", "Done",true));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(theListDto);

        //Given
        assertEquals(3, trelloLists.size());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> theList = new ArrayList<>();
        theList.add(new TrelloList("0001", "To Do",false));
        theList.add(new TrelloList("0002", "In progress",false));
        theList.add(new TrelloList("0003", "Done",true));

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(theList);

        //Given
        assertEquals(3, trelloListDtos.size());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Random name", "The description", "top", "555");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("Random name", trelloCardDto.getName());
        assertEquals("The description", trelloCardDto.getDescription());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Random name2", "The description2", "top",
                "555");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("Random name2", trelloCard.getName());
        assertEquals("The description2", trelloCard.getDescription());
    }

}
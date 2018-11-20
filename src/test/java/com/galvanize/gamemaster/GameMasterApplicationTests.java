package com.galvanize.gamemaster;

import com.galvanize.gamemaster.controller.ItemController;
import com.galvanize.gamemaster.model.Item;
import com.galvanize.gamemaster.repository.ItemRepository;
import com.galvanize.gamemaster.service.ItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;




//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class GameMasterApplicationTests {

   // private static final Logger LOGGER = LoggerFactory.getLogger(GameMasterApplicationTests.class);

//    @Autowired
//    private MockMvc mockMvc;
//
    @MockBean
   private ItemService itemService;
    @MockBean
    private ItemRepository itemRepository;

//    @Before
//    public void setUp() throws Exception{
//
//    }
//
//    @After
//    public void tearDown() throws Exception{
//
//    }

    @Test
    public void contextLoads(){
       // addItemTest();
    }

//	@Test
//	public void addItemTest() throws Exception{
//        Item mockItem  = new Item();
//
//        when(itemService.addItem(mockItem)).thenReturn(mockItem);
//
//        // use mock request
//
//        mockMvc.perform(MockMvcRequestBuilders.get("")
//                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(print())
//                .andReturn();
//
//        verify(itemService, Mockito.times(1)).addItem(mockItem);
//
//
//	}


    @Test
    public void addItemTest(){
        //assertTrue(true);
        //setup
        Item expectedResult = new Item();

        ItemController controller = new ItemController(itemService);
        when(itemService.addItem(expectedResult)).thenReturn(expectedResult);

        //Execute
        Item actualResult = controller.addItem(expectedResult);


        //Assert
        assertEquals(expectedResult, actualResult);
    }


}

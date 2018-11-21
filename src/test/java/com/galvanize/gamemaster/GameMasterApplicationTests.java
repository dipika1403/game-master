package com.galvanize.gamemaster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gamemaster.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Assert;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import java.io.IOException;
import java.util.Arrays;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GameMasterApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GameMasterApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void contextLoads(){
       // addItemTest();
    }

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    // Senario # 1 - add item
    @Test
    public void addItemTest() throws Exception{
        //assertTrue(true);
        Item item = new Item(22L,"Stick");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/itemservice/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(item));

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(print());

    }

    //Senario # 2 - change item
    @Test
    public void addItemChangeTest() throws Exception{
        //assertTrue(true);
        Item item = new Item(2L, "wizard");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/itemservice/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(item));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print());

    }

    //Senario # 3 - Delete item
    //http://localhost:8080/itemservice/delete/4 - 200 - Item Deleted.
    @Test
    public void deleteItemTest() throws Exception {
        mockMvc.perform(delete("/itemservice/delete/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    //Senario # 4
    //http://localhost:8080/itemservice/delete/45 - 404 - Item not Found
    @Test
    public void deleteItemNotFoundTest() throws Exception {
        mockMvc.perform(delete("/itemservice/delete/45")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
                .andDo(print());
    }

    // Senario # 5
    // http://localhost:8080/itemservice/get/3 - 200 - Item retrieved.
    @Test
    public void getItemByIdTest() throws Exception {
        Item item = new Item(5L, "stick");
        mockMvc.perform(get("/itemservice/get/3").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    // Senario # 6
    // http://localhost:8080/itemservice/get/34 - 404 - Item Not Found.
    @Test
    public void getItemByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/itemservice/get/34").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    //Senario # 7
    // http://localhost:8080/itemservice/get/item/sword
    // there are 5 rows with "sword" in table.
    @Test
    public void getAllItemsByName() throws Exception{
        mockMvc.perform(get("/itemservice/get/item/sword").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[*].id", contains(2,3,4,5,6)))  // sorted by id asc by default
                .andDo(print());

    }

    // Senario # 8
    // htto://localhost:8080/itemservice/allitems
    @Test
    public void getAllItems() throws Exception {
        mockMvc.perform(get("/itemservice/allitems").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
}

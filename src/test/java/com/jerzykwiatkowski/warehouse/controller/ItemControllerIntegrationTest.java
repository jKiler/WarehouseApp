package com.jerzykwiatkowski.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerzykwiatkowski.warehouse.entity.Item;
import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    @MockBean
    ItemRepository itemRepository;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void shouldReturnAllItemsByActionGet() throws Exception {

        //given
        Item first = new Item();
        first.setId(1L);
        first.setName("Microphone");
        first.setType("Condenser");
        first.setModel("AKG C3000");
        first.setCategory("Sound");
        first.setQuantity(4);
        first.setInStock(4);

        Item second = new Item();
        second.setId(2L);
        second.setName("Microphone");
        second.setType("Condenser");
        second.setModel("Shure PG48");
        second.setCategory("Sound");
        second.setQuantity(8);
        second.setInStock(8);

        String expectedResponse = "{\"_embedded\":{\"itemList\":[{\"id\":1,\"name\":\"Microphone\",\"type\":\"Condenser\",\"model\":\"AKG C3000\",\"category\":\"sound\",\"quantity\":4,\"inStock\":4,\"_links\":{\"self\":{\"href\":\"http://localhost/items/1\"},\"items\":{\"href\":\"http://localhost/items\"}}},{\"id\":2,\"name\":\"Microphone\",\"type\":\"Condenser\",\"model\":\"Shure PG48\",\"category\":\"sound\",\"quantity\":8,\"inStock\":8,\"_links\":{\"self\":{\"href\":\"http://localhost/items/2\"},\"items\":{\"href\":\"http://localhost/items\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost/items\"}}}";

        //when
        when(itemRepository.findAll()).thenReturn(Arrays.asList(first, second));

        //then
        assertThat(mockMvc.perform(get("/items"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .andExpect(jsonPath("$._embedded.itemList", hasSize(2)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                is(expectedResponse));

        verify(itemRepository, times(1)).findAll();
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void shouldReturnOneItemByActionGet() throws Exception {

        //given
        Item first = new Item();
        first.setId(1L);
        first.setName("Microphone");
        first.setType("Condenser");
        first.setModel("AKG C3000");
        first.setCategory("Sound");
        first.setQuantity(4);
        first.setInStock(4);

        String expectedResponse = "{\"id\":1,\"name\":\"Microphone\",\"type\":\"Condenser\",\"model\":\"AKG C3000\",\"category\":\"sound\",\"quantity\":4,\"inStock\":4,\"_links\":{\"self\":{\"href\":\"http://localhost/items/1\"},\"items\":{\"href\":\"http://localhost/items\"}}}";

        //when
        when(itemRepository.findById(any())).thenReturn(java.util.Optional.of(first));

        //then
        assertThat(mockMvc.perform(get("/items/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .andExpect(jsonPath("$.id", is(1)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                is(expectedResponse));

        verify(itemRepository, times(1)).findById(any());
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnCreatedByActionPost() throws Exception {

        //given
        Item first = new Item();
        first.setId(1L);
        first.setName("Microphone");
        first.setType("Condenser");
        first.setModel("AKG C3000");
        first.setCategory("Sound");
        first.setQuantity(4);
        first.setInStock(4);

        //when
        when(itemRepository.save(any())).thenReturn(first);

        //then
        mockMvc.perform(post("/items")
                .content(asJsonString(first))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemRepository, times(1)).save(any());
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnForbiddenByActionPost() throws Exception {

        //given
        Item first = new Item();
        first.setId(1L);
        first.setName("Microphone");
        first.setType("Condenser");
        first.setModel("AKG C3000");
        first.setCategory("Sound");
        first.setQuantity(4);
        first.setInStock(4);

        //when
        when(itemRepository.save(any())).thenReturn(first);

        //then
        mockMvc.perform(post("/items")
                .content(asJsonString(first))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemRepository, times(0)).save(any());
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnNoContentByActionDelete() throws Exception {

        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isNoContent());

        verify(itemRepository, times(1)).deleteById(any());
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnCreatedByActionPut() throws Exception {

        //given
        Item first = new Item();
        first.setId(1L);
        first.setName("Microphone");
        first.setType("Condenser");
        first.setModel("AKG C3000");
        first.setCategory("Sound");
        first.setQuantity(4);
        first.setInStock(4);

        //when
        when(itemRepository.save(any())).thenReturn(first);

        //then
        mockMvc.perform(put("/items/1")
                .content(asJsonString(first))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemRepository, times(1)).findById(any());
        verify(itemRepository, times(1)).save(any());
        verifyNoMoreInteractions(itemRepository);
    }
}
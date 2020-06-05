package com.tecsup.petclinic.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.Owner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class OwnerControllerTest {
private static final ObjectMapper om = new ObjectMapper();
    
	@Autowired
	private MockMvc mockMvc;


	@Test
	public void testGetOwners() throws Exception {

		//int NRO_RECORD = 73;
		int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/owners"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType(MediaType.APPLICATION_JSON_UTF8))
		//		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
					.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerOK() throws Exception {

		String FIRST_NAME = "George";
		String LAST_NAME = "Franklin";
		String ADDRESS = "110 W. Liberty St.";
		String CITY = "Madison";
		String TELEFONE = "6085551023";

		mockMvc.perform(get("/owners/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.first_name", is(FIRST_NAME)))
				.andExpect(jsonPath("$.last_name", is(LAST_NAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)))
				.andExpect(jsonPath("$.city", is(CITY)))
				.andExpect(jsonPath("$.telephone", is(TELEFONE)));
				//.andExpect(jsonPath("$.birthDate", is("2000-09-07")));
				
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerKO() throws Exception {

		mockMvc.perform(get("/owners/666"))
				.andExpect(status().isNotFound());

	}

	/**
	 * 
	 * @throws Exception
	 */
    @Test
    public void testCreatePet() throws Exception {
		
    	String FIRST_NAME = "Richard";
		String LAST_NAME = "Romero";
		String ADDRESS = "1234 AV.AZANGARO";
		String CITY = "LIMA";
		String TELEFONE = "1236789";
		
		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEFONE);
	
	    mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(owner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated())
	            //.andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.first_name", is(FIRST_NAME)))
				.andExpect(jsonPath("$.last_name", is(LAST_NAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)))
				.andExpect(jsonPath("$.city", is(CITY)))
				.andExpect(jsonPath("$.telephone", is(TELEFONE)));
    
	}

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testDeletePet() throws Exception {

    	String FIRST_NAME = "PAUL2";
		String LAST_NAME = "RICALDI2";
		String ADDRESS = "1234 AV.AREQUIPA2";
		String CITY = "LIMA2";
		String TELEFONE = "123456787";
		
		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEFONE);
		
		ResultActions mvcActions = mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(owner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated());
	            
		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id ))
                /*.andDo(print())*/
                .andExpect(status().isOk());
    }
}

package com.cts.loan.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cts.loan.entity.LoanEntity;
import com.cts.loan.entity.UserEntity;
import com.cts.loan.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class LoanControllerTest {

	private MockMvc mockMvc;

	@Mock
	private LoanService loanService;

	@InjectMocks
	private LoanController loanController;

	private List<LoanEntity> loanlist = new ArrayList<>();
	private LoanEntity loan1;
	private LoanEntity loan2;
	private UserEntity user1;
	private AutoCloseable closeable;

	@BeforeEach
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
		loan1 = new LoanEntity("200", "Education Loan", "6yrs", "7 lakh", "Sha", "Laa", "Chennai");
		loan2 = new LoanEntity("201", "Business Loan", "4yrs", "3 lakh", "Sha", "Laa", "Vellore");
		loanlist.add(loan1);
		loanlist.add(loan2);
		user1 = new UserEntity("1000", "pwd123", "User123", null, null);
	}

	@Test
	public void testViewAllLoan() throws Exception {
		when(loanService.getAllLoan()).thenReturn(loanlist);
		mockMvc.perform(get("/viewAllLoan").header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSearch() throws Exception {
		when(loanService.searchByLoanNoAndOrFirstAndOrLastName("", "", "")).thenReturn(loanlist);
		mockMvc.perform(get("/search").param("loanNo", "").param("firstName", "").param("lastName", "").header(
				"Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testDeleteLoan() throws Exception {
		when(loanService.deleteLoan(loan1.getLoanNo())).thenReturn("Deleted");
		mockMvc.perform(delete("/deleteLoan/{loanNo}", loan1.getLoanNo()).header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUpdateLoan() throws Exception {
		when(loanService.updateLoan(loan1, "200")).thenReturn(loan1);
		mockMvc.perform(put("/updateLoan/{loanNo}", loan1.getLoanNo()).header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan1))).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testViewUserLoan() throws Exception {
		when(loanService.getUser(user1.getUsername())).thenReturn(user1);
		when(loanService.getLoanbyUser(user1, "")).thenReturn(loanlist);
		mockMvc.perform(get("/viewUserLoan/{username}", user1.getUsername()).param("loanNo", "").header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateLoan() throws Exception {
		when(loanService.addLoan(loan1)).thenReturn(loan1);
		mockMvc.perform(post("/createLoan").header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(loan1))).andExpect(status().isCreated())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testViewLoanbyId() throws Exception {
		when(loanService.getLoanbyLoanNo(loan1.getLoanNo())).thenReturn(loan1);
		mockMvc.perform(get("/viewLoanbyId/{loanNo}", loan1.getLoanNo()).header("Authorization",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEiLCJyb2xlIjoiW0FkbWluXSIsImlhdCI6MTY3NDgwNTEwOSwiZXhwIjoxNjc0ODA2MDA5fQ.bITkb7Y0iBmA0-nQIiiBuw9pZyRDrWbZF-0b2MO18AQ")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper objmapper = new ObjectMapper();
			return objmapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@AfterEach
	public void releaseMocks() throws Exception {
		closeable.close();
	}
}

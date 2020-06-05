package com.tecsup.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;
	
	@Test
	public void testFindOwnerById() {
		
		long ID = 1;
		String first_name = "George";
		Owner owner = null;
		
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		logger.info(""+owner);
		assertEquals(first_name, owner.getFirst_name());
	}
	
	
	@Test
	public void testCreatePet() {

		String FIRST_NAME = "PAUL";
		String LAST_NAME = "RICALDI";
		String ADDRESS = "1234 AV.AREQUIPA";
		String CITY = "LIMA";
		String TELEFONE = "123456789";

		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEFONE);
		owner = ownerService.create(owner);
		logger.info("" + owner);

		assertThat(owner.getId()).isNotNull();
		assertEquals(FIRST_NAME, owner.getFirst_name());
		assertEquals(LAST_NAME, owner.getLast_name());
		assertEquals(ADDRESS, owner.getAddress());
		assertEquals(CITY, owner.getCity());
		assertEquals(TELEFONE, owner.getTelephone());

	}
	@Test
	public void testUpdateOwner() {

		String FIRST_NAME = "Betty";
		String LAST_NAME = "Davis";
		String ADDRESS = "638 Cardinal Ave.";
		String CITY = "Sun Prairie";
		String TELEFONE = "6085551749";
		long create_id = -1;

		String UP_FIRST_NAME = "PAUL2";
		String UP_LAST_NAME = "RICALDI2";
		String UP_ADDRESS = "1234 AV.AREQUIPA2";
		String UP_CITY = "LIMA2";
		String UP_TELEFONE = "123456787";

		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEFONE);

		// Create record
		logger.info(">" + owner);
		Owner readOwner = ownerService.create(owner);
		logger.info(">>" + readOwner);

		create_id = readOwner.getId();

		// Prepare data for update
		readOwner.setFirst_name(UP_FIRST_NAME);
		readOwner.setLast_name(UP_LAST_NAME);
		readOwner.setAddress(UP_ADDRESS);
		readOwner.setCity(UP_CITY);
		readOwner.setTelephone(UP_TELEFONE);
		// Execute update
		Owner upgradeOwner = ownerService.update(readOwner);
		logger.info(">>>>" + upgradeOwner);

		assertThat(create_id).isNotNull();
		assertEquals(create_id, upgradeOwner.getId());
		assertEquals(UP_FIRST_NAME, owner.getFirst_name());
		assertEquals(UP_LAST_NAME, owner.getLast_name());
		assertEquals(UP_ADDRESS, owner.getAddress());
		assertEquals(UP_CITY, owner.getCity());
		assertEquals(UP_TELEFONE, owner.getTelephone());
	}
	
	@Test
	public void testDeletePet() {

		String FIRST_NAME = "Peter";
		String LAST_NAME = "McTavish";
		String ADDRESS = "2387 S. Fair Way";
		String CITY = "Madison";
		String TELEFONE = "6085552765";

		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,TELEFONE);
		owner = ownerService.create(owner);
		logger.info("" + owner);

		try {
			ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownerService.findById(owner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException e) {
			assertTrue(true);
		} 			

	}
}

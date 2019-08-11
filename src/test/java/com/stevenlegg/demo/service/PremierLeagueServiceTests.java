package com.stevenlegg.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PremierLeagueServiceTests {
	
	@Autowired
	PremierLeagueService service;

	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testGetFixtures()
	{
		service.getFixtures();
	}

}

package com.stevenlegg.demo.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Service
public class PremierLeagueService {
	
	private static Logger LOG = LoggerFactory.getLogger(PremierLeagueService.class);

	
	public void getFixtures()
	{
		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
				.url("https://sportdata.p.rapidapi.com/api/v1/free/soccer/matches/fixtures/premier-league")
				.get()
				.addHeader("x-rapidapi-host", "sportdata.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "9d23a9654dmshe24b2a0a8c2b246p1250c5jsne2c8e99dda36")
				.build();

			Response response = client.newCall(request).execute();
			
			LOG.info(response.body().string());
		} catch (IOException e) {
			LOG.error("Error getting fixtures");
		}
	}
	
}

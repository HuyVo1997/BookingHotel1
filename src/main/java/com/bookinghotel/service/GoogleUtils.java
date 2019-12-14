package com.bookinghotel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GoogleUtils {
	@Autowired
	com.bookinghotel.repository.userRepository userRepository;
	@Autowired
	private Environment env;

	public String getToken(final String code) throws ClientProtocolException, IOException {
		String link = env.getProperty("google.link.get.token");
		System.out.println(link);
		String response = Request.Post(link)
				.bodyForm(Form.form()
						.add("client_id", env.getProperty("google.app.id"))
						.add("client_secret", env.getProperty("google.app.secret"))
						.add("redirect_uri", env.getProperty("google.redirect.uri"))
						.add("code", code)
						.add("grant_type", "authorization_code").build())
				.execute().returnContent().asString();
		System.out.println("response : " + response);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response).get("access_token");

		return node.textValue();
	}

	public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
		String link = env.getProperty("google.link.get.user_info") + accessToken;
		System.out.println(link);
		String response = Request.Get(link).execute().returnContent().asString();
		System.out.println(response);
		ObjectMapper mapper = new ObjectMapper();
		GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
		System.out.println(googlePojo);
		return googlePojo;

	}

	public UserDetails buildUser(GooglePojo googlePojo) {
		String infor_user = "";
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<com.bookinghotel.model.User> users = userRepository.findUsersByEmail(googlePojo.getEmail());
		if (users == null || users.isEmpty()) {
			com.bookinghotel.model.User newUser = new com.bookinghotel.model.User();
			newUser.setEmail(googlePojo.getEmail());
			newUser.setFirstname(googlePojo.getEmail().substring(0, googlePojo.getEmail().indexOf("@")));
			newUser.setPassword(UUID.randomUUID().toString());
			userRepository.save(newUser);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			UserDetails userDetail = new User(newUser.getEmail(), newUser.getPassword(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked, authorities);
			return userDetail;
		} else {
			com.bookinghotel.model.User user = users.get(0);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			UserDetails userDetail = new User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, authorities);
			return userDetail;
		}
	}

}

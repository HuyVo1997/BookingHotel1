package com.bookinghotel.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.Parameter;
@Component
public class RestFB {
    @Autowired
    com.bookinghotel.repository.userRepository userRepository;
    @Autowired
    private Environment env;
    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = String.format(env.getProperty("facebook.link.get.token"), env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }
    public CustomUser getUserInfo(final String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, env.getProperty("facebook.app.secret"),
                Version.LATEST);
        CustomUser user = facebookClient.fetchObject("me", CustomUser.class, Parameter.with("fields",
                "id, name, email"));
        System.out.println("Name= "+ user.getFullName());
        System.out.println("Email= "+ user.getEmail());
        return user;
    }

    public UserDetails buildUser(CustomUser customUser) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<com.bookinghotel.model.User> users = userRepository.findUsersByEmail(customUser.getEmail());
        if (users == null || users.isEmpty()) {
            com.bookinghotel.model.User newUser = new com.bookinghotel.model.User();
            newUser.setEmail(customUser.getEmail());
            newUser.setFirstname(customUser.getFullName());
            userRepository.save(newUser);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        UserDetails userDetail = new User(customUser.getEmail(), "", enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        return userDetail;
        }else{
                com.bookinghotel.model.User userb = users.get(0);
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
                UserDetails userDetail = new User(userb.getEmail(), "",
                        enabled, accountNonExpired, credentialsNonExpired,
                        accountNonLocked, authorities);
                return userDetail;

        }
    }
}

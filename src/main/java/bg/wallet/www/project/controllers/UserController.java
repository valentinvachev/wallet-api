package bg.wallet.www.project.controllers;

import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;
import bg.wallet.www.project.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(HttpServletRequest request,
                                               @Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel) throws URISyntaxException {

        Map<String,String> bodyResponse = new HashMap<>();
        //TODO handle errors
        this.userService.save(userRegisterBindingModel);

        bodyResponse.put("created",userRegisterBindingModel.getEmail());

        return ResponseEntity.created(new URI(request.getServletPath())).body(bodyResponse);
    }

    @PostMapping("/token/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("SECRET");
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = this.userService.findByEmail(email);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();

                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            } catch (Exception ex) {
                response.setHeader("error", ex.getMessage());
                response.sendError(FORBIDDEN.value(), ex.getMessage());
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

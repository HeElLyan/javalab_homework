package com.he.jwt;

import com.he.jwt.token.JwtTokenCoderAuth0Based;
import com.he.server.dto.ListDto;
import com.he.server.dto.ProductDto;
import com.he.server.dto.TokenDto;
import com.he.server.dto.UserDto;
import com.he.server.protocol.RequestDispatcher;
import com.he.server.services.ProductService;
import com.he.server.services.SignInService;
import com.he.server.util.PermissionVerifier;
import com.he.jwt.token.DecodedJwtToken;
import com.he.jwt.token.JwtTokenCoder;

public class JwtRequestsDispatcher implements RequestDispatcher<JwtRequest, JwtResponse> {

    private ProductService productService;
    private SignInService signInService;

    public JwtRequestsDispatcher(ProductService productService, SignInService signInService) {
        this.productService = productService;
        this.signInService = signInService;
    }

    @Override
    public void doDispatch(JwtRequest request, JwtResponse response) {
        switch (request.getCommand()) {
            case "Sign in":
                signIn(request, response);
                break;
            case "Get all products":
                getAllProducts(request, response);
                break;
            case "Buy product":
                buyProduct(request, response);
                break;
            case "Add product":
                addProduct(request, response);
                break;
            case "Get user products":
                getUserProducts(request, response);
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + request.getCommand());
        }
    }

    public void signIn(JwtRequest request, JwtResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDto userDto = signInService.signIn(login, password);

        String sub = userDto.getId().toString();
        String role = userDto.getRole();
        JwtTokenCoder tokenCoder = new JwtTokenCoderAuth0Based("metal");
        String token = tokenCoder.encode(sub, role);
        response.setData(new TokenDto(token));
    }

    public void getAllProducts(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        ListDto<ProductDto> productDtoList = productService.getAllProducts();
        response.setData(productDtoList);
    }

    public void buyProduct(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        Integer userId = Integer.parseInt(token.getSubject());
        Integer productId = Integer.parseInt(request.getParameter("id"));
        if (!productService.buyProduct(userId, productId))
            throw new IllegalStateException("Haven't bought yet");
    }

    public void getUserProducts(JwtRequest request, JwtResponse response) {
        DecodedJwtToken token = request.getDecodedToken();
        PermissionVerifier.allowIfUserOrAdmin(token.getRole());
        Integer userId = Integer.parseInt(token.getSubject());
        response.setData(productService.getUserProducts(userId));
    }

    public void addProduct(JwtRequest request, JwtResponse response) {
        PermissionVerifier.allowIfAdmin(request.getDecodedToken().getRole());
        String name = request.getParameter("name");
        response.setData(productService.addProduct(name));
    }

}

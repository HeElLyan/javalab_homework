package com.he.servlets;

import com.he.context.ApplicationContext;
import com.he.presenters.Presenter;
import com.he.presenters.PresenterFactory;
import com.he.dto.ListDto;
import com.he.dto.ProductDto;
import com.he.services.ProductService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductService productService;
    private PresenterFactory presenterFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("appContext");
        this.productService = context.getComponent(ProductService.class, "productService");
        this.presenterFactory = context.getAttribute("presenterFactory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        String role = (String) req.getSession().getAttribute("role");
        String userLogin = (String) req.getSession().getAttribute("user_login");
        ListDto<ProductDto> allProducts = productService.getAllProducts();
        ListDto<ProductDto> userProducts = productService.getUserProducts(userId);
        Presenter presenter = presenterFactory.createPresenter("products");
        presenter.put("login", userLogin);
        presenter.put("role", role);
        presenter.put("id", userId);
        presenter.put("allProducts", allProducts.getList());
        presenter.put("userProducts", userProducts.getList());
        presenter.render(resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("form_type");
        switch (type) {
            case "add_product":
                String productName = req.getParameter("name");
                productService.addProduct(productName);
                break;
            case "buy_product":
                Integer userId = (Integer) req.getSession().getAttribute("user_id");
                Integer productId = Integer.parseInt(req.getParameter("id"));
                System.out.println(productId);
                productService.buyProduct(userId, productId);
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/products");
    }

}

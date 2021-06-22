package ru.job4j.cars.servlet;

import com.google.gson.JsonObject;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject json = new JsonObject();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = HbmStore.instOf().findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
            json.addProperty("result", true);
        } else {
            json.addProperty("msg", "Неверный email или пароль");
            json.addProperty("result", false);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}

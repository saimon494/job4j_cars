package ru.job4j.cars.servlet;

import com.google.gson.JsonObject;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject json = new JsonObject();
        Optional.ofNullable((User) req.getSession().getAttribute("user")).ifPresent(
                user ->  {
                    json.addProperty("userName", user.getName());
                    json.addProperty("userId", user.getId());
                }
        );
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}

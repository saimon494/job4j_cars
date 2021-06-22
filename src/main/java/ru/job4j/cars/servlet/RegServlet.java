package ru.job4j.cars.servlet;

import com.google.gson.JsonObject;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject json = new JsonObject();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        var store = HbmStore.instOf();
        if (store.findUserByEmail(email) != null) {
            json.addProperty("result", false);
            json.addProperty("msg", "Email уже занят");
        } else {
            store.save(new User(name, email, password));
            json.addProperty("result", true);
        }
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(resp.getOutputStream(), StandardCharsets.UTF_8));
        writer.println(json);
        writer.flush();
    }
}

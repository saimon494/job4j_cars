package ru.job4j.cars.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetBodyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("windows-1251");
        int id = Integer.parseInt(req.getParameter("id"));
        var model = HbmStore.instOf().findById(Model.class, id);
        var bodies = new ArrayList<>(model.getBodies());
        bodies.sort(Comparator.comparing(Body::getName));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bodies);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(json);
        writer.flush();
    }
}

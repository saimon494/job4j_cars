package ru.job4j.cars.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.model.*;
import ru.job4j.cars.store.HbmStore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AddPostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var store = HbmStore.instOf();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        var post = new Post();
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String key = item.getFieldName();
                    int value = Integer.parseInt(item.getString());
                    switch (key) {
                        case "brand":
                            var brand = new Brand();
                            brand.setId(value);
                            brand.setName(store.findById(Brand.class, value).getName());
                            post.setBrand(brand);
                            break;
                        case "model":
                            var model = new Model();
                            model.setId(value);
                            model.setName(store.findById(Model.class, value).getName());
                            post.setModel(model);
                            break;
                        case "body":
                            var body = new Body();
                            body.setId(value);
                            body.setName(store.findById(Body.class, value).getName());
                            post.setBody(body);
                            break;
                        case "mileage":
                            post.setMileage(value);
                            break;
                        case "color":
                            var color = new Color();
                            color.setId(value);
                            color.setName(store.findById(Color.class, value).getName());
                            post.setColor(color);
                            break;
                        default:
                            break;
                    }
                } else {
                    var photo = new Photo();
                    photo.setName(item.getName());
                    store.save(photo);
                    post.setPhoto(photo);
                    File folderId = new File("images" + File.separator + photo.getId());
                    if (folderId.exists()) {
                        for (File f : folderId.listFiles()) {
                            f.delete();
                        }
                    } else {
                        folderId.mkdir();
                    }
                    try (FileOutputStream out = new FileOutputStream(
                            folderId + File.separator + item.getName())) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        var user = (User) req.getSession().getAttribute("user");
        user.setName(store.findById(User.class, user.getId()).getName());
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setStatus(true);
        store.save(post);
    }
}

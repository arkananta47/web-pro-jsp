package com.example.servlet;

import com.example.dao.MahasiswaDAO;
import com.example.model.Mahasiswa;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet({
        "/mhs-list",
        "/mhs-new",
        "/mhs-insert",
        "/mhs-edit",
        "/mhs-update",
        "/mhs-delete",
        "/mhs-export",
        "/mhs-import"
})
@MultipartConfig
public class MahasiswaServlet extends HttpServlet {

    private MahasiswaDAO dao;

    @Override
    public void init() {
        dao = new MahasiswaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {

            /* =======================================================
             * LIST + SEARCH + SORTING + PAGINATION
             * ======================================================= */
            case "/mhs-list": {

                String keyword = request.getParameter("search");
                String sort = request.getParameter("sort");
                String order = request.getParameter("order");
                String pageStr = request.getParameter("page");

                if (sort == null) sort = "nama";
                if (order == null) order = "asc";

                int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
                int limit = 5;
                int start = (page - 1) * limit;

                List<Mahasiswa> data;

                if (keyword != null && !keyword.trim().isEmpty()) {
                    data = dao.search(keyword);
                    request.setAttribute("search", keyword);

                    if (data.isEmpty()) {
                        request.setAttribute("notfound",
                                "Data tidak ditemukan untuk kata: \"" + keyword + "\"");
                    }
                } else {
                    data = dao.getPagedList(start, limit, sort, order);
                }

                int totalData = dao.countMahasiswa();
                int totalPage = (int) Math.ceil((double) totalData / limit);

                request.setAttribute("data", data);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("sort", sort);
                request.setAttribute("order", order);

                request.getRequestDispatcher("mahasiswa-list.jsp").forward(request, response);
                break;
            }

            /* NEW */
            case "/mhs-new":
                request.getRequestDispatcher("mahasiswa-form.jsp").forward(request, response);
                break;

            /* EDIT */
            case "/mhs-edit":
                String nim = request.getParameter("nim");
                request.setAttribute("mhs", dao.getByNim(nim));
                request.getRequestDispatcher("mahasiswa-edit.jsp").forward(request, response);
                break;

            /* DELETE */
            case "/mhs-delete":
                dao.delete(request.getParameter("nim"));
                response.sendRedirect("mhs-list");
                break;

            /* EXPORT CSV */
            case "/mhs-export":
                List<Mahasiswa> all = dao.getAll();

                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=mahasiswa.csv");

                String header = "nim,nama,prodi,semester,email,nohp\n";
                response.getWriter().write(header);

                for (Mahasiswa s : all) {
                    response.getWriter().write(
                        s.getNim() + "," +
                        s.getNama() + "," +
                        s.getProdi() + "," +
                        s.getSemester() + "," +
                        s.getEmail() + "," +
                        s.getNohp() + "\n"
                    );
                }
                break;

            /* IMPORT CSV */
            case "/mhs-import":
                request.getRequestDispatcher("mhs-import.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {

            /* INSERT */
            case "/mhs-insert":
                Mahasiswa m = new Mahasiswa(
                        request.getParameter("nim"),
                        request.getParameter("nama"),
                        request.getParameter("prodi"),
                        Integer.parseInt(request.getParameter("semester")),
                        request.getParameter("email"),
                        request.getParameter("nohp")
                );
                dao.insert(m);
                response.sendRedirect("mhs-list");
                break;

            /* UPDATE */
            case "/mhs-update":
                Mahasiswa u = new Mahasiswa(
                        request.getParameter("nim"),
                        request.getParameter("nama"),
                        request.getParameter("prodi"),
                        Integer.parseInt(request.getParameter("semester")),
                        request.getParameter("email"),
                        request.getParameter("nohp")
                );
                dao.update(u);
                response.sendRedirect("mhs-list");
                break;

            /* IMPORT CSV ACTION */
            case "/mhs-import":

                Part filePart = request.getPart("file");

                if (filePart != null) {
                    List<Mahasiswa> list = new ArrayList<>();

                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));
                        String line;

                        reader.readLine(); // skip header

                        while ((line = reader.readLine()) != null) {
                            String[] data = line.split(",");

                            if (data.length == 6) {
                                Mahasiswa mhs = new Mahasiswa(
                                    data[0],
                                    data[1],
                                    data[2],
                                    Integer.parseInt(data[3]),
                                    data[4],
                                    data[5]
                                );
                                list.add(mhs);
                            }
                        }

                        dao.insertBatch(list);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                response.sendRedirect("mhs-list");
                break;
        }
    }
}

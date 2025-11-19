<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.model.Mahasiswa" %>

<%
    Mahasiswa m = (Mahasiswa) request.getAttribute("mhs");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Mahasiswa</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="p-10 bg-gray-100">

<h1 class="text-3xl font-bold mb-6">Edit Mahasiswa</h1>

<form action="mhs-update" method="post" class="bg-white p-6 shadow max-w-lg">

    <label>NIM</label>
    <input name="nim" value="<%= m.getNim() %>" class="w-full border p-2 mb-3 bg-gray-200" readonly>

    <label>Nama</label>
    <input name="nama" value="<%= m.getNama() %>" class="w-full border p-2 mb-3">

    <label>Prodi</label>
    <input name="prodi" value="<%= m.getProdi() %>" class="w-full border p-2 mb-3">

    <label>Semester</label>
    <input type="number" name="semester" value="<%= m.getSemester() %>" class="w-full border p-2 mb-3">

    <label>Email</label>
    <input type="email" name="email" value="<%= m.getEmail() %>" class="w-full border p-2 mb-3">

    <label>No HP</label>
    <input name="nohp" value="<%= m.getNohp() %>" class="w-full border p-2 mb-3">

    <button class="bg-indigo-600 text-white px-4 py-2 rounded">Update</button>

</form>

</body>
</html>

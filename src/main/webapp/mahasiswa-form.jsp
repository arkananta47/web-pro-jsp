<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tambah Mahasiswa</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="p-10 bg-gray-100">

<h1 class="text-3xl font-bold mb-6">Tambah Mahasiswa</h1>

<form action="mhs-insert" method="post" class="bg-white p-6 shadow max-w-lg">

    <label>NIM</label>
    <input name="nim" class="w-full border p-2 mb-3" required>

    <label>Nama</label>
    <input name="nama" class="w-full border p-2 mb-3" required>

    <label>Prodi</label>
    <input name="prodi" class="w-full border p-2 mb-3" required>

    <label>Semester</label>
    <input type="number" name="semester" class="w-full border p-2 mb-3" required>

    <label>Email</label>
    <input type="email" name="email" class="w-full border p-2 mb-3" required>

    <label>No HP</label>
    <input name="nohp" class="w-full border p-2 mb-3" required>

    <button class="bg-indigo-600 text-white px-4 py-2 rounded">Simpan</button>

</form>

</body>
</html>

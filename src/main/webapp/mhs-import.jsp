<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Import Data Mahasiswa (CSV)</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="p-10 bg-gray-100">

<!-- TITLE -->
<h1 class="text-3xl font-bold mb-6">Import Data Mahasiswa (CSV)</h1>

<!-- BACK BUTTON -->
<a href="mhs-list"
   class="inline-block mb-6 bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700">
    ← Kembali ke Daftar
</a>

<!-- UPLOAD FORM -->
<form action="mhs-import" method="post" enctype="multipart/form-data"
      class="bg-white p-6 rounded shadow max-w-lg">

    <label class="block font-semibold mb-2">Pilih File CSV</label>

    <input type="file" name="file" accept=".csv"
           class="border p-3 w-full rounded mb-4 bg-gray-50 cursor-pointer" required>

    <p class="text-gray-600 text-sm mb-4">
        Format harus sama seperti hasil Export CSV:<br>
        <span class="font-mono text-xs">
            nim,nama,prodi,semester,email,nohp
        </span>
    </p>

    <button class="bg-indigo-600 text-white px-6 py-2 rounded hover:bg-indigo-700 transition">
        Import Sekarang
    </button>
</form>

</body>
</html>

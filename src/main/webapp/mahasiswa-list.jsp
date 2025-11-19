<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Data Mahasiswa</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="p-10 bg-gray-100">

<!-- ===================== HEADER ====================== -->
<div class="flex justify-between items-center mb-6">
    <h1 class="text-3xl font-bold">Data Mahasiswa</h1>

    <div class="flex gap-3">
        <a href="dashboard"
           class="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700 transition">
            Dashboard
        </a>

        <a href="mhs-new"
           class="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700 transition">
            Tambah Mahasiswa
        </a>
    </div>
</div>


<!-- ===================== SEARCH + EXPORT + IMPORT ====================== -->
<div class="flex items-center gap-3 mb-6">

    <!-- SEARCH -->
    <form action="mhs-list" method="get" class="flex gap-2">
        <input type="text"
               name="search"
               value="${search}"
               placeholder="Cari nama / NIM..."
               class="px-4 py-2 border rounded-lg shadow w-72 focus:ring-indigo-500">

        <button class="px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700">
            Cari
        </button>
    </form>

    <!-- EXPORT CSV -->
    <a href="mhs-export"
       class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition">
        Export CSV
    </a>

    <!-- IMPORT CSV -->
    <a href="mhs-import"
       class="px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition">
        Import CSV
    </a>
</div>


<!-- ===================== NOT FOUND ALERT ====================== -->
<c:if test="${notfound != null}">
    <div class="mb-6 p-4 bg-red-100 text-red-700 border border-red-300 rounded">
        ${notfound}
    </div>
</c:if>


<!-- ===================== TABLE ====================== -->
<div class="bg-white shadow rounded border overflow-hidden">

    <table class="w-full">
        <thead>
            <tr class="bg-indigo-600 text-white text-left">

                <th class="p-3">No</th>

                <!-- SORT TOGGLE NIM -->
                <th class="p-3">
                    <a href="mhs-list?sort=nim&order=${order == 'asc' ? 'desc' : 'asc'}"
                       class="hover:underline">
                        NIM ⬍
                    </a>
                </th>

                <!-- SORT TOGGLE NAMA -->
                <th class="p-3">
                    <a href="mhs-list?sort=nama&order=${order == 'asc' ? 'desc' : 'asc'}"
                       class="hover:underline">
                        Nama ⬍
                    </a>
                </th>

                <th class="p-3">Prodi</th>
                <th class="p-3">Semester</th>
                <th class="p-3">Email</th>
                <th class="p-3">No HP</th>
                <th class="p-3 text-center">Aksi</th>
            </tr>
        </thead>

        <tbody>

        <!-- JIKA KOSONG -->
        <c:if test="${empty data}">
            <tr>
                <td colspan="8" class="text-center p-5 text-gray-500">
                    Tidak ada data untuk ditampilkan.
                </td>
            </tr>
        </c:if>

        <!-- LOOP DATA -->
        <c:forEach var="m" items="${data}" varStatus="i">
            <tr class="border-b hover:bg-gray-50 transition">

                <td class="p-2 font-semibold">
                    ${ (currentPage - 1) * 5 + i.index + 1 }
                </td>

                <td class="p-2">${m.nim}</td>
                <td class="p-2">${m.nama}</td>

                <td class="p-2">
                    <span class="px-3 py-1 bg-indigo-100 text-indigo-700 text-sm rounded-full">
                        ${m.prodi}
                    </span>
                </td>

                <td class="p-2">
                    <span class="px-3 py-1 bg-green-100 text-green-700 text-sm rounded-full">
                        Semester ${m.semester}
                    </span>
                </td>

                <td class="p-2">${m.email}</td>
                <td class="p-2">${m.nohp}</td>

                <td class="p-2 text-center space-x-2">

                    <a href="mhs-edit?nim=${m.nim}"
                       class="text-blue-600 font-semibold hover:text-blue-800">
                        Edit
                    </a>

                    <a href="mhs-delete?nim=${m.nim}"
                       onclick="return confirm('Yakin hapus data ini?')"
                       class="text-red-600 font-semibold hover:text-red-800">
                        Hapus
                    </a>

                </td>

            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>


<!-- ===================== PAGINATION ====================== -->
<div class="flex justify-between items-center mt-6">

    <!-- PREV BUTTON -->
    <c:if test="${currentPage > 1}">
        <a href="mhs-list?page=${currentPage - 1}&sort=${sort}&order=${order}"
           class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">
            « Prev
        </a>
    </c:if>

    <!-- PAGE NUMBERS -->
    <div class="flex gap-2">
        <c:forEach var="i" begin="1" end="${totalPage}">
            <a href="mhs-list?page=${i}&sort=${sort}&order=${order}"
               class="px-3 py-1 rounded
               ${i == currentPage ? 'bg-indigo-600 text-white' : 'bg-white border'}">
                ${i}
            </a>
        </c:forEach>
    </div>

    <!-- NEXT BUTTON -->
    <c:if test="${currentPage < totalPage}">
        <a href="mhs-list?page=${currentPage + 1}&sort=${sort}&order=${order}"
           class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400">
            Next »
        </a>
    </c:if>

</div>

</body>
</html>

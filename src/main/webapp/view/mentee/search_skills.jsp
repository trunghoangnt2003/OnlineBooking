<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 22-May-24
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Skills</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_skill/search_skills.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_skill/side_select.css">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <style>
        .page-header {
            background: linear-gradient(rgba(43, 57, 64, .5), rgba(43, 57, 64, .5)), url('${pageContext.request.contextPath}/img/carousel-1.jpg') center center no-repeat;
            background-size: cover;
        }
    </style>
</head>
<body >
<jsp:include page="../common/header.jsp"></jsp:include>
<%--<div class="banner">
    <img src="${pageContext.request.contextPath}/img/Banner.png" alt="Logo"/>
</div>--%>
<div>
    <div class="container-xxl py-5 bg-dark page-header mb-1">
        <div class="container my-5 pt-5 pb-4">
            <h1 class="display-3 text-white mb-3 fw-bold">Skills List</h1>
        </div>
    </div>
  <%--  <div class="search-container">
        <div class="search-box">
            <label>
                <input type="text" class="search-input" id="search" placeholder="Search skills..." value="${requestScope.name}"/>
            </label>
            <button class="search-btn" onclick="searchHandler()">
                <i class="fas fa-search fa-sm"></i>
            </button>
        </div>
    </div>--%>

    <div class="body-container">
        <div class="skill">
            <div class="side">

              <%--  <div class="level-container">
                    <h4><span class="title_level text-white">Level</span></h4>
                    <hr>
                   &lt;%&ndash; <ul class="list-level" id="listLevel">
                        <c:forEach items="${requestScope.list_level}" var="level">
                            <li class="level">
                                <label>
                                    <input type="checkbox" class="checkbox" name="level" value="${level.name}"
                                        <c:forEach items="${requestScope.levels_select}" var="selectedLevel">
                                            <c:if test="${selectedLevel.equals(level.name)}">
                                                checked
                                            </c:if>
                                        </c:forEach>
                                    >
                                </label>
                                <span class="item-text">&nbsp;${level.name}</span>
                            </li>
                        </c:forEach>
                    </ul>&ndash;%&gt;
                </div>--%>
                <div>
                    <div class="select-content">
                        <div class="select-btn">
                            <h4><span class="title_level text-white">Level</span></h4>
                        </div>
                        <ul class="list-level" id="listLevel">
                            <c:forEach items="${requestScope.list_level}" var="level">
                                <li class="level">
                                    <label>
                                        <input type="checkbox" class="checkbox" name="level" value="${level.name}"
                                        <c:forEach items="${requestScope.levels_select}" var="selectedLevel">
                                        <c:if test="${selectedLevel.equals(level.name)}">
                                               checked
                                        </c:if>
                                        </c:forEach>
                                        >
                                    </label>
                                    <span class="item-text">&nbsp;${level.name}</span>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <c:forEach items="${requestScope.list_cate}" var="cate">
                        <div class="select-content">
                            <div class="select-btn">
                                <span class="btn-text text-white">${cate.name}</span>
<%--                                <span class="arrow-dwn" style="background-color: #07ad90">
                                        <i class="fa-solid fa-chevron-down"></i>
                                    </span>--%>
                            </div>
                            <ul class="list-items">
                                <c:forEach items="${requestScope.list_skill}" var="skill">
                                    <c:if test="${skill.category.id == cate.id}">
                                        <li class="item" onclick="selectHandler('${skill.name}')">
                                            <img src="${pageContext.request.contextPath}/${skill.src_icon}">
                                            <span class="item-text">${skill.name}</span>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="info"  >

            <div class="search-container p-2" style="background-color:rgb(43, 57, 64)">
                <div class="search-box d-flex">
                    <input type="text" class="form-control border-0" id="search" placeholder="Search skills..." value="${requestScope.name}"/>
                    <button class="btn btn-success border-0 w-25 " onclick="searchHandler()" >Search</button>
                </div>
            </div>
            <c:if test="${requestScope.list_level_skill.isEmpty()}">
                <h4 style="display: flex; justify-content: center; color: red; margin-top: 50px">
                    Not Found
                </h4>
            </c:if>
            <c:if test="${!requestScope.list_level_skill.isEmpty()}">
            <div >
                <hr>
                <div class="">
                    <c:forEach var="level_skill" items="${requestScope.list_level_skill}">
                        <div>
                            <form action="SearchMentor" method="get">
                                <input name="skill" type="hidden" value="${level_skill.skill.name}"/>
                                <input name="level" type="hidden" value="${level_skill.level.name}">
                                <div class="response-content">
                                         <div >
                                             <img src="${pageContext.request.contextPath}/${level_skill.skill.src_icon}" style="width: 50px; height: 50px">
                                         </div>
                                        <div style="width: 73%">
                                            <div class="skill-title">
                                                <span style="color: rgb(43, 57, 64)" > ${level_skill.skill.name} for ${level_skill.level.name}</span>
                                            </div>
                                            <div >
                                                <span style="opacity: 80%">${level_skill.description} </span>
                                            </div>
                                        </div>
                                        <div style="width: 17%">
                                                <button type="submit" class="btn btn-success">Find Mentor</button>
                                        </div>
                                </div>
                            </form>
                        </div>
                        <hr>
                    </c:forEach>
                </div>
            </div>
            </c:if>
        </div>
    </div>
</div>

<!-- SweetAlert2 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const list = document.getElementById('listLevel');

        list.addEventListener('click', (e) => {
            const target = e.target;

            // Prevent toggling if the checkbox itself is clicked
            if (target.tagName.toLowerCase() === 'input' && target.type === 'checkbox') {
                return;
            }

            const listItem = target.closest('li.level');
            if (listItem && list.contains(listItem)) {
                const checkbox = listItem.querySelector('input[type="checkbox"]');
                if (checkbox) {
                    checkbox.checked = !checkbox.checked;
                }
            }
        });

/*        // Get all select buttons and items
        const selectBtns = document.querySelectorAll(".select-btn");

        // Add event listeners to all select buttons
        selectBtns.forEach(selectBtn => {
            selectBtn.addEventListener("click", (event) => {
                selectBtn.classList.toggle("open");
                event.stopPropagation(); // Prevent click event from propagating to parent elements
            });
        });

        document.addEventListener("click", () => {
            selectBtns.forEach(selectBtn => {
                selectBtn.classList.remove("open");
            });
        });*/
    });

    function selectHandler(name) {
        if (name) {
            console.log(name);

            let levels = "";
            const checkboxes = document.querySelectorAll('#listLevel .checkbox:checked');
            checkboxes.forEach(checkbox => {
                if (checkbox.checked) {
                    levels +=  "&level="+checkbox.value;
                }
            });
            console.log(levels);
            let encodedSkill = encodeURIComponent(name)
            window.location.href = `${pageContext.request.contextPath}/Search_Skills?skill=` + encodedSkill+ levels;
        }
    }

    function searchHandler() {
        const search_value =  document.getElementById("search").value;
        let levels = "";
        const checkboxes = document.querySelectorAll('#listLevel .checkbox:checked');
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                levels +=  "&level="+checkbox.value;
            }
        });
        let encodedSkill = encodeURIComponent(search_value)
        window.location.href = `${pageContext.request.contextPath}/Search_Skills?skill=` + encodedSkill+ levels;
    }
</script>

<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</body>
</html>

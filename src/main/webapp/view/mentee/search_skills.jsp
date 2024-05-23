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
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container main">
    <div class="search-container">
        <div class="search-box">
            <input type="text" class="search-input" placeholder="Search skills..." />
            <button class="search-btn">
                <i class="fas fa-search fa-sm"></i>
            </button>
        </div>
    </div>

    <div class="body-container">
        <div class="skill">
            <div class="side">
                <div class="level-container">
                    <h4><span class="title_level">Level</span></h4>
                    <hr>
                    <ul class="list-level" id="listLevel">
                        <c:forEach items="${requestScope.list_level}" var="level">
                            <li class="level">
                                <label>

                                    <input type="checkbox" class="checkbox" name="level" value="${level.name}"
                                    <c:forEach items="${requestScope.levels_select}" var="selectedLevel">
                                        <c:if test="${selectedLevel.equals(level.name)}">
                                            <c:set var="isChecked" value="true" />
                                        </c:if>
                                    </c:forEach>
                                    <input type="checkbox" class="checkbox" name="level" value="${level.name}"
                                           <c:if test="${isChecked}">checked</c:if>
                                    >
                                </label>
                                <span class="item-text">&nbsp;${level.name}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div>
                    <c:forEach items="${requestScope.list_cate}" var="cate">
                        <div class="select-content">
                            <div class="select-btn">
                                <span class="btn-text">${cate.name}</span>
                                <span class="arrow-dwn" style="background-color: #07ad90">
                                        <i class="fa-solid fa-chevron-down"></i>
                                    </span>
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
        <div class="info">
            <div class="response-text">

                Response for:
                <c:forEach var="level_skill" items="${requestScope.list_level_skill}" varStatus="status">
                    ${level_skill.skill.name} for ${level_skill.level.name}
                    <c:if test="${!status.last}">, </c:if>
                </c:forEach>

            </div>
            <hr>
            <div class="">
                <c:forEach var="level_skill" items="${requestScope.list_level_skill}">
                    <div>
                        <h4>${level_skill.skill.name} for ${level_skill.level.name}</h4>
                    </div>
                </c:forEach>
            </div>
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

        // Get all select buttons and items
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
        });
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
</script>
</body>
</html>

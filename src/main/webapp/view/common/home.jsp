<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 6/2/2024
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/home.css">
    <style>
        .i {
            background: #0CA4F6;
            display: flex;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            align-content: center;
            justify-content: center;
            margin-left: 210px;
        }

        .footer {
            margin-top: 50px;
        }
        .banner {
            position: relative;
            width: 100%;
            height: 850px;
            margin: 10px 30px 20px 0;
            justify-content: center;
            box-shadow: 4px 6px 8px rgba(0, 0, 0, 0.6);
        }
        .banner img {
            position: absolute;
            width: 100%;
            height: 100%;
            object-fit: cover;
            opacity: 0;
            transition: opacity 1s ease-in-out;
        }

        /* Style the tab container */
        .tab-container {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        /* Individual tab button styling */
        .tab {
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
            color: #333;
            border: none;
            background-color: #f1f1f1;
            border-bottom: 3px solid transparent;
            transition: background-color 0.3s, border-bottom-color 0.3s;
        }

        /* Active tab styling */
        .tab.active {
            background-color: #fff;
            border-bottom-color: #3498db; /* Color for the active tab border */
            font-weight: bold;
        }

        /* Style for the content section */
        .tab-content {
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-top: none;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .mentor-list {
            list-style: none;
        }

        .mentor-item {
            background: whitesmoke;
            border-radius: 20px;
            padding: 10px;
            margin: 10px;
            color: #00B074;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-book-now {
            margin-right: 3em;
        }

        .price {
            font-weight: bolder;
            font-size: 20px;
        }

    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

<section class="banner">
    <img src="${pageContext.request.contextPath}/img/home/banner.jpg" class="active">
    <img src="${pageContext.request.contextPath}/img/home/banner2.jpg">
    <img src="${pageContext.request.contextPath}/img/home/banner3.jpg">
</section>

<div>
    <h1 class="top-content">Recommend for you</h1>
    <div class="top-content">
        <div class="tab active" onclick="showContent('mentor')">Top Mentor</div>
        <div class="tab" onclick="showContent('subject')">Top Subject</div>
    </div>



    <!-- Tab Content -->
    <div class="tab-content">
        <div id="mentor" class="active">
            <ul class="mentor-list">
                <c:forEach items="${requestScope.mentor}" var="m">
                    <li class="mentor-item" style="margin-bottom: 20px;">
                        <div style="display: flex; align-items: center;">
                            <div style="margin-right: 15px;">
                                <img src="${pageContext.request.contextPath}/${m.account.avatar}" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            </div>

                            <div style="flex-grow: 1;">
                                <h5 style="margin: 0; font-weight: bolder">${m.account.name}</h5>
                                <div class="rate-follower">
                                    <div class="stars-outer">
                                        <div class="stars-inner" data-rating="${m.rating}"></div>
                                    </div>
                                </div>
                                <div style="margin-top: 5px;">
                                    <i class="fa-solid fa-user-graduate"></i> ${m.totalBookings} Bookings
                                </div>
                            </div>

                            <div class="btn-book-now">
                                <div class="price">
                                        ${m.price} đ/hour
                                </div>
                                <button onclick="changeToMentorProfile('${m.account.id}')" type="button" class="btn btn-success" data-mdb-ripple-init>Book Now</button>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div id="subject" style="display: none">
            <ul class="mentor-list">
                <li class="mentor-item" style="margin-bottom: 20px;">
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <div style="margin-right: 15px;display: flex; margin-left: 10px">
                            <img src="${pageContext.request.contextPath}/img/image_skill/c.png" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; justify-content: center">
                                <p style="font-size: 20px; font-weight: bold">C basic language</p>
                                <p>C là một ngôn ngữ lập trình bậc trung được phát triển vào năm 1972 bởi Dennis Ritchie tại Bell Labs.
                                    Ngôn ngữ này nổi bật với cú pháp đơn giản và khả năng kiểm soát phần cứng mạnh mẽ,
                                    cho phép lập trình viên tương tác trực tiếp với hệ thống. C thường được sử dụng để phát triển hệ điều hành,
                                    trình biên dịch, và phần mềm nhúng, nhờ vào hiệu suất cao và tính linh hoạt của nó.
                                    Điều này giúp C trở thành nền tảng cho nhiều ngôn ngữ lập trình hiện đại khác</p>
                            </div>
                        </div>

                        <div class="btn-book-now">
                            <button onclick="changeToSearchSkill('C')" type="button" class="btn btn-success" data-mdb-ripple-init>Learn Now</button>
                        </div>
                    </div>
                </li>
                <li class="mentor-item" style="margin-bottom: 20px;">
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <div style="margin-right: 15px; display: flex; margin-left: 10px">
                            <img src="${pageContext.request.contextPath}/img/image_skill/csharp.png" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; justify-content: center">
                                <p style="font-size: 20px; font-weight: bold">C# language</p>
                                <p>C# (C Sharp) là một ngôn ngữ lập trình bậc cao được phát triển bởi Microsoft vào năm 2000, nằm trong khuôn khổ
                                    của .NET Framework. C# là ngôn ngữ lập trình hướng đối tượng, có cú pháp rõ ràng và mạnh mẽ, hỗ trợ lập trình hàm và
                                    lập trình bất đồng bộ. Với C#, lập trình viên có thể phát triển các ứng dụng trên nhiều nền tảng,
                                    bao gồm ứng dụng desktop, web, di động, và game. Ngôn ngữ này cung cấp các tính năng như quản lý bộ nhớ tự động thông qua
                                    Garbage Collection, an toàn kiểu và tính năng LINQ (Language Integrated Query) cho phép truy vấn dữ liệu một cách dễ dàng.
                                    C# đã trở thành một trong những ngôn ngữ lập trình phổ biến nhất, được sử dụng rộng rãi trong phát triển phần mềm và hệ thống
                                    doanh nghiệp, đặc biệt là trong các ứng dụng chạy trên nền tảng Microsoft.</p>
                            </div>
                        </div>

                        <div class="btn-book-now">
                            <button onclick="changeToSearchSkill('C#')" type="button" class="btn btn-success" data-mdb-ripple-init>Learn Now</button>
                        </div>
                    </div>
                </li>
                <li class="mentor-item" style="margin-bottom: 20px;">
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <div style="margin-right: 15px; display: flex; margin-left: 10px">
                            <img src="${pageContext.request.contextPath}/img/image_skill/java.png" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; justify-content: center">
                                <p style="font-size: 20px; font-weight: bold">Java core</p>
                                <p>Java, được phát triển bởi James Gosling tại Sun Microsystems vào năm 1995, là một ngôn ngữ lập trình bậc cao với mô hình lập
                                    trình hướng đối tượng. Với mục tiêu "Viết một lần, chạy mọi nơi", Java cho phép các ứng dụng chạy trên bất kỳ nền tảng nào hỗ
                                    trợ Java Virtual Machine (JVM). Ngôn ngữ này nổi bật với tính bảo mật, tính ổn định và khả năng quản lý bộ nhớ tự động thông
                                    qua Garbage Collection. Java đã trở thành lựa chọn hàng đầu cho phát triển ứng dụng web, ứng dụng doanh nghiệp, và đặc biệt là
                                    ứng dụng di động Android.</p>
                            </div>
                        </div>

                        <div class="btn-book-now">
                            <button onclick="changeToSearchSkill('Java')" type="button" class="btn btn-success" data-mdb-ripple-init>Learn Now</button>
                        </div>
                    </div>
                </li>
                <li class="mentor-item" style="margin-bottom: 20px;">
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <div style="margin-right: 15px; display: flex; margin-left: 10px">
                            <img src="${pageContext.request.contextPath}/img/image_skill/kotlin.png" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; justify-content: center">
                                <p style="font-size: 20px; font-weight: bold">Kotlin</p>
                                <p>Kotlin là một ngôn ngữ lập trình hiện đại được phát triển bởi JetBrains vào năm 2011, được thiết kế để tương
                                    thích hoàn toàn với Java nhưng có cú pháp ngắn gọn và dễ đọc hơn. Ngôn ngữ này hỗ trợ cả lập trình hướng đối tượng và lập trình hàm,
                                    giúp lập trình viên xây dựng ứng dụng một cách nhanh chóng và an toàn hơn, đặc biệt là trong việc loại bỏ lỗi null pointer.
                                    Kotlin đã nhanh chóng trở thành ngôn ngữ chính thức cho phát triển ứng dụng Android từ năm 2017,
                                    nhờ vào sự hỗ trợ mạnh mẽ từ Google và cộng đồng lập trình viên.</p>
                            </div>
                        </div>

                        <div class="btn-book-now">
                            <button onclick="changeToSearchSkill('Kotlin')" type="button" class="btn btn-success" data-mdb-ripple-init>Learn Now</button>
                        </div>
                    </div>
                </li>
                <li class="mentor-item" style="margin-bottom: 20px;">
                    <div style="display: flex; align-items: center; justify-content: space-between">
                        <div style="margin-right: 15px; display: flex; margin-left: 10px">
                            <img src="${pageContext.request.contextPath}/img/image_skill/postgresql.png" alt="image" style="width: 150px; height: 150px; border-radius: 10px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; justify-content: center">
                                <p style="font-size: 20px; font-weight: bold">PostgreSQL</p>
                                <p>PostgreSQL là một hệ quản trị cơ sở dữ liệu quan hệ mã nguồn mở, ra đời vào năm 1996 và được phát triển bởi
                                    nhóm PostgreSQL Global Development Group. Hệ quản trị này nổi bật với khả năng hỗ trợ nhiều loại dữ liệu,
                                    bao gồm dữ liệu quan hệ và đối tượng, cùng với tính năng mạnh mẽ như ACID, bảo mật, và khả năng mở rộng.
                                    PostgreSQL cung cấp các công cụ để quản lý giao dịch và đồng bộ hóa, khiến nó trở thành lựa chọn lý tưởng cho các ứng dụng web,
                                    hệ thống phân tích dữ liệu lớn và các ứng dụng doanh nghiệp yêu cầu hiệu suất cao.</p>
                            </div>
                        </div>

                        <div class="btn-book-now">
                            <button onclick="changeToSearchSkill('PostgreSQL')" type="button" class="btn btn-success" data-mdb-ripple-init>Learn Now</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s">
    <div class="container">
        <h1 style="color: #00B074; font-weight: bolder" class="text-center mb-5">Our Clients Say!!!</h1>
        <div class="owl-carousel testimonial-carousel">
            <div class="testimonial-item bg-light rounded p-4">
                <i class="fa fa-quote-left fa-2x text-primary mb-3"></i>
                <p>Trang đặt khóa học rất dễ sử dụng. Giao diện trực quan và rõ ràng giúp mình tìm và
                    đăng ký khóa học yêu thích chỉ trong vài phút. Rất hài lòng với sự tiện lợi này!</p>
                <div class="d-flex align-items-center">
                    <img class="img-fluid flex-shrink-0 rounded" src="img/image_user/student1.jpg"
                         style="width: 100px; height: 100px;">
                    <div class="ps-3">
                        <h5 class="mb-1">Client Name</h5>
                        <small>Profession</small>
                    </div>
                </div>
            </div>
            <div class="testimonial-item bg-light rounded p-4">
                <i class="fa fa-quote-left fa-2x text-primary mb-3"></i>
                <p>Quá trình đặt khóa học diễn ra rất mượt mà và nhanh chóng. Mình không gặp bất kỳ khó khăn nào khi chọn khóa học,
                    thanh toán cũng dễ dàng và bảo mật.</p>
                <div class="d-flex align-items-center">
                    <img class="img-fluid flex-shrink-0 rounded" src="img/image_user/student2.jpg"
                         style="width: 100px; height: 100px;">
                    <div class="ps-3">
                        <h5 class="mb-1">Client Name</h5>
                        <small>Profession</small>
                    </div>
                </div>
            </div>
            <div class="testimonial-item bg-light rounded p-4">
                <i class="fa fa-quote-left fa-2x text-primary mb-3"></i>
                <p>Mình rất thích cách trang cung cấp đầy đủ thông tin về khóa học, từ thời gian, nội dung đến giảng viên.
                    Điều này giúp mình dễ dàng đưa ra quyết định phù hợp.</p>
                <div class="d-flex align-items-center">
                    <img class="img-fluid flex-shrink-0 rounded" src="img/image_user/student3.jpg"
                         style="width: 100px; height: 100px;">
                    <div class="ps-3">
                        <h5 class="mb-1">Client Name</h5>
                        <small>Profession</small>
                    </div>
                </div>
            </div>
            <div class="testimonial-item bg-light rounded p-4">
                <i class="fa fa-quote-left fa-2x text-primary mb-3"></i>
                <p>Trang hỗ trợ khách hàng rất nhanh chóng và chuyên nghiệp. Mọi thắc mắc của mình đều được giải đáp kịp thời.
                    Mình rất an tâm khi đăng ký khóa học tại đây!</p>
                <div class="d-flex align-items-center">
                    <img class="img-fluid flex-shrink-0 rounded" src="img/image_user/student4.jpg"
                         style="width: 100px; height: 100px;">
                    <div class="ps-3">
                        <h5 class="mb-1">Client Name</h5>
                        <small>Profession</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-xxl py-5">
    <div class="container">
        <h1 style="color: #00B074; font-weight: bolder" class="text-center mb-5 wow fadeInUp" data-wow-delay="0.1s">Service Categories</h1>
        <div class="row g-4">
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-mail-bulk mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Create Learning Fast</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-headset mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Learn Online</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-user-tie mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Human Resource</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-tasks mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Practice Skill</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-code mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Software Development</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-hands-helping mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Learn With Friends</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-book-reader mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3">Teaching & Education</h6>
                </span>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                <span class="cat-item rounded p-4" href="">
                    <i class="fa fa-3x fa-drafting-compass mb-4" style="color: #00B074"></i>
                    <h6 class="mb-3" style="color: black">Design & Creative</h6>
                </span>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid foot-background text-white-50 footer pt-5 mt-5 wow fadeIn" data-wow-delay="0.1s">
    <div class="container py-5">
        <div class="row g-5">
            <div class="col-lg-4 col-md-6">
                <h5 class="text-black mb-4">Company</h5>
                <a class="btn btn-link text-black-50" href="">About Us</a>
                <a class="btn btn-link text-black-50" href="">Contact Us</a>
                <a class="btn btn-link text-black-50" href="">Our Services</a>
                <a class="btn btn-link text-black-50" href="">Privacy Policy</a>
                <a class="btn btn-link text-black-50" href="">Terms & Condition</a>
            </div>
            <div class="col-lg-4 col-md-6">
                <h5 class="text-black mb-4">Contact</h5>
                <p class="mb-2 text-black"><i class="fa fa-map-marker-alt me-3 text-black"></i>KM29, Đại lộ Thăng Long, Hà Nội</p>
                <p class="mb-2 text-black"><i class="fa fa-phone-alt me-3 text-black"></i>0123456789</p>
                <p class="mb-2 text-black"><i class="fa fa-envelope me-3 text-black"></i>@fpt.edu.vn</p>
                <div class="d-flex pt-2">
                    <a style="margin: 10px" class="btn btn-outline-light btn-social" href=""><i class="fab fa-twitter"></i></a>
                    <a style="margin: 10px" class="btn btn-outline-light btn-social" href=""><i class="fab fa-facebook-f"></i></a>
                    <a style="margin: 10px" class="btn btn-outline-light btn-social" href=""><i class="fab fa-youtube"></i></a>
                    <a style="margin: 10px" class="btn btn-outline-light btn-social" href=""><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <h5 class="text-black mb-4">Subscribe</h5>
                <p class="text-black">Enter your mail to take notification about us</p>
                <div class="position-relative mx-auto" style="max-width: 400px;">
                    <input class="form-control bg-transparent w-100 py-3 ps-4 pe-5" type="text" placeholder="Your email">
                    <button type="button" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">Subscribe</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    document.addEventListener('DOMContentLoaded', function() {
        const slides = document.querySelectorAll('.banner img');
        let currentSlide = 0;
        const slideInterval = setInterval(nextSlide, 2000);

        function nextSlide() {
            slides[currentSlide].classList.remove('active');
            currentSlide = (currentSlide + 1) % slides.length;
            slides[currentSlide].classList.add('active');
        }
    });

    document.addEventListener('DOMContentLoaded', () => {
        const starsTotal = 5;

        // Get all star rating elements
        const starContainers = document.querySelectorAll('.stars-inner');

        starContainers.forEach(starsInner => {
            // Get the rating from the data-rating attribute
            const rating = parseFloat(starsInner.getAttribute('data-rating'));
            console.log(starsInner.getAttribute('data-rating'));
            // Calculate the percentage of stars to fill
            const starPercentage = (rating / starsTotal) * 100;
            // Set the width of the stars-inner to the calculated percentage
            starsInner.style.width = starPercentage + '%';
        });
    });

    function changeToMentorProfile(id) {
        window.location.href = 'mentor/profile?mentor' +
            'id='+ id;
    }

    function changeToSearchSkill(skill) {
        window.location.href = 'Search_Skills?skill=' + encodeURIComponent(skill);
    }

    function showContent(type) {
        // Remove active class from both tabs
        document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
        // Add active class to clicked tab
        if (type === 'mentor') {
            document.querySelector('.tab:nth-child(1)').classList.add('active');
        } else {
            document.querySelector('.tab:nth-child(2)').classList.add('active');
        }

        // document.getElementById('mentor').style.display = "block";
        // document.getElementById('subject').style.display = "block";
        if(type === 'mentor') {
            document.getElementById('mentor').style.display = "block";
            document.getElementById('subject').style.display = "none";
        } else {
            document.getElementById('mentor').style.display = "none";
            document.getElementById('subject').style.display = "block";
        }

    }

    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        center: true,
        margin: 24,
        dots: true,
        loop: true,
        nav : false,
        responsive: {
            0:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });

</script>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</body>
</html>

var url = window.location.href;
var lastSegment = url.substring(url.lastIndexOf("/") + 1);
switch (lastSegment) {
    case "register":
        window.onload = function() {
            showSignup();
        };
        break;
    case  "reset-pass":
        window.onload = function() {
            showForgotPassword();
        };
        break
    default:
        // Khối mã được thực thi nếu không có trường hợp case nào khớp
        break;
}

let prism = document.querySelector(".rec-prism");

function showSignup() {
    prism.style.transform = "translateZ(-100px) rotateY( -90deg)";
}
function showLogin() {
    prism.style.transform = "translateZ(-100px)";
}
function showForgotPassword() {
    prism.style.transform = "translateZ(-100px) rotateY( -180deg)";
}

function showSubscribe() {
    prism.style.transform = "translateZ(-100px) rotateX( -90deg)";
}

function showContactUs() {
    prism.style.transform = "translateZ(-100px) rotateY( 90deg)";
}

function showThankYou() {
    prism.style.transform = "translateZ(-100px) rotateX( 90deg)";
}
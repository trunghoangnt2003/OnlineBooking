// Selecting form and input elements
const form = document.querySelector("form");
const passwordInput = document.getElementById("password");
const repasswordInput = document.getElementById("repassword");
const passToggleBtn = document.getElementById("pass-toggle-btn");
const rePassToggleBtn = document.getElementById("repass-toggle");
// Function to display error messages
const showError = (field, errorText) => {
    field.classList.add("error");
    const errorElement = document.createElement("small");
    errorElement.classList.add("error-text");
    errorElement.innerText = errorText;
    field.closest(".form-group").appendChild(errorElement);
}

// Function to handle form submission
const handleFormData = (e) => {
    e.preventDefault();

    // Retrieving input elements
    const fullnameInput = document.getElementById("fullname");
    const usernameInput = document.getElementById("username");
    const emailInput = document.getElementById("email");
    const phoneInput = document.getElementById("phone");
    const dateInput = document.getElementById("date");
    const addressInput = document.getElementById("address");
    const genderInput = document.getElementById("gender");
    const roleInput = document.getElementById("role");

    // Getting trimmed values from input fields
    const fullname = fullnameInput.value.trim();
    const username = usernameInput.value.trim();
    const email = emailInput.value.trim();
    const phone = phoneInput.value.trim();
    const password = passwordInput.value.trim();
    const repassword = repasswordInput.value.trim();
    const date = dateInput.value;
    const address = addressInput.value;
    const gender = genderInput.value;
    const role = roleInput.value;


    // Regular expression pattern for email validation
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    // Clearing previous error messages
    document.querySelectorAll(".form-group .error").forEach(field => field.classList.remove("error"));
    document.querySelectorAll(".error-text").forEach(errorText => errorText.remove());

    // Performing validation checks
    if(repassword === ""){
        showError(repasswordInput, "Enter your confirm password");
    }else {
        if(repassword !== password){
            showError(repasswordInput,"Confirm password don't matching")
        }
    }
    if (username === "") {
        showError(usernameInput, "Enter your user name");
    }
    if (address === "") {
        showError(addressInput, "Enter your address");
    }
    if (phone === "") {
        showError(phoneInput, "Enter your phone");
    }
    if (role === "") {
        showError(roleInput, "Select your role");
    }
    if (fullname === "") {
        showError(fullnameInput, "Enter your full name");
    }
    if (!emailPattern.test(email)) {
        showError(emailInput, "Enter a valid email address : abc@mail.com");
    }
    if (password === "") {
        showError(passwordInput, "Enter your password");
    }else {
        if(!combinations.regex.test(password)){
            showError(passwordInput,"At least 8 characters long");
        }
    }
    if (date === "") {
        showError(dateInput, "Select your date of birth");
    }
    if (gender === "") {
        showError(genderInput, "Select your gender");
    }


    // Checking for any remaining errors before form submission
    const errorInputs = document.querySelectorAll(".form-group .error");
    if (errorInputs.length > 0) return;

    // Submitting the form
    form.submit();
}
rePassToggleBtn.addEventListener('click', () => {
    rePassToggleBtn.className = repasswordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
    repasswordInput.type = repasswordInput.type === "password" ? "text" : "password";
});
// Toggling password visibility
passToggleBtn.addEventListener('click', () => {
    passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
    passwordInput.type = passwordInput.type === "password" ? "text" : "password";
});

// Handling form submission event
form.addEventListener("submit", handleFormData);

const combinations = { regex: /.{8}/, key: 0 };

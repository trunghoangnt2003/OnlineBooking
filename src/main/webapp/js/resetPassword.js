// Selecting form and input elements
const form = document.querySelector("form");

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
    const emailInput = document.getElementById("email");

    // Getting trimmed values from input fields
    const email = emailInput.value.trim();


    // Regular expression pattern for email validation
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    // Clearing previous error messages
    document.querySelectorAll(".form-group .error").forEach(field => field.classList.remove("error"));
    document.querySelectorAll(".error-text").forEach(errorText => errorText.remove());

    // Performing validation checks

    if (!emailPattern.test(email)) {
        showError(emailInput, "Enter a valid email address : abc@mail.com");
    }


    // Checking for any remaining errors before form submission
    const errorInputs = document.querySelectorAll(".form-group .error");
    if (errorInputs.length > 0) return;

    // Submitting the form
    form.submit();
}

// Toggling password visibility
passToggleBtn.addEventListener('click', () => {
    passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
    passwordInput.type = passwordInput.type === "password" ? "text" : "password";
});

// Handling form submission event
form.addEventListener("submit", handleFormData);

const combinations = { regex: /.{8}/, key: 0 };
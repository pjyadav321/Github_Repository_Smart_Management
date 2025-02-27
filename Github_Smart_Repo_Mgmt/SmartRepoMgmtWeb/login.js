
document.getElementById("login-form").addEventListener("submit", function(event) 
{
    event.preventDefault();

    // Get the values from the form
    const userId = document.getElementById("userId").value.trim();
    const password = document.getElementById("password").value.trim();
    const userType = getSelectedRole(); // document.getElementById("userType").value;


    // Validate the input fields
    if (!userId || !password) {
        document.getElementById("error-message").textContent = "User ID and Password are required!";
        document.getElementById("success-message").textContent = ''; // Clear any success message
        return;
    }

    // Prepare the login request data
    const loginRequest = {
        userId: userId,
        password: password,
        userType: userType
    };

    // Make the API request to login
    fetch("http://localhost:8080/api/users/login", { // Replace with your actual API URL
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginRequest) //Converts the loginRequest JavaScript object into a JSON string.
    })
    .then(response => {
        if (response.ok) {
            return response.text(); // Expecting success message as plain text
        } else {
            return response.text().then(error => {
                throw new Error(error); // Handle backend error messages
            });
        }
    })
    .then(data => {
        // Show success message

        const jsonData = JSON.parse(data);
        document.getElementById("success-message").textContent = jsonData.message;
        document.getElementById("error-message").textContent = ''; // Clear any error message

        
        // Redirect based on userType
        if (userType === "ADMIN") 
        {
            setTimeout(() => window.location.href = "admin-dashboard.html", 1000);
        } 
        else if (userType === "STUDENT") 
        {
            // Store required user info for GitHub in local storage
            localStorage.setItem("gHU", jsonData.githubUserId);
            localStorage.setItem("gHR", jsonData.githubRepoName);
            localStorage.setItem("gHK", jsonData.githubAPIToken);

            //alert(jsonData.githubUserId);
            setTimeout(() => window.location.href = "student-dashboard.html", 1000);
        }
    })
    .catch(error => {
        // Show error message
        document.getElementById("error-message").textContent = error.message || "An error occurred during login.";
        document.getElementById("success-message").textContent = ''; // Clear any success message
    });
});

// Function to get the selected radio button value
function getSelectedRole() {
    // Get all the radio buttons with name "role"
    const radios = document.getElementsByName('role');
    
    // Loop through the radio buttons to find the one that's checked
    for (let radio of radios) {
        if (radio.checked) {
            return radio.value; // Return the value of the selected radio button
        }
    }
    return null; // Return null if no radio button is selected
}

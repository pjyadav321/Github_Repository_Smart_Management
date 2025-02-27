async function registerUser() {
    const userId = document.getElementById("userId").value.trim();
    const password = document.getElementById("password").value.trim();
    const userType = document.getElementById("userType").value.trim();
    const userName = document.getElementById("userName").value.trim();
    const githubUserId = document.getElementById("githubUserId").value.trim();
    const githubRepoName = document.getElementById("githubRepoName").value.trim();
    const githubAPIToken = document.getElementById("githubAPIToken").value.trim();

    if (!userId || !password || !userType || !userName || !githubUserId || !githubRepoName || !githubAPIToken) {
        displayMessage("All fields are required.", "red");
        return;
    }

    const user = { userId, password, userType, userName, githubUserId, githubRepoName, githubAPIToken };

    try {
        const response = await fetch("http://localhost:8080/api/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });

        if (response.ok) {
            const responseText = await response.text();
            displayMessage(responseText, "green");
        } else {
            const errorText = await response.text();
            displayMessage(errorText, "red");
        }
    } catch (error) {
        console.error("Error:", error);
        displayMessage("An error occurred while registering. Please try again later.", "red");
    }
}

function displayMessage(message, color) {
    const messageElement = document.getElementById("message");
    messageElement.textContent = message;
    messageElement.style.color = color;
}


const repoOwner = localStorage.getItem("gHU"); //"pjyadav321";                 
const repoName = localStorage.getItem("gHR"); //"APJFSA_N";                    
const baseRepoUrl = `https://api.github.com/repos/${repoOwner}/${repoName}`;        //
const contentsUrl = `${baseRepoUrl}/contents`;  
const commitsUrl = `${baseRepoUrl}/commits`;
const githubToken = localStorage.getItem("gHK"); //"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"; // GitHub Personal Access Token

// Fetch repository summary
async function fetchRepoSummary() {
    try {
        //alert(commitsUrl + githubToken);
        const commitsResponse = await fetch(commitsUrl, {
            headers: { Authorization: `token ${githubToken}` }
        });

        if (!commitsResponse.ok) {
            throw new Error(`Error fetching commits: ${commitsResponse.status}`);
        }
        const commitsData = await commitsResponse.json();
        document.getElementById("total-commits").textContent = commitsData.length;

        const contentsResponse = await fetch(contentsUrl, {
            headers: { Authorization: `token ${githubToken}` }
        });
        if (!contentsResponse.ok) {
            throw new Error(`Error fetching contents: ${contentsResponse.status}`);
        }
        const contentsData = await contentsResponse.json();
        document.getElementById("total-files").textContent = contentsData.length;
    } catch (error) {
        alert('E ' + error);
        console.error("Error fetching repository summary:", error);
        document.getElementById("total-commits").textContent = "Error";
        document.getElementById("total-files").textContent = "Error";
    }
}

// Fetch repository files
async function fetchRepoFiles() {
    try {
        const response = await fetch(contentsUrl, {
            headers: { Authorization: `token ${githubToken}` }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        const dropdown = document.getElementById("file-dropdown");

        data.forEach(file => {
            const option = document.createElement("option");
            option.value = file.download_url;
            option.textContent = file.name;
            dropdown.appendChild(option);
        });

        dropdown.addEventListener("change", (event) => {
            const fileUrl = event.target.value;
            if (fileUrl) {
                fetchFileContent(fileUrl);
            } else {
                document.getElementById("file-content").textContent = "Select a file to view its content.";
            }
        });
    } catch (error) {
        console.error("Error fetching repository files:", error);
    }
}

// Fetch file content
async function fetchFileContent(fileUrl) {
    try {
        const response = await fetch(fileUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const content = await response.text();
        document.getElementById("file-content").textContent = content;
    } catch (error) {
        console.error("Error fetching file content:", error);
        document.getElementById("file-content").textContent = "Error loading file content.";
    }
}

// Upload file to repository
async function uploadFileToRepo(file) {
    const fileName = file.name;
    const filePath = `/${fileName}`;
    const reader = new FileReader();

    reader.onload = async function () {
        const fileContent = reader.result.split(",")[1]; // Get base64 content
        const uploadStatus = document.getElementById("upload-status");

        try {
            const response = await fetch(`${contentsUrl}${filePath}`, {
                method: "PUT",
                headers: {
                    "Authorization": `token ${githubToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    message: `Add file: ${fileName}`,
                    content: fileContent,
                }),
            });

            const responseData = await response.json();
            if (!response.ok) {
                console.error("Error Response:", responseData);
                throw new Error(`Upload failed: ${responseData.message}`);
            }

            uploadStatus.textContent = `File "${fileName}" uploaded successfully!`;
            uploadStatus.style.color = "green";
            fetchRepoFiles(); // Refresh file list
        } catch (error) {
            console.error("Error uploading file:", error);
            uploadStatus.textContent = "Error uploading file.";
            uploadStatus.style.color = "red";
        }
    };

    reader.readAsDataURL(file);
}

// Event listener for file upload
document.getElementById("upload-button").addEventListener("click", () => {
    const fileInput = document.getElementById("file-input");
    if (fileInput.files.length > 0) {
        uploadFileToRepo(fileInput.files[0]);
    } else {
        alert("Please select a file to upload.");
    }
});

// Initialize
fetchRepoSummary();
fetchRepoFiles();


const studentsDropdown = document.getElementById("students-dropdown");
//let selectedStudent = {};

studentsDropdown.addEventListener('change', (event) => {
    // Get the selected option's value
    const selectedOption = event.target.value;

    const selectedStudent = JSON.parse(selectedOption);
    //alert('A' + JSON.stringify(selectedStudent));

    const repoOwner = selectedStudent.githubUserId;  //"pjyadav321"; //localStorage.getItem("gHO");                 
    const repoName = selectedStudent.githubRepoName;    //"APJFSA_N"; //localStorage.getItem("gHR"); 
    //alert(repoOwner + " " + repoName);

    baseRepoUrl = `https://api.github.com/repos/${repoOwner}/${repoName}`;
    contentsUrl = `${baseRepoUrl}/contents`;  
    commitsUrl = `${baseRepoUrl}/commits`;

    fetchRepoSummary();
    fetchRepoFiles();

    //resultParagraph.textContent = `You selected: ${selectedOption}`;
});

let baseRepoUrl = ''; // `https://api.github.com/repos/${repoOwner}/${repoName}`;        //
let contentsUrl = ''; // `${baseRepoUrl}/contents`;  
let commitsUrl = ''; //`${baseRepoUrl}/commits`;
const githubToken = "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"; // GitHub Personal Access Token

// Fetch repository summary
async function fetchRepoSummary()
 {
    try {
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

        // Clear the dropdown of previous options
        dropdown.innerHTML = ""; // Clears all options

        // Optionally, add a default placeholder option
        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "-- Select a file --";
        dropdown.appendChild(defaultOption);

        // Populate the dropdown with new options
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

// Function to fetch students from the backend and populate the students dropdown
async function fetchStudents() {
    try {
        const response = await fetch("http://localhost:8080/api/users/students", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });
 
        if (response.ok) {
            
            const students = await response.json(); // Parse the response as JSON
            const studentsDropdown = document.getElementById("students-dropdown");

            studentsDropdown.innerHTML = '';

            // Add a default option
            const defaultOption = document.createElement("option");
            defaultOption.textContent = "-- Select a Student --";
            studentsDropdown.appendChild(defaultOption);

            students.forEach(student => {
                const option = document.createElement("option");
                //option.value = student.userId; // Using userId as value
                option.value = JSON.stringify(student);
                option.textContent = `${student.userName}`; // Display name 
                studentsDropdown.appendChild(option);
            });
        } else {
            throw new Error('Failed to fetch students');
        }
    } catch (error) {
        console.error("Error fetching students:", error);
        const studentsDropdown = document.getElementById("students-dropdown");
        studentsDropdown.innerHTML = `<option>Error loading students</option>`;
    }
}

// Fetch repository for selected student
async function fetchRepositories(username, githubToken) 
{
    const apiBaseUrl = "https://api.github.com/users";

    try {
        const response = await fetch(`${apiBaseUrl}/${username}/repos`, {
            headers: {
                Authorization: `Bearer ${githubToken}`,
            },
        });

        if (!response.ok) {
            throw new Error(`Error fetching repositories for ${username}: ${response.status}`);
        }

        const repos = await response.json();
        return repos;
    } catch (error) {
        console.error(error);
        alert(`Failed to fetch repositories for ${username}`);
        return [];
    }
}

// Call the function to fetch students when the page loads
fetchStudents();





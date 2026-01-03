async function scanResume() {
    const resumeFile = document.getElementById("resumeFile").files[0];
    const jobDescription = document.getElementById("jobDescription").value;
    const btn = document.getElementById("scanBtn");

    if (!resumeFile || !jobDescription) {
        alert("Please upload resume and enter job description");
        return;
    }

    // 🔹 START loading state
    btn.disabled = true;
    btn.innerText = "Scanning...";

    const formData = new FormData();
    formData.append("resume", resumeFile);
    formData.append("jobDescription", jobDescription);

    try {
        const response = await fetch("/api/match/pdf", {
            method: "POST",
            body: formData
        });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText);
    }

    const data = await response.json();


        document.getElementById("matchPercentage").innerText =
            data.matchPercentage.toFixed(2);

        fillList("missingSkills", data.missingSkills);
        fillList("aiSuggestions", data.aiSuggestions);

        document.getElementById("result").classList.remove("hidden");

    } catch (error) {
        console.error("Backend error:", error.message);
        alert("Error scanning resume:\n" + error.message);
        // console.error(error);
        // alert("Error scanning resume");

    } finally {
        // 🔹 END loading state (runs even if error happens)
        btn.disabled = false;
        btn.innerText = "Scan Resume";
    }
}

function fillList(elementId, items) {
    const ul = document.getElementById(elementId);
    ul.innerHTML = "";

    if (!items || items.length === 0) {
        const li = document.createElement("li");
        li.innerText = "None";
        ul.appendChild(li);
        return;
    }

    items.forEach(item => {
        const li = document.createElement("li");
        li.innerText = item;
        ul.appendChild(li);
    });
}


function showcourse(){
fetch("http://localhost:8080/course")
.then((response) => response.json())
.then((courses)=>{
const datatable=document.getElementById("coursetable")
courses.forEach(course=>{
    var row=`<tr>
        <td>${course.id}</td>
        <td>${course.name}</td>
        <td>${course.courses}</td>
        <td>${course.duration}</td>
          <td>${course.seats > 0 ? course.seats : '<span style="color:red;">Full</span>'}</td>
        
   
    </tr>`
    datatable.innerHTML+=row;
});

});


}

function enrolledcourse() {
    fetch("http://localhost:8080/enroll")
        .then((response) => response.json())
        .then((stu) => {
            const datatable = document.getElementById("enrolltable");
            datatable.innerHTML = ""; 
            stu.forEach((s) => {
                const row = `
                    <tr>
                        <td>${s.name}</td>
                        <td>${s.emailid}</td>
                        <td>${s.coursename}</td>
                        <td>
                            <button onclick="openModal(${s.id}, '${s.name}', '${s.emailid}', '${s.coursename}')">Edit</button>
                            <button onclick="deleteEnrollment(${s.id})">Delete</button>
                        </td>
                    </tr>`;
                datatable.innerHTML += row;
            });
        });
}

function deleteEnrollment(id) {
    if (confirm("Are you sure you want to delete this enrollment?")) {
        fetch(`http://localhost:8080/enroll/${id}`, { method: 'DELETE' })
            .then(() => {
                alert("Deleted successfully.");
                enrolledcourse();
            });
    }
}

function openModal(id, name, email, course) {
    document.getElementById("update-id").value = id;
    document.getElementById("update-name").value = name;
    document.getElementById("update-email").value = email;
    document.getElementById("update-course").value = course;
    document.getElementById("updateModal").style.display = "block";
}

function closeModal() {
    document.getElementById("updateModal").style.display = "none";
}

function submitUpdate() {
    const id = document.getElementById("update-id").value;
    const name = document.getElementById("update-name").value;
    const emailid = document.getElementById("update-email").value;
    const coursename = document.getElementById("update-course").value;

    // Send data as query parameters
    fetch(`http://localhost:8080/enroll/update/${id}?name=${encodeURIComponent(name)}&emailid=${encodeURIComponent(emailid)}&coursename=${encodeURIComponent(coursename)}`, {
        method: 'PUT'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to update enrollment");
        }
        return response.text(); // or response.json() if backend returns JSON
    })
    .then(() => {
        alert("Enrollment updated successfully.");
        closeModal();
        enrolledcourse();
    })
    .catch(error => {
        console.error("Update error:", error);
        alert("Error updating enrollment.");
    });
}
window.onload = enrolledcourse;


function reportHandle(mentor_id,mentor){
  document.getElementById("report-modal").classList.toggle("active");
  const to = document.getElementById("to-mentor");
  const reason = document.getElementById('reason');
  reason.value = "";

  to.innerHTML = "<span class='text-center'>Report mentor: <b>"+mentor+"</b></span>";
  const input_id = document.createElement('input');
  input_id.type = 'text';
  input_id.name = "mentor_id";
  input_id.value = mentor_id;
  input_id.id = "mentor_id";
  input_id.style.display = "none";

  to.appendChild(input_id);
}


function fetchReport(mentor_id,reason){
  fetch("../mentee/report", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({mentor_id:mentor_id,reason:reason}),
  }).then( response => {
    if (response.ok) {
      console.log(response);
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Your work has been saved",
        showConfirmButton: false,
        timer: 1500
      });
      document.getElementById("report-modal").classList.toggle("active");
    } else {
      throw new Error('Network response was not ok.');
    }
  }).catch(
      error => {
        console.error('Error:', error);
      }
  )

}

function sendReport(){
  Swal.fire({
    title: "Are you sure?",
    text: "Do you want to report this mentor?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes"
  }).then((result) => {
    if (result.isConfirmed) {
      const reason = document.getElementById("reason").value;
      if(reason == ""){
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: " Please input reason!",
        });
      }else{
        const mentor_id = document.getElementById("mentor_id").value;
        fetchReport(mentor_id,reason);
        // document.getElementById("frm-report").submit();
      }

    }
  });
}

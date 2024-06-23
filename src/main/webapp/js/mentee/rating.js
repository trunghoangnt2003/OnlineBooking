const stars = document.querySelectorAll('.star');
let rating ;

function ratingHandle(mentor_id,mentor){
    document.getElementById("rating-modal").classList.toggle("active");
    const to = document.getElementById("rating-to-mentor");
    const comment = document.getElementById('comment');
    comment.value = "";
    highlightStars(5);
    to.innerHTML = "<span class='text-start' style='font-size: 16px'> <b>"+mentor+"</b></span>";
    const input_id = document.createElement('input');
    input_id.type = 'text';
    input_id.name = "mentor_id";
    input_id.value = mentor_id;
    input_id.id = "mentor_id";
    input_id.style.display = "none";

    to.appendChild(input_id);
}
stars.forEach(star => {
    star.addEventListener('mouseenter', handleHover);
    star.addEventListener('click', handleClick);
    star.addEventListener('mouseleave', handleLeave);

});

function highlightStars(rating) {
    stars.forEach((star, index) => {
        if(index < rating) {
            star.classList.add('selected');
        } else {
            star.classList.remove('selected');
        }
    })
}

function handleHover(e) {
    const rating = e.currentTarget.getAttribute('data-value');
    highlightStars(rating);
}

function  handleLeave(e) {
    highlightStars(rating);
}

function handleClick(e) {
    // btn.style.display = "block";
    rating = e.currentTarget.getAttribute('data-value');
    highlightStars(rating);
}

function fetchRating(mentor_id,comment){
    fetch("../mentee/review", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({mentor_id:mentor_id,comment:comment,rating:rating}),
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
        }else {
            console.log(response);
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
            });
        }
    }).catch(
        error => {
            console.log('Error',error);
        }
    )
}

function sendRating(){
    const mentor_id = document.getElementById("mentor_id").value;
    const comment = document.getElementById('comment').value;
    console.log(mentor_id);
    console.log(comment);
    console.log(rating);

    if(mentor_id && comment)
    {
        fetchRating(mentor_id,comment);
        document.getElementById("rating-modal").classList.toggle("active");
    }
    else
    {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: " Please rating stars and comment!",
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    // Total number of stars
    const starsTotal = 5;

    // Get all star rating elements
    const starContainers = document.querySelectorAll('.stars-inner');

    starContainers.forEach(starsInner => {
        // Get the rating from the data-rating attribute
        const rating = parseFloat(starsInner.getAttribute('data-rating'));
        // Calculate the percentage of stars to fill
        const starPercentage = (rating / starsTotal) * 100;

        // Set the width of the stars-inner to the calculated percentage
        starsInner.style.width = starPercentage + '%';
    });

});
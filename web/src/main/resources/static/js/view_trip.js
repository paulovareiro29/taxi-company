const FeedbackForm = document.getElementById('write-feedback-form')
const ShowFeedbackFormBtn = document.getElementById('write-feedback-btn')

ShowFeedbackFormBtn.onclick = function () {
    FeedbackForm.classList.remove("hidden");
    FeedbackForm.classList.add("flex");
}

FeedbackForm.onreset = function(e) {
    FeedbackForm.classList.remove("flex");
    FeedbackForm.classList.add("hidden");
}

FeedbackForm.onsubmit = function(e) {
    const { rating } = Object.fromEntries(new FormData(e.target));

    if(typeof rating == "string" && parseInt(rating) === -1) {
        const ratingSelect = e.target["rating"]
        ratingSelect.classList.remove("border-tertiary")
        ratingSelect.classList.add("border-danger")
        e.preventDefault();
    }
}
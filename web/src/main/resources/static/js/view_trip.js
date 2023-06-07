const FeedbackForm = document.getElementById('write-feedback-form')
const ShowFeedbackFormBtn = document.getElementById('write-feedback-btn')
const FinishTripForm = document.getElementById('finish-trip-form')
const FinishTripFormBtn = document.getElementById('finish-trip-btn')

if(ShowFeedbackFormBtn)
    ShowFeedbackFormBtn.onclick = function () {
        FeedbackForm.classList.remove("hidden");
        FeedbackForm.classList.add("flex");
    }

if(FeedbackForm) {
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
}

if(FinishTripFormBtn){
    FinishTripFormBtn.onclick = function () {
        FinishTripForm.classList.remove("hidden");
        FinishTripForm.classList.add("flex");
    }
}

if(FinishTripForm) {
    FinishTripForm.onreset = function (e) {
        FinishTripForm.classList.remove("flex");
        FinishTripForm.classList.add("hidden");
    }

    FinishTripForm.onsubmit = function (e) {
        const {paymentMethod, vatNumber} = Object.fromEntries(new FormData(e.target));

        if (typeof paymentMethod == "string" && paymentMethod === "") {
            const methodSelect = e.target["paymentMethod"]
            methodSelect.classList.remove("border-tertiary")
            methodSelect.classList.add("border-danger")
            e.preventDefault();
        }
    }
}
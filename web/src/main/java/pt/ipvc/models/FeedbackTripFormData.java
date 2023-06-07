package pt.ipvc.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class FeedbackTripFormData {

    @NotBlank(message = "Review is required")
    public String review;

    @Positive(message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating has a maximum of 5")
    @Min(value = 1, message = "Rating has a minimum of 1")
    public int rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

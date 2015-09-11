package microservices.training.app.rest;

/**
 * Created by work on 11/09/15.
 */
class FeedbackPostResponse {
    private String status = "unknown";

    private FeedbackPostResponse(String status) {
        this.status = status;
    }

    public static FeedbackPostResponse ok() {
        return new FeedbackPostResponse("ok");
    }
}

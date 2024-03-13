package id.jkw.demospringquartz.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleExampleResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    public ScheduleExampleResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ScheduleExampleResponse(boolean success, String jobId, String jobGroup, String message) {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
        this.message = message;
    }

}

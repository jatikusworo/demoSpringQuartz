package id.jkw.demospringquartz.controller;

import id.jkw.demospringquartz.job.ExampleJob;
import id.jkw.demospringquartz.payload.ScheduleExampleRequest;
import id.jkw.demospringquartz.payload.ScheduleExampleResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class ExampleSchedulerController {

    private final Scheduler scheduler;

    public ExampleSchedulerController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleExampleResponse> scheduleEmail(@Valid @RequestBody ScheduleExampleRequest scheduleRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(scheduleRequest.getDateTime(), scheduleRequest.getTimeZone());
            if (dateTime.isBefore(ZonedDateTime.now())) {
                ScheduleExampleResponse scheduleEmailResponse = new ScheduleExampleResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleEmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(scheduleRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleExampleResponse scheduleEmailResponse = new ScheduleExampleResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
            log.error("Error scheduling email", ex);

            ScheduleExampleResponse scheduleEmailResponse = new ScheduleExampleResponse(false,
                    "Error scheduling email. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
        }
    }

        private JobDetail buildJobDetail (ScheduleExampleRequest scheduleRequest){
            JobDataMap jobDataMap = new JobDataMap();

            jobDataMap.put("email", scheduleRequest.getEmail());
            jobDataMap.put("subject", scheduleRequest.getSubject());
            jobDataMap.put("body", scheduleRequest.getBody());

            return JobBuilder.newJob(ExampleJob.class)
                    .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                    .withDescription("Send Email Job")
                    .usingJobData(jobDataMap)
                    .storeDurably()
                    .build();
        }

        private Trigger buildJobTrigger (JobDetail jobDetail, ZonedDateTime startAt){
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                    .withDescription("Send Email Trigger")
                    .startAt(Date.from(startAt.toInstant()))
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                    .build();
        }
    }


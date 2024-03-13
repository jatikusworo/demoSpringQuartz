package id.jkw.demospringquartz.config;

import id.jkw.demospringquartz.component.scheduler.ScheduleJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
public class QuartzConfig {

//    @Autowired
//    private  DataSource dataSource;
//    @Autowired
//    private ApplicationContext applicationContext;
//    @Autowired
//    private QuartzProperties quartzProperties;
//
//
//    @Primary
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//
//        ScheduleJobFactory jobFactory = new ScheduleJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//
//        Properties properties = new Properties();
//        properties.putAll(quartzProperties.getProperties());
//
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setOverwriteExistingJobs(true);
//        factory.setDataSource(dataSource);
//        factory.setQuartzProperties(properties);
//        factory.setJobFactory(jobFactory);
//        return factory;
//    }
}

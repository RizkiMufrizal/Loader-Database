package org.spi.loader.database.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 16 May 2019
 * @Time 11:12
 * @Project Loader-Database
 * @Package org.spi.loader.database.listener
 * @File JobCompletionNotificationListener
 */

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Batch Processing Is Complete");

            int countPerson = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tb_person", Integer.class);
            log.info("All Person In Database : {}", countPerson);
        }
    }
}
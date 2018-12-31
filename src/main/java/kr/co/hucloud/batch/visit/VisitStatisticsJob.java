package kr.co.hucloud.batch.visit;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.hucloud.batch.tool.HuCloudContext;
import kr.co.hucloud.batch.tool.Receiver;
import kr.co.hucloud.batch.visit.dao.VisitStatisticsDao;
import kr.co.hucloud.batch.visit.vo.Visit;

public class VisitStatisticsJob extends QuartzJobBean {

	private VisitStatisticsDao visitStatisticsDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		visitStatisticsDao = HuCloudContext.getBean("visitStatisticsDao");
		
		Receiver receiver = HuCloudContext.getBean("kafkaConsumer");
		ConsumerRecords<String, String> logs = receiver.receive("logTopic");
		
		logs.forEach(cr -> {
			Arrays.asList(cr.value())
					.stream().filter(line -> line.trim().length() > 0)
					.filter(line -> line.trim()
										.startsWith("SPARK "))
										.map(line -> line.replace("SPARK ", ""))
					.map(line -> line.split("ł"))
					.map(arr -> new Visit(arr))
					.forEach(v -> {
						visitStatisticsDao.insertStatisticsByYears(v);
						visitStatisticsDao.insertStatisticsByMonths(v);
						visitStatisticsDao.insertStatisticsByDates(v);
						visitStatisticsDao.insertStatisticsByHours(v);
						visitStatisticsDao.insertStatisticsByMinutes(v);
						visitStatisticsDao.insertStatisticsBySeconds(v);
					});
		});
		receiver.pause();

	}

}
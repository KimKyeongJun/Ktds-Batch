package kr.co.hucloud.batch.visit;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.hucloud.batch.tool.HuCloudContext;
import kr.co.hucloud.batch.tool.Receiver;
import kr.co.hucloud.batch.visit.dao.TrafficDao;
import kr.co.hucloud.batch.visit.vo.Traffic;

public class PersonalJob extends QuartzJobBean {

	private TrafficDao trafficDao;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		trafficDao = HuCloudContext.getBean("trafficDao");
		
		Receiver receiver = HuCloudContext.getBean("kafkaConsumer");
		ConsumerRecords<String, String> logs = receiver.receive("BatchTopic");
		
		
		logs.forEach(cr -> {
			Arrays.asList( cr.value() )
				.stream()
				.map( (line) -> line.split("ł"))
				.map( (arr) -> new Traffic(arr) )
				.forEach( (traffic) -> {
					trafficDao.insertTraffic(traffic);
				});
		});

		receiver.pause();
	}

}
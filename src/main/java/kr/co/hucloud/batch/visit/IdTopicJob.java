package kr.co.hucloud.batch.visit;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.hucloud.batch.tool.HuCloudContext;
import kr.co.hucloud.batch.tool.Receiver;
import kr.co.hucloud.batch.visit.dao.IdTopicDao;
import kr.co.hucloud.batch.visit.vo.IdTopic;

public class IdTopicJob extends QuartzJobBean{
	
	private IdTopicDao idTopicDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		idTopicDao = HuCloudContext.getBean("idTopicDao");
		Receiver receiver = HuCloudContext.getBean("kafkaConsumer");
		
		ConsumerRecords<String, String> logs = receiver.receive("IDTopic");
		
		
		logs.forEach( (cr) -> {
			Arrays.asList(cr.value())
					.stream()
					.map( (data) -> {
						String[] array = data.split("ł");
						return new IdTopic(array);
					})
					.forEach( (idTopic) -> {
						System.out.println(idTopic);
						idTopicDao.insertIdTopic(idTopic);
					});
		});
		
	} 
	
	

}

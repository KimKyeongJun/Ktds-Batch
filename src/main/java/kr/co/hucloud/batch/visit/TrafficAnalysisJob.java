package kr.co.hucloud.batch.visit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.hucloud.batch.tool.HuCloudContext;
import kr.co.hucloud.batch.tool.Receiver;
import kr.co.hucloud.batch.visit.dao.RealTimeLogDao;
import kr.co.hucloud.batch.visit.vo.RealTimeLog;

public class TrafficAnalysisJob extends QuartzJobBean {
	
	private RealTimeLogDao realTimeLogDao;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		
		realTimeLogDao = HuCloudContext.getBean("realTimeLogDao");
		
		Receiver receiver = HuCloudContext.getBean("kafkaConsumer");
		// TODO RealTimeLog Table Data 삭제
		realTimeLogDao.deleteAllRealTimeLog();
		
		ConsumerRecords<String, String> logs = receiver.receive("RealTimeTopic");
		
		List<String> logList = new ArrayList<>();
		
		logs.forEach( (log) -> {
			logList.add(log.value());
		});
		
		logList.stream()
				.map( (data) -> {
					String[] array = data.split("ł");
					String[] dataArray = new String[2];
					dataArray[0] = array[1];
					dataArray[1] = array[2];
					return dataArray;
				})
				.collect(Collectors.groupingBy( (dataArray) -> dataArray[0], Collectors.summingInt( (da) -> Integer.parseInt(da[1]))) )
				.entrySet()
				.stream()
				.map( (entry) -> new RealTimeLog(entry.getKey(), entry.getValue()))
				.forEach( (realTimeLog) -> {
						realTimeLogDao.insertAllRealTimeLog(realTimeLog);
				});
		
	}

}

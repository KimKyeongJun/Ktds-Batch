package kr.co.hucloud.batch.visit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.hucloud.batch.tool.HuCloudContext;
import kr.co.hucloud.batch.visit.dao.VisitStatisticsDao;
import kr.co.hucloud.batch.visit.vo.Visit;

public class VisitStatisticsJobOne extends QuartzJobBean {

	private VisitStatisticsDao visitStatisticsDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		visitStatisticsDao = HuCloudContext.getBean("visitStatisticsDao");

		List<String> logFileList = Arrays.asList(getLogFiles("D:\\sparklog").split(","));

		try {

			logFileList.forEach(l -> {
				File partFile = new File(l);

				Arrays.stream(partFile.listFiles()) // 이 폴더에 들어 있는 모든 파일을 가져와
						.filter(f -> f.getName().startsWith("part-")).forEach(f -> {
							try {
								Files.readAllLines(Paths.get(f.getAbsolutePath())) // f.getAbsolutePath() : 파일의 절대 경로
										.stream().filter(line -> line.trim().length() > 0).map(line -> line.split("ł"))
										.map(arr -> new Visit(arr)).forEach(v -> {

											visitStatisticsDao.insertStatisticsByYears(v);
											visitStatisticsDao.insertStatisticsByMonths(v);
											visitStatisticsDao.insertStatisticsByDates(v);
											visitStatisticsDao.insertStatisticsByHours(v);
											visitStatisticsDao.insertStatisticsByMinutes(v);
											visitStatisticsDao.insertStatisticsBySeconds(v);

											f.delete();
											// INSERT

										});
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
			});
		} finally {
			/*
			 * logFileList.forEach(f -> { File dir = new File(f); dir.renameTo(new
			 * File(dir.getAbsolutePath() + "_DONE")); });
			 */
		}
	}

	private String getLogFiles(String path) {

		List<String> fileList = new ArrayList<>();
		File file = new File(path);
		List<File> fileArray = Stream.of(file.listFiles()).collect(Collectors.toList());

		for (File fileItem : fileArray) {
			if (fileItem.isDirectory()) {
				fileList.add(fileItem.getAbsolutePath());
			}
		}

		return fileList.stream().sorted((s2, s1) -> {
			String folderName1 = s1.replaceAll("[^0-9]+", "");
			String folderName2 = s2.replaceAll("[^0-9]+", "");
			return (int) (Long.parseLong(folderName1) - Long.parseLong(folderName2));
		})
//				.filter( (fileName) -> !fileName.endsWith("_DONE") )
				.collect(Collectors.joining(","));
	}

}

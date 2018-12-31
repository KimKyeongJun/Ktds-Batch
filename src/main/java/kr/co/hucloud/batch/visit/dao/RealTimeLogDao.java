package kr.co.hucloud.batch.visit.dao;

import kr.co.hucloud.batch.visit.vo.RealTimeLog;

public interface RealTimeLogDao {
	
	public int deleteAllRealTimeLog();
	
	public int insertAllRealTimeLog(RealTimeLog realTimeLog);

}

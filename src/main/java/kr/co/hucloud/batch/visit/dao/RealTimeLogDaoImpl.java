package kr.co.hucloud.batch.visit.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import kr.co.hucloud.batch.visit.vo.RealTimeLog;

public class RealTimeLogDaoImpl extends SqlSessionDaoSupport implements RealTimeLogDao{

	@Override
	public int deleteAllRealTimeLog() {
		return getSqlSession().delete("RealTimeLogDao.deleteAllRealTimeLog");
	}

	@Override
	public int insertAllRealTimeLog(RealTimeLog realTimeLog) {
		return getSqlSession().insert("RealTimeLogDao.insertAllRealTimeLog", realTimeLog);
	}

}

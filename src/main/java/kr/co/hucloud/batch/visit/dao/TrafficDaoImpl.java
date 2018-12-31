package kr.co.hucloud.batch.visit.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import kr.co.hucloud.batch.visit.vo.Traffic;

public class TrafficDaoImpl extends SqlSessionDaoSupport implements TrafficDao {
	
	@Override
	public int insertTraffic(Traffic traffic) {
		return getSqlSession().insert("TrafficDao.insertTraffic", traffic);
	}

}

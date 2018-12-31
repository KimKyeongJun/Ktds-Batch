package kr.co.hucloud.batch.visit.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import kr.co.hucloud.batch.visit.vo.IdTopic;

public class IdTopicDaoImpl extends SqlSessionDaoSupport implements IdTopicDao {

	@Override
	public int insertIdTopic(IdTopic idTopic) {
		return getSqlSession().insert("IdTopicDao.insertIdTopic", idTopic);
	}

}

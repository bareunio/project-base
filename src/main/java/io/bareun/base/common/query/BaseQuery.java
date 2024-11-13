package io.bareun.base.common.query;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnBean(SqlSession.class)
public class BaseQuery {

    private final SqlSession sqlSession;

    public int selectCount(String id) {
        return sqlSession.selectOne(id);
    }

    public int selectCount(String id, Object parameters) {
        return sqlSession.selectOne(id, parameters);
    }

    public Map<String, Object> selectOne(String id) {
        return sqlSession.selectOne(id);
    }

    public Map<String, Object> selectOne(String id, Object parameters) {
        return sqlSession.selectOne(id, parameters);
    }

    public List<Map<String, Object>> selectList(String id) {
        return sqlSession.selectList(id);
    }

    public List<Map<String, Object>> selectList(String id, Object parameters) {
        return sqlSession.selectList(id, parameters);
    }

    public int insert(String id) {
        return sqlSession.insert(id);
    }

    public int insert(String id, Object parameters) {
        return sqlSession.insert(id, parameters);
    }

    public int update(String id) {
        return sqlSession.update(id);
    }

    public int update(String id, Object parameters) {
        return sqlSession.update(id, parameters);
    }

    public int delete(String id) {
        return sqlSession.delete(id);
    }

    public int delete(String id, Object parameters) {
        return sqlSession.delete(id, parameters);
    }
}


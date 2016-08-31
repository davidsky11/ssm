package com.crm.dao.mybatis;

import java.util.List;
import com.crm.domain.LogInfo;
import com.crm.domain.query.LogInfoQuery;

/**
 * 日志信息Dao
 */
public interface LogInfoDao {

    /**
     * 查询 日志信息
     */
    List<LogInfo> find(final LogInfoQuery query);

    /**
     * 根据时间定时删除系统日志
     */
    int deleteByDate();
}
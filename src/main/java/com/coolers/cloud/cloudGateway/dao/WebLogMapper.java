package com.coolers.cloud.cloudGateway.dao;

import com.coolers.cloud.cloudGateway.po.WebLog;
import java.util.List;

public interface WebLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(WebLog record);

    WebLog selectByPrimaryKey(String id);

    List<WebLog> selectAll();

    int updateByPrimaryKey(WebLog record);
}
package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectByServiceAndServiceUserId(@Param("service") String service, @Param("serviceUserId") long serviceUserId);

    void insert(User user);
}

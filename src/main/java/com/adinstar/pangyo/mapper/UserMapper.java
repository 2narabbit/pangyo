package com.adinstar.pangyo.mapper;

import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectById(long id);
    User selectByServiceAndServiceUserId(@Param("service") String service, @Param("serviceUserId") long serviceUserId);
    int insert(User user);
    int updateStatus(@Param("id") long id, @Param("status") PangyoEnum.UserStatus status);
    User selectByName(String name);
    int update(User user);
}

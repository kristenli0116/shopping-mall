package com.study.api.dao;

import com.study.api.entity.Users;
import com.study.api.general.GeneralDAO;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends GeneralDAO<Users> {
}
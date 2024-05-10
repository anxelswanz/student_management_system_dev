package com.group1.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.group1.system.entity.Staff;
import com.group1.system.mapper.StaffMapper;
import com.group1.system.service.IStaffService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: IStaffService Implementation class
 *  This class encapsulates the logic related to student operations, including creating,
 *  updating, deleting, and retrieving staff user records.
 * @date 2024/3/29 21:51
 * @ProjectName Dyson Student Management System
 **/
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}

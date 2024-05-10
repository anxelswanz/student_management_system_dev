package com.group1.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group1.system.entity.Programme;
import com.group1.system.mapper.ProgrammeMapper;
import com.group1.system.service.IProgrammeService;
import org.springframework.stereotype.Service;

/**
 * @author Ronghui Zhong
 * @description: IProgrammeService Implementation class
 *  This class encapsulates the logic related to module operations
 * @date 2024/4/4 22:10
 * @ProjectName Dyson Student Management System
 **/
@Service
public class ProgrammeServiceImpl extends ServiceImpl<ProgrammeMapper, Programme> implements IProgrammeService {

}

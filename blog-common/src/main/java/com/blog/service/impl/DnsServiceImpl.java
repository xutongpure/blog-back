package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.Dns;
import com.blog.service.DnsService;
import com.blog.mapper.DnsMapper;
import org.springframework.stereotype.Service;

/**
* @author xuton
* @description 针对表【sg_dns】的数据库操作Service实现
* @createDate 2023-08-19 10:27:11
*/
@Service
public class DnsServiceImpl extends ServiceImpl<DnsMapper, Dns>
    implements DnsService{

}





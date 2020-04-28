package com.devin.order.mapper;

import com.devin.order.model.Order;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author Devin Zhang
 * @className OrderMapper
 * @description TODO
 * @date 2020/4/22 16:24
 */

public interface OrderMapper extends Mapper<Order>, MySqlMapper<Order> {
}

package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

/**
 * Created by liulixiang on 16/6/13.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细, 可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(long seckillId, long userPhone);

    /**
     * 根据id查询SuccessKilled,并携带Seckill
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId, long userPhone);
}

/**
 * 项目名: common
 * 文件名：SequenceEnum.java
 * 版本信息： V1.0
 * 日期：2017年3月29日
 * Copyright: Corporation 2017 版权所有
 */
package com.k12.common.plugin;

import com.arlen.common.sequence.SequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目名称：common <br>
 * 类名称：SequenceEnum <br>
 * 类描述：<br>
 * Copyright: Copyright (c) 2017 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2017年3月29日 上午9:52:32 <br>
 * 修改人：arlen<br>
 * 修改时间：2017年3月29日 上午9:52:32 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public enum SequenceEnum {

    PRODUCT((byte) 1),
    LINE((byte) 2),
    UC((byte) 3),
    UPLOAD((byte) 4),
    CLIENT_ORDER((byte) 5),
    PLATFORM_ORDER((byte) 6),
    SUPPLIER_ORDER((byte) 7),
    SERVICE_LOG((byte) 8);

    private final static Logger logger = LoggerFactory.getLogger(SequenceEnum.class);

    private byte value;

    private SequenceEnum(byte value) {
        this.value = value;
    }

    public int getID() {
        int seq = SequenceUtil.getNextId(this.value);
        logger.info("======= Get sequence, key: " + this.value + "; value: " + seq);
        return seq;
    }
}

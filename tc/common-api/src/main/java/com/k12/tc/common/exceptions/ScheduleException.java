package com.k12.tc.common.exceptions;

import com.dexcoder.commons.exceptions.DexcoderException;

/**
 * 项目名称：tc 
 * 类名称：ScheduleException 
 * 类描述：自定义异常
 * 类描述：ScheduleException。 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2016年6月23日 下午4:48:35 
 * 修改人：孙海滨 
 * 修改时间：2016年6月23日 下午4:48:35 
 * 修改备注：
 * @version 1.0*
 */
public class ScheduleException extends DexcoderException {

    /** serialVersionUID */
    private static final long serialVersionUID = -1921648378954132894L;

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
        super(e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public ScheduleException(String code, String message) {
        super(code, message);
    }
}

package com.mildlamb.myhandler;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mildlamb.entities.CommonResult;

public class CustomerBlockHandler implements InitFunc {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(9527,"用户自定义处理方法，Global handlerException ........... 1");
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(9527,"用户自定义处理方法，Global handlerException ........... ");
    }

    @Override
    public void init() throws Exception {

    }
}

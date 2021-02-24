package handler;

import commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/*统一处理异常类*/

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }


    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了特定异常");
    }

    @ExceptionHandler(DikunExceptionHandler.class)
    @ResponseBody
    public R error(DikunExceptionHandler e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }
}

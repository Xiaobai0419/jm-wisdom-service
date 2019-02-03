package com.sunfield.microframe.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 异常工具类
 * @author wangnan
 *
 */
public class ExceptionUtils {

	/**
	 * 将异常信息转化为字符串
	 * @param e 异常信息
	 * @return
	 */
	public static String getException(Throwable e){
		if(e == null) {
            return null;
        }
		
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try{
	        e.printStackTrace(new PrintStream(baos));
	    }finally{
	        try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
	    return baos.toString();
	}
	
}

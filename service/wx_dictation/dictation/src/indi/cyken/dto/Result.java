package indi.cyken.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 与网页端的通信协议
 * @Copyright: 2017 www.fallsea.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:29:07
 */
public class Result implements Serializable
{

    private static final long serialVersionUID = -5063527180151987941L;

    private int errorNo;

    private String errorInfo;

    private Map<String, Object> results = null;

    public Result()
    {
        this.errorNo = 0;
    }

    /**
     * @Description:
     * @author: fallsea
     * @date: 2017年10月22日 下午8:29:29
     * @param errorNo 错误码
     * @param errorInfo 错误消息
     */
    public Result(int errorNo, String errorInfo)
    {
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }


    public int getErrorNo()
    {
        return errorNo;
    }

    public void setErrorNo(int errorNo)
    {
        this.errorNo = errorNo;
    }

    public String getErrorInfo()
    {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public Map<String, Object> getResults()
    {
        return results;
    }

    /**
     * @Description: 获取第一个集合
     * @author: fallsea
     * @date: 2017年10月22日 下午8:29:43
     * @return
     */
    
    public Object MyGetResult()
    {
        if( null != this.results)
        {
            Set<String> set = this.results.keySet();
            if(null!=set && !set.isEmpty())
            {
                Iterator<String> iter = set.iterator();
                if ( iter.hasNext() )
                {
                    String key = iter.next();
                    return MyGetResult(key);
                }
            }
        }
        return null;
    }

    /**
     * @Description: 获取属性集合
     * @author: fallsea
     * @date: 2017年10月22日 下午8:29:51
     * @param dsName
     * @return
     */

    public Object MyGetResult(String dsName)
    {
        if( null != getResults())
        {
            return getResults().get(dsName);
        }
        return null;
    }



    /**
     * @Description: 当一个 接口只返回一个结果集时，可调用此方法
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:28
     * @param object
     */
    public void setResult(Object object)
    {
        this.setResult("data", object);
    }

    /**
     * @Description: 多个结果集需使用此方法，前台根据结果集名称获取不同的数据
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:42
     * @param name
     * @param object
     */
    public void setResult(String name, Object object)
    {
        if(null == this.results)
        {
            this.results = new HashMap<String, Object>();
        }
        this.results.put(name, object);
    }
}
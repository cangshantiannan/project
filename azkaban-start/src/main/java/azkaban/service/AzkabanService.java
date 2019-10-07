package azkaban.service;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

 public interface AzkabanService {

	 /**
	  * getSessionId:初始化azkaban连接
	  * @author Administrator  
	  * @return
	  * @throws Exception  
	  * @since JDK 1.7
	  */
	 String getSessionId() throws Exception;

    
	 /**
	  * 
	  * createProject:创建一个项目
	  * @author Administrator  
	  * @param projectName 项目名称
	  * @param description 项目描述
	  * @return
	  * @throws Exception  
	  * @since JDK 1.7
	  */
	 String createProject(String projectName, String description) throws Exception;

    /**
     * Azkaban 删除 project
     *
     * @param projectName project名称
     * @throws Exception
     * @author wuzy
     * @date 2017年12月21日
     */
     String deleteProject(String projectName) throws Exception;

    /**
     * Azkaban 上传 zip文件
     *
     * @param projectName
     * @param file
     * @return projectId
     * @throws Exception
     * @author wuzy
     * @date 2017年12月21日
     */
     String uploadZip(String projectName, File file) throws Exception;

    /**
     * 下载 Azkaban 压缩文件
     *
     * @param projectName
     * @param zipPath
     * @throws Exception
     * @author wuzy
     * @date 2017年12月22日
     */
     String downLoadZip(String projectName, String zipPath) throws Exception;


    /**
     * Fetch Flows of a Project 获取一个project的流ID
     *
     *
     */
     String fetchProjectFlows(String projectName)throws Exception;

    /**
     * Fetch Jobs of a Flow 获取一个job的流结构 依赖关系
     */
      String fetchFlowGraph(String projectName, String flowId)throws Exception;

    /**
     * Fetch Executions of a Flow 获取指定 project 的执行列表
     *
     */
     String fetchFlowExecutions(String projectName, String flowId, Integer startOffset, Integer lenth)throws Exception;

    /**
     * Fetch Running Executions of a Flow 获取正在运行的执行信息(execution)
     *
     */
     String getRunningExcutionsForFlow(String projectName, String flowId)throws Exception;

    /**
     * Execute a Flow 执行一个流 还有很多其他参数 具体参考api
     *
     */
     String executeFlow(String projectName, String flowId, Map<String, String> flowParamMap)throws Exception;

    /**
     * Cancel a Flow Execution 中断一个执行流
     *
     */
     String cancelFlow(String execId)throws Exception;


    /**
     * 根据时间 创建调度任务
     * Schedule a period-based Flow 创建调度任务
     *
     * @param projectId
     * @param projectName
     * @param flow
     * @param flowName
     * @param recurring   是否循环，on循环
     * @param period      循环频率： M Months，w Weeks，d Days，h Hours，m Minutes，s Seconds；如60s，支持分钟的倍数
     * @param date        开始时间
     * @return 返回scheduleId
     * @throws Exception
     * @author wuzy
     * @date 2017年12月21日
     */
     String scheduleFlowByPeriod(String projectId, String projectName, String flow, String flowName, String recurring, String period, Date date) throws Exception;

    /**
     * 根据cron表达式 创建调度任务
     *
     * @param projectName
     * @param cron
     * @param flow
     * @param flowName
     * @return 返回scheduleId
     * @throws Exception
     * @author wuzy
     * @date 2017年12月21日
     */
     String scheduleFlowByCron(String projectName, String cron, String flow, String flowName) throws Exception;

    /**
     * 根据 scheduleId 取消一个流的调度
     *
     * @param scheduleId
     * @throws Exception
     * @author wuzy
     * @date 2017年12月21日
     */
     String unscheduleFlow(String scheduleId) throws Exception;

    /**
     * Fetch a Schedule 获取一个调度器job的信息 根据project的id 和 flowId
     *
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
     String fetchScheduleInfo(String projectId, String flowId)throws Exception;

    /**
     * Set a SLA 设置调度任务 执行的时候 或者执行成功失败等等的规则匹配 发邮件或者...
     *
     */
     String setScheduleFlowSLA(String scheduleId)throws Exception;

    /**
     * Fetch a SLA 获取调度的规则配置
     *
     */
     String fetchScheduleSLAInfo(String scheduleId)throws Exception;

    /**
     * Pause a Flow Execution 暂停一个执行流
     *
     */
     String pauseFlow(String execId)throws Exception;

    /**
     * Resume a Flow Execution 重新执行一个执行流
     *
     */
     String resumeFlow(String execId)throws Exception;

    /**
     * Fetch a Flow Execution 获取一个执行流的详细信息 这个流的每个节点的信息 成功或者失败等等
     *
     */
     String fetchexecflow(String execId)throws Exception;

    /**
     * Fetch Execution Job Logs 获取一个执行流的日志
     *
     */
     String fetchFlowLog(String execId, String jobId, Integer offset, Integer length)throws Exception;

    /**
     * Fetch Flow Execution Updates 获取执行流的信息状态
     *
     */
     String fetchFlowUpdate(String execId)throws Exception;

}
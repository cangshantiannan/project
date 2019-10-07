package azkaban.service.Impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import javax.annotation.PostConstruct;
import azkaban.service.AzkabanService;
import azkaban.util.Operator;
import azkaban.util.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import lombok.Data;


/**
 * @ClassName: AzkabanServiceImpl
 * @Function:  azkaban 接口调用
 * @Date:      2019/9/23 0:35
 * @author     wangyl
 * @version    V1.0
 */
@Service
@Data
public class AzkabanServiceImpl implements AzkabanService {

	@Value("${wyl.azkaban.api.url}")
	private String url;
	@Value("${wyl.azkaban.api.user}")
	private String user;
	@Value("${wyl.azkaban.api.password}")
	private String password;

	@Autowired
	private RestTemplate restTemplate;
	public String SESSION = null;
	static HttpHeaders hs = new HttpHeaders();
	static {
		hs.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		hs.add("X-Requested-With", "XMLHttpRequest");
		hs.add("Accept", "text/plain;charset=utf-8");
	}

	@PostConstruct
    @Override
	public String getSessionId() {
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>(2);
		linkedMultiValueMap.add(Parameter.ACTION, Operator.LOGIN);
		linkedMultiValueMap.add(Parameter.USERNAME, user);
		linkedMultiValueMap.add(Parameter.PASSWORD, password);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		Map postForObject = restTemplate.postForObject(url, httpEntity, Map.class);
        assert postForObject != null;
        SESSION = postForObject.get("session.id").toString();
		return SESSION;
	}

    @Override
	public String createProject(String projectName, String description) throws Exception {
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.PROJECTNAME, projectName);
		linkedMultiValueMap.add(Parameter.DESCRIPTION, description);
		linkedMultiValueMap.add(Parameter.ACTION, Operator.CREATE);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		String postForObject = restTemplate.postForObject(url + "/" + Operator.MANAGER, httpEntity, String.class);
		return postForObject;
	}

    @Override
	public String deleteProject(String projectName) throws Exception {

		Map<String, String> linkedMultiValueMap = new HashMap<String, String>(3);
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.PROJECT, projectName);
		linkedMultiValueMap.put(Parameter.DELETE, Parameter.TRUE);
		ResponseEntity<String> postForObject = restTemplate.exchange(
				getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.MANAGER), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		if (200 != postForObject.getStatusCodeValue()) {
			throw new Exception("删除Azkaban Project失败");
		}
		return postForObject.toString();
	}

    @Override
	public String uploadZip(String projectName, File file) throws Exception {
		FileSystemResource resource = new FileSystemResource(file);
		LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<String, Object>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.AJAX, Parameter.UPLOAD);
		linkedMultiValueMap.add(Parameter.PROJECT, projectName);
		linkedMultiValueMap.add(Parameter.FILE, resource);
		String result = restTemplate.postForObject(url + "/" + Operator.MANAGER, linkedMultiValueMap, String.class);
		return result;
	}

    @Override
	public String downLoadZip(String projectName, String zipPath) throws Exception {

		return null;
	}

    @Override
	public String fetchProjectFlows(String projectName) {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.PROJECT, projectName);
		linkedMultiValueMap.put(Parameter.AJAX,Operator.FETCHPROJECTFLOWS);
		ResponseEntity<String> result = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.MANAGER), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		return result.getBody().toString();
	}

    @Override
	public String fetchFlowGraph(String projectName, String flowId) {

		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.PROJECT, projectName);
		linkedMultiValueMap.put(Parameter.AJAX, Operator.FETCHFLOWGRAPH);
		linkedMultiValueMap.put(Parameter.FLOW, flowId);

		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.MANAGER), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		return exchange.getBody();
	}

    @Override
	public String fetchFlowExecutions(String projectName, String flowId, Integer startOffset, Integer lenth) {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.PROJECT, projectName);
		linkedMultiValueMap.put(Parameter.FLOW, flowId);
		linkedMultiValueMap.put(Parameter.START, String.valueOf(startOffset));
		linkedMultiValueMap.put(Parameter.LENGTH, String.valueOf(lenth));
		linkedMultiValueMap.put(Parameter.AJAX, Operator.FETCHFLOWEXECUTIONS);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.MANAGER), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		return exchange.getBody().toString();
	}

    @Override
	public String getRunningExcutionsForFlow(String projectName, String flowId) {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.AJAX, Operator.GETRUNNING);
		linkedMultiValueMap.put(Parameter.PROJECT, projectName);
		linkedMultiValueMap.put(Parameter.FLOW, flowId);
		ResponseEntity<String> exchange = restTemplate.exchange(
				getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return exchange.getBody();
	}

	@Override
    public String executeFlow(String projectName, String flowId, Map<String, String> flowParamMap) {
		LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.AJAX, Operator.EXECUTEFLOW);
		linkedMultiValueMap.add(Parameter.PROJECT, projectName);
		linkedMultiValueMap.add(Parameter.FLOW, flowId);
		if(flowParamMap!=null)
		{
			flowParamMap.forEach((k, v) -> {
				linkedMultiValueMap.add("flowOverride[" + k + "]", v);
			});
		}
		
		String respMap = restTemplate.postForObject(url + "/" + Operator.EXECUTOR, linkedMultiValueMap, String.class);
		System.out.println(respMap);
		return respMap;
	}

    @Override
	public String cancelFlow(String execId) {
		Map<String, String> map = new HashMap<>(2);
		map.put(Parameter.SESSION, SESSION);
		map.put(Parameter.EXECID, execId);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(map, url + "/" + Operator.EXECUTOR),
				HttpMethod.GET, new HttpEntity<String>(hs), String.class, map);
		String body = exchange.getBody();
		System.out.println(body);
		return body;
	}

    @Override
	@Deprecated
	public String scheduleFlowByPeriod(String projectId, String projectName, String flow, String flowName,
			String recurring, String period, Date date){
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>(10);
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.AJAX, Operator.SCHEDULEFLOW);
		linkedMultiValueMap.add(Parameter.PROJECT_NAME, projectName);
		linkedMultiValueMap.add(Parameter.PROJECTID, projectId);
		linkedMultiValueMap.add(Parameter.FLOW, flow);
		linkedMultiValueMap.add(Parameter.FLOWNAME, flowName);
		linkedMultiValueMap.add(Parameter.IS_RECURRING, recurring);
		linkedMultiValueMap.add(Parameter.PERID, period);
		scheduleTimeInit(linkedMultiValueMap, date);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		String result = restTemplate.postForObject(url + "/" + Operator.SCHEDULE, httpEntity, String.class);
		return result;
	}

    @Override
	public String scheduleFlowByCron(String projectName, String cron, String flow, String flowName) throws Exception {
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.AJAX, Operator.SCHEDULECRONFLOW);
		linkedMultiValueMap.add(Parameter.PROJECT_NAME, projectName);
		linkedMultiValueMap.add(Parameter.CRONEXPRESSION, cron);
		linkedMultiValueMap.add(Parameter.FLOW, flow);
		linkedMultiValueMap.add("flowOverride["+"ui_test"+"]","123");
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		String result = restTemplate.postForObject(url + "/" + Operator.SCHEDULE, httpEntity, String.class);
		System.out.println(result);
		return null;
	}

    @Override
	public String unscheduleFlow(String scheduleId) throws Exception {
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.ACTION, Operator.REMOVESCHED);
		linkedMultiValueMap.add(Parameter.SCHEDULEID, scheduleId);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		String result = restTemplate.postForObject(url + "/" + Operator.SCHEDULE, httpEntity, String.class);
		System.out.println(result);
		return result;
	}

    @Override
	public String fetchScheduleInfo(String projectId, String flowId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.PROJECTID, projectId);
		linkedMultiValueMap.put(Parameter.FLOWID, flowId);
		linkedMultiValueMap.put(Parameter.AJAX,Operator.FETCHSCHEDULE);
		ResponseEntity<String> exchange = restTemplate.exchange(
				getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.SCHEDULE), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		
		System.out.println(exchange.getBody());
		return null;
	}

    @Override
	public String setScheduleFlowSLA(String scheduleId) throws Exception {
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>();
		linkedMultiValueMap.add(Parameter.SESSION, SESSION);
		linkedMultiValueMap.add(Parameter.AJAX, Operator.SETSLA);
		linkedMultiValueMap.add(Parameter.SCHEDULEID, scheduleId);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
		String postForObject = restTemplate.postForObject(url + "/" + Operator.SCHEDULE, httpEntity, String.class);
		System.out.println(postForObject);
		return null;
	}

    @Override
	public String fetchScheduleSLAInfo(String scheduleId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.SCHEDULEID, scheduleId);
		linkedMultiValueMap.put(Parameter.AJAX, Operator.SLAINFO);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.SCHEDULE), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return null;
	}

    @Override
	public String pauseFlow(String execId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.EXECID, execId);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return null;
	}

    @Override
	public String resumeFlow(String execId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.EXECID, execId);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return null;
	}

    @Override
	public String fetchexecflow(String execId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.EXECID, execId);
		linkedMultiValueMap.put(Parameter.AJAX, Operator.FETCHEXECFLOW);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		String resp = exchange.getBody();
		System.out.println(resp);
		return null;
	}

    @Override
	public String fetchFlowLog(String execId, String jobId, Integer offset, Integer length) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.EXECID, execId);
		linkedMultiValueMap.put(Parameter.JOBID, jobId);
		linkedMultiValueMap.put(Parameter.AJAX, Operator.FETCHEXECJOBLOGS);
		linkedMultiValueMap.put(Parameter.OFFSET, String.valueOf(offset));
		linkedMultiValueMap.put(Parameter.LENGTH, String.valueOf(length));
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return exchange.getBody();
	}

    @Override
	public String fetchFlowUpdate(String execId) throws Exception {
		Map<String, String> linkedMultiValueMap = new HashMap<String, String>();
		linkedMultiValueMap.put(Parameter.SESSION, SESSION);
		linkedMultiValueMap.put(Parameter.EXECID, execId);
		linkedMultiValueMap.put(Parameter.LASTUPDATETIME, "-1");
		linkedMultiValueMap.put(Parameter.AJAX, Operator.FETCHEXECFLOWUPDATE);
		ResponseEntity<String> exchange = restTemplate.exchange(getAzkabanUrl(linkedMultiValueMap, url + "/" + Operator.EXECUTOR), HttpMethod.GET,
				new HttpEntity<String>(hs), String.class, linkedMultiValueMap);
		System.out.println(exchange.getBody());
		return null;
	}

	private void scheduleTimeInit(LinkedMultiValueMap<String, String> linkedMultiValueMap, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1;
		Integer day = calendar.get(Calendar.DATE);
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute = calendar.get(Calendar.MINUTE);
		linkedMultiValueMap.add("scheduleTime", hour + "," + minute + (hour > 11 ? ",pm,PDT" : ",am,EDT"));
		linkedMultiValueMap.add("scheduleDate", month + "/" + day + "/" + year);
	}

	/**
	 * 生成访问的url 
	 * @param par map参数
	 * @param url 基础url
	 * @return
	 */
	private String getAzkabanUrl(Map<String, String> par, String url) {
		url += "?";
		for (String key : par.keySet()) {
			url += key + "=" + "{" + key + "}&";
		}
		return url.substring(0, url.length() - 1);
	}

}
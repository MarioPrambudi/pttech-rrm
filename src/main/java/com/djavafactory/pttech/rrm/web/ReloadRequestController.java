package com.djavafactory.pttech.rrm.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.util.DateUtil;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "reloadrequests", formBackingObject = ReloadRequest.class)
@RequestMapping("/reloadrequests")
@Controller
public class ReloadRequestController extends BaseController {

	private final String resourcePrefix = "reload_request_status_";

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		return findReloadRequestsByParam(page, size, null, null, null, null, uiModel);
	}

	@RequestMapping(value = "/findReloadRequestsByParam", method = { RequestMethod.GET, RequestMethod.POST })
	public String findReloadRequestsByParam(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "serviceProviderId", required = false) String serviceProviderId,
			@RequestParam(value = "requestedTimeFrom", required = false) String requestedTimeFromStr,
			@RequestParam(value = "requestedTimeTo", required = false) String requestedTimeToStr, Model uiModel) {
		int sizeNo = size == null ? 10 : size.intValue();
		Date requestedTimeFrom = DateUtil.smartConvertStringToDate(requestedTimeFromStr);
		Date requestedTimeTo = DateUtil.smartConvertStringToDate(requestedTimeToStr);
		requestedTimeFrom = requestedTimeFrom == null ? null : DateUtil.resetTimeToMinimum(requestedTimeFrom);
		requestedTimeTo = requestedTimeTo == null ? null : DateUtil.resetTimeToMaximum(requestedTimeTo);
		List<ReloadRequest> reloadRequests = ReloadRequest.findReloadRequestsByParam(status, serviceProviderId,
				requestedTimeFrom, requestedTimeTo, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo,
				"reloadrequest.transId").getResultList();
		uiModel.addAttribute("reloadrequests", regenerateList(reloadRequests));
		if (page != null || size != null) {
			float nrOfPages = (float) ReloadRequest.countReloadRequestsByParam(status, serviceProviderId, requestedTimeFrom,
					requestedTimeTo) / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
			uiModel.addAttribute("params", "&status=" + status + "&serviceProviderId=" + serviceProviderId
					+ "&requestedTimeFrom=" + requestedTimeFromStr + "&requestedTimeTo=" + requestedTimeToStr);
		}
		addDateTimeFormatPatterns(uiModel);
		return "reloadrequests/list";
	}

	@ModelAttribute("statuscode")
	public Collection<Map<String, String>> populateStatusCodes() {
		List<Map<String, String>> statusList = new ArrayList<Map<String, String>>();
		Map<String, String> statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_NEW);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_NEW));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_PENDING);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_PENDING));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_FAILED);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_FAILED));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_EXPIRED);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_EXPIRED));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_MANUALCANCEL);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_MANUALCANCEL));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_STATUS_SUCCESS);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_STATUS_SUCCESS));
		statusList.add(statusMap);
		return statusList;
	}

	/**
	 * Turn status codes into user-friendly status texts
	 * 
	 * @param reloadRequestList
	 * @return reloadRequestList
	 */
	public List<ReloadRequest> regenerateList(List<ReloadRequest> reloadRequestList) {
		Iterator<ReloadRequest> reloadRequestIterator = reloadRequestList.iterator();
		while (reloadRequestIterator.hasNext()) {
			ReloadRequest reloadRequest = reloadRequestIterator.next();
			String status = reloadRequest.getStatus();
			if (status != null && !status.isEmpty())
				reloadRequest.setStatus(getResourceText(resourcePrefix + status));
		}
		return reloadRequestList;
	}
	
    void addDateTimeFormatPatterns(Model uiModel) {
    	String dateTimeFormat = getResourceText("date_time_display_format");
        uiModel.addAttribute("reloadRequest_minrequestedtime_date_format", dateTimeFormat);
        uiModel.addAttribute("reloadRequest_requestedtime_date_format", dateTimeFormat);
        uiModel.addAttribute("reloadRequest_modifiedtime_date_format", dateTimeFormat);
        uiModel.addAttribute("reloadRequest_maxrequestedtime_date_format", dateTimeFormat);
    }
    

}

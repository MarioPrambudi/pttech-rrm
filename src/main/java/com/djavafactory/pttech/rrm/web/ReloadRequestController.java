package com.djavafactory.pttech.rrm.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
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

	@RequestMapping(value = "/findReloadRequestsByParam", method = RequestMethod.POST)
	public String findReloadRequestsByParam(@RequestParam(value = "status", required = false) String status, Model uiModel) {
		uiModel.addAttribute(
				"reloadrequests",
				regenerateList(ReloadRequest.findReloadRequestsByParam(status, -1, -1, "reloadrequest.transId").getResultList()));
		addDateTimeFormatPatterns(uiModel);
		return "reloadrequests/list";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			List<ReloadRequest> reloadRequestList = ReloadRequest.findReloadRequestsByParam(null,
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo, "reloadrequest.transId").getResultList();
			uiModel.addAttribute("reloadrequests", regenerateList(reloadRequestList));
			float nrOfPages = (float) ReloadRequest.countReloadRequests() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("reloadrequests",
					regenerateList(ReloadRequest.findReloadRequestsByParam(null, -1, -1, "reloadrequest.transId")
							.getResultList()));
		}
		addDateTimeFormatPatterns(uiModel);
		return "reloadrequests/list";
	}

	@ModelAttribute("statuscode")
	public Collection<Map<String, String>> populateStatusCodes() {
		List<Map<String, String>> statusList = new ArrayList<Map<String, String>>();
		Map<String, String> statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_REQUEST_NEW);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_REQUEST_NEW));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_REQUEST_FAILED);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_REQUEST_FAILED));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_REQUEST_EXPIRED);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_REQUEST_EXPIRED));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_REQUEST_MANUALCANCEL);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_REQUEST_MANUALCANCEL));
		statusList.add(statusMap);

		statusMap = new TreeMap<String, String>();
		statusMap.put("id", Constants.RELOAD_REQUEST_SUCCESS);
		statusMap.put("value", getResourceText(resourcePrefix + Constants.RELOAD_REQUEST_SUCCESS));
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
}

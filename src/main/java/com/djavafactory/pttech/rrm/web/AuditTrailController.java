package com.djavafactory.pttech.rrm.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.AuditTrail;
import com.djavafactory.pttech.rrm.mongorepository.AuditTrailRepository;
import com.djavafactory.pttech.rrm.util.DateUtil;
import com.djavafactory.pttech.rrm.util.PageableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/audittrails")
@Controller
public class AuditTrailController extends BaseController {

	@Autowired
	AuditTrailRepository auditTrailRepository;

	private final String resourcePrefix = "audit_trail_action_";

	@RequestMapping(value = "/findAuditTrailsByParam", method = RequestMethod.POST)
	public String findAuditTrailsByParam(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "dateFrom", required = false) String dateFromStr,
			@RequestParam(value = "dateTo", required = false) String dateToStr,
			@RequestParam(value = "entity", required = false) String entity,
			@RequestParam(value = "action", required = false) String action, Model uiModel) {
		Date dateFrom = DateUtil.smartConvertStringToDate(dateFromStr);
		Date dateTo = DateUtil.smartConvertStringToDate(dateToStr);
		List<AuditTrail> auditTrailList = regenerateList(auditTrailRepository.findByParam(
				dateFrom == null ? null : DateUtil.resetTimeToMinimum(dateFrom),
				dateTo == null ? null : DateUtil.resetTimeToMaximum(dateTo), entity, action));
		uiModel.addAttribute("audittrails", auditTrailList);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			float nrOfPages = (float) auditTrailList.size() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		}
		return "audittrails/list";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Pageable pageable = new PageableImpl(page, size, 0, null);
		Page<AuditTrail> auditTrailPage = auditTrailRepository.findAll(pageable);
		List<AuditTrail> auditTrailList = new ArrayList<AuditTrail>();
		Iterator<AuditTrail> iter = auditTrailPage.iterator();
		while (iter.hasNext()) {
			auditTrailList.add(iter.next());
		}
		uiModel.addAttribute("audittrails", auditTrailList);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			float nrOfPages = (float) auditTrailRepository.count() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		}
		return "audittrails/list";
	}

	@ModelAttribute("action")
	public Collection<Map<String, String>> populateActions() {
		List<Map<String, String>> actionList = new ArrayList<Map<String, String>>();
		Map<String, String> actionMap = new TreeMap<String, String>();
		actionMap.put("id", Constants.AUDIT_TRAIL_ACTION_PERSIST);
		actionMap.put("value", getResourceText(resourcePrefix + Constants.AUDIT_TRAIL_ACTION_PERSIST));
		actionList.add(actionMap);

		actionMap = new TreeMap<String, String>();
		actionMap.put("id", Constants.AUDIT_TRAIL_ACTION_MERGE);
		actionMap.put("value", getResourceText(resourcePrefix + Constants.AUDIT_TRAIL_ACTION_MERGE));
		actionList.add(actionMap);

		actionMap = new TreeMap<String, String>();
		actionMap.put("id", Constants.AUDIT_TRAIL_ACTION_REMOVE);
		actionMap.put("value", getResourceText(resourcePrefix + Constants.AUDIT_TRAIL_ACTION_REMOVE));
		actionList.add(actionMap);

		return actionList;
	}

	public List<AuditTrail> regenerateList(List<AuditTrail> auditTrailList) {
		Iterator<AuditTrail> auditTrailIterator = auditTrailList.iterator();
		while (auditTrailIterator.hasNext()) {
			AuditTrail auditTrail = auditTrailIterator.next();
			String action = auditTrail.getAction();
			if (action != null && !action.isEmpty()) {
				try {
					auditTrail.setAction(getResourceText(resourcePrefix + action));
				} catch (Exception x) {
					auditTrail.setAction(action);
				}
			}
		}
		return auditTrailList;
	}

}

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
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.djavafactory.pttech.rrm.mongorepository.AuditTrailRepository;
import com.djavafactory.pttech.rrm.util.DateUtil;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/audittrails")
@Controller
public class AuditTrailController extends BaseController {

	@Autowired
	AuditTrailRepository auditTrailRepository;

	private final String resourcePrefix = "audit_trail_action_";

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		return findAuditTrailsByParam(page, size, null, null, null, null, uiModel);
	}

	@RequestMapping(value = "/findAuditTrailsByParam", method = { RequestMethod.GET, RequestMethod.POST })
	public String findAuditTrailsByParam(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "entity", required = false) String entity,
			@RequestParam(value = "dateFrom", required = false) String dateFromStr,
			@RequestParam(value = "dateTo", required = false) String dateToStr, Model uiModel) {
		Date dateFrom = DateUtil.smartConvertStringToDate(dateFromStr);
		Date dateTo = DateUtil.smartConvertStringToDate(dateToStr);
		dateFrom = dateFrom == null ? null : DateUtil.resetTimeToMinimum(dateFrom);
		dateTo = dateTo == null ? null : DateUtil.resetTimeToMaximum(dateTo);
		List<AuditTrail> auditTrailList = regenerateList(auditTrailRepository.findByParam(dateFrom, dateTo, entity, action,
				page == null ? 1 : page, size == null ? 10 : size));
		uiModel.addAttribute("audittrails", auditTrailList);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			float nrOfPages = (float) auditTrailRepository.countByParam(dateFrom, dateTo, entity, action) / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
			uiModel.addAttribute("params", "&action=" + action + "&entity=" + entity + "&dateFrom=" + dateFromStr + "&dateTo="
					+ dateToStr);
		}
		addDateTimeFormatPatterns(uiModel);
		return "audittrails/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") ObjectId id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("audittrail", regenerateShow(auditTrailRepository.findOne(id)));
		uiModel.addAttribute("itemId", id);
		addDateTimeFormatPatterns(uiModel);
		return "audittrails/show";
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

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("auditTrail_performedat_date_format", getResourceText("date_time_display_format"));
	}
	
    /**
	 * display presentable source info on show details.
	 * @param auditTrail AuditTrail
	 * @return auditTrail
	 */
	public AuditTrail regenerateShow(AuditTrail auditTrail) {
		String action = auditTrail.getAction();
		if (action != null && !action.isEmpty()) {
			try {		
				 auditTrail.setAction(getResourceText(resourcePrefix + action));
			} catch (Exception x) {
				 auditTrail.setAction(action);
			}	
		}
		return auditTrail;
	}

}

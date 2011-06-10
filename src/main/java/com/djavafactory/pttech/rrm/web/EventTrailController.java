package com.djavafactory.pttech.rrm.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.domain.EventTrail;
import com.djavafactory.pttech.rrm.mongorepository.EventTrailRepository;
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

@RequestMapping("/eventtrails")
@Controller
public class EventTrailController extends BaseController{
	private final String resourcePrefix = "event_trail_source_";
	
	@Autowired
	EventTrailRepository eventTrailRepository;
	
	@RequestMapping(value = "/findEventTrailsByParam", method = { RequestMethod.GET, RequestMethod.POST })
	public String findEventTrailsByParam(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "dateFrom", required = false) String dateFromStr,
			@RequestParam(value = "dateTo", required = false) String dateToStr, Model uiModel) {
		Date dateFrom = null;
		Date dateTo = null;
		if (dateFromStr != null)dateFrom = DateUtil.smartConvertStringToDate(dateFromStr);
		if (dateToStr != null)dateTo = DateUtil.smartConvertStringToDate(dateToStr);		
		dateFrom = dateFrom == null ? null : DateUtil.resetTimeToMinimum(dateFrom);
		dateTo = dateTo == null ? null : DateUtil.resetTimeToMaximum(dateTo);
		List<EventTrail> eventTrailList = regenerateList(eventTrailRepository.findByParam(dateFrom, dateTo, source, code, message, page == null ? 1 : page, size == null ? 10 : size));
		uiModel.addAttribute("eventtrails", eventTrailList);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			float nrOfPages = (float) eventTrailRepository.countByParam(dateFrom, dateTo, source, code, message) / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1: nrOfPages));
			uiModel.addAttribute("params", "&source=" + source + "&code=" + code + "&message=" + message + "&dateFrom=" + dateFromStr + "&dateTo="+ dateToStr);
		}
		addDateTimeFormatPatterns(uiModel);
		return "eventtrails/list";
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		return findEventTrailsByParam(page, size, null, null, null, null, null, uiModel);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") ObjectId id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("eventtrail", regenerateShow(eventTrailRepository.findOne(id)));
		uiModel.addAttribute("itemId", id);
		addDateTimeFormatPatterns(uiModel);
		return "eventtrails/show";
	}
	
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("eventTrail_date_date_format", getResourceText("date_time_display_format"));
	}
	
	@ModelAttribute("source")
	public Collection<Map<String, String>> populateSouces() {
		List<Map<String, String>> sourceList = new ArrayList<Map<String, String>>();
		Map<String, String> sourceMap = new TreeMap<String, String>();
		sourceMap.put("id", Constants.EVENT_TRAIL_SOURCE_RRM);
		sourceMap.put("value", getResourceText(resourcePrefix + Constants.EVENT_TRAIL_SOURCE_RRM));
		sourceList.add(sourceMap);

		sourceMap = new TreeMap<String, String>();
		sourceMap.put("id", Constants.EVENT_TRAIL_SOURCE_RMI);
		sourceMap.put("value", getResourceText(resourcePrefix + Constants.EVENT_TRAIL_SOURCE_RMI));
		sourceList.add(sourceMap);

		sourceMap = new TreeMap<String, String>();
		sourceMap.put("id", Constants.EVENT_TRAIL_SOURCE_RTM);
		sourceMap.put("value", getResourceText(resourcePrefix + Constants.EVENT_TRAIL_SOURCE_RTM));
		sourceList.add(sourceMap);

		return sourceList;
	}
	
	public List<EventTrail> regenerateList(List<EventTrail> eventTrailList) {
		Iterator<EventTrail> eventTrailIterator = eventTrailList.iterator();
		while (eventTrailIterator.hasNext()) {
			EventTrail eventTrail = eventTrailIterator.next();
			String source = eventTrail.getSource();
			if (source != null && !source.isEmpty()) {
				try {
					eventTrail.setSource(getResourceText(resourcePrefix + source));
				} catch (Exception x) {
					eventTrail.setSource(source);
				}
			}
		}
		return eventTrailList;
	}
	
    /**
	 * display presentable source info on show details.
	 * @param eventTrail EventTrail 
	 * @return eventTrail 
	 */
	public EventTrail regenerateShow(EventTrail eventTrail) {
		String source = eventTrail.getSource();
		if (source != null && !source.isEmpty()) {
			try {			
				eventTrail.setSource(getResourceText(resourcePrefix + source));
			} catch (Exception x) {
				eventTrail.setSource(source);
			}	
		}
		return eventTrail;
	}
}

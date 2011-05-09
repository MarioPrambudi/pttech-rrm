package com.djavafactory.pttech.rrm.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.djavafactory.pttech.rrm.domain.AuditTrail;
import com.djavafactory.pttech.rrm.domain.mongorepository.AuditTrailRepository;
import com.djavafactory.pttech.rrm.util.PageableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/audittrails")
@Controller
public class AuditTrailController {

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String getAuditTrailList(@RequestParam(value = "page", required = false) Integer page,
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

}

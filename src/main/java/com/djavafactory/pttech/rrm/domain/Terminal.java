package com.djavafactory.pttech.rrm.domain;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.djavafactory.pttech.rrm.domain.Acquirer;
import javax.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Value;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findTerminalsByTerminalIdLikeOrCreatedTimeBetween" })
public class Terminal {

    @NotNull
    private String terminalId;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date modifiedTime;

    private String createdBy;

    private String modifiedBy;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;

    @Value("a")
    private String deletedStatus;
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect TerminalType_Roo_ToString {
    
    public String TerminalType.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Deleted: ").append(getDeleted()).append(", ");
        sb.append("Terminal: ").append(getTerminal() == null ? "null" : getTerminal().size());
        return sb.toString();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.bus;

/**
 *
 * @author jmulutu
 */
public class MenuDTO {
    private int ID;
    private int menuID;
    private int positionID;
    private int parentID;
    private byte serviceCode;
    private String name;
    private String displayName;
    
    public MenuDTO(){}

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @return the menuID
     */
    public int getMenuID() {
        return menuID;
    }

    /**
     * @return the positionID
     */
    public int getPositionID() {
        return positionID;
    }

    /**
     * @return the parentID
     */
    public int getParentID() {
        return parentID;
    }

    /**
     * @return the serviceCode
     */
    public byte getServiceCode() {
        return serviceCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @param menuID the menuID to set
     */
    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    /**
     * @param positionID the positionID to set
     */
    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    /**
     * @param parentID the parentID to set
     */
    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    /**
     * @param serviceCode the serviceCode to set
     */
    public void setServiceCode(byte serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
    
}

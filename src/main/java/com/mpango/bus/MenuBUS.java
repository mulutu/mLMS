/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.bus;

import com.mpango.bus.MenuDTO;
import com.mpango.dao.factory.LMSDAOFactory;
import com.mpango.dao.idao.I_Menu;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jmulutu
 */
public class MenuBUS {
    
    public static List<MenuDTO> getMenusByParentID(int parentID, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Menu myMenu = factory.get_Menu();
        return myMenu.getMenusByParentID(parentID, con);
    }

    public static List<MenuDTO> getMenus(Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Menu myMenu = factory.get_Menu();
        return myMenu.getMenus(con);
    }

    public static MenuDTO getMenuByID(int menuID, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Menu myMenu = factory.get_Menu();
        return myMenu.getMenuByID(menuID, con);
    }

}

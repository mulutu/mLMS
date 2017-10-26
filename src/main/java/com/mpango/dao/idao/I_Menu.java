/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.dao.idao;

import com.mpango.bus.MenuDTO;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jmulutu
 */
public interface I_Menu {
    
    List<MenuDTO> getMenusByParentID( int parentID, Connection con);
    
    MenuDTO getMenuByID(int menuID, Connection con);
    
    List<MenuDTO> getMenus( Connection con);
}

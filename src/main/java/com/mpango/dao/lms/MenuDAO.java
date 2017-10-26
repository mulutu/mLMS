/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.bus.MenuDTO;
import com.mpango.dao.idao.I_Menu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author jmulutu
 */
public class MenuDAO implements I_Menu {

    @Override
    public List<MenuDTO> getMenusByParentID(int parentID, Connection con) {
        List<MenuDTO> menulist = new ArrayList<>();
        String SQL = "SELECT * FROM MENUS WHERE parent_id =" + parentID;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setID(rs.getInt("ID"));
                menu.setMenuID(rs.getInt("MENU_ID"));
                menu.setName(rs.getString("NAME"));
                menu.setParentID(rs.getInt("PARENT_ID"));
                menu.setPositionID(rs.getInt("POSITION_ID"));
                menu.setServiceCode(rs.getByte("SERVICE_ID"));
                menulist.add(menu);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            DbUtils.closeQuietly(con);
        }
        return menulist;
    }

    @Override
    public List<MenuDTO> getMenus(Connection con) {
        List<MenuDTO> menulist = new ArrayList<>();
        String SQL = "SELECT * FROM MENUS";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setID(rs.getInt("ID"));
                menu.setMenuID(rs.getInt("MENU_ID"));
                menu.setName(rs.getString("NAME"));
                menu.setParentID(rs.getInt("PARENT_ID"));
                menu.setPositionID(rs.getInt("POSITION_ID"));
                menu.setServiceCode(rs.getByte("SERVICE_ID"));
                menulist.add(menu);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            DbUtils.closeQuietly(con);
        }
        return menulist;
    }

    @Override
    public MenuDTO getMenuByID(int menuID, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

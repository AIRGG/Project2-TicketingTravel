/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelticketing;

import javax.swing.JTable;
import travelticketing.util.Utility;

/**
 *
 * @author AIRGG
 */
public class Transportasi {
    String idtp = "";
    String jenistp = "";
    String namatp = "";
    Integer roda = 0;
    String warna = "";
    Integer typetp = 0; // 1: Udara, 2: Air, 3: Darat
    
    Utility util = new Utility();
    
    public void Transportasi(){
        
    }
    
    public void showData(JTable tblMain){
        String sql = "SELECT * FROM transportasi";
        util.getData(tblMain, sql, true);
    }
    
    public void showData(JTable tblMain, String prmType, Boolean no) {
        String sql = "SELECT * FROM transportasi WHERE typetp="+prmType;
        util.getData(tblMain, sql, no);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelticketing;

import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import travelticketing.util.Utility;

/**
 *
 * @author AIRGG
 */
public class Transaksi {
    Utility util = new Utility();
    public void Transaksi(){
        
    }
    
    public void showData(JTable tblMain){
        String sql = "SELECT * FROM transaksi";
        util.getData(tblMain, sql, true);
    }
    public void showData(JTable tblMain, Boolean no){
        String sql = "SELECT * FROM transaksi";
        util.getData(tblMain, sql, no);
    }
    
    public Integer getLastIdx(){
        Integer idx = 1;
        JTable tbl = new JTable();
        String sql = "SELECT max(idtrn) FROM transaksi";
        util.getData(tbl, sql, false);
        if(tbl.getRowCount() == 0) return idx;
        String lastidx = tbl.getModel().getValueAt(0, 0).toString();
        return Integer.parseInt(lastidx)+1;
//System.out.println(tbl.getModel().getValueAt(0, 0).toString());
//return 1;
    }
    public void executeData(Map<String, Object> payload){
        Object act = payload.get("act");
        Integer idtrn = getLastIdx();
        Object tmpidtrn = payload.get("tmpidtrn");
        Object typetp = payload.get("typetp");
        Object namatp = payload.get("namatp");
        Object jmlpenumpang = payload.get("jmlpenumpang");
        Object klspenumpang = payload.get("klspenumpang");
        Object harga = payload.get("harga");
        Object totalharga = payload.get("totalharga");
        Object asal = payload.get("asal");
        Object tujuan = payload.get("tujuan");
        Object tglberangkat = payload.get("tglberangkat");
        Object jamberangkat = payload.get("jamberangkat");
            
        String sql = String.format("INSERT INTO transaksi(idtrn, namatp, typetp, jmlpenumpang, klspenumpang, harga, totalharga, asal, tujuan, tglberangkat, jamberangkat) "
                + "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", idtrn, namatp, typetp, jmlpenumpang, klspenumpang, harga, totalharga, asal, tujuan, tglberangkat, jamberangkat);
        if(act.equals("edit")){
            sql = String.format("UPDATE transaksi "
                + "SET namatp='%s', typetp='%s', jmlpenumpang='%s', klspenumpang='%s', harga='%s', totalharga='%s', asal='%s', tujuan='%s', tglberangkat='%s', jamberangkat='%s' WHERE idtrn='%s' ", 
                namatp, typetp, jmlpenumpang, klspenumpang, harga, totalharga, asal, tujuan, tglberangkat, jamberangkat, tmpidtrn);
        }
        if(act.equals("delete")){
            sql = String.format("DELETE FROM transaksi WHERE idtrn='%s' ", tmpidtrn);
        }
        util.executeSQL(sql);
        JOptionPane.showMessageDialog(null, "Berhasil "+act);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelticketing;

import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author AIRGG
 */
public class TransportasiAir extends Transportasi{
    
    public void TransportasiAir(){
        
    }
    
    public void showData(JTable tblMain, String prmType) {
        String sql = "SELECT * FROM transportasi WHERE typetp="+prmType;
        util.getData(tblMain, sql);
    }
    
    public void executeData(Map<String, Object> payload){
        Object act = payload.get("act");
        Object tmpidtp = payload.get("tmpidtp");
        Object idtp = payload.get("idtp");
        Object jenistp = payload.get("jenistp");
        Object namatp = payload.get("namatp");
        Object roda = payload.get("roda");
        Object warna = payload.get("warna");
        Object typetp = payload.get("typetp");
        
        String sql = String.format("INSERT INTO transportasi(idtp, jenistp, namatp, roda, warna, typetp) "
                + "VALUES('%s','%s','%s','%s','%s','%s')", idtp, jenistp, namatp, roda, warna, typetp);
        if(act.equals("edit")){
            sql = String.format("UPDATE transportasi "
                + "SET idtp='%s', jenistp='%s', namatp='%s', roda='%s', warna='%s', typetp='%s' WHERE idtp='%s' ", 
                idtp, jenistp, namatp, roda, warna, typetp, tmpidtp);
        }
        if(act.equals("delete")){
            sql = String.format("DELETE FROM transportasi WHERE idtp='%s' ", tmpidtp);
        }
        util.executeSQL(sql);
        JOptionPane.showMessageDialog(null, "Berhasil "+act);
    }
}

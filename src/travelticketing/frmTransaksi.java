/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelticketing;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import travelticketing.util.Utility;

/**
 *
 * @author AIRGG
 */
public class frmTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form frmTransaksi
     */
    TransportasiAir ta = new TransportasiAir();
    Transaksi trn = new Transaksi();
    String id = "", act = "", typetp = ""; // 1: Udara, 2: Air, 3: Darat
    String[] hidcol = {"nmpenumpang", "typetp", "idtp"}; // kolom apa yang mau di hide
    Map<String, Object> jsnData = new HashMap<>();
    Utility util = new Utility();
    public frmTransaksi() {
        initComponents();
        runAwal();
    }
    void hitTotal(){
        String harga = txtHarga.getText();
        String jmlpm = txtJmlPenumpang.getText();
        
        Integer total = 0;
        try{
            total = Integer.parseInt(harga)*Integer.parseInt(jmlpm);
        }catch(Exception ex){
            System.out.println(ex);
        }
        Double amount = Double.parseDouble(total.toString());
        lblHarga.setText(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(amount));
        
    }
    void toCb(){
        JTable tbl = new JTable();
        ta.showData(tbl, typetp, false);
        List<String> arr = new ArrayList<>();
        for(int i = 0; i < tbl.getRowCount(); i++){
//            System.out.println(tbl.getModel().getValueAt(i, 0));
//            System.out.println(tbl.getModel().getValueAt(i, 2));
            arr.add(tbl.getModel().getValueAt(i, 2).toString());
        }
        String[] simpleArray = new String[tbl.getRowCount()];
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(arr.toArray( simpleArray ));
        cbKendaraan.setModel( model );
    }
    void frmEtc(Boolean prm, Boolean btn){
        // Formnya
        rdDarat.setEnabled(prm);
        rdAir.setEnabled(prm);
        rdUdara.setEnabled(prm);
        cbKelas.setEnabled(prm);
        cbKendaraan.setEnabled(prm);
        txtHarga.setEnabled(prm);
        txtJmlPenumpang.setEnabled(prm);
        txtAsal.setEnabled(prm);
        txtTujuan.setEnabled(prm);
        txtTglBerangkat.setEnabled(prm);
        txtTglBerangkat.datePicker.setEnabled(prm);
        txtTglBerangkat.timePicker.setEnabled(prm);
        // Btnnya
        btnEdit.setEnabled(btn);
        btnDelete.setEnabled(btn);
        btnPrint.setEnabled(btn);
    }
    void frmClear(){
        rdDarat.setSelected(false);
        rdAir.setSelected(false);
        rdUdara.setSelected(false);
        cbKelas.setSelectedIndex(0);
        cbKendaraan.removeAllItems();
        txtHarga.setText("");
        txtJmlPenumpang.setText("");
        txtAsal.setText("");
        txtTujuan.setText("");
        txtTglBerangkat.datePicker.setDateToToday();
        txtTglBerangkat.timePicker.setTimeToNow();
        lblHarga.setText("");
    }
    void tblOnClick(java.awt.event.MouseEvent evt){
        int row = tblMain.rowAtPoint(evt.getPoint());
        String idnya = util.getVal(tblMain, row, "idtrn");
        String namatp = util.getVal(tblMain, row, "namatp");
        String typetpnya = util.getVal(tblMain, row, "typetp");
        String jmlpm = util.getVal(tblMain, row, "jmlpenumpang");
        String klspm = util.getVal(tblMain, row, "klspenumpang");
        String harga = util.getVal(tblMain, row, "harga");
        String totalharga = util.getVal(tblMain, row, "totalharga");
        String asal = util.getVal(tblMain, row, "asal");
        String tujuan = util.getVal(tblMain, row, "tujuan");
        String tglberangkat = util.getVal(tblMain, row, "tglberangkat");
        String jamberangkat = util.getVal(tblMain, row, "jamberangkat");
        
        if(typetpnya.equals("1")) rdUdara.setSelected(true);
        if(typetpnya.equals("2")) rdAir.setSelected(true);
        if(typetpnya.equals("3")) rdDarat.setSelected(true);
        typetp = typetpnya;
        toCb();
        cbKendaraan.setSelectedItem(namatp);
        cbKelas.setSelectedItem(klspm);
        txtHarga.setText(harga);
        txtJmlPenumpang.setText(jmlpm);
        txtAsal.setText(asal);
        txtTujuan.setText(tujuan);
        txtTglBerangkat.datePicker.setText(tglberangkat);
        txtTglBerangkat.timePicker.setText(jamberangkat);
        lblHarga.setText(totalharga);
        id = idnya;
//        -- BATAS --
        this.frmEtc(false, true); // matiin formnya nyalain btn Edit & Delete & Print
        btnAdd.setEnabled(false); // matiin juga btn Add nya
    }
    void prepareParam(){
        // Prepare Param
        jsnData.put("act", act);
        jsnData.put("tmpidtrn", id);
        jsnData.put("typetp", typetp);
        // -- Bawah itu Form
        jsnData.put("namatp", cbKendaraan.getSelectedItem().toString());
        jsnData.put("jmlpenumpang", txtJmlPenumpang.getText());
        jsnData.put("klspenumpang", cbKelas.getSelectedItem());
        jsnData.put("harga", txtHarga.getText());
        jsnData.put("totalharga", lblHarga.getText());
        jsnData.put("asal", txtAsal.getText());
        jsnData.put("tujuan", txtTujuan.getText());
        jsnData.put("tglberangkat", txtTglBerangkat.datePicker.getText());
        jsnData.put("jamberangkat", txtTglBerangkat.timePicker.getText());
        System.out.print(jsnData);
    }
// ---    BATAS ---
    void runAwal(){
        prosesData("cancel");
    }
    void btnFrm(Boolean prm){
        btnAdd.setEnabled(prm);
        btnSave.setEnabled(!prm);
    }
    
    void frmEtc(Boolean prm){
        this.frmEtc(prm, false);
    }
    
    void prosesData(String proses){
        this.btnFrm(false);
        switch (proses) {
            case "add":
                act = "add";
                this.frmEtc(true);
                break;
            case "edit":
                act = "edit";
                this.frmEtc(true);
                break;
            case "delete":
                act = "delete";
                Integer msg = JOptionPane.showConfirmDialog(null, "Yakin Delete ?", "nanya", JOptionPane.YES_NO_OPTION);
                if(msg == 0){
                    prepareParam();
                    trn.executeData(jsnData);
                    runAwal();
                }
                break;
            case "save":
                prepareParam();
                // Execute Query
                switch(act){
                    case "add":
                        trn.executeData(jsnData);
                        runAwal();
                        break;
                    case "edit":
                        trn.executeData(jsnData);
                        runAwal();
                        break;
                }
                break;
            case "cancel":
                id = "";
                this.frmClear(); // clear isi form
                this.frmEtc(false); // matiin formnya
                this.btnFrm(true); // nyalain btn nya
                trn.showData(tblMain, false); // ambil data
                util.hidCols(tblMain, hidcol); // hidden column
                break;
            default:
                System.err.println("Tidak Ada");
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtTransaksi = new javax.swing.JLabel();
        rdDarat = new javax.swing.JRadioButton();
        rdAir = new javax.swing.JRadioButton();
        rdUdara = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbKendaraan = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbKelas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtTujuan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        txtTglBerangkat = new com.github.lgooddatepicker.components.DateTimePicker();
        txtAsal = new javax.swing.JTextField();
        txtJmlPenumpang = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtTransaksi.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        txtTransaksi.setText("Transaksi");

        buttonGroup1.add(rdDarat);
        rdDarat.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        rdDarat.setText("Darat");
        rdDarat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDaratActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdAir);
        rdAir.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        rdAir.setText("Air");
        rdAir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAirActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdUdara);
        rdUdara.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        rdUdara.setText("Udara");
        rdUdara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdUdaraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Transportasi");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Kendaraan");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Kelas Penumpang");

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ekonomi", "Bisnis" }));
        cbKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKelasActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Harga");

        txtHarga.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtHarga.setText("0");
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Jumlah Penumpang");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Asal");

        txtTujuan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTujuanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Tujuan");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Tgl Berangkat");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Total Harga");

        lblHarga.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lblHarga.setText("0");

        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMainMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMain);

        txtAsal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsalActionPerformed(evt);
            }
        });

        txtJmlPenumpang.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtJmlPenumpang.setText("0");
        txtJmlPenumpang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJmlPenumpangKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdDarat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdAir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdUdara))
                    .addComponent(cbKelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHarga)
                    .addComponent(cbKendaraan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJmlPenumpang, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(62, 62, 62))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(txtTransaksi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(txtTglBerangkat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAsal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(lblHarga))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtTransaksi)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdDarat)
                    .addComponent(rdAir)
                    .addComponent(rdUdara)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtJmlPenumpang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHarga)
                    .addComponent(txtTglBerangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdDaratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdDaratActionPerformed
        // TODO add your handling code here:
        typetp = "3";
        toCb();
    }//GEN-LAST:event_rdDaratActionPerformed

    private void cbKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKelasActionPerformed

    private void txtTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTujuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTujuanActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        prosesData("delete");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsalActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        prosesData("add");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        prosesData("edit");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        prosesData("save");
//        txtTglBerangkat.datePicker.clear();
//        txtTglBerangkat.timePicker.clear();
//        txtTglBerangkat.datePicker.setEnabled(false);
//        txtTglBerangkat.timePicker.setEnabled(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        prosesData("cancel");
//        String dt = "1111-01-01";
//        String[] arrdt = dt.split("-");
//        String tm = "00:00";
//        String[] arrtm = tm.split(":");
//        LocalDate ldt = LocalDate.of(Integer.parseInt(arrdt[0]), Integer.parseInt(arrdt[1]), Integer.parseInt(arrdt[2]));
//        txtTglBerangkat.datePicker.setDate(ldt);
//        LocalTime ltm = LocalTime.of(Integer.parseInt(arrtm[0]), Integer.parseInt(arrtm[1]));
//        txtTglBerangkat.datePicker.setDate(ldt);
//        txtTglBerangkat.timePicker.setTime(ltm);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        prepareParam();
        new frmTransaksiPrint(jsnData).setVisible(true);
//        System.out.println(txtTglBerangkat.getDatePicker());
//        System.out.println(txtTglBerangkat.getTimePicker());
        
    }//GEN-LAST:event_btnPrintActionPerformed

    private void tblMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainMouseClicked
        // TODO add your handling code here:
        tblOnClick(evt);
        
    }//GEN-LAST:event_tblMainMouseClicked

    private void rdAirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAirActionPerformed
        // TODO add your handling code here:
        typetp = "2";
        toCb();
    }//GEN-LAST:event_rdAirActionPerformed

    private void rdUdaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdUdaraActionPerformed
        // TODO add your handling code here:
        typetp = "1";
        toCb();
    }//GEN-LAST:event_rdUdaraActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyReleased
        // TODO add your handling code here:
         hitTotal();
    }//GEN-LAST:event_txtHargaKeyReleased

    private void txtJmlPenumpangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlPenumpangKeyReleased
        // TODO add your handling code here:
        hitTotal();
    }//GEN-LAST:event_txtJmlPenumpangKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JComboBox<String> cbKendaraan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JRadioButton rdAir;
    private javax.swing.JRadioButton rdDarat;
    private javax.swing.JRadioButton rdUdara;
    private javax.swing.JTable tblMain;
    private javax.swing.JTextField txtAsal;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJmlPenumpang;
    private com.github.lgooddatepicker.components.DateTimePicker txtTglBerangkat;
    private javax.swing.JLabel txtTransaksi;
    private javax.swing.JTextField txtTujuan;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekam.medis;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.sql.*;
import java.awt.event.KeyEvent;
import javax.swing.table.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP
 */

public class DataDokter extends javax.swing.JFrame {
    String user = "root";
    String password = "";
    String url = "jdbc:mysql://localhost/rekam_medis";
    /**
     * Creates new form DataDokter
     */
    String[] columnNames = {"ID Dokter", "Nama Dokter", "Spesialis", "Jenis Kelamin", "No Telp", "Alamat", "Email"};
    private DefaultTableModel model;
    private JTable table;
    public DataDokter() {
        initComponents();
        setLocationRelativeTo(null);
        jkLaki.setActionCommand("Laki-laki");
        jkPerempuan.setActionCommand("Perempuan");
        
    }
    void aktif(){
        namaDokter.setEnabled(true);
        jkLaki.setEnabled(true);
        jkPerempuan.setEnabled(true);
        alamatField.setEnabled(true);
        noTelp.setEnabled(true);
        emailField.setEnabled(true);
        saveBtn.setEnabled(true);
    }
    
    void clear(){
        spesialis.setSelectedIndex(0);
        idDokter.setText("");
        namaDokter.setText("");
        jkLaki.setSelected(false);
        jkPerempuan.setSelected(false);
        alamatField.setText("");
        noTelp.setText("");
        emailField.setText("");
    }
    
    void nonaktif(){
        namaDokter.setEnabled(false);
        jkLaki.setEnabled(false);
        jkPerempuan.setEnabled(false);
        alamatField.setEnabled(false);
        noTelp.setEnabled(false);
        emailField.setEnabled(false);
        saveBtn.setEnabled(false);
//        editBtn.setEnabled(false);
    }
    
    void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO dokter VALUES('"+ idDokter.getText()+ "','" + namaDokter.getText()+ "','" + jenisKelamin.getSelection().getActionCommand()+ "','"+ spesialis.getSelectedItem()+ "','"+ alamatField.getText()+ "','"+ noTelp.getText()+ "','"+ emailField.getText() +"')";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan","info",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        clear();
        formWindowActivated(null);
    }
    
    void update(){
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "UPDATE dokter SET idDokter='"+ idDokter.getText()+ "',namaDokter='" + namaDokter.getText()+ "',jkDokter='" + jenisKelamin.getSelection().getActionCommand()+ "',spesialis='"+ spesialis.getSelectedItem()+ "',alamat='"+ alamatField.getText()+ "',noTelp='"+ noTelp.getText()+ "',email='"+ emailField.getText() +"' where idDokter='"+idDokter.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate","info",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        clear();
        formWindowActivated(null);
    }
    
    private Object[][]getData(){
        Object[][]data = null;
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM dokter";
            ResultSet rs = st.executeQuery(sql);
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            data = new Object[rowCount][7];
            int no = -1;
            while(rs.next()){
                no = no+1;
                data[no][0] = rs.getString("idDokter");
                data[no][1] = rs.getString("namaDokter");
                data[no][2] = rs.getString("spesialis");
                data[no][3] = rs.getString("jkDokter");
                data[no][4] = rs.getString("noTelp");
                data[no][5] = rs.getString("alamat");
                data[no][6] = rs.getString("email");
            }
            String nos = String.valueOf(no+1);
            jlhDokter.setText(nos);
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        return data;
    }
    
    private Object[][]getDataSearch(){
        Object[][]data = null;
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM dokter WHERE idDokter LIKE '%"+cariField.getText()+"%'";
            ResultSet rs = st.executeQuery(sql);
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            data = new Object[rowCount][7];
            int no = -1;
            while(rs.next()){
                no = no+1;
                data[no][0] = rs.getString("idDokter");
                data[no][1] = rs.getString("namaDokter");
                data[no][2] = rs.getString("spesialis");
                data[no][3] = rs.getString("jkDokter");
                data[no][4] = rs.getString("noTelp");
                data[no][5] = rs.getString("alamat");
                data[no][6] = rs.getString("email");
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        return data;
    }
    
    void tampilGrid(Object[][]tampil){
        String[] columnNames = {"ID Dokter", "Nama Dokter", "Spesialis", "Jenis Kelamin", "No Telp", "Alamat", "Email"};
        model = new DefaultTableModel(tampil, columnNames);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        for (int i = 0; i < table.getColumnCount(); i++) {
          DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
          TableColumn col = colModel.getColumn(i);
          int width = 0;
          TableCellRenderer renderer = col.getHeaderRenderer();
          for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, i);
            Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, i),false, false, r, i);
            width = Math.max(width, comp.getPreferredSize().width);
          }
          col.setPreferredWidth(width + 50);
        }
        
        jScrollPane1.setViewportView(table);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenisKelamin = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        namaDokter = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        noTelp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTable tableDokter = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        idDokter = new javax.swing.JTextField();
        jkPerempuan = new javax.swing.JRadioButton();
        jkLaki = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        editBtn = new javax.swing.JButton();
        cariField = new javax.swing.JTextField();
        cancelBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jlhDokter = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cariBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        alamatField = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        spesialis = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Input Data Dokter");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cari Data Dokter");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("Nama Dokter");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Spesialis");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("Alamat");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Nomor Telepon");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("Email");

        tableDokter.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDokter.setShowGrid(true);
        jScrollPane1.setViewportView(tableDokter);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("ID Dokter");

        idDokter.setEnabled(false);

        jenisKelamin.add(jkPerempuan);
        jkPerempuan.setText("Perempuan");
        jkPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkPerempuanActionPerformed(evt);
            }
        });

        jenisKelamin.add(jkLaki);
        jkLaki.setText("Laki-laki");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("Jenis Kelamin");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Jumlah Dokter : ");

        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        cariField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariFieldActionPerformed(evt);
            }
        });
        cariField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cariFieldKeyPressed(evt);
            }
        });

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        jlhDokter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlhDokter.setText("12345");

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 42)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rekam/medis/data dokter.png"))); // NOI18N
        jLabel1.setText("  DATA DOKTER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(378, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(352, 352, 352))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        cariBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rekam/medis/search.png"))); // NOI18N
        cariBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariBtnActionPerformed(evt);
            }
        });

        alamatField.setColumns(20);
        alamatField.setRows(5);
        jScrollPane3.setViewportView(alamatField);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Data Dokter");

        spesialis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gigi", "Umum", "Kandungan", "Anak", "Bedah" }));
        spesialis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spesialisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel5))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(namaDokter)
                                            .addComponent(idDokter)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jkLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jkPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(spesialis, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(noTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jlhDokter)
                                .addGap(62, 62, 62))
                            .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cariBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cariBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(editBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addGap(6, 6, 6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(spesialis, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(idDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(namaDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jkPerempuan)
                                            .addComponent(jkLaki))))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(noTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jlhDokter))
                        .addGap(15, 15, 15)
                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveBtn)
                            .addComponent(cancelBtn))
                        .addGap(57, 57, 57))))
        );

        jLabel7.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jkPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkPerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkPerempuanActionPerformed

    private void cariBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT idDokter FROM dokter where idDokter='"+idDokter.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                update();
            }else{
                simpan();
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void spesialisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spesialisActionPerformed
        // TODO add your handling code here:
        try{
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String specialist = spesialis.getSelectedItem().toString();
            specialist = specialist.substring(0,1);
            String sql = "SELECT RIGHT (idDokter,3)+1 FROM dokter WHERE idDokter LIKE '"+specialist+"%'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                rs.last();
                String no = rs.getString(1);
                no = no.substring(0, no.length() - 2);
                if(no.length()==1){
                    no = "00"+no;
                    idDokter.setText(specialist+no);
                }else if(no.length() == 2){
                    no = "0"+no;
                    idDokter.setText(specialist+no);
                }else if(no.length() == 3){
                    idDokter.setText(specialist+no);
                }
            }else{
                idDokter.setText(specialist+"001");
            }
            aktif();
        }catch(SQLException e){
          System.out.println("Koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_spesialisActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tampilGrid(getData());
        nonaktif();
    }//GEN-LAST:event_formWindowActivated

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        // TODO add your handling code here:
        if(table.getSelectedRow() != -1) {
            try{
                Connection conn = DriverManager.getConnection(url,user,password);
                Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                String sql = "SELECT * FROM dokter where idDokter='"+table.getValueAt(table.getSelectedRow(),0)+"'";
                ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    spesialis.setSelectedItem(rs.getString("spesialis"));
                    idDokter.setText(rs.getString("idDokter"));
                    namaDokter.setText(rs.getString("namaDokter"));
                    if(rs.getString("jkDokter").equals("Laki-laki")){
                        jkLaki.setSelected(true);
                    }else{
                        jkPerempuan.setSelected(true);
                    }
                    alamatField.setText(rs.getString("alamat"));
                    noTelp.setText(rs.getString("noTelp"));
                    emailField.setText(rs.getString("email"));
                }
                aktif();
                spesialis.setEnabled(false);
            }catch(SQLException e){
                System.out.println("Koneksi gagal"+e.toString());
            }
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void cariFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariFieldActionPerformed
        // TODO add your handling code here:
            tampilGrid(getDataSearch());
    }//GEN-LAST:event_cariFieldActionPerformed

    private void cariFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyChar() == KeyEvent.VK_ENTER){
            tampilGrid(getDataSearch());
        }
    }//GEN-LAST:event_cariFieldKeyPressed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        clear();
        nonaktif();
        spesialis.setEnabled(true);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this,"Yakin mau keluar?","Confirm",JOptionPane.YES_NO_OPTION);
        if(confirm == 0){
            dispose();
        }
    }//GEN-LAST:event_exitActionPerformed

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
            java.util.logging.Logger.getLogger(DataDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataDokter().setVisible(true);
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea alamatField;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton cariBtn;
    private javax.swing.JTextField cariField;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton exit;
    private javax.swing.JTextField idDokter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.ButtonGroup jenisKelamin;
    private javax.swing.JRadioButton jkLaki;
    private javax.swing.JRadioButton jkPerempuan;
    private javax.swing.JLabel jlhDokter;
    private javax.swing.JTextField namaDokter;
    private javax.swing.JTextField noTelp;
    private javax.swing.JButton saveBtn;
    private javax.swing.JComboBox<String> spesialis;
    // End of variables declaration//GEN-END:variables
}

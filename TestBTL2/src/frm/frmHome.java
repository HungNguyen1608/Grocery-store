/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frm;

import static KetNoi.startclass.connection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import frm.displayvalueModel;
import static frm.frmLogin.ThongBao;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 *
 * @author ADMIN
 */
public class frmHome extends javax.swing.JFrame {

    /**
     * Creates new form frmHome
     */
    ImageIcon icon;
    Calendar cal = Calendar.getInstance();

    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH) + 1;
    int year = cal.get(Calendar.YEAR);
    Date dt = cal.getTime();
// Dinh dang lai hien thi thong tin ngay gio ra man hinh
    SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
    public frmHome() {
        initComponents();
        if(frmLogin.quyen != 1) {
            jTabbedPane1.removeTabAt(2);
            jTabbedPane1.removeTabAt(3);
            jTabbedPane1.removeTabAt(3);
            jTabbedPane1.removeTabAt(4);
            jTabbedPane1.setTitleAt(0, "Xin chào: "+ frmLogin.user.toString());
        }
    }
    static String MaSanPham ="";
    private String getStringValue(Object value) {
        return (value != null) ? value.toString() : "";
    }
    public void LayDuLieuSanPham(String TrangThai) {
    String cautruyvan = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, SanPham.MaLoaiSanPham, LoaiSanPham.TenLoaiSanPham, GiaNhap, GiaBan, HangSanXuat, TonKho, Images, DVT " +
                        "FROM SanPham " +
                        "JOIN LoaiSanPham ON SanPham.MaLoaiSanPham = LoaiSanPham.MaLoaiSanPham " +
                        "WHERE TrangThai = '" + TrangThai + "'"; // Đảm bảo TrangThai được bao quanh bởi dấu nháy đơn nếu là chuỗi

    ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
    DefaultTableModel tableModel = new DefaultTableModel(); // Tạo một DefaultTableModel rỗng
    String[] columnNames = {"STT","Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Hãng Sản Xuất", "Giá nhập", "Giá bán", "Tồn kho", "Đơn vị tính", "Ảnh"};
    tableModel.setColumnIdentifiers(columnNames);
    tbSanPham.setModel(tableModel); // Gán DefaultTableModel vào JTable
    int c = 0;
    try {
        while (rs.next()) {
            c++;
            Object[] item = new Object[10];
            item[0] = c;
            item[1] = rs.getInt("MaSanPham");
            item[2] = rs.getString("TenSanPham");
            item[3] = rs.getString("TenLoaiSanPham"); // Tên cột của bảng LoaiSanPham
            item[4] = rs.getString("HangSanXuat");
            item[5] = rs.getInt("GiaNhap");
            item[6] = rs.getInt("GiaBan");
            item[7] = rs.getInt("TonKho");
            item[8] = rs.getString("DVT");
            item[9] = rs.getString("Images");
            tableModel.addRow(item); // Thêm dòng vào DefaultTableModel
            for (Object obj : item) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    public void layDuLieuLoaiSanPham() {
        String cautruyvan = "";
        cautruyvan = "select * from LoaiSanPham ";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Loại", "Tên Loại"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbLoaiSanPham_LoaiSanPham.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[3];
                item[0] = c;
                item[1] = rs.getInt("MaLoaiSanPham");
                item[2] = rs.getString("TenLoaiSanPham");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    public void LayDuLieuSanPhamofLoaiSanPham(String MaLoaiSanPham) {
        String cautruyvan = "";
//        cautruyvan = "select MaSanPham,SanPham.TenSanPham,LoaiSanPham.TenLoaiSanPham"
//                + " as TenLoaiSanPham from SanPham,LoaiSanPham where"
//                + " SanPham.MaLoaiSanPham=LoaiSanPham.MaLoaiSanPham and MaLoaiSanPham=" + MaLoaiSanPham;
        cautruyvan = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, LoaiSanPham.TenLoaiSanPham AS TenLoaiSanPham " +
        "FROM SanPham " +
        "JOIN LoaiSanPham ON SanPham.MaLoaiSanPham = LoaiSanPham.MaLoaiSanPham " +
        "WHERE SanPham.MaLoaiSanPham = " + MaLoaiSanPham;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Sản Phẩm", "Tên sản phẩm", "Loại sản phẩm"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham_LoaiSanPham.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[4];
                item[0] = c;
                item[1] = rs.getInt("MaSanPham");
                item[2] = rs.getString("TenSanPham");
                item[3] = rs.getString("TenLoaiSanPham");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
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

        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanelSanPham = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lblMaSanPham = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        lblTenSanPham = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        lblLoaiSanPham = new javax.swing.JLabel();
        cbMaLoaiSanPham = new javax.swing.JComboBox<>();
        lblHangSanXuat = new javax.swing.JLabel();
        txtHangSanXuat = new javax.swing.JTextField();
        lblGiaNhap = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        lblTonKho = new javax.swing.JLabel();
        txtTonKho = new javax.swing.JTextField();
        lblAnh = new javax.swing.JLabel();
        txtAnh = new javax.swing.JTextField();
        lblGiaBan = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        txtXoa = new javax.swing.JButton();
        btnXemSPAn = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        rbTimKiemMaSanPham = new javax.swing.JRadioButton();
        rbTimKiemTenSanPham = new javax.swing.JRadioButton();
        txtTimKiem = new javax.swing.JTextField();
        rbTimKiemLoaiSanPham = new javax.swing.JRadioButton();
        cbTimKiemLoaiSanPham = new javax.swing.JComboBox<>();
        jPanelLoaiSanPham = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbLoaiSanPham_LoaiSanPham = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaLoaiSanPham = new javax.swing.JTextField();
        txtTenLoaiSanPham = new javax.swing.JTextField();
        btnThem_LoaiSanPham = new javax.swing.JButton();
        btnSua_LoaiSanPham = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbSanPham_LoaiSanPham = new javax.swing.JTable();
        btnXoa_LoaiSanPham = new javax.swing.JButton();
        btnReset_LoaiSanPham = new javax.swing.JButton();
        jPanelHangCanNhap = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHangCanNhap = new javax.swing.JTable();
        btnNhapHangConIt = new javax.swing.JButton();
        jPanelNhanVien = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        cbNgaySinh_NhanVien = new javax.swing.JComboBox<>();
        cbThangSinh_NhanVien = new javax.swing.JComboBox<>();
        cbNamSinh_NhanVien = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        rbNam_NhanVien = new javax.swing.JRadioButton();
        rbNu_NhanVien = new javax.swing.JRadioButton();
        jLabel36 = new javax.swing.JLabel();
        cbNgayBDLam_NhanVien = new javax.swing.JComboBox<>();
        cbThangBDLam_NhanVien = new javax.swing.JComboBox<>();
        cbNamBDLam_NhanVien = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        txtChucVu_NhanVien = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtDiaChi_NhanVien = new javax.swing.JTextField();
        txtSDT_NhanVien = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        txtGhiChu_NhanVien = new javax.swing.JTextArea();
        btnThem_NhanVien = new javax.swing.JButton();
        btnSua_NhanVien = new javax.swing.JButton();
        btnXoa_NhanVien = new javax.swing.JButton();
        btnReset_NhanVien = new javax.swing.JButton();
        txtTimKiem_NhanVien = new javax.swing.JTextField();
        rbTimKiemMa_NhanVien = new javax.swing.JRadioButton();
        rbTimKiemTen_NhanVien = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanelKhachHang = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        ckboxTimKiem_KhachHang = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        txtTimKiemTen_KhachHang = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        cbTimKiemTuoi1_KhachHang = new javax.swing.JComboBox<>();
        cbTimKiemTuoi2_KhachHang = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        btnThem_KhachHang = new javax.swing.JButton();
        btnSua_KhachHang = new javax.swing.JButton();
        btnXoa_KhachHang = new javax.swing.JButton();
        btnRes_KhachHang = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtSoDienThoai_KhachHang = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cbNgay_KhachHang = new javax.swing.JComboBox<>();
        cbThang_KhachHang = new javax.swing.JComboBox<>();
        cbNam_KhachHang = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        rbNam_KhachHang = new javax.swing.JRadioButton();
        rbNu_KhachHang = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        txtDiaChi_KhachHang = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        rbOld_KhachHang = new javax.swing.JRadioButton();
        rbNew_KhachHang = new javax.swing.JRadioButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtGhiChu_KhachHang = new javax.swing.JTextArea();
        jPanelTaiKhoan = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tbTaiKhoan = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txtID_TaiKhoan = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        cbTenNhanVien_TaiKhoan = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        txtUsername_TaiKhoan = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtPassword_TaiKhoan = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtGhiChu_TaiKhoan = new javax.swing.JTextArea();
        rbQuyen_user = new javax.swing.JRadioButton();
        rbQuyen_ad = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        btnThem_TaiKhoan = new javax.swing.JButton();
        btnSua_TaiKhoan = new javax.swing.JButton();
        btnXoa_TaiKhoan = new javax.swing.JButton();
        btnRes_TaiKhoan = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tbNhaPhanPhoi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        txtMa_NPP = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtTen_NPP = new javax.swing.JTextField();
        txtDiaChi_NPP = new javax.swing.JTextField();
        txtSoDienThoai_NPP = new javax.swing.JTextField();
        txtEmail_NPP = new javax.swing.JTextField();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtGhiChu_NPP = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        btnThem_NPP = new javax.swing.JButton();
        btnSua_NPP = new javax.swing.JButton();
        btnXoa_NPP = new javax.swing.JButton();
        btnReset_NPP = new javax.swing.JButton();
        jPanelHoaDon = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tbCTHD = new javax.swing.JTable();
        btnTimKiem_HoaDon = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        txtMa_CTHD = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtMaHoaDon_CTHD = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        cbSanPham_CTHD = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        txtSoLuong_CTHD = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtTongTien_CTHD = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtGhiChu_CTHD = new javax.swing.JTextArea();
        btnThem_CTHD = new javax.swing.JButton();
        btnSua_CTHD = new javax.swing.JButton();
        btnXoa_CTHD = new javax.swing.JButton();
        btnReset_CTHD = new javax.swing.JButton();
        btnHoanHang = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        rbMaSP_HoaDon = new javax.swing.JRadioButton();
        rbTenSP_HoaDon = new javax.swing.JRadioButton();
        txtTKSP_HoaDon = new javax.swing.JTextField();
        jScrollPane26 = new javax.swing.JScrollPane();
        tbSanPham_HoaDon = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        txtMa_HoaDon = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        cbKhachHang_HoaDon = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        cbNhanVien_HoaDon = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        cbNgayLap_HoaDon = new javax.swing.JComboBox<>();
        cbThangLap_HoaDon = new javax.swing.JComboBox<>();
        cbNamLap_HoaDon = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        txtTongTien_HoaDon = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtGhiChu_HoaDon = new javax.swing.JTextArea();
        btnThem_HoaDon = new javax.swing.JButton();
        btnSua_HoaDon = new javax.swing.JButton();
        btnXoa_HoaDon = new javax.swing.JButton();
        btnReset_HoaDon = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        txtTenKH_HoaDon = new javax.swing.JTextField();
        txtSDT_HoaDon = new javax.swing.JTextField();
        btnKiemTra_HoaDon = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        lbTongTien = new javax.swing.JLabel();
        lbTienTraKhach = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jPanelDoanhThu = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tbDoanhThu_SanPham = new javax.swing.JTable();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tbDoanhThu_NhanVien = new javax.swing.JTable();
        jLabel74 = new javax.swing.JLabel();
        cbNgay1_DoanhThu = new javax.swing.JComboBox<>();
        cbThang1_DoanhThu = new javax.swing.JComboBox<>();
        cbNam1_DoanhThu = new javax.swing.JComboBox<>();
        cbNgay2_DoanhThu = new javax.swing.JComboBox<>();
        cbThang2_DoanhThu = new javax.swing.JComboBox<>();
        cbNam2_DoanhThu = new javax.swing.JComboBox<>();
        rbNhanVien_DoanhThu = new javax.swing.JRadioButton();
        rbSanPham_DoanhThu = new javax.swing.JRadioButton();
        jPanelNhapHang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbChiTietPhieuNhap = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbPhieuNhap = new javax.swing.JTable();
        btnTimKiem_PhieuNhap = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtMa_PhieuNhap = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbNhanVien_PhieuNhap = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbNgay_PhieuNhap = new javax.swing.JComboBox<>();
        cbThang_PhieuNhap = new javax.swing.JComboBox<>();
        cbNam_PhieuNhap = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbNhaPhanPhoi_PhieuNhap = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtTongTien_PhieuNhap = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtGhiChu_PhieuNhap = new javax.swing.JTextArea();
        btnThem_PhieuNhap = new javax.swing.JButton();
        btnSua_PhieuNhap = new javax.swing.JButton();
        btnXoa_PhieuNhap = new javax.swing.JButton();
        btnReset_PhieuNhap = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtMa_CTPN = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMaPhieuNhap_CTPN = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cbSanPham_CTPN = new javax.swing.JComboBox<>();
        txtSoLuong_CTPN = new javax.swing.JTextField();
        txtTongTien_CTPN = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtGhiChu_CTPN = new javax.swing.JTextArea();
        btnThem_CTPN = new javax.swing.JButton();
        btnSua_CTPN = new javax.swing.JButton();
        btnXoa_CTPN = new javax.swing.JButton();
        btnReset_CTPN = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        rbMaSP_PhieuNhap = new javax.swing.JRadioButton();
        rbTenSP_PhieuNhap = new javax.swing.JRadioButton();
        txtTKSP_PhieuNhap = new javax.swing.JTextField();
        jScrollPane27 = new javax.swing.JScrollPane();
        tbSanPham_PhieuNhap = new javax.swing.JTable();
        jPanelDangXuat = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2279, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1456, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("XIN CHÀO ADMIN", jPanel1);

        jPanelSanPham.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelSanPhamComponentShown(evt);
            }
        });

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Hãng Sản Xuất", "Giá nhập", "Giá bán", "Tồn kho", "Đơn vị tính", "Ảnh"
            }
        ));
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSanPham);
        if (tbSanPham.getColumnModel().getColumnCount() > 0) {
            tbSanPham.getColumnModel().getColumn(3).setHeaderValue("Loại sản phẩm");
            tbSanPham.getColumnModel().getColumn(5).setHeaderValue("Giá nhập");
            tbSanPham.getColumnModel().getColumn(9).setHeaderValue("Ảnh");
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Danh mục sản phẩm");

        jLabel73.setText("Ảnh:");

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblMaSanPham.setText("Mã sản phẩm:");

        lblTenSanPham.setText("Tên sản phẩm");

        lblLoaiSanPham.setText("Loại sản phẩm");

        cbMaLoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblHangSanXuat.setText("Hãng sản xuất:");

        txtHangSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHangSanXuatActionPerformed(evt);
            }
        });

        lblGiaNhap.setText("Giá nhập:");

        jLabel20.setText("Giá bán");

        lblTonKho.setText("Tồn kho:");

        lblAnh.setText("Ảnh:");

        lblGiaBan.setText("Đơn vị tính");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(lblMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblLoaiSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                            .addComponent(lblTenSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbMaLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(lblHangSanXuat)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtHangSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(lblGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGiaNhap)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTonKho)
                                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGiaBan))
                                .addGap(0, 12, Short.MAX_VALUE)))
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaSanPham)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtGiaBan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSanPham)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTonKho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTonKho))
                .addGap(12, 12, 12)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMaLoaiSanPham)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblLoaiSanPham)
                        .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHangSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHangSanXuat)
                        .addComponent(lblGiaBan)
                        .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        txtXoa.setText("Xóa");
        txtXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtXoaActionPerformed(evt);
            }
        });

        btnXemSPAn.setText("Xem SP ẩn");
        btnXemSPAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemSPAnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemSPAn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXemSPAn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        buttonGroup2.add(rbTimKiemMaSanPham);
        rbTimKiemMaSanPham.setText("Tìm kiếm theo mã sản phẩm");

        buttonGroup2.add(rbTimKiemTenSanPham);
        rbTimKiemTenSanPham.setText("Tìm kiếm theo tên sản phẩm");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        buttonGroup2.add(rbTimKiemLoaiSanPham);
        rbTimKiemLoaiSanPham.setText("Tìm kiếm theo loại sản phẩm");
        rbTimKiemLoaiSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbTimKiemLoaiSanPhamItemStateChanged(evt);
            }
        });

        cbTimKiemLoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTimKiemLoaiSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTimKiemLoaiSanPhamItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbTimKiemMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbTimKiemTenSanPham)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbTimKiemLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTimKiemLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(rbTimKiemMaSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbTimKiemTenSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbTimKiemLoaiSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbTimKiemLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelSanPhamLayout = new javax.swing.GroupLayout(jPanelSanPham);
        jPanelSanPham.setLayout(jPanelSanPhamLayout);
        jPanelSanPhamLayout.setHorizontalGroup(
            jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSanPhamLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSanPhamLayout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanelSanPhamLayout.createSequentialGroup()
                        .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSanPhamLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(1006, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSanPhamLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(894, 894, 894))))))
        );
        jPanelSanPhamLayout.setVerticalGroup(
            jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSanPhamLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(843, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sản phẩm", jPanelSanPham);

        jPanelLoaiSanPham.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelLoaiSanPhamComponentShown(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Danh mục loại sản phẩm:");

        tbLoaiSanPham_LoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã loại sản phẩm", "Tên loại sản phẩm"
            }
        ));
        tbLoaiSanPham_LoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLoaiSanPham_LoaiSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbLoaiSanPham_LoaiSanPham);

        jLabel3.setText("Mã loại sp:");

        jLabel4.setText("Tên loại sp:");

        btnThem_LoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_LoaiSanPham.setText("Thêm");
        btnThem_LoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_LoaiSanPhamActionPerformed(evt);
            }
        });

        btnSua_LoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_LoaiSanPham.setText("Sửa");
        btnSua_LoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_LoaiSanPhamActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Danh mục sản phẩm:");

        tbSanPham_LoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm ", "Loại sản phẩm"
            }
        ));
        jScrollPane4.setViewportView(tbSanPham_LoaiSanPham);

        btnXoa_LoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_LoaiSanPham.setText("Xóa");
        btnXoa_LoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_LoaiSanPhamActionPerformed(evt);
            }
        });

        btnReset_LoaiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_LoaiSanPham.setText("RESET");
        btnReset_LoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_LoaiSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLoaiSanPhamLayout = new javax.swing.GroupLayout(jPanelLoaiSanPham);
        jPanelLoaiSanPham.setLayout(jPanelLoaiSanPhamLayout);
        jPanelLoaiSanPhamLayout.setHorizontalGroup(
            jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                                        .addComponent(btnThem_LoaiSanPham)
                                        .addGap(1, 1, 1))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLoaiSanPhamLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnSua_LoaiSanPham)
                                .addGap(53, 53, 53)
                                .addComponent(btnXoa_LoaiSanPham)
                                .addGap(56, 56, 56)
                                .addComponent(btnReset_LoaiSanPham)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLoaiSanPhamLayout.setVerticalGroup(
            jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoaiSanPhamLayout.createSequentialGroup()
                        .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanelLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem_LoaiSanPham)
                            .addComponent(btnSua_LoaiSanPham)
                            .addComponent(btnXoa_LoaiSanPham)
                            .addComponent(btnReset_LoaiSanPham)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Loại sản phẩm", jPanelLoaiSanPham);

        jPanelHangCanNhap.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelHangCanNhapComponentShown(evt);
            }
        });

        tbHangCanNhap.setModel(new javax.swing.table.DefaultTableModel(
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
        tbHangCanNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHangCanNhapMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbHangCanNhap);

        btnNhapHangConIt.setText("Nhập hàng");
        btnNhapHangConIt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapHangConItActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelHangCanNhapLayout = new javax.swing.GroupLayout(jPanelHangCanNhap);
        jPanelHangCanNhap.setLayout(jPanelHangCanNhapLayout);
        jPanelHangCanNhapLayout.setHorizontalGroup(
            jPanelHangCanNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHangCanNhapLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelHangCanNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNhapHangConIt)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelHangCanNhapLayout.setVerticalGroup(
            jPanelHangCanNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHangCanNhapLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnNhapHangConIt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Hàng cần nhập", jPanelHangCanNhap);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTabbedPane1.addTab("Quản lý sản phẩm", new javax.swing.ImageIcon(getClass().getResource("/icon/product.png")), jPanel2); // NOI18N

        jPanelNhanVien.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelNhanVienComponentShown(evt);
            }
        });

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "Ngày vào làm", "Chức vụ", "Địa chỉ", "SDT", "Ghi chú"
            }
        ));
        tbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanVienMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tbNhanVien);

        jLabel32.setText("Mã nhân viên");

        jLabel33.setText("Tên nhân viên");

        jLabel34.setText("Ngày sinh");

        cbNgaySinh_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThangSinh_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNamSinh_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel35.setText("Giới tính");

        buttonGroup3.add(rbNam_NhanVien);
        rbNam_NhanVien.setText("Nam");

        buttonGroup3.add(rbNu_NhanVien);
        rbNu_NhanVien.setText("Nữ");

        jLabel36.setText("Ngày vào làm");

        cbNgayBDLam_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThangBDLam_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNamBDLam_NhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel37.setText("Chức vụ");

        jLabel38.setText("Địa chỉ");

        jLabel39.setText("SDT");

        jLabel40.setText("Ghi chú");

        txtGhiChu_NhanVien.setColumns(20);
        txtGhiChu_NhanVien.setRows(5);
        jScrollPane12.setViewportView(txtGhiChu_NhanVien);

        btnThem_NhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_NhanVien.setText("Thêm");
        btnThem_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_NhanVienActionPerformed(evt);
            }
        });

        btnSua_NhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_NhanVien.setText("Sửa");
        btnSua_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_NhanVienActionPerformed(evt);
            }
        });

        btnXoa_NhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_NhanVien.setText("Xóa");
        btnXoa_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_NhanVienActionPerformed(evt);
            }
        });

        btnReset_NhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_NhanVien.setText("RESET");
        btnReset_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_NhanVienActionPerformed(evt);
            }
        });

        txtTimKiem_NhanVien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem_NhanVienKeyReleased(evt);
            }
        });

        buttonGroup4.add(rbTimKiemMa_NhanVien);
        rbTimKiemMa_NhanVien.setText("Mã nhân viên");

        buttonGroup4.add(rbTimKiemTen_NhanVien);
        rbTimKiemTen_NhanVien.setText("Tên nhân viên");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 255));
        jLabel41.setText("Nhân viên");

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        jLabel42.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanelNhanVienLayout = new javax.swing.GroupLayout(jPanelNhanVien);
        jPanelNhanVien.setLayout(jPanelNhanVienLayout);
        jPanelNhanVienLayout.setHorizontalGroup(
            jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnXoa_NhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua_NhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThem_NhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                                        .addComponent(cbNgayBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbThangBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbNamBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                                        .addComponent(rbNam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbNu_NhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                                        .addComponent(cbNgaySinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbThangSinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbNamSinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenNhanVien)
                                    .addComponent(txtMaNhanVien)
                                    .addComponent(txtChucVu_NhanVien))
                                .addGap(65, 65, 65)
                                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTimKiem_NhanVien)
                                    .addComponent(txtSDT_NhanVien)
                                    .addComponent(txtDiaChi_NhanVien)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                                        .addComponent(rbTimKiemMa_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rbTimKiemTen_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(271, 271, 271))))
        );
        jPanelNhanVienLayout.setVerticalGroup(
            jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                        .addComponent(btnThem_NhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua_NhanVien)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa_NhanVien)
                        .addGap(24, 24, 24)
                        .addComponent(btnReset_NhanVien)))
                .addGap(18, 18, 18)
                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(txtDiaChi_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(txtSDT_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbNgaySinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbThangSinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbNamSinh_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel40)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(rbNam_NhanVien)
                            .addComponent(rbNu_NhanVien))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(cbNgayBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbThangBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNamBDLam_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtChucVu_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelNhanVienLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addGroup(jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbTimKiemMa_NhanVien)
                            .addComponent(rbTimKiemTen_NhanVien)
                            .addComponent(jLabel42))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(628, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý nhân viên", new javax.swing.ImageIcon(getClass().getResource("/icon/staff.png")), jPanelNhanVien); // NOI18N

        jPanelKhachHang.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelKhachHangComponentShown(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 255));
        jLabel21.setText("Khách hàng");

        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Giới tính", "Địa chỉ", "Số điện thoại", "Loại khách hàng", "Ghi chú"
            }
        ));
        tbKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKhachHangMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbKhachHang);

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ckboxTimKiem_KhachHang.setText("Tìm kiếm");
        ckboxTimKiem_KhachHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ckboxTimKiem_KhachHangItemStateChanged(evt);
            }
        });

        jLabel30.setText("Tên");

        txtTimKiemTen_KhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTen_KhachHangKeyReleased(evt);
            }
        });

        jLabel31.setText("Tuổi");

        cbTimKiemTuoi1_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTimKiemTuoi1_KhachHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTimKiemTuoi1_KhachHangItemStateChanged(evt);
            }
        });

        cbTimKiemTuoi2_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTimKiemTuoi2_KhachHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTimKiemTuoi2_KhachHangItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTimKiemTuoi1_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTimKiemTuoi2_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckboxTimKiem_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemTen_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(ckboxTimKiem_KhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtTimKiemTen_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(cbTimKiemTuoi1_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTimKiemTuoi2_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnThem_KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_KhachHang.setText("Thêm");
        btnThem_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_KhachHangActionPerformed(evt);
            }
        });

        btnSua_KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_KhachHang.setText("Sửa");
        btnSua_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_KhachHangActionPerformed(evt);
            }
        });

        btnXoa_KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_KhachHang.setText("Xóa");
        btnXoa_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_KhachHangActionPerformed(evt);
            }
        });

        btnRes_KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnRes_KhachHang.setText("RESET");
        btnRes_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRes_KhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSua_KhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa_KhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRes_KhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem_KhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem_KhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua_KhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa_KhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRes_KhachHang)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel22.setText("Mã khách hàng:");

        jLabel27.setText("Số điện thoại:");

        jLabel23.setText("Tên khách hàng:");

        jLabel24.setText("Ngày sinh:");

        cbNgay_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThang_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNam_KhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel25.setText("Giới tính: ");

        buttonGroup1.add(rbNam_KhachHang);
        rbNam_KhachHang.setText("Nam");

        buttonGroup1.add(rbNu_KhachHang);
        rbNu_KhachHang.setText("Nữ");

        jLabel26.setText("Địa chỉ:");

        jLabel28.setText("Loại KH:");

        buttonGroup7.add(rbOld_KhachHang);
        rbOld_KhachHang.setText("Thân thiết");

        buttonGroup7.add(rbNew_KhachHang);
        rbNew_KhachHang.setText("Mới");

        jLabel29.setText("Ghi chú:");

        txtGhiChu_KhachHang.setColumns(20);
        txtGhiChu_KhachHang.setRows(5);
        jScrollPane10.setViewportView(txtGhiChu_KhachHang);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaKhachHang)
                                    .addComponent(txtTenKhachHang))
                                .addGap(36, 36, 36))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDiaChi_KhachHang)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                        .addComponent(rbNam_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rbNu_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                        .addComponent(cbNgay_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbThang_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbNam_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(rbOld_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbNew_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtSoDienThoai_KhachHang)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(txtSoDienThoai_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(rbOld_KhachHang)
                    .addComponent(rbNew_KhachHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(cbNgay_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbThang_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNam_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(rbNam_KhachHang)
                            .addComponent(rbNu_KhachHang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addComponent(txtDiaChi_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelKhachHangLayout = new javax.swing.GroupLayout(jPanelKhachHang);
        jPanelKhachHang.setLayout(jPanelKhachHangLayout);
        jPanelKhachHangLayout.setHorizontalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1091, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addGap(423, 423, 423)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(189, 1168, Short.MAX_VALUE))
        );
        jPanelKhachHangLayout.setVerticalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelKhachHangLayout.createSequentialGroup()
                        .addGap(492, 492, 492)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(752, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý khách hàng", new javax.swing.ImageIcon(getClass().getResource("/icon/customer.png")), jPanelKhachHang); // NOI18N

        jPanelTaiKhoan.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelTaiKhoanComponentShown(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 255));
        jLabel43.setText("Tài khoản");

        tbTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên nhân viên", "Username", "Password", "Quyền", "Ghi chú"
            }
        ));
        tbTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tbTaiKhoan);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập liệu:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel44.setText("ID");

        jLabel45.setText("Tên nhân viên");

        cbTenNhanVien_TaiKhoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel46.setText("Username");

        jLabel47.setText("Password");

        jLabel48.setText("Quyền");

        jLabel49.setText("Ghi chú");

        txtGhiChu_TaiKhoan.setColumns(20);
        txtGhiChu_TaiKhoan.setRows(5);
        jScrollPane14.setViewportView(txtGhiChu_TaiKhoan);

        buttonGroup8.add(rbQuyen_user);
        rbQuyen_user.setText("User");

        buttonGroup8.add(rbQuyen_ad);
        rbQuyen_ad.setText("Admin");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTenNhanVien_TaiKhoan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtID_TaiKhoan)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword_TaiKhoan)
                            .addComponent(txtUsername_TaiKhoan)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbQuyen_ad, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbQuyen_user, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtID_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(cbTenNhanVien_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtUsername_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtPassword_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(rbQuyen_user)
                    .addComponent(rbQuyen_ad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnThem_TaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_TaiKhoan.setText("Thêm");
        btnThem_TaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_TaiKhoanActionPerformed(evt);
            }
        });

        btnSua_TaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_TaiKhoan.setText("Sửa");
        btnSua_TaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_TaiKhoanActionPerformed(evt);
            }
        });

        btnXoa_TaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_TaiKhoan.setText("Xóa");
        btnXoa_TaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_TaiKhoanActionPerformed(evt);
            }
        });

        btnRes_TaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnRes_TaiKhoan.setText("RESET");
        btnRes_TaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRes_TaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa_TaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRes_TaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(btnThem_TaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua_TaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem_TaiKhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua_TaiKhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa_TaiKhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRes_TaiKhoan)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelTaiKhoanLayout = new javax.swing.GroupLayout(jPanelTaiKhoan);
        jPanelTaiKhoan.setLayout(jPanelTaiKhoanLayout);
        jPanelTaiKhoanLayout.setHorizontalGroup(
            jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(1535, Short.MAX_VALUE))
        );
        jPanelTaiKhoanLayout.setVerticalGroup(
            jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTaiKhoanLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel43)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(749, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý tài khoản", new javax.swing.ImageIcon(getClass().getResource("/icon/account.png")), jPanelTaiKhoan); // NOI18N

        jPanel3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel3ComponentShown(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 255));
        jLabel50.setText("Nhà phân phối");

        tbNhaPhanPhoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhà phân phối", "Tên nhà phân phối", "Địa chỉ", "Số điện thoại", "Email", "Ghi chú"
            }
        ));
        tbNhaPhanPhoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhaPhanPhoiMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tbNhaPhanPhoi);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel51.setText("Mã NPP:");

        jLabel52.setText("Tên NPP:");

        jLabel53.setText("Địa chỉ:");

        jLabel54.setText("Số điện thoại: ");

        jLabel55.setText("Email:");

        jLabel56.setText("Ghi chú:");

        txtGhiChu_NPP.setColumns(20);
        txtGhiChu_NPP.setRows(5);
        jScrollPane16.setViewportView(txtGhiChu_NPP);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMa_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen_NPP)
                            .addComponent(txtDiaChi_NPP)
                            .addComponent(txtSoDienThoai_NPP)
                            .addComponent(txtEmail_NPP)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtMa_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txtTen_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txtDiaChi_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtSoDienThoai_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtEmail_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel56))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnThem_NPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_NPP.setText("Thêm");
        btnThem_NPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_NPPActionPerformed(evt);
            }
        });

        btnSua_NPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_NPP.setText("Sửa");
        btnSua_NPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_NPPActionPerformed(evt);
            }
        });

        btnXoa_NPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_NPP.setText("Xóa");
        btnXoa_NPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_NPPActionPerformed(evt);
            }
        });

        btnReset_NPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_NPP.setText("RESET");
        btnReset_NPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_NPPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa_NPP, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset_NPP))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnThem_NPP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua_NPP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa_NPP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset_NPP)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1495, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(378, 378, 378)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(744, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý nhà phân phối", new javax.swing.ImageIcon(getClass().getResource("/icon/supplier.png")), jPanel3); // NOI18N

        jPanelHoaDon.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelHoaDonComponentShown(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 255));
        jLabel57.setText("Hóa Đơn:");

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Khách hàng", "Nhân viên", "Ngày lập hóa đơn", "Tổng tiền", "Ghi chú"
            }
        ));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(tbHoaDon);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 255));
        jLabel58.setText("Chi tiết hóa đơn:");

        tbCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTHD", "Mã HD", "Sản phẩm", "Số lượng", "Tổng tiền", "Ghi chú"
            }
        ));
        tbCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCTHDMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(tbCTHD);

        btnTimKiem_HoaDon.setText("Tìm kiếm");
        btnTimKiem_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem_HoaDonActionPerformed(evt);
            }
        });

        jButton1.setText("Xuất HD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel65.setText("Mã CTHD:");

        jLabel66.setText("Mã HD:");

        jLabel67.setText("Sản phẩm:");

        cbSanPham_CTHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel68.setText("Số lượng:");

        jLabel69.setText("Tổng tiền:");

        jLabel70.setText("Ghi chú: ");

        txtGhiChu_CTHD.setColumns(20);
        txtGhiChu_CTHD.setRows(5);
        jScrollPane21.setViewportView(txtGhiChu_CTHD);

        btnThem_CTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_CTHD.setText("Thêm");
        btnThem_CTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_CTHDActionPerformed(evt);
            }
        });

        btnSua_CTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_CTHD.setText("Sửa");
        btnSua_CTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_CTHDActionPerformed(evt);
            }
        });

        btnXoa_CTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_CTHD.setText("Xóa");
        btnXoa_CTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_CTHDActionPerformed(evt);
            }
        });

        btnReset_CTHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_CTHD.setText("RESET");
        btnReset_CTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_CTHDActionPerformed(evt);
            }
        });

        btnHoanHang.setText("Hoàn hàng");
        btnHoanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMa_CTHD))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaHoaDon_CTHD))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69)
                            .addComponent(jLabel67)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtTongTien_CTHD)
                            .addComponent(txtSoLuong_CTHD)
                            .addComponent(cbSanPham_CTHD, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnThem_CTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset_CTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHoanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua_CTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(btnXoa_CTHD)
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(txtMa_CTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txtMaHoaDon_CTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(cbSanPham_CTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txtSoLuong_CTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(txtTongTien_CTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem_CTHD)
                    .addComponent(btnSua_CTHD)
                    .addComponent(btnXoa_CTHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset_CTHD)
                    .addComponent(btnHoanHang)))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        buttonGroup6.add(rbMaSP_HoaDon);
        rbMaSP_HoaDon.setText("Mã sản phẩm");

        buttonGroup6.add(rbTenSP_HoaDon);
        rbTenSP_HoaDon.setText("Tên sản phẩm");

        txtTKSP_HoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKSP_HoaDonKeyReleased(evt);
            }
        });

        tbSanPham_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Hãng Sản Xuất", "Giá bán", "Tồn kho", "Đơn vị tính"
            }
        ));
        tbSanPham_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPham_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane26.setViewportView(tbSanPham_HoaDon);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbMaSP_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbTenSP_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTKSP_HoaDon)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbMaSP_HoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbTenSP_HoaDon))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(txtTKSP_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel59.setText("Mã hóa đơn:");

        jLabel60.setText("Khách hàng");

        cbKhachHang_HoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel61.setText("Nhân viên:");

        cbNhanVien_HoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel62.setText("Ngày lập:");

        cbNgayLap_HoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThangLap_HoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNamLap_HoaDon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNamLap_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNamLap_HoaDonActionPerformed(evt);
            }
        });

        jLabel63.setText("Tổng tiền:");

        jLabel64.setText("Ghi chú:");

        txtGhiChu_HoaDon.setColumns(20);
        txtGhiChu_HoaDon.setRows(5);
        jScrollPane19.setViewportView(txtGhiChu_HoaDon);

        btnThem_HoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_HoaDon.setText("Thêm");
        btnThem_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_HoaDonActionPerformed(evt);
            }
        });

        btnSua_HoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_HoaDon.setText("Sửa");
        btnSua_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_HoaDonActionPerformed(evt);
            }
        });

        btnXoa_HoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_HoaDon.setText("Xóa");
        btnXoa_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_HoaDonActionPerformed(evt);
            }
        });

        btnReset_HoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_HoaDon.setText("RESET");
        btnReset_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_HoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbKhachHang_HoaDon, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(cbNgayLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbThangLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbNamLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongTien_HoaDon, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbNhanVien_HoaDon, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMa_HoaDon))
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnThem_HoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua_HoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa_HoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset_HoaDon)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtMa_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(cbKhachHang_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(cbNhanVien_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(cbNgayLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbThangLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNamLap_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(txtTongTien_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem_HoaDon)
                    .addComponent(btnSua_HoaDon)
                    .addComponent(btnXoa_HoaDon)
                    .addComponent(btnReset_HoaDon))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khách hàng mới", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel91.setText("Tên khách hàng:");

        jLabel92.setText("Số điện thoại");

        btnKiemTra_HoaDon.setText("Kiểm tra");
        btnKiemTra_HoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTra_HoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnKiemTra_HoaDon)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTenKH_HoaDon)
                    .addComponent(txtSDT_HoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(txtTenKH_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(txtSDT_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnKiemTra_HoaDon)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel93.setText("Tổng tiền:");

        jLabel94.setText("Tiền khách đưa:");

        jLabel95.setText("Tiền trả khách:");

        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        lbTongTien.setText("Tổng tiền");

        lbTienTraKhach.setText("Tiền trả khách");

        jLabel96.setText("VNĐ");

        jLabel97.setText("VNĐ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTienKhachDua)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lbTienTraKhach, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(lbTongTien)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(lbTienTraKhach)
                    .addComponent(jLabel97))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelHoaDonLayout = new javax.swing.GroupLayout(jPanelHoaDon);
        jPanelHoaDon.setLayout(jPanelHoaDonLayout);
        jPanelHoaDonLayout.setHorizontalGroup(
            jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                        .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelHoaDonLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                                .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHoaDonLayout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTimKiem_HoaDon)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHoaDonLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)))))
                .addContainerGap(1157, Short.MAX_VALUE))
        );
        jPanelHoaDonLayout.setVerticalGroup(
            jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel58)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(682, Short.MAX_VALUE))
            .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHoaDonLayout.createSequentialGroup()
                        .addComponent(btnTimKiem_HoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(816, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý hóa đơn ", new javax.swing.ImageIcon(getClass().getResource("/icon/bill.png")), jPanelHoaDon); // NOI18N

        jPanelDoanhThu.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelDoanhThuComponentShown(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 255));
        jLabel71.setText("Doanh thu theo nhân viên:");

        tbDoanhThu_SanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Ngày bán", "Tổng SL bán", "Tổng tiền thu được", "Lãi thu được"
            }
        ));
        jScrollPane20.setViewportView(tbDoanhThu_SanPham);

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 255));
        jLabel72.setText("Doanh thu theo sản phẩm:");

        tbDoanhThu_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Số lượng bán", "Ngày bán", "Số lượng sản phẩm bán được", "Tổng tiền bán được"
            }
        ));
        jScrollPane22.setViewportView(tbDoanhThu_NhanVien);

        jLabel74.setText("Ngày bán:");

        cbNgay1_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThang1_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNam1_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNgay2_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThang2_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNam2_DoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup5.add(rbNhanVien_DoanhThu);
        rbNhanVien_DoanhThu.setText("Nhân viên");
        rbNhanVien_DoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNhanVien_DoanhThuActionPerformed(evt);
            }
        });

        buttonGroup5.add(rbSanPham_DoanhThu);
        rbSanPham_DoanhThu.setText("Sản phẩm");
        rbSanPham_DoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSanPham_DoanhThuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDoanhThuLayout = new javax.swing.GroupLayout(jPanelDoanhThu);
        jPanelDoanhThu.setLayout(jPanelDoanhThuLayout);
        jPanelDoanhThuLayout.setHorizontalGroup(
            jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDoanhThuLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 959, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(rbSanPham_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelDoanhThuLayout.createSequentialGroup()
                            .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbNgay2_DoanhThu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbNgay1_DoanhThu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbThang1_DoanhThu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbThang2_DoanhThu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbNam1_DoanhThu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbNam2_DoanhThu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(226, 226, 226)
                            .addComponent(rbNhanVien_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1273, Short.MAX_VALUE))
        );
        jPanelDoanhThuLayout.setVerticalGroup(
            jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDoanhThuLayout.createSequentialGroup()
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDoanhThuLayout.createSequentialGroup()
                        .addComponent(rbNhanVien_DoanhThu)
                        .addGap(9, 9, 9)))
                .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(cbNgay1_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbThang1_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNam1_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNgay2_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbThang2_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNam2_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbSanPham_DoanhThu))
                .addGap(25, 25, 25)
                .addComponent(jLabel72)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(810, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý doanh thu", new javax.swing.ImageIcon(getClass().getResource("/icon/revenue.png")), jPanelDoanhThu); // NOI18N

        jPanelNhapHang.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelNhapHangComponentShown(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Phiếu nhập");

        tbChiTietPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTPN", "Mã phiếu nhập", "Sản phẩm", "Số lượng", "Tổng tiền", "Ghi chú"
            }
        ));
        tbChiTietPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbChiTietPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbChiTietPhieuNhap);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Chi tiết phiếu nhập:");

        tbPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu nhập", "Nhân viên", "Nhà phân phối", "Tổng tiền", "Ngày nhập ", "Ghi chú"
            }
        ));
        tbPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbPhieuNhap);

        btnTimKiem_PhieuNhap.setText("Tìm kiếm");
        btnTimKiem_PhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem_PhieuNhapActionPerformed(evt);
            }
        });

        jLabel8.setText("Mã phiếu nhập:");

        jLabel9.setText("Nhân viên:");

        cbNhanVien_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Ngày nhập:");

        cbNgay_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbThang_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbNam_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Nhà phân phối:");

        cbNhaPhanPhoi_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Tổng tiền");

        jLabel13.setText("Ghi chú:");

        txtGhiChu_PhieuNhap.setColumns(20);
        txtGhiChu_PhieuNhap.setRows(5);
        jScrollPane7.setViewportView(txtGhiChu_PhieuNhap);

        btnThem_PhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_PhieuNhap.setText("Thêm");
        btnThem_PhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_PhieuNhapActionPerformed(evt);
            }
        });

        btnSua_PhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_PhieuNhap.setText("Sửa");
        btnSua_PhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_PhieuNhapActionPerformed(evt);
            }
        });

        btnXoa_PhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_PhieuNhap.setText("Xóa");
        btnXoa_PhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_PhieuNhapActionPerformed(evt);
            }
        });

        btnReset_PhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_PhieuNhap.setText("RESET");
        btnReset_PhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_PhieuNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(cbNgay_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbThang_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbNam_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMa_PhieuNhap)
                            .addComponent(cbNhanVien_PhieuNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbNhaPhanPhoi_PhieuNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTien_PhieuNhap)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnThem_PhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua_PhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa_PhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset_PhieuNhap)
                        .addContainerGap(10, Short.MAX_VALUE))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMa_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbNhanVien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbNgay_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbThang_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbNam_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbNhaPhanPhoi_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTongTien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem_PhieuNhap)
                    .addComponent(btnSua_PhieuNhap)
                    .addComponent(btnXoa_PhieuNhap)
                    .addComponent(btnReset_PhieuNhap))
                .addGap(23, 23, 23))
        );

        jLabel14.setText("Mã CTPN:");

        jLabel15.setText("Mã phiếu nhập:");

        jLabel16.setText("Sản phẩm:");

        jLabel17.setText("Số lượng");

        jLabel18.setText("Tổng tiền:");

        jLabel19.setText("Ghi chú:");

        cbSanPham_CTPN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtGhiChu_CTPN.setColumns(20);
        txtGhiChu_CTPN.setRows(5);
        jScrollPane8.setViewportView(txtGhiChu_CTPN);

        btnThem_CTPN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnThem_CTPN.setText("Thêm");
        btnThem_CTPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_CTPNActionPerformed(evt);
            }
        });

        btnSua_CTPN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua_CTPN.setText("Sửa");
        btnSua_CTPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_CTPNActionPerformed(evt);
            }
        });

        btnXoa_CTPN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa_CTPN.setText("Xóa");
        btnXoa_CTPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_CTPNActionPerformed(evt);
            }
        });

        btnReset_CTPN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnReset_CTPN.setText("RESET");
        btnReset_CTPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset_CTPNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(btnThem_CTPN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua_CTPN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa_CTPN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset_CTPN))
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane8))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTongTien_CTPN))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSoLuong_CTPN))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbSanPham_CTPN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMaPhieuNhap_CTPN))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMa_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMa_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMaPhieuNhap_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbSanPham_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtSoLuong_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTongTien_CTPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem_CTPN)
                    .addComponent(btnSua_CTPN)
                    .addComponent(btnXoa_CTPN)
                    .addComponent(btnReset_CTPN))
                .addGap(35, 35, 35))
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        buttonGroup9.add(rbMaSP_PhieuNhap);
        rbMaSP_PhieuNhap.setText("Mã sản phẩm");

        buttonGroup9.add(rbTenSP_PhieuNhap);
        rbTenSP_PhieuNhap.setText("Tên sản phẩm");

        txtTKSP_PhieuNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKSP_PhieuNhapKeyReleased(evt);
            }
        });

        tbSanPham_PhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Hãng Sản Xuất", "Giá bán", "Tồn kho", "Đơn vị tính"
            }
        ));
        tbSanPham_PhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPham_PhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane27.setViewportView(tbSanPham_PhieuNhap);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbMaSP_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbTenSP_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTKSP_PhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbMaSP_PhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbTenSP_PhieuNhap))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(txtTKSP_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelNhapHangLayout = new javax.swing.GroupLayout(jPanelNhapHang);
        jPanelNhapHang.setLayout(jPanelNhapHangLayout);
        jPanelNhapHangLayout.setHorizontalGroup(
            jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                        .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem_PhieuNhap))))
                .addContainerGap(940, Short.MAX_VALUE))
        );
        jPanelNhapHangLayout.setVerticalGroup(
            jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)))
                        .addGap(7, 7, 7)
                        .addGroup(jPanelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelNhapHangLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnTimKiem_PhieuNhap)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(780, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhập hàng", new javax.swing.ImageIcon(getClass().getResource("/icon/import.png")), jPanelNhapHang); // NOI18N

        jPanelDangXuat.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelDangXuatComponentShown(evt);
            }
        });

        javax.swing.GroupLayout jPanelDangXuatLayout = new javax.swing.GroupLayout(jPanelDangXuat);
        jPanelDangXuat.setLayout(jPanelDangXuatLayout);
        jPanelDangXuatLayout.setHorizontalGroup(
            jPanelDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2279, Short.MAX_VALUE)
        );
        jPanelDangXuatLayout.setVerticalGroup(
            jPanelDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1456, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Đăng xuất", new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png")), jPanelDangXuat); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1)
                .addGap(367, 367, 367))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelDangXuatComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelDangXuatComponentShown
        frmXacNhanDangXuat frmdx = new frmXacNhanDangXuat();
        frmdx.show();
        this.dispose();
    }//GEN-LAST:event_jPanelDangXuatComponentShown

    private void jPanelNhapHangComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelNhapHangComponentShown
        layDuLieuSP_PN();
        txtTongTien_PhieuNhap.setEditable(false);
        txtTongTien_CTPN.setEditable(false);
        //txtMa_PhieuNhap.setEditable(false);
        txtMa_CTPN.setEditable(false);
        LayDuLieuPhieuNhap();
        cbNhanVien_PhieuNhap.setModel(LayDuLieucbb("NhanVien","TenNhanVien","MaNhanVien"));
        cbNhaPhanPhoi_PhieuNhap.setModel(LayDuLieucbb("NPP","TenNPP","MaNPP"));
        cbSanPham_CTPN.setModel(LayDuLieucbb("SanPham","TenSanPham","MaSanPham"));
        for (int i = 1; i < 32; i++) {
            cbNgay_PhieuNhap.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThang_PhieuNhap.addItem(String.valueOf(i));
        }
        for (int i = year; i > 1950; i--) {
            cbNam_PhieuNhap.addItem(String.valueOf(i));
        }
        cbNgay_PhieuNhap.setSelectedItem(String.valueOf(day));
        cbThang_PhieuNhap.setSelectedItem(String.valueOf(month));
        cbNam_PhieuNhap.setSelectedItem(String.valueOf(year));
    }//GEN-LAST:event_jPanelNhapHangComponentShown

    private void btnTimKiem_PhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem_PhieuNhapActionPerformed
        TimKiem_PhieuNhap(txtMa_PhieuNhap.getText());
    }//GEN-LAST:event_btnTimKiem_PhieuNhapActionPerformed

    private void btnReset_CTPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_CTPNActionPerformed
        ResCTPN();
    }//GEN-LAST:event_btnReset_CTPNActionPerformed

    private void btnXoa_CTPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_CTPNActionPerformed
        String MaPhieuNhap = txtMaPhieuNhap_CTPN.getText();
        String MaChiTietPhieuNhap = txtMa_CTPN.getText();
        String cautruyvan = "delete ChiTietPhieuNhap where MaCTPN =" + MaChiTietPhieuNhap;
        if(!MaChiTietPhieuNhap.equals("")){
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("đã xóa");
            ThongBao("Xóa thành công","Thông báo",1);
            LayDuLieuChiTietPhieuNhap(MaPhieuNhap);
            SetTongTien_PhieuNhap(MaPhieuNhap);
        }else
        ThongBao("bạn chưa chọn chi tiết phiếu nhập để xóa", "xóa", 2);

    }//GEN-LAST:event_btnXoa_CTPNActionPerformed

    private void btnSua_CTPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_CTPNActionPerformed
        String MaCTPN,MaPhieuNhap, MaSanPham, SoLuong, GhiChu;
        MaCTPN = txtMa_CTPN.getText();
        MaPhieuNhap = txtMa_PhieuNhap.getText();
        MaSanPham = GetCbbSelected(cbSanPham_CTPN).toString();
        SoLuong = txtSoLuong_CTPN.getText().trim();
        //Lấy giá nhập
        String laygia = "select SanPham.GiaNhap as GiaNhap from ChiTietPhieuNhap, SanPham  where ChiTietPhieuNhap.MaSanPham = SanPham.MaSanPham and ChiTietPhieuNhap.MaSanPham = "+ MaSanPham;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(laygia);
        int gianhap = 0;
        try {
            if(rs.next())
            {
                gianhap = rs.getInt("GiaNhap");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(gianhap);
        int soluong =Integer.parseInt(SoLuong);
        int TongTien = soluong* gianhap;
        GhiChu = txtGhiChu_CTPN.getText();
        String cautruyvan = "update ChiTietPhieuNhap set MaPhieuNhap = " + MaPhieuNhap + ",MaSanPham =" + MaSanPham + ",SoLuong = " + SoLuong + ",TongTien = " + TongTien + ",GhiChu= N'" + GhiChu + "' where MaCTPN = "+ MaCTPN;
        System.out.println(cautruyvan);
        if (!MaPhieuNhap.equals("") && !MaSanPham.equals("") && !SoLuong.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Sửa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
            LayDuLieuChiTietPhieuNhap(MaPhieuNhap);
            SetTongTien_PhieuNhap(MaPhieuNhap);
            SetTonKho_SanPham(MaSanPham, slbd_phieunhap-soluong);
            layDuLieuSP_PN();
        } else {
            System.out.println("Thất bại");
            ThongBao("Sửa thất bại","Lỗi",0);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSua_CTPNActionPerformed

    private void btnThem_CTPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_CTPNActionPerformed
//        String MaPhieuNhap, MaSanPham, SoLuong, GhiChu;
//        MaPhieuNhap = txtMa_PhieuNhap.getText();
//        MaSanPham = GetCbbSelected(cbSanPham_CTPN).toString();
//        SoLuong = txtSoLuong_CTPN.getText().trim();
//        //Lấy giá nhập
//        String laygia = "select SanPham.GiaNhap as GiaNhap from ChiTietPhieuNhap, SanPham  where ChiTietPhieuNhap.MaSanPham = SanPham.MaSanPham and ChiTietPhieuNhap.MaSanPham = "+ MaSanPham;
//        ResultSet rs = startclass.connection.ExcuteQueryGetTable(laygia);
//        int gianhap = 0;
//        try {
//            if(rs.next())
//            {
//                gianhap = rs.getInt("GiaNhap");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(gianhap);
//        int soluong =Integer.parseInt(SoLuong);
//        int TongTien = soluong* gianhap;
//        GhiChu = txtGhiChu_CTPN.getText();
//        String cautruyvan = "insert into ChiTietPhieuNhap(MaPhieuNhap, MaSanPham, SoLuong, TongTien, GhiChu) values(" + MaPhieuNhap + "," + MaSanPham + "," + SoLuong + "," + TongTien + ",N'" + GhiChu + "')";
//        System.out.println(cautruyvan);
//        if (!MaPhieuNhap.equals("") && !MaSanPham.equals("") && !SoLuong.equals("") ) {
//            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
//            System.out.println("Đã Thêm Thành Công");
//            ThongBao("Thêm thành công","Thông báo",1);
//        } else {
//            System.out.println("Thất bại");
//            ThongBao("Thêm thất bại","Lỗi",0);
//        }
//        LayDuLieuChiTietPhieuNhap(MaPhieuNhap);
//        SetTongTien_PhieuNhap(MaPhieuNhap);
//        SetTonKho_SanPham(MaSanPham,-soluong);
        String MaPhieuNhap, MaSanPham, SoLuong, GhiChu;
        MaPhieuNhap = txtMa_PhieuNhap.getText();
        MaSanPham = GetCbbSelected(cbSanPham_CTPN).toString();
        SoLuong = txtSoLuong_CTPN.getText().trim();

        // Lấy giá nhập từ cơ sở dữ liệu
        int gianhap = 0;
        try {
            String laygia = "SELECT SanPham.GiaNhap AS GiaNhap " +
                            "FROM  SanPham " +
                            "WHERE SanPham.MaSanPham = ?";
            PreparedStatement psGiaNhap = startclass.connection.prepareStatement(laygia);
            psGiaNhap.setString(1, MaSanPham);
            ResultSet rs = psGiaNhap.executeQuery();

            if (rs.next()) {
                gianhap = rs.getInt("GiaNhap");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Giá nhập: " + gianhap);

        int soluong = Integer.parseInt(SoLuong);
        int TongTien = soluong * gianhap;
        GhiChu = txtGhiChu_CTPN.getText();

        if (!MaPhieuNhap.equals("") && !MaSanPham.equals("") && !SoLuong.equals("")) {
            try {
                String cautruyvan = "INSERT INTO ChiTietPhieuNhap (MaPhieuNhap, MaSanPham, SoLuong, TongTien, GhiChu) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psInsert = startclass.connection.prepareStatement(cautruyvan);
                psInsert.setString(1, MaPhieuNhap);
                psInsert.setString(2, MaSanPham);
                psInsert.setString(3, SoLuong);
                psInsert.setInt(4, TongTien);
                psInsert.setString(5, GhiChu);
                psInsert.executeUpdate();

                System.out.println("Đã Thêm Thành Công");
                ThongBao("Thêm thành công", "Thông báo", 1);
                LayDuLieuChiTietPhieuNhap(MaPhieuNhap);
                SetTongTien_PhieuNhap(MaPhieuNhap);
                SetTonKho_SanPham(MaSanPham, -soluong);
                layDuLieuSP_PN(); 
            } catch (SQLException ex) {
                ex.printStackTrace();
                ThongBao("Thêm thất bại", "Lỗi", 0);
            }

            
        } else {
            System.out.println("Thất bại");
            ThongBao("Thêm thất bại", "Lỗi", 0);
        }
    }//GEN-LAST:event_btnThem_CTPNActionPerformed

    private void btnReset_PhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_PhieuNhapActionPerformed
        Res_PhieuNhap();
        LayDuLieuPhieuNhap();
    }//GEN-LAST:event_btnReset_PhieuNhapActionPerformed

    private void btnXoa_PhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_PhieuNhapActionPerformed
        if (!txtMa_PhieuNhap.getText().equals("")) {
            String MaPhieuNhap = txtMa_PhieuNhap.getText();
            String cautruyvan = "delete PhieuNhap where MaPhieuNhap = " + MaPhieuNhap;
            String cautruyvan2 = "delete ChiTietPhieuNhap where MaPhieuNhap = " + MaPhieuNhap;
//            String ctvKiemThu = "select count(MaCTPN) as SoChiTietPhieuNhap"
//            + " from PhieuNhap,ChiTietPhieuNhap where PhieuNhap.MaPhieuNhap=ChiTietPhieuNhap.MaPhieuNhap and PhieuNhap.MaPhieuNhap=" + MaPhieuNhap;
//            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu);
//            System.out.println(ctvKiemThu);
//            int so1 = 0;
//            try {
//                if (rs1.next()) {
//                    so1 = rs1.getInt("SoChiTietPhieuNhap");
//                    if (rs1.getInt("SoChiTietPhieuNhap") == 0) {
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan2);
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
                        System.out.println("đã xóa");
                        ThongBao("Xóa thành công","Thông báo",1);
                        LayDuLieuPhieuNhap();
//                    } else {
//                        ThongBao("không thể xóa bởi phiếu nhập đã có " + so1 + " chi tiết phiếu nhập!", "báo lỗi", 2);
//                    }
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            ThongBao("bạn chưa chọn phiếu nhập để xóa", "xóa", 2);
        }
    }//GEN-LAST:event_btnXoa_PhieuNhapActionPerformed

    private void btnSua_PhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_PhieuNhapActionPerformed
        String MaPhieuNhap,MaNPP, MaNhanVien, NgayLap, TongTien, GhiChu;
        MaPhieuNhap = txtMa_PhieuNhap.getText();
        MaNPP = GetCbbSelected(cbNhaPhanPhoi_PhieuNhap);
        MaNhanVien = GetCbbSelected(cbNhanVien_PhieuNhap);
        String ngay, thang, nam;
        ngay = cbNgay_PhieuNhap.getSelectedItem().toString();
        thang = cbThang_PhieuNhap.getSelectedItem().toString();
        nam = cbNam_PhieuNhap.getSelectedItem().toString();
        NgayLap = nam + "-" + thang + "-" + ngay;
        TongTien = txtTongTien_PhieuNhap.getText();
        GhiChu = txtGhiChu_PhieuNhap.getText();
        String cautruyvan = "update PhieuNhap set MaNPP =" + MaNPP + ", MaNhanVien =" + MaNhanVien + ",NgayNhap ='" + NgayLap +"', TongTien = "+ TongTien +", GhiChu = N'" + GhiChu + "' where MaPhieuNhap = "+ MaPhieuNhap;
        System.out.println(cautruyvan);
        if (!MaNPP.equals("") && !MaNhanVien.equals("") && !NgayLap.equals("") && !TongTien.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Sửa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            ThongBao("Không thể ", "Sửa Hóa Đơn", 2);
        }
        LayDuLieuPhieuNhap();
    }//GEN-LAST:event_btnSua_PhieuNhapActionPerformed

    private void btnThem_PhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_PhieuNhapActionPerformed
        String MaNPP, MaNhanVien, NgayLap, TongTien, GhiChu;
        MaNPP = GetCbbSelected(cbNhaPhanPhoi_PhieuNhap);
        MaNhanVien = GetCbbSelected(cbNhanVien_PhieuNhap);
        String ngay, thang, nam;
        ngay = cbNgay_PhieuNhap.getSelectedItem().toString();
        thang = cbThang_PhieuNhap.getSelectedItem().toString();
        nam = cbNam_PhieuNhap.getSelectedItem().toString();
        NgayLap = nam + "-" + thang + "-" + ngay;
        TongTien = txtTongTien_PhieuNhap.getText();
        GhiChu = txtGhiChu_PhieuNhap.getText();
        String cautruyvan = "insert into PhieuNhap(MaNPP,MaNhanVien,NgayNhap,TongTien,GhiChu) values(" + MaNPP + "," + MaNhanVien + ",'" + NgayLap +"',"+ 0 +",N'" + GhiChu + "')";
        System.out.println(cautruyvan);
        if (!MaNPP.equals("") && !MaNhanVien.equals("") && !NgayLap.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            ThongBao("Thêm thành công","Thông báo",1);
        } else {
            System.out.println("Thất bại");
            ThongBao("Thêm thất bại","Lỗi",0);
        }
        LayDuLieuPhieuNhap();
    }//GEN-LAST:event_btnThem_PhieuNhapActionPerformed

    private void tbPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuNhapMouseClicked
        int viTriVuaBam = tbPhieuNhap.getSelectedRow();
        String MaPhieuNhap = tbPhieuNhap.getValueAt(viTriVuaBam, 1).toString();
        txtMa_PhieuNhap.setText(tbPhieuNhap.getValueAt(viTriVuaBam, 1).toString());
        txtMaPhieuNhap_CTPN.setText(MaPhieuNhap);
        txtTongTien_PhieuNhap.setText(tbPhieuNhap.getValueAt(viTriVuaBam,4).toString());
        txtGhiChu_PhieuNhap.setText(tbPhieuNhap.getValueAt(viTriVuaBam, 6).toString());
        setSelectedCombobox(tbPhieuNhap.getValueAt(viTriVuaBam, 2).toString(), cbNhanVien_PhieuNhap);
        setSelectedCombobox(tbPhieuNhap.getValueAt(viTriVuaBam,3).toString(),cbNhaPhanPhoi_PhieuNhap);
        String ngaylap = tbPhieuNhap.getValueAt(viTriVuaBam, 5).toString();
        System.out.println("" + ngaylap);
        String strngay, strthang, strnam;
        strngay = ngaylap.substring(8, 10);
        strthang = ngaylap.substring(5, 7);
        strnam = ngaylap.substring(0, 4);
        int ngay, thang, nam;
        ngay = Integer.valueOf(strngay);
        thang = Integer.valueOf(strthang);
        nam = Integer.valueOf(strnam);
        cbNgay_PhieuNhap.setSelectedItem(String.valueOf(ngay));
        cbThang_PhieuNhap.setSelectedItem(String.valueOf(thang));
        cbNam_PhieuNhap.setSelectedItem(String.valueOf(nam));
        LayDuLieuChiTietPhieuNhap(MaPhieuNhap.toString());
    }//GEN-LAST:event_tbPhieuNhapMouseClicked

    private void tbChiTietPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChiTietPhieuNhapMouseClicked
        int viTriVuaBam = tbChiTietPhieuNhap.getSelectedRow();
        txtMa_CTPN.setText(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 1).toString());
        txtMaPhieuNhap_CTPN.setText(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 2).toString());
        setSelectedCombobox(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 3).toString(),cbSanPham_CTPN);
        txtSoLuong_CTPN.setText(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 4).toString());
        slbd_phieunhap = Integer.parseInt(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 4).toString());
        txtTongTien_CTPN.setText(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 5).toString());
        txtGhiChu_CTPN.setText(tbChiTietPhieuNhap.getValueAt(viTriVuaBam, 6).toString());
    }//GEN-LAST:event_tbChiTietPhieuNhapMouseClicked

    private void jPanelDoanhThuComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelDoanhThuComponentShown
        for (int i = 1; i < 32; i++) {
            cbNgay1_DoanhThu.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThang1_DoanhThu.addItem(String.valueOf(i));
        }
        for (int i = 2023; i > 1950; i--) {
            cbNam1_DoanhThu.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 32; i++) {
            cbNgay2_DoanhThu.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThang2_DoanhThu.addItem(String.valueOf(i));
        }
        for (int i = 2023; i > 1950; i--) {
            cbNam2_DoanhThu.addItem(String.valueOf(i));
        }
        cbNgay1_DoanhThu.setSelectedItem(String.valueOf(day));
        cbThang1_DoanhThu.setSelectedItem(String.valueOf(month));
        cbNam1_DoanhThu.setSelectedItem(String.valueOf(year));
        cbNgay2_DoanhThu.setSelectedItem(String.valueOf(day));
        cbThang2_DoanhThu.setSelectedItem(String.valueOf(month));
        cbNam2_DoanhThu.setSelectedItem(String.valueOf(year));
    }//GEN-LAST:event_jPanelDoanhThuComponentShown

    private void rbSanPham_DoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSanPham_DoanhThuActionPerformed
        String ngay1 = cbNgay1_DoanhThu.getSelectedItem().toString();
        String thang1 = cbThang1_DoanhThu.getSelectedItem().toString();
        String nam1 = cbNam1_DoanhThu.getSelectedItem().toString();
        String dk1 = nam1 + "-" +thang1+ "-" + ngay1;
        String ngay2 = cbNgay2_DoanhThu.getSelectedItem().toString();
        String thang2 = cbThang2_DoanhThu.getSelectedItem().toString();
        String nam2 = cbNam2_DoanhThu.getSelectedItem().toString();
        String dk2 = nam2 + "-" +thang2+ "-" + ngay2;
        ThongKe_SanPham(dk1,dk2);
    }//GEN-LAST:event_rbSanPham_DoanhThuActionPerformed

    private void rbNhanVien_DoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNhanVien_DoanhThuActionPerformed
        String ngay1 = cbNgay1_DoanhThu.getSelectedItem().toString();
        String thang1 = cbThang1_DoanhThu.getSelectedItem().toString();
        String nam1 = cbNam1_DoanhThu.getSelectedItem().toString();
        String dk1 = nam1 + "-" +thang1+ "-" + ngay1;
        String ngay2 = cbNgay2_DoanhThu.getSelectedItem().toString();
        String thang2 = cbThang2_DoanhThu.getSelectedItem().toString();
        String nam2 = cbNam2_DoanhThu.getSelectedItem().toString();
        String dk2 = nam2 + "-" +thang2+ "-" + ngay2;
        ThongKe_NhanVien(dk1,dk2);
    }//GEN-LAST:event_rbNhanVien_DoanhThuActionPerformed

    private void jPanelHoaDonComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelHoaDonComponentShown
        txtTongTien_HoaDon.setEditable(false);
        txtTongTien_CTHD.setEditable(false);
        //txtMa_HoaDon.setEditable(false);
        txtMa_CTHD.setEditable(false);
        layDuLieuHoaDon();
        cbKhachHang_HoaDon.setModel(LayDuLieucbb("KhachHang","TenKhachHang","MaKhachHang"));
        cbNhanVien_HoaDon.setModel(LayDuLieucbb("NhanVien","TenNhanVien","MaNhanVien"));
        cbSanPham_CTHD.setModel(LayDuLieucbb("SanPham","TenSanPham","MaSanPham"));
        for (int i = 1; i < 32; i++) {
            cbNgayLap_HoaDon.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThangLap_HoaDon.addItem(String.valueOf(i));
        }
        for (int i = year; i > 1950; i--) {
            cbNamLap_HoaDon.addItem(String.valueOf(i));
        }
        cbNgayLap_HoaDon.setSelectedItem(String.valueOf(day));
        cbThangLap_HoaDon.setSelectedItem(String.valueOf(month));
        cbNamLap_HoaDon.setSelectedItem(String.valueOf(year));
        layDuLieuSP();
    }//GEN-LAST:event_jPanelHoaDonComponentShown

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        Double tt = Double.parseDouble(lbTongTien.getText().toString());
        Double tkd = Double.parseDouble(txtTienKhachDua.getText().toString());
        lbTienTraKhach.setText(String.valueOf(tkd - tt));
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnKiemTra_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiemTra_HoaDonActionPerformed
        boolean kt = false;
        String tenkh= txtTenKH_HoaDon.getText().trim();
        String sdt= txtSDT_HoaDon.getText().trim();
        if(!tenkh.equals("") && !sdt.equals("")){
            String sql = "select * from KhachHang where TenKhachHang= N'"+tenkh+"' and SDT='"+sdt+"'";
            ResultSet rs = startclass.connection.ExcuteQueryGetTable(sql);
            String a = "";
            try {
                if(rs.next())
                {
                    kt = true;
                    a = rs.getString("TenKhachHang");
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(kt)
            {
                ThongBao("Khách hàng đã tồn tại trong csdl","Thông báo",1);
                setSelectedCombobox(a,cbKhachHang_HoaDon);
            }
            else
            {
                ThongBao("Khách hàng mới, thêm vào csdl","Thông báo",1);
                String ctv = "insert into KhachHang(TenKhachHang,SDT,LoaiKhachHang) values( N'"+tenkh+ "','"+sdt+"',0)";
                startclass.connection.ExcuteQueryUpdateDB(ctv);
                cbKhachHang_HoaDon.setModel(LayDuLieucbb("KhachHang","TenKhachHang","MaKhachHang"));
                setSelectedCombobox(tenkh, cbKhachHang_HoaDon); 
            }
        }else
        {
            ThongBao("Bạn chưa nhập đủ thông tin","Thông báo",2);
        }

    }//GEN-LAST:event_btnKiemTra_HoaDonActionPerformed

    private void btnReset_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_HoaDonActionPerformed
        ResHoaDon();
        layDuLieuHoaDon();
    }//GEN-LAST:event_btnReset_HoaDonActionPerformed

    private void btnXoa_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_HoaDonActionPerformed
        if (!txtMa_HoaDon.getText().equals("")) {
            String MaHoaDon = txtMa_HoaDon.getText();
            String ctv1 = "delete HoaDon where MaHoaDon=" + MaHoaDon;
//            String ctvKiemThu = "select count(MaCTHD) as SoChiTietPhieuMua"
//            + " from HoaDon,ChiTietHoaDon where HoaDon.MaHoaDon=ChiTietHoaDon.MaHoaDon and HoaDon.MaHoaDon=" + MaHoaDon;
            String ctv2 = "delete ChiTietHoaDon where MaHoaDon=" + MaHoaDon;
//            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu);

            //int so1 = 0;
//            try {
//                if (rs1.next()) {
//                    so1 = rs1.getInt("SoChiTietPhieuMua");
//                    if (rs1.getInt("SoChiTietPhieuMua") == 0) {
                        startclass.connection.ExcuteQueryUpdateDB(ctv2);
                        startclass.connection.ExcuteQueryUpdateDB(ctv1);
                        System.out.println("đã xóa");
                        layDuLieuHoaDon(); 
                        Object[] obj = new Object[]{"STT", "Mã CTHD", "Mã Hóa Đơn", "Sản Phẩm", "Số Lượng", "tổng tiền", "Ghi Chú"};
                        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
                        tbCTHD.setModel(tableModel); 
//                    } else {
//                        ThongBao("không thể xóa bởi hóa đơn đã có " + so1 + " chi tiết hóa đơn!", "báo lỗi", 2);
//                    }
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            ThongBao("bạn chưa chọn hóa đơn để xóa", "xóa", 2);
        }
        
    }//GEN-LAST:event_btnXoa_HoaDonActionPerformed

    private void btnSua_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_HoaDonActionPerformed
        String MaHoaDon,MaKhachHang, MaNhanVien, NgayLapHoaDon, TongTien, GhiChu;
        MaHoaDon = txtMa_HoaDon.getText();
        MaKhachHang = GetCbbSelected(cbKhachHang_HoaDon);
        MaNhanVien = GetCbbSelected(cbNhanVien_HoaDon);
        String ngay, thang, nam;
        ngay = cbNgayLap_HoaDon.getSelectedItem().toString();
        thang = cbThangLap_HoaDon.getSelectedItem().toString();
        nam = cbNamLap_HoaDon.getSelectedItem().toString();
        NgayLapHoaDon = nam + "-" + thang + "-" + ngay;
        TongTien = txtTongTien_HoaDon.getText();
        GhiChu = txtGhiChu_HoaDon.getText();
        String cautruyvan = "update HoaDon set MaKhachHang =" + MaKhachHang + ", MaNhanVien =" + MaNhanVien + ",NgayLapHD ='" + NgayLapHoaDon +"', TongTien = "+ TongTien +", GhiChu = N'" + GhiChu + "' where MaHoaDon = "+ MaHoaDon;
        System.out.println(cautruyvan);
        if (!MaKhachHang.equals("") && !MaNhanVien.equals("") && !NgayLapHoaDon.equals("") && !TongTien.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Sửa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            ThongBao("Không thể ", "Sửa Hóa Đơn", 2);
        }
        layDuLieuHoaDon();
    }//GEN-LAST:event_btnSua_HoaDonActionPerformed

    private void btnThem_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_HoaDonActionPerformed
        String MaKhachHang, MaNhanVien, NgayLapHoaDon, TongTien, GhiChu;
        MaKhachHang = GetCbbSelected(cbKhachHang_HoaDon);
        MaNhanVien = GetCbbSelected(cbNhanVien_HoaDon);
        String ngay, thang, nam;
        ngay = cbNgayLap_HoaDon.getSelectedItem().toString();
        thang = cbThangLap_HoaDon.getSelectedItem().toString();
        nam = cbNamLap_HoaDon.getSelectedItem().toString();
        NgayLapHoaDon = nam + "-" + thang + "-" + ngay;
        TongTien = txtTongTien_HoaDon.getText();
        GhiChu = txtGhiChu_HoaDon.getText();
        String cautruyvan = "insert into HoaDon(MaKhachHang,MaNhanVien,NgayLapHD,TongTien,GhiChu) values(" + MaKhachHang + "," + MaNhanVien + ",'" + NgayLapHoaDon +"',"+ 0 +",N'" + GhiChu + "')";
        System.out.println(cautruyvan);
        if (!MaKhachHang.equals("") && !MaNhanVien.equals("") && !NgayLapHoaDon.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            ThongBao("Thêm thành công","Thông báo",1);
        } else {
            System.out.println("Thất bại");
            ThongBao("Thêm thất bại","Lỗi",0);
        }
        layDuLieuHoaDon();
    }//GEN-LAST:event_btnThem_HoaDonActionPerformed

    private void cbNamLap_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNamLap_HoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNamLap_HoaDonActionPerformed

    private void tbSanPham_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPham_HoaDonMouseClicked
        int vt = tbSanPham_HoaDon.getSelectedRow();
        setSelectedCombobox(tbSanPham_HoaDon.getValueAt(vt, 1).toString(),cbSanPham_CTHD);
        if(TBconit(GetCbbSelected(cbSanPham_CTHD).toString()))
        {
            ThongBao("Lượng hàng của sản phẩm còn ít. Mau nhập hàng đi nào","Thông báo",1);
        }
    }//GEN-LAST:event_tbSanPham_HoaDonMouseClicked

    private void txtTKSP_HoaDonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKSP_HoaDonKeyReleased

        TimKiemSP_HoaDon(txtTKSP_HoaDon.getText().trim());
    }//GEN-LAST:event_txtTKSP_HoaDonKeyReleased

    private void btnHoanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanHangActionPerformed

        String rsTB = "";
        String maCTHD = txtMa_CTHD.getText().trim();
        int soluong = 0;

        try {
            soluong = Integer.parseInt(txtSoLuong_CTHD.getText().trim());
            System.out.println(soluong);
        } catch (NumberFormatException ex) {
            rsTB += " Số lượng không hợp lệ";
        }

        if (maCTHD.isEmpty()) {
            rsTB += " Bạn chưa nhập mã chi tiết HD";
        } else {
            if (soluong <= 0 || soluong > slbd) {
                rsTB += " Số lượng hoàn phải lớn hơn 0 và nhỏ hơn hoặc bằng số lượng đã mua";
            } else {
                setCTHD(maCTHD, slbd-soluong);
                layDuLieuCTHD(txtMa_HoaDon.getText());
                SetTongTien_HoaDon(txtMa_HoaDon.getText());
                layDuLieuSP();
                ThongBao("Hoàn trả hàng thành công", "Thông báo", 1);
            }
        }

        if (!rsTB.isEmpty()) {
            ThongBao(rsTB, "Thông báo", 2);
        }
    }//GEN-LAST:event_btnHoanHangActionPerformed

    private void btnReset_CTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_CTHDActionPerformed
        ResCTHD();
    }//GEN-LAST:event_btnReset_CTHDActionPerformed

    private void btnXoa_CTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_CTHDActionPerformed
        String MaHoaDon = txtMaHoaDon_CTHD.getText();
        String MaChiTietHoaDon = txtMa_CTHD.getText();
        String cautruyvan = "delete ChiTietHoaDon where MaCTHD=" + MaChiTietHoaDon;
        if(!MaChiTietHoaDon.equals("")){
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("đã xóa");
            ThongBao("Xóa thành công","Thông báo",1);
            layDuLieuCTHD(MaHoaDon);
            SetTongTien_HoaDon(MaHoaDon);
        }
        else
        ThongBao("bạn chưa chọn mã chi tiết hóa đơn để xóa", "xóa", 2);
    }//GEN-LAST:event_btnXoa_CTHDActionPerformed

    private void btnSua_CTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_CTHDActionPerformed
        String MaCTHD,MaHoaDon, MaSanPham, SoLuong, GhiChu;
        MaCTHD = txtMa_CTHD.getText();
        MaHoaDon = txtMa_HoaDon.getText();
        MaSanPham = GetCbbSelected(cbSanPham_CTHD).toString();
        SoLuong = txtSoLuong_CTHD.getText().trim();
        //Lấy giá bán
        String laygia = "select SanPham.GiaBan as GiaBan from ChiTietHoaDon, SanPham  where ChiTietHoaDon.MaSanPham = SanPham.MaSanPham and ChiTietHoaDon.MaSanPham = "+ MaSanPham;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(laygia);
        int giaban = 0;
        try {
            if(rs.next())
            {
                giaban = rs.getInt("GiaBan");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(giaban);
        int soluong =Integer.parseInt(SoLuong);
        int TongTien = soluong* giaban;
        GhiChu = txtGhiChu_CTHD.getText();
        String cautruyvan = "update ChiTietHoaDon set MaHoaDon = " + MaHoaDon + ",MaSanPham =" + MaSanPham + ",SoLuong = " + SoLuong + ",TongTien = " + TongTien + ",GhiChu= N'" + GhiChu + "' where MaCTHD = "+ MaCTHD;
        System.out.println(cautruyvan);
        if (!MaHoaDon.equals("") && !MaSanPham.equals("") && !SoLuong.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã sửa Thành Công");
            layDuLieuCTHD(MaHoaDon);
            SetTongTien_HoaDon(MaHoaDon);
            SetTonKho_SanPham(MaSanPham,soluong - slbd);
            ThongBao("Sửa thành công","Thông báo",1);
            layDuLieuSP();
        } else {
            System.out.println("Thất bại");
            ThongBao("Sửa thất bại","Lỗi",0);
        } 
    }//GEN-LAST:event_btnSua_CTHDActionPerformed

    private void btnThem_CTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_CTHDActionPerformed
        String MaHoaDon, MaSanPham, SoLuong, GhiChu;
        MaHoaDon = txtMa_HoaDon.getText();
        MaSanPham = GetCbbSelected(cbSanPham_CTHD).toString();
        SoLuong = txtSoLuong_CTHD.getText().trim();

        // Kiểm tra và lấy giá bán từ cơ sở dữ liệu
        int giaban = 0;
        try {
            String laygia = "SELECT SanPham.GiaBan AS GiaBan " +
                            "FROM SanPham " +
                            "WHERE SanPham.MaSanPham = ?";
            PreparedStatement psGiaBan = startclass.connection.prepareStatement(laygia);
            psGiaBan.setString(1, MaSanPham);
            ResultSet rs = psGiaBan.executeQuery();

            if (rs.next()) {
                giaban = rs.getInt("GiaBan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Giá bán: " + giaban);

        int soluong = Integer.parseInt(SoLuong);
        int TongTien = soluong * giaban;
        GhiChu = txtGhiChu_CTHD.getText();

        if (ConHang(MaSanPham)) {
            if (!MaHoaDon.equals("") && !MaSanPham.equals("") && !SoLuong.equals("")) {
                try {
                    String cautruyvan = "INSERT INTO ChiTietHoaDon(MaHoaDon, MaSanPham, SoLuong, TongTien, GhiChu) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement psInsert = startclass.connection.prepareStatement(cautruyvan);
                    psInsert.setString(1, MaHoaDon);
                    psInsert.setString(2, MaSanPham);
                    psInsert.setInt(3, soluong);
                    psInsert.setInt(4, TongTien);
                    psInsert.setString(5, GhiChu);
                    psInsert.executeUpdate();

                    System.out.println("Đã Thêm Thành Công");

                    SetTonKho_SanPham(MaSanPham, soluong);
                    layDuLieuCTHD(MaHoaDon);
                    SetTongTien_HoaDon(MaHoaDon);
                    layDuLieuSP();
                    ThongBao("Thêm thành công", "Thông báo", 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Xử lý lỗi khi thêm dữ liệu vào cơ sở dữ liệu
                    ThongBao("Thêm thất bại", "Lỗi", 0);
                }
            } else {
                ThongBao("Nhập đầy đủ thông tin", "Thông báo", 2);
            }
        } else {
            ThongBao("Đã hết hàng", "Không thể bán", 2);
        }
    }//GEN-LAST:event_btnThem_CTHDActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmXuatHoaDonToExcel xuathd = new frmXuatHoaDonToExcel();
        xuathd.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTimKiem_HoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem_HoaDonActionPerformed
        TimKiem_HoaDon(txtMa_HoaDon.getText());
    }//GEN-LAST:event_btnTimKiem_HoaDonActionPerformed
    int slbd =0;
    private void tbCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCTHDMouseClicked
        int viTriDongVuaBam = tbCTHD.getSelectedRow();
        txtMa_CTHD.setText(tbCTHD.getValueAt(viTriDongVuaBam, 1).toString());
        txtMaHoaDon_CTHD.setText(tbCTHD.getValueAt(viTriDongVuaBam, 2).toString());
        txtSoLuong_CTHD.setText(tbCTHD.getValueAt(viTriDongVuaBam, 4).toString());
        slbd = Integer.parseInt(tbCTHD.getValueAt(viTriDongVuaBam, 4).toString());
        txtTongTien_CTHD.setText(tbCTHD.getValueAt(viTriDongVuaBam, 5).toString());
        txtGhiChu_CTHD.setText(tbCTHD.getValueAt(viTriDongVuaBam, 6).toString());
        setSelectedCombobox(tbCTHD.getValueAt(viTriDongVuaBam, 3).toString(),cbSanPham_CTHD);
    }//GEN-LAST:event_tbCTHDMouseClicked

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        int viTriDongVuaBam = tbHoaDon.getSelectedRow();
        String MaHoaDon = tbHoaDon.getValueAt(viTriDongVuaBam, 1).toString();
        mahoadon = tbHoaDon.getValueAt(viTriDongVuaBam, 1).toString();
        txtMa_HoaDon.setText(tbHoaDon.getValueAt(viTriDongVuaBam, 1).toString());
        String ngaylap = tbHoaDon.getValueAt(viTriDongVuaBam, 4).toString();
        System.out.println("" + ngaylap);
        String strngay, strthang, strnam;
        strngay = ngaylap.substring(8, 10);
        strthang = ngaylap.substring(5, 7);
        strnam = ngaylap.substring(0, 4);
        int ngay, thang, nam;
        ngay = Integer.valueOf(strngay);
        thang = Integer.valueOf(strthang);
        nam = Integer.valueOf(strnam);
        cbNgayLap_HoaDon.setSelectedItem(String.valueOf(ngay));
        cbThangLap_HoaDon.setSelectedItem(String.valueOf(thang));
        cbNamLap_HoaDon.setSelectedItem(String.valueOf(nam));
        txtTongTien_HoaDon.setText(tbHoaDon.getValueAt(viTriDongVuaBam, 5).toString());
        lbTongTien.setText(tbHoaDon.getValueAt(viTriDongVuaBam, 5).toString());
        txtGhiChu_HoaDon.setText(tbHoaDon.getValueAt(viTriDongVuaBam, 6).toString());
        setSelectedCombobox(tbHoaDon.getValueAt(viTriDongVuaBam, 3).toString(), cbNhanVien_HoaDon);
        setSelectedCombobox(tbHoaDon.getValueAt(viTriDongVuaBam, 2).toString(), cbKhachHang_HoaDon);
        layDuLieuCTHD(MaHoaDon);
        txtMaHoaDon_CTHD.setText(MaHoaDon.toString());
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void jPanel3ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel3ComponentShown
        layDuLieuNPP();
        txtMa_NPP.setEditable(false);
    }//GEN-LAST:event_jPanel3ComponentShown

    private void btnReset_NPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_NPPActionPerformed
        ResNPP();
    }//GEN-LAST:event_btnReset_NPPActionPerformed

    private void btnXoa_NPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_NPPActionPerformed
        String MaNPP = txtMa_NPP.getText();
        if (!MaNPP.equals("")) {
            String cautruyvan = "delete NPP where MaNPP=" + MaNPP;
            String ctvKiemThu = "select count(MaPhieuNhap) as SoPhieuNhap"
            + " from NPP,PhieuNhap where NPP.MaNPP=PhieuNhap.MaNPP"
            + " and  NPP.MaNPP=" + MaNPP;
            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu);
            System.out.println(ctvKiemThu);
            int so1 = 0;
            try {
                if (rs1.next()) {
                    so1 = rs1.getInt("SoPhieuNhap");
                    if (rs1.getInt("SoPhieuNhap") == 0) {
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
                        System.out.println("đã xóa");
                        layDuLieuNPP();
                        ResNPP();
                    } else {
                        ThongBao("không thể xóa bởi NPP đã có " + so1 + " Phiếu Nhập!", "báo lỗi", 2);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ThongBao("bạn chưa nhập mã NPP!", "tôi cần sự chăm chỉ của bạn!hãy click vô bảng đã!", 2);
        }
    }//GEN-LAST:event_btnXoa_NPPActionPerformed

    private void btnSua_NPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_NPPActionPerformed
        String MaNPP, TenNPP, DiaChi, SDT, Email, GhiChu;
        MaNPP = txtMa_NPP.getText();
        TenNPP = txtTen_NPP.getText();
        DiaChi = txtDiaChi_NPP.getText();
        SDT = txtSoDienThoai_NPP.getText();
        Email = txtEmail_NPP.getText();
        GhiChu = txtGhiChu_KhachHang.getText();
        String cautruyvan = "update NPP set TenNPP = N'"
        + TenNPP + "' ,DiaChi = N'" + DiaChi + "' ,SDT ='" + SDT
        + "',Email ='" + Email + "', GhiChu = N'" + GhiChu + "' where MaNPP ="+MaNPP;
        System.out.println(cautruyvan);
        if (!TenNPP.equals("") && !DiaChi.equals("") && !SDT.equals("") && !Email.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Sửa thành công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            ThongBao("Không thể sửa NPP", "lỗi", 2);
        }
        layDuLieuNPP();
    }//GEN-LAST:event_btnSua_NPPActionPerformed

    private void btnThem_NPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_NPPActionPerformed
        String MaNPP, TenNPP, DiaChi, SDT, Email, GhiChu;
        MaNPP = txtMa_NPP.getText();
        TenNPP = txtTen_NPP.getText();
        DiaChi = txtDiaChi_NPP.getText();
        SDT = txtSoDienThoai_NPP.getText();
        Email = txtEmail_NPP.getText();
        GhiChu = txtGhiChu_KhachHang.getText();
        String cautruyvan = "insert into NPP values("
        + " N'" + TenNPP + "' , N'" + DiaChi + "' ,'" + SDT
        + "','" + Email + "', N'" + GhiChu + "')";
        System.out.println(cautruyvan);
        if (!TenNPP.equals("") && !DiaChi.equals("") && !SDT.equals("") && !Email.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            ThongBao("Thêm thành công","Thông báo",1);
        } else {
            System.out.println("Thất bại");
            ThongBao("Thêm thất bại","Lỗi",0);
        }
        layDuLieuNPP();
    }//GEN-LAST:event_btnThem_NPPActionPerformed

    private void tbNhaPhanPhoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhaPhanPhoiMouseClicked
        int viTriDongVuaBam = tbNhaPhanPhoi.getSelectedRow();
        txtMa_NPP.setText(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam,1).toString());
        txtTen_NPP.setText(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam,2).toString());
        txtDiaChi_NPP.setText(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam,3).toString());
        txtSoDienThoai_NPP.setText(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam, 4).toString());
        txtEmail_NPP.setText(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam,5).toString());
        txtGhiChu_NPP.setText(getStringValue(tbNhaPhanPhoi.getValueAt(viTriDongVuaBam,6).toString()));
    }//GEN-LAST:event_tbNhaPhanPhoiMouseClicked

    private void jPanelTaiKhoanComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelTaiKhoanComponentShown
        layDuLieuTaiKhoan();
        cbTenNhanVien_TaiKhoan.setModel(LayDuLieucbb("NhanVien","TenNhanVien","MaNhanVien"));
        txtID_TaiKhoan.setEditable(false);
    }//GEN-LAST:event_jPanelTaiKhoanComponentShown

    private void btnRes_TaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRes_TaiKhoanActionPerformed
        ResTaiKhoan();       // TODO add your handling code here:
    }//GEN-LAST:event_btnRes_TaiKhoanActionPerformed

    private void btnSua_TaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_TaiKhoanActionPerformed
        String ID, MaNhanVien, TenDangNhap, Password, Quyen, GhiChu;
        ID = txtID_TaiKhoan.getText();
        MaNhanVien = GetCbbSelected(cbTenNhanVien_TaiKhoan);
        TenDangNhap = txtUsername_TaiKhoan.getText();
        Password = txtPassword_TaiKhoan.getText();
        if (rbQuyen_ad.isSelected()) {
            Quyen = "1";
        } else {
            Quyen = "0";
        }
        GhiChu = txtGhiChu_TaiKhoan.getText();
        String cautruyvan = "update  Users set MaNhanVien=" + MaNhanVien
        + " ,UserName='" + TenDangNhap + "' ,PassWord= '" + Password + "' ,Quyen= '" + Quyen
        + "',GhiChu= N'" + GhiChu + "'Where ID=" + ID;
        boolean kiemtra = true;
        if (kiemtra) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Sửa Thành Công tài khoản có id=" + ID);
            ThongBao("Đã Sửa Thành Công tài khoản có id=" + ID,"Thông báo",1);
        } else {
            ThongBao("Không thể Sửa tài Khoản với Tên đăng nhập là =" + TenDangNhap, "lỗi", 2);
        }
        layDuLieuTaiKhoan();
    }//GEN-LAST:event_btnSua_TaiKhoanActionPerformed

    private void btnXoa_TaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_TaiKhoanActionPerformed
        String ID;
        ID = txtID_TaiKhoan.getText();
        String cautruyvan = "delete from Users where ID = "+ ID;
        if(!ID.equals(""))
        {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã xóa thành công");
            ThongBao("Xóa thành công","Thông báo",1);
        }else
        {
            ThongBao("Không thể xóa tài khoản", "lỗi",2);
        }
        layDuLieuTaiKhoan();
    }//GEN-LAST:event_btnXoa_TaiKhoanActionPerformed

    private void btnThem_TaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_TaiKhoanActionPerformed
        String ID, MaNhanVien, TenDangNhap, Password, Quyen, GhiChu;
        ID = txtID_TaiKhoan.getText();
        MaNhanVien = GetCbbSelected(cbTenNhanVien_TaiKhoan);
        TenDangNhap = txtUsername_TaiKhoan.getText();
        Password = txtPassword_TaiKhoan.getText();
        if (rbQuyen_ad.isSelected()) {
            Quyen = "1";
        } else {
            Quyen = "0";
        }
        GhiChu = txtGhiChu_TaiKhoan.getText();
        String cautruyvan = "insert into Users values(" + MaNhanVien
        + " ,'" + TenDangNhap + "' , '" + Password + "' ,'" + Quyen
        + "', N'" + GhiChu + "')";
        System.out.println(cautruyvan);
        if (!MaNhanVien.equals("") && !TenDangNhap.equals("") && !Password.equals("") && !Quyen.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            ThongBao("Thêm thành công","Thông báo",1);
        } else {
            ThongBao("Không thể Thêm tài Khoản", "lỗi", 2);
        }
        layDuLieuTaiKhoan();
    }//GEN-LAST:event_btnThem_TaiKhoanActionPerformed

    private void tbTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTaiKhoanMouseClicked
        int viTriDongVuaBam = tbTaiKhoan.getSelectedRow();
        txtID_TaiKhoan.setText(tbTaiKhoan.getValueAt(viTriDongVuaBam, 1).toString());
        txtUsername_TaiKhoan.setText(tbTaiKhoan.getValueAt(viTriDongVuaBam, 3).toString());
        txtPassword_TaiKhoan.setText(tbTaiKhoan.getValueAt(viTriDongVuaBam, 4).toString());
        txtGhiChu_TaiKhoan.setText(tbTaiKhoan.getValueAt(viTriDongVuaBam, 6).toString());
        setSelectedCombobox(tbTaiKhoan.getValueAt(viTriDongVuaBam, 2).toString(), cbTenNhanVien_TaiKhoan);
        String quyen = getStringValue(tbTaiKhoan.getValueAt(viTriDongVuaBam, 5).toString());
        if (quyen.equalsIgnoreCase("Admin")) {
            rbQuyen_ad.setSelected(true);
            rbQuyen_user.setSelected(false);
        } else {
            rbQuyen_ad.setSelected(false);
            rbQuyen_user.setSelected(true);
        }
    }//GEN-LAST:event_tbTaiKhoanMouseClicked

    private void jPanelKhachHangComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelKhachHangComponentShown
        layDuLieuKhachHang();       // TODO add your handling code here:
        for (int i = 1; i < 32; i++) {
            cbNgay_KhachHang.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThang_KhachHang.addItem(String.valueOf(i));
        }
        for (int i = year; i > 1950; i--) {
            cbNam_KhachHang.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 70; i++) {
            cbTimKiemTuoi1_KhachHang.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 70; i++) {
            cbTimKiemTuoi2_KhachHang.addItem(String.valueOf(i));
        }
        rbNam_KhachHang.setSelected(true);
        cbNgay_KhachHang.setSelectedItem(String.valueOf(day));
        cbThang_KhachHang.setSelectedItem(String.valueOf(month));
        cbNam_KhachHang.setSelectedItem(String.valueOf(year));
        txtMaKhachHang.setEditable(false);

    }//GEN-LAST:event_jPanelKhachHangComponentShown

    private void cbTimKiemTuoi2_KhachHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTimKiemTuoi2_KhachHangItemStateChanged
        if (ckboxTimKiem_KhachHang.isSelected()) {

            TimKiemKhachHang("year(Getdate())-  year(Ngaysinh) >"
                + " " + cbTimKiemTuoi1_KhachHang.getSelectedItem().toString() + " and year(Getdate())- "
                + " year(Ngaysinh)<" + cbTimKiemTuoi2_KhachHang.getSelectedItem().toString());
            ResKhachHang();
        }
    }//GEN-LAST:event_cbTimKiemTuoi2_KhachHangItemStateChanged

    private void cbTimKiemTuoi1_KhachHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTimKiemTuoi1_KhachHangItemStateChanged
        DefaultComboBoxModel cbbmodel = new DefaultComboBoxModel();
        cbTimKiemTuoi2_KhachHang.setModel(cbbmodel);
        for (int i = cbTimKiemTuoi1_KhachHang.getSelectedIndex() + 1; i < 80; i++) {
            cbTimKiemTuoi2_KhachHang.addItem(String.valueOf(i));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbTimKiemTuoi1_KhachHangItemStateChanged

    private void txtTimKiemTen_KhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTen_KhachHangKeyReleased
        if (ckboxTimKiem_KhachHang.isSelected()) {
            TimKiemKhachHang("TenKhachHang like N'%" + txtTimKiemTen_KhachHang.getText() + "%'");
            ResKhachHang();
        }
    }//GEN-LAST:event_txtTimKiemTen_KhachHangKeyReleased

    private void ckboxTimKiem_KhachHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ckboxTimKiem_KhachHangItemStateChanged
        if (ckboxTimKiem_KhachHang.isSelected()) {
            layDuLieuKhachHang();
        }
    }//GEN-LAST:event_ckboxTimKiem_KhachHangItemStateChanged

    private void btnRes_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRes_KhachHangActionPerformed
        ResKhachHang();
        layDuLieuKhachHang();
    }//GEN-LAST:event_btnRes_KhachHangActionPerformed

    private void btnXoa_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_KhachHangActionPerformed
        String MaKhachHang = txtMaKhachHang.getText();
        if (!MaKhachHang.equals("")) {
            String cautruyvan = "delete KhachHang where MaKhachHang=" + MaKhachHang;
            String ctvKiemThu = "select count(MaHoaDon) as SoPhieuMua"
            + " from KhachHang,HoaDon where KhachHang.MaKhachHang=HoaDon.MaKhachHang"
            + " and  KhachHang.MaKhachHang=" + MaKhachHang;
            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu);
            System.out.println(ctvKiemThu);
            int so1 = 0;
            try {
                if (rs1.next()) {
                    so1 = rs1.getInt("SoPhieuMua");
                    if (rs1.getInt("SoPhieuMua") == 0) {
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
                        System.out.println("đã xóa");
                        layDuLieuKhachHang();
                        ResKhachHang();
                    } else {
                        ThongBao("không thể xóa bởi Khách Hàng đã có " + so1 + " hóa đơn!", "báo lỗi", 2);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            ThongBao("bạn chưa nhập Mã khách hàng", "lỗi khi cố muốn xóa mà không thèm nhập mã", 2);
        }
    }//GEN-LAST:event_btnXoa_KhachHangActionPerformed

    private void btnSua_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_KhachHangActionPerformed
        String MaKhachHang, TenKhachHang, NgaySinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu;
        MaKhachHang = txtMaKhachHang.getText();
        TenKhachHang = txtTenKhachHang.getText();
        String ngay, thang, nam;
        ngay = cbNgay_KhachHang.getSelectedItem().toString();
        thang = cbThang_KhachHang.getSelectedItem().toString();
        nam = cbNam_KhachHang.getSelectedItem().toString();
        NgaySinh = nam + "-" + thang + "-" + ngay;
        if (rbNam_KhachHang.isSelected()) {
            GioiTinh = "1";
        } else {
            GioiTinh = "0";
        }
        DiaChi = txtDiaChi_KhachHang.getText();
        SDT = txtSoDienThoai_KhachHang.getText();
        if(rbOld_KhachHang.isSelected())
        {
            LoaiKhachHang = "1";
        }
        else
        {
            LoaiKhachHang = "0";
        }
        GhiChu = txtGhiChu_KhachHang.getText();
        String cautruyvan = "update  KhachHang set "
        + "TenKhachHang= N'" + TenKhachHang + "' , NgaySinh='" + NgaySinh + "' ,GioiTinh=" + GioiTinh
        + ",DiaChi=N'" + DiaChi + "',SDT='" + SDT + "',LoaiKhachHang=" + LoaiKhachHang + ",GhiChu= N'" + GhiChu + "'where MaKhachHang=" + MaKhachHang;
        System.out.println(cautruyvan);
        if (!TenKhachHang.equals("") && !NgaySinh.equals("") && !GioiTinh.equals("") && !DiaChi.equals("") && !SDT.equals("") && !LoaiKhachHang.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã sủa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            System.out.println("Sửa thất bại");
            ThongBao("Sửa thất bại","Lỗi",0);
        }
        layDuLieuKhachHang();
    }//GEN-LAST:event_btnSua_KhachHangActionPerformed

    private void btnThem_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_KhachHangActionPerformed
        String MaKhachHang, TenKhachHang, NgaySinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, GhiChu;
        MaKhachHang = txtMaKhachHang.getText();
        TenKhachHang = txtTenKhachHang.getText();
        String ngay, thang, nam;
        ngay = cbNgay_KhachHang.getSelectedItem().toString();
        thang = cbThang_KhachHang.getSelectedItem().toString();
        nam = cbNam_KhachHang.getSelectedItem().toString();
        NgaySinh = nam + "-" + thang + "-" + ngay;
        if (rbNam_KhachHang.isSelected()) {
            GioiTinh = "1";
        } else {
            GioiTinh = "0";
        }
        DiaChi = txtDiaChi_KhachHang.getText();
        SDT = txtSoDienThoai_KhachHang.getText();
        if(rbOld_KhachHang.isSelected())
        {
            LoaiKhachHang = "1";
        }else
        {
            LoaiKhachHang = "0";
        }
        GhiChu = txtGhiChu_KhachHang.getText();
        String cautruyvan = "insert into KhachHang values("
        + " N'" + TenKhachHang + "' , '" + NgaySinh + "' ," + GioiTinh
        + ",N'" + DiaChi + "','" + SDT + "'," + LoaiKhachHang + ", N'" + GhiChu + "')";
        if (!TenKhachHang.equals("")  && !NgaySinh.equals("") && !GioiTinh.equals("") && !DiaChi.equals("") && !SDT.equals("") && !LoaiKhachHang.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println(cautruyvan);
            ThongBao("Thêm thành công","Thông báo",1);

        } else {
            ThongBao("Thêm thất bại","Lỗi",2);
        }
        layDuLieuKhachHang();
    }//GEN-LAST:event_btnThem_KhachHangActionPerformed

    private void tbKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhachHangMouseClicked
        int viTriDongVuaBam = tbKhachHang.getSelectedRow();
        txtMaKhachHang.setText(getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 1)));
        txtTenKhachHang.setText(getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 2)));
        txtDiaChi_KhachHang.setText(getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 5)));
        txtSoDienThoai_KhachHang.setText(getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 6)));
        txtGhiChu_KhachHang.setText(getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 8)));
        String loaikhachhang = getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 7));
        if (loaikhachhang.equalsIgnoreCase("Thân thiết")) {
            rbOld_KhachHang.setSelected(true);
            rbNew_KhachHang.setSelected(false);
        } else {
            rbOld_KhachHang.setSelected(false);
            rbNew_KhachHang.setSelected(true);
        }
        String gioitinh = getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 4));
        if (gioitinh.equals("Nam")) {
            rbNam_KhachHang.setSelected(true);
            rbNu_KhachHang.setSelected(false);
        } else if (gioitinh.equals("Nữ")) {
            rbNam_KhachHang.setSelected(false);
            rbNu_KhachHang.setSelected(true);
        } else {
            rbNam_KhachHang.setSelected(false);
            rbNu_KhachHang.setSelected(false);
        }
        String ngaysinh = getStringValue(tbKhachHang.getValueAt(viTriDongVuaBam, 3));
        System.out.println("" + ngaysinh);
        String strngay, strthang, strnam;
        strngay = ngaysinh.substring(8, 10);
        strthang = ngaysinh.substring(5, 7);
        strnam = ngaysinh.substring(0, 4);
        int ngay, thang, nam;
        ngay = Integer.valueOf(strngay);
        thang = Integer.valueOf(strthang);
        nam = Integer.valueOf(strnam);
        cbNgay_KhachHang.setSelectedItem(String.valueOf(ngay));
        cbThang_KhachHang.setSelectedItem(String.valueOf(thang));
        cbNam_KhachHang.setSelectedItem(String.valueOf(nam));
        System.out.println(ngay+ " " + thang + " "+nam);
    }//GEN-LAST:event_tbKhachHangMouseClicked

    private void jPanelNhanVienComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelNhanVienComponentShown
        layDuLieuNhanVien();
        rbNam_NhanVien.setSelected(true);
        for (int i = 1; i < 32; i++) {
            cbNgaySinh_NhanVien.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThangSinh_NhanVien.addItem(String.valueOf(i));
        }
        for (int i = 2023; i > 1950; i--) {
            cbNamSinh_NhanVien.addItem(String.valueOf(i));
        }
        cbNgaySinh_NhanVien.setSelectedItem(String.valueOf(day));
        cbThangSinh_NhanVien.setSelectedItem(String.valueOf(month));
        cbNamSinh_NhanVien.setSelectedItem(String.valueOf(year));
        for (int i = 1; i < 32; i++) {
            cbNgayBDLam_NhanVien.addItem(String.valueOf(i));
        }
        for (int i = 1; i < 13; i++) {
            cbThangBDLam_NhanVien.addItem(String.valueOf(i));
        }
        for (int i = 2023; i > 1950; i--) {
            cbNamBDLam_NhanVien.addItem(String.valueOf(i));
        }
        cbNgayBDLam_NhanVien.setSelectedItem(String.valueOf(day));
        cbThangBDLam_NhanVien.setSelectedItem(String.valueOf(month));
        cbNamBDLam_NhanVien.setSelectedItem(String.valueOf(year));
        txtMaNhanVien.setEditable(false);
    }//GEN-LAST:event_jPanelNhanVienComponentShown

    private void txtTimKiem_NhanVienKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem_NhanVienKeyReleased
        TimKiemNhanVien(txtTimKiem_NhanVien.getText().trim());
    }//GEN-LAST:event_txtTimKiem_NhanVienKeyReleased

    private void btnReset_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_NhanVienActionPerformed
        ResNhanVien();
    }//GEN-LAST:event_btnReset_NhanVienActionPerformed

    private void btnXoa_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_NhanVienActionPerformed
        String MaNhanVien = txtMaNhanVien.getText();
        System.out.println(MaNhanVien);
        if (!MaNhanVien.equals("")) {
            String cautruyvan = "delete NhanVien where MaNhanVien=" + MaNhanVien;
            String ctvKiemThu = "select count(MaHoaDon) as SoHoaDon"
            + " from NhanVien,HoaDon where NhanVien.MaNhanVien=HoaDon.MaNhanVien and NhanVien.MaNhanVien=" + MaNhanVien;
            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu);
            String ctvKiemThu2 = "select count(MaPhieuNhap) as SoPhieuNhap"
            + " from NhanVien,PhieuNhap where NhanVien.MaNhanVien=PhieuNhap.MaNhanVien and NhanVien.MaNhanVien=" + MaNhanVien;
            ResultSet rs2 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu2);
            String ctvKiemThu3 = "select count(ID) as SoTK from Users,NhanVien where NhanVien.MaNhanVien=Users.MaNhanVien and NhanVien.MaNhanVien=" + MaNhanVien;
            ResultSet rs3 = startclass.connection.ExcuteQueryGetTable(ctvKiemThu3);
            int so1 = 0, so2 = 0, so3 = 0;
            try {
                if (rs1.next()) {
                    so1 = rs1.getInt("SoHoaDon");
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            try {
                if (rs3.next()) {
                    so3 = rs3.getInt("SoTK");
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            try {
                if (rs2.next()) {
                    so2 = rs2.getInt("SoPhieuNhap");
                    if (rs2.getInt("SoPhieuNhap") == 0 && so1 == 0 && so3 == 0) {
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
                        System.out.println("đã xóa");
                        ThongBao("Xóa thành công","Thông báo",1);
                        layDuLieuNhanVien();
                        ResNhanVien();
                    } else {
                        ThongBao("không thể xóa bởi có trong " + so1 + " hóa đơn \n và có trong "
                            + so2 + "   phiếu nhập \n và có trong "+ so3 +" tài khoản", "báo lỗi", 2);
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        } else {
            ThongBao("Bạn chưa nhập mã nhân viên", "lỗi khi cố xóa nhân viên mà chưa click chuột vô anh ấy", 2);
        }
    }//GEN-LAST:event_btnXoa_NhanVienActionPerformed

    private void btnSua_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_NhanVienActionPerformed
        String MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, NgayVaoLam, ChucVu, DiaChi, SoDT, GhiChu;
        MaNhanVien = txtMaNhanVien.getText();
        if (rbNam_NhanVien.isSelected()) {
            GioiTinh = "1";
        } else {
            GioiTinh = "0";
        }
        ChucVu = txtChucVu_NhanVien.getText();
        TenNhanVien = txtTenNhanVien.getText();
        String ngay, thang, nam;
        ngay = cbNgaySinh_NhanVien.getSelectedItem().toString();
        thang = cbThangSinh_NhanVien.getSelectedItem().toString();
        nam = cbNamSinh_NhanVien.getSelectedItem().toString();
        NgaySinh = nam + "-" + thang + "-" + ngay;
        String ngayv, thangv, namv;
        ngayv = cbNgayBDLam_NhanVien.getSelectedItem().toString();
        thangv = cbThangBDLam_NhanVien.getSelectedItem().toString();
        namv = cbNamBDLam_NhanVien.getSelectedItem().toString();
        NgayVaoLam = namv + "-" + thangv + "-" + ngayv;
        DiaChi = txtDiaChi_NhanVien.getText();
        SoDT = txtSDT_NhanVien.getText();
        GhiChu = txtGhiChu_NhanVien.getText();
        String cautruyvan = "update NhanVien set TenNhanVien=" + "N'" + TenNhanVien
        + "',NgaySinh='" + NgaySinh + "',GioiTinh=" + GioiTinh
        + ",NgayBDLam='" + NgayVaoLam + "',ChucVu='"
        + ChucVu + "',DiaChi=N'" + DiaChi + "',SDT='" + SoDT + "',GhiChu=N'" + GhiChu + "'where MaNhanVien=" + MaNhanVien;
        if (!TenNhanVien.equals("") && !NgaySinh.equals("") && !GioiTinh.equals("") && !ChucVu.equals("") && !DiaChi.equals("") && !SoDT.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã sửa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            ThongBao("Không thể sửa Nhân Viên", "lỗi", 2);
        }
        layDuLieuNhanVien();
    }//GEN-LAST:event_btnSua_NhanVienActionPerformed

    private void btnThem_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_NhanVienActionPerformed
        String MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, NgayVaoLam, ChucVu, DiaChi, SoDT, GhiChu;
        MaNhanVien = txtMaNhanVien.getText();
        if (rbNam_NhanVien.isSelected()) {
            GioiTinh = "1";
        } else {
            GioiTinh = "0";
        }
        ChucVu = txtChucVu_NhanVien.getText();
        TenNhanVien = txtTenNhanVien.getText();
        String ngay, thang, nam;
        ngay = cbNgaySinh_NhanVien.getSelectedItem().toString();
        thang = cbThangSinh_NhanVien.getSelectedItem().toString();
        nam = cbNamSinh_NhanVien.getSelectedItem().toString();
        NgaySinh = nam + "-" + thang + "-" + ngay;
        String ngayv, thangv, namv;
        ngayv = cbNgayBDLam_NhanVien.getSelectedItem().toString();
        thangv = cbThangBDLam_NhanVien.getSelectedItem().toString();
        namv = cbNamBDLam_NhanVien.getSelectedItem().toString();
        NgayVaoLam = namv + "-" + thangv + "-" + ngayv;
        DiaChi = txtDiaChi_NhanVien.getText();
        SoDT = txtSDT_NhanVien.getText();
        GhiChu = txtGhiChu_NhanVien.getText();
        String cautruyvan = "insert into NhanVien values(" + "N'" + TenNhanVien
        + "','" + NgaySinh + "'," + GioiTinh + ",'" + NgayVaoLam + "','"
        + ChucVu + "',N'" + DiaChi + "','" + SoDT + "',N'" + GhiChu + "')";

        if (!TenNhanVien.equals("") && !NgaySinh.equals("") && !GioiTinh.equals("") && !ChucVu.equals("") && !DiaChi.equals("") && !SoDT.equals("") ) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            System.out.println(cautruyvan);
            ThongBao("Thêm thành công","Thông báo",1);
        } else {
            System.out.println("Thất Bại");
            ThongBao("Không thể thêm","Lỗi",0);
        }
        layDuLieuNhanVien();
    }//GEN-LAST:event_btnThem_NhanVienActionPerformed

    private void tbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMouseClicked
        int viTriDongVuaBam = tbNhanVien.getSelectedRow();
        txtMaNhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 1).toString()));
        txtTenNhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 2).toString()));
        txtDiaChi_NhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 7).toString()));
        txtSDT_NhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 8).toString()));
        txtGhiChu_NhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 9).toString()));
        txtChucVu_NhanVien.setText(getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 6).toString()));
        String gioitinh = getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 4).toString());
        if (gioitinh.equals("Nam")) {
            rbNam_NhanVien.setSelected(true);
            rbNu_NhanVien.setSelected(false);
        } else {
            rbNu_NhanVien.setSelected(true);
            rbNam_NhanVien.setSelected(false);
        }
        String ngaysinh = getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 3).toString());
        System.out.println("" + ngaysinh);
        String strngay, strthang, strnam;
        strngay = ngaysinh.substring(8, 10);
        strthang = ngaysinh.substring(5, 7);
        strnam = ngaysinh.substring(0, 4);
        int ngay, thang, nam;
        ngay = Integer.valueOf(strngay);
        thang = Integer.valueOf(strthang);
        nam = Integer.valueOf(strnam);
        cbNgaySinh_NhanVien.setSelectedItem(String.valueOf(ngay));
        cbThangSinh_NhanVien.setSelectedItem(String.valueOf(thang));
        cbNamSinh_NhanVien.setSelectedItem(String.valueOf(nam));
        String ngayVaoLam = getStringValue(tbNhanVien.getValueAt(viTriDongVuaBam, 5).toString());
        System.out.println("" + ngayVaoLam);
        String strngayv, strthangv, strnamv;
        strngayv = ngayVaoLam.substring(8, 10);
        strthangv = ngayVaoLam.substring(5, 7);
        strnamv = ngayVaoLam.substring(0, 4);
        int ngayv, thangv, namv;
        ngayv = Integer.valueOf(strngayv);
        thangv = Integer.valueOf(strthangv);
        namv = Integer.valueOf(strnamv);
        cbNgayBDLam_NhanVien.setSelectedItem(String.valueOf(ngayv));
        cbThangBDLam_NhanVien.setSelectedItem(String.valueOf(thangv));
        cbNamBDLam_NhanVien.setSelectedItem(String.valueOf(namv));
    }//GEN-LAST:event_tbNhanVienMouseClicked

    private void jPanelHangCanNhapComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelHangCanNhapComponentShown
        LayDuLieuSanPhamConIt();
    }//GEN-LAST:event_jPanelHangCanNhapComponentShown

    private void btnNhapHangConItActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapHangConItActionPerformed
        if (frmLogin.quyen == 1){
            jTabbedPane1.setSelectedIndex(8);
        }else
        {
            jTabbedPane1.setSelectedIndex(4);
        }
        setSelectedCombobox(tenhangcannhap,cbSanPham_CTPN);
    }//GEN-LAST:event_btnNhapHangConItActionPerformed

    private void tbHangCanNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHangCanNhapMouseClicked
        int vt = tbHangCanNhap.getSelectedRow();
        tenhangcannhap =tbHangCanNhap.getValueAt(vt, 2).toString().trim();
    }//GEN-LAST:event_tbHangCanNhapMouseClicked

    private void jPanelLoaiSanPhamComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelLoaiSanPhamComponentShown
        layDuLieuLoaiSanPham();
        txtMaLoaiSanPham.setEditable(false);
    }//GEN-LAST:event_jPanelLoaiSanPhamComponentShown

    private void btnReset_LoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset_LoaiSanPhamActionPerformed
        ResLoaiSanPham();
    }//GEN-LAST:event_btnReset_LoaiSanPhamActionPerformed

    private void btnXoa_LoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_LoaiSanPhamActionPerformed
        if (!txtMaLoaiSanPham.getText().equals("")){
            String MaLoaiSanPham = txtMaLoaiSanPham.getText();
            String cautruyvan = "delete LoaiSanPham where MaLoaiSanPham = "+ MaLoaiSanPham;
            String ctv = "select count(MaSanPham) as SoSanPham"
            +" from LoaiSanPham, SanPham where LoaiSanPham.MaLoaiSanPham = SanPham.MaLoaiSanPham and LoaiSanPham.MaLoaiSanPham = "+ MaLoaiSanPham;
            ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(ctv);
            System.out.println(ctv);
            int so =0;
            try{
                if(rs1.next()){
                    so = rs1.getInt("SoSanPham");
                    if(so ==0)
                    {
                        startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
                        System.out.println("Đã xóa");
                        ThongBao("Xóa thành công","Thông báo",1);
                        layDuLieuLoaiSanPham();
                    }else{
                        ThongBao("Không thể xóa bởi loại sản phẩm đã có "+ so +" sản phẩm đang tồn tại","Báo lỗi",2);
                    }

                }
            }catch (SQLException ex) {
                Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            ThongBao("Bạn chưa chọn mã loại sản phẩm để xóa", "Báo lỗi",2);
        }
    }//GEN-LAST:event_btnXoa_LoaiSanPhamActionPerformed

    private void btnSua_LoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_LoaiSanPhamActionPerformed
        String MaLoaiSanPham, TenLoai;
        MaLoaiSanPham = txtMaLoaiSanPham.getText();
        TenLoai = txtTenLoaiSanPham.getText();
        String cautruyvan = "update LoaiSanPham set"
        + " TenLoaiSanPham=N'" + TenLoai + "'where MaLoaiSanPham=" + MaLoaiSanPham;
        if (!txtTenLoaiSanPham.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã sửa Thành Công");
            ThongBao("Sửa thành công","Thông báo",1);
        } else {
            ThongBao("Bạn chưa nhập tên loại sản phẩm", TenLoai, 2);
        }
        layDuLieuLoaiSanPham();
    }//GEN-LAST:event_btnSua_LoaiSanPhamActionPerformed

    private void btnThem_LoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_LoaiSanPhamActionPerformed
        String MaLoaiSanPham, TenLoai;
        MaLoaiSanPham = txtMaLoaiSanPham.getText();
        TenLoai = txtTenLoaiSanPham.getText();
        String cautruyvan = "insert into LoaiSanPham values("
        + " N'" + TenLoai + "')";
        System.out.println(cautruyvan);
        boolean kiemtra = true;
        if (!txtTenLoaiSanPham.equals("")) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            System.out.println("Đã Thêm Thành Công");
            ThongBao("Thêm thành công loại sản phẩm","Thông báo",1);
        } else {
            ThongBao("Bạn chưa nhập tên loại sản phẩm", TenLoai, 2);
        }
        layDuLieuLoaiSanPham();        // TODO add your handling code here:
    }//GEN-LAST:event_btnThem_LoaiSanPhamActionPerformed

    // Cài đặt sự kiện cho click vào bảng
    private void tbLoaiSanPham_LoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLoaiSanPham_LoaiSanPhamMouseClicked
        int viTriDongVuaBam = tbLoaiSanPham_LoaiSanPham.getSelectedRow();
        txtMaLoaiSanPham.setText(tbLoaiSanPham_LoaiSanPham.getValueAt(viTriDongVuaBam, 1).toString());
        txtTenLoaiSanPham.setText(tbLoaiSanPham_LoaiSanPham.getValueAt(viTriDongVuaBam, 2).toString());

        LayDuLieuSanPhamofLoaiSanPham(txtMaLoaiSanPham.getText());
    }//GEN-LAST:event_tbLoaiSanPham_LoaiSanPhamMouseClicked

    private void jPanelSanPhamComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelSanPhamComponentShown
        LayDuLieuSanPham("1");
        cbMaLoaiSanPham.setModel(LayDuLieucbb("LoaiSanPham", "TenLoaiSanPham", "MaLoaiSanPham"));
        cbTimKiemLoaiSanPham.setModel(LayDuLieucbb("LoaiSanPham", "TenLoaiSanPham", "MaLoaiSanPham"));
        txtMaSanPham.setEditable(false);
    }//GEN-LAST:event_jPanelSanPhamComponentShown

    private void btnXemSPAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemSPAnActionPerformed
        frmSanPhamAn frmSpa = new frmSanPhamAn();
        frmSpa.show();
    }//GEN-LAST:event_btnXemSPAnActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        txtTimKiem.getText().trim();
        TimKiemSanPham();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cbTimKiemLoaiSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTimKiemLoaiSanPhamItemStateChanged
        rbTimKiemLoaiSanPham.setSelected(true);
        TimKiemSanPham();
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_cbTimKiemLoaiSanPhamItemStateChanged

    private void rbTimKiemLoaiSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbTimKiemLoaiSanPhamItemStateChanged

    }//GEN-LAST:event_rbTimKiemLoaiSanPhamItemStateChanged

    private void txtXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtXoaActionPerformed
        frmAnSanPham frman = new frmAnSanPham();
        frman.show();
    }//GEN-LAST:event_txtXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        String MaSanPham, TenSanPham, MaLoaiSanPham, GiaNhap, GiaBan, HangSanXuat, TonKho, TrangThai, Image, DVT;
        MaSanPham = txtMaSanPham.getText();
        TenSanPham = txtTenSanPham.getText();
        MaLoaiSanPham = GetCbbSelected(cbMaLoaiSanPham);
        GiaNhap = txtGiaNhap.getText();
        GiaBan = txtGiaBan.getText();
        HangSanXuat = txtHangSanXuat.getText();
        TonKho = txtTonKho.getText();
        TrangThai = "1";
        Image = txtAnh.getText();

        DVT = txtDonViTinh.getText();
        String cautruyvan = "update  SanPham set "
        + "TenSanPham =" + "N'" + TenSanPham + "',MaLoaiSanPham=" + MaLoaiSanPham + ",HangSanXuat=N'"
        + HangSanXuat + "',GiaNhap=" + GiaNhap + ",GiaBan=" + GiaBan + ",TonKho=" + TonKho
        + ",TrangThai=" + TrangThai + ",Images='" + Image + "',DVT =N'" + DVT + "' where MaSanPham=" + MaSanPham;
        System.out.println(cautruyvan);
        boolean kiemtra = KiemTraNhanSanPham(1);
        if (kiemtra) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            ThongBao("Sửa thành công","Thông báo",1);
            System.out.println("Đã Sửa Thành Công Sản Phẩm");
        } else {
            ThongBao("Sửa thất bại","Lỗi",0);
            System.out.println("Thất Bại");
        }
        LayDuLieuSanPham("1");
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        ResSanPham();
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String TenSanPham, MaLoaiSanPham, GiaNhap, GiaBan, HangSanXuat, TonKho, TrangThai, Image, DVT;
        //MaSanPham = txtMaSanPham.getText();
        TenSanPham = txtTenSanPham.getText();
        MaLoaiSanPham = GetCbbSelected(cbMaLoaiSanPham);
        GiaNhap = txtGiaNhap.getText();
        GiaBan = txtGiaBan.getText();
        HangSanXuat = txtHangSanXuat.getText();
        TonKho = txtTonKho.getText();
        TrangThai = "1";
        DVT = txtDonViTinh.getText();
        Image = txtAnh.getText();

        String cautruyvan = "insert into SanPham "
        + "values(N'" + TenSanPham + "'," + MaLoaiSanPham + ",'" + HangSanXuat + "'," + GiaNhap + "," + GiaBan + "," + TonKho+ "," + TrangThai + ",'" + Image + "',N'"+ DVT + "')";
        System.out.println(cautruyvan);
        boolean kiemtra = KiemTraNhanSanPham(0);
        if (kiemtra) {
            startclass.connection.ExcuteQueryUpdateDB(cautruyvan);
            ThongBao("Thêm thành công","Thông báo",1);
            System.out.println("Đã Thêm Thành Công");
        } else {
            ThongBao("Thêm thất bại","Lỗi",0);
            System.out.println("Thất Bại");
        }
        LayDuLieuSanPham("1");
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtHangSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHangSanXuatActionPerformed

    }//GEN-LAST:event_txtHangSanXuatActionPerformed

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked

        int row = tbSanPham.getSelectedRow();
        int column = tbSanPham.getSelectedColumn();
        setSelectedCombobox(tbSanPham.getValueAt(row, 3).toString(), cbMaLoaiSanPham);
        if (row >= 0 && column >= 0) {
            txtMaSanPham.setText(getStringValue(tbSanPham.getValueAt(row, 1).toString()));
            MaSanPham = getStringValue(tbSanPham.getValueAt(row, 1).toString());
            txtTenSanPham.setText(getStringValue(tbSanPham.getValueAt(row, 2).toString()));
            txtGiaNhap.setText(getStringValue(tbSanPham.getValueAt(row, 5).toString()));
            txtGiaBan.setText(getStringValue(tbSanPham.getValueAt(row, 6).toString()));
            txtHangSanXuat.setText(getStringValue(tbSanPham.getValueAt(row, 4).toString()));
            txtTonKho.setText(getStringValue(tbSanPham.getValueAt(row, 7).toString()));
            txtDonViTinh.setText(getStringValue(tbSanPham.getValueAt(row, 8).toString()));
            try {
                if (tbSanPham.getValueAt(row, 9).toString() != null) {
                    txtAnh.setText(tbSanPham.getValueAt(row, 9).toString());
                    setLabelImage(txtAnh.getText());
                }
            } catch (Exception e) {
                txtAnh.setText("");
            }
        }
    }//GEN-LAST:event_tbSanPhamMouseClicked
    public void TimKiemSP_PhieuNhap(String dkt)
    {
        String dk = "";
        if(!dkt.equals("")){
            if(rbMaSP_PhieuNhap.isSelected())
            {
                dk = "where MaSanPham= "+ dkt;
            }else if(rbTenSP_PhieuNhap.isSelected()){
                dk = "where TenSanPham like N'%" + dkt + "%'";
            }
        }
        else{
            dk = "";
        }
        String ctv = "select * from SanPham "+dk;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        Object[] obj = new Object[]{"Mã sản phẩm", "Tên sản phẩm", "Hãng sản xuất", "Tồn kho", "Giá nhập"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham_PhieuNhap.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[5];
                c++;
                item[0] = rs.getInt("MaSanPham");
                item[1] = rs.getString("TenSanPham");
                item[2] = rs.getString("HangSanXuat");
                item[3] = rs.getInt("TonKho");
                item[4] = rs.getInt("GiaNhap");
                tableModel.addRow(item);
                for (Object ob : item) {
                System.out.print(ob + " ");
            }
            System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
    }    
    private void txtTKSP_PhieuNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKSP_PhieuNhapKeyReleased
        TimKiemSP_PhieuNhap(txtTKSP_PhieuNhap.getText().trim());
    }//GEN-LAST:event_txtTKSP_PhieuNhapKeyReleased

    private void tbSanPham_PhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPham_PhieuNhapMouseClicked
        int vt = tbSanPham_PhieuNhap.getSelectedRow();
        setSelectedCombobox(tbSanPham_PhieuNhap.getValueAt(vt, 1).toString(),cbSanPham_CTPN);
        if(TBconit(GetCbbSelected(cbSanPham_CTPN).toString()))
        {
            ThongBao("Lượng hàng của sản phẩm còn ít. Mau nhập hàng đi nào","Thông báo",1);
        }
    }//GEN-LAST:event_tbSanPham_PhieuNhapMouseClicked
    public boolean KiemTraNhanSanPham(int ts) {
        String MaSanPham, TenSanPham, GiaNhap,GiaBan,TonKho;
        boolean kiemtra = false;
        MaSanPham = txtMaSanPham.getText();
        TenSanPham = txtTenSanPham.getText();
        TonKho= txtTonKho.getText().trim();
        String  ThongBao = "";
        GiaNhap = txtGiaNhap.getText();
        GiaBan = txtGiaBan.getText();
        if (MaSanPham.equals("") && ts == 1) {
            ThongBao += "Bạn chưa chọn khách hàng để lấy  Mã Khách Hàng\n";
            lblMaSanPham.setForeground(Color.red);
        }
        if (TenSanPham.equals("")) {
            ThongBao += "Bạn chưa Nhập Tên Sản Phẩm\n";
            lblTenSanPham.setForeground(Color.red);
        }
        if (GiaNhap.equals("")) {
            lblGiaNhap.setForeground(Color.red);
            ThongBao += "Bạn chưa Nhập Giá Bán\n";
        }
        if (GiaBan.equals("")) {
            lblGiaBan.setForeground(Color.red);
            ThongBao += "Bạn chưa Nhập Giá Bán \n";
        }
              try {
                  int bien =Integer.valueOf(GiaNhap);
              } catch (Exception e) {
                  ThongBao+="Giá Nhập Phải là số !\n";
              }
               try {
                  int bien =Integer.valueOf(GiaBan);
              } catch (Exception e) {
                  ThongBao+="Giá Bán Phải là số !\n";
              }
              try {
                   if (Integer.valueOf(GiaNhap)>Integer.valueOf(GiaBan)) {
            lblGiaBan.setForeground(Color.red);
            ThongBao += "Nhập Giá Bán Phải Lớn Hơn Giá Nhập \n";
        } 
              } catch (Exception e) {
              }
      
         if (TonKho.equals("")) {
            lblTonKho.setForeground(Color.red);
            ThongBao += "Bạn chưa Nhập Tồn Kho \n";
        }
        if (ThongBao.equals("")) {
            kiemtra = true;
            lblTenSanPham.setForeground(Color.black);
            lblMaSanPham.setForeground(Color.black);
            lblGiaBan.setForeground(Color.black);
            lblTonKho.setForeground(Color.black);
            lblGiaNhap.setForeground(Color.black);
        } else {
            kiemtra = false;
            ThongBao(ThongBao, "Lỗi nhập liệu", 2);
        }
        return kiemtra;
    }
    //Lấy mã sản phẩm đang được chọn
    public String GetMaSanPham() {
        return txtMaSanPham.getText();
    }
    //Thông báo
    public void ThongBao(String noiDungThongBao, String tieuDeThongBao, int icon) {
        JOptionPane.showMessageDialog(new JFrame(), noiDungThongBao,
                tieuDeThongBao, icon);
    }
    //Xóa sản phẩm
    public void XoaSanPham(String msp) {
        if(!msp.isEmpty())
        {
            String ctv = "delete SanPham where MaSanPham ='"+ msp + "'";
            startclass.connection.ExcuteQueryUpdateDB(ctv);
            LayDuLieuSanPham("1");
        }
    }
    //Reset các trường thông tin đang hiển thị
    public void ResSanPham() {
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        txtTonKho.setText("");
        txtDonViTinh.setText("");
        cbMaLoaiSanPham.setSelectedIndex(0);
        txtHangSanXuat.setText("");
        LayDuLieuSanPham("1");
        txtAnh.setText("");
    }      //Tìm kiếm:
    public void TimKiemSanPham() {
        String wheretk = "";
        if (rbTimKiemMaSanPham.isSelected()) {
            wheretk = "MaSanPham=" + txtTimKiem.getText();
        } else if (rbTimKiemTenSanPham.isSelected()) {
            wheretk = "TenSanPham like N'%" + txtTimKiem.getText() + "%'";
        } else if (rbTimKiemLoaiSanPham.isSelected()) {
            wheretk = "SanPham.MaLoaiSanPham=" + GetCbbSelected(cbTimKiemLoaiSanPham);
        }
        String cautruyvan = "";
//        cautruyvan = "select MaSanPham,SanPham.TenSanPham,SanPham.LoaiSanPham,GiaNhap,GiaBan,HangSanXuat,TonKho,LoaiSanPham.TenLoaiSanPham"
//                + " as TenLoaiSanPham,Images,SanPham.GhiChu from SanPham,LoaiSanPham where "
//                + "SanPham.LoaiSanPham=LoaiSanPham.MaLoaiSanPham and TrangThai=1 and " + wheretk;
        cautruyvan = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, SanPham.MaLoaiSanPham, LoaiSanPham.TenLoaiSanPham, GiaNhap, GiaBan, HangSanXuat, TonKho, Images, DVT " +
                        "FROM SanPham " +
                        "JOIN LoaiSanPham ON SanPham.MaLoaiSanPham = LoaiSanPham.MaLoaiSanPham  WHERE TrangThai = 1 AND " + wheretk;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Sản Phẩm", "Tên sản phẩm", "Loại sản phẩm", "Giá nhập", "Giá bán", "Hãng sản Xuất", "Tồn kho", "Đơn vị tính", "Ảnh"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[10];
                item[0] = c;
                item[1] = rs.getInt("MaSanPham");
                item[2] = rs.getString("TenSanPham");
                item[3] = rs.getString("TenLoaiSanPham"); // Tên cột của bảng LoaiSanPham
                item[4] = rs.getString("HangSanXuat");
                item[5] = rs.getInt("GiaNhap");
                item[6] = rs.getInt("GiaBan");
                item[7] = rs.getInt("TonKho");
                item[8] = rs.getString("DVT");
                item[9] = rs.getString("Images");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }    //Lấy dữ liệu cho combobox, lấy từ cơ sở dữ liệu
    public DefaultComboBoxModel LayDuLieucbb(String bang, String Ten, String Ma) {
        String cautruyvan = "select *from " + bang;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        DefaultComboBoxModel cbbmodel = new DefaultComboBoxModel();
        try {
            while (rs.next()) {
                displayvalueModel valueModel = new displayvalueModel(rs.getString(Ten), rs.getString(Ma));
                cbbmodel.addElement(valueModel);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return cbbmodel;
    }    // set lựa chọn của combobox
    public void setSelectedCombobox(String cbbselected, JComboBox cbb) {
        for (int i = 0; i < cbb.getItemCount(); i++) {
            Object obj = cbb.getItemAt(i);
            if (obj != null) {
                displayvalueModel m = (displayvalueModel) obj;
                if (cbbselected.trim().equals(m.displayMember)) {
                    cbb.setSelectedItem(m);
                }
            }
        }
    }
    //Lấy giá trị từ combobox
    public String GetCbbSelected(JComboBox cbb) {
        Object[] obj = cbb.getSelectedObjects();
        displayvalueModel item = (displayvalueModel) obj[0];
        return item.displayvalue.toString();

    }
    public void setLabelImage(String imageName) {
        if (imageName == null) {
            imageName = "f:\\pictures\\laptop\\default.jpg";
        }
        
        String imageLocation = "f:\\pictures\\laptop\\" + imageName;
        System.out.println("Đường dẫn ảnh: " + imageLocation); // In ra đường dẫn để kiểm tra
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imageLocation));
        } catch (IOException e) {
               e.printStackTrace();
        }
        icon = new ImageIcon(img);

        lbAnh.setIcon(icon);
    }    public void LayDuLieuPhieuNhap() {
        String cautruyvan = "";
        cautruyvan ="SELECT MaPhieuNhap, NhanVien.TenNhanVien, NPP.TenNPP, TongTien, NgayNhap, PhieuNhap.GhiChu FROM PhieuNhap, NhanVien, NPP WHERE PhieuNhap.MaNhanVien = NhanVien.MaNhanVien AND PhieuNhap.MaNPP = NPP.MaNPP";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Phiếu Nhập", "Nhân Viên Nhập", "Tên Nhà Phân phối", "Thành tiền", "Ngày lập", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbPhieuNhap.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[7];
                c++;
                item[0] = c;
                item[1] = rs.getInt("MaPhieuNhap");
                item[2] = rs.getString("TenNhanVien");
                item[3] = rs.getString("TenNPP");
                item[4] = rs.getString("TongTien");
                item[5] = rs.getString("NgayNhap");
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    public void LayDuLieuChiTietPhieuNhap(String MaPhieuNhap) {
        String cautruyvan = "";
        cautruyvan = "select MaCTPN,ChiTietPhieuNhap.MaPhieuNhap ,SanPham.TenSanPham,ChiTietPhieuNhap.SoLuong,"
                + "ChiTietPhieuNhap.TongTien,ChiTietPhieuNhap.GhiChu"
                + " from PhieuNhap,ChiTietPhieuNhap,SanPham where "
                + "PhieuNhap.MaPhieuNhap=ChiTietPhieuNhap.MaPhieuNhap and"
                + " SanPham.MaSanPham=ChiTietPhieuNhap.MaSanPham and ChiTietPhieuNhap.MaphieuNhap=" + MaPhieuNhap;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã CTPN", "Mã Phiếu Nhập", "Sản Phẩm", "Số Lượng", "Tổng Tiền", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbChiTietPhieuNhap.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[7];
                c++;
                item[0] = c;
                item[1] = rs.getInt("MaCTPN");
                item[2] = rs.getInt("MaPhieuNhap");
                item[3] = rs.getString("TenSanPham");
                item[4] = rs.getString("Soluong");
                item[5] = rs.getString("TongTien");
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void SetTongTien_PhieuNhap(String MaPhieuNhap) {
        String cautruyvan = "select sum(ChiTietPhieuNhap.TongTien) as TongTienHienTai,PhieuNhap.MaPhieuNhap from PhieuNhap,ChiTietPhieuNhap "
                + "where PhieuNhap.MaPhieuNhap=ChiTietPhieuNhap.MaPhieuNhap"
                + " and PhieuNhap.MaPhieuNhap=" + MaPhieuNhap + "group by PhieuNhap.MaPhieuNhap";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        String ttht = "";
        try {
            if (rs.next()) {
                ttht = rs.getString("TongTienHienTai");
                txtTongTien_PhieuNhap.setText(ttht);
                String ctv = "update PhieuNhap set TongTien= " + ttht + " where MaPhieuNhap=" + MaPhieuNhap;
                System.out.println(ctv);
                startclass.connection.ExcuteQueryUpdateDB(ctv);
                LayDuLieuPhieuNhap();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }    public void ResKhachHang()
    {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtDiaChi_KhachHang.setText("");
        txtSoDienThoai_KhachHang.setText("");
        rbNew_KhachHang.setSelected(true);
        txtGhiChu_KhachHang.setText("");
        rbNam_KhachHang.setSelected(true);
    }    
    public void layDuLieuKhachHang() {
        String cautruyvan = "";
        cautruyvan = "select * from KhachHang";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Khách Hàng", "Tên Khách Hàng", "Ngày Sinh", "Giới Tính", "Địa Chỉ", "SDT", "Loại Khách Hàng", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbKhachHang.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[9];
                item[0] = c;
                item[1] = rs.getInt("MaKhachHang");
                item[2] = rs.getString("TenKhachHang");
                item[3] = rs.getString("NgaySinh");
                int gioiTinhValue = rs.getInt("GioiTinh");
                if (rs.wasNull()) {
                    item[4] = ""; // Nếu giới tính là null, không hiển thị gì
                } else {
                    if (gioiTinhValue == 1) {
                        item[4] = "Nam";
                    } else {
                        item[4] = "Nữ";
                    }
                }
                item[5] = rs.getString("DiaChi");
                item[6] = rs.getString("SDT");
                if (rs.getInt("LoaiKhachHang") == 1) {
                    item[7] = "Thân thiết";
                } else {
                    item[7] = "Mới";
                }
                item[8] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }   
    public void TimKiemKhachHang(String wheretk) {
        String cautruyvan = "";

        cautruyvan = "select * from KhachHang "
                + "where " + wheretk;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Khách Hàng", "Tên Khách Hàng", "Ngày Sinh", "Giới Tính", "Dịa Chỉ", "SDT", "Loại Khách Hàng", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbKhachHang.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[9];
                item[0] = c;
                item[1] = rs.getInt("MaKhachHang");
                item[2] = rs.getString("TenKhachHang");
                item[3] = rs.getString("NgaySinh");
                if (rs.getInt("GioiTinh") == 1) {
                    item[4] = "Nam";
                } else {
                    item[4] = "Nữ";
                }
                item[5] = rs.getString("DiaChi");
                item[6] = rs.getString("SDT");
                item[7] = rs.getString("LoaiKhachHang");
                item[8] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

        public void layDuLieuNhanVien() {
        String cautruyvan = "";
        cautruyvan = "select * from NhanVien ";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Sinh", "Giới Tính", "Ngày Vào Làm", "Chức Vụ", "Dịa Chỉ", "SDT", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbNhanVien.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[10];
                item[0] = c;
                item[1] = rs.getInt("MaNhanVien");
                item[2] = rs.getString("TenNhanVien");
                item[3] = rs.getString("NgaySinh");
                if (rs.getInt("GioiTinh") == 1) {
                    item[4] = "Nam";
                } else {
                    item[4] = "Nữ";
                }
                item[5] = rs.getString("NgayBDLam");
                item[6] = rs.getString("ChucVu");
                item[7] = rs.getString("DiaChi");
                item[8] = rs.getString("SDT");
                item[9] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    public void ResNhanVien()
    {
        txtMaNhanVien.setText("");
        txtTenNhanVien.setText("");
        cbNgaySinh_NhanVien.setSelectedItem(String.valueOf(day));
        cbThangSinh_NhanVien.setSelectedItem(String.valueOf(month));
        cbNamSinh_NhanVien.setSelectedItem(String.valueOf(year));
        cbNgayBDLam_NhanVien.setSelectedItem(String.valueOf(day));
        cbThangBDLam_NhanVien.setSelectedItem(String.valueOf(month));
        cbNamBDLam_NhanVien.setSelectedItem(String.valueOf(year));
        txtDiaChi_NhanVien.setText("");
        txtSDT_NhanVien.setText("");
        txtGhiChu_NhanVien.setText("");
        txtChucVu_NhanVien.setText("");
        rbNam_NhanVien.setSelected(true);
        rbNu_NhanVien.setSelected(false);
    }
    public void layDuLieuTaiKhoan() {
        String cautruyvan = "";
        cautruyvan = "select ID, NhanVien.TenNhanVien ,Users.UserName,Users.Password"
                + ",Users.Quyen,Users.GhiChu  from Users,NhanVien "
                + "where Users.MaNhanVien=NhanVien.MaNhanVien";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "ID", "Nhân Viên", "Username", "Password", "Quyền", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbTaiKhoan.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[7];
                item[0] = c;
                item[1] = rs.getString("ID");
                item[2] = rs.getString("TenNhanVien");
                item[3] = rs.getString("UserName");
                item[4] = rs.getString("PassWord");
                if (rs.getInt("Quyen") == 1) {
                    item[5] = "Admin";
                } else {
                    item[5] = "User";
                }
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    public void ResTaiKhoan()
    {
        txtID_TaiKhoan.setText("");
        txtUsername_TaiKhoan.setText("");
        txtPassword_TaiKhoan.setText("");
        buttonGroup8.clearSelection();
        txtGhiChu_TaiKhoan.setText("");
    }   
    public void layDuLieuNPP() {
        String cautruyvan = "";
        cautruyvan = "select * from NPP ";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã NPP", "Tên NPP", "Địa chỉ", "Số DT", "Email", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbNhaPhanPhoi.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[7];
                item[0] = c;
                item[1] = rs.getInt("MaNPP");
                item[2] = rs.getString("TenNPP");
                item[3] = rs.getString("DiaChi");
                item[4] = rs.getString("SDT");
                item[5] = rs.getString("Email");
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }    
    public void ResNPP()
    {
        txtMa_NPP.setText("");
        txtTen_NPP.setText("");
        txtDiaChi_NPP.setText("");
        txtSoDienThoai_NPP.setText("");
        txtEmail_NPP.setText("");
        txtGhiChu_NPP.setText("");
    }    
    public void layDuLieuSP()
    {
        String ctv = "select SanPham.MaSanPham, SanPham.TenSanPham, HangSanXuat, TonKho, GiaBan from SanPham";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        Object[] obj = new Object[]{"Mã sản phẩm", "Tên sản phẩm", "Hãng sản xuất", "Tồn kho", "Giá bán"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham_HoaDon.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[5];
                c++;
                item[0] = rs.getInt("MaSanPham");
                item[1] = rs.getString("TenSanPham");
                item[2] = rs.getString("HangSanXuat");
                item[3] = rs.getInt("TonKho");
                item[4] = rs.getInt("GiaBan");
                tableModel.addRow(item);
                for (Object ob : item) {
                System.out.print(ob + " ");
            }
            System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
    }
    public void layDuLieuSP_PN()
    {
        String ctv = "select SanPham.MaSanPham, SanPham.TenSanPham, HangSanXuat, TonKho, GiaNhap from SanPham";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        Object[] obj = new Object[]{"Mã sản phẩm", "Tên sản phẩm", "Hãng sản xuất", "Tồn kho", "Giá nhập"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham_PhieuNhap.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[5];
                c++;
                item[0] = rs.getInt("MaSanPham");
                item[1] = rs.getString("TenSanPham");
                item[2] = rs.getString("HangSanXuat");
                item[3] = rs.getInt("TonKho");
                item[4] = rs.getInt("GiaNhap");
                tableModel.addRow(item);
                for (Object ob : item) {
                System.out.print(ob + " ");
            }
            System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
    }
    
    public void layDuLieuHoaDon() {
        String cautruyvan = "";
        cautruyvan = "select MaHoaDon,KhachHang.TenKhachHang as TenKhachHang,NhanVien.TenNhanVien as TenNhanVien,TongTien,NgayLapHD,HoaDon.GhiChu from HoaDon,KhachHang,NhanVien where HoaDon.MaKhachHang =KhachHang.MaKhachHang "
                + "and HoaDon.MaNhanVien=NhanVien.MaNhanVien ";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã hóa đơn", "Khách Hàng ", "Nhân viên", "Ngày lập hóa dơn", "Tổng tiền", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbHoaDon.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[7];
                c++;
                item[0] = c;
                item[1] = rs.getInt("MaHoaDon");
                item[2] = rs.getString("TenKhachHang");
                item[3] = rs.getString("TenNhanVien");
                item[4] = rs.getString("NgayLapHD");
                item[5] = rs.getInt("TongTien");
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
                for (Object ob : item) {
                System.out.print(ob + " ");
            }
            System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }    public void layDuLieuCTHD(String MaHoaDon) {
        String cautruyvan = "";
        cautruyvan = "select MaCTHD,MaHoaDon,SanPham.TenSanPham,SoLuong,TongTien,ChiTietHoaDon.GhiChu "
                + " from ChiTietHoaDon,SanPham where ChiTietHoaDon.MaSanPham=SanPham.MaSanPham  and MaHoaDon=" + MaHoaDon;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã CTHD", "Mã Hóa Đơn", "Sản Phẩm", "Số Lượng", "tổng tiền", "Ghi Chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbCTHD.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[7];
                item[0] = c;
                item[1] = rs.getInt("MaCTHD");
                item[2] = rs.getString("MaHoaDon");
                item[3] = rs.getString("TenSanPham");
                item[4] = rs.getString("SoLuong");
                item[5] = rs.getDouble("TongTien");
                item[6] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    static String mahoadon = "";    
    public void ResHoaDon()
    {
        txtMa_HoaDon.setText("");
        txtTongTien_HoaDon.setText("");
        txtGhiChu_HoaDon.setText("");
        cbNgayLap_HoaDon.setSelectedItem(String.valueOf(day));
        cbThangLap_HoaDon.setSelectedItem(String.valueOf(month));
        cbNamLap_HoaDon.setSelectedItem(String.valueOf(year));
    }        
    public void SetTonKho_SanPham(String MaSanPham,int slt)
    {
        String cautruyvan1 = "select TonKho from SanPham where MaSanPham = "+ MaSanPham;
        ResultSet rs1 = startclass.connection.ExcuteQueryGetTable(cautruyvan1);
        int soLuongTonKhoHienTai =0;
        try {
            if (rs1.next()) {
                soLuongTonKhoHienTai = rs1.getInt("TonKho");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        int soluong = soLuongTonKhoHienTai - slt;
        String cautruyvan3 = "update SanPham set TonKho = "+ soluong + " where MaSanPham = "+ MaSanPham;
        System.out.println(cautruyvan3);
        startclass.connection.ExcuteQueryUpdateDB(cautruyvan3);
        LayDuLieuSanPham("1");
    }
    public void SetTongTien_HoaDon(String MaHoaDon) {
        String cautruyvan = "select sum(ChiTietHoaDon.TongTien) as TongTienHienTai,HoaDon.MaHoaDon from HoaDon,ChiTietHoaDon "
                + "where HoaDon.MaHoaDon=ChiTietHoaDon.MaHoaDon"
                + " and HoaDon.MaHoaDon=" + MaHoaDon + "group by HoaDon.MaHoaDon";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        String ttht = "";

        try {
            if (rs.next()) {
                ttht = rs.getString("TongTienHienTai");
                txtTongTien_HoaDon.setText(ttht);
                String ctv = "update HoaDon set TongTien= " + ttht + "where MaHoaDon=" + MaHoaDon;
                System.out.println(ctv);
                startclass.connection.ExcuteQueryUpdateDB(ctv);
                layDuLieuHoaDon();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }
    public boolean ConHang(String msp)
    {
        String ctv = "select TonKho from SanPham where MaSanPham = '" + msp + "'";
        int soluong = 0;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        try {
            if(rs.next())
            {
                soluong = rs.getInt("TonKho");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(soluong != 0)
        {
            return true;
        }
        else
            return false;
        
    }        public void ResCTHD()
    {
        txtMa_CTHD.setText("");
        txtMaHoaDon_CTHD.setText("");
        txtSoLuong_CTHD.setText("");
        txtTongTien_CTHD.setText("");
        txtGhiChu_CTHD.setText("");
    }    public void ThongKe_NhanVien(String dk1, String dk2)
    {
        String cautruyvan = "select NV.MaNhanVien, NV.TenNhanVien, convert(date, HD.NgayLapHD) as Ngay, count(CT.MaCTHD) as SoLuongBan, sum(CT.SoLuong) as SoLuongSanPhamBanDuoc, sum(CT.TongTien) as TongTienBanDuoc from"
                + " NhanVien NV inner join HoaDon HD on NV.MaNhanVien = HD.MaNhanVien inner join ChiTietHoaDon CT on HD.MaHoaDon = CT.MaHoaDon "
                + " where HD.NgayLapHD between '"+ dk1 + "' and '" + dk2 + "' group by NV.MaNhanVien, NV.TenNhanVien, convert(date, HD.NgayLapHD)";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã nhân viên", "Tên nhân viên", "Ngày bán", "Số lượng bán", "Số lượng SP bán", "Tổng tiền"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbDoanhThu_NhanVien.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[7];
                item[0] = c;
                item[1] = rs.getInt("MaNhanVien");
                item[2] = rs.getString("TenNhanVien");
                item[3] = rs.getString("Ngay");
                item[4] = rs.getString("SoLuongBan");
                item[5] = rs.getInt("SoLuongSanPhamBanDuoc");
                item[6] = rs.getString("TongTienBanDuoc");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }      
    }
    public void ThongKe_SanPham(String dk1, String dk2)
    {
        String cautruyvan = "select SP.MaSanPham, SP.TenSanPham, convert(date, HD.NgayLapHD) as NgayBan, sum(CT.SoLuong) as TongSoLuongBan, sum(CT.TongTien) as TongTienThuDuoc, sum(CT.TongTien - (SP.GiaNhap * CT.SoLuong)) as LaiThuDuoc"
                            + " from SanPham SP inner join ChiTietHoaDon CT on SP.MaSanPham = CT.MaSanPham inner join HoaDon HD on CT.MaHoaDon = HD.MaHoaDon"
                            + " where HD.NgayLapHD between '"+ dk1 + "' and '" + dk2 + "'"
                            + " group by SP.MaSanPham, SP.TenSanPham, convert(date, HD.NgayLapHD)";
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Ngày bán", "Tổng số lượng bán", "Tổng tiền thu được", "Lãi thu được"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbDoanhThu_SanPham.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[7];
                item[0] = c;
                item[1] = rs.getInt("MaSanPham");
                item[2] = rs.getString("TenSanPham");
                item[3] = rs.getString("NgayBan");
                item[4] = rs.getString("TongSoLuongBan");
                item[5] = rs.getDouble("TongTienThuDuoc");
                item[6] = rs.getDouble("LaiThuDuoc");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }     
    }    public void Res_PhieuNhap(){
        txtMa_PhieuNhap.setText("");
        txtTongTien_PhieuNhap.setText("");
        txtGhiChu_PhieuNhap.setText("");
        cbNhanVien_PhieuNhap.setSelectedIndex(0);
        cbNhaPhanPhoi_PhieuNhap.setSelectedIndex(0);
    }    int slbd_phieunhap = 0;    public void ResCTPN()
    {
        txtMa_CTPN.setText("");
        txtMaPhieuNhap_CTPN.setText("");
        txtSoLuong_CTPN.setText("");
        txtTongTien_CTPN.setText("");
        txtGhiChu_CTPN.setText("");
    }    public void ResLoaiSanPham()
    {
        txtMaLoaiSanPham.setText("");
        txtTenLoaiSanPham.setText("");
    }    public void TimKiem_HoaDon(String mahoadon)
    {
        if(!mahoadon.equals(""))
        {
            String cautruyvan = "select MaHoaDon,KhachHang.TenKhachHang as TenKhachHang,NhanVien.TenNhanVien as TenNhanVien,TongTien,NgayLapHD,HoaDon.GhiChu from HoaDon,KhachHang,NhanVien where HoaDon.MaKhachHang =KhachHang.MaKhachHang "
                + "and HoaDon.MaNhanVien=NhanVien.MaNhanVien and HoaDon.MaHoaDon ="+mahoadon;
            ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
            Object[] obj = new Object[]{"STT", "Mã hóa đơn", "Khách Hàng ", "Nhân viên", "Ngày lập hóa dơn", "Tổng tiền", "Ghi chú"};
            DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
            tbHoaDon.setModel(tableModel);
            int c = 0;
            try {
                while (rs.next()) {
                    Object[] item = new Object[7];
                    c++;
                    item[0] = c;
                    item[1] = rs.getInt("MaHoaDon");
                    item[2] = rs.getString("TenKhachHang");
                    item[3] = rs.getString("TenNhanVien");
                    item[4] = rs.getString("NgayLapHD");
                    item[5] = rs.getInt("TongTien");
                    item[6] = rs.getString("GhiChu");
                    tableModel.addRow(item);
                    for (Object ob : item) {
                    System.out.print(ob + " ");
                }
                System.out.println();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }else
        {
            ThongBao("Ban chưa nhập mã hóa đơn","Thông báo",2);
        }
    }    public void TimKiem_PhieuNhap(String maphieunhap)
    {
        if(!maphieunhap.equals(""))
        {
            String cautruyvan = "select MaPhieuNhap,NhanVien.TenNhanVien as TenNhanVien,NPP.TenNPP as TenNPP,PhieuNhap.TongTien,NgayNhap,PhieuNhap.GhiChu from PhieuNhap,NPP,NhanVien where PhieuNhap.MaNhanVien =NhanVien.MaNhanVien "
                + "and PhieuNhap.MaNPP=NPP.MaNPP and PhieuNhap.MaPhieuNhap ="+maphieunhap;
            ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
            Object[] obj = new Object[]{"STT", "Mã phiếu nhập", "Nhân viên", "Nhà PP", "Ngày nhập", "Tổng tiền", "Ghi chú"};
            DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
            tbPhieuNhap.setModel(tableModel);
            int c = 0;
            try {
                while (rs.next()) {
                    Object[] item = new Object[7];
                    c++;
                    item[0] = c;
                    item[1] = rs.getInt("MaPhieuNhap");
                    item[2] = rs.getString("TenNhanVien");
                    item[3] = rs.getString("TenNPP");
                    item[4] = rs.getString("NgayNhap");
                    item[5] = rs.getInt("TongTien");
                    item[6] = rs.getString("GhiChu");
                    tableModel.addRow(item);
                    for (Object ob : item) {
                    System.out.print(ob + " ");
                }
                System.out.println();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }else
        {
            ThongBao("Ban chưa nhập mã phiếu nhập","Thông báo",2);
        }
    }    public void setCTHD(String maCTHD, int soluongmoi)
    {
        String msp = GetCbbSelected(cbSanPham_CTHD).toString();
        //Lấy giá bán
        String laygia = "select SanPham.GiaBan as GiaBan from ChiTietHoaDon, SanPham  where ChiTietHoaDon.MaSanPham = SanPham.MaSanPham and ChiTietHoaDon.MaSanPham = "+ msp;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(laygia);
        int giaban = 0;
        try {
            if(rs.next())
            {
                giaban = rs.getInt("GiaBan");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(giaban);
        String sql = "update ChiTietHoaDon set SoLuong = " + soluongmoi +", TongTien = " + (soluongmoi*giaban)+", GhiChu = 'Hoàn hàng' where MaCTHD ="+ maCTHD ;
        
        if(!maCTHD.isEmpty())
        {
            startclass.connection.ExcuteQueryUpdateDB(sql);
            ThongBao("Đã cập nhật hoàn hàng","ThongBao",1);
            SetTonKho_SanPham(msp,soluongmoi-slbd);
        }
        else
        {
            ThongBao("Bạn chưa chọn mã chi tiết hóa đơn cần hoàn","Thông báo",2);
        }
    }    public void LayDuLieuSanPhamConIt() {
    String cautruyvan = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, SanPham.MaLoaiSanPham, LoaiSanPham.TenLoaiSanPham, GiaNhap, GiaBan, HangSanXuat, TonKho, Images, DVT " +
                        "FROM SanPham " +
                        "JOIN LoaiSanPham ON SanPham.MaLoaiSanPham = LoaiSanPham.MaLoaiSanPham " +
                        "WHERE TrangThai = 1 and SanPham.TonKho < 20"; 

    ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
    DefaultTableModel tableModel = new DefaultTableModel(); // Tạo một DefaultTableModel rỗng
    String[] columnNames = {"STT","Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Hãng Sản Xuất", "Giá nhập", "Giá bán", "Tồn kho", "Đơn vị tính", "Ảnh"};
    tableModel.setColumnIdentifiers(columnNames);
    tbHangCanNhap.setModel(tableModel); // Gán DefaultTableModel vào JTable
    int c = 0;
    try {
        while (rs.next()) {
            c++;
            Object[] item = new Object[10];
            item[0] = c;
            item[1] = rs.getInt("MaSanPham");
            item[2] = rs.getString("TenSanPham");
            item[3] = rs.getString("TenLoaiSanPham"); // Tên cột của bảng LoaiSanPham
            item[4] = rs.getString("HangSanXuat");
            item[5] = rs.getInt("GiaNhap");
            item[6] = rs.getInt("GiaBan");
            item[7] = rs.getInt("TonKho");
            item[8] = rs.getString("DVT");
            item[9] = rs.getString("Images");
            tableModel.addRow(item); // Thêm dòng vào DefaultTableModel
            for (Object obj : item) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }    String tenhangcannhap = "";    public boolean TBconit(String msp)
    {
        String ctv = "select TonKho from SanPham where MaSanPham= "+ msp;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        int sl = 0;
        try {
            if(rs.next())
            {
                sl = rs.getInt("TonKho");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(sl < 20)
        {
            return true;
        }
        return false;
    }    
    public void TimKiemSP_HoaDon(String dktk)
    {
        String dk = "";
        if(!dktk.equals("")){
            if(rbMaSP_HoaDon.isSelected())
            {
                dk = "where MaSanPham= "+ dktk;
            }else if(rbTenSP_HoaDon.isSelected()){
                dk = "where TenSanPham like N'%" + dktk + "%'";
            }
        }
        else{
            dk = "";
        }
        String ctv = "select * from SanPham "+dk;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(ctv);
        Object[] obj = new Object[]{"Mã sản phẩm", "Tên sản phẩm", "Hãng sản xuất", "Tồn kho", "Giá bán"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbSanPham_HoaDon.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                Object[] item = new Object[5];
                c++;
                item[0] = rs.getInt("MaSanPham");
                item[1] = rs.getString("TenSanPham");
                item[2] = rs.getString("HangSanXuat");
                item[3] = rs.getInt("TonKho");
                item[4] = rs.getInt("GiaBan");
                tableModel.addRow(item);
                for (Object ob : item) {
                System.out.print(ob + " ");
            }
            System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
    }    
    public void TimKiemNhanVien(String wheretk) {

        if(!wheretk.equals("")){
            if (rbTimKiemMa_NhanVien.isSelected()) {
                wheretk = "where MaNhanVien= " + txtTimKiem_NhanVien.getText();
            } else if (rbTimKiemTen_NhanVien.isSelected()) {
                wheretk = "where TenNhanVien like N'%" + txtTimKiem_NhanVien.getText() + "%'";
            }
        }else
        {
            wheretk = "";
        }
        String cautruyvan = "";
        cautruyvan = "select * from NhanVien " + wheretk;
        ResultSet rs = startclass.connection.ExcuteQueryGetTable(cautruyvan);
        Object[] obj = new Object[]{"STT", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Sinh", "Giới Tính", "Ngày Vào Làm", "Chức Vụ", "Dịa Chỉ", "SDT", "Ghi chú"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tbNhanVien.setModel(tableModel);
        int c = 0;
        try {
            while (rs.next()) {
                c++;
                Object[] item = new Object[10];
                item[0] = c;
                item[1] = rs.getInt("MaNhanVien");
                item[2] = rs.getString("TenNhanVien");
                item[3] = rs.getString("NgaySinh");
                if (rs.getInt("GioiTinh") == 1) {
                    item[4] = "Nam";
                } else {
                    item[4] = "Nữ";
                }
                item[5] = rs.getString("NgayBDLam");
                item[6] = rs.getString("ChucVu");
                item[7] = rs.getString("DiaChi");
                item[8] = rs.getString("SDT");
                item[9] = rs.getString("GhiChu");
                tableModel.addRow(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
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
            java.util.logging.Logger.getLogger(frmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmHome().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHoanHang;
    private javax.swing.JButton btnKiemTra_HoaDon;
    private javax.swing.JButton btnNhapHangConIt;
    private javax.swing.JButton btnRes_KhachHang;
    private javax.swing.JButton btnRes_TaiKhoan;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnReset_CTHD;
    private javax.swing.JButton btnReset_CTPN;
    private javax.swing.JButton btnReset_HoaDon;
    private javax.swing.JButton btnReset_LoaiSanPham;
    private javax.swing.JButton btnReset_NPP;
    private javax.swing.JButton btnReset_NhanVien;
    private javax.swing.JButton btnReset_PhieuNhap;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua_CTHD;
    private javax.swing.JButton btnSua_CTPN;
    private javax.swing.JButton btnSua_HoaDon;
    private javax.swing.JButton btnSua_KhachHang;
    private javax.swing.JButton btnSua_LoaiSanPham;
    private javax.swing.JButton btnSua_NPP;
    private javax.swing.JButton btnSua_NhanVien;
    private javax.swing.JButton btnSua_PhieuNhap;
    private javax.swing.JButton btnSua_TaiKhoan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem_CTHD;
    private javax.swing.JButton btnThem_CTPN;
    private javax.swing.JButton btnThem_HoaDon;
    private javax.swing.JButton btnThem_KhachHang;
    private javax.swing.JButton btnThem_LoaiSanPham;
    private javax.swing.JButton btnThem_NPP;
    private javax.swing.JButton btnThem_NhanVien;
    private javax.swing.JButton btnThem_PhieuNhap;
    private javax.swing.JButton btnThem_TaiKhoan;
    private javax.swing.JButton btnTimKiem_HoaDon;
    private javax.swing.JButton btnTimKiem_PhieuNhap;
    private javax.swing.JButton btnXemSPAn;
    private javax.swing.JButton btnXoa_CTHD;
    private javax.swing.JButton btnXoa_CTPN;
    private javax.swing.JButton btnXoa_HoaDon;
    private javax.swing.JButton btnXoa_KhachHang;
    private javax.swing.JButton btnXoa_LoaiSanPham;
    private javax.swing.JButton btnXoa_NPP;
    private javax.swing.JButton btnXoa_NhanVien;
    private javax.swing.JButton btnXoa_PhieuNhap;
    private javax.swing.JButton btnXoa_TaiKhoan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JComboBox<String> cbKhachHang_HoaDon;
    private javax.swing.JComboBox<String> cbMaLoaiSanPham;
    private javax.swing.JComboBox<String> cbNam1_DoanhThu;
    private javax.swing.JComboBox<String> cbNam2_DoanhThu;
    private javax.swing.JComboBox<String> cbNamBDLam_NhanVien;
    private javax.swing.JComboBox<String> cbNamLap_HoaDon;
    private javax.swing.JComboBox<String> cbNamSinh_NhanVien;
    private javax.swing.JComboBox<String> cbNam_KhachHang;
    private javax.swing.JComboBox<String> cbNam_PhieuNhap;
    private javax.swing.JComboBox<String> cbNgay1_DoanhThu;
    private javax.swing.JComboBox<String> cbNgay2_DoanhThu;
    private javax.swing.JComboBox<String> cbNgayBDLam_NhanVien;
    private javax.swing.JComboBox<String> cbNgayLap_HoaDon;
    private javax.swing.JComboBox<String> cbNgaySinh_NhanVien;
    private javax.swing.JComboBox<String> cbNgay_KhachHang;
    private javax.swing.JComboBox<String> cbNgay_PhieuNhap;
    private javax.swing.JComboBox<String> cbNhaPhanPhoi_PhieuNhap;
    private javax.swing.JComboBox<String> cbNhanVien_HoaDon;
    private javax.swing.JComboBox<String> cbNhanVien_PhieuNhap;
    private javax.swing.JComboBox<String> cbSanPham_CTHD;
    private javax.swing.JComboBox<String> cbSanPham_CTPN;
    private javax.swing.JComboBox<String> cbTenNhanVien_TaiKhoan;
    private javax.swing.JComboBox<String> cbThang1_DoanhThu;
    private javax.swing.JComboBox<String> cbThang2_DoanhThu;
    private javax.swing.JComboBox<String> cbThangBDLam_NhanVien;
    private javax.swing.JComboBox<String> cbThangLap_HoaDon;
    private javax.swing.JComboBox<String> cbThangSinh_NhanVien;
    private javax.swing.JComboBox<String> cbThang_KhachHang;
    private javax.swing.JComboBox<String> cbThang_PhieuNhap;
    private javax.swing.JComboBox<String> cbTimKiemLoaiSanPham;
    private javax.swing.JComboBox<String> cbTimKiemTuoi1_KhachHang;
    private javax.swing.JComboBox<String> cbTimKiemTuoi2_KhachHang;
    private javax.swing.JCheckBox ckboxTimKiem_KhachHang;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDangXuat;
    private javax.swing.JPanel jPanelDoanhThu;
    private javax.swing.JPanel jPanelHangCanNhap;
    private javax.swing.JPanel jPanelHoaDon;
    private javax.swing.JPanel jPanelKhachHang;
    private javax.swing.JPanel jPanelLoaiSanPham;
    private javax.swing.JPanel jPanelNhanVien;
    private javax.swing.JPanel jPanelNhapHang;
    private javax.swing.JPanel jPanelSanPham;
    private javax.swing.JPanel jPanelTaiKhoan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JLabel lbTienTraKhach;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblHangSanXuat;
    private javax.swing.JLabel lblLoaiSanPham;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblTonKho;
    private javax.swing.JRadioButton rbMaSP_HoaDon;
    private javax.swing.JRadioButton rbMaSP_PhieuNhap;
    private javax.swing.JRadioButton rbNam_KhachHang;
    private javax.swing.JRadioButton rbNam_NhanVien;
    private javax.swing.JRadioButton rbNew_KhachHang;
    private javax.swing.JRadioButton rbNhanVien_DoanhThu;
    private javax.swing.JRadioButton rbNu_KhachHang;
    private javax.swing.JRadioButton rbNu_NhanVien;
    private javax.swing.JRadioButton rbOld_KhachHang;
    private javax.swing.JRadioButton rbQuyen_ad;
    private javax.swing.JRadioButton rbQuyen_user;
    private javax.swing.JRadioButton rbSanPham_DoanhThu;
    private javax.swing.JRadioButton rbTenSP_HoaDon;
    private javax.swing.JRadioButton rbTenSP_PhieuNhap;
    private javax.swing.JRadioButton rbTimKiemLoaiSanPham;
    private javax.swing.JRadioButton rbTimKiemMaSanPham;
    private javax.swing.JRadioButton rbTimKiemMa_NhanVien;
    private javax.swing.JRadioButton rbTimKiemTenSanPham;
    private javax.swing.JRadioButton rbTimKiemTen_NhanVien;
    private javax.swing.JTable tbCTHD;
    private javax.swing.JTable tbChiTietPhieuNhap;
    private javax.swing.JTable tbDoanhThu_NhanVien;
    private javax.swing.JTable tbDoanhThu_SanPham;
    private javax.swing.JTable tbHangCanNhap;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTable tbLoaiSanPham_LoaiSanPham;
    private javax.swing.JTable tbNhaPhanPhoi;
    private javax.swing.JTable tbNhanVien;
    private javax.swing.JTable tbPhieuNhap;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tbSanPham_HoaDon;
    private javax.swing.JTable tbSanPham_LoaiSanPham;
    private javax.swing.JTable tbSanPham_PhieuNhap;
    private javax.swing.JTable tbTaiKhoan;
    private javax.swing.JTextField txtAnh;
    private javax.swing.JTextField txtChucVu_NhanVien;
    private javax.swing.JTextField txtDiaChi_KhachHang;
    private javax.swing.JTextField txtDiaChi_NPP;
    private javax.swing.JTextField txtDiaChi_NhanVien;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtEmail_NPP;
    private javax.swing.JTextArea txtGhiChu_CTHD;
    private javax.swing.JTextArea txtGhiChu_CTPN;
    private javax.swing.JTextArea txtGhiChu_HoaDon;
    private javax.swing.JTextArea txtGhiChu_KhachHang;
    private javax.swing.JTextArea txtGhiChu_NPP;
    private javax.swing.JTextArea txtGhiChu_NhanVien;
    private javax.swing.JTextArea txtGhiChu_PhieuNhap;
    private javax.swing.JTextArea txtGhiChu_TaiKhoan;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtHangSanXuat;
    private javax.swing.JTextField txtID_TaiKhoan;
    private javax.swing.JTextField txtMaHoaDon_CTHD;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaLoaiSanPham;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaPhieuNhap_CTPN;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtMa_CTHD;
    private javax.swing.JTextField txtMa_CTPN;
    private javax.swing.JTextField txtMa_HoaDon;
    private javax.swing.JTextField txtMa_NPP;
    private javax.swing.JTextField txtMa_PhieuNhap;
    private javax.swing.JTextField txtPassword_TaiKhoan;
    private javax.swing.JTextField txtSDT_HoaDon;
    private javax.swing.JTextField txtSDT_NhanVien;
    private javax.swing.JTextField txtSoDienThoai_KhachHang;
    private javax.swing.JTextField txtSoDienThoai_NPP;
    private javax.swing.JTextField txtSoLuong_CTHD;
    private javax.swing.JTextField txtSoLuong_CTPN;
    private javax.swing.JTextField txtTKSP_HoaDon;
    private javax.swing.JTextField txtTKSP_PhieuNhap;
    private javax.swing.JTextField txtTenKH_HoaDon;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTenLoaiSanPham;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTen_NPP;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemTen_KhachHang;
    private javax.swing.JTextField txtTimKiem_NhanVien;
    private javax.swing.JTextField txtTonKho;
    private javax.swing.JTextField txtTongTien_CTHD;
    private javax.swing.JTextField txtTongTien_CTPN;
    private javax.swing.JTextField txtTongTien_HoaDon;
    private javax.swing.JTextField txtTongTien_PhieuNhap;
    private javax.swing.JTextField txtUsername_TaiKhoan;
    private javax.swing.JButton txtXoa;
    // End of variables declaration//GEN-END:variables
}

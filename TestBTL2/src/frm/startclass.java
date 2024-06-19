/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frm;

import KetNoi.connect;

/**
 *
 * @author ADMIN
 */
public class startclass {
    public static  frm.frmHome frmTC =new frm.frmHome();
    public static connect connection = new connect ();

    public static void main(String[] args) {
       frm.frmLogin frmDN= new  frm.frmLogin();
       frmDN.show();
    }

}

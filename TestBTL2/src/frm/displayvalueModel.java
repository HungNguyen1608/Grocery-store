/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frm;

/**
 *
 * @author ADMIN
 */
public class displayvalueModel {
    public  Object displayMember;
    public  Object displayvalue;
    public displayvalueModel() {
   
    }
    public displayvalueModel(Object displayMember, Object displayvalue) {
        this.displayMember = displayMember;
        this.displayvalue = displayvalue;
    }
    @Override
    public String toString(){
        return displayMember.toString();
    }
}

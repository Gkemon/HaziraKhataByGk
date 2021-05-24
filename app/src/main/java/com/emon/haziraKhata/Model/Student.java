package com.emon.haziraKhata.Model;

import com.emon.haziraKhata.Constant.Constant;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;

import java.io.Serializable;

public class Student implements Serializable {
    private String roll;
    private String Name;
    private String ParentContact;
    private String StudentsContactNumber;
    private String ParentName;
    private String parent2Name;
    private String parent2Contact;
    private String birthCertificateNo;
    private String birthDate;
    private String imageUrl;
    private String StudentClass;
    private String StudentSection;
    private String uuid;
    private String gander = Constant.MALE;

    public Student() {
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStudentsContactNumber() {
        return StudentsContactNumber;
    }

    public void setStudentsContactNumber(String studentsContactNumber) {
        StudentsContactNumber = studentsContactNumber;
    }

    public String getParent2Name() {
        return parent2Name;
    }

    public void setParent2Name(String parent2Name) {
        this.parent2Name = parent2Name;
    }

    public String getParent2Contact() {
        return parent2Contact;
    }

    public void setParent2Contact(String parent2Contact) {
        this.parent2Contact = parent2Contact;
    }

    public String getBirthCertificateNo() {
        return birthCertificateNo;
    }

    public void setBirthCertificateNo(String birthCertificateNo) {
        this.birthCertificateNo = birthCertificateNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageUrl() {
        return UtilsCommon.isValideString(imageUrl) ? imageUrl : "";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return roll;
    }

    public void setId(String id) {
        this.roll = id;
    }

    public String getStudentName() {
        return Name;
    }

    public void setStudentName(String name) {
        this.Name = name;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String name) {
        this.ParentName = name;
    }

    public String getParentContact() {
        return ParentContact;
    }

    public void setParentContact(String phone) {
        this.ParentContact = phone;
    }

    public String getPhone() {
        return StudentsContactNumber;
    }

    public void setPhone(String phone) {
        this.StudentsContactNumber = phone;
    }

    public String getStudentClass() {
        return UtilsCommon.isValideString(StudentClass)?StudentClass.trim():"";
    }

    public void setStudentClass(String studentClass) {
        this.StudentClass = studentClass;
    }

    public String getStudentSection() {
        return UtilsCommon.isValideString(StudentSection)?StudentSection.trim():"";
    }

    public void setStudentSection(String studentSection) {
        this.StudentSection = studentSection;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mercenaries.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DiegoFeliú
 */
@Entity
@Table(name = "email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e")
    , @NamedQuery(name = "Email.findById", query = "SELECT e FROM Email e WHERE e.id = :id")
    , @NamedQuery(name = "Email.findByFromEmail", query = "SELECT e FROM Email e WHERE e.fromEmail = :fromEmail")
    , @NamedQuery(name = "Email.findByToEmail", query = "SELECT e FROM Email e WHERE e.toEmail = :toEmail")
    , @NamedQuery(name = "Email.findByCc", query = "SELECT e FROM Email e WHERE e.cc = :cc")
    , @NamedQuery(name = "Email.findByBcc", query = "SELECT e FROM Email e WHERE e.bcc = :bcc")
    , @NamedQuery(name = "Email.findBySubject", query = "SELECT e FROM Email e WHERE e.subject = :subject")
    , @NamedQuery(name = "Email.findByStatus", query = "SELECT e FROM Email e WHERE e.status = :status")
    , @NamedQuery(name = "Email.findByErrorMessage", query = "SELECT e FROM Email e WHERE e.errorMessage = :errorMessage")
    , @NamedQuery(name = "Email.findByErrorCode", query = "SELECT e FROM Email e WHERE e.errorCode = :errorCode")
    , @NamedQuery(name = "Email.findByRunClient", query = "SELECT e FROM Email e WHERE e.runClient = :runClient")
    , @NamedQuery(name = "Email.findByFilename", query = "SELECT e FROM Email e WHERE e.filename = :filename")
    , @NamedQuery(name = "Email.findByCreatedAt", query = "SELECT e FROM Email e WHERE e.createdAt = :createdAt")
    , @NamedQuery(name = "Email.findByUpdatedAt", query = "SELECT e FROM Email e WHERE e.updatedAt = :updatedAt")
    , @NamedQuery(name = "Email.findBySentAt", query = "SELECT e FROM Email e WHERE e.sentAt = :sentAt")
    , @NamedQuery(name = "Email.findByRedAt", query = "SELECT e FROM Email e WHERE e.redAt = :redAt")})
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fromEmail")
    private String fromEmail;
    @Basic(optional = false)
    @Column(name = "toEmail")
    private String toEmail;
    @Column(name = "cc")
    private String cc;
    @Column(name = "bcc")
    private String bcc;
    @Column(name = "subject")
    private String subject;
    @Column(name = "status")
    private String status;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "error_code")
    private Integer errorCode;
    @Column(name = "run_client")
    private String runClient;
    @Column(name = "filename")
    private String filename;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    @Column(name = "red_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date redAt;
    @JoinColumn(name = "campaign_email_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CampaignEmail campaignEmailId;

    public Email() {
    }

    public Email(Integer id) {
        this.id = id;
    }

    public Email(Integer id, String fromEmail, String toEmail) {
        this.id = id;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getRunClient() {
        return runClient;
    }

    public void setRunClient(String runClient) {
        this.runClient = runClient;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Date getRedAt() {
        return redAt;
    }

    public void setRedAt(Date redAt) {
        this.redAt = redAt;
    }

    public CampaignEmail getCampaignEmailId() {
        return campaignEmailId;
    }

    public void setCampaignEmailId(CampaignEmail campaignEmailId) {
        this.campaignEmailId = campaignEmailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mercenaries.jpa.Email[ id=" + id + " ]";
    }

}

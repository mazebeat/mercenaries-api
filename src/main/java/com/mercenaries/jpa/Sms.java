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
import javax.persistence.Lob;
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
@Table(name = "sms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sms.findAll", query = "SELECT s FROM Sms s")
    , @NamedQuery(name = "Sms.findById", query = "SELECT s FROM Sms s WHERE s.id = :id")
    , @NamedQuery(name = "Sms.findBySid", query = "SELECT s FROM Sms s WHERE s.sid = :sid")
    , @NamedQuery(name = "Sms.findByAccountSid", query = "SELECT s FROM Sms s WHERE s.accountSid = :accountSid")
    , @NamedQuery(name = "Sms.findByMessagingServiceId", query = "SELECT s FROM Sms s WHERE s.messagingServiceId = :messagingServiceId")
    , @NamedQuery(name = "Sms.findByFromNumber", query = "SELECT s FROM Sms s WHERE s.fromNumber = :fromNumber")
    , @NamedQuery(name = "Sms.findByToNumber", query = "SELECT s FROM Sms s WHERE s.toNumber = :toNumber")
    , @NamedQuery(name = "Sms.findByNumMedia", query = "SELECT s FROM Sms s WHERE s.numMedia = :numMedia")
    , @NamedQuery(name = "Sms.findByNumSegments", query = "SELECT s FROM Sms s WHERE s.numSegments = :numSegments")
    , @NamedQuery(name = "Sms.findByStatus", query = "SELECT s FROM Sms s WHERE s.status = :status")
    , @NamedQuery(name = "Sms.findByErrorCode", query = "SELECT s FROM Sms s WHERE s.errorCode = :errorCode")
    , @NamedQuery(name = "Sms.findByErrorMessage", query = "SELECT s FROM Sms s WHERE s.errorMessage = :errorMessage")
    , @NamedQuery(name = "Sms.findByDirection", query = "SELECT s FROM Sms s WHERE s.direction = :direction")
    , @NamedQuery(name = "Sms.findByPrice", query = "SELECT s FROM Sms s WHERE s.price = :price")
    , @NamedQuery(name = "Sms.findByPriceUnit", query = "SELECT s FROM Sms s WHERE s.priceUnit = :priceUnit")
    , @NamedQuery(name = "Sms.findByApiVersion", query = "SELECT s FROM Sms s WHERE s.apiVersion = :apiVersion")
    , @NamedQuery(name = "Sms.findByUriTwillio", query = "SELECT s FROM Sms s WHERE s.uriTwillio = :uriTwillio")
    , @NamedQuery(name = "Sms.findBySubreSourceUri", query = "SELECT s FROM Sms s WHERE s.subreSourceUri = :subreSourceUri")
    , @NamedQuery(name = "Sms.findByFilename", query = "SELECT s FROM Sms s WHERE s.filename = :filename")
    , @NamedQuery(name = "Sms.findByCreatedAt", query = "SELECT s FROM Sms s WHERE s.createdAt = :createdAt")
    , @NamedQuery(name = "Sms.findByUpdatedAt", query = "SELECT s FROM Sms s WHERE s.updatedAt = :updatedAt")
    , @NamedQuery(name = "Sms.findBySentAt", query = "SELECT s FROM Sms s WHERE s.sentAt = :sentAt")})
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sid")
    private String sid;
    @Column(name = "accountSid")
    private String accountSid;
    @Column(name = "messaging_service_id")
    private String messagingServiceId;
    @Basic(optional = false)
    @Column(name = "from_number")
    private String fromNumber;
    @Basic(optional = false)
    @Column(name = "to_number")
    private String toNumber;
    @Basic(optional = false)
    @Lob
    @Column(name = "body_message")
    private String bodyMessage;
    @Column(name = "num_media")
    private Integer numMedia;
    @Column(name = "num_segments")
    private Integer numSegments;
    @Column(name = "status")
    private String status;
    @Column(name = "error_code")
    private String errorCode;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "direction")
    private String direction;
    @Column(name = "price")
    private Integer price;
    @Column(name = "price_unit")
    private Integer priceUnit;
    @Column(name = "api_version")
    private String apiVersion;
    @Column(name = "uri_twillio")
    private String uriTwillio;
    @Column(name = "subre_source_uri")
    private String subreSourceUri;
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    @JoinColumn(name = "campaign_sms_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CampaignSms campaignSmsId;

    public Sms() {
    }

    public Sms(Integer id) {
        this.id = id;
    }

    public Sms(Integer id, String fromNumber, String toNumber, String bodyMessage, Date createdAt) {
        this.id = id;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.bodyMessage = bodyMessage;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getMessagingServiceId() {
        return messagingServiceId;
    }

    public void setMessagingServiceId(String messagingServiceId) {
        this.messagingServiceId = messagingServiceId;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public Integer getNumMedia() {
        return numMedia;
    }

    public void setNumMedia(Integer numMedia) {
        this.numMedia = numMedia;
    }

    public Integer getNumSegments() {
        return numSegments;
    }

    public void setNumSegments(Integer numSegments) {
        this.numSegments = numSegments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getUriTwillio() {
        return uriTwillio;
    }

    public void setUriTwillio(String uriTwillio) {
        this.uriTwillio = uriTwillio;
    }

    public String getSubreSourceUri() {
        return subreSourceUri;
    }

    public void setSubreSourceUri(String subreSourceUri) {
        this.subreSourceUri = subreSourceUri;
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

    public CampaignSms getCampaignSmsId() {
        return campaignSmsId;
    }

    public void setCampaignSmsId(CampaignSms campaignSmsId) {
        this.campaignSmsId = campaignSmsId;
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
        if (!(object instanceof Sms)) {
            return false;
        }
        Sms other = (Sms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mercenaries.jpa.Sms[ id=" + id + " ]";
    }

}

package com.project.moneymanagement;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "listData")
public class Transaction {
    @Id
    private String tracId;
    private String tracName;
    private BigDecimal tracValueB;
    private BigDecimal tracRatio;
    private BigDecimal tracValueA;
    private String tracRef;
    private LocalDate tracDate;
    private String tracType;

    @Lob
    @Column(name = "tracPic", columnDefinition = "VARBINARY(MAX)")
    private byte[] tracPic;

    // Getter dan Setter (Bisa digenerate otomatis di Eclipse: Source > Generate Getters/Setters)
    public String getTracId() { return tracId; }
    public void setTracId(String tracId) { this.tracId = tracId; }
    public String getTracName() { return tracName; }
    public void setTracName(String tracName) { this.tracName = tracName; }
    public BigDecimal getTracValueB() { return tracValueB; }
    public void setTracValueB(BigDecimal tracValueB) { this.tracValueB = tracValueB; }
    public BigDecimal getTracRatio() { return tracRatio; }
    public void setTracRatio(BigDecimal tracRatio) { this.tracRatio = tracRatio; }
    public BigDecimal getTracValueA() { return tracValueA; }
    public void setTracValueA(BigDecimal tracValueA) { this.tracValueA = tracValueA; }
    public String getTracRef() { return tracRef; }
    public void setTracRef(String tracRef) { this.tracRef = tracRef; }
    public LocalDate getTracDate() { return tracDate; }
    public void setTracDate(LocalDate tracDate) { this.tracDate = tracDate; }
    public String getTracType() { return tracType; }
    public void setTracType(String tracType) { this.tracType = tracType; }
    public byte[] getTracPic() { return tracPic; }
    public void setTracPic(byte[] tracPic) { this.tracPic = tracPic; }
}
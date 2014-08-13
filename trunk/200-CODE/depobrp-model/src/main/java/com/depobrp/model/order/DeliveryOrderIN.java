package com.depobrp.model.order;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.depobrp.model.common.Auditable;
import com.depobrp.model.master.Consignee;
import com.depobrp.model.master.MLO;
import com.depobrp.model.master.Vessel;

@Entity
@Table(name = "TBL_OP_DELIVERY_ORDER_IN")
public class DeliveryOrderIN extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160558624314682617L;

	@Id
	@Column(name = "DO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DO_NUM")
	private String doNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="MLO_ID", nullable=false)
	private MLO owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="CONSIGNEE_ID", nullable=false)
	private Consignee consignee;
	
	@Column(name = "DELIVERY_DATE")
	private Date deliveryDate;
	
	@OneToMany(mappedBy="doIN")
	private Set<FreightContainer> containers;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="EX_VESSEL_ID", nullable=false)
	private Vessel exVessel;
	
	@Column(name = "EX_VESSEL_VNUM")
	private String exVesselVoyageNo;
	
	@Column(name = "NOTE_IN")
	private String noteIn;
	
	@Column(name = "INITIAL_CONTAINER_COUNT")
	private Integer initialContainerCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDoNumber() {
		return doNumber;
	}

	public void setDoNumber(String doNumber) {
		this.doNumber = doNumber;
	}

	public MLO getOwner() {
		return owner;
	}

	public void setOwner(MLO owner) {
		this.owner = owner;
	}

	public Consignee getConsignee() {
		return consignee;
	}

	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Set<FreightContainer> getContainers() {
		return containers;
	}

	public void setContainers(Set<FreightContainer> containers) {
		this.containers = containers;
	}

	public Vessel getExVessel() {
		return exVessel;
	}

	public void setExVessel(Vessel exVessel) {
		this.exVessel = exVessel;
	}

	public String getExVesselVoyageNo() {
		return exVesselVoyageNo;
	}

	public void setExVesselVoyageNo(String exVesselVoyageNo) {
		this.exVesselVoyageNo = exVesselVoyageNo;
	}

	public String getNoteIn() {
		return noteIn;
	}

	public void setNoteIn(String noteIn) {
		this.noteIn = noteIn;
	}

	public Integer getInitialContainerCount() {
		return initialContainerCount;
	}

	public void setInitialContainerCount(Integer initialContainerCount) {
		this.initialContainerCount = initialContainerCount;
	}
	
	
}

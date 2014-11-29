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
@Table(name = "TBL_OP_DELIVERY_ORDER_OUT")
public class DeliveryOrderOUT extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160558624314682617L;

	@Id
	@Column(name = "DO_OUT_ID")
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
	
	@OneToMany(mappedBy="doOUT")
	private Set<FreightContainer> containers;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="NEXT_VESSEL_ID", nullable=false)
	private Vessel nextVessel;
	
	@Column(name = "NEXT_VESSEL_VNUM")
	private String nextVesselVoyageNo;
	
	@Column(name = "NOTE_OUT")
	private String noteIn;
	
	@Column(name = "NEXT_CITY_PORT", length = 32)
	private String nextCityPort;
	
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

	public Vessel getNextVessel() {
		return nextVessel;
	}

	public void setNextVessel(Vessel nextVessel) {
		this.nextVessel = nextVessel;
	}

	public String getNextVesselVoyageNo() {
		return nextVesselVoyageNo;
	}

	public void setNextVesselVoyageNo(String nextVesselVoyageNo) {
		this.nextVesselVoyageNo = nextVesselVoyageNo;
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

	public String getNextCityPort() {
		return nextCityPort;
	}

	public void setNextCityPort(String nextCityPort) {
		this.nextCityPort = nextCityPort;
	}
	
	
}

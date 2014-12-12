package com.depobrp.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.depobrp.commons.util.DateUtils;
import com.depobrp.model.common.Auditable;

@Entity
@Table(name = "TBL_OP_FREIGHT_CONTAINER")
public class FreightContainer extends Auditable {

	public FreightContainer(
			String containerNum, 
			Integer checkDigit,
			Size size,
			Type type, 
			Condition conditionIN, 
			Integer payloadKGIN,
			EmptyFull emptyFullIN) {
		
		super();
		this.containerNum = containerNum;
		this.checkDigit = checkDigit;
		this.size = size;
		this.type = type;
		this.conditionIN = conditionIN;
		this.payloadKGIN = payloadKGIN;
		this.emptyFullIN = emptyFullIN;
	}

	public FreightContainer() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4762910165748544510L;
	

	@Id
	@Column(name = "CONTAINER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CONTAINER_NUM", length=32)
	private String containerNum;
	
	@Column(name = "CD", length=2)
	private Integer checkDigit;

	@Column(name = "SIZE", length=8)
	@Enumerated(EnumType.STRING)
	private Size size;

	@Column(name = "TYPE", length=8)
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "IN_CONDITION", length=8)
	@Enumerated(EnumType.STRING)
	private Condition conditionIN;

	@Column(name = "IN_PAYLOAD_KG", length=6)
	private Integer payloadKGIN;

	@Column(name = "IN_EMPTY_FULL", length=8)
	@Enumerated(EnumType.STRING)
	private EmptyFull emptyFullIN;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="DO_ID", nullable=false)
	private DeliveryOrderIN doIN;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="DO_OUT_ID", nullable=true)
	private DeliveryOrderOUT doOUT;
	
	@Column(name = "ORDER_STATUS", length=32)
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Column(name = "MOVE_IN_DATE")
	private Date moveINDate;

	@Column(name = "MOVE_OUT_DATE")
	private Date moveOUTDate;
	
	@Transient
	private Integer totalDayInStorage; 

	public enum Type {
		GP, HC
	}

	public enum Condition {
		AV("Available"), DMG("Damage");
		
		Condition(String desc){
			this.description = desc;
		}
		
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	public enum EmptyFull {
		MTY("EMPTY"), FCL("FULL");
		
		EmptyFull(String desc){
			this.description = desc;
		}
		
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public enum OrderStatus {
		DO_IN("DO_IN"), ON_STORAGE("ON_STORAGE"), DO_OUT("DO_OUT"), MOVE_OUT("MOVE_OUT");
		
		OrderStatus(String desc){
			this.description = desc;
		}
		
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public enum Size {
		
		F_20("20FT"), F_40("40FT");
		
		Size(String desc){
			this.description = desc;
		}
		
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	};
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCheckDigit() {
		return checkDigit;
	}

	public void setCheckDigit(Integer checkDigit) {
		this.checkDigit = checkDigit;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Condition getConditionIN() {
		return conditionIN;
	}

	public void setConditionIN(Condition conditionIN) {
		this.conditionIN = conditionIN;
	}

	public Integer getPayloadKGIN() {
		return payloadKGIN;
	}

	public void setPayloadKGIN(Integer payloadKGIN) {
		this.payloadKGIN = payloadKGIN;
	}

	public EmptyFull getEmptyFullIN() {
		return emptyFullIN;
	}

	public void setEmptyFullIN(EmptyFull emptyFullIN) {
		this.emptyFullIN = emptyFullIN;
	}

	public DeliveryOrderIN getDoIN() {
		return doIN;
	}

	public void setDoIN(DeliveryOrderIN doIN) {
		this.doIN = doIN;
	}

	public String getContainerNum() {
		return containerNum;
	}

	public void setContainerNum(String containerNum) {
		this.containerNum = containerNum;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getMoveINDate() {
		return moveINDate;
	}

	public void setMoveINDate(Date moveINDate) {
		this.moveINDate = moveINDate;
	}

	public Date getMoveOUTDate() {
		return moveOUTDate;
	}

	public void setMoveOUTDate(Date moveOUTDate) {
		this.moveOUTDate = moveOUTDate;
	}

	public DeliveryOrderOUT getDoOUT() {
		return doOUT;
	}

	public void setDoOUT(DeliveryOrderOUT doOUT) {
		this.doOUT = doOUT;
	}

	public Integer getTotalDayInStorage() {
		
		if(this.orderStatus == null || this.orderStatus == OrderStatus.DO_IN ) 
			return 0;
		
		else if(this.orderStatus == OrderStatus.ON_STORAGE || this.orderStatus == OrderStatus.DO_OUT )
			return DateUtils.totalDiffDay(this.moveINDate, new Date());
		
		else if(this.orderStatus == OrderStatus.MOVE_OUT )
			return DateUtils.totalDiffDay(this.moveINDate, this.moveOUTDate);
		
		return 0;
	}

	public void setTotalDayInStorage(Integer totalDayInStorage) {
		this.totalDayInStorage = totalDayInStorage;
	}

}

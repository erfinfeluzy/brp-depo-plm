package com.depobrp.model.order;

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
	
	@Column(name = "ORDER_STATUS", length=32)
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	public enum Type {
		GP, HC
	}

	public enum Condition {
		AV, DMG
	}

	public enum EmptyFull {
		EMPTY, FULL
	}
	
	public enum OrderStatus {
		DO_IN, MOVE_IN, DO_OUT, MOVE_OUT
	}
	
	public enum Size {
		
		F_20("20\""), F_40("40\"");
		
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

}

package com.testcontainerslab.testcontainerslab.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "USUARIO")
@JsonInclude(Include.NON_EMPTY)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Usuario {

	/**
	 * @label id
	 */
	@Id
	@Column(updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO")
	private Long id;

	@Column
	private String login;

	@Column
	private String nome;

}
package com.inoptra.employeedepartmentdemo.models;

/* @Author: Shrikrishna Prabhumirashi
 * @Description:
 * SalaryComponent is dependent upon base salary and can be calculated as baseSalary multiplied by respective factor.
 *  i.e. SalaryComponent_amount = baseSalary * factor;
 *  Actual salary can be calculated as sum of all SalaryComponent amounts.
 * */

import javax.persistence.*;

@Entity
@Table(name = "salary_component")
public class SalaryComponent {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private double factor;


	//@JoinColumn(name="salary_id", nullable=false)
	@ManyToOne
	@JoinTable(name = "salary_salarycomponent",
			inverseJoinColumns = { @JoinColumn(name = "salary_id", referencedColumnName = "id") },
			joinColumns = { @JoinColumn(name = "salary_salarycomponentid", referencedColumnName = "id") }
	)
	private Salary salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}

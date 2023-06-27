package com.inoptra.employeedepartmentdemo.models;

import javax.persistence.*;
import java.util.List;

/* @Author: Shrikrishna Prabhumirashi
 * @Description:
 * SalaryComponent is dependent upon base salary and can be calculated as baseSalary multiplied by respective factor.
 *  i.e. SalaryComponent_amount = baseSalary * factor;
 *  Actual salary can be calculated as sum of all SalaryComponent amounts.
 * */

@Entity
@Table(name = "salary")
public class Salary {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "base_salary")
	private double baseSalary;

	@OneToMany
	@JoinTable(name = "salary_salarycomponent",
			joinColumns = { @JoinColumn(name = "salary_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "salary_salarycomponentid", referencedColumnName = "id") }
	)
	private List<SalaryComponent> salaryComponents;

	public double getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}
	public List<SalaryComponent> getSalaryComponents() {
		return salaryComponents;
	}
	public void setSalaryComponents(List<SalaryComponent> salaryComponents) {
		this.salaryComponents = salaryComponents;
	}

}

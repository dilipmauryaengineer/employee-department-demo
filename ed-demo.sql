---- Reset ----

drop table employee;
drop table salary_salarycomponent;
drop table salary_component;
drop table salary;
drop table department;

----- DDL ----

create table department(
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(200)
);

create table salary(
	id int PRIMARY KEY AUTO_INCREMENT,
	base_salary decimal(8,3)
);

create table salary_component(
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(200),
	factor decimal(5,4)
);

create table salary_salarycomponent(
	salary_id int,
	salary_salarycomponentid int,
	PRIMARY KEY(salary_id, salary_salarycomponentid)
);

ALTER TABLE salary_salarycomponent
ADD FOREIGN KEY (salary_id) REFERENCES salary(id);

ALTER TABLE salary_salarycomponent
ADD FOREIGN KEY (salary_salarycomponentid) REFERENCES salary_component(id);

create table employee(
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(200),
	salary_id int,
	department_id int
);

ALTER TABLE employee
ADD FOREIGN KEY (salary_id) REFERENCES salary(id);

ALTER TABLE employee
ADD FOREIGN KEY (department_id) REFERENCES department(id);

---- DML ----
insert into department(id, name) values (1, 'Dept-1');
insert into department(id, name) values (2, 'Dept-2');
insert into department(id, name) values (3, 'Dept-3');

insert into salary(id, base_salary) values (11, 100);
insert into salary(id, base_salary) values (12, 200);
--insert into salary(id, base_salary) values (13, 300);
--insert into salary(id, base_salary) values (14, 400);

insert into salary_component(id, name, factor) values (101, 'factor-1', 0.7);
insert into salary_component(id, name, factor) values (102, 'factor-2', 0.8);
insert into salary_component(id, name, factor) values (103, 'factor-3', 0.9);
insert into salary_component(id, name, factor) values (104, 'factor-4', 1.0);

insert into salary_salarycomponent(salary_id, salary_salarycomponentid) values (11, 101);
insert into salary_salarycomponent(salary_id, salary_salarycomponentid) values (11, 104);

insert into salary_salarycomponent(salary_id, salary_salarycomponentid) values (12, 103);

insert into employee(id, name, salary_id, department_id) values (1001, 'Emp-1', 11, 1);
insert into employee(id, name, salary_id, department_id) values (1002, 'Emp-2', 12, 2);
insert into employee(id, name, salary_id, department_id) values (1003, 'Emp-3', 12, 2);

commit;


/*
Salary by Dept 1 - (0.7 * 100 + 1.0 * 100) = 70+100 = 170
Salary by Dept 2 - (0.9 * 200) + (0.9 * 200) = 180 + 180 = 360
Salary by Dept 3 - 0

-- Query to get Salary by Department
Select d.id, sum(s.base_salary * sc.factor)
from employee e, department d, salary s, salary_component sc, salary_salarycomponent ssc
where d.id = e.department_id
and e.salary_id = s.id
and s.id = ssc.salary_id
and ssc.salary_salarycomponentid = sc.id
group by d.id;

*/

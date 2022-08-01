--User1@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (1, 'Employee1LastName', 'Employee1FirstName', 'EMPLOYEE', 'user1_mogilev@yopmail.com');
--User2@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (2, 'Employee2LastName', 'Employee2FirstName', 'EMPLOYEE', 'user2_mogilev@yopmail.com');
--Manager1@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (3, 'Manager1LastName', 'Manager1FirstName', 'MANAGER', 'manager1_mogilev@yopmail.com');
--Manager2@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (4, 'Manager2LastName', 'Manager2FirstName', 'MANAGER', 'manager2_mogilev@yopmail.com');
--Engineer1@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (5, 'Engineer1LastName', 'Engineer1FirstName', 'ENGINEER', 'engineer1_mogilev@yopmail.com');
--Engineer2@Pass
insert into USERS (user_id, last_name, first_name, role, email) values (6, 'Engineer2LastName', 'Engineer2FirstName', 'ENGINEER', 'engineer2_mogilev@yopmail.com');

insert into CATEGORIES (id, name) values (1, 'Application & Services');
insert into CATEGORIES (id, name) values (2, 'Benefits & Paper Work');
insert into CATEGORIES (id, name) values (3, 'Hardware & Software');
insert into CATEGORIES (id, name) values (4, 'People Management');
insert into CATEGORIES (id, name) values (5, 'Security & Access');
insert into CATEGORIES (id, name) values (6, 'Workplaces & Facilities');

insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (1, 'Ticke/t1', 'Description of Ticket1',current_date, date '2022-10-25', 5, 1, 3, 0, 1, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (2, 'Ticket2', 'Description of Ticket2',current_date, date '2022-10-25', 6, 1, 3, 2, 5, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (3, 'Ticket3', 'Description of Ticket3',current_date, date '2022-10-13', 5, 1, 3, 3, 5, 3);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (4, 'Ticket4', 'Description of Ticket4',current_date, date '2022-10-12', 5, 3, 3, 0, 1, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (5, 'Ticket5', 'Description of Ticket5',current_date, date '2022-10-12', 5, 2, null, 0, 2, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (6, 'Ticket6', 'Description of Ticket6',current_date, date '2022-10-27', 5, 1, null, 0, 2, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (7, 'Ticket7', 'Description of Ticket7',current_date, date '2022-10-21', 6, 4, 3, 2, 3, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (8, 'Ticket8', 'Description of Ticket8',current_date, date '2022-10-12', 5, 1, 3, 1, 6, 5);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (9, 'Ticke/t9', 'Description of Ticket9',current_date, date '2022-10-12', 5, 3, 3, 3, 1, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (10, 'Ticket10', 'Description of Ticket10',current_date, date '2022-10-13', 5, 2, 4, 0, 6, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (11, 'Ticket11', 'Description of Ticket11',current_date, date '2022-10-28', 5, 1, 3, 0, 6, 6);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (12, 'Ticket12', 'Description of Ticket12',current_date, date '2022-10-11', 6, 1, 4, 2, 5, 6);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (13, 'Ticket13', 'Description of Ticket13',current_date, date '2022-10-8', 5, 4, 3, 3, 6, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (14, 'Ticket14', 'Description of Ticket14',current_date, date '2022-10-7', 5, 3, 3, 0, 5, 3);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (15, 'Ticket15', 'Description of Ticket15',current_date, date '2022-10-13', 5, 2, 4, 0, 3, 3);

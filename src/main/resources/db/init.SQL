--User1@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (1, 'Employee1LastName', 'Employee1FirstName', 'EMPLOYEE', '$2a$12$q.6S4gBWTPN4lEznD6A1muMgewSeXoqByODFtHVdb1BDW8VtfB2jm', 'user1_mogilev@yopmail.com');
--User2@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (2, 'Employee2LastName', 'Employee2FirstName', 'EMPLOYEE', '$2a$12$p31Ej16iY8u3TJaeEg7vHu4N/W3.hteG2Sn4Q0cOUpLErFIZnht6C', 'user2_mogilev@yopmail.com');
--Manager1@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (3, 'Manager1LastName', 'Manager1FirstName', 'MANAGER', '$2a$12$mawnbtMFQah4NrGBnraKDODNkdrME/WFFg57QAn7q79AGgD92Cz36', 'manager1_mogilev@yopmail.com');
--Manager2@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (4, 'Manager2LastName', 'Manager2FirstName', 'MANAGER', '$2a$12$DA4j56XggwYZbf4yefGMcuzqGF1DtyY5tAjAx7BqfNpNtHSxuJWO6', 'manager2_mogilev@yopmail.com');
--Engineer1@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (5, 'Engineer1LastName', 'Engineer1FirstName', 'ENGINEER', '$2a$12$8WJcH9viE.gLsOyPNeSbz.xrHA617b0yWAGOIyd74ye8K6/jmqCkK', 'engineer1_mogilev@yopmail.com');
--Engineer2@Pass
insert into USERS (user_id, last_name, first_name, role, password, email) values (6, 'Engineer2LastName', 'Engineer2FirstName', 'ENGINEER', '$2a$12$jF1wnbyXfj060jf.uCV6Ke5PXySNFvgKRZFme.5lcIbUSTukmKws6', 'engineer2_mogilev@yopmail.com');

insert into CATEGORIES (id, name) values (1, 'Application & Services');
insert into CATEGORIES (id, name) values (2, 'Benefits & Paper Work');
insert into CATEGORIES (id, name) values (3, 'Hardware & Software');
insert into CATEGORIES (id, name) values (4, 'People Management');
insert into CATEGORIES (id, name) values (5, 'Security & Access');
insert into CATEGORIES (id, name) values (6, 'Workplaces & Facilities');

insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (1, 'Ticke/t1', 'Description of Ticket1',CURRENT_DATE, DATE '2022-10-25', 5, 1, 3, 0, 1, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (2, 'Ticket2', 'Description of Ticket2',CURRENT_DATE, DATE '2022-10-25', 6, 1, 3, 2, 5, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (3, 'Ticket3', 'Description of Ticket3',CURRENT_DATE, DATE '2022-10-13', 5, 1, 3, 3, 5, 3);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (4, 'Ticket4', 'Description of Ticket4',CURRENT_DATE, DATE '2022-10-12', 5, 3, 3, 0, 1, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (5, 'Ticket5', 'Description of Ticket5',CURRENT_DATE, DATE '2022-10-12', 5, 2, null, 0, 2, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (6, 'Ticket6', 'Description of Ticket6',CURRENT_DATE, DATE '2022-10-27', 5, 1, null, 0, 2, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (7, 'Ticket7', 'Description of Ticket7',CURRENT_DATE, DATE '2022-10-21', 6, 4, 3, 2, 3, 4);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (8, 'Ticket8', 'Description of Ticket8',CURRENT_DATE, DATE '2022-10-12', 5, 1, 3, 1, 6, 5);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (9, 'Ticke/t9', 'Description of Ticket9',CURRENT_DATE, DATE '2022-10-12', 5, 3, 3, 3, 1, 2);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (10, 'Ticket10', 'Description of Ticket10',CURRENT_DATE, DATE '2022-10-13', 5, 2, 4, 0, 6, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (11, 'Ticket11', 'Description of Ticket11',CURRENT_DATE, DATE '2022-10-28', 5, 1, 3, 0, 6, 6);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (12, 'Ticket12', 'Description of Ticket12',CURRENT_DATE, DATE '2022-10-11', 6, 1, 4, 2, 5, 6);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (13, 'Ticket13', 'Description of Ticket13',CURRENT_DATE, DATE '2022-10-8', 5, 4, 3, 3, 6, 1);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (14, 'Ticket14', 'Description of Ticket14',CURRENT_DATE, DATE '2022-10-7', 5, 3, 3, 0, 5, 3);
insert into TICKETS (id, name, description, created_on, desired_resolution_date, assignee_id, owner_id, approver_id, urgency_id, status_id, category_id) values (15, 'Ticket15', 'Description of Ticket15',CURRENT_DATE, DATE '2022-10-13', 5, 2, 4, 0, 3, 3);

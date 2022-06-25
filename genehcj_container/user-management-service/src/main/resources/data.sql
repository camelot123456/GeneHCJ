insert into `genehcj-user-management`.`t_authority`(`name`) values ('ROLE_ADMIN');
insert into `genehcj-user-management`.`t_authority`(`name`) values ('ROLE_USER');

insert into `genehcj-user-management`.`t_user`(`id`, `email`, `password_hash`, `activated`, `login`, `auth_provider`) values ('1', 'admin@admin.com', '$2a$04$o5fwFP1289YLhqSDkkxC3emG6yj04PVphvd3TfrvuqtBzbyyGCRvK', 1, 'admin', 'LOCAL');
insert into `genehcj-user-management`.`t_user`(`id`, `email`, `password_hash`, `activated`, `login`, `auth_provider`) values ('2', 'user@user.com', '$2a$04$GvPCvb0wXtzQMFfedhQxD.q37P5xlap.TsBD63COOnQ7wmPF5sAUS', 1, 'user', 'LOCAL');

insert into `genehcj-user-management`.`t_user_authority`(`id_user`, `id_authority`) values ('1', 'ROLE_ADMIN');
insert into `genehcj-user-management`.`t_user_authority`(`id_user`, `id_authority`) values ('1', 'ROLE_USER');
insert into `genehcj-user-management`.`t_user_authority`(`id_user`, `id_authority`) values ('2', 'ROLE_USER');
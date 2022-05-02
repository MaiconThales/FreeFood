# FreeFood

Projeto de teste para fazer algo semelhante ao IFood. Utilizando Spring Boot para Back e Angular para Front.

## Feito Com:
- Visual Studio: https://code.visualstudio.com/
- Spring Boot: https://spring.io
- Angular: https://angular.io
- Java 11: https://www.java.com/pt-BR/
- MySQL: https://www.mysql.com

# Utilização
- Back: Após baixar o projeto deverá fazer o update do Maven para que os Frameworks sejam puxados.
- - Abaixo script para executar no banco: 
```MySQL 
	INSERT INTO role (id, description, name) 
	VALUES 	(1, 'Admin role', 'ROLE_USER'),
			(2, 'User role', 'ROLE_MODERATOR'),
			(3, 'Admin role', 'ROLE_ADMIN');
```

# Links utilizados para auxilios
- Spring Security: https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt
- Spring Security com detalhes e GIT para auxilio: https://medium.com/@akhileshanand/spring-boot-api-security-with-jwt-and-role-based-authorization-fea1fd7c9e32

https://www.npmjs.com/package/@angular/flex-layout
https://www.npmjs.com/package/@ngx-translate/core
https://www.npmjs.com/package/ngx-skeleton-loader
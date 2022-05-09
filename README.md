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
- Spring Security with Angular: https://www.bezkoder.com/spring-boot-jwt-authentication/, https://www.bezkoder.com/spring-boot-refresh-token-jwt/ e https://www.bezkoder.com/angular-12-refresh-token/

## 📚 Bibliotecas utilizas no projeto
### Angular Material:
- Link: https://material.angular.io
- Comando: 'ng add @angular/material'
### ngx-translate:
- Link: https://github.com/ngx-translate/core
- Comandos: 'npm install @ngx-translate/core --save' e 'npm install @ngx-translate/http-loader --save'
### flex-layout:
- Link: https://github.com/angular/flex-layout
- Comando: npm i -s @angular/flex-layout @angular/cdk
### Router Guard:
- Comando abaixo é para implementar o GUARD para quando estiver logado.
- Comando: npm install --save @auth0/angular-jwt
- Comando abaixo é para implementar o GUARD por permissão de user.
- Comando: npm install --save jwt-decode
### Sketeton (Aquardando):
- Link: https://www.npmjs.com/package/ngx-skeleton-loader
### ngx-mask
- Link: https://www.npmjs.com/package/ngx-mask
- Comando: npm i ngx-mask

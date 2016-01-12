## vraptor-i18n

Informações sobre o vraptor-i18n na página principal: https://github.com/caelum/vraptor-i18n.

## Sobre esse fork

O objetivo aqui é tornar o vraptor-i18n compatível com a versão **4.2.0-RC3** do vraptor (atualmente a última).

Foram realizadas algumas correções sem alterar as funções.

Para testar a versão beta adicione o repositório do JitPack.io no seu **pom.xml** e uma dependência ligada a essa página:
```xml
	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependencies>	
	[...]
		<dependency>
			<groupId>com.github.gpassero</groupId>
			<artifactId>vraptor-i18n</artifactId>
			<version>420c75e93b</version>
		</dependency>	
	</dependencies>	
```	

Se preferir, baixe o pacote na pasta /releases e inclua o jar manualmente.
